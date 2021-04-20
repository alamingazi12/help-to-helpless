package com.example.help2helpless.network;

import android.content.Context;

import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static retrofit2.CallAdapter.*;

public class ApiClient {
    public ApiClient(){


    }

    public  static String baseUrl="https://apps.help2helpless.com/";
    public int CacheSize=5*1024*1024;
    public static Retrofit retrofit,retrofit2=null;

    public static Retrofit getApiClient(Context context){


        if(retrofit==null){

            retrofit=new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            retrofit2=new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

        }
        return retrofit;
    }

}
