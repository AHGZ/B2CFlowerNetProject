package com.android.p2pflowernet.project.entity;

import java.util.List;

/**
 * Created by heguozhong on 2018/1/13/013.
 * 团购评价详情
 */

public class GroupEvaluationBean {

    private List<EvalListBean> eval_list;

    public List<EvalListBean> getEval_list() {
        return eval_list;
    }

    public void setEval_list(List<EvalListBean> eval_list) {
        this.eval_list = eval_list;
    }

    public static class EvalListBean {
        /**
         * score : 5
         * content : 去你妹的
         * reply :
         * is_anon : 1
         * created : 2018-01-18 17:20:49
         * username : 匿名用户
         * header_img :
         * img_list : []
         */

        private String score;
        private String content;
        private String reply;
        private String is_anon;
        private String created;
        private String username;
        private String header_img;
        private List<String> img_list;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public String getIs_anon() {
            return is_anon;
        }

        public void setIs_anon(String is_anon) {
            this.is_anon = is_anon;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHeader_img() {
            return header_img;
        }

        public void setHeader_img(String header_img) {
            this.header_img = header_img;
        }

        public List<String> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<String> img_list) {
            this.img_list = img_list;
        }
    }
}
