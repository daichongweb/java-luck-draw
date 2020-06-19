package com.example.demo.service.Impl;

import com.example.demo.data.RedPaperData;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.DrawService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class DrawServiceImpl implements DrawService {

    @Resource
    RedPaperData redPaperData;

    @Override
    public float getPrice(long roomId) {
        // 红包信息
        Map<Object, Object> redInfo = redPaperData.getRedInfo(roomId);
        if (redInfo.isEmpty()) {
            //['type' => 3, 'drawPrice' => 0]
            throw new BusinessException(203, "手慢了，红包派完了");
        }
        String status = redInfo.get("status").toString();
        if (redInfo.isEmpty() || status.equals("2")) {
            //['type' =>3, 'drawPrice' =>0]
            throw new BusinessException(203, "手慢了，红包派完了");
        }
        if (redInfo.get("num").equals(redInfo.get("joinUserNum")) || redInfo.get("price").equals("0")) {
            throw new BusinessException(203, "手慢了，红包派完了");
        }
        double price = redPaperData.getPrice(roomId);
        if (price > 0) {
            //['drawPrice' =>0]
            throw new BusinessException(200, "操作成功");
        }
        String drawPrice = redPaperData.findDraw(roomId);
        if (StringUtils.isNotBlank(drawPrice)) {
            //['type' =>3, 'drawPrice' =>0]
            throw new BusinessException(203, "手慢了，红包派完了");
        }

        String userId = "12232";
        // 加入抽奖列表
        Boolean addStatus = redPaperData.addJoinUserList(roomId, userId, Double.valueOf(drawPrice));
        // 增加参与人数
        long userNum = redPaperData.addUserNum(roomId);
        // 设置参与用户信息
        redPaperData.setDrawUserData(roomId, userId);
        // 创建日志account_log表

        return 0;
    }
}
