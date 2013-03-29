package com.test.tracesystem.database;

import java.io.File;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.test.tracesystem.util.LogUtil;

public class DbHelper {

	private static String DATABASE_NAME = "App.db";
	private static String DATABASE_TABLE = "total_table";
	private static String DATABASE_PATH;
	private SQLiteDatabase sqldb;

	private String CREATE_TABLE = "create table if not exists "
			+ DATABASE_TABLE + "(id integer not null ," + "name text not null,"
			+ "downUrl text not null," + "state integer not null,"
			+ "image blob)";
	
	public SQLiteDatabase openDatabase(Context context) {
		DATABASE_PATH = getFilepath();

		String dbFileName = DATABASE_PATH + "/" + DATABASE_NAME;
		// try {
		//
		// if (!(new File(dbFileName).exists())) {
		// InputStream is = context.getResources().openRawResource(
		// R.raw.hotel_cache);
		// FileOutputStream fos = new FileOutputStream(dbFileName);
		// byte[] buffer = new byte[512];
		// int count=is.read(buffer);
		// while(count>0){
		// fos.write(buffer, 0, count);
		// count=is.read(buffer);
		// }
		// fos.close();
		// is.close();
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		try {
			sqldb = SQLiteDatabase.openOrCreateDatabase(dbFileName, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqldb;
	}

	private String getFilepath() {

		boolean hasSDCard = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		String filepath = "";
		if (hasSDCard) {
			filepath = "/sdcard/tracesystem/database";
		} else {
			filepath = "/data/data/com.test.tracesystem/database";
		}
		File file = new File(filepath);
		if (!file.exists()) {
			file.mkdirs();
		}
		filepath = file.getPath();

		return filepath;
	}
	
	
	public void getData(){
		
	}
	
	public void closeDb(){
		sqldb.close();
	}
	
	
	public static final String CREATE_TOTAL_TABLE="create table if not exists "
			+ DATABASE_TABLE + "(" + "name text ,"
			+ "nameenglish text ," + "childname text ,"
			+ "childnameenglish text )";
	public void createTotalTable(){
		sqldb.execSQL(CREATE_TOTAL_TABLE);
	}
	
	/***
	 * 判断当前表是否存在，防止表重名生成不了新表
	 * @param tableName
	 * @return true表示存在，false表示不存在
	 */
	public boolean queryTableExit(String tableName){
		Cursor cursor=sqldb.query(DATABASE_TABLE, null, "childnameenglish = '"+tableName+"'", null, null, null, null);
		int size=cursor.getCount();
		if(size!=0){
			return true;
		}else{
			return false;
		}
	}
	/***
	 * 在主表里面添加信息
	 * @param name
	 * @param nameEnglish
	 * @param childName
	 * @param childNameEnglish
	 * @return
	 */
	public long insertParentTable(String name,String nameEnglish,String childName,String childNameEnglish){
		ContentValues initialValues=new ContentValues();
		initialValues.put("name", name);
		initialValues.put("nameenglish", nameEnglish);
		initialValues.put("childname", childName);
		initialValues.put("childnameenglish", childNameEnglish);
		
		LogUtil.out("aa", name+"name---"+nameEnglish+"nameenglish---"+childName+"childname---"+childNameEnglish+"childnameenglish---");
		
		return sqldb.insert(DATABASE_TABLE, null, initialValues);
	}
	
	/***
	 * 创建表
	 * @param tableName
	 * @param size
	 */
	public void creatTable(String tableName,int size){
		String s="";
		for(int i=0;i<size;i++){
			s+="ext"+i+" text ,";
		}
		
		String sqlString="create table if not exists "
				+ tableName + "(_id integer PRIMARY KEY,"
				+s
				+ "childnameenglish text )"
				;
		
		sqldb.execSQL(sqlString);
	}
	/***
	 * 向指定表添加数据  这是添加第一行表示列名 每次创建表的时候须执行
	 * @param tableNameEnglish
	 * @param tableListString
	 * @return
	 */
	public long insertChildTableFirstLine(String childName,String tableNameEnglish,ArrayList<String> tableListString){
		
		ContentValues initialValues=new ContentValues();
		for(int i=0;i<tableListString.size();i++){
			initialValues.put("ext"+i, tableListString.get(i));
		}
		initialValues.put("childnameenglish",childName);
		return sqldb.insert(tableNameEnglish, null, initialValues);
	}
	
	/***
	 * 根据菜单查询这个类别下一共有几个表
	 * @param name
	 * @param nameEnglish
	 * @return
	 */
	public Cursor queryParentTable(String name,String nameEnglish){
		String[] queryString=new String[2];
		queryString[0]="name = '"+name+"'";
		queryString[1]="nameenglish = '"+nameEnglish+"'";
		
		Cursor cursor=sqldb.query(DATABASE_TABLE, null, queryString[1], null, null, null, null);
		return cursor;
	}
	
	public Cursor queryChildTable(String tableName){
		return sqldb.query(tableName, null, null, null, null, null, null);
	}
	
	public long insertDataChild(String tableNameEnglish,ArrayList<String> tableListString){
		ContentValues initialValues=new ContentValues();
		for(int i=0;i<tableListString.size();i++){
			initialValues.put("ext"+i, tableListString.get(i));
		}
		return sqldb.insert(tableNameEnglish, null, initialValues);
	}
	
	public int updataDataChild(String tableNameEnglish,ArrayList<String> tableListString,long id){
		ContentValues initialValues=new ContentValues();
		for(int i=0;i<tableListString.size();i++){
			initialValues.put("ext"+i, tableListString.get(i));
		}
		return sqldb.update(tableNameEnglish,initialValues, "_id = '"+id+"'",null);
	}
	
	public int deleteChild(String tableNameEnglish,String _id){
		return sqldb.delete(tableNameEnglish, "_id = '"+_id+"'", null);
	}
	
	public int deleteParentData(String ChildNameEnglish){
		return sqldb.delete(DATABASE_TABLE, "childnameenglish = '"+ChildNameEnglish+"'", null);
	}
	
	public void deleteChildTable(String ChildNameEnglish){
		String s="DROP TABLE IF EXISTS "+ChildNameEnglish;
		sqldb.execSQL(s);
	}
}