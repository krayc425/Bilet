package com.krayc.repository;

import com.krayc.model.OrderEntity;
import com.krayc.model.OrderEventSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderEventSeatRepository extends JpaRepository<OrderEventSeatEntity, Integer> {

    public OrderEventSeatEntity findBySeatNumberAndIsValid(Integer seatNumber, Integer isValid);

    @Modifying
    @Transactional
    @Query("update OrderEventSeatEntity o set o.isValid=:qValid where o.oesid=:qOesid")
    public void updateStatus(@Param("qValid") Integer qValid, @Param("qOesid") int qOesid);

    public List<OrderEventSeatEntity> findByOrder(OrderEntity orderEntity);

}
