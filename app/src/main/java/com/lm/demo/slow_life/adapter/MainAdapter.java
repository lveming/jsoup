package com.lm.demo.slow_life.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lm.demo.slow_life.R;
import com.lm.demo.slow_life.bean.LifeBean;
import com.lm.demo.slow_life.util.ImageLoader;
import com.lm.demo.slow_life.widget.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/10.
 *
 * RecycleView不支持点击事件，所以在适配器中添加OnItemClickLitener
 * 根据是否为item最后一项，显示“加载更多item布局”
 *
 */
public class MainAdapter extends RecyclerView.Adapter {
    //常规子布局
    private final int TYPE_NORMAL = 0;
    //加载更多子布局
    private final int TYPE_FOOT = 1;

    private Context mContext;
    private List<LifeBean> mDatas=new ArrayList<>();

    private OnItemClickLitener mOnItemClickLitener;
    private boolean isLoading;
    private String mError = null;

    public MainAdapter() {
    }

    public MainAdapter(Context context) {
        this.mContext=context;
    }

    @Override
    public int getItemViewType(int position) {

        if (position+1==getItemCount()){
            return TYPE_FOOT;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mDatas.size()+1;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据getItemViewType()返回的值进行判断
        if (viewType==TYPE_NORMAL){
            View view= LayoutInflater.from(mContext).inflate(R.layout.item_content,parent,false);
            return new ItemViewHolder(view);
        }
        if (viewType==TYPE_FOOT){
            View view= LayoutInflater.from(mContext).inflate(R.layout.item_footer,parent,false);
            return new FootHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            ((ItemViewHolder) holder).content.setText(mDatas.get(position).getContent());
           ((ItemViewHolder) holder).date.setText(mDatas.get(position).getTime());
            ((ItemViewHolder) holder).title.setText(mDatas.get(position).getTitle());
            if (mDatas.get(position).getImgLink()!=null){
            ((ItemViewHolder) holder).icon.setVisibility(View.VISIBLE);
                ImageLoader.withAndPlaveholder(mContext,mDatas.get(position).getImgLink(),R.mipmap.default_bg,R.mipmap.default_bg,((ItemViewHolder) holder).icon);
            }else {
                ((ItemViewHolder) holder).icon.setVisibility(View.GONE);
            }

            if (holder instanceof FootHolder) {
                ((FootHolder) holder).foot.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (mError != null) {
                    ((FootHolder) holder).progressBar.setVisibility(View.GONE);
                    ((FootHolder) holder).message.setText(mError);
                } else {
                    Log.e("打印","加载中");
                    ((FootHolder) holder).progressBar.setVisibility(View.VISIBLE);
                    ((FootHolder) holder).message.setText("加载中....");
                }
            }
            // 如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                        return false;
                    }
                });
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        TextView content;
        TextView date;

        public ItemViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.id_item_content_ImageView);
            title = (TextView) itemView.findViewById(R.id.id_item_content_TextView_Title);
            content = (TextView) itemView.findViewById(R.id.id_item_content_TextView_Content);
            date = (TextView) itemView.findViewById(R.id.id_item_content_TextView_time);
        }
    }

    class FootHolder extends RecyclerView.ViewHolder {
        LinearLayout foot;
        ProgressWheel progressBar;
        TextView message;

        public FootHolder(View itemView) {
            super(itemView);
            foot = (LinearLayout) itemView.findViewById(R.id.item_news_foot);
            progressBar = (ProgressWheel) itemView.findViewById(R.id.item_footer_progressbar);
            message = (TextView) itemView.findViewById(R.id.item_footer_message);
        }
    }

    public void addDatas(List<LifeBean> datas){
        mDatas.addAll(datas);
    }

    public void setDatas(List<LifeBean> datas){
        mDatas.clear();
        mDatas.addAll(datas);
    }

    public List<LifeBean> getDatas() {
        return mDatas;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public String getmError() {
        return mError;
    }

    public void setmError(String mError) {
        this.mError = mError;
    }
}
