package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/26.
 * by--首页
 */

public class IndexHomeBean implements Serializable{


    /**
     * countlc : 2
     * Carousel : [{"id":"1","name":"测试","img":"74","position":"1","url":"","starttime":"1514270094","endtime":"1514649600","is_show":"1","sort":"100","note":"111","updated":"1514270094","created":"1514270094","file_path":"/goods/20171222/16a001d2cb8a1f81b541fbec8d364ee7.jpg"}]
     * category : [{"id":"1001","name":"衣服","file_path":"user_img/20171220/73d8d7ebe88bf503890e6843b2ac2b48.png"},{"id":"1007","name":"方法","file_path":"user_img/20171220/9af7c3dc55d668f5b297268632e65cac.png"}]
     * list : [{"id":"2","name":"精品","icon":"2","goods_count":"2","sort":"80","is_show":"1","admin_id":"0","updated":"0","created":"1513761478","user_type":"1","user_id":"3","file_type":"1","file_path":"user_img/20171220/76838e1bb2d82d7bdb4cff0ef96ed202.png","goods":[{"id":"4","floor_id":"2","goods_id":"3","position":"1","img":"4","user_type":"1","user_id":"1","file_type":"1","file_path":"user_img/20171220/73d8d7ebe88bf503890e6843b2ac2b48.png","created":"1513761979"},{"id":"73","floor_id":"2","goods_id":"10","position":"2","img":"73","user_type":"3","user_id":"10088","file_type":"5","file_path":"/goods/20171222/626a5bc5e237566a7bac211981289f43.jpg","created":"1513914111"},{"id":"76","floor_id":"2","goods_id":"4","position":"3","img":"76","user_type":"3","user_id":"10088","file_type":"5","file_path":"/goods/20171222/04cb9c2e017b7401801527c78776768e.jpg","created":"1513914448"}],"lcid":1,"count":3},{"id":"1","name":"热推","icon":"1","goods_count":"3","sort":"100","is_show":"1","admin_id":"0","updated":"0","created":"1513759872","user_type":"1","user_id":"2","file_type":"1","file_path":"user_img/20171220/9af7c3dc55d668f5b297268632e65cac.png","goods":[{"id":"4","floor_id":"1","goods_id":"1","position":"1","img":"4","user_type":"1","user_id":"1","file_type":"1","file_path":"user_img/20171220/73d8d7ebe88bf503890e6843b2ac2b48.png","created":"1513761979"},{"id":"70","floor_id":"1","goods_id":"2","position":"2","img":"70","user_type":"3","user_id":"10088","file_type":"5","file_path":"/goods/20171222/d854f93f760bea4b35a7c2cf1c5c5ae9.jpg","created":"1513914111"}],"lcid":2,"count":2}]
     */

    private int countlc;
    private List<CarouselBean> Carousel;
    private List<CategoryBean> category;
    private List<ListBean> list;

    public int getCountlc() {
        return countlc;
    }

    public void setCountlc(int countlc) {
        this.countlc = countlc;
    }

    public List<CarouselBean> getCarousel() {
        return Carousel;
    }

    public void setCarousel(List<CarouselBean> Carousel) {
        this.Carousel = Carousel;
    }

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class CarouselBean {
        /**
         * id : 1
         * name : 测试
         * img : 74
         * position : 1
         * url :
         * starttime : 1514270094
         * endtime : 1514649600
         * is_show : 1
         * sort : 100
         * note : 111
         * updated : 1514270094
         * created : 1514270094
         * file_path : /goods/20171222/16a001d2cb8a1f81b541fbec8d364ee7.jpg
         */

        private String id;
        private String name;
        private String img;
        private String position;
        private String url;
        private String starttime;
        private String endtime;
        private String is_show;
        private String sort;
        private String note;
        private String updated;
        private String created;
        private String file_path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
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

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
    }

    public static class CategoryBean {
        /**
         * id : 1001
         * name : 衣服
         * file_path : user_img/20171220/73d8d7ebe88bf503890e6843b2ac2b48.png
         */

        private String id;
        private String name;
        private String file_path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
    }

    public static class ListBean {
        /**
         * id : 2
         * name : 精品
         * icon : 2
         * goods_count : 2
         * sort : 80
         * is_show : 1
         * admin_id : 0
         * updated : 0
         * created : 1513761478
         * user_type : 1
         * user_id : 3
         * file_type : 1
         * file_path : user_img/20171220/76838e1bb2d82d7bdb4cff0ef96ed202.png
         * goods : [{"id":"4","floor_id":"2","goods_id":"3","position":"1","img":"4","user_type":"1","user_id":"1","file_type":"1","file_path":"user_img/20171220/73d8d7ebe88bf503890e6843b2ac2b48.png","created":"1513761979"},{"id":"73","floor_id":"2","goods_id":"10","position":"2","img":"73","user_type":"3","user_id":"10088","file_type":"5","file_path":"/goods/20171222/626a5bc5e237566a7bac211981289f43.jpg","created":"1513914111"},{"id":"76","floor_id":"2","goods_id":"4","position":"3","img":"76","user_type":"3","user_id":"10088","file_type":"5","file_path":"/goods/20171222/04cb9c2e017b7401801527c78776768e.jpg","created":"1513914448"}]
         * lcid : 1
         * count : 3
         */

        private String id;
        private String name;
        private String icon;
        private String goods_count;
        private String sort;
        private String is_show;
        private String admin_id;
        private String updated;
        private String created;
        private String user_type;
        private String user_id;
        private String file_type;
        private String file_path;
        private int lcid;
        private int count;
        private List<GoodsBean> goods;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(String goods_count) {
            this.goods_count = goods_count;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
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

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFile_type() {
            return file_type;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public int getLcid() {
            return lcid;
        }

        public void setLcid(int lcid) {
            this.lcid = lcid;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * id : 4
             * floor_id : 2
             * goods_id : 3
             * position : 1
             * img : 4
             * user_type : 1
             * user_id : 1
             * file_type : 1
             * file_path : user_img/20171220/73d8d7ebe88bf503890e6843b2ac2b48.png
             * created : 1513761979
             */

            private String id;
            private String floor_id;
            private String goods_id;
            private String position;
            private String img;
            private String user_type;
            private String user_id;
            private String file_type;
            private String file_path;
            private String created;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFloor_id() {
                return floor_id;
            }

            public void setFloor_id(String floor_id) {
                this.floor_id = floor_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getFile_type() {
                return file_type;
            }

            public void setFile_type(String file_type) {
                this.file_type = file_type;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }
        }
    }
}
