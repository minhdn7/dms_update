package vn.com.vsc.ptpm.VNPT_DMS.model.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.vsc.ptpm.VNPT_DMS.common.ErrorDef;
import vn.com.vsc.ptpm.VNPT_DMS.config.TimeDef;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.HeaderAPI;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.APIError;

/**
 * Created by MinhDN on 2/10/2017.
 */

public class BaseService {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Gson gson = new GsonBuilder().setLenient().create();
    private static Retrofit.Builder builder = null;

    public static <S> S createServiceDMS(Class<S> serviceClass, final HeaderAPI headerAPI) {
        builder = new Retrofit.Builder().baseUrl(API_code.URL_API_ROOT).addConverterFactory(GsonConverterFactory.create(gson));
        httpClient.connectTimeout(TimeDef.CONNECTION_TIMEOUT, TimeUnit.SECONDS).readTimeout(TimeDef.READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout(TimeDef.WRITE_TIMEOUT, TimeUnit.SECONDS);
        if (headerAPI != null) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder();
                    requestBuilder.addHeader(headerAPI.getKey(), headerAPI.getValue());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceDMS(Class<S> serviceClass) {
        builder = new Retrofit.Builder().baseUrl(API_code.URL_API_ROOT).addConverterFactory(GsonConverterFactory.create(gson));
        httpClient.connectTimeout(TimeDef.CONNECTION_TIMEOUT, TimeUnit.SECONDS).readTimeout(TimeDef.READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout(TimeDef.WRITE_TIMEOUT, TimeUnit.SECONDS);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder();
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static <T> APIError parseErrorHandler(Response<T> response) {
        Converter<ResponseBody, APIError> converter =
                builder.client(httpClient.build()).build().responseBodyConverter(APIError.class, new Annotation[0]);
        APIError error;
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError(0, ErrorDef.MESSAGE_RESPONSE);
        }
        return error;
    }
}
