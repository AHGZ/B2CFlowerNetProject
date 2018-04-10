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

package com.android.p2pflowernet.project.base;

import android.view.View;

import com.android.p2pflowernet.project.anntoation.SelectMode;
import com.android.p2pflowernet.project.callback.ISelect;

import java.util.HashSet;

/**
 * @描述:对recycleview 子类进行单选 多选
 * @创建人：zhangpeisen
 * @创建时间：2017/10/26 下午6:20
 * @修改人：zhangpeisen
 * @修改时间：2017/10/26 下午6:20
 * @修改备注：
 * @throws
 */
public abstract class ReSelectAdapter<T extends ISelect> extends HFWBaseAdapter<T> {

    private int currentMode = ISelect.SINGLE_MODE;
    private int prePos;
    private boolean longTouchEnable = false;
    public boolean isSelectMode;

    public HashSet<T> getSelectedBeans() {
        return selectedBeans;
    }

    private final HashSet<T> selectedBeans = new HashSet<>();

    public void updateSelectMode(boolean isSelect) {
        if (isSelectMode != isSelect) {
            isSelectMode = isSelect;
            resetData();
            notifyDataSetChanged();
        }
    }

    public ReSelectAdapter(@SelectMode int currentMode, boolean longTouchEnable) {
        this.currentMode = currentMode;
        this.longTouchEnable = longTouchEnable;
    }

    private void resetData() {
        for (ISelect bean : list) {
            bean.setSelected(false);
        }
    }

    public boolean isSelectMode() {
        return isSelectMode;
    }

    public void longTouchSelectModeEnable(boolean longTouchSelectModeEnable) {
        longTouchEnable = longTouchSelectModeEnable;
    }


    public void setSelectedMode(@SelectMode int mode) {
        currentMode = mode;
    }

    @Override
    public void performClick(final View itemView, final int position, T item) {
        final T testBean = list.get(position);

        if (isSelectMode) {
            boolean selected = !testBean.isSelected();
            testBean.setSelected(selected);
            dispatchSelected(itemView, position, testBean, selected);
            if (currentMode == ISelect.SINGLE_MODE && position != prePos && testBean.isSelected()) {
                list.get(prePos).setSelected(false);
                dispatchSelected(itemView, prePos, testBean, false);
                notifyItemChanged(prePos);
            }
            notifyItemRangeChanged(position, 1);
            prePos = position;
        } else {
            if (clickListener != null) {
                clickListener.onItemClick(itemView, position, getItem(position));
            }
        }
    }

    private void dispatchSelected(View itemView, int position, T testBean, boolean isSelected) {
        if (isSelected) {
            selectedBeans.add(testBean);
        } else {
            selectedBeans.remove(testBean);
            if (selectedListener != null && selectedBeans.isEmpty()) {
                selectedListener.onNothingSelected();
            }
        }
        if (selectedListener != null) {
            selectedListener.onItemSelected(itemView, position, isSelected);
        }
    }

    @Override
    public boolean performLongClick(View itemView, int position, T item) {
        if (longTouchEnable) {
            final T testBean = list.get(position);
            updateSelectMode(true);
            testBean.setSelected(!testBean.isSelected());
            dispatchSelected(itemView, position, testBean, testBean.isSelected());
            notifyItemChanged(position);
            prePos = position;
            return true;
        } else {
            return super.performLongClick(itemView, position, item);
        }

    }

    public void setOnItemSelectListener(OnItemSelectedListener listener) {
        this.selectedListener = listener;
    }

    @Override
    public int getSpanSize(int position) {
        return super.getSpanSize(position);
    }
}
