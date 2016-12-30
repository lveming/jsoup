package com.lm.demo.slow_life.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lm.demo.slow_life.R;
import com.lm.demo.slow_life.bean.CommonException;
import com.lm.demo.slow_life.bean.DetailsBean;
import com.lm.demo.slow_life.bean.HtmlFrame;
import com.lm.demo.slow_life.biz.LifeItemBiz;
import com.lm.demo.slow_life.util.DataUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
* 详细类
* 使用WebView显示，思路：将html爬取下来，解析有用的部分，
* 分别为标题，时间，内容，然后自己写一个html模板，使用String类的format()插入解析出来的内容
*
* */
public class DetailsActivity extends AppCompatActivity {


    @BindView(R.id.id_imb_back)
    ImageButton back;
    @BindView(R.id.id_loadfailed)
    TextView LoadfailedTV;
    @BindView(R.id.id_newsinfo_webview)
    WebView mWebview;
    @BindView(R.id.id_newsinfo_refresh)
    SwipeRefreshLayout mRefresh;

    private String link;
    private WebSettings mWebSettings;
    private LifeItemBiz lifeItemBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initView();
        link = getIntent().getStringExtra("link");
        lifeItemBiz = new LifeItemBiz();
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                mRefresh.setRefreshing(true);
            }
        });
        new LoadDataTask().execute();
    }

    private void initView() {
        mWebSettings = mWebview.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mRefresh.setRefreshing(false);
            }
        });
        mRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.green,
                R.color.colorAccent, R.color.colorPrimaryDark);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadDataTask().execute();
            }
        });
    }

    @OnClick(R.id.id_imb_back)
    public void onClick() {
        finish();
    }

    class LoadDataTask extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params) {
            String html = null;
            try {
                html = DataUtil.doGet(link);
            } catch (CommonException e) {
                e.printStackTrace();
            }
            return html;
        }
        @Override
        protected void onPostExecute(String s) {
            if(!TextUtils.isEmpty(s)){
                LoadfailedTV.setVisibility(View.GONE);
                DetailsBean mNews = lifeItemBiz.getNewsDetial(s);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(formatHtml(HtmlFrame.FRAME, mNews.getTitle(), mNews.getSource(), mNews.getContent()));
                mWebview.loadData(stringBuffer.toString(), "text/html; charset=UTF-8", null);
            }else{
                LoadfailedTV.setVisibility(View.VISIBLE);
            }
            mRefresh.setRefreshing(false);
        }
    }
    private String formatHtml(String frame, String title, String infor, String texts) {
        return String.format(frame, title, infor, texts);

    }
    public static void actionStart(Context context, String url){
        Intent intent = new Intent(context,DetailsActivity.class);
        intent.putExtra("link",url);
        context.startActivity(intent);
    }
}
