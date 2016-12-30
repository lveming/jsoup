package com.lm.demo.slow_life.biz;

import android.util.Log;

import com.lm.demo.slow_life.bean.CommonException;
import com.lm.demo.slow_life.bean.DetailsBean;
import com.lm.demo.slow_life.bean.LifeBean;
import com.lm.demo.slow_life.util.DataUtil;
import com.lm.demo.slow_life.util.UrlUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/11.
 */
public class LifeItemBiz {

    //利用jsoup解析html，得到相应的信息
    public List<LifeBean> getNewsItems(int newsType, int curPage) throws CommonException {
        Log.e("提示","进入到LifeItemBiz");
        List<LifeBean> newsItems = new ArrayList<>();
        //根据类型和页码得到相应的路径，具体去UrlUtil查看
        String url = UrlUtil.getUrl(newsType, curPage);
        //得到html数据
        String htmlStr = DataUtil.doGet(url);
        //  Log.e("html数据", htmlStr);
        LifeBean item = null;
        //jsoup爬取数据
        Document doc = Jsoup.parse(htmlStr);
        Elements W_linka = doc.getElementsByClass("W_linka");
        for (Element element:W_linka){
            item=new LifeBean();
            //标题和详细内容链接
            Element h1=element.getElementsByTag("h1").first();
            String urlLink=h1.getElementsByTag("a").first().attr("href");
            String title=h1.text();
            //内容
            String conment=element.getElementsByClass("comment").text();
           //获取时间
            String typeStr= element.getElementsByTag("span").get(0).text();
            String timeStr=element.getElementsByTag("span").get(1).text();
            String time=typeStr+"  "+timeStr;
            try {
                Element img_ele = element.getElementsByTag("img").get(0);
                String imgLink = img_ele.attr("src");
                //Log.e("打印",imgLink);
                item.setImgLink(imgLink);
            } catch (IndexOutOfBoundsException e) {
            }
            item.setTime(time);
            item.setLink(urlLink);
            item.setTitle(title);
            item.setLifeType(newsType);
            item.setContent(conment);
            newsItems.add(item);
        }
        return newsItems;
    }

    public DetailsBean getNewsDetial(String html) {
        Log.e("打印","进入getNewsDetial（）");
        DetailsBean bean = new DetailsBean();
        Document doc = Jsoup.parse(html);
        // 获得文章中的第一个detail
        //Log.e("打印html",html);
        Element content = doc.getElementsByClass("xq-center").first();
        Log.e("打印title",content.toString());
        //获取title
        Element title = content.getElementsByClass("xq-title").first();
        bean.setTitle(title.text());
        Log.e("打印title",title.text());
        String sourceStr=content.getElementsByClass("xq-fbr").first().text();
        Log.e("打印sourceStr",sourceStr);
        bean.setSource(sourceStr);
        Element nrwz=content.getElementsByClass("xq-nrwz").first();
        Log.e("打印contentStr",nrwz.toString());
        bean.setContent(nrwz.toString());

        return bean;
    }
}
