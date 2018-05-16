package vn.com.vsc.ptpm.VNPT_DMS.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import vn.com.vsc.ptpm.VNPT_DMS.entity.DonHangDeleteEntity;
import vn.com.vsc.ptpm.VNPT_DMS.entity.HangHoaEntity;
import vn.com.vsc.ptpm.VNPT_DMS.entity.InfoDeviceEntity;
import vn.com.vsc.ptpm.VNPT_DMS.entity.SanPhamDonHangEntity;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.BinhLuan;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Checkin;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DatHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.HTTT;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhaoSat;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TTKH;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TenKH;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.ThemKH;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Tuyen;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TypeCompany;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.UpdateGPSKH;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "quanlynhanvienkinhdoanh.db";

	// Table Tuyen
	private static final String SQL_CREATE_TABLE_TUYEN = "CREATE TABLE "
			+ Tuyen.TABLE_NAME + "(" + Tuyen.NO_OF_NONE + " TEXT, "
			+ Tuyen.IS_FINISH + " TEXT, " + Tuyen.IS_ACTIVE + " TEXT, "
			+ Tuyen.CODE + " TEXT, " + Tuyen.SELLER + " TEXT, "
			+ Tuyen.TOTAL_OF_ORDER + " TEXT, " + Tuyen.ID + " TEXT, "
			+ Tuyen.ROW_STT + " TEXT, " + Tuyen.END_DATE + " TEXT, "
			+ Tuyen.NO_OF_DONE + " TEXT, " + Tuyen.NO_OF_ORDERED_CUSTOMER
			+ " TEXT, " + Tuyen.NO_OF_ORDER + " TEXT, " + Tuyen.NAME
			+ " TEXT, " + Tuyen.START_DATE + " TEXT)";

	// Table Tuyen Khach hang
	private static final String SQL_CREATE_TABLE_TUYEN_KHACH_HANG = "CREATE TABLE "
			+ KhachHang.TABLE_NAME
			+ "("
			+ KhachHang.ID
			+ " TEXT, "
			+ KhachHang.CODE
			+ " TEXT, "
			+ KhachHang.NAME
			+ " TEXT, "
			+ KhachHang.ASSIGN_ID
			+ " TEXT, "
			+ KhachHang.ADDRESS
			+ " TEXT, "
			+ KhachHang.ROW_STT
			+ " TEXT, "
			+ KhachHang.IMAGE_URL
			+ " TEXT, "
			+ KhachHang.LATTITUDE
			+ " TEXT, "
			+ KhachHang.LONGTITUDE
			+ " TEXT, "
			+ KhachHang.STATUS
			+ " TEXT, "
			+ KhachHang.ROAD_ID
			+ " TEXT, "
			+ KhachHang.ROAD_NAME
			+ " TEXT, "
			+ KhachHang.CHECKIN_TIME
			+ " TEXT, "
			+ KhachHang.CHECKOUT_TIME
			+ " TEXT )";

	// Table ThongTinKhachHang
	private static final String SQL_CREATE_TABLE_THONG_TIN_KHACH_HANG = "CREATE TABLE "
			+ ThemKH.TABLE_NAME
			+ "("
			+ ThemKH._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ThemKH.ID
			+ " TEXT, "
			+ ThemKH.NEW_CODE
			+ " TEXT, "
			+ ThemKH.CODE
			+ " TEXT, "
			+ ThemKH.NAME
			+ " TEXT, "
			+ ThemKH.MANAGER
			+ " TEXT, "
			+ ThemKH.MOBILE
			+ " TEXT, "
			+ ThemKH.TEL
			+ " TEXT, "
			+ ThemKH.FAX
			+ " TEXT, "
			+ ThemKH.EMAIL
			+ " TEXT, "
			+ ThemKH.ADDRESS
			+ " TEXT, "
			+ ThemKH.TAX
			+ " TEXT, "
			+ ThemKH.ENT_ID
			+ " TEXT, "
			+ ThemKH.NOTE
			+ " TEXT, "
			+ ThemKH.ASSIGN
			+ " TEXT, "
			+ ThemKH.TYPE
			+ " INTEGER, "
			+ ThemKH.LATTITUDE
			+ " DOUBLE, "
			+ ThemKH.LONGTITUDE
			+ " DOUBLE)";

	// Table TTKH
	private static final String SQL_CREATE_TABLE_TTKH = "CREATE TABLE "
			+ TTKH.TABLE_NAME + "(" + TTKH.ID + " TEXT, " + TTKH.CODE
			+ " TEXT, " + TTKH.NAME + " TEXT, " + TTKH.MANAGER + " TEXT, "
			+ TTKH.MOBILE + " TEXT, " + TTKH.TEL + " TEXT, " + TTKH.FAX
			+ " TEXT, " + TTKH.EMAIL + " TEXT, " + TTKH.ADDRESS + " TEXT, "
			+ TTKH.ADDRESS_ID + " TEXT, " + TTKH.TAX_CODE + " TEXT, "
			+ TTKH.NOTE + " TEXT, " + TTKH.LATTITUDE + " DOUBLE, "
			+ TTKH.LONGTITUDE + " DOUBLE)";

	// Table HangHoa
	private static final String SQL_CREATE_TABLE_HANG_HOA = "CREATE TABLE "
			+ HangHoaEntity.TABLE_NAME + "(" + HangHoaEntity.PRODUCT_ID
			+ " TEXT, " + HangHoaEntity.PRODUCT_CAT_ID + " TEXT, "
			+ HangHoaEntity.TOTAL_VOTE + " TEXT, " + HangHoaEntity.GIABAN
			+ " TEXT, " + HangHoaEntity.SOLUONGBAN + " TEXT, "
			+ HangHoaEntity.PROMOTION_AMOUNT + " TEXT, "
			+ HangHoaEntity.DS_DONVI + " TEXT, " + HangHoaEntity.CODE
			+ " TEXT, " + HangHoaEntity.URL + " TEXT, "
			+ HangHoaEntity.AVG_POINT + " TEXT, " + HangHoaEntity.ROW_STT
			+ " TEXT, " + HangHoaEntity.UNIT + " TEXT, " + HangHoaEntity.THUE
			+ " TEXT, " + HangHoaEntity.TOTAL_COMMENT + " TEXT, "
			+ HangHoaEntity.GIA_TRUOCTHUE + " TEXT, " + HangHoaEntity.NAME
			+ " TEXT, " + HangHoaEntity.GIA_BAN + " TEXT, "
			+ HangHoaEntity.SOLUONG + " TEXT, " + HangHoaEntity.TAX_RATE
			+ " TEXT, " + HangHoaEntity.DATE + " INTEGER)";

	// Table DonHang
	private static final String SQL_CREATE_TABLE_DON_HANG = "CREATE TABLE "
			+ DonHang.TABLE_NAME + "(" + DonHang.ORDERER_NAME + " TEXT, "
			+ DonHang.TRANGTHAI + " TEXT, " + DonHang.TRANGTHAI_ID + " TEXT, "
			+ DonHang.SUPPLIER_NAME + " TEXT, " + DonHang.NGAY_LAP + " TEXT, "
			+ DonHang.NGUOI_LAP + " TEXT, " + DonHang.ORDERER_CODE + " TEXT, "
			+ DonHang.TONG_KHUYENMAI + " TEXT, " + DonHang.DONNHAP_ID
			+ " TEXT, " + DonHang.TONGTIEN + " TEXT, " + DonHang.NOTE
			+ " TEXT)";

	// Table TypeCompany
	private static final String SQL_CREATE_TABLE_TYPE_COMPANY = "CREATE TABLE "
			+ TypeCompany.TABLE_NAME + "(" + TypeCompany.ID + " TEXT, "
			+ TypeCompany.NAME + " TEXT)";

	// Table Ten KhachHang
	private static final String SQL_CREATE_TABLE_KHACH_HANG = "CREATE TABLE "
			+ TenKH.TABLE_NAME + "(" + TenKH.ID + " TEXT, " + TenKH.NAME
			+ " TEXT)";

	// Table NhaCungCap
	private static final String SQL_CREATE_TABLE_NHA_CUNG_CAP = "CREATE TABLE "
			+ TenKH.TABLE_NAME_NCC + "(" + TenKH.ID + " TEXT, " + TenKH.NAME
			+ " TEXT)";

	// Table NhaCungCap
	private static final String SQL_CREATE_TABLE_TRANG_THAI_DON_HANG = "CREATE TABLE "
			+ TenKH.TABLE_NAME_TTDH
			+ "("
			+ TenKH.ID
			+ " TEXT, "
			+ TenKH.NAME
			+ " TEXT)";

	// Table HinhThucThanhToan
	private static final String SQL_CREATE_TABLE_HINH_THUC_THANH_TOAN = "CREATE TABLE "
			+ HTTT.TABLE_NAME
			+ "("
			+ HTTT.HTTT_ID
			+ " TEXT, "
			+ HTTT.HINHTHUC
			+ " TEXT)";

	// Table Checkin
	private static final String SQL_CREATE_TABLE_CHECKIN = "CREATE TABLE "
			+ Checkin.TABLE_NAME + "(" + Checkin.ID + " TEXT, " + Checkin.CODE
			+ " TEXT, " + Checkin.ASSIGN + " TEXT, " + Checkin.LNG + " TEXT, "
			+ Checkin.LAT + " TEXT, " + Checkin.CHECKTYPE + " TEXT, "
			+ Checkin.TIME + " TEXT)";

	// Table Update Location
	private static final String SQL_CREATE_TABLE_UPDATE_LOCATION = "CREATE TABLE "
			+ UpdateGPSKH.TABLE_NAME
			+ "("
			+ UpdateGPSKH.ID
			+ " TEXT, "
			+ UpdateGPSKH.CODE
			+ " TEXT, "
			+ UpdateGPSKH.LNG
			+ " INTEGER, "
			+ UpdateGPSKH.LAT
			+ " INTEGER, "
			+ UpdateGPSKH.UPD
			+ " INTEGER, "
			+ UpdateGPSKH.ASSIGN
			+ " TEXT, "
			+ UpdateGPSKH.TIME
			+ " INTEGER)";

	// Table InfoDevice
	private static final String SQL_CREATE_TABLE_INFO_DEVICE = "CREATE TABLE "
			+ InfoDeviceEntity.TABLE_NAME + "("
			+ InfoDeviceEntity.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ InfoDeviceEntity.CODE + " TEXT, "
			+ InfoDeviceEntity.ASSIGN + " TEXT, "
			+ InfoDeviceEntity.LATITUDE + " INTEGER, "
			+ InfoDeviceEntity.LONGITUDE + " INTEGER, "
			+ InfoDeviceEntity.TIME + " TEXT, "
			+ InfoDeviceEntity.LEVEL_BATTERY + " INTEGER, "
			+ InfoDeviceEntity.NETWORK_TYPE + " TEXT, "
			+ InfoDeviceEntity.SIGNAL_STRENGTH + " INTEGER,"
			+ InfoDeviceEntity.SEND_STATUS + " INTEGER,"
			+ InfoDeviceEntity.DISTANCE + " INTEGER,"
			+ InfoDeviceEntity.ACCURACY + " INTEGER,"
			+ InfoDeviceEntity.HAS_ACCURACY + " INTEGER);";

	// Table DatHang
	private static final String SQL_CREATE_TABLE_DAT_HANG = "CREATE TABLE "
			+ DatHang.TABLE_NAME + "(" + DatHang._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatHang.ID + " TEXT, "
			+ DatHang.DVDH + " TEXT, " + DatHang.NCC + " TEXT, "
			+ DatHang.NGAYDH + " TEXT, " + DatHang.DIENGIAI + " TEXT, "
			+ DatHang.DIACHI + " TEXT, " + DatHang.GHICHU + " TEXT, "
			+ DatHang.NGAYYC + " TEXT, " + DatHang.PHIEUGIAO + " TEXT, "
			+ DatHang.HTTT + " TEXT)";

	// Table SanPhamDonHang
	private static final String SQL_CREATE_TABLE_SAN_PHAM_DON_HANG = "CREATE TABLE "
			+ SanPhamDonHangEntity.TABLE_NAME
			+ "("
			+ SanPhamDonHangEntity._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ SanPhamDonHangEntity.PO_ID
			+ " TEXT, "
			+ SanPhamDonHangEntity.LOAISP_ID
			+ " TEXT, "
			+ SanPhamDonHangEntity.SOLUONG
			+ " TEXT, "
			+ SanPhamDonHangEntity.GIA + " TEXT)";

	// Table BinhLuan
	private static final String SQL_CREATE_TABLE_BINH_LUAN = "CREATE TABLE "
			+ BinhLuan.TABLE_NAME + "(" + BinhLuan.ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + BinhLuan.CREATED_DATE
			+ " TEXT, " + BinhLuan.CREATED_BY + " TEXT, " + BinhLuan.ROW_STT
			+ " TEXT, " + BinhLuan.IMAGE_URL + " TEXT, "
			+ BinhLuan.IMAGE_RELATED_ID + " TEXT, " + BinhLuan.NOTE + " TEXT, "
			+ BinhLuan.STATUS + " INTEGER, " + BinhLuan.ORG_ID + " TEXT, "
			+ BinhLuan.ASSIGN_ID + " TEXT)";

	// Table KhaoSat
	private static final String SQL_CREATE_TABLE_KHAO_SAT = "CREATE TABLE "
			+ KhaoSat.TABLE_NAME + "(" + KhaoSat._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KhaoSat.ID + " TEXT, "
			+ KhaoSat.UPDATED_DATE + " TEXT, " + KhaoSat.JSON_DEFAULT_VALUE
			+ " TEXT, " + KhaoSat.COMPONENT_TYPE + " TEXT, " + KhaoSat.VALUE
			+ " TEXT, " + KhaoSat.UPDATED_BY + " TEXT, " + KhaoSat.CODE
			+ " TEXT, " + KhaoSat.UPDATED_IP + " TEXT, " + KhaoSat.STATUS
			+ " INTEGER, " + KhaoSat.ORG_ID + " TEXT, " + KhaoSat.ORG_CODE
			+ " TEXT, " + KhaoSat.ASSIGN_ID + " TEXT, " + KhaoSat.NAME
			+ " TEXT)";

	// Table DonHangDelete
	private static final String SQL_CREATE_TABLE_DON_HANG_DELETE = "CREATE TABLE "
			+ DonHangDeleteEntity.TABLE_NAME
			+ "("
			+ DonHangDeleteEntity.ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ DonHangDeleteEntity.DONHANGID + " TEXT)";


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE_TUYEN);
		db.execSQL(SQL_CREATE_TABLE_TUYEN_KHACH_HANG);
		db.execSQL(SQL_CREATE_TABLE_THONG_TIN_KHACH_HANG);
		db.execSQL(SQL_CREATE_TABLE_TTKH);

		db.execSQL(SQL_CREATE_TABLE_TYPE_COMPANY);
		db.execSQL(SQL_CREATE_TABLE_KHACH_HANG);
		db.execSQL(SQL_CREATE_TABLE_NHA_CUNG_CAP);
		db.execSQL(SQL_CREATE_TABLE_TRANG_THAI_DON_HANG);
		db.execSQL(SQL_CREATE_TABLE_HINH_THUC_THANH_TOAN);

		db.execSQL(SQL_CREATE_TABLE_DON_HANG);
		db.execSQL(SQL_CREATE_TABLE_HANG_HOA);

		db.execSQL(SQL_CREATE_TABLE_CHECKIN);
		db.execSQL(SQL_CREATE_TABLE_UPDATE_LOCATION);
		db.execSQL(SQL_CREATE_TABLE_INFO_DEVICE);
		Log.d("TABLE",SQL_CREATE_TABLE_INFO_DEVICE);
		db.execSQL(SQL_CREATE_TABLE_DAT_HANG);
		db.execSQL(SQL_CREATE_TABLE_SAN_PHAM_DON_HANG);
		db.execSQL(SQL_CREATE_TABLE_BINH_LUAN);
		db.execSQL(SQL_CREATE_TABLE_KHAO_SAT);
		db.execSQL(SQL_CREATE_TABLE_DON_HANG_DELETE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHandler.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");

		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + Tuyen.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + KhachHang.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + ThemKH.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TTKH.TABLE_NAME);
		
		db.execSQL("DROP TABLE IF EXISTS " + TypeCompany.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TenKH.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TenKH.TABLE_NAME_NCC);
		db.execSQL("DROP TABLE IF EXISTS " + HTTT.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TenKH.TABLE_NAME_TTDH);
		
		db.execSQL("DROP TABLE IF EXISTS " + DonHang.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + HangHoaEntity.TABLE_NAME);

		db.execSQL("DROP TABLE IF EXISTS " + Checkin.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + UpdateGPSKH.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + InfoDeviceEntity.TABLE_NAME);
		
		db.execSQL("DROP TABLE IF EXISTS " + DatHang.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + SanPhamDonHangEntity.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + BinhLuan.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + KhaoSat.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DonHangDeleteEntity.TABLE_NAME);
		// Create tables again
		onCreate(db);
	}
}
