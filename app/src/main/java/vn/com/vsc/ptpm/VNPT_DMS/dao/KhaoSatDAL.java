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
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhaoSat;

public class KhaoSatDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public KhaoSatDAL(Context context) {
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
	 * Ham luu danh sach Khao sat vao DB
	 * @param context
	 * @param url
	 */
	public static void saveToDB(Context context, String url, String org_id, String org_code, String assign_id) {
		try {
			Controller controller = new Controller(context);
			String json = controller.getDataJSON(url, false);
			Type typeOfT = new TypeToken<ArrayList<KhaoSat>>() {}.getType();
			ArrayList<KhaoSat> glstData = new Gson().fromJson(json, typeOfT);

			if (glstData.size() > 0) {

				KhaoSatDAL dal = new KhaoSatDAL(context);
				for (KhaoSat item : glstData) {

					if (dal.getById(item.getId(), org_id, org_code, assign_id).getId() == null) {
						item.setStatus(0);
						item.setOrg_id(org_id);
						item.setOrg_code(org_code);
						item.setAssign_id(assign_id);

						long resultInsert = dal.add(item);
						Log.d("INSERT-KHAO-SAT-ITEM", "" + resultInsert);
					} else {
						Log.d("KHAO-SAT-ITEM-EXISIT", "Khao sat is exist");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Them moi 1 Khao sat vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public long add(KhaoSat data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(KhaoSat.ID, data.getId());
				values.put(KhaoSat.UPDATED_DATE, data.getUpdated_date());
				values.put(KhaoSat.JSON_DEFAULT_VALUE, data.getJson_default_value());
				values.put(KhaoSat.COMPONENT_TYPE, data.getComponent_type());
				values.put(KhaoSat.VALUE, data.getValue());
				values.put(KhaoSat.UPDATED_BY, data.getUpdated_by());
				values.put(KhaoSat.CODE, data.getCode());
				values.put(KhaoSat.UPDATED_IP, data.getUpdated_ip());
				values.put(KhaoSat.STATUS, data.getStatus());
				values.put(KhaoSat.ORG_ID, data.getOrg_id());
				values.put(KhaoSat.ORG_CODE, data.getOrg_code());
				values.put(KhaoSat.ASSIGN_ID, data.getAssign_id());
				values.put(KhaoSat.NAME, data.getName());

				result = database.insert(KhaoSat.TABLE_NAME, null, values);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * update trang thai da cap nhat
	 * @param context
	 * @param data
	 * @return
	 */
	public int update(KhaoSat data) { 

		int result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(KhaoSat.UPDATED_DATE, data.getUpdated_date());
				values.put(KhaoSat.VALUE, data.getValue());
				values.put(KhaoSat.UPDATED_BY, data.getUpdated_by());
				values.put(KhaoSat.STATUS, data.getStatus());

				String where = KhaoSat._ID + "=?";

				result = database.update(KhaoSat.TABLE_NAME, values, where, 
						new String[] {String.valueOf(data.get_id()) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * Xoa 1 Khao sat
	 * @param id
	 * @return
	 */
	public int delete(String id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = KhaoSat.ID + " = ?";
				result = database.delete(KhaoSat.TABLE_NAME, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Lay tat ca Khao sat offline co trong CSDL
	 * @return
	 */
	public ArrayList<KhaoSat> getAll() {
		ArrayList<KhaoSat> glstData = new ArrayList<KhaoSat>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + KhaoSat.TABLE_NAME;
				cursor = database.rawQuery(selectQuery, null);

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						KhaoSat item = new KhaoSat();
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
	 * Lay tat ca Khao sat offline co trong CSDL
	 * @return
	 */
	public ArrayList<KhaoSat> getByTuyenKhachHang(String orgId, String orgCode, String assignId) {
		ArrayList<KhaoSat> glstData = new ArrayList<KhaoSat>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + KhaoSat.TABLE_NAME;
				selectQuery += " WHERE " + KhaoSat.ORG_ID + "=?";
				selectQuery += " AND " + KhaoSat.ORG_CODE + "=?";
				selectQuery += " AND " + KhaoSat.ASSIGN_ID + "=?";
				cursor = database.rawQuery(selectQuery, new String[] { orgId, orgCode, assignId });  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						KhaoSat item = new KhaoSat();
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
	 * Lay tat ca Khao sat theo id
	 * @return
	 */
	public KhaoSat getById(String id, String orgId, String orgCode, String assignId) {
		KhaoSat item = new KhaoSat();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + KhaoSat.TABLE_NAME;
				selectQuery += " WHERE " + KhaoSat.ORG_ID + " = ?";
				selectQuery += " AND " + KhaoSat.ORG_CODE + " = ?";
				selectQuery += " AND " + KhaoSat.ASSIGN_ID + " = ?";
				selectQuery += " AND " + KhaoSat.ID + " = ?";
				cursor = database.rawQuery(selectQuery, new String[] { orgId, orgCode, assignId, id }); 

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
	 * Lay tat ca Khao sat offline theo Status co trong CSDL
	 * @return
	 */
	public ArrayList<KhaoSat> getByStatus(String orgId, String orgCode, String assignId, int status) {
		ArrayList<KhaoSat> glstData = new ArrayList<KhaoSat>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + KhaoSat.TABLE_NAME;
				selectQuery += " WHERE " + KhaoSat.ORG_ID + " = ?";
				selectQuery += " AND " + KhaoSat.ORG_CODE + " = ?";
				selectQuery += " AND " + KhaoSat.ASSIGN_ID + " = ?";
				selectQuery += " AND " + KhaoSat.STATUS + " = ?";
				selectQuery += " ORDER BY " + KhaoSat.ID + " ASC";
				cursor = database.rawQuery(selectQuery, new String[] { orgId, orgCode, assignId, String.valueOf(status) });  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						KhaoSat item = new KhaoSat();
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

	private KhaoSat getObject(Cursor cursor) {
		KhaoSat item = new KhaoSat();
		try {
			item.set_id(cursor.getInt(0));
			item.setId(cursor.getString(1));
			item.setUpdated_date(cursor.getString(2));
			item.setJson_default_value(cursor.getString(3));
			item.setComponent_type(cursor.getString(4));
			item.setValue(cursor.getString(5));
			item.setUpdated_by(cursor.getString(6));
			item.setCode(cursor.getString(7));
			item.setUpdated_ip(cursor.getString(8));
			item.setStatus(cursor.getInt(9));
			item.setOrg_id(cursor.getString(10));
			item.setOrg_code(cursor.getString(11));
			item.setAssign_id(cursor.getString(12));
			item.setName(cursor.getString(13));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	} 
}
