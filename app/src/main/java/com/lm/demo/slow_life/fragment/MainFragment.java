package com.lm.demo.slow_life.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lm.demo.slow_life.Activity.DetailsActivity;
import com.lm.demo.slow_life.R;
import com.lm.demo.slow_life.adapter.MainAdapter;
import com.lm.demo.slow_life.bean.CommonException;
import com.lm.demo.slow_life.bean.Constant;
import com.lm.demo.slow_life.bean.LifeBean;
import com.lm.demo.slow_life.biz.LifeItemBiz;
import com.lm.demo.slow_life.db.NewsItemDao;
import com.lm.demo.slow_life.util.NetUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/12/10.
 * 重点实现部分，也是难点部分
 * 对网络进行判断，没网时进行加载数据库中的数据
 *
 *
 */
public class MainFragment extends Fragment{

    public static final String Slow_TYPE = "SLOW_TYPE";
    public static final int LOAD_REFRESH = 0x01;
    public static final int LOAD_MORE = 0x02;
    public static final String TIP_ERROR_NO_NETWORK = "没有网络连接";
    public static final String TIP_ERROR_NO_SERVICE = "服务器错误";

    private Context mContext;
//    private TextView mTextView;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecycleView;
    private LinearLayoutManager mManager;

    private LifeItemBiz lifeItemBiz;
    private MainAdapter mAdapter;
    private NewsItemDao mNewsItemDao;
    //默认类型
    private int lifeType = 1;
    //当前页面
    private int curPage = 1;
    //是否从服务器(网上)下载数据
    private boolean isLoadFromService;
    public static MainFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(Slow_TYPE, position);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //根据生命周期，先onCreateView（）后onActivityCreated（）
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext=getActivity();

        mNewsItemDao=new NewsItemDao(mContext);
        lifeItemBiz=new LifeItemBiz();
        initView();
        initData();
        initEvent();

        new DownLoadTask().execute(LOAD_REFRESH);

    }

    private void initEvent() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DownLoadTask().execute(LOAD_REFRESH);

            }
        });

        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //取到当前可视化的最底部位置
                int last = mManager.findLastVisibleItemPosition();
                //newState== RecyclerView.SCROLL_STATE_IDLE 表示停止滑动状态
                //last + 1 == mAdapter.getItemCount()       表示当前看到的最底项是否为第一次加载数据的最底项，适配器的getItemCount也做了+1的处理
                //mAdapter.getItemCount() > 1               表示确保先有数据才能加载更多
                if (newState == RecyclerView.SCROLL_STATE_IDLE && last + 1 == mAdapter.getItemCount() && mAdapter.getItemCount() > 1) {
                    new DownLoadTask().execute(LOAD_MORE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mAdapter.setOnItemClickLitener(new MainAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                //得到点击项的bean
                LifeBean item = mAdapter.getDatas().get(position);
                //让人知道启动SNewsInfoActivity需要传递那些数据,另外简化了启动活动的代码
                //http://blog.csdn.net/chathello/article/details/53201536 这里讲得很详细
                //启动另一个Activity
                DetailsActivity.actionStart(mContext, item.getLink());
              //  Test.actionStart(mContext,item.getLink());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    private void initData() {
        Bundle bundle = getArguments();
        lifeType = bundle.getInt(Slow_TYPE, Constant.LIFE_TYPE_SUIBI);
        mAdapter=new MainAdapter(mContext);
        mRecycleView.setAdapter(mAdapter);
    }

    private void initView() {
        mSwipeRefresh= (SwipeRefreshLayout) getView().findViewById(R.id.id_fragment_SwipeRefreshLayout);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent,
                R.color.green, R.color.colorPrimaryDark);
        mRecycleView= (RecyclerView) getView().findViewById(R.id.id_fragment_RecyclerView);
        mManager=new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(mManager);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

//        mSwipeRefresh.post(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeRefresh.setRefreshing(true);
//            }
//        });
    }

    class DownLoadTask extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... params) {
            switch (params[0]) {
                case LOAD_REFRESH:
                    return refreshData();
                case LOAD_MORE:
                    return loadMoreData();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (null == result) {
                //如果异常信息等于空（没有异常），就加载更多数据
                mAdapter.setIsLoading(false);
                mAdapter.setmError(null);
            } else {
                mAdapter.setmError(result);
                mAdapter.setIsLoading(true);
                //Snackbar提供了一个介于Toast和AlertDialog之间轻量级控件，它可以很方便的提供消息的提示和动作反馈。
                Snackbar.make(mSwipeRefresh, result, Snackbar.LENGTH_LONG).show();
            }

            mSwipeRefresh.setRefreshing(false);
            mAdapter.notifyDataSetChanged();
        }
    }

    //更新数据
    private String refreshData() {
        if (NetUtil.isOnline(mContext)) {
            curPage = 1;
            try {
                //根据类型和页码得到想要的数据存到每个Bean中
                List<LifeBean> items = lifeItemBiz.getNewsItems(lifeType, curPage);
                if (!items.isEmpty()) {
                    mAdapter.setDatas(items);
                    //更新数据库
                    mNewsItemDao.refreshData(lifeType, items);
                }

                isLoadFromService = true;
            } catch (CommonException e) {
                e.printStackTrace();
                isLoadFromService = false;
                return TIP_ERROR_NO_SERVICE;
            }
        } else {
            //没有网络时，从数据库中得到当前类型的第一页数据
            List<LifeBean> items = mNewsItemDao.getNewsItems(curPage, lifeType);
            if (!items.isEmpty()) {
                mAdapter.setDatas(items);
                isLoadFromService = false;
            }
            return TIP_ERROR_NO_NETWORK;
        }
        return null;
    }
    //加载更多数据
    private String loadMoreData() {
        mAdapter.setIsLoading(true);
        if (isLoadFromService) {
            curPage++;
            try {
                //根据类型和页码得到想要的数据存到每个Bean中
                List<LifeBean> items = lifeItemBiz.getNewsItems(lifeType, curPage);
                mAdapter.addDatas(items);
                mNewsItemDao.addNewsItems(items);
            } catch (CommonException e) {
                e.printStackTrace();
                return e.getMessage();
            }

        } else {
            curPage++;
            //不从网上加载数据，就从数据库中加载
            List<LifeBean> items = mNewsItemDao.getNewsItems(curPage, lifeType);
            mAdapter.addDatas(items);
            return TIP_ERROR_NO_NETWORK;
        }
        return null;
    }
}
