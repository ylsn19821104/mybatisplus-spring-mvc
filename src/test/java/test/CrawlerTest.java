package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.springmvc.model.system.BeautifulPictures;
import com.baomidou.springmvc.model.system.Jobs;
import com.baomidou.springmvc.model.system.Picture;
import com.baomidou.springmvc.service.system.IBeautifulPicturesService;
import com.baomidou.springmvc.service.system.IPictureService;
import com.baomidou.springmvc.util.CrawlerUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath:spring/spring.xml"})
public class CrawlerTest {
    @Autowired
    IBeautifulPicturesService beautifulPicturesService;

    @Autowired
    IPictureService pictureService;

    @Test
    public void crawler() {
        //返回值
        int result = 1;
        //访问页码
        Integer page = 1;
        //启动爬虫
        System.out.println("爬虫开始工作！");
        while (result == 1) {
            result = doCrawler(page.toString());
            page += 1;
            if (result == 0) {
                System.out.println("爬虫运行结束！！");
            }
        }
    }

    @Test
    public void testLagou() {
        // 查詢關鍵字
        String kd = "java";
        // 查詢第幾頁
        int pn = 2;
        run(pn, kd);
    }

    public static void run(int pn, String kd) {
        // 訪問接口
//		JSONObject resultjson = CrawlerUtil.getReturnJson(URL);
//		System.out.println(resultjson.get("content"));
        String json = CrawlerUtil.sendPost("https://www.lagou.com/jobs/positionAjax.json?", "pn=" + pn + "&kd=" + kd);
        JSONObject content = (JSONObject) JSONObject.parseObject(json).get("content");
        JSONObject positionResult = (JSONObject) content.get("positionResult");
        Integer totalCount = (Integer) positionResult.get("totalCount");
        System.out.println(totalCount);
        JSONArray jsonArray = (JSONArray) positionResult.get("result");
        for (Object Object : jsonArray) {
            Jobs jobs = JSON.parseObject(Object.toString(), Jobs.class);
            System.out.println(jobs);
        }
    }


    private int doCrawler(String page) {
        //初始化返回值
        int result = 1;
        //网站首页地址
        String homeUrl = "http://www.87g.com/";
        //接口地址
        String url = "http://www.87g.com/index.php?m=content&c=content_ajax&a=picture_page&siteid=1&catid=35&page=" + page;
        System.out.println("当前爬取第" + page + "页数据");
        //访问接口，
        JSONObject resultjson = CrawlerUtil.getReturnJson(url);
        if (resultjson != null) {
            //获取其value值
            Collection<Object> jsonList = resultjson.values();
            for (Object obj : jsonList) {
                BeautifulPictures beautifulPictures = JSON.parseObject(obj.toString(), BeautifulPictures.class);
                String Keywords = beautifulPictures.getKeywords();
                //按map条件查询。判断是否已经爬过，没有就入库
                Map<String, Object> map = new HashMap<>();
                map.put("keywords", Keywords);
                int cont = beautifulPicturesService.selectByMap(map).size();
                if (cont == 0) {
                    //入库
                    beautifulPicturesService.insert(beautifulPictures);
                    //访问链接获取document，并保存里面的图片
                    List<Picture> listPicture = getArticleInfo(homeUrl + beautifulPictures.getUrl(), beautifulPictures);
                    for (Picture picture : listPicture) {
                        //入库
                        pictureService.insert(picture);
                    }
                } else {
                    System.out.println(homeUrl + beautifulPictures.getUrl() + "页面数据已经爬过了！！");
                }
            }
        } else {
            System.out.println("爬取到" + page + "页时没有数据了！！");
            result = 0;
        }
        return result;
    }

    String local = "d:/test";

    // 获取网站的document对象,通过jsoup获取图片链接并保存到本地
    public List<Picture> getArticleInfo(String url, BeautifulPictures beautifulPictures) {
        try {
            Connection connect = Jsoup.connect(url);
            Document document = connect.get();
            Element article = document.getElementById("mkPic");
            Elements a = article.getElementsByTag("img");
            List<Picture> listPicture = new ArrayList<>();
            if (a.size() > 0) {
                for (Element e : a) {
                    String url2 = e.attr("src");
                    // 下载img标签里面的图片到本地
                    CrawlerUtil.saveToFile(url2, local);
                    Picture picture = new Picture();
                    picture.setPicturesId(beautifulPictures.getId());
                    picture.setUrl(url2);
                    listPicture.add(picture);
                }
            }
            System.out.println("页面图片地址list获取成功，页面地址为：" + url);
            return listPicture;
        } catch (IOException e) {
            System.err.println("访问图片集合页失败:" + url + "  原因" + e.getMessage());
            return null;
        }
    }


}
