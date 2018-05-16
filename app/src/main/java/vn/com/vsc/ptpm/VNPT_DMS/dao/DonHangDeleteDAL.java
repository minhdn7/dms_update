package vn.com.vsc.ptpm.VNPT_DMS.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;
import vn.com.vsc.ptpm.VNPT_DMS.entity.DonHangDeleteEntity;

public class DonHangDeleteDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public DonHangDeleteDAL(Context context) {
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
	 * @param data
	 * @return
	 */
	public long add(DonHangDeleteEntity data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(DonHangDeleteEntity.DONHANGID, data.getDonhangId());

				result = database.insert(DonHangDeleteEntity.TABLE_NAME, null, values);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * Xoa 1 ban ghi
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = DonHangDeleteEntity.ID + " = ?";
				result = database.delete(DonHangDeleteEntity.TABLE_NAME, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Lay tat ca thong tin location offline co trong CSDL
	 * @return
	 */
	public ArrayList<DonHangDeleteEntity> getAll() {
		ArrayList<DonHangDeleteEntity> glstData = new ArrayList<DonHangDeleteEntity>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + DonHangDeleteEntity.TABLE_NAME;
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						DonHangDeleteEntity item = cursorToObject(cursor);
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

	private DonHangDeleteEntity cursorToObject(Cursor cursor) {
		DonHangDeleteEntity object = new DonHangDeleteEntity();

		object.setId(cursor.getInt(0));
		object.setDonhangId(cursor.getString(1));

		return object;

	}
}
