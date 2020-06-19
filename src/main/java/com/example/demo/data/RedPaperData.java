package com.example.demo.data;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class RedPaperData {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 红包主表
     */
    final String redMapKey = "redPaper:redItem:";

    /**
     * 参与用户
     */
    final String redJoinUserZsetKey = "redPaper:userList:";

    /**
     * 抽奖列表
     */
    final String redDrawListKey = "redPaper:draw:";

    final String redUserDataKey = "redPaper:room:";

    public Map<Object, Object> getRedInfo(long roomId) {
        String key = redMapKey + roomId;
        return stringRedisTemplate.opsForHash().entries(key);
    }

    public Double getPrice(long roomId) {
        String key = redJoinUserZsetKey + roomId;
        return stringRedisTemplate.opsForZSet().score(key, roomId);
    }

    public String findDraw(long roomId) {
        String key = redDrawListKey + roomId;
        return stringRedisTemplate.opsForList().leftPop(key);
    }

    public Boolean addJoinUserList(long roomId, String userId, Double price) {
        String key = redJoinUserZsetKey + roomId;
        // 获取总金额
        Object totalPrice = getTotalPrice(roomId);
        double totalPriceD = Double.parseDouble(totalPrice.toString());
        double cha = totalPriceD - price;
        // 总金额减去剩余的金额
        setTotalPrice(roomId, cha);
        return stringRedisTemplate.opsForZSet().add(key, userId, price);
    }

    public long addUserNum(long roomId) {
        String key = redMapKey + roomId;
        return stringRedisTemplate.opsForHash().increment(key, "joinUserNum", 1);
    }

    public Object getTotalPrice(long roomId) {
        String key = redMapKey + roomId;
        return stringRedisTemplate.opsForHash().get(key, "price");
    }

    public void setTotalPrice(long roomId, double price) {
        String key = redMapKey + roomId;
        stringRedisTemplate.opsForHash().put(key, "price", price);
    }

    public void setDrawUserData(long roomId, String userId) {
        String key = redUserDataKey + roomId + ":user:" + userId;
        // 获取用户信息
        Map<String, Object> userData = new HashMap<>();
        userData.put("user_id", "1111");
        userData.put("nick_name", "狗叼");
        userData.put("headimg", "img.pnh");
        JSONObject json = new JSONObject(userData);
        stringRedisTemplate.opsForValue().set(key, json.toJSONString());
    }
}
