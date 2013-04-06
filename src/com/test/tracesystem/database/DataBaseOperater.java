package com.test.tracesystem.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.test.tracesystem.entity.TotalTableEntity;
import com.test.tracesystem.util.LogUtil;

public class DataBaseOperater {
	
	private static DataBaseOperater dataBaseOperater=null;
	private DbHelper dbHelper;
	public Context context;
	private DataBaseOperater(Context context){
		this.context=context;
		dbHelper=new DbHelper();
		dbHelper.openDatabase(context);
		dbHelper.createTotalTable();
		
		dbHelper.closeDb();
	}
	
	public static DataBaseOperater getInstance(Context context){
		if(dataBaseOperater==null){
			dataBaseOperater=new DataBaseOperater(context);
		}
		return dataBaseOperater;
	}
	/***
	 * 判断当前表是否存在，防止表重名生成不了新表
	 * @param tableName
	 * @return true表示存在，false表示不存在
	 */
	public boolean tableIsExit(String tableName){
		dbHelper.openDatabase(context);
		boolean result=dbHelper.queryTableExit(tableName);
		dbHelper.closeDb();
		return result;
	}
	
	/***
	 * 	创建新表，分三步：1，先在主table里面添加表信息2，生成新表3，添加列名到第一行
	 * @param name
	 * @param nameEnglish
	 * @param childName
	 * @param childNameEnglish
	 * @param tableListString
	 * @return	true表示添加成功，false表示添加失败
	 */
	public boolean addTable(String name,String nameEnglish,String childName,String childNameEnglish,ArrayList<String> tableListString){
		boolean result=false;
		dbHelper.openDatabase(context);
		long id=dbHelper.insertParentTable(name, nameEnglish, childName, childNameEnglish);
		if(id==-1){
			result=false;
		}else{
			dbHelper.creatTable(childNameEnglish, tableListString.size());
			id=dbHelper.insertChildTableFirstLine(childName,childNameEnglish, tableListString);
			/*LogUtil.out("aa", "childName:"+childName+"childNameEnglish:"+childNameEnglish);
			for(int i = 0;i<tableListString.size();i++)
				LogUtil.out("aa", "tableListString.get(i):"+tableListString.get(i));*/
			if(id==-1){
				result=false;
			}else{
				result=true;
			}
		}
		dbHelper.closeDb();
		
		return result;
	}
	
	public ArrayList<TotalTableEntity> queryParentTable(String name,String nameEnglish){
		ArrayList<TotalTableEntity> list=new ArrayList<TotalTableEntity>();
		dbHelper.openDatabase(context);
		Cursor cursor=dbHelper.queryParentTable(name,nameEnglish);
		if(cursor.getCount()==0){
			cursor.close();
		}else{
			while(cursor.moveToNext()){
				TotalTableEntity entity=new TotalTableEntity();
				entity.setName(cursor.getString(cursor.getColumnIndex("name")));
				entity.setNameEnglish(cursor.getString(cursor.getColumnIndex("nameenglish")));
				entity.setChild(cursor.getString(cursor.getColumnIndex("childname")));
				entity.setChildEnglish(cursor.getString(cursor.getColumnIndex("childnameenglish")));
				list.add(entity);
			}
			cursor.close();
		}
		
		dbHelper.closeDb();
		return list;
	}
	
	public ArrayList<String[]> queryChildTable(String tableName){
		dbHelper.openDatabase(context);
		Cursor cursor=dbHelper.queryChildTable(tableName);
		int columCount=cursor.getColumnCount();
		ArrayList<String[]> list=new ArrayList<String[]>();
		LogUtil.out("aa", "tableName--->"+tableName+"---count--->"+cursor.getCount());
		while(cursor.moveToNext()){
			String[] data=new String[columCount];
			for(int i=0;i<data.length;i++){
				data[i]=cursor.getString(i);
		//		LogUtil.out("aa", "data--->"+data[i]);
			}
			list.add(data);
		}
		cursor.close();
		
		dbHelper.closeDb();
		return list;
	}
	
	public boolean insertDataChild(String tableNameEnglish,ArrayList<String> tableListString){
		boolean result=false;
		dbHelper.openDatabase(context);
		
		long id=dbHelper.insertDataChild(tableNameEnglish, tableListString);
		if(id>0){
			result=true;
		}else{
			result=false;
		}
		
		dbHelper.closeDb();
		return result;
	}
	
	public boolean updataDataChild(String tableNameEnglish,ArrayList<String> tableListString,long id){
		boolean result=false;
		dbHelper.openDatabase(context);
		
		/*LogUtil.out("aa", "tableNameEnglish:"+tableNameEnglish+" id:"+id);
		for(int i = 0; i< tableListString.size();i++){
			LogUtil.out("aa", "getList:"+tableListString.get(i));
		}*/
		int ids=dbHelper.updataDataChild(tableNameEnglish, tableListString, id);
		if(ids>0){
			result=true;
		}else{
			result=false;
		}
		
		dbHelper.closeDb();
		return result;
	}
	
	public boolean deleteChild(String tableNameEnglish,String id){
		boolean result=false;
		dbHelper.openDatabase(context);
		
		int number=dbHelper.deleteChild(tableNameEnglish, id);
		if(number>0){
			result=true;
		}
		
		dbHelper.closeDb();
		return result;
	}
	
	public boolean deleteTable(String childNameEnglish){
		boolean result=false;
		dbHelper.openDatabase(context);
		
		int number=dbHelper.deleteParentData(childNameEnglish);
		if(number>0){
			dbHelper.deleteChildTable(childNameEnglish);
			result=true;
		}
		
		dbHelper.closeDb();
		return result;
	}
}