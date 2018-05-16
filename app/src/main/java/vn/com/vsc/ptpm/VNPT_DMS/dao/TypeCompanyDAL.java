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
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TypeCompany;

public class TypeCompanyDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public TypeCompanyDAL(Context context) {
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
	 * Ham luu danh sach Kieu cong ty vao DB
	 * @param context
	 * @param url
	 */
	public static void saveToDB(Context context, String url) {
		try {
			Controller controller = new Controller(context);
			String json = controller.getDataJSON(url, false);
			Type typeOfT = new TypeToken<ArrayList<TypeCompany>>() {}.getType();
			ArrayList<TypeCompany> glstData = new Gson().fromJson(json, typeOfT);

			if (glstData.size() > 0) {

				TypeCompanyDAL dal = new TypeCompanyDAL(context);
				for (TypeCompany item : glstData) {

					if (dal.getById(item.getId()) == null) {
						long resultInsert = dal.add(item);
						Log.d("INSERT-TYPECOMPANY-ITEM", "" + resultInsert);
					} else {
						Log.d("TYPECOMPANY-ITEM-EXISIT", "ID: " + item.getId() + " is exist");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Them moi 1 kieu cty vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public long add(TypeCompany data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(TypeCompany.ID, data.getId());
				values.put(TypeCompany.NAME, data.getName());

				result = database.insert(TypeCompany.TABLE_NAME, null, values);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * Xoa 1 kieu cty
	 * @param id
	 * @return
	 */
	public int delete(String id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = TypeCompany.ID + " = ?";
				result = database.delete(TypeCompany.TABLE_NAME, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Ham lay Kieu cong ty qua id
	 * @param id
	 * @return
	 */
	public TypeCompany getById(String id) {
		TypeCompany item = new TypeCompany();
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			if (db != null) {
				String[] columns = new String[] { TypeCompany.ID, TypeCompany.NAME};

				Cursor cursor = db.query(TypeCompany.TABLE_NAME,
						columns, "id=?", new String[] { String.valueOf(id) }, null, null, null, null);

				if (cursor != null && cursor.getCount() > 0) {
					cursor.moveToFirst();

					item.setId(cursor.getString(0));
					item.setName(cursor.getString(1));

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
	 * Lay tat ca Kieu cty offline co trong CSDL
	 * @return
	 */
	public ArrayList<TypeCompany> getAll() {
		ArrayList<TypeCompany> glstTypeCompany = new ArrayList<TypeCompany>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + TypeCompany.TABLE_NAME;
				selectQuery += " ORDER BY " + TypeCompany.ID + " ASC";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						TypeCompany item = new TypeCompany();

						item.setId(cursor.getString(0));
						item.setName(cursor.getString(1));

						// Add item to list
						glstTypeCompany.add(item);
					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return glstTypeCompany;
	}
}
