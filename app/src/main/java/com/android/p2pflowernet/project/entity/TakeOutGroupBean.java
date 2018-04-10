package com.android.p2pflowernet.project.entity;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by caishen on 2018/1/6.
 * by--
 */

public class TakeOutGroupBean implements Serializable {

    private String title;
    private byte[] bytes;
    private String filepath;
    private String price;
    private String huafan;
    private String num;
    private String ordernum;
    private String date;
    private String number;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHuafan() {
        return huafan;
    }

    public void setHuafan(String huafan) {
        this.huafan = huafan;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TakeOutGroupBean{" +
                "title='" + title + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                ", filepath='" + filepath + '\'' +
                ", price='" + price + '\'' +
                ", huafan='" + huafan + '\'' +
                ", num='" + num + '\'' +
                ", ordernum='" + ordernum + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
