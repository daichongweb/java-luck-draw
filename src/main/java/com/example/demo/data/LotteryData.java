package com.example.demo.data;

import com.alibaba.fastjson.JSON;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.GiftModel;
import com.example.demo.model.JoinUserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class LotteryData {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    final String giftKey = "room_lottery:";

    /**
     * @param joinUserModel
     * @return
     */
    public long leftPush(JoinUserModel joinUserModel) {
        String key = this.getKey(joinUserModel.getRoomId()) + ":joinUserList";
        long successNum = stringRedisTemplate.opsForList().leftPush(key, JSON.toJSONString(joinUserModel));
        // 加入抽奖记录
        this.put(joinUserModel);
        // 增加参与数量
        this.addLookUserTotal("lotteryJoinTotal", joinUserModel.getRoomId());
        return successNum;
    }

    /**
     * @param joinUserModel
     * @return
     */
    public long put(JoinUserModel joinUserModel) {
        String key = this.getKey(joinUserModel.getRoomId()) + ":joinUserMembers";
        long add = stringRedisTemplate.opsForSet().add(key, joinUserModel.getUserId().toString());
        return add;
    }

    /**
     * @param joinUserModel
     * @return
     */
    public boolean isMember(JoinUserModel joinUserModel) {
        String key = this.getKey(joinUserModel.getRoomId()) + ":joinUserMembers";
        boolean isMember = stringRedisTemplate.opsForSet().isMember(key, joinUserModel.getUserId().toString());
        return isMember;
    }

    /**
     * @param giftModel
     */
    public void putAll(GiftModel giftModel) {
        String key = this.getKey(giftModel.getRoomId());
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("anchorId", giftModel.getAnchorId().toString());
        valueMap.put("roomId", giftModel.getRoomId().toString());
        valueMap.put("roomName", giftModel.getRoomName());
        valueMap.put("nickName", giftModel.getNickName());
        valueMap.put("lookUserTotal", giftModel.getLookUserTotal().toString());
        valueMap.put("lotteryJoinTotal", giftModel.getLotteryJoinTotal().toString());
        valueMap.put("lotteryTitle", giftModel.getLotteryTitle());
        valueMap.put("lotteryGift", giftModel.getLotteryGift());
        valueMap.put("getLotteryNum", giftModel.getGetLotteryNum().toString());
        stringRedisTemplate.opsForHash().putAll(key, valueMap);
    }

    /**
     * @param roomId
     * @param hashKey
     * @return
     */
    public long getHashByKey(Integer roomId, String hashKey) {
        String key = this.getKey(roomId);
        Object value = stringRedisTemplate.opsForHash().get(key, hashKey);
        return Long.parseLong(value.toString());
    }

    /**
     * @param roomId
     * @return
     */
    public long getSize(Integer roomId) {
        String key = this.getKey(roomId) + ":joinUserList";
        Long aLong = stringRedisTemplate.opsForList().size(key);
        return aLong;
    }

    /**
     * @param roomId
     * @return
     */
    private String getKey(Integer roomId) {
        String roomIds = Integer.toString(roomId);
        return this.giftKey + roomIds;
    }

    /**
     * @param field
     * @param roomId
     * @return
     */
    public long addLookUserTotal(String field, Integer roomId) {
        String key = this.getKey(roomId);
        Long increment = stringRedisTemplate.opsForHash().increment(key, field, 1);
        return increment;
    }

    /**
     * 获取集合指定位置的值。
     * <p>
     * index(K key, long index)
     */
    public Object index(Integer roomId, long index) {
        String key = this.getKey(roomId) + ":joinUserList";
        Object listValue = stringRedisTemplate.opsForList().index(key, index);
        return listValue;
    }

    /**
     * 存储中奖的用户
     *
     * @param roomId
     * @param awardUser
     */
    public void setAwardMember(Integer roomId, String awardUser) {
        String key = this.getKey(roomId) + ":awardMember";
        Long add = stringRedisTemplate.opsForSet().add(key, awardUser);
        if (add == null) {
            throw new BusinessException(400, "抽奖失败");
        }
    }

    /**
     * 获取中奖用户
     *
     * @param roomId
     * @return
     */
    public List<Map<String, Object>> getAwardMember(Integer roomId) {
        List<Map<String, Object>> list = new ArrayList<>();
        String key = this.getKey(roomId) + ":awardMember";
        Set<String> set = stringRedisTemplate.opsForSet().members(key);
        for (String str : set) {
            list.add(JSON.parseObject(str));
        }
        return list;
    }

    /**
     * 修改抽奖状态
     *
     * @param roomId
     * @param status
     */
    public void setAwardStatus(Integer roomId, String status) {
        if (!StringUtils.isNotBlank(status)) {
            status = "completed";
        }
        String key = this.getKey(roomId);
        stringRedisTemplate.opsForHash().put(key, "awardStatus", status);
    }

    /**
     * 获取抽奖状态
     *
     * @param roomId
     * @return
     */
    public Object getAwardStatus(Integer roomId) {
        String key = this.getKey(roomId);
        Object value = stringRedisTemplate.opsForHash().get(key, "awardStatus");
        return value;
    }
}
