package vn.com.vsc.ptpm.VNPT_DMS.common;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;

/* mAF : My Android Functions. Các hàm tiện ích cho Android
 * Trần Mạnh Cường, bổ sung 10/5/2016
 * Tel: 091 322 1234
 * 
 * 016: String formatNumber(String frmType, String strNumber) : format dữ liệu dạng số về dạng ###,###.#
 * 015: String genJsonData(String act, String ftbl, String flist, String fvalue) : Tạo chuỗi json để gửi lên thực hiện DoAction
 * 014: boolean init4Log(Context ctx, String file1) : Chuẩn bị cho việc ghi log. file1=/Log/log_vnptdms.txt
 * 013: void writelog(final String note1, Context ctx, String file1) : ghi log vào file file1.
 * 012: boolean createFileExternalStore(String file1) : Tạo file trên thẻ nhớ. ví dụ file1=/Log/log_vnptdms.txt
 * 011: boolean chkFileExternalStore(String file1) : Kiểm tra file trên thẻ nhớ có hay không. ví dụ file1=/Log/log_vnptdms.txt
 * 010: boolean isExternalStorageWritable() : Kiểm tra thẻ nhớ (bộ nhớ ngoài) có thể ghi vào được không
 * 009: String getDataDir(Context context): Lấy đường dẫn cài ứng dụng
 * 008: String getDistanceStr(String distance) Lấy khoảng cách, có quy đổi ra m, km. Lọc các kết quả khi không lấy được lat, long của NVKD
 * 007: void getLatLongCurrent(Context ctx) : Lấy giá trị Lat, Long hiện tại của thiết bị 
 * 006: String getURLDistance(String latlongOrigins, String latlongDest) : Tạo URL đo khoảng cách
 * 005: int dpToPx(int dp, Context ctx): Đổi đơn vị từ DP sang PX (Có hàm không cần Context)
 * 004: int pxToDp(int px, Context ctx): Đổi đơn vị từ PX sang DP (Có hàm không cần Context)
 * 003: void setSpinnerSize(Spinner obj, int hdp, int wdp): Thiết lập độ rộng cho Spinner
 * 002: void setEditTextSize(EditText myEdt, int hdp, int wdp): Thiết lập độ rộng cho EditText
 * 001: void setButtonSize(Button myButton, int hdp, int wdp): Thiết lập độ rộng cho Button theo DP
 */


public class mAF {
	//Độ cao, độ rộng màn hình tính bằng dp
	public static float dpHeight;
	public static float dpWidth;
	public static float density;
	public static float maxHeight = 400;  //Độ cao tối đa để hiểu đấy là Smartphone, không phải Tablet
	public static String keyDistance = "AIzaSyDz4cSLPGYPOvfr4UwHRCT8h780y6LFbXM"; //Key cho serach khoảng cách, của rdp4you@gmail.com
	public static double latCurrent;
	public static double longCurrent;
	public static int isDistance; //Xác nhận đã lấy khoảng cách hay chưa
	public static String sGlobal = "";
	public static String sTmp = ""; //Gia tri tmp global, su dung o cac form

	public static Boolean ghilog = false; //Cờ xác nhận có ghi log hay không
	public static final  String filelog = "/Log/log_vnptdms.txt"; //File để ghi log
	public static Boolean isPhone = false; //Cờ xác nhận có phải là phone 4,5 inch hay không

	public static int rowClick = -1; //Vi tri dong da click o Tuyen, su dung de doi mau
	public static String Route_assign_id; //ID tuyen duoc gan cho nhan vien, luu lai de dua vao don hang pos_po.Route_assign_id

	public static float maxDistance = 0; //Khoang cach toi da de co the checkin duoc, don vi la m
	public static float nStopCust = 0; //So thang ma khach hang se chuyen sang trang thai sang STOP
	public static String act = ""; //Flag de chi 1 hoat dong nao do



