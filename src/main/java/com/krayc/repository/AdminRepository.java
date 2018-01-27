package com.krayc.repository;

import com.krayc.model.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {

    public AdminEntity findByUsername(String username);

}
