package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2018/1/20.
 * by--店铺类别
 */

public class MerchTypeBean implements Serializable{


    /**
     * cate : [{"id":"100001","name":"美食","level":"1","parent_id":"100000","child":[{"id":"100101","name":"快餐小吃","level":"2","parent_id":"100001","child":[{"id":"100201","name":"沙县小吃","level":"3","parent_id":"100101"},{"id":"100202","name":"肉夹馍","level":"3","parent_id":"100101"}]},{"id":"100102","name":"特色菜","level":"2","parent_id":"100001"},{"id":"100103","name":"地方菜","level":"2","parent_id":"100001"}]},{"id":"100002","name":"鲜花","level":"1","parent_id":"100000"}]
     * mts : 1516434664
     */

    private int mts;
    private List<CateBean> cate;

    public int getMts() {
        return mts;
    }

    public void setMts(int mts) {
        this.mts = mts;
    }

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public static class CateBean {
        /**
         * id : 100001
         * name : 美食
         * level : 1
         * parent_id : 100000
         * child : [{"id":"100101","name":"快餐小吃","level":"2","parent_id":"100001","child":[{"id":"100201","name":"沙县小吃","level":"3","parent_id":"100101"},{"id":"100202","name":"肉夹馍","level":"3","parent_id":"100101"}]},{"id":"100102","name":"特色菜","level":"2","parent_id":"100001"},{"id":"100103","name":"地方菜","level":"2","parent_id":"100001"}]
         */

        private int id;
        private String name;
        private String level;
        private String parent_id;
        private List<ChildBeanX> child;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public List<ChildBeanX> getChild() {
            return child;
        }

        public void setChild(List<ChildBeanX> child) {
            this.child = child;
        }

        public static class ChildBeanX {
            /**
             * id : 100101
             * name : 快餐小吃
             * level : 2
             * parent_id : 100001
             * child : [{"id":"100201","name":"沙县小吃","level":"3","parent_id":"100101"},{"id":"100202","name":"肉夹馍","level":"3","parent_id":"100101"}]
             */

            private int id;
            private String name;
            private String level;
            private String parent_id;
            private List<ChildBean> child;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean {
                /**
                 * id : 100201
                 * name : 沙县小吃
                 * level : 3
                 * parent_id : 100101
                 */

                private int id;
                private String name;
                private String level;
                private String parent_id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
                }

                public String getParent_id() {
                    return parent_id;
                }

                public void setParent_id(String parent_id) {
                    this.parent_id = parent_id;
                }
            }
        }
    }
}
