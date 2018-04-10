package com.android.p2pflowernet.project.entity;

/**
 * Created by Administrator on 2018/1/25.
 */

public class EvaluateGoodsBean {
    private int id;
    private int score;
    private String spec;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EvaluateGoodsBean{" +
                "id='" + id + '\'' +
                ", score='" + score + '\'' +
                ", spec='" + spec + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