	public static String sendPost(String url, String postParams, Context ctx) {
		String kq = "";

		try{
			//
			CookieManager cookieManager = new CookieManager();
			CookieHandler.setDefault(cookieManager);

			HttpURLConnection conn;
			URL obj = new URL(url);

			Log.d("post 1", url);

			conn = (HttpURLConnection) obj.openConnection();

			Log.d("post 2", "OK");

			// Acts like a browser
			conn.setUseCaches(false);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));
			//ok

			conn.addRequestProperty("Cookie","");
			conn.setInstanceFollowRedirects(true); //Quan trong, server chuyen cho page khac xu ly


			conn.setDoOutput(true);
			conn.setDoInput(true);

			//OK

			// Send post request
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			Log.d("post 3", "OK");


			wr.writeBytes(postParams);
			wr.flush();
			wr.close();

			Log.d("post 4", "OK");

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			kq = response.toString();
			Log.d("post result", kq);
			mAF.writelog("sendPost ok: "+ url + ". Result=" + kq, ctx, mAF.filelog);
		}catch (Exception e)
		{
			Log.d("post result error: " + e.toString(), kq);
			mAF.writelog("Error sendPost: "+ url, ctx, mAF.filelog);
			kq = "Error " + e.toString();
		}
		return kq;
	}


	/**
	 * Định dạng số: Nếu chuỗi đưa vào là dạng số thì đưa ra định dạng, còn không giữ nguyên
	 * @frmType: Kiểu định dạng. Ví dụ ###,###.# : nhóm 3 số ngăn cách bởi dấu phẩy, có 1 chữ số thập phân ở sau
	 * @strNumber: Số cần định dạng
	 */
	public static String formatNumber(String frmType, String strNumber) {
		String kq = "";
		Context ctx = null;
	    try{
	    	DecimalFormat myFormatter = new DecimalFormat(frmType);
	    	if (strNumber == null)
	    	{
	    		kq = "";
	    	}else
	    	{
	    		if (isNumeric(strNumber))
		    	{
		    		Double d = Double.parseDouble(strNumber);
		    		kq = myFormatter.format(d);
		    	}else
		    	{
		    		kq = strNumber;
		    	}
	    	}

	    	Log.d("cuong formatNumber: ", kq);
	    	mAF.writelog("Error formatNumber: no error" , ctx , mAF.filelog);
	    }
	    catch (Exception e)
		{
	    	mAF.writelog("Error formatNumber: " + e.toString(), ctx , mAF.filelog);
			kq = e.toString();
		}
	    return kq;
	}

	/**
	 * Check điều kiện để những khi chưa update thay đổi lên server chính thức thì vẫn test được
	 */
	public static Boolean chkDieukien() {
		Boolean kq = false;
	    try{
	    	if (!API_code.URL_API_ROOT.contains("neo")){
	    		// && !API_code.URL_API_ROOT.contains("vnptsoftware")
	    		kq = true;
	    	}
	    	return kq;
	    }
	    catch (Exception e)
		{
	    	Log.d("cuong eeror chkDieukien", e.toString());
			kq = false;
		}
	    return kq;
	}

	/**
	 * Tạo chuỗi định dạng json để gửi lên hàm doact trên oracle
	 */
	public static String genJsonData(String act, String ftbl, String flist, String fvalue) throws Exception {
	    try{
	    	String kq = "";

	    	/**
	    	kq = "\"act\":\"" + act + "\"";
	    	kq = kq + ",\"ftbl\":\"" + ftbl + "\"";
	    	kq = kq + ",\"flist\":\"" + flist + "\"";
	    	kq = kq + ",\"fvalue\":\"" + fvalue + "\"";
	    	kq = kq + "";
	    	**/

	    	/**
	    	kq = "%22act%22:%22" + act + "%22";
	    	kq = kq + ",%22ftbl%22:%22" + ftbl + "%22";
	    	kq = kq + ",%22flist%22:%22" + flist + "%22";
	    	kq = kq + ",%22fvalue%22:%22" + fvalue + "%22";
	    	**/
	    	kq = "&act=" + act;
	    	kq =  kq + "&f1=" + ftbl;
	    	kq =  kq + "&f2=" + flist;
	    	kq =  kq + "&f3=" + fvalue;

	    	return kq;
	    }
	    catch (Exception e)
		{
			return e.toString();
		}
	}


	/**
	 * Chuẩn bị cho việc ghi log. Tạo thư mục Log ở thẻ nhớ, ...
	 * @file1: File đưa vào để tạo, ví dụ file1=/Log/log_vnptdms.txt
	 */
	public static boolean init4Log(Context ctx, String file1){
		/**
		 * Tạo ra các file cần thiết: log, config khi chạy lần đầu tiên
		**/

		boolean kq = false;
		String f1 = "";
		try{

			if (isExternalStorageWritable()){
				//Có thẻ nhớ
				//Tao file log
				if (!chkFileExternalStore(file1)){
					createFileExternalStore(file1);
				}
				/*
				//Tao file cofig
				f1 = "/Config/cfg_vnptdms.txt";
				if (!chkFileExternalStore(f1)){
					createFileExternalStore(f1);
				}
				*/
				kq = true;
			}
			else
			{
				/*
				//Khong co the nho	
				//Tao file log
				f1 = "/Log/log_checkserver.txt";
				if (!chkFileInternalStore(f1, ctx)){
					createFileInternalStore(f1, ctx);
				}
				//Tao file cofig
				f1 = "/Config/cfg_checkserver.txt";
				if (!chkFileInternalStore(f1, ctx)){
					createFileInternalStore(f1, ctx);
				}
				*/
				kq = true;
			}
		}
		catch (Exception e)
		{
			kq = false;
		}
		return kq;
	}

	/**
	 * Ghi dữ liệu vào file text trên thẻ nhớ. Nếu dung lượng lớn 1Mb thì xóa
	 * @note1: Dữ liệu cần ghi vào file
	 * @file1: File và đường dẫn trên thẻ nhớ. ví dụ /Log/log_vnptdms.txt
	 */
	public static void writelog(final String note1, Context ctx, String file1) {
		if (ghilog) {
			//file1 = "/Log/log_vnptdms.txt"
			FileWriter f;
			String f1 ="";
			try {
			 //String sdcard=Environment.getExternalStorageDirectory().getAbsolutePath()+"/myfile.txt";
			 //Environment.getExternalStorageDirectory().getAbsolutePath()+
			 if (isExternalStorageWritable()) {
				 Calendar c = Calendar.getInstance();
				 SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				 String formattedDate = df.format(c.getTime());

				 f1 = Environment.getExternalStorageDirectory().getAbsolutePath() + file1;
				 f = new FileWriter(f1, true);
				 f.write(formattedDate + ": " + note1 + "\n");
				 f.flush();
				 f.close();

				 File file = new File(f1);
				 long dungluong = file.length();
				 if (dungluong >= 1024*1000){
					 file.delete();
				 }
			 }
			} catch (Exception e){

			}
		}
	}

	/**
	 * Tạo file trên thẻ nhớ
	 * @file1: File và đường dẫn trên thẻ nhớ. ví dụ /Log/log_vnptdms.txt
	 */
	public static boolean createFileExternalStore(String file1){
		boolean kq = false;
		try{
			FileWriter f;
			//file1 = "/Log/log_checkserver.txt"
			String[] a = file1.split("/");
			String f1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + a[1];
		    File documentsFolder = new File(f1);
		    if (documentsFolder.exists()){
		    	kq = true;
		    }else
		    {
		    	kq = documentsFolder.mkdir();
		    	f1 = Environment.getExternalStorageDirectory().getAbsolutePath() + file1;
				 f = new FileWriter(f1, true);
				 f.write("" + "\n");
				 f.flush();
				 f.close();
		    }

		}
		catch (Exception e)
		{
			kq = false;
		}
		return kq;
	}

	/**
	 * Kiểm tra file có trên thẻ nhớ hay không
	 * @file1: File và đường dẫn trên thẻ nhớ. ví dụ /Log/log_vnptdms.txt
	 */
	public static boolean chkFileExternalStore(String file1){
		boolean kq = false;
		try{
			//file1 = "/Log/log_checkserver.txt"
			String f1 = Environment.getExternalStorageDirectory().getAbsolutePath() + file1;
		    File documentsFolder = new File(f1);
		    if (documentsFolder.exists()){
		    	kq = true;
		    }else
		    {
		    	kq = false;
		    }
		}
		catch (Exception e)
		{
			kq = false;
		}
		return kq;
	}

	/**
	 * Kiểm tra thẻ nhớ (bộ nhớ ngoài) có thể ghi vào được không
	 */
	public static boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}


	/**
	 * Lấy đường dẫn cài đặt app. Thường là \data\data\{package_app}\
	 */
	public static String getDataDir(Context context) throws Exception {
	    try{
	    	 return context.getPackageManager()
	 	            .getPackageInfo(context.getPackageName(), 0)
	 	            .applicationInfo.dataDir;
	    }
	    catch (Exception e)
		{
			return e.toString();
		}
	}


	/**
	 * Lấy khoảng cách, có quy đổi ra m, km. Lọc các kết quả khi không lấy được lat, long của NVKD
	 * @distance : string khoảng cách đưa vào
	 **/
	public static String getDistanceStr(String distance) {
		String result = "";
		distance = distance.replace("m","");
		distance = distance.replace(" ","");

		if (mAF.isNumeric(distance))
		{
			DecimalFormat f = new DecimalFormat("#.##");
		    //System.out.println(f.format(d));
			double d = Double.parseDouble(distance);
			if (d >= 1000)
			{
				d = d/1000;
				if (d >10000)
				{
					result = ""; //Lọc các kết quả khi không lấy được lat, long của NVKD, lúc đó
					//lat,long=0,0 thì khoảng cách ra hơn 11 ngàn km
				}else
				{
					result = f.format(d) + "km";
				}
			}else
			{
				if (d < 0 )
				{
					d = 0;
				}
				result = f.format(d) + "m";
			}
		}else
		{
			result = "";
		}
		//Log.i("cuong getDistanceStr", result + "-" + distance);
		return result;
	}

    /**
     * Kiểm tra xem có phải là dạng số hay không
     * @str - Chuỗi đưa vào để kiểm tra
     * */
	public static boolean isNumeric(String str)
	{
	  try
	  {
	    double d = Double.parseDouble(str);
	  }
	  catch(NumberFormatException nfe)
	  {
	    return false;
	  }
	  return true;
	}

    /**
     * Kiểm tra xem có phải null hay không
     * @strIN - Địa chỉ Lat và Long của điểm đầu theo định dạng lat,long
     * */
	public static Boolean chkNUll(String strIN) {
		Boolean kq = false;
		if (strIN != null && !strIN.isEmpty() && !strIN.equals("null"))
		{
			kq = true;
		}
		return kq;
	}

    /**
     * Lấy giá trị Lat, Long hiện tại của thiết bị
     * @ctx - Context của chương trình
     * */

	public static void getLatLongCurrent(Context ctx) {
		// Get current location
		boolean isFirstLogin = true;
		GPSTracker gpsTracker;
		gpsTracker = new GPSTracker(ctx);
		if (gpsTracker.canGetLocation()) {
//			if(gpsTracker.getLatitude() != 0 && gpsTracker.getLongitude() != 0){
//				latCurrent = gpsTracker.getLatitude();
//				longCurrent = gpsTracker.getLongitude();
//			}

		}
	}

    /**
     * Tạo chuỗi URL để đo khoảng cách bằng Google API (Bị giới hạn 2.500 hit / ngày)
     * @latlongOrigins - Địa chỉ Lat và Long của điểm đầu theo định dạng lat,long
     * @latlongDest - Địa chỉ Lat và Long của các điểm cần đo. Theo định dạng: lat1,long1|lat2,long2 ...
     * */
	public static String getURLDistance(String latlongOrigins, String latlongDest) {
		String url1 = "";
		latlongDest = latlongDest.trim();
		latlongOrigins = latlongOrigins.trim();
		if (latlongOrigins.contains("0,0"))
		{
			return "";
		}
		if (latlongDest.contains("0,0"))
		{
			return "";
		}

		url1 = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + latlongOrigins;
		url1 = url1 + "&destinations=" + latlongDest;
		url1 = url1 +"&key=" + keyDistance;
		return url1;
	}

	/**
	 * Thiết lập độ rộng, độ cao cho button, đơn vị là dp (Nếu truyền vào 0 thì sẽ giữ nguyên kích thước)
	 * @param myButton
	 * @param hdp
	 * @param wdp
	 */
	public static void setButtonSize(Button myButton, int hdp, int wdp) {
		ViewGroup.LayoutParams params = myButton.getLayoutParams();
		if (wdp >=1)
		{
			params.width = (int) (wdp * density + 0.5f);
		}
		if (hdp >=1)
		{
			params.height = (int) (hdp * density + 0.5f);;
		}
	    myButton.setLayoutParams(params);
	}

	/**
	 * Thiết lập độ rộng, độ cao cho TextEdit, đơn vị là dp (Nếu truyền vào 0 thì sẽ giữ nguyên kích thước)
	 * @param myEdt : EditText
	 * @param hdp
	 * @param wdp
	 */
	public static void setEditTextSize(EditText myEdt, int hdp, int wdp) {
		ViewGroup.LayoutParams params = myEdt.getLayoutParams();
		if (wdp >=1)
		{
			params.width = (int) (wdp * density + 0.5f);;
		}
		if (hdp >=1)
		{
			params.height = (int) (hdp * density + 0.5f);
		}
		myEdt.setLayoutParams(params);
	}

	/**
	 * Thiết lập độ rộng, độ cao cho CheckBox, đơn vị là dp (Nếu truyền vào 0 thì sẽ giữ nguyên kích thước)
	 * @param obj : CheckBox
	 * @param hdp
	 * @param wdp
	 */
	public static void setCheckboxSize(CheckBox obj, int hdp, int wdp) {
		ViewGroup.LayoutParams params = obj.getLayoutParams();
		if (wdp >=1)
		{
			params.width = (int) (wdp * density + 0.5f);;
		}
		if (hdp >=1)
		{
			params.height = (int) (hdp * density + 0.5f);
		}
		obj.setLayoutParams(params);
	}

	/**
	 * Thiết lập độ rộng, độ cao cho Spinner, đơn vị là dp (Nếu truyền vào 0 thì sẽ giữ nguyên kích thước)
	 * @param obj : Spinner
	 * @param hdp
	 * @param wdp
	 */
	public static void setSpinnerSize(Spinner obj, int hdp, int wdp) {
		ViewGroup.LayoutParams params = obj.getLayoutParams();
		if (wdp >=1)
		{
			params.width = (int) (wdp * density + 0.5f);;
		}
		if (hdp >=1)
		{
			params.height = (int) (hdp * density + 0.5f);
		}
		obj.setLayoutParams(params);
	}

	//Đổi từ px thành dp
	public static int pxToDp(int px, Context ctx) {
	    DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
	    int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	    return dp;
	}
	public static int pxToDp(int px) {
	    int dp = Math.round((px-0.5f)/density);
	    return dp;
	}

	//Đổi từ dp thành px
	public static int dpToPx(int dp, Context ctx) {
	    DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	    return px;
	}
	public static int dpToPx(int dp) {
	    int px = Math.round(dp * density + 0.5f);
	    return px;
	}


	//Code viết trên dòng này
}
