package com.example.demo.service.Impl;

import com.example.demo.data.LotteryData;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.GiftModel;
import com.example.demo.model.JoinUserModel;
import com.example.demo.service.LotteryService;
import com.example.demo.util.Helper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class LotteryServiceImpl implements LotteryService {

    @Resource
    LotteryData lotteryData;

    @Override
    public void setGift(GiftModel giftModel) {
        lotteryData.putAll(giftModel);
    }

    @Override
    public long increment(String field, Integer roomId) {
        return lotteryData.addLookUserTotal(field, roomId);
    }

    @Override
    public void joinLottery(JoinUserModel joinUserModel) {
        // 检测是否已经参加过
        boolean ifJoin = lotteryData.isMember(joinUserModel);
        if (ifJoin) {
            throw new BusinessException(400, "您已经参与过了");
        }
        lotteryData.leftPush(joinUserModel);
    }

    @Override
    public void award(Integer roomId) {
        // 查询抽奖状态
        String awardStatus = lotteryData.getAwardStatus(roomId).toString();
        if (awardStatus.equals("completed")) {
            throw new BusinessException(400, "抽奖已结束");
        }
        long listSize = lotteryData.getSize(roomId);
        long lotteryNum = lotteryData.getHashByKey(roomId, "getLotteryNum");
        if (listSize < lotteryNum) {
            throw new BusinessException(400, "参与人数不足，无法开奖");
        }
        Helper helper = new Helper();
        long[] rands = helper.getRandom(listSize, lotteryNum);
        for (long userId : rands) {
            String value = lotteryData.index(roomId, userId).toString();
            lotteryData.setAwardMember(roomId, value);
        }
        // 修改抽奖状态
        lotteryData.setAwardStatus(roomId, "completed");
    }

    @Override
    public List<Map<String, Object>> getAwardUserList(Integer roomId) {
        return lotteryData.getAwardMember(roomId);
    }
}
