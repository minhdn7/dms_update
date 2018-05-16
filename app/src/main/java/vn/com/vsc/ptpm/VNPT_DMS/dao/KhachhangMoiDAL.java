package vn.com.vsc.ptpm.VNPT_DMS.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;
import vn.com.vsc.ptpm.VNPT_DMS.entity.KhachHangMoiEntity;

public class KhachhangMoiDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public KhachhangMoiDAL(Context context) {
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
	public long add(KhachHangMoiEntity data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(KhachHangMoiEntity.NEW_CODE, data.getNew_code());
				values.put(KhachHangMoiEntity.NAME, data.getName());
				values.put(KhachHangMoiEntity.MANAGER, data.getMgr());
				values.put(KhachHangMoiEntity.MOBILE, data.getMobile());
				values.put(KhachHangMoiEntity.FAX, data.getFax());
				values.put(KhachHangMoiEntity.EMAIL, data.getEmail());
				values.put(KhachHangMoiEntity.ADDRESS, data.getAddr());
				values.put(KhachHangMoiEntity.TAX, data.getTax());
				values.put(KhachHangMoiEntity.ENT_ID, data.getEnt_id());
				values.put(KhachHangMoiEntity.NOTE, data.getNote());
				values.put(KhachHangMoiEntity.ORDER_NOW, data.getOrder_now());
				values.put(KhachHangMoiEntity.LATTITUDE, data.getLattitude());
				values.put(KhachHangMoiEntity.LONGTITUDE, data.getLongtitude());

				result = database.insert(KhachHangMoiEntity.TABLE_NAME, null, values);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}
	
	
	/**
	 * update thong tin khach hang moi vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public int update(KhachHangMoiEntity data) { 

		int result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(KhachHangMoiEntity.NEW_CODE, data.getNew_code());
				values.put(KhachHangMoiEntity.NAME, data.getName());
				values.put(KhachHangMoiEntity.MANAGER, data.getMgr());
				values.put(KhachHangMoiEntity.MOBILE, data.getMobile());
				values.put(KhachHangMoiEntity.FAX, data.getFax());
				values.put(KhachHangMoiEntity.EMAIL, data.getEmail());
				values.put(KhachHangMoiEntity.ADDRESS, data.getAddr());
				values.put(KhachHangMoiEntity.TAX, data.getTax());
				values.put(KhachHangMoiEntity.ENT_ID, data.getEnt_id());
				values.put(KhachHangMoiEntity.NOTE, data.getNote());
				values.put(KhachHangMoiEntity.ORDER_NOW, data.getOrder_now());
				values.put(KhachHangMoiEntity.LATTITUDE, data.getLattitude());
				values.put(KhachHangMoiEntity.LONGTITUDE, data.getLongtitude());

				result = database.update(KhachHangMoiEntity.TABLE_NAME, values, "id = ?", new String[] {String.valueOf(data.getId())});
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
				String where = KhachHangMoiEntity.ID + " = ?";
				result = database.delete(KhachHangMoiEntity.TABLE_NAME, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Lay tat ca Khach hang offline co trong CSDL
	 * @return
	 */
	public KhachHangMoiEntity getById(int id) {
		KhachHangMoiEntity khachhangmoi = new KhachHangMoiEntity();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + KhachHangMoiEntity.TABLE_NAME;
				selectQuery += " WHERE " + KhachHangMoiEntity.ID + " = "+ id;
				cursor = database.rawQuery(selectQuery, null);  

				if (cursor.moveToFirst()) {
					do {
						khachhangmoi.setId(cursor.getInt(0));
						khachhangmoi.setNew_code(cursor.getString(1));
						khachhangmoi.setName(cursor.getString(2));
						khachhangmoi.setMgr(cursor.getString(3));
						khachhangmoi.setMobile(cursor.getString(4));
						khachhangmoi.setFax(cursor.getString(5));
						khachhangmoi.setEmail(cursor.getString(6));
						khachhangmoi.setAddr(cursor.getString(7));
						khachhangmoi.setTax(cursor.getString(8));
						khachhangmoi.setEnt_id(cursor.getString(9));
						khachhangmoi.setNote(cursor.getString(10));
						khachhangmoi.setOrder_now(cursor.getInt(11));
						khachhangmoi.setLattitude(cursor.getDouble(12));
						khachhangmoi.setLongtitude(cursor.getDouble(13));

					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return khachhangmoi;
	}

	/**
	 * Lay tat ca Khach hang offline co trong CSDL
	 * @return
	 */
	public ArrayList<KhachHangMoiEntity> getAll() {
		ArrayList<KhachHangMoiEntity> glstKhachHang = new ArrayList<KhachHangMoiEntity>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + KhachHangMoiEntity.TABLE_NAME;
				selectQuery += " ORDER BY " + KhachHangMoiEntity.ID + " DESC";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						KhachHangMoiEntity item = new KhachHangMoiEntity();

						item.setId(cursor.getInt(0));
						item.setNew_code(cursor.getString(1));
						item.setName(cursor.getString(2));
						item.setMgr(cursor.getString(3));
						item.setMobile(cursor.getString(4));
						item.setFax(cursor.getString(5));
						item.setEmail(cursor.getString(6));
						item.setAddr(cursor.getString(7));
						item.setTax(cursor.getString(8));
						item.setEnt_id(cursor.getString(9));
						item.setNote(cursor.getString(10));
						item.setOrder_now(cursor.getInt(11));
						item.setLattitude(cursor.getDouble(12));
						item.setLongtitude(cursor.getDouble(13));

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
}
