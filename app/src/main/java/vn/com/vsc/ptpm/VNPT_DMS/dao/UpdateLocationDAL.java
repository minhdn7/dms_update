package vn.com.vsc.ptpm.VNPT_DMS.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.UpdateGPSKH;

public class UpdateLocationDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public UpdateLocationDAL(Context context) {
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
	public long add(UpdateGPSKH data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(UpdateGPSKH.ID, data.getId());
				values.put(UpdateGPSKH.CODE, data.getCode());
				values.put(UpdateGPSKH.LNG, data.getLng());
				values.put(UpdateGPSKH.LAT, data.getLat());
				values.put(UpdateGPSKH.UPD, data.getUpd());
				values.put(UpdateGPSKH.ASSIGN, data.getAssign());
				values.put(UpdateGPSKH.TIME, data.getTime());

				result = database.insert(UpdateGPSKH.TABLE_NAME, null, values);
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
	 * @param time
	 * @return
	 */
	public int delete(int time) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = UpdateGPSKH.TIME + " = ?";
				result = database.delete(UpdateGPSKH.TABLE_NAME, where, new String[] { String.valueOf(time) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Lay tat ca thong tin offline co trong CSDL
	 * @return
	 */
	public ArrayList<UpdateGPSKH> getAll() {
		ArrayList<UpdateGPSKH> glstData = new ArrayList<UpdateGPSKH>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + UpdateGPSKH.TABLE_NAME;
				selectQuery += " ORDER BY " + UpdateGPSKH.TIME + " DESC";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						UpdateGPSKH item = cursorToObject(cursor);
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

	private UpdateGPSKH cursorToObject(Cursor cursor) {
		UpdateGPSKH object = new UpdateGPSKH();

		object.setId(cursor.getString(cursor.getColumnIndex(UpdateGPSKH.ID)));
		object.setCode(cursor.getString(cursor.getColumnIndex(UpdateGPSKH.CODE)));
		object.setLng(cursor.getDouble(cursor.getColumnIndex(UpdateGPSKH.LNG)));
		object.setLat(cursor.getDouble(cursor.getColumnIndex(UpdateGPSKH.LAT)));
		object.setUpd(cursor.getInt(cursor.getColumnIndex(UpdateGPSKH.UPD)));
		object.setAssign(cursor.getString(cursor.getColumnIndex(UpdateGPSKH.ASSIGN)));
		object.setTime(cursor.getInt(cursor.getColumnIndex(UpdateGPSKH.TIME)));

		return object;

	}
}
