package vn.com.vsc.ptpm.VNPT_DMS.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.ThemKH;

public class ThongTinKhachhangDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public ThongTinKhachhangDAL(Context context) {
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
	 * Them moi 1 khach hang vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public long add(ThemKH data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(ThemKH.ID, data.getId());
				values.put(ThemKH.NEW_CODE, data.getNew_code());
				values.put(ThemKH.CODE, data.getCode());
				values.put(ThemKH.NAME, data.getName());
				values.put(ThemKH.MANAGER, data.getMgr());
				values.put(ThemKH.MOBILE, data.getMobile());
				values.put(ThemKH.TEL, data.getTel());
				values.put(ThemKH.FAX, data.getFax());
				values.put(ThemKH.EMAIL, data.getEmail());
				values.put(ThemKH.ADDRESS, data.getAddr());
				values.put(ThemKH.TAX, data.getTax());
				values.put(ThemKH.ENT_ID, data.getEnt_id());
				values.put(ThemKH.NOTE, data.getNote());
				values.put(ThemKH.ASSIGN, data.getAssign());
				values.put(ThemKH.TYPE, data.getType());
				values.put(ThemKH.LATTITUDE, data.getLattitude());
				values.put(ThemKH.LONGTITUDE, data.getLongtitude());

				result = database.insert(ThemKH.TABLE_NAME, null, values);
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
	public int update(ThemKH data) { 

		int result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(ThemKH.ID, data.getId());
				values.put(ThemKH.NEW_CODE, data.getNew_code());
				values.put(ThemKH.CODE, data.getCode());
				values.put(ThemKH.NAME, data.getName());
				values.put(ThemKH.MANAGER, data.getMgr());
				values.put(ThemKH.MOBILE, data.getMobile());
				values.put(ThemKH.TEL, data.getTel());
				values.put(ThemKH.FAX, data.getFax());
				values.put(ThemKH.EMAIL, data.getEmail());
				values.put(ThemKH.ADDRESS, data.getAddr());
				values.put(ThemKH.TAX, data.getTax());
				values.put(ThemKH.ENT_ID, data.getEnt_id());
				values.put(ThemKH.NOTE, data.getNote());
				values.put(ThemKH.ASSIGN, data.getAssign());
				values.put(ThemKH.TYPE, data.getType());
				values.put(ThemKH.LATTITUDE, data.getLattitude());
				values.put(ThemKH.LONGTITUDE, data.getLongtitude());

				result = database.update(ThemKH.TABLE_NAME, values, "_id = ?", new String[] {String.valueOf(data.get_id())});
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
				String where = ThemKH._ID + " = ?";
				result = database.delete(ThemKH.TABLE_NAME, where, new String[] { String.valueOf(id) });
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
	public ThemKH getById(String id) {
		ThemKH khachhang = new ThemKH();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + ThemKH.TABLE_NAME;
				selectQuery += " WHERE " + ThemKH.ID + " = "+ id;
				cursor = database.rawQuery(selectQuery, null);  

				if (cursor.moveToFirst()) {
					do {
						khachhang = getObject(cursor);

					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return khachhang;
	}

	/**
	 * Lay Khach hang theo _Id
	 * @return
	 */
	public ThemKH getBy_Id(int id) {
		ThemKH khachhang = new ThemKH();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + ThemKH.TABLE_NAME;
				selectQuery += " WHERE " + ThemKH._ID + " = "+ id;
				cursor = database.rawQuery(selectQuery, null);  

				if (cursor.moveToFirst()) {
					do {
						khachhang = getObject(cursor);

					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return khachhang;
	}
	
	
	/**
	 * Lay Khach hang them moi hay update
	 * @return
	 */
	public ArrayList<ThemKH> getByType(int type) {
		ArrayList<ThemKH> glstKhachHang = new ArrayList<ThemKH>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + ThemKH.TABLE_NAME;
				selectQuery += " WHERE " + ThemKH.TYPE + " = "+ type;
				cursor = database.rawQuery(selectQuery, null);  

				if (cursor.moveToFirst()) {
					do {
						ThemKH item = new ThemKH();
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

	/**
	 * Lay tat ca Khach hang offline co trong CSDL
	 * @return
	 */
	public ArrayList<ThemKH> getAll() {
		ArrayList<ThemKH> glstKhachHang = new ArrayList<ThemKH>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + ThemKH.TABLE_NAME;
				selectQuery += " ORDER BY " + ThemKH._ID + " ASC";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						ThemKH item = new ThemKH();
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
	
	
	private ThemKH getObject(Cursor cursor) {
		ThemKH khachhang = new ThemKH();
		try {
			khachhang.set_id(cursor.getInt(0));
			khachhang.setId(cursor.getString(1));
			khachhang.setNew_code(cursor.getString(2));
			khachhang.setCode(cursor.getString(3));
			khachhang.setName(cursor.getString(4));
			khachhang.setMgr(cursor.getString(5));
			khachhang.setMobile(cursor.getString(6));
			khachhang.setTel(cursor.getString(7));
			khachhang.setFax(cursor.getString(8));
			khachhang.setEmail(cursor.getString(9));
			khachhang.setAddr(cursor.getString(10));
			khachhang.setTax(cursor.getString(11));
			khachhang.setEnt_id(cursor.getString(12));
			khachhang.setNote(cursor.getString(13));
			khachhang.setAssign(cursor.getString(14));
			khachhang.setType(cursor.getInt(15));
			khachhang.setLattitude(cursor.getDouble(16));
			khachhang.setLongtitude(cursor.getDouble(17));
		} catch (Exception e) {
			return null;
		}
		return khachhang;
	}
}
