package com.example.demo.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GiftModel {

    @NotNull(message = "主播ID不能为空")
    private Integer anchorId; // 主播id
    @NotEmpty(message = "主播昵称不能为空")
    private String nickName; // 主播昵称
    @NotNull(message = "房间ID不能为空")
    private Integer roomId; // 房间号
    @NotEmpty(message = "房间名称不能为空")
    private String roomName; // 房间名
    private Integer lookUserTotal; // 观看人数
    private Integer lotteryJoinTotal; // 抽奖参数人数
    @NotEmpty(message = "抽奖标题不能为空")
    private String lotteryTitle; // 抽奖标题
    @NotEmpty(message = "抽奖礼物未设置")
    private String lotteryGift; // 抽奖礼品
    @NotNull(message = "请设置中奖人数")
    private Integer getLotteryNum;
    // 抽奖状态  completed已完成  incomplete未完成
    private String awardStatus;

    public Integer getGetLotteryNum() {
        return getLotteryNum;
    }

    public void setGetLotteryNum(Integer getLotteryNum) {
        this.getLotteryNum = getLotteryNum;
    }

    public String getLotteryGift() {
        return lotteryGift;
    }

    public void setLotteryGift(String lotteryGift) {
        this.lotteryGift = lotteryGift;
    }

    public String getLotteryTitle() {
        return lotteryTitle;
    }

    public void setLotteryTitle(String lotteryTitle) {
        this.lotteryTitle = lotteryTitle;
    }

    public Integer getLotteryJoinTotal() {
        if (lotteryJoinTotal == null) {
            return 0;
        }
        return lotteryJoinTotal;
    }

    public void setLotteryJoinTotal(Integer lotteryJoinTotal) {
        this.lotteryJoinTotal = lotteryJoinTotal;
    }

    public Integer getLookUserTotal() {
        if (lookUserTotal == null) {
            return 0;
        }
        return lookUserTotal;
    }

    public void setLookUserTotal(Integer lookUserTotal) {
        this.lookUserTotal = lookUserTotal;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(Integer anchorId) {
        this.anchorId = anchorId;
    }

    public String getAwardStatus() {
        if (awardStatus == null) {
            return "incomplete";
        }
        return awardStatus;
    }

    public void setAwardStatus(String awardStatus) {
        this.awardStatus = awardStatus;
    }

    @Override
    public String toString() {
        return "GiftModel{" +
                "anchorId=" + anchorId +
                ", nickName='" + nickName + '\'' +
                ", roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", lookUserTotal=" + lookUserTotal +
                ", lotteryJoinTotal=" + lotteryJoinTotal +
                ", lotteryTitle='" + lotteryTitle + '\'' +
                ", lotteryGift='" + lotteryGift + '\'' +
                '}';
    }
}
