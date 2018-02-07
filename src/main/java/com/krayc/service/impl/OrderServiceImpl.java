package com.krayc.service.impl;

import com.krayc.model.BankAccountEntity;
import com.krayc.model.EventSeatEntity;
import com.krayc.model.OrderEntity;
import com.krayc.model.OrderEventSeatEntity;
import com.krayc.repository.BankAccountRepository;
import com.krayc.repository.MemberCouponRepository;
import com.krayc.repository.OrderEventSeatRepository;
import com.krayc.repository.OrderRepository;
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

    public void createOrder(OrderEntity orderEntity, List<OrderEventSeatEntity> eventSeatEntityList) {
        Date date = new Date(System.currentTimeMillis());
        orderEntity.setOrderTime(DateFormatter.getDateFormatter().stringFromDate(date));

        orderRepository.saveAndFlush(orderEntity);

        for (OrderEventSeatEntity orderEventSeatEntity : eventSeatEntityList) {
            orderEventSeatEntity.setOrderByOid(orderEntity);
            orderEventSeatEntity.setSeatNumber(findMinimumAvailableSeatNumberByEventSeat(orderEventSeatEntity.getEventSeatByEsid()));
            orderEventSeatRepository.save(orderEventSeatEntity);
        }

        if (orderEntity.getMemberCouponEntity() != null) {
            memberCouponRepository.updateUsage(1, orderEntity.getMemberCouponEntity().getMcid());
        }

        // 15 分钟取消订单
        Timer timer = new Timer();
        timer.schedule(new OrderChecker(orderEntity.getOid()), 15 * 60 * 1000);
    }

    public Boolean payOrder(OrderEntity orderEntity, String bankAccount) {
        Double orderPrice = calculateTotalPriceOfOrder(orderEntity);
        BankAccountEntity bankAccountEntity = bankAccountRepository.findByBankAccount(bankAccount);
        if (bankAccountEntity.getBalance() >= orderPrice) {
            bankAccountRepository.updateBalance(bankAccountEntity.getBalance() - orderPrice, bankAccountEntity.getBankAccount());
            orderRepository.updateStatus(Byte.valueOf("1"), orderEntity.getOid());
            return true;
        } else {
            return false;
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
    }

    public void refundOrder(OrderEntity orderEntity, String bankAccount) {
        BankAccountEntity bankAccountEntity = bankAccountRepository.findByBankAccount(bankAccount);

        bankAccountRepository.updateBalance(bankAccountEntity.getBalance() + calculateRefundAmount(orderEntity),
                bankAccountEntity.getBankAccount());
        orderRepository.updateStatus(Byte.valueOf("3"), orderEntity.getOid());

        for (OrderEventSeatEntity orderEventSeatEntity : orderEntity.getOrderEventSeats()) {
            orderEventSeatRepository.updateStatus(0, orderEventSeatEntity.getOesid());
        }

        if (orderEntity.getMemberCouponEntity() != null) {
            memberCouponRepository.updateUsage(0, orderEntity.getMemberCouponEntity().getMcid());
        }
    }

    public void checkOutOfTimeOrder() {
        System.out.println("Check Order Now");
    }

    public OrderEntity findByOid(Integer oid) {
        return orderRepository.findOne(oid);
    }

    private Integer findMinimumAvailableSeatNumberByEventSeat(EventSeatEntity eventSeatEntity) {
        for (int i = 0; i < eventSeatEntity.getNumber(); i++) {
            if (orderEventSeatRepository.findBySeatNumberAndIsValid(i + 1, 1) == null) {
                return i + 1;
            }
        }
        return 1;
    }

    private Double calculateTotalPriceOfOrder(OrderEntity orderEntity) {
        Double totalAmount = 0.0;
        for (OrderEventSeatEntity orderEventSeatEntity : orderEntity.getOrderEventSeats()) {
            totalAmount += orderEventSeatEntity.getEventSeatByEsid().getPrice();
        }
        if (orderEntity.getMemberCouponEntity() != null) {
            totalAmount *= orderEntity.getMemberCouponEntity().getCouponByCid().getDiscount();
        }
        return totalAmount;
    }

    private Double calculateRefundAmount(OrderEntity orderEntity) {
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
        return calculateTotalPriceOfOrder(orderEntity) * percent;
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
