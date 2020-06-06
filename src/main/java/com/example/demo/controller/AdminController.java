package com.example.demo.controller;

import com.example.demo.exception.BusinessException;
import com.example.demo.model.AdminModel;
import com.example.demo.service.AdminService;
import com.example.demo.util.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.util.PageRequest;

import java.security.NoSuchAlgorithmException;

import com.example.demo.util.PageResult;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/getAdmin")
    public JsonResult<AdminModel> index(Integer id) {
        if (id == null) {
            throw new BusinessException(400, "用户不存在");
        }
        AdminModel adminModel = adminService.queryAdminUserById(id);
        return new JsonResult<>(adminModel);
    }

    @PostMapping("/saveAdmin")
    public JsonResult update(AdminModel adminModel) throws NoSuchAlgorithmException {
        if (adminModel.getId() == null) {
            throw new BusinessException(400, "用户不存在");
        }
        if (!StringUtils.isNotBlank(adminModel.getName())) {
            throw new BusinessException(400, "姓名不能为空");
        }
        if (!StringUtils.isNotBlank(adminModel.getPassword())) {
            throw new BusinessException(400, "密码不能为空");
        }
        adminService.updateAdminUserById(adminModel);
        return new JsonResult<>("200", "修改成功");
    }

    @PostMapping("/createAdmin")
    public JsonResult insert(AdminModel adminModel) throws NoSuchAlgorithmException {
        if (!StringUtils.isNotBlank(adminModel.getName())) {
            throw new BusinessException(400, "姓名不能为空");
        }
        if (!StringUtils.isNotBlank(adminModel.getPassword())) {
            throw new BusinessException(400, "密码不能为空");
        }
        adminService.insert(adminModel);
        return new JsonResult<>("200", "创建成功");
    }

    @PostMapping("/deleteAdmin")
    public JsonResult delete(Integer id) {
        if (id == null) {
            throw new BusinessException(400, "用户不存在");
        }
        adminService.delete(id);
        return new JsonResult<>("200", "删除成功");
    }

    @PostMapping("/getAdminAll")
    public JsonResult selectPage(@RequestBody PageRequest pageQuery) {
        PageResult adminModelList = adminService.selectPage(pageQuery);
        return new JsonResult<>(adminModelList);
    }
}
