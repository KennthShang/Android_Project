package com.example.administrator.project;

/**
 * Created by Administrator on 2016/11/27 0027.
 */


public class Event {
    private String name;        // 事件名称
    private int color;          // 事件颜色
    private String startTime;   // 事件开始时间
    private String comment;     // 备注，暂时不管

    Event(String name, int color, String startTime, String comment) {
        this.name = name;
        this.color = color;
        this.startTime = startTime;
        this.comment = comment;
    }

    Event() {

    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
