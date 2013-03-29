package com.test.tracesystem;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.test.tracesystem.database.DataBaseOperater;
import com.test.tracesystem.util.ToastUtil;

public class SystemAppcation extends Application{
	
	private static Handler handler;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ToastUtil.i(this, "get all parent table");
		
		DataBaseOperater.getInstance(this);
	}
	
	public void sendRightHandler(Message msg){
		if(handler==null){
			ToastUtil.i(this, "handler is null");
			return;
		}else{
			handler.sendMessage(msg);
		}
	}

	public static Handler getHandler() {
		return handler;
	}

	public static void setHandler(Handler handler) {
		SystemAppcation.handler = handler;
	}
	
	public ProgressDialog mProgressDialog;
	
	public ProgressDialog showProgressDialog(Context context) {
//		if (mProgressDialog == null) {
            ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("请稍候…");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            mProgressDialog = dialog;
//        }
        mProgressDialog.show();
        return mProgressDialog;
    }
    public void dismissProgressDialog() {
        try {
        	if(mProgressDialog!=null){
        		mProgressDialog.cancel();
        	}
        } catch (IllegalArgumentException e) {
        	e.printStackTrace();
        }
    }
}