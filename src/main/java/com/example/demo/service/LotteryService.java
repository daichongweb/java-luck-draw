package com.example.demo.service;

import com.example.demo.model.GiftModel;
import com.example.demo.model.JoinUserModel;

import java.util.List;
import java.util.Map;

public interface LotteryService {

    void setGift(GiftModel giftModel);

    long increment(String field, Integer roomId);

    void joinLottery(JoinUserModel joinUserModel);

    void award(Integer roomId);

    List<Map<String, Object>> getAwardUserList(Integer roomId);
}
