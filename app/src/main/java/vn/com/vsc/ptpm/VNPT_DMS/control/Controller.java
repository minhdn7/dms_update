package vn.com.vsc.ptpm.VNPT_DMS.control;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.Time;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.WeekAndDateModel;

public class Controller {
	private Context mContext;
	String date_time = "";
	private EditText editText;
	private TextView textView;
	public Controller(Context context){
		this.mContext = context;
	}

	public String subJSON(String json) {
		String s = json.substring(json.indexOf("[") + 1, json.lastIndexOf("]"));
		return s;
	}

//	public String subJSON2(String json) {
//		String s = json.substring(json.indexOf("(") + 1, json.lastIndexOf(")"));
//		return s;
//	}

	public int daysBetween(String startDate, String endDate) {
		int daysBetween = 0;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();
		try {
			sDate.setTime(df.parse(startDate));
			eDate.setTime(df.parse(endDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (sDate.before(eDate)) {
			sDate.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	public String getDataJSON(String urls, boolean isObject) {
		InputStream inputStream;
		HttpURLConnection httpConn = null;
		String result = null;
		int resCode;
		int numberOfRequest = 0;// Chỉ cho request không quá 3 lần, đến lần thứ 3 tự hiểu là lỗi

		do {
			try {
				//Reset value
				inputStream = null;
				httpConn = null;
				result = "";
				resCode = -1;

				numberOfRequest += 1;
				Log.e("CONCRETE", "URL REQUEST getDataJSON = " + urls);
				URL url = new URL(urls);
				URLConnection urlConn = url.openConnection();
				if (!(urlConn instanceof HttpURLConnection)) {
					throw new IOException("URL is not an Http URL");
				}
				httpConn = (HttpURLConnection) urlConn;
				httpConn.setAllowUserInteraction(false);
				httpConn.setInstanceFollowRedirects(true);
				httpConn.setRequestMethod("GET");
				httpConn.connect();
				try {
					resCode = httpConn.getResponseCode();
				} catch (EOFException e) {
					e.printStackTrace();
				}
				if (resCode == HttpURLConnection.HTTP_OK) {
					inputStream = httpConn.getInputStream();
					if (inputStream != null) {
						result = convertInputStreamToString(inputStream);
						inputStream.close();
					}
				}

			} catch (IOException e) {
				Log.e("ERROR-GET-DATA", e.getMessage());
				e.printStackTrace();
			} finally {
				if (httpConn != null)
					httpConn.disconnect();
			}
		}while (reSignIn(result) && numberOfRequest < 3);


		if (!result.equals("")) {
			if (isObject) {
				result = subJson(result);
			} else {
				result = subJson2(result);
			}
		}

		Log.e("CONCRETE", "RESPONSE getDataJSON = " + result);
		Log.e("CONCRETE", "#############################################################");

		return result;
	}

	// check internet connection
	public boolean isOnline(Context contex) {
		try {
			ConnectivityManager conMgr = (ConnectivityManager) contex.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

			if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;

		// cuong them de luon offline
		// return false;
	}

	public String subJson(String urlJson) {
		String newUrl = (String) urlJson.substring(3, (urlJson.length() - 2));
		return newUrl;
	}

	public String subJson2(String urlJson) {
		String newUrl = (String) urlJson.substring(2, (urlJson.length() - 1));
		return newUrl;
	}

	public String convertStreamToString(InputStreamReader isr) {
		BufferedReader reader = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String convertInputStreamToString(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null) {
			result += line;
		}
		if (null != inputStream) {
			inputStream.close();
		}

		return result;
	}

	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
		Log.e("CONCRETE", "context = " + context);

		if(context != null){
			AlertDialog alertDialog = new AlertDialog.Builder(context).create();
			alertDialog.setTitle(title);
			alertDialog.setMessage(message);
			alertDialog.setIcon((status) ? R.drawable.ic_success : R.drawable.ic_fail);
			alertDialog.setButton("Đóng", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			alertDialog.show();
		}
	}

	public InputStream openHttpConnection(String urlStr) {
		InputStream in = null;
		int resCode = -1;

		try {
			URL url = new URL(urlStr);
			URLConnection urlConn = url.openConnection();

			if (!(urlConn instanceof HttpURLConnection)) {
				throw new IOException("URL is not an Http URL");
			}
			HttpURLConnection httpConn = (HttpURLConnection) urlConn;
			// HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect();

			resCode = httpConn.getResponseCode();

			if (resCode == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}

	public int getTotalPages(String getURL_Info) {
		int i = 0;
		String result = "";
		int numberOfRequest = 0;// Chỉ cho request không quá 3 lần, đến lần thứ 3 tự hiểu là lỗi

		do {
			try {
				numberOfRequest += 1;

				//Reset value
				result = "";

				Log.e("CONCRETE", "URL REQUEST getTotalPages = " + getURL_Info);
				InputStream isInfo = openHttpConnection(getURL_Info);
				result = convertInputStreamToString(isInfo);
			}catch (IOException e){
				e.printStackTrace();
			}
		}while (reSignIn(result) && numberOfRequest < 3);

		if (result != null && !result.equals("")){
			try {
				result = subJson(result.trim());
				Log.e("CONCRETE", "RESPONSE getTotalPages = " + result);
				Log.e("CONCRETE", "#############################################################");
				JSONObject pageInfo = new JSONObject(result);
				// i[0] = Integer.parseInt(pageInfo.optString("nor").toString());// total records
				i = Integer.parseInt(pageInfo.optString("nop"));// total pages
				Log.d("isinfo: ", String.valueOf(i));
			}catch (JSONException e){
				e.printStackTrace();
			}
		}

		return i;
	}

	public NorAndNop getNorNop(String url) {
		String result = "";
		InputStream isInfo = null;
		NorAndNop n = new NorAndNop();
		int numberOfRequest = 0;// Chỉ cho request không quá 3 lần, đến lần thứ 3 tự hiểu là lỗi

		do {
			try {
				numberOfRequest += 1;

				//Reset value
				result = "";
				isInfo = null;

				Log.e("CONCRETE", "URL REQUEST getNorNop = " + url);
				isInfo = openHttpConnection(url);
				result = convertInputStreamToString(isInfo);
			}catch (IOException e){
				e.printStackTrace();
			}
		} while (reSignIn(result) && numberOfRequest < 3);

		if (result != null && !result.equals("")){
			try {
				result = subJson(result.trim());
				Log.e("CONCRETE", "RESPONSE getNorNop = " + result);
				Log.e("CONCRETE", "#############################################################");
				JSONObject pageInfo = new JSONObject(result);
				n.setNor(pageInfo.optString("nor"));
				n.setNop(pageInfo.optString("nop"));
				Log.d("isinfo: ", n.getNop());
			}catch (JSONException e){
				e.printStackTrace();
			}
		}

		return n;
	}

	// from urlString to json string
	public String jsonValues(String urlStr, boolean isObject) {
		InputStream in;
		int resCode;
		String result;
		int numberOfRequest = 0;// Chỉ cho request không quá 3 lần, đến lần thứ 3 tự hiểu là lỗi

		do {
			try {
				numberOfRequest += 1;

				in = null;
				resCode = -1;
				result = "";

				Log.e("CONCRETE", "URL REQUEST "+ numberOfRequest +" jsonValues = " + urlStr);
				URL url = new URL(urlStr);
				URLConnection urlConn = url.openConnection();
				if (!(urlConn instanceof HttpURLConnection)) {
					throw new IOException("URL is not an Http URL");
				}
				HttpURLConnection httpConn = (HttpURLConnection) urlConn;
				httpConn.setAllowUserInteraction(false);
				httpConn.setInstanceFollowRedirects(true);
				httpConn.setRequestMethod("GET");
				httpConn.setConnectTimeout(5000);
				httpConn.setReadTimeout(5000);
				httpConn.connect();
				resCode = httpConn.getResponseCode();

				// Log.d("cuong ket qua goi URL", resCode+"");

				if (resCode == HttpURLConnection.HTTP_OK) {
					in = httpConn.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
					String line = "";
					while ((line = bufferedReader.readLine()) != null) {
						result += line;
					}

					// Log.d("cuong ket qua goi URL", result+"");

				/* Close Stream */
					if (null != in) {
						in.close();
					}
				} else {
					result = "?([{\"api_error\":\"Không kết nối được tới máy chủ. Vui lòng thử lại sau!\",\"error_id\":5}])";
				}
			} catch (IOException e) {
				e.printStackTrace();
				result = "?([{\"api_error\":\"Không kết nối được tới máy chủ. Vui lòng thử lại sau!\",\"error_id\":5}])";
			}
		}while (reSignIn(result) && numberOfRequest < 3);

		if (!result.equals("")) {
			if (isObject) {
				result = subJson(result.trim());
			} else {
				result = subJson2(result.trim());
			}
		}
		Log.e("CONCRETE", "RESPONSE jsonValues = " + result);
		Log.e("CONCRETE", "#############################################################");
		return result;
	}

	private synchronized boolean reSignIn(String response){
		if (response.equals("?([{\"api_error\":\"Ban can dang nhap vao he thong\",\"error_id\":3}])")){
			Log.e("CONCRETE", "Lỗi hết SESSION, Đăng nhập lại");

			//Lấy thông tin user password
			String username = null;
			String password = null;
			SQLiteData sqlite = new SQLiteData(mContext);
			sqlite.createTable();
			String[] s = sqlite.getAccount();
			if (s != null) {
				if (s[2].equals("true")) {
					username = s[0];
					password = s[1];
				}
			}

			if (username == null || password == null){
				//Quay trở lại, lỗi không lấy được username, password
				Log.e("CONCRETE", "Lỗi không lấy được username, password");
			}

			InputStream in = null;
			int resCode = -1;
			String result = "";
			String urlStr = API_code.URL_LOGIN + "&username=" + username
					+ "&password=" + password;
			try {
				Log.e("CONCRETE", "Hết SESSION, tiến hành đăng nhập lại...");
				URL url = new URL(urlStr);
				URLConnection urlConn = url.openConnection();
				if (!(urlConn instanceof HttpURLConnection)) {
					throw new IOException("URL is not an Http URL");
				}
				HttpURLConnection httpConn = (HttpURLConnection) urlConn;
				httpConn.setAllowUserInteraction(false);
				httpConn.setInstanceFollowRedirects(true);
				httpConn.setRequestMethod("GET");
				httpConn.setConnectTimeout(5000);
				httpConn.setReadTimeout(5000);
				httpConn.connect();
				resCode = httpConn.getResponseCode();

				if (resCode == HttpURLConnection.HTTP_OK) {
					in = httpConn.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
					String line = "";
					while ((line = bufferedReader.readLine()) != null) {
						result += line;
					}

					if (null != in) {
						in.close();
					}

					if (!result.equals("")){
						result = subJson(result.trim());
						JSONObject jsonResult = new JSONObject(result);

						//Kiểm tra xem đăng nhập lại có thành công không?/ Có trường username trả về là xác nhận thành công
						if (jsonResult.has("user_name")){
							//Request lại url đã bị timeout trước đó
							Log.e("CONCRETE", "Lỗi hết SESSION, Đăng nhập lại THÀNH CÔNG");
						}else {
							Log.e("CONCRETE", "Lỗi hết SESSION, Đăng nhập lại THẤT BẠI");
						}
					}else {
						Log.e("CONCRETE", "Lỗi hết SESSION, Đăng nhập lại THẤT BẠI, response lỗi");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("CONCRETE", "Lỗi hết SESSION, Đăng nhập lại THẤT BẠI");
			}
			return true;
		}else {
			return false;
		}
	}

	public String convertURL(String urlStr) {

		URL url = null;
		try {
			url = new URL(urlStr);
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			url = uri.toURL();
			Log.d("cuong convert url", url.toString());
			return url.toString();
		} catch (Exception e) {
			e.printStackTrace();
			if (url != null)
				return url.toString();
			else
				return null;
		}
	}

	/**
	 * lấy định dạng ngày
	 *
	 * @param d
	 * @return
	 */
	public String getDateFormat(Date d) {
		SimpleDateFormat dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		return dft.format(d);
	}

	/**
	 * lấy định dạng giờ phút
	 *
	 * @param d
	 * @return
	 */
	public String getHourFormat(Date d) {
		SimpleDateFormat dft = new SimpleDateFormat("hh:mm a", Locale.getDefault());
		return dft.format(d);
	}

	public String readData(Context c, String fName) {
		StringBuilder builder = null;
		try {
			FileInputStream in = c.openFileInput(fName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String data = "";
			builder = new StringBuilder();
			while ((data = reader.readLine()) != null) {
				builder.append(data);
				builder.append("\n");
			}
			in.close();
			// txtSearchKH.setText(builder.toString());
			return builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
			if (builder != null)
				return builder.toString();
			else
				return null;
		}
	}

	/**
	 * Hàm ghi tập tin trong Android dùng openFileOutput để ghi openFileOutput tạo ra FileOutputStream
	 */
	public void writeData(Context c, String fName, String data) {
		try {
			FileOutputStream out = c.openFileOutput(fName, 0);
			OutputStreamWriter writer = new OutputStreamWriter(out);
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showDatePicker(final EditText e, Context context) {
		final Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		// Launch Date Picker Dialog
		DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				e.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
				Time chosenDate = new Time();
				chosenDate.set(dayOfMonth, monthOfYear, year);
				// long dtDob = chosenDate.toMillis(true);
				// CharSequence strDate = DateFormat.format("dd/MM/yyyy", dtDob);
			}
		}, mYear, mMonth, mDay);
		dpd.show();
	}

    public void showDatePicker(final TextView e, Context context) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                e.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                Time chosenDate = new Time();
                chosenDate.set(dayOfMonth, monthOfYear, year);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

	public void datePicker(final EditText e, Context context){

		// Get Current Date
		final Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		editText = e;
		DatePickerDialog datePickerDialog = new DatePickerDialog(context,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

						date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
						//*************Call Time Picker Here ********************
						timePicker();
					}
				}, mYear, mMonth, mDay);
		datePickerDialog.show();
	}


	int mHour, mMinute;
	private void timePicker(){
		// Get Current Time
		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);

		// Launch Time Picker Dialog
		TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,int minute) {

						mHour = hourOfDay;
						mMinute = minute;

						editText.setText(date_time + " " + hourOfDay + ":" + minute);
					}
				}, mHour, mMinute, false);
		timePickerDialog.show();
	}

	public boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int mWeek;
	public int weekPicker(final TextView e, Context context){

		// Get Current Date
		final Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		textView = e;
		mWeek = 0;
		DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				c.set(year, monthOfYear, dayOfMonth);
				mWeek = c.get(Calendar.WEEK_OF_YEAR);
				String startDayOfWeek = getStartOfWeek(mWeek, year);
				String endDayOfWeek = getEndOfWeek(mWeek, year);
                String sWeek = "Tuần " + String.valueOf(mWeek - 1) + "(" + startDayOfWeek + "-" + endDayOfWeek + ")";
                e.setText(sWeek);

			}
		}, mYear, mMonth, mDay);
		dpd.show();
		return mWeek - 1;
	}

	public String getStartOfWeek(int enterWeek, int enterYear){
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(Calendar.WEEK_OF_YEAR, enterWeek);
			calendar.set(Calendar.YEAR, enterYear);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date startDate = calendar.getTime();
			String startDateInStr = formatter.format(startDate);
			return  startDateInStr;
	};

	public String getEndOfWeek(int enterWeek, int enterYear){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.WEEK_OF_YEAR, enterWeek);
		calendar.set(Calendar.YEAR, enterYear);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		calendar.add(Calendar.DATE, 6);
		Date enddate = calendar.getTime();
		String endDaString = formatter.format(enddate);
		return  endDaString;
	};

    public int getWeekFromDate(int mYear, int monthOfYear, int dayOfMonth){
        final Calendar c = Calendar.getInstance();
        c.set(mYear, monthOfYear, dayOfMonth);
        mWeek = c.get(Calendar.WEEK_OF_YEAR);
        return mWeek;
    }
}
