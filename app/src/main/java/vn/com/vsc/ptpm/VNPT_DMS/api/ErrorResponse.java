package vn.com.vsc.ptpm.VNPT_DMS.api;

/**
 * Created by LONGPD on 4/26/2016.
 */
public class ErrorResponse {
    Error error;

    public class Error {
        Data data;

        public class Data {
            String message;
        }
    }
}