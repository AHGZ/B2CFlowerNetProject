package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/12/16 上午10:29
 * description: 退换货详情
 */
public class RefundDetailBean implements Serializable {
    /**
     * id : 24
     * manufac_id : 10088
     * refund_money : 0.00
     * reason : 没有原因，就是想退货
     * is_return : 0
     * explain : 同学
     * express_name :
     * waybill_num :
     * refuse_reason :
     * cs_id : 0
     * arbit_id : 0
     * refund_state : 1
     * finished : 0
     * created : 2017-12-18 11:43:37
     * manufac_name : test1
     * goods_list : [{"goods_name":"花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身","goods_num":"1","goods_spec":"红色 160","goods_price":"2281.00","goods_img":"business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg"}]
     * imgs_path : [{"file_path":"role_auth_img/20171218/36b8f975b013c75a3b65a1e257784988.png"},{"file_path":"role_auth_img/20171218/2cece49028fb10630e29e056f40e4c08.png"}]
     * arbit_content :
     */

    private String id;
    private String manufac_id;
    private String refund_money;
    private String reason;
    private String is_return;
    private String explain;
    private String express_name;
    private String waybill_num;
    private String refuse_reason;
    private String cs_id;
    private String arbit_id;
    private String refund_state;
    private String finished;
    private String created;
    private String manufac_name;
    private String arbit_content;
    /**
     * goods_name : 花花公子真皮皮衣男2017秋冬新品海宁绵羊皮真皮男士商务修身
     * goods_num : 1
     * goods_spec : 红色 160
     * goods_price : 2281.00
     * goods_img : business_auth_img/20171123/3bb1a17ee24736d7391a6e0891016d2c.jpg
     */

    private List<GoodsListBean> goods_list;
    /**
     * file_path : role_auth_img/20171218/36b8f975b013c75a3b65a1e257784988.png
     */

    private List<ImgsPathBean> imgs_path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManufac_id() {
        return manufac_id;
    }

    public void setManufac_id(String manufac_id) {
        this.manufac_id = manufac_id;
    }

    public String getRefund_money() {
        return refund_money;
    }

    public void setRefund_money(String refund_money) {
        this.refund_money = refund_money;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIs_return() {
        return is_return;
    }

    public void setIs_return(String is_return) {
        this.is_return = is_return;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public String getWaybill_num() {
        return waybill_num;
    }

    public void setWaybill_num(String waybill_num) {
        this.waybill_num = waybill_num;
    }

    public String getRefuse_reason() {
        return refuse_reason;
    }

    public void setRefuse_reason(String refuse_reason) {
        this.refuse_reason = refuse_reason;
    }

    public String getCs_id() {
        return cs_id;
    }

    public void setCs_id(String cs_id) {
        this.cs_id = cs_id;
    }

    public String getArbit_id() {
        return arbit_id;
    }

    public void setArbit_id(String arbit_id) {
        this.arbit_id = arbit_id;
    }

    public String getRefund_state() {
        return refund_state;
    }

    public void setRefund_state(String refund_state) {
        this.refund_state = refund_state;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getManufac_name() {
        return manufac_name;
    }

    public void setManufac_name(String manufac_name) {
        this.manufac_name = manufac_name;
    }

    public String getArbit_content() {
        return arbit_content;
    }

    public void setArbit_content(String arbit_content) {
        this.arbit_content = arbit_content;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public List<ImgsPathBean> getImgs_path() {
        return imgs_path;
    }

    public void setImgs_path(List<ImgsPathBean> imgs_path) {
        this.imgs_path = imgs_path;
    }

    public static class GoodsListBean implements Serializable{
        private String goods_name;
        private String goods_num;
        private String goods_spec;
        private String goods_price;
        private String goods_img;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_spec() {
            return goods_spec;
        }

        public void setGoods_spec(String goods_spec) {
            this.goods_spec = goods_spec;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        @Override
        public String toString() {
            return "O2oOrderListBean{" +
                    "goods_name='" + goods_name + '\'' +
                    ", goods_num='" + goods_num + '\'' +
                    ", goods_spec='" + goods_spec + '\'' +
                    ", goods_price='" + goods_price + '\'' +
                    ", goods_img='" + goods_img + '\'' +
                    '}';
        }
    }

    public static class ImgsPathBean implements Serializable{
        private String file_path;

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        @Override
        public String toString() {
            return "ImgsPathBean{" +
                    "file_path='" + file_path + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RefundDetailBean{" +
                "id='" + id + '\'' +
                ", manufac_id='" + manufac_id + '\'' +
                ", refund_money='" + refund_money + '\'' +
                ", reason='" + reason + '\'' +
                ", is_return='" + is_return + '\'' +
                ", explain='" + explain + '\'' +
                ", express_name='" + express_name + '\'' +
                ", waybill_num='" + waybill_num + '\'' +
                ", refuse_reason='" + refuse_reason + '\'' +
                ", cs_id='" + cs_id + '\'' +
                ", arbit_id='" + arbit_id + '\'' +
                ", refund_state='" + refund_state + '\'' +
                ", finished='" + finished + '\'' +
                ", created='" + created + '\'' +
                ", manufac_name='" + manufac_name + '\'' +
                ", arbit_content='" + arbit_content + '\'' +
                ", goods_list=" + goods_list +
                ", imgs_path=" + imgs_path +
                '}';
    }
}
