package com.krayc.service;

import com.krayc.model.AdminBookEntity;
import com.krayc.model.AdminEntity;
import com.krayc.model.EventEntity;
import com.krayc.util.LoginStatus;

import java.util.List;

public interface AdminService {

    public LoginStatus login(AdminEntity adminEntity);

    public void confirmEvent(EventEntity eventEntity);

    public List<AdminBookEntity> getAllAdminBooks();

}
