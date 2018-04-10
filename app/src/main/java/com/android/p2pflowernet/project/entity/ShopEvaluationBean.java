package com.android.p2pflowernet.project.entity;

import java.util.List;

/**
 * Created by heguozhong on 2018/1/13/013.
 * 商家店铺评价
 */

public class ShopEvaluationBean {

    private List<ListsBean> lists;

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * username : hf15145186228757
         * file_path : null
         * score : 5
         * imgs : 279,278
         * content : 阿打发斯蒂芬
         * is_anon : 0
         * created : 2018-01-03 09:40:31
         * imgs_list : ["img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg","img/o2o_good/2018/01-03/d74038ae515a50e5a3a88d179f9041da.jpg"]
         */

        private String username;
        private Object file_path;
        private String score;
        private String imgs;
        private String content;
        private String is_anon;
        private String created;
        private List<String> imgs_list;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Object getFile_path() {
            return file_path;
        }

        public void setFile_path(Object file_path) {
            this.file_path = file_path;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public List<String> getImgs_list() {
            return imgs_list;
        }

        public void setImgs_list(List<String> imgs_list) {
            this.imgs_list = imgs_list;
        }
    }
}
