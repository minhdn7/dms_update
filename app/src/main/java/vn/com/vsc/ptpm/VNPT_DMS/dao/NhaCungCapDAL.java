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
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TenKH;

public class NhaCungCapDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public NhaCungCapDAL(Context context) {
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
	 * Ham luu danh sach Nha cung cap vao DB
	 * @param context
	 * @param url
	 */
	public static void saveToDB(Context context, String url) {
		try {
			Controller controller = new Controller(context);
			String json = controller.getDataJSON(url, false);
			Type typeOfT = new TypeToken<ArrayList<TenKH>>() {}.getType();
			ArrayList<TenKH> glstData = new Gson().fromJson(json, typeOfT);

			if (glstData.size() > 0) {

				NhaCungCapDAL dal = new NhaCungCapDAL(context);
				for (TenKH item : glstData) {

					if (dal.getById(item.getId()) == null) {
						long resultInsert = dal.add(item);
						Log.d("INSERT-NHACUNGCAP-ITEM", "" + resultInsert);
					} else {
						Log.d("NHACUNGCAP-ITEM-EXISIT", "ID: " + item.getId() + " is exist");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Them moi 1 Nha cung cap vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public long add(TenKH data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(TenKH.ID, data.getId());
				values.put(TenKH.NAME, data.getName());

				result = database.insert(TenKH.TABLE_NAME_NCC, null, values);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * Xoa 1 Nha cung cap
	 * @param id
	 * @return
	 */
	public int delete(String id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = TenKH.ID + " = ?";
				result = database.delete(TenKH.TABLE_NAME_NCC, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Ham lay Nha cung cap qua id
	 * @param id
	 * @return
	 */
	public TenKH getById(String id) {
		TenKH item = new TenKH();
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			if (db != null) {
				String[] columns = new String[] { TenKH.ID, TenKH.NAME};

				Cursor cursor = db.query(TenKH.TABLE_NAME_NCC,
						columns, "id=?", new String[] { String.valueOf(id) }, null, null, null, null);

				if (cursor != null && cursor.getCount() > 0) {
					cursor.moveToFirst();

					item.setId(cursor.getString(0));
					item.setName(cursor.getString(1));

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
	 * Lay tat ca Nha cung cap offline co trong CSDL
	 * @return
	 */
	public ArrayList<TenKH> getAll() {
		ArrayList<TenKH> glstNhaCungCap = new ArrayList<TenKH>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + TenKH.TABLE_NAME_NCC;
				//selectQuery += " ORDER BY " + TenKH.ID + " ASC";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						TenKH item = new TenKH();

						item.setId(cursor.getString(0));
						item.setName(cursor.getString(1));

						// Add item to list
						glstNhaCungCap.add(item);
					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return glstNhaCungCap;
	}
}
