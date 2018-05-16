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
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHang;

public class DonHangDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public DonHangDAL(Context context) {
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
	 * Ham luu danh sach Hang hoa vao DB
	 * @param context
	 * @param url
	 */
	public static void saveToDB(Context context, String url) {
		try {
			Controller controller = new Controller(context);
			String json = controller.getDataJSON(url, false);
			Type typeOfT = new TypeToken<ArrayList<DonHang>>() {}.getType();
			ArrayList<DonHang> glstData = new Gson().fromJson(json, typeOfT);

			if (glstData.size() > 0) {

				DonHangDAL dal = new DonHangDAL(context);
				for (DonHang item : glstData) {

					if (dal.getById(item.getDonnhap_id()) == null) {
						long resultInsert = dal.add(item);
						Log.d("INSERT-DONHANG-ITEM", "" + resultInsert);
					} else {
						Log.d("DONHANG-ITEM-EXISIT", "ID: " + item.getDonnhap_id() + " is exist");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Them moi 1 mat hang vao DB
	 * @param context
	 * @param data
	 * @return
	 */
	public long add(DonHang data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();

				values.put(DonHang.ORDERER_NAME, data.getOrderer_name());
				values.put(DonHang.TRANGTHAI, data.getTrangthai());
				values.put(DonHang.TRANGTHAI_ID, data.getTrangthai_id());
				values.put(DonHang.SUPPLIER_NAME, data.getSupplier_name());
				values.put(DonHang.NGAY_LAP, data.getNgay_lap());
				values.put(DonHang.NGUOI_LAP, data.getNguoi_lap());
				values.put(DonHang.ORDERER_CODE, data.getOrderer_code());
				values.put(DonHang.TONG_KHUYENMAI, data.getTong_khuyenmai());
				values.put(DonHang.DONNHAP_ID, data.getDonnhap_id());
				values.put(DonHang.TONGTIEN, data.getTongtien());
				values.put(DonHang.NOTE, data.getNote());

				result = database.insert(DonHang.TABLE_NAME, null, values);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}

		return result;
	}

	/**
	 * Xoa 1 mat hang
	 * @param id
	 * @return
	 */
	public int delete(String id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = DonHang.DONNHAP_ID + " = ?";
				result = database.delete(DonHang.TABLE_NAME, where, new String[] { String.valueOf(id) });
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}
	
	
	/**
	 * Xoa tat ca don hang
	 * @return
	 */
	public int deleteAll() {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				result = database.delete(DonHang.TABLE_NAME, null, null);
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}


	/**
	 * Ham lay Hang hoa qua id
	 * @param id
	 * @return
	 */
	public DonHang getById(String id) {
		DonHang item = new DonHang();
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			if (db != null) {
				//String[] columns = new String[] { DonHang.DONNHAP_ID};

				Cursor cursor = db.query(DonHang.TABLE_NAME,
						null, DonHang.DONNHAP_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);

				if (cursor != null && cursor.getCount() > 0) {
					cursor.moveToFirst();

					item.setDonnhap_id(cursor.getString(0));

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
	 * Lay tat ca don hang offline co trong CSDL
	 * @return
	 */
	public ArrayList<DonHang> getAll() {
		ArrayList<DonHang> glstData = new ArrayList<DonHang>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + DonHang.TABLE_NAME;;
				//selectQuery += " ORDER BY " + DonHang.DATE;
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						DonHang item = new DonHang();
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
	public ArrayList<DonHang> getAllByOrderCode(String ordercode) {
		ArrayList<DonHang> glstData = new ArrayList<DonHang>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + DonHang.TABLE_NAME;;
				selectQuery += " WHERE " + DonHang.ORDERER_CODE + " = '" + ordercode + "'";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						DonHang item = new DonHang();
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
	 * Lay don hang offline co trong CSDL
	 * @return
	 */
	public ArrayList<DonHang> getListDonHang(String id) {
		ArrayList<DonHang> glstData = new ArrayList<DonHang>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + DonHang.TABLE_NAME;
				selectQuery += " WHERE " + DonHang.DONNHAP_ID + "= '" + id + "'";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						DonHang item = new DonHang();
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
	 * Lay don hang offline co trong CSDL
	 * @return
	 */
	public ArrayList<DonHang> getListDonHangByOrderCode(String ordercode, String id) {
		ArrayList<DonHang> glstData = new ArrayList<DonHang>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + DonHang.TABLE_NAME;
				selectQuery += " WHERE " + DonHang.ORDERER_CODE + "= '" + ordercode + "'";
				selectQuery += " AND " + DonHang.DONNHAP_ID + "= '" + id + "'";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						DonHang item = new DonHang();
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
	 * Lay don hang offline co trong CSDL
	 * @return
	 */
	@SuppressWarnings("static-access")
	public ArrayList<DonHang> getListDonHang(String id, String tungay, String denngay, String tensp, String nguoilap, String trangthai, String ncc) {
		ArrayList<DonHang> glstData = new ArrayList<DonHang>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String nhacc = new ConvertFont().GetNCRDecimalString(ncc);
				String nguoiLap = new ConvertFont().GetNCRDecimalString(nguoilap);
				
				String selectQuery = "SELECT * FROM " + DonHang.TABLE_NAME;
				selectQuery += " WHERE " + DonHang.TRANGTHAI_ID + "= '" + trangthai + "'";

				if (!id.equals(""))
					selectQuery += " AND " + DonHang.DONNHAP_ID + "= '" + id + "'";
				if (!nguoiLap.equals(""))
					selectQuery += " AND " + DonHang.NGUOI_LAP + " like '%" + nguoiLap + "%'";
				if (!nhacc.equals(""))
					selectQuery += " AND " + DonHang.SUPPLIER_NAME + " like '%" + nhacc + "%'";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						DonHang item = new DonHang();
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
	 * Lay don hang offline co trong CSDL
	 * @return
	 */
	@SuppressWarnings("static-access")
	public ArrayList<DonHang> getListDonHang(String ordercode, String id, String tungay, String denngay, String tensp, String nguoilap, String trangthai, String ncc) {
		ArrayList<DonHang> glstData = new ArrayList<DonHang>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String nhacc = new ConvertFont().GetNCRDecimalString(ncc);
				String nguoiLap = new ConvertFont().GetNCRDecimalString(nguoilap);
				
				String selectQuery = "SELECT * FROM " + DonHang.TABLE_NAME;
				selectQuery += " WHERE " + DonHang.ORDERER_CODE + "= '" + ordercode + "'";
				selectQuery += " AND " + DonHang.TRANGTHAI_ID + "= '" + trangthai + "'";

				if (!id.equals(""))
					selectQuery += " AND " + DonHang.DONNHAP_ID + "= '" + id + "'";
				if (!nguoiLap.equals(""))
					selectQuery += " AND " + DonHang.NGUOI_LAP + " like '%" + nguoiLap + "%'";
				if (!nhacc.equals(""))
					selectQuery += " AND " + DonHang.SUPPLIER_NAME + " like '%" + nhacc + "%'";
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						DonHang item = new DonHang();
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

	private DonHang getObject(Cursor cursor) {
		DonHang item = new DonHang();
		try {
			item.setOrderer_name(cursor.getString(0));
			item.setTrangthai(cursor.getString(1));
			item.setTrangthai_id(cursor.getString(2));
			item.setSupplier_name(cursor.getString(3));
			item.setNgay_lap(cursor.getString(4));
			item.setNguoi_lap(cursor.getString(5));
			item.setOrderer_code(cursor.getString(6));
			item.setTong_khuyenmai(cursor.getString(7));
			item.setDonnhap_id(cursor.getString(8));
			item.setTongtien(cursor.getString(9));
			item.setNote(cursor.getString(10));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}
}
