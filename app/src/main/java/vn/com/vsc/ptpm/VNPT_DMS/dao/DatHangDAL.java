package vn.com.vsc.ptpm.VNPT_DMS.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DatHang;

public class DatHangDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public DatHangDAL(Context context) {
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
	public long add(DatHang data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(DatHang.ID, data.getId());
				values.put(DatHang.DVDH, data.getDvdh());
				values.put(DatHang.NCC, data.getNcc());
				values.put(DatHang.NGAYDH, data.getNgaydh());
				values.put(DatHang.DIENGIAI, data.getDiengiai());
				values.put(DatHang.DIACHI, data.getDiachi());
				values.put(DatHang.GHICHU, data.getGhichu());
				values.put(DatHang.NGAYYC, data.getNgayyc());
				values.put(DatHang.PHIEUGIAO, data.getPhieugiao());
				values.put(DatHang.HTTT, data.getHttt());

				result = database.insert(DatHang.TABLE_NAME, null, values);
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
	public int update(DatHang data) { 

		int result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(DatHang.ID, data.getId());
				values.put(DatHang.DVDH, data.getDvdh());
				values.put(DatHang.NCC, data.getNcc());
				values.put(DatHang.NGAYDH, data.getNgaydh());
				values.put(DatHang.DIENGIAI, data.getDiengiai());
				values.put(DatHang.DIACHI, data.getDiachi());
				values.put(DatHang.GHICHU, data.getGhichu());
				values.put(DatHang.NGAYYC, data.getNgayyc());
				values.put(DatHang.PHIEUGIAO, data.getPhieugiao());
				values.put(DatHang.HTTT, data.getHttt());

				result = database.update(DatHang.TABLE_NAME, values, "_id = ?", new String[] {String.valueOf(data.get_id())});
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * Xoa 1 don hang
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = DatHang._ID + " = ?";
				result = database.delete(DatHang.TABLE_NAME, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Lay don hang theo Id
	 * @return
	 */
	public DatHang getById(String id) {
		DatHang item = new DatHang();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + DatHang.TABLE_NAME;
				selectQuery += " WHERE " + DatHang.ID + " = " + id;
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
	 * Lay don hang theo _Id
	 * @return
	 */
	public DatHang getBy_Id(int id) {
		DatHang item = new DatHang();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + DatHang.TABLE_NAME;
				selectQuery += " WHERE " + DatHang._ID + " = "+ id;
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
	 * Lay don hang theo dvdh
	 * @param id
	 * @return
	 */
	public DatHang getByDVDH(String id) {
		DatHang item = new DatHang();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + DatHang.TABLE_NAME;
				selectQuery += " WHERE " + DatHang.DVDH + " = '" + id + "'";
				cursor = database.rawQuery(selectQuery, null);  

				if (cursor.moveToFirst()) {
					do {
						item = getObject(cursor);

					} while (cursor.moveToNext());
				} else {
					item = null;
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
	 * Lay don hang offline co trong CSDL
	 * @return
	 */
	public ArrayList<DatHang> getListBy_Id(int id) {
		ArrayList<DatHang> glstData = new ArrayList<DatHang>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + DatHang.TABLE_NAME;
				selectQuery += " WHERE " + DatHang._ID + " = "+ id;
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						DatHang item = new DatHang();
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
	 * Lay tat ca don hang offline co trong CSDL
	 * @return
	 */
	public ArrayList<DatHang> getAll() {
		ArrayList<DatHang> glstData = new ArrayList<DatHang>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + DatHang.TABLE_NAME;;
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						DatHang item = new DatHang();
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

	private DatHang getObject(Cursor cursor) {
		DatHang item = new DatHang();
		try {
			item.set_id(cursor.getInt(0));
			item.setId(cursor.getString(1));
			item.setDvdh(cursor.getString(2));
			item.setNcc(cursor.getString(3));
			item.setNgaydh(cursor.getString(4));
			item.setDiengiai(cursor.getString(5));
			item.setDiachi(cursor.getString(6));
			item.setGhichu(cursor.getString(7));
			item.setNgayyc(cursor.getString(8));
			item.setPhieugiao(cursor.getString(9));
			item.setHttt(cursor.getString(10));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}
}
