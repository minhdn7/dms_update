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
import vn.com.vsc.ptpm.VNPT_DMS.model.model.HTTT;

public class HinhThucThanhToanDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public HinhThucThanhToanDAL(Context context) {
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
	 * Ham luu danh sach Hinh thuc thanh toan vao DB
	 * @param context
	 * @param url
	 */
	public static void saveToDB(Context context, String url) {
		try {
			Controller controller = new Controller(context);
			String json = controller.getDataJSON(url, false);
			Type typeOfT = new TypeToken<ArrayList<HTTT>>() {}.getType();
			ArrayList<HTTT> glstData = new Gson().fromJson(json, typeOfT);

			if (glstData.size() > 0) {

				HinhThucThanhToanDAL dal = new HinhThucThanhToanDAL(context);
				for (HTTT item : glstData) {

					if (dal.getById(item.getHttt_id()) == null) {
						long resultInsert = dal.add(item);
						Log.d("INSERT-HINHTHUCTHANHTOAN-ITEM", "" + resultInsert);
					} else {
						Log.d("INSERT-HINHTHUCTHANHTOAN-EXISIT", "ID: " + item.getHttt_id() + " is exist");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Them moi Hinh thuc thanh toan vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public long add(HTTT data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(HTTT.HTTT_ID, data.getHttt_id());
				values.put(HTTT.HINHTHUC, data.getHinhthuc());

				result = database.insert(HTTT.TABLE_NAME, null, values);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * Xoa 1 Hinh thuc thanh toan
	 * @param id
	 * @return
	 */
	public int delete(String id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = HTTT.HTTT_ID + " = ?";
				result = database.delete(HTTT.TABLE_NAME, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Ham lay Hinh thuc thanh toan qua id
	 * @param id
	 * @return
	 */
	public HTTT getById(String id) {
		HTTT item = new HTTT();
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			if (db != null) {
				String[] columns = new String[] { HTTT.HTTT_ID, HTTT.HINHTHUC};

				Cursor cursor = db.query(HTTT.TABLE_NAME,
						columns, "httt_id=?", new String[] { String.valueOf(id) }, null, null, null, null);

				if (cursor != null && cursor.getCount() > 0) {
					cursor.moveToFirst();

					item.setHttt_id(cursor.getString(0));
					item.setHinhthuc(cursor.getString(1));
				} else {
					item = null;
				}
				db.close();
				dbHelper.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return item;
	}


	/**
	 * Ham lay Hinh thuc thanh toan qua id
	 * @param id
	 * @return
	 */
	public HTTT getNhaCungCapById(int id) {
		HTTT item = new HTTT();
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			if (db != null) {
				String[] columns = new String[] { HTTT.HTTT_ID, HTTT.HINHTHUC};

				Cursor cursor = db.query(HTTT.TABLE_NAME,
						columns, "id=?", new String[] { String.valueOf(id) }, null, null, null, null);

				if (cursor != null && cursor.getCount() > 0) {
					cursor.moveToFirst();

					item.setHttt_id(cursor.getString(0));
					item.setHinhthuc(cursor.getString(1));

				} else {
					item = null;
				}
				db.close();
				dbHelper.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return item;
	}

	/**
	 * Lay tat ca HTTT offline co trong CSDL
	 * @return
	 */
	public ArrayList<HTTT> getAll() {
		ArrayList<HTTT> glstHinhThucThanhToan = new ArrayList<HTTT>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + HTTT.TABLE_NAME;
				selectQuery += " ORDER BY " + HTTT.HTTT_ID + " ASC";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						HTTT item = new HTTT();

						item.setHttt_id(cursor.getString(0));
						item.setHinhthuc(cursor.getString(1));

						// Add item to list
						glstHinhThucThanhToan.add(item);
					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return glstHinhThucThanhToan;
	}
}
