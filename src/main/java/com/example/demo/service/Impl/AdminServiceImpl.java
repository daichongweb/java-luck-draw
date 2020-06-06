package com.example.demo.service.Impl;

import com.example.demo.mapper.AdminMapper;
import com.example.demo.model.AdminModel;
import com.example.demo.service.AdminService;
import com.example.demo.util.Helper;
import com.example.demo.util.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.util.PageRequest;
import com.example.demo.util.PageResult;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public AdminModel queryAdminUserById(Integer id) {
        return adminMapper.queryAdminUserById(id);
    }

    @Override
    public void updateAdminUserById(AdminModel adminModel) throws NoSuchAlgorithmException {
        Helper helper = new Helper();
        String password = helper.md5(adminModel.getPassword());
        String salt = helper.getRandomString(8);
        adminMapper.updateAdminUserById(adminModel.getId(), adminModel.getName(), password, salt);
    }

    @Override
    public void insert(AdminModel adminModel) throws NoSuchAlgorithmException {
        Helper helper = new Helper();
        String password = helper.md5(adminModel.getPassword());
        String salt = helper.getRandomString(8);
        adminMapper.insert(adminModel.getName(), password, salt);
    }

    @Override
    public void delete(Integer id) {
        adminMapper.delete(id);
    }

    @Override
    public PageResult selectPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    private PageInfo<AdminModel> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<AdminModel> adminModels = adminMapper.selectPage();
        return new PageInfo(adminModels);
    }
}
