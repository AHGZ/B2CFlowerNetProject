package com.android.p2pflowernet.project.callback;

/**
 * author: zhangpeisen
 * created on: 2017/10/26 下午5:42
 * description:
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.p2pflowernet.project.anntoation.LoadState;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;

import java.util.List;

/**
 * @描述:加载更多基类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/26 下午6:16
 * @修改人：zhangpeisen
 * @修改时间：2017/10/26 下午6:16
 * @修改备注：
 * @throws
 */
public interface AdapterLoader<T> {

    /**
     * state about load more..
     */
    int STATE_LOADING = 1;
    int STATE_LASTED = 2;
    int STATE_ERROR = 3;

    int TYPE_BOTTOM = 0x80000000;
    int TYPE_EMPT = 0x80000001;
    int TYPE_ERROR = 0x80000010;


    /**
     * This method should be called  when you load more !
     *
     * @param holder    the current holder.
     * @param loadState the current state.
     */
    void onBottomViewHolderBind(BaseHolder<T> holder, @LoadState int loadState);

    /**
     * If you want to create the specified bottom layout,you must call this method to add your specified layout !
     *
     * @param view the specified bottom layout
     */
    void setLoadMoreView(View view);

    void setEmptyView(View emptyView);

    void setErrorView(View errorView);

    void showEmpty();

    /**
     * show the error view when load data error.
     *
     * @param force true would show the error view don't care there was data before.false would care about.
     */
    void showError(boolean force);

    /**
     * If you want to create the specified bottom layout ,you should implements this method to create your own bottomViewHolder
     *
     * @param loadMore whether is loadingMore or not..
     */
    BaseHolder<T> onBottomViewHolderCreate(View loadMore);

    boolean isHasMore();

    void isLoadingMore();

    void loadMoreError();

    void enableLoadMore(boolean loadMore);

    /**
     * You can call this method to add data to RecycleView,if you want to append data,you should call {@link #appendList(List)}
     *
     * @param data the data you want to add
     */
    void setList(List<T> data);

    void clearList();

    /**
     * @param data the data you want to add
     */
    void appendList(List<T> data);

    /**
     * remove the specified position in the list.
     *
     * @param position he specified position to remove
     * @return if successful return_ticket the removed object,otherwise null
     */
    T removeItem(int position);


    T getItem(int position);

    /**
     * remove the specified position in the list.
     *
     * @param position he specified position to remove
     */
    void insertItem(int position, T bean);

    /**
     * @param position the current pos .
     * @return the current Type.
     */
    int getItemViewTypes(int position);

    /**
     * @param holder   current holder.
     * @param position current pos.
     */
    void onViewHolderBind(BaseHolder<T> holder, int position);

    BaseHolder<T> onViewHolderCreate(ViewGroup parent, int viewType);

    /**
     * Return the current size about {@link HFWBaseAdapter#list}.
     *
     * @return current list size!
     */
    int getItemRealCount();

    void performClick(View itemView, int position, T item);

    boolean performLongClick(View itemView, int position, T item);

    /**
     * call this method after init RecyclerView(set LayoutManager)
     */
    void attachRecyclerView(@NonNull RecyclerView recyclerView);

    /**
     * Interface definition for a callback to be invoked when
     * an item in this view has been selected.
     */
    interface OnItemSelectedListener {
        /**
         * <p>Callback method to be invoked when an item in this view has been
         * selected. This callback is invoked only when the newly selected
         * position is different from the previously selected position or if
         * there was no selected item.</p>
         * <p>
         * Impelmenters can call getItemAtPosition(position) if they need to access the
         * data associated with the selected item.
         *
         * @param view       The view within the AdapterView that was clicked
         * @param position   The position of the view in the adapter
         * @param isSelected The state of isSelected
         */
        void onItemSelected(View view, int position, boolean isSelected);

        /**
         * Callback method to be invoked when the selection disappears from this
         * view. The selection can disappear for instance when touch is activated
         * or when the adapter becomes empty.
         */
        void onNothingSelected();
    }

    interface OnItemClickListener<T> {
        void onItemClick(View itemView, int position, T item);
    }

    interface OnItemLongClickListener<T> {
        boolean onItemLongClick(View itemView, int position, T item);
    }


}