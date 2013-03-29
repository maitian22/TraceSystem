package com.test.tracesystem.dialog;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.tracesystem.R;
import com.test.tracesystem.database.DataBaseOperater;
import com.test.tracesystem.util.PinyinUtils;
import com.test.tracesystem.util.ToastUtil;

public class AddTableDialog extends Dialog implements android.view.View.OnClickListener{
	
	String name;
	String tableName;
	Handler handler;
	public AddTableDialog(Context context,String name,String tableName,Handler handler) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.name=name;
		this.tableName=tableName;
		this.handler=handler;
	}
	
	Context context;
	LayoutInflater inflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_table_dialog);
		inflater=LayoutInflater.from(context);
		
		initView();
		initEvent();
	}
	
	TextView title_text;
	EditText title_edit;
	Button test_exit_btn;
	LinearLayout addlinear_view;
	Button cancel_btn,ensure_btn;
	public void initView() {
		title_text = (TextView) findViewById(R.id.title_text);
		title_edit=(EditText)findViewById(R.id.table_title_edit);
		test_exit_btn = (Button) findViewById(R.id.table_exist_btn);

		title_text.setText(name);
		
		addlinear_view=(LinearLayout)findViewById(R.id.addlinear_view);
		initEditText();
		cancel_btn= (Button) findViewById(R.id.cancel_btn);
		ensure_btn= (Button) findViewById(R.id.ensure_btn);
		
	}

	public void initEvent() {
		test_exit_btn.setOnClickListener(this);
		cancel_btn.setOnClickListener(this);
		ensure_btn.setOnClickListener(this);
	}
	
	
	String editTableName=null;
	String editRableNameEnglish=null;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.table_exist_btn:
			if(title_edit.getText().toString().trim().equals("")){
				ToastUtil.s(context, "表名不能为空");
			}else{
				checkTableNameExit();
			}
			
			
			
			break;
		case R.id.cancel_btn:
			dismiss();
			break;
		case R.id.ensure_btn:
			if(title_edit.getText().toString().trim().equals("")){
				ToastUtil.s(context, "表名不能为空");
			}else{
				columName = getColumName();
				if(columName==null){
					ToastUtil.s(context, "请填写列名");
				}else{
					if(checkTableNameExit()){
						boolean result=DataBaseOperater.getInstance(context).addTable(name,tableName,editTableName,editRableNameEnglish,columName);
						Log.i("TraceSystem","name:"+name+",tableName:"+tableName+",editTableName:"+editTableName+",editRableNameEnglish:"+editRableNameEnglish);
						if(result){
							ToastUtil.s(context, "创建"+editTableName+"表成功");
							handler.sendEmptyMessage(1);
							dismiss();
						}else{
							ToastUtil.s(context, "创建"+editTableName+"表失败");
						}
					}
					
				}
				
				
			}
			break;
		}
	}
	public boolean checkTableNameExit(){
		editTableName=title_edit.getText().toString();
		editRableNameEnglish=PinyinUtils.getPingYin(editTableName);
		editRableNameEnglish=tableName+editRableNameEnglish;
		
		boolean result=DataBaseOperater.getInstance(context).tableIsExit(editRableNameEnglish);
		if(result){
			ToastUtil.s(context, "表名已存在，请重新命名");
			return false;
		}else{
			title_edit.setEnabled(false);
			return true;
		}
	}
	
	
	ArrayList<EditText> listEdit;
	public void initEditText(){
		listEdit=null;
		listEdit=new ArrayList<EditText>();
		for(int i=0;i<50;i++){
			View view=inflater.inflate(R.layout.add_table_dialog_item, null);
			
			EditText edit=(EditText)view.findViewById(R.id.item_edit);
			TextView text=(TextView)view.findViewById(R.id.item_text);
			String showText="第"+(i+1)+"列列名";
			edit.setHint(showText);
			text.setText(showText);
			showText=null;
			text=null;
			listEdit.add(edit);
			addlinear_view.addView(view);
		}
	}
	
	ArrayList<String> columName;
	public ArrayList<String> getColumName(){
		ArrayList<String> columName=null;
		columName=new ArrayList<String>();
		for(int i=0;i<listEdit.size();i++){
			if(!listEdit.get(i).getText().toString().trim().equals("")){
				columName.add(listEdit.get(i).getText().toString().trim());
			}
		}
		if(columName.size()==0){
			return null;
		}else{
			return columName;
		}
	}
	
	
	
	
}