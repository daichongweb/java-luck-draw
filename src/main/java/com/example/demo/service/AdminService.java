package com.example.demo.service;

import com.example.demo.model.AdminModel;
import com.example.demo.util.PageRequest;
import com.example.demo.util.PageResult;

import java.security.NoSuchAlgorithmException;

public interface AdminService {

    AdminModel queryAdminUserById(Integer id);

    void updateAdminUserById(AdminModel adminModel) throws NoSuchAlgorithmException;

    void insert(AdminModel adminModel) throws NoSuchAlgorithmException;

    void delete(Integer id);

    PageResult selectPage(PageRequest pageRequest);
}
