package com.example.demo.controller;

import com.example.demo.service.DrawService;
import com.example.demo.util.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController

public class RedPaperController {

    @Resource
    DrawService drawService;

    @PostMapping("/repaper/draw")
    public JsonResult draw(long roomId) {
        drawService.getPrice(roomId);
        return new JsonResult<>("200", "设置成功");
    }
}
