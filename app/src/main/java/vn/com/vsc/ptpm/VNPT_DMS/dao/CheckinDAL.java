package vn.com.vsc.ptpm.VNPT_DMS.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Checkin;

public class CheckinDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public CheckinDAL(Context context) {
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
	public long add(Checkin data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(Checkin.ID, data.getId());
				values.put(Checkin.CODE, data.getCode());
				values.put(Checkin.ASSIGN, data.getAssign());
				values.put(Checkin.LNG, data.getLng());
				values.put(Checkin.LAT, data.getLat());
				values.put(Checkin.CHECKTYPE, data.getChecktype());
				values.put(Checkin.TIME, data.getTime());
				values.put(Checkin.PIN, data.getPin());
				values.put(Checkin.NETWORK_TYPE, data.getType_network());
				values.put(Checkin.SIGNAL_STRENGTH, data.getValue());
				
				result = database.insert(Checkin.TABLE_NAME, null, values);
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
	public int delete(String time) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = Checkin.TIME + " = ?";
				result = database.delete(Checkin.TABLE_NAME, where, new String[] { String.valueOf(time) });
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
	public ArrayList<Checkin> getAll() {
		ArrayList<Checkin> glstData = new ArrayList<Checkin>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + Checkin.TABLE_NAME;
				selectQuery += " ORDER BY " + Checkin.TIME + " DESC";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						Checkin item = cursorToObject(cursor);
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
	
	private Checkin cursorToObject(Cursor cursor) {
		Checkin object = new Checkin();
		
		object.setId(cursor.getString(cursor.getColumnIndex(Checkin.ID)));
		object.setCode(cursor.getString(cursor.getColumnIndex(Checkin.CODE)));
		object.setAssign(cursor.getString(cursor.getColumnIndex(Checkin.ASSIGN)));
		object.setLng(cursor.getString(cursor.getColumnIndex(Checkin.LNG)));
		object.setLat(cursor.getString(cursor.getColumnIndex(Checkin.LAT)));
		object.setChecktype(cursor.getString(cursor.getColumnIndex(Checkin.CHECKTYPE)));
		object.setTime(cursor.getString(cursor.getColumnIndex(Checkin.TIME)));
		object.setPin(cursor.getString(cursor.getColumnIndex(Checkin.PIN)));
		object.setType_network(cursor.getString(cursor.getColumnIndex(Checkin.NETWORK_TYPE)));
		object.setValue(cursor.getString(cursor.getColumnIndex(Checkin.SIGNAL_STRENGTH)));
		
		return object;
		
	}
}
