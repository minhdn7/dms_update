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
import vn.com.vsc.ptpm.VNPT_DMS.entity.HangHoaEntity;

public class HangHoaDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;
	private Context mContext;

	public HangHoaDAL(Context context) {
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
	 * Ham luu danh sach Hang hoa vao DB
	 * @param context
	 * @param url
	 */
	public static void saveToDB(Context context, String url) {
		try {
			Controller controller = new Controller(context);
			String json = controller.getDataJSON(url, false);
			Type typeOfT = new TypeToken<ArrayList<HangHoaEntity>>() {}.getType();
			ArrayList<HangHoaEntity> glstData = new Gson().fromJson(json, typeOfT);

			if (glstData.size() > 0) {

				HangHoaDAL dal = new HangHoaDAL(context);
				for (HangHoaEntity item : glstData) {

					if (dal.getByProducCatId(item.getProduct_cat_id()) == null) {
						long resultInsert = dal.add(item);
						Log.d("INSERT-HANGHOA-ITEM", "" + resultInsert);
					} else {
						Log.d("HANGHOA-ITEM-EXISIT", "ID: " + item.getProduct_cat_id() + " is exist");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Them moi 1 mat hang vao DB
	 * param context
	 * @param data
	 * @return
	 */
	public long add(HangHoaEntity data) { 

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				values.put(HangHoaEntity.PRODUCT_ID, data.getProduct_id());
				values.put(HangHoaEntity.PRODUCT_CAT_ID, data.getProduct_cat_id());
				values.put(HangHoaEntity.TOTAL_VOTE, data.getTotal_vote());
				values.put(HangHoaEntity.GIA_BAN, data.getGia_ban());
				values.put(HangHoaEntity.SOLUONGBAN, data.getSoluongban());
				values.put(HangHoaEntity.PROMOTION_AMOUNT, data.getPromotion_amount());
				values.put(HangHoaEntity.DS_DONVI, data.getDs_donvi());
				values.put(HangHoaEntity.CODE, data.getCode());
				values.put(HangHoaEntity.URL, data.getUrl());
				values.put(HangHoaEntity.AVG_POINT, data.getAvg_point());
				values.put(HangHoaEntity.ROW_STT, data.getRow_stt());
				values.put(HangHoaEntity.UNIT, data.getUnit());
				values.put(HangHoaEntity.THUE, data.getThue());
				values.put(HangHoaEntity.TOTAL_COMMENT, data.getTotal_comment());
				values.put(HangHoaEntity.GIA_TRUOCTHUE, data.getGia_truocthue());
				values.put(HangHoaEntity.NAME, data.getName());
				values.put(HangHoaEntity.GIA_BAN, data.getGia_ban());
				values.put(HangHoaEntity.SOLUONG, data.getSoluong());
				values.put(HangHoaEntity.TAX_RATE, data.getTax_rate());
				values.put(HangHoaEntity.DATE, data.getDate());
				values.put(HangHoaEntity.UNIT_DISPLAY, data.getUnit_display());


				result = database.insert(HangHoaEntity.TABLE_NAME, null, values);
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
	public int delete(int id) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = HangHoaEntity.PRODUCT_CAT_ID + " = ?";
				result = database.delete(HangHoaEntity.TABLE_NAME, where, new String[] { String.valueOf(id) });
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
	public HangHoaEntity getByProducCatId(String id) {
		HangHoaEntity item = new HangHoaEntity();
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			if (db != null) {
				String[] columns = new String[] { HangHoaEntity.PRODUCT_ID, HangHoaEntity.PRODUCT_CAT_ID};

				Cursor cursor = db.query(HangHoaEntity.TABLE_NAME,
						columns, "product_cat_id=?", new String[] { String.valueOf(id) }, null, null, null, null);

				if (cursor != null && cursor.getCount() > 0) {
					cursor.moveToFirst();

					item.setProduct_id(cursor.getString(0));
					item.setProduct_cat_id(cursor.getString(1));

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
	 * Lay tat ca hang hoa offline co trong CSDL
	 * @return
	 */
	public ArrayList<HangHoaEntity> getAll() {
		ArrayList<HangHoaEntity> glstHangHoa = new ArrayList<HangHoaEntity>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + HangHoaEntity.TABLE_NAME;;
				//selectQuery += " ORDER BY " + HangHoaEntity.DATE;
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						HangHoaEntity item = new HangHoaEntity();
						item = getObject(cursor);

						// Add item to list
						glstHangHoa.add(item);
					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return glstHangHoa;
	}
	
	/**
	 * Lay tat ca hang hoa offline co trong CSDL
	 * @return
	 */
	public ArrayList<HangHoaEntity> getByFilter(String name) {
		ArrayList<HangHoaEntity> glstHangHoa = new ArrayList<HangHoaEntity>();
		try {
			openDB();
			if (database != null) {
				Controller controller = new Controller(mContext);
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + HangHoaEntity.TABLE_NAME;;
				
				if (controller.isNumber(name)) {
					selectQuery += " WHERE " + HangHoaEntity.PRODUCT_CAT_ID + " = '" + name + "'";
				} else {
					selectQuery += " WHERE " + HangHoaEntity.NAME + " like '%" + name + "%'";
				}
				
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						HangHoaEntity item = new HangHoaEntity();
						item = getObject(cursor);

						// Add item to list
						glstHangHoa.add(item);
					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return glstHangHoa;
	}


	/**
	 * Lay hang hoa offline co trong CSDL
	 * @return
	 */
	public ArrayList<HangHoaEntity> getHangHoa(boolean isTrongKho, int page_no, int item_per_page) {
		ArrayList<HangHoaEntity> glstHangHoa = new ArrayList<HangHoaEntity>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + HangHoaEntity.TABLE_NAME;;

				int beginPage, pageSize;
				beginPage = (page_no-1)*item_per_page;
				pageSize = item_per_page;

				selectQuery += " ORDER BY date DESC";
				selectQuery += " LIMIT " + beginPage + ", " + pageSize;
				cursor = database.rawQuery(selectQuery, null);  

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						HangHoaEntity item = new HangHoaEntity();
						item = getObject(cursor);

						// Add item to list
						glstHangHoa.add(item);
					} while (cursor.moveToNext());
				}
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return glstHangHoa;
	}

	private HangHoaEntity getObject(Cursor cursor) {
		HangHoaEntity item = new HangHoaEntity();
		try {
			item.setProduct_id(cursor.getString(0));
			item.setProduct_cat_id(cursor.getString(1));
			item.setTotal_vote(cursor.getString(2));
			item.setGiaban(cursor.getString(3));
			item.setSoluongban(cursor.getString(4));
			item.setPromotion_amount(cursor.getString(5));
			item.setDs_donvi(cursor.getString(6));
			item.setCode(cursor.getString(7));
			item.setUrl(cursor.getString(8));
			item.setAvg_point(cursor.getString(9));
			item.setRow_stt(cursor.getString(10));
			item.setUnit(cursor.getString(11));
			item.setThue(cursor.getString(12));
			item.setTotal_comment(cursor.getString(13));
			item.setGia_truocthue(cursor.getString(14));
			item.setName(cursor.getString(15));
			item.setGia_ban(cursor.getString(16));
			item.setSoluong(cursor.getString(17));
			item.setTax_rate(cursor.getString(18));
			item.setDate(cursor.getInt(19));
			item.setUnit_display(cursor.getString(20));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	} 
}
