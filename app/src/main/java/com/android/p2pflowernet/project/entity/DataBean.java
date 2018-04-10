package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/11/14 上午10:49
 * description:
 */
public class DataBean implements Serializable {


    /**
     * region : [{"id":"110000","region_name":"北京","parent_id":"100000","region_type":"1","child":[{"id":"110101","region_name":"东城区","parent_id":"110000","region_type":"2"},{"id":"110102","region_name":"西城区","parent_id":"110000","region_type":"2"},{"id":"110105","region_name":"朝阳区","parent_id":"110000","region_type":"2"},{"id":"110106","region_name":"丰台区","parent_id":"110000","region_type":"2"},{"id":"110107","region_name":"石景山区","parent_id":"110000","region_type":"2"},{"id":"110108","region_name":"海淀区","parent_id":"110000","region_type":"2"},{"id":"110109","region_name":"门头沟区","parent_id":"110000","region_type":"2"},{"id":"110111","region_name":"房山区","parent_id":"110000","region_type":"2"},{"id":"110112","region_name":"通州区","parent_id":"110000","region_type":"2"},{"id":"110113","region_name":"顺义区","parent_id":"110000","region_type":"2"},{"id":"110114","region_name":"昌平区","parent_id":"110000","region_type":"2"},{"id":"110115","region_name":"大兴区","parent_id":"110000","region_type":"2"},{"id":"110116","region_name":"怀柔区","parent_id":"110000","region_type":"2"},{"id":"110117","region_name":"平谷区","parent_id":"110000","region_type":"2"}]},{"id":"130000","region_name":"河北省","parent_id":"100000","region_type":"1","child":[{"id":"130100","region_name":"石家庄市","parent_id":"130000","region_type":"2"},{"id":"130181","region_name":"辛集市","parent_id":"130000","region_type":"2"},{"id":"130183","region_name":"晋州市","parent_id":"130000","region_type":"2"},{"id":"130184","region_name":"新乐市","parent_id":"130000","region_type":"2"},{"id":"130200","region_name":"唐山市","parent_id":"130000","region_type":"2"},{"id":"130281","region_name":"遵化市","parent_id":"130000","region_type":"2"},{"id":"130283","region_name":"迁安市","parent_id":"130000","region_type":"2"},{"id":"130300","region_name":"秦皇岛市","parent_id":"130000","region_type":"2"},{"id":"130400","region_name":"邯郸市","parent_id":"130000","region_type":"2"},{"id":"130481","region_name":"武安市","parent_id":"130000","region_type":"2"},{"id":"130500","region_name":"邢台市","parent_id":"130000","region_type":"2"},{"id":"130581","region_name":"南宫市","parent_id":"130000","region_type":"2"},{"id":"130582","region_name":"沙河市","parent_id":"130000","region_type":"2"},{"id":"130600","region_name":"保定市","parent_id":"130000","region_type":"2"},{"id":"130681","region_name":"涿州市","parent_id":"130000","region_type":"2"},{"id":"130682","region_name":"定州市","parent_id":"130000","region_type":"2"},{"id":"130683","region_name":"安国市","parent_id":"130000","region_type":"2"},{"id":"130684","region_name":"高碑店市","parent_id":"130000","region_type":"2"},{"id":"130700","region_name":"张家口市","parent_id":"130000","region_type":"2"},{"id":"130800","region_name":"承德市","parent_id":"130000","region_type":"2"},{"id":"130900","region_name":"沧州市","parent_id":"130000","region_type":"2"},{"id":"130981","region_name":"泊头市","parent_id":"130000","region_type":"2"},{"id":"130982","region_name":"任丘市","parent_id":"130000","region_type":"2"},{"id":"130983","region_name":"黄骅市","parent_id":"130000","region_type":"2"},{"id":"130984","region_name":"河间市","parent_id":"130000","region_type":"2"},{"id":"131000","region_name":"廊坊市","parent_id":"130000","region_type":"2"},{"id":"131081","region_name":"霸州市","parent_id":"130000","region_type":"2"},{"id":"131082","region_name":"三河市","parent_id":"130000","region_type":"2"},{"id":"131100","region_name":"衡水市","parent_id":"130000","region_type":"2"},{"id":"131181","region_name":"冀州市","parent_id":"130000","region_type":"2"},{"id":"131182","region_name":"深州市","parent_id":"130000","region_type":"2"}]},{"id":"140000","region_name":"山西省","parent_id":"100000","region_type":"1","child":[{"id":"140100","region_name":"太原市","parent_id":"140000","region_type":"2"},{"id":"140181","region_name":"古交市","parent_id":"140000","region_type":"2"},{"id":"140200","region_name":"大同市","parent_id":"140000","region_type":"2"},{"id":"140300","region_name":"阳泉市","parent_id":"140000","region_type":"2"},{"id":"140400","region_name":"长治市","parent_id":"140000","region_type":"2"},{"id":"140481","region_name":"潞城市","parent_id":"140000","region_type":"2"},{"id":"140500","region_name":"晋城市","parent_id":"140000","region_type":"2"},{"id":"140581","region_name":"高平市","parent_id":"140000","region_type":"2"},{"id":"140600","region_name":"朔州市","parent_id":"140000","region_type":"2"},{"id":"140700","region_name":"晋中市","parent_id":"140000","region_type":"2"},{"id":"140781","region_name":"介休市","parent_id":"140000","region_type":"2"},{"id":"140800","region_name":"运城市","parent_id":"140000","region_type":"2"},{"id":"140881","region_name":"永济市","parent_id":"140000","region_type":"2"},{"id":"140882","region_name":"河津市","parent_id":"140000","region_type":"2"},{"id":"140900","region_name":"忻州市","parent_id":"140000","region_type":"2"},{"id":"140981","region_name":"原平市","parent_id":"140000","region_type":"2"},{"id":"141000","region_name":"临汾市","parent_id":"140000","region_type":"2"},{"id":"141081","region_name":"侯马市","parent_id":"140000","region_type":"2"},{"id":"141082","region_name":"霍州市","parent_id":"140000","region_type":"2"},{"id":"141100","region_name":"吕梁市","parent_id":"140000","region_type":"2"},{"id":"141181","region_name":"孝义市","parent_id":"140000","region_type":"2"},{"id":"141182","region_name":"汾阳市","parent_id":"140000","region_type":"2"}]},{"id":"150000","region_name":"内蒙古自治区","parent_id":"100000","region_type":"1","child":[{"id":"150203","region_name":"昆都仑区","parent_id":"150000","region_type":"2"}]},{"id":"440000","region_name":"广东省","parent_id":"100000","region_type":"1"}]
     * rts : 1512637930
     */

