package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/11/21 下午7:40
 * description:
 */
public class ImgDataBean implements Serializable {
    List<String> file_path;

    public List<String> getFile_path() {
        return file_path;
    }

    public void setFile_path(List<String> file_path) {
        this.file_path = file_path;
    }


    @Override
    public String toString() {
        return "ImgDataBean{" +
                "file_path=" + file_path +
                '}';
    }
}
