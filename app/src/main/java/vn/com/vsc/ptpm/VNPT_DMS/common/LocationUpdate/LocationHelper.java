package vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate;

import java.util.ArrayList;

import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.database.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class LocationHelper {
	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;
	private Context context;
	private int countSaveDB = 0;

	public LocationHelper(Context context) {
		this.context = context;
		dbHelper = new DatabaseHandler(context);
	}

	public void openDB() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void closeDB() {
//        if (database != null)
//            database.close();
//        if (dbHelper != null)
//            dbHelper.close();
	}

	public long add(LocationModel data) {

		long result = 0;
		try {
			openDB();
			Log.e("Save DB= ", countSaveDB + "");
			if (database != null) {
				ContentValues values = new ContentValues();
				// values.put(LocationModel.ID, data.getId());
				values.put(LocationModel.CODE, data.getCode());
				values.put(LocationModel.ASSIGN, data.getAssign());
				values.put(LocationModel.LATITUDE, data.getLat());
				values.put(LocationModel.LONGITUDE, data.getLng());
				values.put(LocationModel.TIME, data.getTime());
				values.put(LocationModel.LEVEL_BATTERY, data.getPin());
				values.put(LocationModel.NETWORK_TYPE, data.getType_network());
				values.put(LocationModel.SIGNAL_STRENGTH, data.getValue());
				values.put(LocationModel.SEND_STATUS, data.getSend_status());
				values.put(LocationModel.DISTANCE, data.getDistance());
				values.put(LocationModel.ACCURACY, data.getAccuracy());
				values.put(LocationModel.HAS_ACCURACY, data.getHasAccuracy());

				result = database
						.insert(LocationModel.TABLE_NAME, null, values);
			}
		} catch (Exception e) {
			e.printStackTrace();
			countSaveDB += 1;
			if(countSaveDB == 5){
				countSaveDB = 0;
//				removeAll();
			}
			result = -2;
		} finally {
			closeDB();
		}

		return result;
	}

	public int deleteSended() {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String q = "";
				q = "delete from infodevice where send_status=1";
				database.execSQL(q);

//                q = "delete from infodevice where distance >= 300";
//                database.execSQL(q);
//				q = "WITH CTE AS";
//				database.execSQL(q);
//				q = "(SELECT *,RN=ROW_NUMBER() OVER(PARTITION BY time ORDER BY id) FROM infodevice)";
//				database.execSQL(q);
//				q = "DELETE FROM CTE WHERE RN>1";
				q = "DELETE FROM infodevice WHERE id NOT IN (SELECT MIN(id) as id FROM infodevice GROUP BY lat,lng,time)";
				database.execSQL(q);
			}

		} catch (Exception e) {
			result = -2;
		} finally {
			closeDB();
		}
		return result;
	}

	public void removeAll() {
		database = dbHelper.getWritableDatabase();
		if (database != null) {
			database.execSQL("delete from infodevice");
		}
	}

	public int delete(String time) {
		int result = 0;
		try {
			openDB();
			if (database != null) {
				String where = LocationModel.TIME + " = ?";
				result = database.delete(LocationModel.TABLE_NAME, where,
						new String[]{String.valueOf(time)});
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		} finally {
			closeDB();
		}
		return result;
	}

	public ArrayList<LocationModel> getAll() {
		ArrayList<LocationModel> glstData = new ArrayList<LocationModel>();
		try {
			openDB();
			if (database != null) {
				Cursor cursor = null;
				String selectQuery = "SELECT * FROM "
						+ LocationModel.TABLE_NAME;
				selectQuery += " ORDER BY " + LocationModel.TIME + " DESC";
				cursor = database.rawQuery(selectQuery, null);

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						LocationModel item = cursorToObject(cursor);
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

	public ArrayList<LocationModel> getAccuracyData() {
		ArrayList<LocationModel> glstData = new ArrayList<LocationModel>();
		Cursor cursor = null;
		try {
			openDB();
			if (database != null) {
				String selectQuery = "SELECT * FROM "
						+ LocationModel.TABLE_NAME;
				selectQuery += " WHERE " + LocationModel.SEND_STATUS + "=0 "
						+ "AND " + LocationModel.ASSIGN + "=\'" + Config.username
//						+ "\' ORDER BY " + LocationModel.ID + " ASC" + " LIMIT 400";
						+ "\' ORDER BY " + LocationModel.TIME + " DESC" + " LIMIT 400";
				cursor = database.rawQuery(selectQuery, null);

				if (cursor.moveToFirst()) {
					do {
						LocationModel item = cursorToObject(cursor);
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

	public int countCases() {
		Cursor cursor = null;
		int count = 0;
		try {
			openDB();
			if (database != null) {
				String selectQuery = "SELECT COUNT(" + LocationModel.ID + ") FROM " + LocationModel.TABLE_NAME + ";";
				cursor = database.rawQuery(selectQuery, null);
				cursor.moveToFirst();
				count = cursor.getInt(0);
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			closeDB();
		}
		return count;
	}

	private LocationModel cursorToObject(Cursor cursor) {
		LocationModel object = new LocationModel();

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

	public LocationModel getInfoDevice(int id) {
		String selectQuery = "SELECT * FROM " + LocationModel.TABLE_NAME
				+ " WHERE " + LocationModel.ID + " =?";
		LocationModel entity = null;
		try {
			openDB();
			if (database != null) {
				Cursor cursor = database.rawQuery(selectQuery,
						new String[]{String.valueOf(id)});
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

	public LocationModel getLastInfoDevice() {
		LocationModel entity = null;
		try {
			String selectQuery = "SELECT * FROM " + LocationModel.TABLE_NAME
					+ " ORDER BY " + LocationModel.ID + " DESC LIMIT 1";
			Log.d("TAG LocationModel", selectQuery);
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

	public int updateSendStatus(ArrayList<LocationModel> list) {
		try {
			openDB();
			if (database != null) {
				database.beginTransaction();
				String query = "UPDATE " + LocationModel.TABLE_NAME + " SET "
						+ LocationModel.SEND_STATUS + "=? WHERE "
						+ LocationModel.ID + "=?";
				SQLiteStatement updateStmt = database.compileStatement(query);
				for (int i = 0; i < list.size(); i++) {
					LocationModel item = list.get(i);
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
		String countQuery = "SELECT * FROM " + LocationModel.TABLE_NAME;
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
