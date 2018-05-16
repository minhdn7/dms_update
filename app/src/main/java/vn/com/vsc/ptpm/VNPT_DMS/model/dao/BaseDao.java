package vn.com.vsc.ptpm.VNPT_DMS.model.dao;

import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.vsc.ptpm.VNPT_DMS.common.ErrorDef;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.APIError;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.BaseService;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;

/**
 * Created by MinhDN on 2/10/2017.
 */

public class BaseDao {
    public static <T> void call(Call<T> call, final IFinishedListener finishedTest) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    finishedTest.onSuccess(response.body());
                } else {
                    try{
                        finishedTest.onError(BaseService.parseErrorHandler(response));
                    }catch (Exception e){
                        Log.e("ERRROR BaseService: ", e.toString());
                    }

                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (t instanceof IOException) {
                    finishedTest.onError(new APIError(0, ErrorDef.MESSAGE_NO_NETWORK));
                } else {
                    finishedTest.onError(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                }
            }
        });
    }
}
