package com.example.demo.controller;

import com.example.demo.model.GiftModel;
import com.example.demo.model.JoinUserModel;
import com.example.demo.service.LotteryService;
import com.example.demo.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class LotteryController {

    @Autowired
    LotteryService lotteryService;

    @PostMapping("/setGift")
    @ResponseBody
    public JsonResult setGift(@Valid GiftModel giftModel) {
        lotteryService.setGift(giftModel);
        return new JsonResult<>("200", "设置成功");
    }

    @PostMapping("/lookUserAdd")
    public JsonResult addUserNum(Integer roomId) {
        lotteryService.increment("lookUserTotal", roomId);
        return new JsonResult<>("200", "增加成功");
    }

    @PostMapping("/joinUserAdd")
    public JsonResult addJoinUserNum(Integer roomId) {
        lotteryService.increment("lotteryJoinTotal", roomId);
        return new JsonResult<>("200", "增加成功");
    }

    @PostMapping("/joinLottery")
    @ResponseBody
    public JsonResult joinLottery(@Valid JoinUserModel joinUserModel) {
        lotteryService.joinLottery(joinUserModel);
        return new JsonResult<>("200", "参与成功");
    }

    @PostMapping("/award")
    public JsonResult award(Integer roomId) {
        lotteryService.award(roomId);
        return new JsonResult<>();
    }

    @PostMapping("/getAwardUser")
    public JsonResult getAwardList(Integer roomId) {
        List<Map<String, Object>> userList = lotteryService.getAwardUserList(roomId);
        return new JsonResult<>(userList);
    }
}