    private String rts;
    private List<RegionBean> region;

    public String getRts() {
        return rts;
    }

    public void setRts(String rts) {
        this.rts = rts;
    }

    public List<RegionBean> getRegion() {
        return region;
    }

    public void setRegion(List<RegionBean> region) {
        this.region = region;
    }

    public static class RegionBean {
        /**
         * id : 110000
         * region_name : 北京
         * parent_id : 100000
         * region_type : 1
         * child : [{"id":"110101","region_name":"东城区","parent_id":"110000","region_type":"2"},{"id":"110102","region_name":"西城区","parent_id":"110000","region_type":"2"},{"id":"110105","region_name":"朝阳区","parent_id":"110000","region_type":"2"},{"id":"110106","region_name":"丰台区","parent_id":"110000","region_type":"2"},{"id":"110107","region_name":"石景山区","parent_id":"110000","region_type":"2"},{"id":"110108","region_name":"海淀区","parent_id":"110000","region_type":"2"},{"id":"110109","region_name":"门头沟区","parent_id":"110000","region_type":"2"},{"id":"110111","region_name":"房山区","parent_id":"110000","region_type":"2"},{"id":"110112","region_name":"通州区","parent_id":"110000","region_type":"2"},{"id":"110113","region_name":"顺义区","parent_id":"110000","region_type":"2"},{"id":"110114","region_name":"昌平区","parent_id":"110000","region_type":"2"},{"id":"110115","region_name":"大兴区","parent_id":"110000","region_type":"2"},{"id":"110116","region_name":"怀柔区","parent_id":"110000","region_type":"2"},{"id":"110117","region_name":"平谷区","parent_id":"110000","region_type":"2"}]
         */

        private int id;
        private String region_name;
        private String parent_id;
        private String region_type;
        private List<ChildBean> child;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getRegion_type() {
            return region_type;
        }

        public void setRegion_type(String region_type) {
            this.region_type = region_type;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class ChildBean implements Serializable{
            /**
             * id : 110101
             * region_name : 东城区
             * parent_id : 110000
             * region_type : 2
             */

            private int id;
            private String region_name;
            private String parent_id;
            private String region_type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRegion_name() {
                return region_name;
            }

            public void setRegion_name(String region_name) {
                this.region_name = region_name;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getRegion_type() {
                return region_type;
            }

            public void setRegion_type(String region_type) {
                this.region_type = region_type;
            }
        }
    }
}
