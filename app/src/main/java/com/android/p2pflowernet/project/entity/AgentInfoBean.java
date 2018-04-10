package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/4.
 * by--代理人信息
 */

public class AgentInfoBean implements Serializable{


    private List<AlBean> al;

    public List<AlBean> getAl() {
        return al;
    }

    public void setAl(List<AlBean> al) {
        this.al = al;
    }

    public static class AlBean {
        /**
         * id : 2
         * user_id : 9
         * region : 110114
         * realname : 张怡
         * sex : 1
         * id_number : 130503198805160071
         * agent_deposit_amount : 0.00
         * agent_qual_num : 0
         * agent_qual_amount : 0.00
         * violation_times : 0
         * state : 9
         * info_state : 2
         * admin_id : 0
         * updated : 1512364862
         * created : 1512364705
         * imgs : [{"img_id":"61","img_type":"1","file_path":"business_auth_img/20171127/063635fb231522af528fe721bafa8ddc.jpg"},{"img_id":"62","img_type":"2","file_path":"business_auth_img/20171127/f7ea567144dd6580b902e83caaf377ab.jpg"},{"img_id":"63","img_type":"3","file_path":"business_auth_img/20171127/578392ef0ad16c824b2c37998c6fbaa8.jpg"},{"img_id":"64","img_type":"4","file_path":"business_auth_img/20171127/f9b049322761f901bf36155fdcf9e0a3.jpg"},{"img_id":"65","img_type":"5","file_path":"business_auth_img/20171127/fb68ed92bc96874d578ff5542f7843c0.jpg"},{"img_id":"137","img_type":"1","file_path":"business_auth_img/20171204/38350c04704151a71572399d29ec54ac.jpg"},{"img_id":"138","img_type":"2","file_path":"business_auth_img/20171204/24f1ad8a3b152b0cd0947155b07f2887.jpg"},{"img_id":"139","img_type":"3","file_path":"business_auth_img/20171204/20f6d1cbca43fb85e51c47220cf756dc.jpg"},{"img_id":"140","img_type":"4","file_path":"business_auth_img/20171204/a38b87c0be677f4ea9316b94965e5525.jpg"},{"img_id":"141","img_type":"5","file_path":"business_auth_img/20171204/2453e4ecb710a108c41ac60976191474.jpg"},{"img_id":"159","img_type":"1","file_path":"business_auth_img/20171204/f2c732ba57de8eb6812cdb266e3f7397.png"},{"img_id":"160","img_type":"2","file_path":"business_auth_img/20171204/935e5425a30d9f166d3b16435a6f0001.png"},{"img_id":"161","img_type":"3","file_path":"business_auth_img/20171204/58ddf86ffbbd9ff082aa131f8671ae32.png"},{"img_id":"162","img_type":"4","file_path":"business_auth_img/20171204/36b98c08018138ab912a85e35f6bdb3c.png"},{"img_id":"163","img_type":"5","file_path":"business_auth_img/20171204/58ddf86ffbbd9ff082aa131f8671ae32.png"}]
         */

        private String id;
        private String user_id;
        private String region;
        private String realname;
        private String sex;
        private String id_number;
        private String agent_deposit_amount;
        private String agent_qual_num;
        private String agent_qual_amount;
        private String violation_times;
        private String state;
        private String info_state;
        private String admin_id;
        private String updated;
        private String created;
        private List<ImgsBean> imgs;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getId_number() {
            return id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }

        public String getAgent_deposit_amount() {
            return agent_deposit_amount;
        }

        public void setAgent_deposit_amount(String agent_deposit_amount) {
            this.agent_deposit_amount = agent_deposit_amount;
        }

        public String getAgent_qual_num() {
            return agent_qual_num;
        }

        public void setAgent_qual_num(String agent_qual_num) {
            this.agent_qual_num = agent_qual_num;
        }

        public String getAgent_qual_amount() {
            return agent_qual_amount;
        }

        public void setAgent_qual_amount(String agent_qual_amount) {
            this.agent_qual_amount = agent_qual_amount;
        }

        public String getViolation_times() {
            return violation_times;
        }

        public void setViolation_times(String violation_times) {
            this.violation_times = violation_times;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getInfo_state() {
            return info_state;
        }

        public void setInfo_state(String info_state) {
            this.info_state = info_state;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public static class ImgsBean {
            /**
             * img_id : 61
             * img_type : 1
             * file_path : business_auth_img/20171127/063635fb231522af528fe721bafa8ddc.jpg
             */

            private String img_id;
            private String img_type;
            private String file_path;

            public String getImg_id() {
                return img_id;
            }

            public void setImg_id(String img_id) {
                this.img_id = img_id;
            }

            public String getImg_type() {
                return img_type;
            }

            public void setImg_type(String img_type) {
                this.img_type = img_type;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }
        }
    }
}
