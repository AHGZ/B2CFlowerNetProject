/*
 * Copyright (c) 2016.  Joe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.p2pflowernet.project.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.p2pflowernet.project.callback.ISelect;
/**
 * @描述: 多选的
 * @创建人：zhangpeisen
 * @创建时间：2017/10/26 下午6:45
 * @修改人：zhangpeisen
 * @修改时间：2017/10/26 下午6:45
 * @修改备注：
 * @throws
 */
public class SelectBean implements ISelect, Parcelable {
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected SelectBean() {
    }

    @SuppressWarnings("WeakerAccess")
    protected SelectBean(Parcel in) {
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<SelectBean> CREATOR = new Creator<SelectBean>() {
        @Override
        public SelectBean createFromParcel(Parcel source) {
            return new SelectBean(source);
        }

        @Override
        public SelectBean[] newArray(int size) {
            return new SelectBean[size];
        }
    };
}
