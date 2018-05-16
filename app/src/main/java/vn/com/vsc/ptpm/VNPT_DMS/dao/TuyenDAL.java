package vn.com.vsc.ptpm.VNPT_DMS.dao;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Tuyen;

public class TuyenDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public TuyenDAL(Context context) {
		dbHelper = new DatabaseHandler(context);
	}

	public void openDB() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void closeDB() {
		if (database != null)
			database.close();
		if (dbHelper != null)
			dbHelper.close();
	}

	/**
	 * Ham luu danh sach Tuyen vao DB
	 * @param context
	 * @param url
	 */
	public static void saveToDB(Context context, String url) {
		try {
			Controller controller = new Controller(context);
			String json = controller.getDataJSON(url, false);
			Type typeOfT = new TypeToken<ArrayList<Tuyen>>() {}.getType();
			ArrayList<Tuyen> glstData = new Gson().fromJson(json, typeOfT);

			if (glstData.size() > 0) {

				TuyenDAL dal = new TuyenDAL(context);
				for (Tuyen item : glstData) {

					long resultInsert = dal.add(item);
					Log.d("INSERT-TUYEN", "" + resultInsert);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Them moi 1 tuyen vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public long add(Tuyen data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(Tuyen.NO_OF_NONE, data.getNo_of_none());
				values.put(Tuyen.IS_FINISH, data.getIs_finish());
				values.put(Tuyen.IS_ACTIVE, data.getIs_active());
				values.put(Tuyen.CODE, data.getCode());
				values.put(Tuyen.SELLER, data.getSeller());
				values.put(Tuyen.TOTAL_OF_ORDER, data.getTotal_of_order());
				values.put(Tuyen.ID, data.getId());
				values.put(Tuyen.ROW_STT, data.getRow_stt());
				values.put(Tuyen.END_DATE, data.getEnd_date());
				values.put(Tuyen.NO_OF_DONE, data.getNo_of_done());
				values.put(Tuyen.NO_OF_ORDERED_CUSTOMER, data.getNo_of_ordered_customer());
				values.put(Tuyen.NO_OF_ORDER, data.getNo_of_order());
				values.put(Tuyen.NAME, data.getName());
				values.put(Tuyen.START_DATE, data.getStart_date());

				result = database.insert(Tuyen.TABLE_NAME, null, values);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * Xoa 1 tuyen
	 * @param id
	 * @return
	 */
	public int delete(String id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = Tuyen.ID + " = ?";
				result = database.delete(Tuyen.TABLE_NAME, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}

	/**
	 * Xoa tat ca cac tuyen
	 * @param id
	 * @return
	 */
	public int deleteAll() {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				result = database.delete(Tuyen.TABLE_NAME, null, null);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}

	/**
	 * Lay tat ca cac tuyen offline co trong CSDL
	 * @return
	 */
	public ArrayList<Tuyen> getAll() {
		ArrayList<Tuyen> glstData = new ArrayList<Tuyen>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + Tuyen.TABLE_NAME;
				selectQuery += " ORDER BY " + Tuyen.ID + " ASC";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						Tuyen item = new Tuyen();

						item.setNo_of_none(cursor.getString(0));
						item.setIs_finish(cursor.getString(1));
						item.setIs_active(cursor.getString(2));
						item.setCode(cursor.getString(3));
						item.setSeller(cursor.getString(4));
						item.setTotal_of_order(cursor.getString(5));
						item.setId(cursor.getString(6));
						item.setRow_stt(cursor.getString(7));
						item.setEnd_date(cursor.getString(8));
						item.setNo_of_done(cursor.getString(9));
						item.setNo_of_ordered_customer(cursor.getString(10));
						item.setNo_of_order(cursor.getString(11));
						item.setName(cursor.getString(12));
						item.setStart_date(cursor.getString(13));

						// Add item to list
						glstData.add(item);
					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return glstData;
	}
}
