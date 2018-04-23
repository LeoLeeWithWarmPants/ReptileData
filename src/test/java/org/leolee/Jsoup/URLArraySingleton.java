package org.leolee.Jsoup;

import java.util.List;

public class URLArraySingleton {

    private volatile static URLArraySingleton urlArraySingleton;

    private List<String> URL;

    private URLArraySingleton(){

    }

    private URLArraySingleton(List<String> url){
        this.URL = url;
    }

    public static URLArraySingleton getInstance(){
        if(urlArraySingleton == null){
            synchronized (URLArraySingleton.class){
                if(urlArraySingleton == null){
                    urlArraySingleton = new URLArraySingleton();
                }
            }
        }
        return urlArraySingleton;
    }

    public static List<String> getUrl(){
        return getInstance().URL;
    }


}
