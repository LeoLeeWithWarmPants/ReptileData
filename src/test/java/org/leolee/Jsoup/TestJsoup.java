package org.leolee.Jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestJsoup {

    private static String URL = "http://search.ccgp.gov.cn/bxsearch?" +
        "searchtype=1&page_index=1&bidSort=0&buyerName=&projectId=&pinMu=0&bidType=0&dbselect=bidx&kw=&" +
        "start_time=2018%3A03%3A21&end_time=2018%3A03%3A28&timeType=2&displayZone=&zoneId=&pppStatus=0&agentName=";

    private static String infoURL = "http://www.ccgp.gov.cn/cggg/dfgg/jzxcs/201804/t20180401_9732462.htm";

    private static StringBuffer  fileNameWithPath = new StringBuffer();

    private static String currentDayPageURL= "http://search.ccgp.gov.cn/bxsearch?\n" +
            "searchtype=1" +
            "&page_index=:index" +
            "&bidSort=0" +
            "&buyerName=" +
            "&projectId=" +
            "&pinMu=0" +
            "&bidType=0" +
            "&dbselect=bidx" +
            "&kw=" +
            "&start_time=:startTime" +
            "&end_time=:endTime" +
            "&timeType=0" +
            "&displayZone=" +
            "&zoneId=" +
            "&pppStatus=" +
            "0&agentName=";

    private static final String dataLabelClassName = "vT-srch-result-list-bid";

    private static final String PagerClass = "pager";

    public static void getStationHtmlText(String URL){
        try {
            Document doc = Jsoup.connect(URL).userAgent("Mozilla").get();
            Element element = doc.body();
            String bodyHTML = element.html();
            System.out.println(bodyHTML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static Element getPageBodyElement(String url) throws IOException {
        Document doc = Jsoup.connect(URL).userAgent("Mozilla").get();
        Element element = doc.body();
        return element;
    }

    private List<String> getAllPageALabelURL(){
        TestJsoup tj = new TestJsoup();
        int MaxPage = tj.getMaxPage();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        for(int i = 1; i <= MaxPage; i++){
            tj.synthesizeAllURL(getCurrentDayAllURL(String.valueOf(i), sdf.format(d)));
        }
        return URLArraySingleton.getUrl();
    }


    public void synthesizeAllURL(List<String> URLArray){
        for(String url : URLArray){
            URLArraySingleton.getUrl().add(url);
        }
    }


    private static List<String> getDayURL(String URL){
        try {
            Element element = getPageBodyElement(URL);
            Elements ulElement = element.getElementsByClass(dataLabelClassName).select("a");
            List<String> hrefURL = new ArrayList<String>();
            for (Element e : ulElement){
                hrefURL.add(e.attr("href"));
            }
            return hrefURL;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private static List<String> getCurrentDayAllURL(String index, String currentDay){
        String url = currentDayPageURL.replace(":index", index).replace(":startTime", currentDay).replace("endTime", currentDay);
        return TestJsoup.getDayURL(url);
    }


    private int getMaxPage(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String url = currentDayPageURL.replace(":index", "1").replace(":startTime", sdf.format(d)).replace("endTime", sdf.format(d));
        Element element = null;
        try {
            element = getPageBodyElement(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element maxPageElement = element.getElementsByClass(PagerClass).select("a").get(5);
        return Integer.valueOf(maxPageElement.ownText());
    }





















    public static String createFile(String fileName, String filePath){
        fileNameWithPath.setLength(0);
        fileNameWithPath.append(filePath).append(fileName);
        File file = new File(fileNameWithPath.toString());
        if(!file.exists()){
            try {
                file.createNewFile();
                return fileNameWithPath.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    public static boolean wirteDataToFile(String filePath, String content) throws IOException {
        String tempString = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        StringBuffer sb = new StringBuffer();
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            for (int i = 0; (tempString = br.readLine()) != null; i++) {
                sb.append(tempString);
            }
            sb.append(content);
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(sb.toString().toCharArray());
            pw.flush();
            pw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }  finally {
            pw.flush();
            pw.close();
            fis.close();
            isr.close();
            br.close();
            fos.flush();
            fos.close();
        }
    }

    public static void main(String[] args) {
        TestJsoup.getStationHtmlText(infoURL);
        org.leolee.Jsoup.URLArraySingleton.getInstance();
        System.out.println("hehehhe");
    }
}
