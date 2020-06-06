package com.example.demo.mapper;

import com.example.demo.model.AdminModel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {

    AdminModel queryAdminUserById(@Param("id") Integer id);

    void updateAdminUserById(@Param("id") Integer id, @Param("name") String name, @Param("password") String password, @Param("salt") String salt);

    void insert(@Param("name") String name, @Param("password") String password, @Param("salt") String salt);

    void delete(@Param("id") Integer id);

    List<AdminModel> selectPage();
}
