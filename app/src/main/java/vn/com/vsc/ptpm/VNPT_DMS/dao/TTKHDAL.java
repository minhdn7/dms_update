package vn.com.vsc.ptpm.VNPT_DMS.dao;

import java.util.ArrayList;

import com.google.gson.Gson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TTKH;

public class TTKHDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public TTKHDAL(Context context) {
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
	 * Ham luu danh sach TT Khach hang vao DB
	 * @param context
	 * @param url
	 */
	public static void saveToDB(Context context, String url) {
		try {
			Controller controller = new Controller(context);
			String json = controller.jsonValues(url, true);
			TTKH ttkh = (TTKH) new Gson().fromJson(json, TTKH.class);

			if (ttkh != null) {
				long resultInsert = new TTKHDAL(context).add(ttkh);
				Log.d("INSERT-TTKH", resultInsert>0?""+resultInsert:"Failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Them moi 1 khach hang vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public long add(TTKH data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(TTKH.ID, data.getId());
				values.put(TTKH.CODE, data.getCode());
				values.put(TTKH.NAME, data.getName());
				values.put(TTKH.MANAGER, data.getManager());
				values.put(TTKH.MOBILE, data.getMobile());
				values.put(TTKH.TEL, data.getTel());
				values.put(TTKH.FAX, data.getFax());
				values.put(TTKH.EMAIL, data.getEmail());
				values.put(TTKH.ADDRESS, data.getAddress());
				values.put(TTKH.ADDRESS_ID, data.getAddress_id());
				values.put(TTKH.TAX_CODE, data.getTax_code());
				values.put(TTKH.NOTE, data.getNote());
				values.put(TTKH.LATTITUDE, data.getLattitude());
				values.put(TTKH.LONGTITUDE, data.getLongtitude());

				result = database.insert(TTKH.TABLE_NAME, null, values);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}


	/**
	 * update thong tin khach hang vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public int update(TTKH data) { 

		int result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(TTKH.CODE, data.getCode());
				values.put(TTKH.NAME, data.getName());
				values.put(TTKH.MANAGER, data.getManager());
				values.put(TTKH.MOBILE, data.getMobile());
				values.put(TTKH.TEL, data.getTel());
				values.put(TTKH.FAX, data.getFax());
				values.put(TTKH.EMAIL, data.getEmail());
				values.put(TTKH.ADDRESS, data.getAddress());
				values.put(TTKH.ADDRESS_ID, data.getAddress_id());
				values.put(TTKH.TAX_CODE, data.getTax_code());
				values.put(TTKH.NOTE, data.getNote());
				values.put(TTKH.LATTITUDE, data.getLattitude());
				values.put(TTKH.LONGTITUDE, data.getLongtitude());

				result = database.update(TTKH.TABLE_NAME, values, "id = ?", new String[] {String.valueOf(data.getId())});
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * Xoa 1 khach hang
	 * @param id
	 * @return
	 */
	public int delete(String id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = TTKH.ID + " = ?";
				result = database.delete(TTKH.TABLE_NAME, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Xoa tat ca cac thong tin khach hang
	 * @return
	 */
	public int deleteAll() {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				result = database.delete(TTKH.TABLE_NAME, null, null);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Lay Khach hang theo Id
	 * @return
	 */
	public TTKH getById(String id) {
		TTKH item = new TTKH();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + TTKH.TABLE_NAME;
				selectQuery += " WHERE " + TTKH.ID + " = "+ id;
				cursor = database.rawQuery(selectQuery, null);  

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


	/**
	 * Lay tat ca Khach hang offline co trong CSDL
	 * @return
	 */
	public ArrayList<TTKH> getAll() {
		ArrayList<TTKH> glstKhachHang = new ArrayList<TTKH>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + TTKH.TABLE_NAME;
				selectQuery += " ORDER BY " + TTKH.ID + " ASC";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						TTKH item = new TTKH();
						item = getObject(cursor);

						// Add item to list
						glstKhachHang.add(item);
					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return glstKhachHang;
	}


	private TTKH getObject(Cursor cursor) {
		TTKH item = new TTKH();
		try {
			item.setId(cursor.getString(0));
			item.setCode(cursor.getString(1));
			item.setName(cursor.getString(2));
			item.setManager(cursor.getString(3));
			item.setMobile(cursor.getString(4));
			item.setTel(cursor.getString(5));
			item.setFax(cursor.getString(6));
			item.setEmail(cursor.getString(7));
			item.setAddress(cursor.getString(8));
			item.setAddress_id(cursor.getString(9));
			item.setTax_code(cursor.getString(10));
			item.setNote(cursor.getString(11));
			item.setLattitude(cursor.getString(12));
			item.setLongtitude(cursor.getString(13));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	} 
}
