package com.krayc.service.impl;

import com.krayc.model.BankAccountEntity;
import com.krayc.model.EventSeatEntity;
import com.krayc.model.OrderEntity;
import com.krayc.model.OrderEventSeatEntity;
import com.krayc.repository.BankAccountRepository;
import com.krayc.repository.OrderEventSeatRepository;
import com.krayc.repository.OrderRepository;
import com.krayc.service.OrderService;
import com.krayc.util.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventSeatRepository orderEventSeatRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void createOrder(OrderEntity orderEntity, List<OrderEventSeatEntity> eventSeatEntityList) {
        Date date = new Date(System.currentTimeMillis());
        orderEntity.setOrderTime(DateFormatter.getDateFormatter().stringFromDate(date));

        orderRepository.saveAndFlush(orderEntity);

        for (OrderEventSeatEntity orderEventSeatEntity : eventSeatEntityList) {
            orderEventSeatEntity.setOrderByOid(orderEntity);

            orderEventSeatEntity.setSeatNumber(findMinimumAvailableSeatNumberByEventSeat(orderEventSeatEntity.getEventSeatByEsid()));

            orderEventSeatRepository.save(orderEventSeatEntity);
        }
    }

    public void payOrder(OrderEntity orderEntity, String bankAccount) {
        Double orderPrice = calculateTotalPriceOfOrder(orderEntity);
        BankAccountEntity bankAccountEntity = bankAccountRepository.findByBankAccount(bankAccount);
        if (bankAccountEntity.getBalance() >= orderPrice) {
            bankAccountRepository.updateBalance(bankAccountEntity.getBalance() - orderPrice, bankAccountEntity.getBankAccount());
            orderRepository.updateStatus(Byte.valueOf("1"), orderEntity.getOid());
        }
    }

    public void cancelOrder(Integer oid) {
        orderRepository.updateStatus(Byte.valueOf("2"), oid);

        for (OrderEventSeatEntity orderEventSeatEntity : orderRepository.findOne(oid).getOrderEventSeats()) {
//            orderEventSeatRepository.delete(orderEventSeatEntity.getOesid());
            orderEventSeatRepository.updateStatus(0, orderEventSeatEntity.getOesid());
        }
    }

    public void refundOrder(OrderEntity orderEntity, String bankAccount) {
        Double orderPrice = calculateTotalPriceOfOrder(orderEntity);
        BankAccountEntity bankAccountEntity = bankAccountRepository.findByBankAccount(bankAccount);
        bankAccountRepository.updateBalance(bankAccountEntity.getBalance() + orderPrice, bankAccountEntity.getBankAccount());
        orderRepository.updateStatus(Byte.valueOf("3"), orderEntity.getOid());

        for (OrderEventSeatEntity orderEventSeatEntity : orderEntity.getOrderEventSeats()) {
//            orderEventSeatRepository.delete(orderEventSeatEntity.getOesid());
            orderEventSeatRepository.updateStatus(0, orderEventSeatEntity.getOesid());
        }
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
        if (orderEntity.getCouponByCid() != null) {
            totalAmount *= orderEntity.getCouponByCid().getDiscount();
        }
        return totalAmount;
    }

}
