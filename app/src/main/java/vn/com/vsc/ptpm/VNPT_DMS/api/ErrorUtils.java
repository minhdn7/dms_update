package vn.com.vsc.ptpm.VNPT_DMS.api;
//package longpd.neo.vn.fansipan.api;
//
//import java.io.IOException;
//import java.lang.annotation.Annotation;
//
//import longpd.neo.vn.fansipan.model.NeoResponse;
//import okhttp3.ResponseBody;
//import retrofit2.Converter;
//import retrofit2.Response;
//
///**
// * Created by LONGPD on 4/26/2016.
// */
//public class ErrorUtils {
//    public static NeoResponse parseError(Response<?> response) {
//        Converter<ResponseBody, NeoResponse> converter =
//                ServiceGenerator.retrofit()
//                        .responseBodyConverter(NeoResponse.class, new Annotation[0]);
//
//        NeoResponse error;
//
//        try {
//            error = converter.convert(response.errorBody());
//        } catch (IOException e) {
//            return new NeoResponse();
//        }
//
//        return error;
//    }
//}
