package com.mc.reader.database;



import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Database extends Activity {
	private String log = "Database";
	private DatabaseHelper databaseHelper;
	public SQLiteDatabase sqlite = null;

	public static final int DB_WRITE_AND_READ = 1;
	public static final int DB_READ = 2;

	public Database(Context context) {
		super();

		databaseHelper = new DatabaseHelper(context);

		databaseHelper.initializeDataBase();

		connect(DB_READ);

	}

	/*
	 * connect
	 * 
	 * @description Open connection
	 * 
	 * @return none
	 */
	public void connect(int type) {

		try {
			switch (type) {

			case DB_WRITE_AND_READ:
				sqlite = databaseHelper.getWritableDatabase();
				break;
			case DB_READ:
				sqlite = databaseHelper.getReadableDatabase();
				break;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
	}

	/*
	 * disconnect
	 * 
	 * @description Close connection
	 * 
	 * @return none
	 */
	public void disconnect() {

		try {
			databaseHelper.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sqlite.close();
		}
	}

	/*
	 * setArticle
	 * 
	 * @description Set a setArticle
	 * 
	 * @return none
	 */
	public void setArticle(JSONObject row) {
		try {
			Log.i(log, "setArticle: " + row.getString("_id"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		connect(DB_WRITE_AND_READ);
		String query = "";
		try {
			if (this.getArticle(row.getString("_id")).getCount() > 0) {
			} else {
				query = "INSERT INTO tblArticles VALUES ( '" + row.getString("_id") + "', '" + row.getString("title") + "', '" + row.getString("author") + "', '" + row.getString("content") + "', '" + row.getString("picture") + "', '" + row.getString("type") + "', '" + row.getString("registered") + "' )";
				sqlite.execSQL(query);

			}
		} catch (SQLException e) {
			Log.i("SQLException", e.getMessage());
		} catch (JSONException e) {
			Log.i("JSONException", e.getMessage());
		}

	}

	/*
	 * getArticle
	 * 
	 * @description Return a cursor from id
	 * 
	 * @return Cursor
	 */
	@SuppressWarnings("deprecation")
	public Cursor getArticle(String id) {
		Log.i(log, "getArticle:" + id);
		connect(DB_READ);
		String[] where = new String[] { id };

		String query = "";
		query = "SELECT * FROM tblArticles WHERE article_id = ?";
		Cursor cursor = sqlite.rawQuery(query, where);
		startManagingCursor(cursor);
		return cursor;
	}

	/*
	 * getAllArticle
	 * 
	 * @description
	 * 
	 * @return Cursor
	 */
	@SuppressWarnings("deprecation")
	public Cursor getAllArticle(String type) {
		Log.i(log, "getAllArticle: " + type);
		connect(DB_READ);
		String query;
		if (type.equals("")) {
			query = "SELECT * FROM tblArticles ORDER BY article_registered DESC LIMIT 5";
		} else {
			query = "SELECT * FROM tblArticles WHERE article_type = '"
					+ type + "' ORDER BY article_registered DESC LIMIT 5";
		}
		Cursor cursor = sqlite.rawQuery(query, null);
		startManagingCursor(cursor);
		return cursor;
	}

	/*
	 * setConfig
	 * 
	 * @description Set a config based in config_key
	 * 
	 * @return none
	 */
	public void setConfig(String config_key, String config_value) {
		Log.i(log, "setConfig");
		connect(DB_WRITE_AND_READ);
		String query = "";
		String[] key = new String[] { config_key };
		if (getConfig(key).equals("")) {
			query = "INSERT INTO tblConfig VALUES ( null, '" + config_key
					+ "','" + config_value + "')";
		} else {
			query = "UPDATE tblConfig SET config_value = '" + config_value
					+ "' WHERE config_key = '" + config_key + "'";
		}
		sqlite.execSQL(query);

	}

	/*
	 * deleteConfig
	 * 
	 * @description Delete a row in tblConfig
	 * 
	 * @return none
	 */
	public void deleteConfig(String config_key) {
		Log.i(log, "deleteConfig");
		connect(DB_READ);
		String query = "DELETE FROM tblConfig WHERE config_key = '"
				+ config_key + "'";

		sqlite.execSQL(query);

	}

	/*
	 * getConfig
	 * 
	 * @description Return a String value based in config_key
	 * 
	 * @return String
	 */
	public String getConfig(String[] where) {
		Log.i(log, "getConfig");
		connect(DB_READ);
		String returnData = "";
		String query = "SELECT config_value FROM tblConfig WHERE config_key = ?";
		Cursor cursor = sqlite.rawQuery(query, where);
		startManagingCursor(cursor);

		if (cursor.getCount() == 0) {
			cursor.close();
			return returnData;
		}
		while (cursor.moveToNext()) {
			returnData = cursor
					.getString(cursor.getColumnIndex("config_value"));
		}
		cursor.close();
		return returnData;
	}

	/*
	 * truncateTable
	 * 
	 * @description empty rows in table
	 * 
	 * @return Cursor
	 */

	public void truncateTable(String table) {
		Log.i(log, "truncateTable " + table);
		connect(DB_READ);

		String query = "DELETE FROM " + table;

		sqlite.execSQL(query);

	}
}
