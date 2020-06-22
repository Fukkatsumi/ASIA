package com.fukkatsumi.asiia_mobile;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Objects;

public class HttpService {
    private static HttpService mInstance;
    private static final String BASE_PRIVATE_URL = "http://192.168.1.13:8090";
    private static final String BASE_URL = "http://192.168.1.4:8080";
    private Retrofit mRetrofit;

    private HttpService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_PRIVATE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static HttpService getInstance() {
        if (mInstance == null) {
            mInstance = new HttpService();
        }
        return mInstance;
    }

    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HttpService)) return false;
        HttpService that = (HttpService) o;
        return Objects.equals(mRetrofit, that.mRetrofit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mRetrofit);
    }
}
