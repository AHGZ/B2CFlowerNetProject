package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heguozhong on 2018/1/29/029.
 * 外卖美食二级
 */

public class TakeCateTwoBean implements Serializable{

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 快餐小吃
         * id : 100101
         * level : 2
         * son : [{"name":"沙县小吃","id":"100201","level":"3"},{"name":"肉夹馍","id":"100202","level":"3"},{"name":"东北菜","id":"100204","level":"3"},{"name":"东北菜2","id":"100205","level":"3"},{"name":"东北菜3","id":"100206","level":"3"},{"name":"东北菜4","id":"100207","level":"3"},{"name":"东北菜","id":"100208","level":"3"},{"name":"东北菜","id":"100209","level":"3"},{"name":"东北菜","id":"100210","level":"3"}]
         */

        private String name;
        private int id;
        private String level;
        private List<SonBean> son;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<SonBean> getSon() {
            return son;
        }

        public void setSon(List<SonBean> son) {
            this.son = son;
        }

        public static class SonBean {
            /**
             * name : 沙县小吃
             * id : 100201
             * level : 3
             */

            private String name;
            private String id;
            private String level;
            private boolean ischoose;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public boolean ischoose() {
                return ischoose;
            }

            public void setIschoose(boolean ischoose) {
                this.ischoose = ischoose;
            }
        }
    }
}
