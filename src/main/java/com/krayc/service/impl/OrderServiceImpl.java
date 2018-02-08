package com.krayc.service.impl;

import com.krayc.model.*;
import com.krayc.repository.*;
import com.krayc.service.LevelService;
import com.krayc.service.OrderService;
import com.krayc.util.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private EventSeatRepository eventSeatRepository;

    public void createOrder(OrderEntity orderEntity, List<OrderEventSeatEntity> eventSeatEntityList) {
        Date date = new Date(System.currentTimeMillis());
        orderEntity.setOrderTime(DateFormatter.getDateFormatter().stringFromDate(date));

        orderRepository.saveAndFlush(orderEntity);

        for (OrderEventSeatEntity orderEventSeatEntity : eventSeatEntityList) {
            orderEventSeatEntity.setOrder(orderEntity);
            orderEventSeatEntity.setSeatNumber(findMinimumAvailableSeatNumberByEventSeat(orderEventSeatEntity.getEventSeatByEsid()));
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

        if (orderEntity.getType().equals(Byte.valueOf("1"))) {
            orderRepository.updateStatus(Byte.valueOf("1"), orderEntity.getOid());
            return true;
        } else {
            if (bankAccountEntity.getBalance() >= orderPrice) {
                bankAccountRepository.updateBalance(bankAccountEntity.getBalance() - orderPrice, bankAccountEntity.getBankAccount());
                orderRepository.updateStatus(Byte.valueOf("1"), orderEntity.getOid());
                return true;
            } else {
                return false;
            }
        }
    }

    public void cancelOrder(OrderEntity orderEntity) {
        orderRepository.updateStatus(Byte.valueOf("2"), orderEntity.getOid());

        for (OrderEventSeatEntity orderEventSeatEntity : orderEntity.getOrderEventSeats()) {
            orderEventSeatRepository.updateStatus(0, orderEventSeatEntity.getOesid());
        }

        if (orderEntity.getMemberCouponEntity() != null) {
            memberCouponRepository.updateUsage(0, orderEntity.getMemberCouponEntity().getMcid());
        }

        for (OrderEventSeatEntity eventSeatEntity : orderEventSeatRepository.findByOrder(orderEntity)) {
            orderEventSeatRepository.updateStatus(1, eventSeatEntity.getOesid());
        }
    }

    public void refundOrder(OrderEntity orderEntity, MemberEntity memberEntity) {
        BankAccountEntity bankAccountEntity = bankAccountRepository.findByBankAccount(memberEntity.getBankAccount());

        bankAccountRepository.updateBalance(bankAccountEntity.getBalance() + calculateRefundAmount(orderEntity, memberEntity),
                bankAccountEntity.getBankAccount());
        orderRepository.updateStatus(Byte.valueOf("3"), orderEntity.getOid());

        for (OrderEventSeatEntity orderEventSeatEntity : orderEntity.getOrderEventSeats()) {
            orderEventSeatRepository.updateStatus(0, orderEventSeatEntity.getOesid());
        }

        if (orderEntity.getMemberCouponEntity() != null) {
            memberCouponRepository.updateUsage(0, orderEntity.getMemberCouponEntity().getMcid());
        }

        for (OrderEventSeatEntity eventSeatEntity : orderEventSeatRepository.findByOrder(orderEntity)) {
            orderEventSeatRepository.updateStatus(1, eventSeatEntity.getOesid());
        }
    }

    public void confirmOrder(OrderEntity orderEntity) {
        orderRepository.updateStatus(Byte.valueOf("4"), orderEntity.getOid());

        MemberEntity memberEntity = orderEntity.getMemberByMid();
        // 增加积分
        int deltaPoint = (int) (0 + calculateTotalPriceOfOrder(orderEntity, memberEntity));
        memberRepository.updateCurrentPoint(memberEntity.getCurrentPoint() + deltaPoint, memberEntity.getMid());
        memberRepository.updateTotalPoint(memberEntity.getTotalPoint() + deltaPoint, memberEntity.getMid());
    }

    public OrderEntity findByOid(Integer oid) {
        return orderRepository.findOne(oid);
    }

    public List<OrderEntity> findOrderByEvent(EventEntity eventEntity) {
        return orderRepository.findByEventByEid(eventEntity);
    }

    private Integer findMinimumAvailableSeatNumberByEventSeat(EventSeatEntity eventSeatEntity) {
        for (int i = 0; i < eventSeatEntity.getNumber(); i++) {
            if (orderEventSeatRepository.findBySeatNumberAndIsValid(i + 1, 1) == null) {
                return i + 1;
            }
        }
        return 1;
    }

    private Double calculateTotalPriceOfOrder(OrderEntity orderEntity, MemberEntity memberEntity) {
        Double totalAmount = 0.0;
        for (OrderEventSeatEntity orderEventSeatEntity : orderEventSeatRepository.findByOrder(orderEntity)) {
//            orderEventSeatEntity.get
            System.out.println(orderEventSeatEntity.getOesid());
            System.out.println(orderEventSeatEntity.getEventSeatByEsid().getEsid());
            totalAmount += orderEventSeatEntity.getEventSeatByEsid().getPrice();
        }
        if (orderEntity.getMemberCouponEntity() != null) {
            totalAmount *= orderEntity.getMemberCouponEntity().getCouponByCid().getDiscount();
        }
        return totalAmount * levelService.findLevelEntityWithPoint(memberEntity.getTotalPoint()).getDiscount();
    }

    private Double calculateRefundAmount(OrderEntity orderEntity, MemberEntity memberEntity) {
        long timeEvent = orderEntity.getEventByEid().getTime().getTime();
        long timeOrder = orderEntity.getOrderTime().getTime();
        long delta = timeEvent - timeOrder;
        double percent;
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
            if (orderEntity.getStatus() == Byte.valueOf("0")) {
                orderRepository.updateStatus(Byte.valueOf("2"), oid);
            }
        }

    }

}
