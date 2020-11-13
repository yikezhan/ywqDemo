package com.yuwuquan.demo.webspider;

import com.yuwuquan.demo.util.common.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


//<ul><li><a href="https://m.k886.net/look/name/拳愿奧米迦/cid/38657/id/498632" title="拳愿奧米迦083集">083集<i class="new-icon"></i></a></li><li><a href="https://m.k886.net/look/name/拳愿奧米迦/cid/38657/id/497589" title="拳愿奧米迦082集">082集
public class ImgWebSpider {
    public static void main(String[] args) {
        List<String> urls = new ArrayList<>();
        try {
            String url="https://m.k886.net/comic/name/%E6%8B%B3%E6%84%BF%E5%A5%A7%E7%B1%B3%E8%BF%A6/id/38657";
            Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
            Elements elements = document.getElementsByClass("chapter-list").first().select("a");
            for(Element element : elements){
                String url2 = element.attr("href");
                String dirName = element.attr("title");//章节文件夹名字
                Document document2 = Jsoup.parse(new URL(url2).openStream(), "UTF-8", url2);
                Elements elements2 = document.select("img");
                for(int i=0;i<elements2.size();i++){
                    if(StringUtil.isNotBlank(elements2.get(i).attr("alt"))){
                        String src = elements2.get(i).attr("src");
                        if(src.contains("jpg")){
                            System.out.println(src);
                            (new ImgWebSpider()).download(src,".jpg","d:\\image\\"+dirName+"\\",i+"图片");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<String> getUrls(){
        List<String> urls = new ArrayList<>();
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605270562690&di=926a3de2930d206536c9f4ee81b1b69c&imgtype=0&src=http%3A%2F%2Fa4.att.hudong.com%2F22%2F59%2F19300001325156131228593878903.jpg";
        urls.add(url);
        return urls;
    }
    public void pull() throws Exception {
        List<String> urls = getUrls();
        for(int i=0;i<urls.size();i++){
            String extensionName = urls.get(i).substring(urls.get(i).lastIndexOf(".") +     1);
            download(urls.get(i),extensionName,"d:\\image\\",i+"图片");
        }
    }
    private void download(String urlString, String extensionName,String savePath,String newFileName) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5*1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf=new File(savePath);
        if(!sf.exists()){
            sf.mkdirs();
        }
        // 新的图片文件名 = 编号 +"."图片扩展名
        newFileName = newFileName+ "." + extensionName;
        OutputStream os = new FileOutputStream(sf.getPath()+"\\"+newFileName);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

}
