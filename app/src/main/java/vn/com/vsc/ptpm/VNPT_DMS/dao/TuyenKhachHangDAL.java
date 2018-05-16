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
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;

public class TuyenKhachHangDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;
	private Context mContext;

	public TuyenKhachHangDAL(Context context) {
		this.dbHelper = new DatabaseHandler(context);
		this.mContext = context;
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
	 * Ham luu danh sach Tuyen Khach hang vao DB
	 * @param context
	 * @param url
	 */
	public static void saveToDB(Context context, String url) {
		try {
			Controller controller = new Controller(context);
			String json = controller.getDataJSON(url, false);
			Type typeOfT = new TypeToken<ArrayList<KhachHang>>() {}.getType();
			ArrayList<KhachHang> glstData = new Gson().fromJson(json, typeOfT);

			if (glstData.size() > 0) {

				TuyenKhachHangDAL dal = new TuyenKhachHangDAL(context);
				for (KhachHang item : glstData) {

					long resultInsert = dal.add(item);
					if (resultInsert > 0) {
						Log.d("INSERT-TUYEN-KHACH-HANG", "" + resultInsert);

						// Insert TTKH
						//String urlTTKH = Config.GetUrlTTKH(item.getId(), item.getCode(), item.getAssign_id());
						//TTKHDAL.saveToDB(context, urlTTKH);
					} else {
						Log.d("INSERT-TUYEN-KHACH-HANG", "Failure");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Them moi 1 KhachHang vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public long add(KhachHang data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(KhachHang.ID, data.getId());
				values.put(KhachHang.CODE, data.getCode());
				values.put(KhachHang.NAME, data.getName());
				values.put(KhachHang.ASSIGN_ID, data.getAssign_id());
				values.put(KhachHang.ADDRESS, data.getAddress());
				values.put(KhachHang.ROW_STT, data.getRow_stt());
				values.put(KhachHang.IMAGE_URL, data.getImage_url());
				values.put(KhachHang.LATTITUDE, data.getLattitude());
				values.put(KhachHang.LONGTITUDE, data.getLongtitude());
				values.put(KhachHang.STATUS, data.getStatus());
				values.put(KhachHang.ROAD_ID, data.getRoad_id());
				values.put(KhachHang.ROAD_NAME, data.getRoad_name());
				values.put(KhachHang.CHECKIN_TIME, data.getCheckin_time());
				values.put(KhachHang.CHECKOUT_TIME, data.getCheckout_time());

				result = database.insert(KhachHang.TABLE_NAME, null, values);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}
	
	/**
	 * Cap nhat thong 1 KhachHang vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public long update(KhachHang data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(KhachHang.STATUS, data.getStatus());
				values.put(KhachHang.CHECKIN_TIME, data.getCheckin_time());
				values.put(KhachHang.CHECKOUT_TIME, data.getCheckout_time());

				result = database.update(KhachHang.TABLE_NAME, values, "id=?", new String[] {data.getId()});
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}
	
	/**
	 * Cap nhat ten, dia chi KhachHang vao DB
	 * @param id
	 * @param name
	 * @param address
	 * @return
	 */
	public long updateInfoTuyenKH(String id, String name, String address) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(KhachHang.NAME, name);
				values.put(KhachHang.ADDRESS, address);

				result = database.update(KhachHang.TABLE_NAME, values, KhachHang.ID + "=?", new String[] {id});
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * Xoa 1 tuyen KhachHang
	 * @param id
	 * @return
	 */
	public int delete(String id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = KhachHang.ID + " = ?";
				result = database.delete(KhachHang.TABLE_NAME, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}

	/**
	 * Xoa tat ca cac tuyen khach hang
	 * @param id
	 * @return
	 */
	public int deleteAll() {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				result = database.delete(KhachHang.TABLE_NAME, null, null);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Lay tat ca cac tuyen KhachHang offline co trong CSDL
	 * @return
	 */
	public ArrayList<KhachHang> getByAssignId(String id, String status, String name) {
		ArrayList<KhachHang> glstData = new ArrayList<KhachHang>();
		try {
			openDB();
			if (database != null) {
				Controller controller = new Controller(mContext);

				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + KhachHang.TABLE_NAME;
				selectQuery += " WHERE " + KhachHang.ASSIGN_ID + " = ?";

				if (status.equals("1")) {
					selectQuery += " AND " + KhachHang.STATUS + " <> 'HT'";
				} else if (status.equals("2")) {
					selectQuery += " AND " + KhachHang.STATUS + " = 'HT'";
				} else if (!status.equals("")) {
					selectQuery += " AND " + KhachHang.STATUS + " = " + status;
				}

				if (!name.equals("")) {
					if (controller.isNumber(name)) {
						selectQuery += " AND " + KhachHang.CODE + " = '" + name + "'";
					} else {
						selectQuery += " AND " + KhachHang.NAME + " like '%" + name + "%'";
					}
				}

				selectQuery += " ORDER BY " + KhachHang.ROW_STT + " ASC";
				Log.e("SQL", selectQuery);
				cursor = database.rawQuery(selectQuery, new String[] { id });  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						KhachHang item = new KhachHang();
						item = getObject(cursor);


						// Add item to list
						glstData.add(item);
					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return glstData;
	}

	/**
	 * Lay tat ca cac tuyen KhachHang offline co trong CSDL
	 * @return
	 */
	public ArrayList<KhachHang> getAll() {
		ArrayList<KhachHang> glstData = new ArrayList<KhachHang>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + KhachHang.TABLE_NAME;
				selectQuery += " ORDER BY " + KhachHang.ROW_STT + " ASC";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						KhachHang item = new KhachHang();
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

	private KhachHang getObject(Cursor cursor) {
		KhachHang item = new KhachHang();
		try {
			item.setId(cursor.getString(0));
			item.setCode(cursor.getString(1));
			item.setName(cursor.getString(2));
			item.setAssign_id(cursor.getString(3));
			item.setAddress(cursor.getString(4));
			item.setRow_stt(cursor.getString(5));
			item.setImage_url(cursor.getString(6));
			item.setLattitude(cursor.getString(7));
			item.setLongtitude(cursor.getString(8));
			item.setStatus(cursor.getString(9));
			item.setRoad_id(cursor.getString(10));
			item.setRoad_name(cursor.getString(11));
			item.setCheckin_time(cursor.getString(12));
			item.setCheckout_time(cursor.getString(13));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}
}
