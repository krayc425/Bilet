package com.krayc.service.impl;

import com.krayc.model.AdminEntity;
import com.krayc.repository.AdminRepository;
import com.krayc.service.AdminService;
import com.krayc.util.LoginStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public LoginStatus login(AdminEntity adminEntity) {
        AdminEntity anotherEntity = adminRepository.findByUsername(adminEntity.getUsername());
        if (anotherEntity == null) {
            return LoginStatus.LOGIN_WRONG_EMAIL_PASSWORD;
        }
        if (anotherEntity.getPassword().equals(adminEntity.getPassword())) {
            return LoginStatus.LOGIN_SUCCESS;
        } else {
            return LoginStatus.LOGIN_WRONG_EMAIL_PASSWORD;
        }
    }

}