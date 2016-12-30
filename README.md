# jsoup
数据爬取自慢生活，使用jsoup爬取

根据github上一个仿csdn项目做了下图的功能，主要学习jsoup的使用，TabLayout+RecycleView搭配fragment的使用，还有就是数据库知识，数据来源：使用jsoup爬取《慢生活》的[文艺类](http://www.manshijian.com/articles/category/dianying.html)

要点是要理解爬取url的结构
例如：http://www.manshijian.com/articles/category/dianying/2
它的结构为“http://www.manshijian.com/articles/category/”+ 种类 +"/"+页数
根据这样，我们只要设计怎样提供种类和页数，就能设计出各种风格的界面（比如选择按钮，这里是根据TabLayout的文字提供种类，下拉刷新时加载第二页，以此类推，并存储在数据库中）

具体内容，思路都写在代码中了

github地址：[https://github.com/lveming/jsoup/tree/master](https://github.com/lveming/jsoup/tree/master)

![这里写图片描述](http://img.blog.csdn.net/20161230205047584?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzMzMDY5ODg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
