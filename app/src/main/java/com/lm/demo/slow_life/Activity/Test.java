package com.lm.demo.slow_life.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.lm.demo.slow_life.R;
import com.lm.demo.slow_life.bean.CommonException;
import com.lm.demo.slow_life.biz.LifeItemBiz;
import com.lm.demo.slow_life.util.DataUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/11.
 * 测试类
 */
public class Test extends AppCompatActivity {

    @BindView(R.id.id_test_button)
    Button idTestButton;
    String link;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        idTestButton.setText("按钮");
       link = getIntent().getStringExtra("link");
        Log.e("link",link);
    }

    @OnClick(R.id.id_test_button)
    public void onClick() {
        Log.e("打印","点击按钮");
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("");
    }

    class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String html = null;
            try {
                html = DataUtil.doGet(link);
                LifeItemBiz lifeItemBiz=new LifeItemBiz();
                lifeItemBiz.getNewsDetial(html);
            } catch (CommonException e) {
                e.printStackTrace();
            }
            return html;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public static void actionStart(Context context, String url){
        Intent intent = new Intent(context,Test.class);
        intent.putExtra("link",url);
        context.startActivity(intent);
    }
}
