package com.example.help2helpless.network;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public  static String baseUrl="https://apps.help2helpless.com/";
    public int CacheSize=5*1024*1024;
    public static Retrofit retrofit=null;

    public static Retrofit getApiClient(Context context){


        if(retrofit==null){

            retrofit=new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
