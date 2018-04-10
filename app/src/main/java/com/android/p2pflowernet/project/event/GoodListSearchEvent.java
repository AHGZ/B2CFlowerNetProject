package com.android.p2pflowernet.project.event;

/**
 * Created by Administrator on 2018/3/15/015.
 */

public class GoodListSearchEvent {
    String id;
    String tag;
    String searchName;

    public GoodListSearchEvent(String id,String tag,String searchName) {
        this.id = id;
        this.tag = tag;
        this.searchName = searchName;
    }

    public String getId() {
        return id;
    }
    public String getTag() {
        return tag;
    }
    public String getSearchName() {
        return searchName;
    }
}
