package vn.com.vsc.ptpm.VNPT_DMS.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.User;

public class UserDAL {
	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public UserDAL(Context context) {
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
	 * Lay tat ca thong tin offline co trong CSDL
	 * @return
	 */
	public ArrayList<User> getAll() {
		ArrayList<User> glstData = new ArrayList<User>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + User.TABLE_NAME;				
				cursor = database.rawQuery(selectQuery, null);  
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						User item = cursorToObject(cursor);
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
	private User cursorToObject(Cursor cursor) {
		User object = new User();
		object.setId(cursor.getString(cursor.getColumnIndex(User.ID)));
		object.setUsername(cursor.getString(cursor.getColumnIndex(User.USERNAME)));
		object.setPassword(cursor.getString(cursor.getColumnIndex(User.PASSWORD)));		
		return object;

	}
}
