package com.krayc.service;

import com.krayc.model.AdminEntity;
import com.krayc.util.LoginStatus;

public interface AdminService {

    public LoginStatus login(AdminEntity adminEntity);

}
