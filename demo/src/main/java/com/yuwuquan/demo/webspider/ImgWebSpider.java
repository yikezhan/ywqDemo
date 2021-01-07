package com.yuwuquan.demo.webspider;

import com.yuwuquan.demo.util.common.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


//<ul><li><a href="https://m.k886.net/look/name/拳愿奧米迦/cid/38657/id/498632" title="拳愿奧米迦083集">083集<i class="new-icon"></i></a></li><li><a href="https://m.k886.net/look/name/拳愿奧米迦/cid/38657/id/497589" title="拳愿奧米迦082集">082集
public class ImgWebSpider {
    public static void main(String[] args) {
        try {
            /**
             * 1、根网址，解析章节信息
             */
            String url="https://m.k886.net/comic/name/%E6%95%99%E6%8E%88%E4%BD%A0%E9%82%84%E7%AD%89%E4%BB%80%E9%BA%BC/id/47155";
            Document document = getDocument(url);
            Elements elements = document.getElementsByClass("chapter-list").first().select("a");
            int start = 0;//开始章节(0开始)
            int end = 20;//结束章节
            for(int index =0; index < elements.size() && index<=end;index++){//遍历每一章节
                if(index < start) continue;
                Element element = elements.get(index);
                //章节名
                String dirName = element.attr("title")
                        .replaceAll(" ","")
                        .replaceAll("-","")
                        .replaceAll("，","")
                        .replaceAll("\\?","")
                        .replaceAll("\\.","")
                        .replaceAll("…","");
                /**
                 * 2、具体章节面，循环遍历页码信息
                 */
                String pageUrl = element.attr("href");
                int pageNum = 0;
                while(pageUrl.contains("https")){
                    pageNum++;
                    /**
                     * 3、解析具体页码内的信息
                     */
                    Document pageDocument = getDocument(pageUrl);
                    if(pageDocument == null) break;
                    Elements imgElement = pageDocument.select("img");
                    downloadPageImage(dirName, imgElement, pageNum);
                    try{
                        pageUrl = pageDocument.getElementsByClass("action-list").select("a").get(2).attr("href");
                    } catch (Exception e){
                        System.out.println("该页面出错，pageUrl=" + pageUrl);
                        e.printStackTrace();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document getDocument(String url){//获取页面
        Document document = null;
        boolean res = true;
        int retryTime = 0;
        while (res  &&  retryTime<3){
            try {
                //   url = "https://www.baidu.com";
                setProxy();
                URL postUrl = new URL(url);
                URLConnection connection = postUrl.openConnection();
                HttpsURLConnection httpsCon = (HttpsURLConnection) connection;
                document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);
                res = false;
            } catch (Exception e) {
                System.out.println("重试" + (++retryTime) + "次" + url);
            }
        }
        return document;
    }

    /**
     * vpn则需要加这一段。
     * https://www.cnblogs.com/borter/p/9617178.html  固定的代理域名配置的话
     * https://www.cnblogs.com/littleatp/p/4729781.html  使用翻墙软件，自动使用系统的代理
     */
    private static void setProxy() {
        System.setProperty("java.net.useSystemProxies", "true");//使用系统代理
//        String proxyHost = "xxx.xxxxx.com";
//        int proxyPort = 8080;
//        // 设置系统变量
//        System.setProperty("http.proxySet", "true");
//        System.setProperty("http.proxyHost", proxyHost);
//        System.setProperty("http.proxyPort", "" + proxyPort);
//
//        // 针对https也开启代理
//        System.setProperty("https.proxyHost", proxyHost);
//        System.setProperty("https.proxyPort", "" + proxyPort);
        // 设置默认校验器
//        BasicAuthenticator auth = new BasicAuthenticator(proxyUser, proxyPass);
//        Authenticator.setDefault(auth);
    }

    private static void downloadPageImage(String dirName, Elements imgElement, int pageNum){
        for(int imagNum=0; imagNum<imgElement.size(); imagNum++){
            if(StringUtil.isNotBlank(imgElement.get(imagNum).attr("alt"))){
                String src = imgElement.get(imagNum).attr("src");
                if(src.contains("jpg")){
                    System.out.println(src);
                    try {
                        (new ImgWebSpider()).download(src,"jpg","d:\\image\\"+dirName+"\\",formateNum(pageNum)+"_"+formateNum(imagNum)+"");
                    } catch (Exception e) {//当前页面图片下载失败，略过
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private static String formateNum(int n){
        if(n<10) return "00"+n;
        if(n<100) return "0"+n;
        return ""+n;
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
