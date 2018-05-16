package vn.com.vsc.ptpm.VNPT_DMS.control;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLiteData {

	public static final String DATABASE_NAME = "QLNVKD.db";
	public SQLiteDatabase db;

	public static final String CREATE_TABLE_TAIKHOAN = "Create table if not exists TaiKhoan("+
			"_id integer primary key AUTOINCREMENT, " +
			"username varchar, " +
			"password varchar, " +
			"isSave bool, " +
			"user_name varchar, "+
			"language_id varchar, " +
			"active_shop_code varchar, "+
			"active_shop_id varchar, " +
			"mobile_theme_id varchar, "+
			"UNIQUE (username, password, isSave, user_name, language_id, active_shop_code, active_shop_id, mobile_theme_id))";

	public static final String CREATE_TABLE_KHACHHANG = "Create table if not exists KhachHang("+
			"_id integer primary key AUTOINCREMENT, "+
			"id varchar, " +
			"new_code varchar, " +
			"name varchar,"+
			"mgr varchar, " +
			"fax varchar, " +
			"mobile varchar, "+
			"email varchar, " +
			"addr varchar, " +
			"tax varchar, " +
			"end_id varchar, "+
			"note varchar, " +
			"lattitude double, " +
			"longtitude double);";

	public static final String CREATE_TABLE_TYPECOMPANY = "Create table if not exists TypeCompany("+
			"_id integer primary key AUTOINCREMENT, " +
			"id int, " +
			"name varchar,"+
			"UNIQUE (id, name));";

	public static final String CREATE_TABLE_HANGHOA = "Create table if not exists HangHoa("+
			"_id integer primary key AUTOINCREMENT, "+
			"total_vote varchar, " +
			"giaban varchar, " +
			"product_id varchar, "+
			"product_cat_id varchar, " +
			"soluongban varchar, " +
			"promotion_amount varchar, " +
			"ds_donvi varchar, "+
			"code varchar, " +
			"url varchar, " +
			"avg_point varchar, " +
			"row_stt varchar, " +
			"unit varchar, " +
			"is_not_display_wh_remain varchar, "+ 
			"thue varchar, " +
			"total_comment varchar, " +
			"gia_truocthue varchar, " +
			"name varchar, " +
			"gia_ban varchar, " +
			"soluong varchar, " +
			"tax_rate varchar);";

	public SQLiteData(Context context) {
		db = context.openOrCreateDatabase(DATABASE_NAME, 0, null);
	}

	public void createTable() {
		db.execSQL(CREATE_TABLE_TAIKHOAN);
		db.execSQL(CREATE_TABLE_KHACHHANG);
		db.execSQL(CREATE_TABLE_TYPECOMPANY);
		db.execSQL(CREATE_TABLE_HANGHOA);
	}

	public boolean queryTable(String query) {
		try {
			db.execSQL(query);
		} catch (SQLException e) {
			Log.i("SQLite not insert : ", e.toString());
			return false;
		}
		return true;
	}

	public boolean isOpen() {
		try {
			if (db.isOpen()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void close() {
//		if (db.isOpen()) {
//			db.close();
//		}
	}


	/*public String getLastIDRecord() {
		Cursor c = db.rawQuery("SELECT MAX(_id) from KhachHang", null);
		c.moveToFirst();
		return String.valueOf(c.getInt(0));
	}*/

	public String[] getAccount() {
		//"_id integer primary key AUTOINCREMENT, " +
		//"username varchar, " +
		//"password varchar, " +
		//"isSave bool, " +
		
		String[] account = null;
		Cursor c = db.rawQuery("SELECT _id, username, password, isSave, isSave  from TaiKhoan", null);
		if (c.moveToFirst()) {
			String user = c.getString(1);
			String pass = c.getString(2);
			String isSave = c.getString(3);
			account = new String[] { user, pass, isSave };
		}
		c.close();
		return account;
	}

	/*public List<ThemKH> getThemKH() {
		List<ThemKH> list = new ArrayList<ThemKH>();
		Cursor c = db.rawQuery("Select * from KhachHang", null);
		if (c.moveToFirst()) {
			do {
				String new_code = c.getString(2);
				String name = c.getString(3);
				String mgr = c.getString(4);
				String fax = c.getString(5);
				String mobile = c.getString(6);
				String email = c.getString(7);
				String addr = c.getString(8);
				String tax = c.getString(9);
				String end_id = c.getString(10);
				String note = c.getString(11);
				Double lattitude = c.getDouble(12);
				Double longtitude = c.getDouble(13);
				list.add(new ThemKH(new_code, name, mgr, mobile, fax, email, addr, tax, end_id, note, lattitude, longtitude));
			} while (c.moveToNext());
		}
		return list;
	}*/

	/*public List<TypeCompany> getTypeCompany() {
		List<TypeCompany> list = new ArrayList<TypeCompany>();
		Cursor c = db.rawQuery("Select * from TypeCompany", null);
		if (c.moveToFirst()) {
			do {
				String id = c.getString(1);
				String type = c.getString(2);
				list.add(new TypeCompany(id, type));
			} while (c.moveToNext());
		}
		return list;
	}*/
	
	
	
}