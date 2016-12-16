package com.base.simple.test.reptile;

import com.base.simple.common.CommonTest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ReptileTest
 * @author zhouyw
 * @date 2016.12.15
 */
public class ReptileTest extends CommonTest {

    private String BAIDU_URL = "http://www.baidu.com";
    private String ZHIHU_URL = "https://www.zhihu.com";
    private String myCookie;

    @Test
    public void getUrlContent() {
        // 定义即将访问的链接
        String url = BAIDU_URL;

        // 访问链接并获取页面内容
        String result = getContentFromUrl(url);
        logger.info("result={}", result);

        //过滤目标内容
        String regex = "(?<=href=http[s]?://).*?(?=[>\\s;'\"])";
        Set<String> set  = match(result, regex);
        logger.info("-->size={},set={}",set.size(), set);
    }

    @Test
    public void getContentFromZhiHu() {

    }
    /*作者：卧颜沉默
    链接：https://www.zhihu.com/question/36909173/answer/97643000
    https://github.com/wycm/zhihu-crawler.git
    来源：知乎
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/
    /**
     *
     * @param httpClient Http客户端
     * @param context Http上下文
     * @return
     */
    private boolean login(CloseableHttpClient httpClient, HttpClientContext context){
       return false;//TODO
    }


    @Test
    public void reptileCircle(){

    }


    @Test
    public void testMatch(){
        String source = "abdcefg123abec";
        String regex = "ab[\\w]*c";
        Set<String> set  = match(source, regex);
        logger.info("-->set={}", set);
    }


    /* -----private method spilt----- */

    private Map<String,List<String>> reptileCircleFromUrl(String url,Map<String,List<String>> contentMap,Set<String> targetCircleSet){
        if(contentMap==null){
            contentMap = new HashMap<String,List<String>>();
        }
        if(targetCircleSet==null){
            targetCircleSet = new HashSet<String>();
            targetCircleSet.add(url);
        }

        for (String tempUrl : targetCircleSet) {
            while(!contentMap.containsKey(url)){
                List<String> target = new ArrayList<String>();
                String content = getContentFromUrl(url);

                List<String> urlList = new ArrayList<String>();
//              circleMap.put(url,target);
            }
        }
        return contentMap;
    };

    /**
     * 从源串中获取目标信息
     * @param source 源文
     * @param regex  正则表达
     * @return
     */
    private Set<String> match(String source,String regex){
        Set<String> set = new HashSet<String>();
        logger.info("match-->source={},regex={}", source,regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while(matcher.find()){
            int str = matcher.start();
            int end = matcher.end();
            String target = source.substring(str, end);
            if(set.contains(target)){
                continue;
            }
            set.add(target);
            logger.info("-->str={},end={},target={}", str,end, target);

        }
        return set;
    }

    private String getContentFromUrl(String url) {
        if(StringUtils.isEmpty(url)){
            logger.info("empty-->url={}", url);
            return "";
        }
        if(url.indexOf("http://")==-1 && url.indexOf("https://")==-1){
            url = "http://" + url;
        }
        // 定义一个字符串用来存储网页内容
        String result = "";
        // 定义一个缓冲字符输入流
        BufferedReader in = null;
        try {
            logger.info("read-->url={}", url);
            // 将string转成url对象
            URL realUrl = new URL(url);
            // 初始化一个链接到那个url的连接
            URLConnection connection = realUrl.openConnection();
            // 开始实际的连接
            connection.connect();
            // 初始化 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null) {
                // 遍历抓取到的每一行并将其存储到result里面
                result += line + "\n";
            }
        } catch (Exception e) {
          logger.error("error:发送GET请求出现异常-->e={}", e,e);
        } // 使用finally来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }




}
