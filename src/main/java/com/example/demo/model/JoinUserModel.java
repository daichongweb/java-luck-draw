package com.example.demo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class JoinUserModel {

    @Min(value = 1)
    @NotNull(message = "房间号不能为空")
    private Integer roomId;
    @Min(value = 1)
    @NotNull(message = "用户ID不能为空")
    private Integer userId;
    @NotEmpty(message = "用户昵称不能为空")
    private String nickname;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "JoinUserModel{" +
                "roomId=" + roomId +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
