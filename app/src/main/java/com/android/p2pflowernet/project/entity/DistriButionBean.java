package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2018/1/22.
 * by--首页数据
 */

public class DistriButionBean implements Serializable {


    private List<DistriButionsBean> list;

    public List<DistriButionsBean> getList() {
        return list;
    }

    public void setList(List<DistriButionsBean> list) {
        this.list = list;
    }

    public static class DistriButionsBean {

        private String name;
        private String id;
        private List<DistriBean> Distri;

        public List<DistriBean> getDistri() {
            return Distri;
        }

        public void setDistri(List<DistriBean> distri) {
            Distri = distri;
        }

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

        public static class DistriBean {

            private String name;
            private String id;
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

            public boolean ischoose() {
                return ischoose;
            }

            public void setIschoose(boolean ischoose) {
                this.ischoose = ischoose;
            }
        }
    }
}
