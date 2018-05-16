package vn.com.vsc.ptpm.VNPT_DMS.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class DownloadURLData extends AsyncTask<String, Integer, String> {
	@Override
	protected String doInBackground(String... params) {
	    String line;
	    try {
	    	
	    	//Thiet lap timout khi ket noi lay du lieu
	    	HttpParams httpParameters = new BasicHttpParams();
	    	// Set the timeout in milliseconds until a connection is established.
	    	// The default value is zero, that means the timeout is not used. 
	    	int timeoutConnection = 10000;
	    	HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
	    	// Set the default socket timeout (SO_TIMEOUT) 
	    	// in milliseconds which is the timeout for waiting for data.
	    	int timeoutSocket = 10000;
	    	HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	    	
	        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
	        HttpPost httpPost = new HttpPost(params[0]);
	        
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        line = EntityUtils.toString(httpEntity);

	    } catch (UnsupportedEncodingException e) {
	        line = "Không lấy được dữ liệu\n";
	    } catch (MalformedURLException e) {
	        line = "Không lấy được dữ liệu\n";
	    } catch (IOException e) {
	        line = "Không lấy được dữ liệu\n";
	    } catch (Exception e){
	    	line = "Không lấy được dữ liệu\n";
	    }
	    
	    return line;
	}

	@Override
    protected void onPreExecute() {
        //Dialog.setMessage("Đang thực hiện ...");
        //Dialog.show();
    }
    
    
	@Override
	protected void onPostExecute(String result) {
		//Dialog.dismiss();
	    super.onPostExecute(result);
	}
}
