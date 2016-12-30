package com.lm.demo.slow_life.util;

import com.lm.demo.slow_life.bean.Constant;

/**
 * Created by Administrator on 2016/12/11.
 */
public class UrlUtil {
    //得到最终的链接为 http://mobile.csdn.net/mobile/1  数字1代表哪一页
    public static final String NEWS_LIST_URL_SUIBI = "http://www.manshijian.com/articles/category/suibi/";// 随笔
    public static final String NEWS_LIST_URL_DONGMAN = "http://www.manshijian.com/articles/category/dongman/";// 动漫
    public static final String NEWS_LIST_URL_YINYUE = "http://www.manshijian.com/articles/category/yinyue/";// 音乐
    public static final String NEWS_LIST_URL_DIANYING = "http://www.manshijian.com/articles/category/dianying/";// 电影
    public static final String NEWS_LIST_URL_XIJU = "http://www.manshijian.com/articles/category/xiju/";// 戏剧
    public static final String NEWS_LIST_URL_YUEDU = "http://www.manshijian.com/articles/category/yuedu/";// 阅读

    /**
     * 根据文章类型和当前页码生成url
     * @param newsType
     * @param curPage
     * @return
     */
    public static String getUrl(int newsType, int curPage) {
        String url = "";
        switch (newsType) {
            case Constant.LIFE_TYPE_SUIBI:
                url = NEWS_LIST_URL_SUIBI;
                break;
            case Constant.LIFE_TYPE_DONGMAN:
                url = NEWS_LIST_URL_DONGMAN;
                break;
            case Constant.LIFE_TYPE_XIJU:
                url = NEWS_LIST_URL_XIJU;
                break;
            case Constant.LIFE_TYPE_DIANYING:
                url = NEWS_LIST_URL_DIANYING;
                break;
            case Constant.LIFE_TYPE_YUEDU:
                url = NEWS_LIST_URL_YUEDU;
                break;
            case Constant.LIFE_TYPE_YINYUE:
                url = NEWS_LIST_URL_YINYUE;
                break;

            default:
                break;
        }
        url+=curPage;
        return url;
    }

}
