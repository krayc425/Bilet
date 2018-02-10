package com.krayc.service.impl;

import com.krayc.model.*;
import com.krayc.repository.*;
import com.krayc.service.BookService;
import com.krayc.service.LevelService;
import com.krayc.service.OrderService;
import com.krayc.util.DateFormatter;
import com.krayc.util.OrderStatus;
import com.krayc.util.OrderType;
import com.krayc.util.PayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventSeatRepository orderEventSeatRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LevelService levelService;

    @Autowired
    private BookService bookService;

    public void createOrder(OrderEntity orderEntity, List<OrderEventSeatEntity> eventSeatEntityList) {
        Date date = new Date(System.currentTimeMillis());
        orderEntity.setOrderTime(DateFormatter.getDateFormatter().stringFromDate(date));

        orderRepository.saveAndFlush(orderEntity);

        for (OrderEventSeatEntity orderEventSeatEntity : eventSeatEntityList) {
            orderEventSeatEntity.setOrder(orderEntity);
            if (orderEntity.getType() == OrderType.RANDOM_SEAT) {
                orderEventSeatEntity.setSeatNumber(-1);
            } else {
                orderEventSeatEntity.setSeatNumber(findMinimumAvailableSeatNumberByEventSeat(orderEventSeatEntity.getEventSeatByEsid()));
            }
            orderEventSeatRepository.saveAndFlush(orderEventSeatEntity);
        }

        if (orderEntity.getMemberCouponEntity() != null) {
            memberCouponRepository.updateUsage(1, orderEntity.getMemberCouponEntity().getMcid());
        }

        // 15 分钟取消订单
        Timer timer = new Timer();
        timer.schedule(new OrderChecker(orderEntity.getOid()), 15 * 60 * 1000);
    }

    public Boolean payOrder(OrderEntity orderEntity, MemberEntity memberEntity) {
        Double orderPrice = calculateTotalPriceOfOrder(orderEntity, memberEntity);
        BankAccountEntity bankAccountEntity = bankAccountRepository.findByBankAccount(memberEntity.getBankAccount());

        if (orderEntity.getType().equals(OrderType.AT_VENUE)) {
            orderRepository.updateStatus(OrderStatus.ORDER_PAID, orderEntity.getOid());

            MemberBookEntity memberBookEntity = new MemberBookEntity();
            memberBookEntity.setMember(memberEntity);
            memberBookEntity.setOrder(orderEntity);
            memberBookEntity.setType(PayType.AT_VENUE_PAY);
            memberBookEntity.setAmount(-orderPrice);
            memberBookEntity.setTime(new Timestamp(System.currentTimeMillis()));

            bookService.createMemberBookEntity(memberBookEntity);

            return true;
        } else {
            if (bankAccountEntity.getBalance() >= orderPrice) {
                bankAccountRepository.updateBalance(bankAccountEntity.getBalance() - orderPrice, bankAccountEntity.getBankAccount());
                if (orderEntity.getType() == OrderType.CHOOSE_SEAT) {
                    orderRepository.updateStatus(OrderStatus.ORDER_PAID, orderEntity.getOid());
                } else {
                    orderRepository.updateStatus(OrderStatus.ORDER_WAITING, orderEntity.getOid());
                }

                MemberBookEntity memberBookEntity = new MemberBookEntity();
                memberBookEntity.setMember(memberEntity);
                memberBookEntity.setOrder(orderEntity);
                memberBookEntity.setType(PayType.ONLINE_PAY);
                memberBookEntity.setAmount(-orderPrice);
                memberBookEntity.setTime(new Timestamp(System.currentTimeMillis()));

                bookService.createMemberBookEntity(memberBookEntity);

                return true;
            } else {
                return false;
            }
        }
    }

    public void cancelOrder(OrderEntity orderEntity) {
        orderRepository.updateStatus(OrderStatus.ORDER_CANCELLED, orderEntity.getOid());

        if (orderEntity.getMemberCouponEntity() != null) {
            memberCouponRepository.updateUsage(0, orderEntity.getMemberCouponEntity().getMcid());
        }

        for (OrderEventSeatEntity eventSeatEntity : orderEventSeatRepository.findByOrder(orderEntity)) {
            orderEventSeatRepository.updateStatus(1, eventSeatEntity.getOesid());
        }
    }

    public void refundOrder(OrderEntity orderEntity, MemberEntity memberEntity) {
        BankAccountEntity bankAccountEntity = bankAccountRepository.findByBankAccount(memberEntity.getBankAccount());

        Double refundPrice = calculateRefundAmount(orderEntity, memberEntity);
        bankAccountRepository.updateBalance(bankAccountEntity.getBalance() + refundPrice,
                bankAccountEntity.getBankAccount());
        orderRepository.updateStatus(OrderStatus.ORDER_REFUNDED, orderEntity.getOid());

        MemberBookEntity memberBookEntity = new MemberBookEntity();
        memberBookEntity.setMember(memberEntity);
        memberBookEntity.setOrder(orderEntity);
        memberBookEntity.setType(PayType.ONLINE_REFUND);
        memberBookEntity.setAmount(refundPrice);
        memberBookEntity.setTime(new Timestamp(System.currentTimeMillis()));

        bookService.createMemberBookEntity(memberBookEntity);

        if (orderEntity.getMemberCouponEntity() != null) {
            memberCouponRepository.updateUsage(0, orderEntity.getMemberCouponEntity().getMcid());
        }

        for (OrderEventSeatEntity eventSeatEntity : orderEventSeatRepository.findByOrder(orderEntity)) {
            orderEventSeatRepository.updateStatus(1, eventSeatEntity.getOesid());
        }
    }

    public void confirmOrder(OrderEntity orderEntity) {
        orderRepository.updateStatus(OrderStatus.ORDER_CONFIRMED, orderEntity.getOid());

        MemberEntity memberEntity = orderEntity.getMemberByMid();
        // 增加积分
        Double totalPrice = calculateTotalPriceOfOrder(orderEntity, memberEntity);
        int deltaPoint = (int) (0 + totalPrice);
        memberRepository.updateCurrentPoint(memberEntity.getCurrentPoint() + deltaPoint, memberEntity.getMid());
        memberRepository.updateTotalPoint(memberEntity.getTotalPoint() + deltaPoint, memberEntity.getMid());

        // 写入管理员和公司账本
        bookService.updateEventAmount(orderEntity, totalPrice);
    }

    public OrderEntity findByOid(Integer oid) {
        return orderRepository.findOne(oid);
    }

    public List<OrderEntity> findOrderByEvent(EventEntity eventEntity) {
        return orderRepository.findByEventByEid(eventEntity);
    }

    public List<OrderEntity> findOrderByMember(MemberEntity memberEntity) {
        return orderRepository.findByMemberByMid(memberEntity);
    }

    public void distributeOrderEventSeat(OrderEntity orderEntity) {
        if (orderEntity.getType() != OrderType.RANDOM_SEAT) {
            return;
        }

        Collection<OrderEventSeatEntity> orderEventSeatEntities = orderEventSeatRepository.findByOrder(orderEntity);
        for (OrderEventSeatEntity orderEventSeatEntity : orderEventSeatEntities) {
            EventSeatEntity eventSeatEntity = orderEventSeatEntity.getEventSeatByEsid();
            if (eventSeatEntity.getNumber() + 1 - findMinimumAvailableSeatNumberByEventSeat(eventSeatEntity) < orderEventSeatEntities.size()) {
                // 配票失败，退款
                refundOrder(orderEntity, orderEntity.getMemberByMid());
                return;
            }
        }

        // 配票成功，更新订单状态，更新位置信息
        orderRepository.updateStatus(OrderStatus.ORDER_PAID, orderEntity.getOid());

        for (OrderEventSeatEntity orderEventSeatEntity : orderEventSeatEntities) {
            orderEventSeatEntity.setSeatNumber(findMinimumAvailableSeatNumberByEventSeat(orderEventSeatEntity.getEventSeatByEsid()));
            orderEventSeatEntity.setIsValid(0);
            orderEventSeatRepository.saveAndFlush(orderEventSeatEntity);
        }
    }

    private Integer findMinimumAvailableSeatNumberByEventSeat(EventSeatEntity eventSeatEntity) {
        for (int i = 1; i <= eventSeatEntity.getNumber(); i++) {
            if (orderEventSeatRepository.findBySeatNumberAndIsValidAndEventSeatByEsidIs(i, 0, eventSeatEntity) == null) {
                return i;
            }
        }
        return 1;
    }

    public Double calculateTotalPriceOfOrder(OrderEntity orderEntity, MemberEntity memberEntity) {
        Double totalAmount = 0.0;
        for (OrderEventSeatEntity orderEventSeatEntity : orderEventSeatRepository.findByOrder(orderEntity)) {
            totalAmount += orderEventSeatEntity.getEventSeatByEsid().getPrice();
        }
        if (orderEntity.getMemberCouponEntity() != null) {
            totalAmount *= orderEntity.getMemberCouponEntity().getCouponByCid().getDiscount();
        }
        return totalAmount * levelService.findLevelEntityWithPoint(memberEntity.getTotalPoint()).getDiscount();
    }

    private Double calculateRefundAmount(OrderEntity orderEntity, MemberEntity memberEntity) {
        double percent;
        // 立即购买，全额退款
        if (orderEntity.getType() == OrderType.RANDOM_SEAT) {
            percent = 1.0;
        } else {
            long timeEvent = orderEntity.getEventByEid().getTime().getTime();
            long timeOrder = orderEntity.getOrderTime().getTime();
            long delta = timeEvent - timeOrder;
            int days = (int) (delta / (86400 * 1000));
            switch (days) {
                case 0:
                case 1:
                    percent = 0.0;
                    break;
                case 2:
                case 3:
                    percent = 0.25;
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                    percent = 0.5;
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    percent = 0.75;
                    break;
                default:
                    percent = 1.0;
                    break;
            }
        }
        return calculateTotalPriceOfOrder(orderEntity, memberEntity) * percent;
    }

    public class OrderChecker extends TimerTask {

        private Integer oid;

        OrderChecker(Integer oid) {
            this.oid = oid;
        }

        @Override
        public void run() {
            OrderEntity orderEntity = orderRepository.findOne(oid);
            if (orderEntity.getStatus() == OrderStatus.ORDER_CREATED) {
                orderRepository.updateStatus(OrderStatus.ORDER_CANCELLED, oid);
            }
        }

    }

}
