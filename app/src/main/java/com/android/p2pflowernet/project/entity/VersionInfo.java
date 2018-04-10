package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * @描述:版本更新
 * @创建人：zhangpeisen
 * @创建时间：2017/12/26 下午2:14
 * @修改人：zhangpeisen
 * @修改时间：2017/12/26 下午2:14
 * @修改备注：
 * @throws
 */
public class VersionInfo implements Serializable {

    /**
     * id : 2
     * app_type : 1
     * version_code : 2
     * version_name : 1.0.2
     * version_dec : 强制更新
     * url :
     * is_impose : 1
     * create_time : 1514256259
     */

    private String id;
    private String app_type;
    private String version_code;
    private String version_name;
    private String version_dec;
    private String url;
    private String is_impose;
    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getVersion_dec() {
        return version_dec;
    }

    public void setVersion_dec(String version_dec) {
        this.version_dec = version_dec;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIs_impose() {
        return is_impose;
    }

    public void setIs_impose(String is_impose) {
        this.is_impose = is_impose;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "VersionInfo{" +
                "id='" + id + '\'' +
                ", app_type='" + app_type + '\'' +
                ", version_code='" + version_code + '\'' +
                ", version_name='" + version_name + '\'' +
                ", version_dec='" + version_dec + '\'' +
                ", url='" + url + '\'' +
                ", is_impose='" + is_impose + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
