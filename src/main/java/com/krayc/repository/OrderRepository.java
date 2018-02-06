package com.krayc.repository;

import com.krayc.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update OrderEntity o set o.status=:qStatus where o.oid=:qOid")
    public void updateStatus(@Param("qStatus") Byte qStatus, @Param("qOid") int qOid);

}
