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

package com.android.p2pflowernet.project.callback;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.android.p2pflowernet.project.base.HFWBaseAdapter;

/**
 * @描述: 加载更多处理滚动监听
 * @创建人：zhangpeisen
 * @创建时间：2017/10/26 下午6:17
 * @修改人：zhangpeisen
 * @修改时间：2017/10/26 下午6:17
 * @修改备注：
 * @throws
 */
public class LoadMoreScrollListener extends RecyclerView.OnScrollListener {
    private final RecyclerView mRecyclerView;

    //        private int lastTitlePos = -1;
    public LoadMoreScrollListener(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
        HFWBaseAdapter adapter = (HFWBaseAdapter) mRecyclerView.getAdapter();
        if (null == manager) {
            throw new RuntimeException("you should call setLayoutManager() first!!");
        }
        if (null == adapter) {
            throw new RuntimeException("you should call setAdapter() first!!");
        }
        if (manager instanceof LinearLayoutManager) {
            int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition();

            if (adapter.getItemCount() > 1 && lastCompletelyVisibleItemPosition >= adapter.getItemCount() - 1 && adapter.isHasMore()) {
                adapter.isLoadingMore();
//                    if (null != listener) {
//                        listener.onLoadMore();
//                    }
            }
//                int position = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
//                if (lastTitlePos == position) {
//                    return_ticket;
//                }
//                lastTitlePos = position;
        }
        if (manager instanceof StaggeredGridLayoutManager) {
            int count = ((StaggeredGridLayoutManager) manager).getSpanCount();
            int[] itemPositions = new int[count];
            ((StaggeredGridLayoutManager) manager).findLastVisibleItemPositions(itemPositions);
            int lastVisibleItemPosition = itemPositions[0];
            for (int i = count - 1; i > 0; i--) {
                if (lastVisibleItemPosition < itemPositions[i]) {
                    lastVisibleItemPosition = itemPositions[i];
                }
            }
            if (lastVisibleItemPosition >= adapter.getItemCount() - 1 && adapter.isHasMore()) {
                adapter.isLoadingMore();
//                    if (null != listener) {
//                        listener.onLoadMore();
//                    }
            }
        }
    }

}
