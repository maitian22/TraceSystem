package com.test.tracesystem.dialog;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.tracesystem.R;
import com.test.tracesystem.database.DataBaseOperater;
import com.test.tracesystem.entity.TotalTableEntity;
import com.test.tracesystem.util.ToastUtil;
public class AddAndUpdateChildDialog extends Dialog implements
		android.view.View.OnClickListener {

	Context context;
	TotalTableEntity entitys;
	String type;
	String[] data;
	String[] dataTitle;
	Handler handler;
	public AddAndUpdateChildDialog(Context context,TotalTableEntity entitys, String type,String[] data,String[] dataTitle,Handler handler) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.entitys = entitys;
		this.type=type;
		this.data=data;
		this.dataTitle=dataTitle;
		this.handler=handler;
	}

	
	LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_table_dialog);
		inflater = LayoutInflater.from(context);

		initView();
		initEvent();
	}

	TextView title_text,title2_text;
	View edit_view;
	LinearLayout addlinear_view;
	Button cancel_btn, ensure_btn;

	public void initView() {
		title_text = (TextView) findViewById(R.id.title_text);
		title2_text = (TextView) findViewById(R.id.title2_text);
		title_text.setText(entitys.getName()+"--->"+entitys.getChild());
		if(type.equals("insert")){
			title2_text.setText("添加数据");
		}else if(type.equals("updata")){
			title2_text.setText("修改数据");
		}
		edit_view=findViewById(R.id.table_title_view);
		edit_view.setVisibility(View.GONE);
		
		
		addlinear_view = (LinearLayout) findViewById(R.id.addlinear_view);
		 initEditText();
		cancel_btn = (Button) findViewById(R.id.cancel_btn);
		ensure_btn = (Button) findViewById(R.id.ensure_btn);

	}

	public void initEvent() {
		cancel_btn.setOnClickListener(this);
		ensure_btn.setOnClickListener(this);
	}

	String editTableName = null;
	String editRableNameEnglish = null;
	boolean result=false;
	String showMessage="";
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cancel_btn:
			dismiss();
			break;
		case R.id.ensure_btn:
			columName=getColumName();
			if(columName==null){
				ToastUtil.s(context, "请填写完整的数据");
			}else{
				if(type.equals("insert")){
					 result=DataBaseOperater.getInstance(context).insertDataChild(entitys.getChildEnglish(), columName);
					 showMessage="添加成功";
				}else if(type.equals("updata")){
					 result=DataBaseOperater.getInstance(context).updataDataChild(entitys.getChildEnglish(), columName, Long.parseLong(data[0]));
					 showMessage="修改成功";
				}
				if(result){
					ToastUtil.i(context, showMessage);
					handler.sendEmptyMessage(1);
				}else{
					ToastUtil.i(context, "操作失败");
				}
				
				dismiss();
			}
			break;
		}
	}
	
	
	
	
	ArrayList<EditText> listEdit;
	public void initEditText(){
		listEdit=null;
		listEdit=new ArrayList<EditText>();
		for(int i=1;i<dataTitle.length-1;i++){
			View view=inflater.inflate(R.layout.add_table_dialog_item, null);
			
			EditText edit=(EditText)view.findViewById(R.id.item_edit);
			TextView text=(TextView)view.findViewById(R.id.item_text);

			edit.setHint("不能为空");
			if(type.equals("insert")){
				edit.setText("");
			}else if(type.equals("updata")){
				edit.setText(data[i]);
			}
			text.setText(dataTitle[i]);
			text =null;
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
		if(columName.size()!=  dataTitle.length-2){
			return null;
		}else{
			return columName;
		}
	}
}