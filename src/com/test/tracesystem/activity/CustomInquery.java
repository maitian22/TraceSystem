package com.test.tracesystem.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.test.tracesystem.R;
import com.test.tracesystem.SystemAppcation;

public class CustomInquery extends Activity{

	private Context mContext;
	private SystemAppcation application;
	private LayoutInflater inflater;
	private TextView mText;
	
	private void initView(){
	//	mText = (TextView)findViewById(R.id.text);
	//	mText.setTextColor(Color.BLACK);
	}
	
	private void initEvent(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mContext = this;
		application = (SystemAppcation) getApplication();
		inflater = LayoutInflater.from(mContext);
		setContentView(R.layout.custom_inquery);
		initView();
		initEvent();
	}

}
