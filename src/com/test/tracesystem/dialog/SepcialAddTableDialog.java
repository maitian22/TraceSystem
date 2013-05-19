package com.test.tracesystem.dialog;

import com.test.tracesystem.R;
import com.test.tracesystem.database.DataBaseOperater;
import com.test.tracesystem.util.LogUtil;
import com.test.tracesystem.util.PinyinUtils;
import com.test.tracesystem.util.ToastUtil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class SepcialAddTableDialog extends Dialog implements android.view.View.OnClickListener{
	String name,tableName;
	int xmlid;
	Handler handler;
	Context context;
	TextView title;
	String TAG = "SepcialAddTableDialog";
	public SepcialAddTableDialog(Context context,String name,String tableName,int xmlId,Handler handler) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.name=name;
		this.tableName=tableName;
		this.xmlid=xmlId;
		this.handler=handler;
	}

	void CheckTheTableIsExist(){
		String TableName=title.getText().toString();
		String TableNameEnglish=PinyinUtils.getPingYin(TableName);
		TableNameEnglish=tableName+TableNameEnglish;
		
		boolean result=DataBaseOperater.getInstance(context).tableIsExit(TableNameEnglish);
		LogUtil.out(TAG, "Special Add Table Dialog,CheckTheTableIsExist result:"+result);
		if(result){
			
		}else{
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		LogUtil.out(TAG, "xmlid:"+xmlid);
		setContentView(xmlid);
		
		title = (TextView)findViewById(R.id.titleView);
		
		CheckTheTableIsExist();
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
