package vn.com.vsc.ptpm.VNPT_DMS.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;
import vn.com.vsc.ptpm.VNPT_DMS.entity.InfoDeviceEntity;

public class InfoDeviceDAL {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;
	private Context context;

	public InfoDeviceDAL(Context context) {
		this.context = context;
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
	 * 
	 * @param data
	 * @return
	 */
	public long add(InfoDeviceEntity data) {

		long result = 0;
		try {
			openDB();
			if (database != null) {
				ContentValues values = new ContentValues();
				// values.put(InfoDeviceEntity.ID, data.getId());
				values.put(InfoDeviceEntity.CODE, data.getCode());
				values.put(InfoDeviceEntity.ASSIGN, data.getAssign());
				values.put(InfoDeviceEntity.LATITUDE, data.getLat());
				values.put(InfoDeviceEntity.LONGITUDE, data.getLng());
				values.put(InfoDeviceEntity.TIME, data.getTime());
				values.put(InfoDeviceEntity.LEVEL_BATTERY, data.getPin());
				values.put(InfoDeviceEntity.NETWORK_TYPE, data.getType_network());
				values.put(InfoDeviceEntity.SIGNAL_STRENGTH, data.getValue());
				values.put(InfoDeviceEntity.SEND_STATUS, data.getSend_status());
				values.put(InfoDeviceEntity.DISTANCE, data.getDistance());

				result = database.insert(InfoDeviceEntity.TABLE_NAME, null, values);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		} finally {
			closeDB();
		}

		return result;
	}

	/**
	 * Xoa cac ban ghi da update va khong hop le
	 * 
	 * @return
	 */
	public int deleteNoACCURACY() {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String q = "";
				q = "delete from infodevice where send_status=1 or distance<=1";
				database.execSQL(q);
				
				q = "delete from infodevice where distance >= 667";
				database.execSQL(q);
			}

		} catch (Exception e) {			
			result = -2;
		} finally {
			closeDB();
		}
		return result;
	}

	/**
	 * Xoa 1 ban ghi
	 * 
	 * @param time
	 * @return
	 */
	public int delete(String time) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = InfoDeviceEntity.TIME + " = ?";
				result = database.delete(InfoDeviceEntity.TABLE_NAME, where, new String[] { String.valueOf(time) });
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		} finally {
			closeDB();
		}
		return result;
	}	
	/**
	 * Lay tat ca thong tin location offline co trong CSDL
	 * 
	 * @return
	 */
	public ArrayList<InfoDeviceEntity> getAll() {
		ArrayList<InfoDeviceEntity> glstData = new ArrayList<InfoDeviceEntity>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM " + InfoDeviceEntity.TABLE_NAME;
				selectQuery += " ORDER BY " + InfoDeviceEntity.TIME + " DESC";
				cursor = database.rawQuery(selectQuery, null);

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						InfoDeviceEntity item = cursorToObject(cursor);
						glstData.add(item);
					} while (cursor.moveToNext());
				}
				cursor.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			closeDB();
		}
		return glstData;
	}

	public ArrayList<InfoDeviceEntity> getAccuracyData() {
		ArrayList<InfoDeviceEntity> glstData = new ArrayList<InfoDeviceEntity>();
		Cursor cursor = null;
		try {
			openDB();
			if (database != null) {
				String selectQuery = "SELECT * FROM " + InfoDeviceEntity.TABLE_NAME;
				selectQuery += " WHERE " + InfoDeviceEntity.SEND_STATUS + "=0 AND " + InfoDeviceEntity.DISTANCE + ">=" + Config.MAX_DISTANCE_ACCURACY + " AND "
						+ InfoDeviceEntity.DISTANCE + "<= 660 " + " ORDER BY " + InfoDeviceEntity.ID + " ASC";
				cursor = database.rawQuery(selectQuery, null);
				if (cursor.moveToFirst()) {
					do {
						InfoDeviceEntity item = cursorToObject(cursor);
						glstData.add(item);
					} while (cursor.moveToNext());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			closeDB();
		}
		return glstData;
	}

	private InfoDeviceEntity cursorToObject(Cursor cursor) {
		InfoDeviceEntity object = new InfoDeviceEntity();

		object.setId(cursor.getInt(0));
		object.setCode(cursor.getString(1));
		object.setAssign(cursor.getString(2));
		object.setLat(cursor.getDouble(3));
		object.setLng(cursor.getDouble(4));
		object.setTime(cursor.getString(5));
		object.setPin(cursor.getInt(6));
		object.setType_network(cursor.getString(7));
		object.setValue(cursor.getInt(8));
		object.setSend_status(cursor.getInt(9));
		object.setDistance(cursor.getDouble(10));
		object.setAccuracy(cursor.getDouble(11));
		object.setHasAccuracy(cursor.getInt(12));

		return object;
	}

	public InfoDeviceEntity getInfoDevice(int id) {
		String selectQuery = "SELECT * FROM " + InfoDeviceEntity.TABLE_NAME + " WHERE " + InfoDeviceEntity.ID + " =?";
		InfoDeviceEntity entity = null;
		try {
			openDB();
			if (database != null) {
				Cursor cursor = database.rawQuery(selectQuery, new String[] { String.valueOf(id) });
				if (cursor.moveToFirst()) {
					do {
						entity = cursorToObject(cursor);
					} while (cursor.moveToNext());
				}
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}

		return entity;
	}

	public InfoDeviceEntity getLastInfoDevice() {
		InfoDeviceEntity entity = null;
		try {
			String selectQuery = "SELECT * FROM " + InfoDeviceEntity.TABLE_NAME + " ORDER BY " + InfoDeviceEntity.ID + " DESC LIMIT 1";
			Log.d("TAG InfoDeviceEntity", selectQuery);
			openDB();
			if (database != null) {
				Cursor cursor = database.rawQuery(selectQuery, null);
				if (cursor.moveToFirst()) {
					do {
						entity = cursorToObject(cursor);
					} while (cursor.moveToNext());
				}
				cursor.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			entity = null;
		} finally {
			closeDB();
		}
		return entity;
	}

	public int updateSendStatus(ArrayList<InfoDeviceEntity> list) {
		try {
			openDB();
			if (database != null) {
				database.beginTransaction();
				String query = "UPDATE " + InfoDeviceEntity.TABLE_NAME + " SET " + InfoDeviceEntity.SEND_STATUS + "=? WHERE " + InfoDeviceEntity.ID + "=?";
				SQLiteStatement updateStmt = database.compileStatement(query);
				for (int i = 0; i < list.size(); i++) {
					InfoDeviceEntity item = list.get(i);
					updateStmt.bindLong(1, 1);
					updateStmt.bindLong(2, item.getId());
					updateStmt.execute();
				}
				database.setTransactionSuccessful();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			database.endTransaction();
			closeDB();
		}
		return 1;
	}

	public int getCount() {
		int count = 0;
		String countQuery = "SELECT * FROM " + InfoDeviceEntity.TABLE_NAME;
		try {
			openDB();
			if (database != null) {
				Cursor cursor = database.rawQuery(countQuery, null);
				count = cursor.getCount();
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return count;
	}
}
