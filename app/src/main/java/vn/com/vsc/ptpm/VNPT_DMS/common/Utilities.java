package vn.com.vsc.ptpm.VNPT_DMS.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class Utilities {

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static String getDate(long milliSeconds, String dateFormat) throws ParseException
	{
		String formattedDate = "";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
		Date date = new Date(milliSeconds);
		formattedDate = formatter.format(date);

		return formattedDate;
	}
	
	public static String getDate(int milliSeconds, String dateFormat) throws ParseException
	{
		String formattedDate = "";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
		Date date = new Date(milliSeconds * 1000L);
		formattedDate = formatter.format(date);

		return formattedDate;
	}

	public static int getCurrentDate() {
		long d = new Date().getTime();
		int offset = TimeZone.getDefault().getOffset(d);
		d = ((d + offset)/ 86400000l) * 86400000l - offset;
		int date = (int)(d/1000);
		//Log.i("CURRENT_DATE", "" + date);

		return date;
	}

	public static int getCurrentTime() {
		//		int h = new Date().getHours() * 60 * 60;
		//		int m = new Date().getMinutes() * 60;

		Calendar calendar = Calendar.getInstance();
		int h = calendar.get(Calendar.HOUR_OF_DAY);
		int m = calendar.get(Calendar.MINUTE);
		int time = (h * 60 * 60) + (m * 60);

		return time;
	}

	public static void trimCache(Context context) {
		try {
			File dir = context.getCacheDir();
			if (dir != null && dir.isDirectory()) {
				deleteDir(dir);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// The directory is now empty so delete it
		return dir.delete();
	}

	/**
	 * Function to convert milliseconds time to Timer Format
	 * Hours:Minutes:Seconds
	 * */
	public static String milliSecondsToTimer(long milliseconds) {
		String finalTimerString = "";
		String minutesString = "";
		String secondsString = "";

		// Convert total duration into time
		int hours = (int) (milliseconds / (1000 * 60 * 60));
		int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
		int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
		// Add hours if there
		if (hours > 0) {
			finalTimerString = hours + ":";
		}

		// Prepending 0 to minutes if it is one digit
		if (minutes < 10) {
			minutesString = "0" + minutes;
		} else {
			minutesString = "" + minutes;
		}

		// Prepending 0 to seconds if it is one digit
		if (seconds < 10) {
			secondsString = "0" + seconds;
		} else {
			secondsString = "" + seconds;
		}

		finalTimerString = finalTimerString + minutesString + ":"
				+ secondsString;

		// return timer string
		return finalTimerString;
	}


	/**
	 * Lay tong dung luong cua thiet bi 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long getTotalMemory() {
		long memory = 0;
		try {
			File path = Environment.getExternalStorageDirectory();
			StatFs statfs = new StatFs(path.getPath());
			int blockCount = statfs.getBlockCount();
			int blockSize = statfs.getBlockSize();
			memory = ((long)blockSize * (long)blockCount) / (1024 * 1024);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memory;
	}

	/**
	 * Lay dung luong con trong cua thiet bi
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long getFreeMemory() {
		long memory = 0;
		try {
			File path = Environment.getExternalStorageDirectory();
			StatFs statfs = new StatFs(path.getPath());
			int availBlocks = statfs.getAvailableBlocks();
			int blockSize = statfs.getBlockSize();
			memory = ((long)blockSize * (long)availBlocks) / (1024 * 1024);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memory;
	}


	public static int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	/***
	 * Save bitmap
	 * @param playerId
	 * @param bitmap
	 * @return
	 * @throws ParseException
	 */
	public static String saveBitmap(int playerId, Bitmap bitmap) throws ParseException {
		String fileName = "";
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
			fileName = "screenshot" + timeStamp + "_" + playerId + ".png";

			File imagePath = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
			FileOutputStream fos;

			fos = new FileOutputStream(imagePath);
			bitmap.compress(CompressFormat.JPEG, 90, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			Log.e("GREC", e.getMessage(), e);
		} catch (IOException e) {
			Log.e("GREC", e.getMessage(), e);
		} catch (Exception e) {
			Log.e("EXCEPTION", e.getMessage(), e);
		}
		return fileName;
	}

}