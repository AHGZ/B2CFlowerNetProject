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

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.AdapterLoader;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.callback.OnLoadMoreListener;
/**
 * @描述:
 * @创建人：zhangpeisen
 * @创建时间：2017/10/26 下午6:38
 * @修改人：zhangpeisen
 * @修改时间：2017/10/26 下午6:38
 * @修改备注：
 * @throws
 */
public class BottomViewHolder<T> extends BaseHolder<T> {

    private final View bottomView;

    public BottomViewHolder(View itemView) {
        super(itemView);
        bottomView = itemView.findViewById(R.id.progressbar);
    }

    public void onBind(OnLoadMoreListener loadMoreListener, int loadState) {
        if (loadState == AdapterLoader.STATE_LOADING) {
            // TODO: 2016/12/5 handle error state
            bottomView.setVisibility(View.VISIBLE);
            loadMoreListener.onLoadMore();
        } else {
            bottomView.setVisibility(View.GONE);
        }
    }
}
