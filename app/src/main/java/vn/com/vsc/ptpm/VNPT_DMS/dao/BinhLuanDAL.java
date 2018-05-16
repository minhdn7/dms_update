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
import vn.com.vsc.ptpm.VNPT_DMS.model.model.BinhLuan;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhaoSat;

public class BinhLuanDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public BinhLuanDAL(Context context) {
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
	 * Ham luu danh sach Binh luan vao DB
	 * @param context
	 * @param url
	 */
	public static void saveToDB(Context context, String url, String orgId, String assignId) {
		try {
			Controller controller = new Controller(context);
			String json = controller.getDataJSON(url, false);
			Type typeOfT = new TypeToken<ArrayList<BinhLuan>>() {}.getType();
			ArrayList<BinhLuan> glstData = new Gson().fromJson(json, typeOfT);

			if (glstData.size() > 0) {

				BinhLuanDAL dal = new BinhLuanDAL(context);
				for (BinhLuan item : glstData) {
					item.setStatus(0);
					item.setOrg_id(orgId);
					item.setAssign_id(assignId);

					long resultInsert = dal.add(item);
					Log.d("INSERT-BINH-LUAN-ITEM", resultInsert>0?""+resultInsert:"Failure");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Them moi 1 Binh luan vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public long add(BinhLuan data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(BinhLuan.CREATED_DATE, data.getCreated_date());
				values.put(BinhLuan.CREATED_BY, data.getCreated_by());
				values.put(BinhLuan.ROW_STT, data.getRow_stt());
				values.put(BinhLuan.IMAGE_URL, data.getImage_url());
				values.put(BinhLuan.IMAGE_RELATED_ID, data.getImage_related_id());
				values.put(BinhLuan.NOTE, data.getNote());
				values.put(BinhLuan.STATUS, data.getStatus());
				values.put(BinhLuan.ORG_ID, data.getOrg_id());
				values.put(BinhLuan.ASSIGN_ID, data.getAssign_id());


				result = database.insert(BinhLuan.TABLE_NAME, null, values);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * Xoa 1 binh luan
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = BinhLuan.ID + " = ?";
				result = database.delete(BinhLuan.TABLE_NAME, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Xoa tat ca cac Binh luan
	 * @return
	 */
	public int deleteAll() {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				result = database.delete(BinhLuan.TABLE_NAME, null, null);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Ham lay Binh luan qua id
	 * @param id
	 * @return
	 */
	public BinhLuan getById(int id) {
		BinhLuan item = new BinhLuan();
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			if (db != null) {

				Cursor cursor = db.query(BinhLuan.TABLE_NAME, null, "id=?", 
						new String[] { String.valueOf(id) }, null, null, null, null);

				if (cursor != null && cursor.getCount() > 0) {
					cursor.moveToFirst();
					item = getObject(cursor);

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
	 * Lay tat ca Binh luan offline co trong CSDL
	 * @return
	 */
	public ArrayList<BinhLuan> getAll() {
		ArrayList<BinhLuan> glstData = new ArrayList<BinhLuan>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + BinhLuan.TABLE_NAME;
				selectQuery += " ORDER BY " + BinhLuan.ID + " DESC";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						BinhLuan item = new BinhLuan();
						item = getObject(cursor);

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
	
	/**
	 * Lay tat ca Binh luan offline co trong CSDL
	 * @return
	 */
	public ArrayList<BinhLuan> getByTuyenKhachHang(String orgId, String assignId) {
		ArrayList<BinhLuan> glstData = new ArrayList<BinhLuan>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + BinhLuan.TABLE_NAME;
				selectQuery += " WHERE " + KhaoSat.ORG_ID + "=?";
				selectQuery += " AND " + KhaoSat.ASSIGN_ID + "=?";
				selectQuery += " ORDER BY " + BinhLuan.ID + " ASC";
				cursor = database.rawQuery(selectQuery, new String[] { orgId, assignId });

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						BinhLuan item = new BinhLuan();
						item = getObject(cursor);

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


	/**
	 * Lay tat ca Binh luan offline theo Status co trong CSDL
	 * @return
	 */
	public ArrayList<BinhLuan> getByStatus(int status) {
		ArrayList<BinhLuan> glstData = new ArrayList<BinhLuan>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + BinhLuan.TABLE_NAME;
				selectQuery += " WHERE " + BinhLuan.STATUS + " = " + status;
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						BinhLuan item = new BinhLuan();
						item = getObject(cursor);

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

	/**
	 * Lay tat ca Binh Luan offline co trong CSDL
	 * @return
	 */
	public BinhLuan getByCreatedDate(String date) {
		BinhLuan item = new BinhLuan();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + BinhLuan.TABLE_NAME;
				selectQuery += " WHERE " + BinhLuan.CREATED_DATE + " = '" + date + "'";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						item = getObject(cursor);

					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return item;
	}

	private BinhLuan getObject(Cursor cursor) {
		BinhLuan item = new BinhLuan();
		try {
			item.setId(cursor.getInt(0));
			item.setCreated_date(cursor.getString(1));
			item.setCreated_by(cursor.getString(2));
			item.setRow_stt(cursor.getString(3));
			item.setImage_url(cursor.getString(4));
			item.setImage_related_id(cursor.getString(5));
			item.setNote(cursor.getString(6));
			item.setStatus(cursor.getInt(7));
			item.setOrg_id(cursor.getString(8));
			item.setAssign_id(cursor.getString(9));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	} 
}
