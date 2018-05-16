package vn.com.vsc.ptpm.VNPT_DMS.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;
import vn.com.vsc.ptpm.VNPT_DMS.entity.SanPhamDonHangEntity;

public class SanPhamDonHangDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public SanPhamDonHangDAL(Context context) {
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
	 * Them moi vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public long add(SanPhamDonHangEntity data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(SanPhamDonHangEntity.PO_ID, data.getPo_id());
				values.put(SanPhamDonHangEntity.LOAISP_ID, data.getLoaisp_id());
				values.put(SanPhamDonHangEntity.SOLUONG, data.getSoluong());
				values.put(SanPhamDonHangEntity.GIA, data.getGia());

				result = database.insert(SanPhamDonHangEntity.TABLE_NAME, null, values);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}


	/**
	 * update thong tin
	 * @param context
	 * @param data
	 * @return
	 */
	public int update(SanPhamDonHangEntity data) { 

		int result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(SanPhamDonHangEntity.PO_ID, data.getPo_id());
				values.put(SanPhamDonHangEntity.LOAISP_ID, data.getLoaisp_id());
				values.put(SanPhamDonHangEntity.SOLUONG, data.getSoluong());
				values.put(SanPhamDonHangEntity.GIA, data.getGia());

				result = database.update(SanPhamDonHangEntity.TABLE_NAME, values, "_id = ?", new String[] {String.valueOf(data.get_id())});
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * Xoa 1 san pham
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = SanPhamDonHangEntity._ID + " = ?";
				result = database.delete(SanPhamDonHangEntity.TABLE_NAME, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Lay san pham theo Id
	 * @return
	 */
	public SanPhamDonHangEntity getById(String id) {
		SanPhamDonHangEntity item = new SanPhamDonHangEntity();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + SanPhamDonHangEntity.TABLE_NAME;
				selectQuery += " WHERE " + SanPhamDonHangEntity.PO_ID + " = " + id;
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
	 * Lay San pham theo _Id
	 * @return
	 */
	public SanPhamDonHangEntity getBy_Id(int id) {
		SanPhamDonHangEntity item = new SanPhamDonHangEntity();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + SanPhamDonHangEntity.TABLE_NAME;
				selectQuery += " WHERE " + SanPhamDonHangEntity._ID + " = "+ id;
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
	 * Lay san pham offline co trong CSDL
	 * @return
	 */
	public ArrayList<SanPhamDonHangEntity> getListBy_Id(String id) {
		ArrayList<SanPhamDonHangEntity> glstData = new ArrayList<SanPhamDonHangEntity>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + SanPhamDonHangEntity.TABLE_NAME;
				selectQuery += " WHERE " + SanPhamDonHangEntity._ID + " = "+ id;
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						SanPhamDonHangEntity item = new SanPhamDonHangEntity();
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
	 * Lay san pham offline co trong CSDL
	 * @return
	 */
	public ArrayList<SanPhamDonHangEntity> getListById(String id) {
		ArrayList<SanPhamDonHangEntity> glstData = new ArrayList<SanPhamDonHangEntity>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + SanPhamDonHangEntity.TABLE_NAME;
				selectQuery += " WHERE " + SanPhamDonHangEntity.PO_ID + " = "+ id;
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						SanPhamDonHangEntity item = new SanPhamDonHangEntity();
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

	private SanPhamDonHangEntity getObject(Cursor cursor) {
		SanPhamDonHangEntity item = new SanPhamDonHangEntity();
		try {
			item.set_id(cursor.getInt(0));
			item.setPo_id(cursor.getString(1));
			item.setLoaisp_id(cursor.getString(2));
			item.setSoluong(cursor.getString(3));
			item.setGia(cursor.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}
}
