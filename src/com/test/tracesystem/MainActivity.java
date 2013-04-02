package com.test.tracesystem;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aliyun.android.oss.OSSClient;
import com.test.tracesystem.activity.CustomInquery;
import com.test.tracesystem.activity.LeftMenuActivity;
import com.test.tracesystem.activity.RightChildActivity;
import com.test.tracesystem.util.Constant;
import com.test.tracesystem.util.ToastUtil;

public class MainActivity extends ActivityGroup {

	public static String TAG = "TraceSystem";

	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == Constant.FLAG_START_ACTIVITY) {
				right_view.removeAllViews();
				Class<?> rightClass;
				if(msg.arg1 == Constant.parentMenu.length && msg.arg2 == 1){
					//choose custom inquery option
					rightClass = CustomInquery.class;
				}else{
					rightClass = RightChildActivity.class;
				}
				String name = Constant.childMenu[msg.arg1][msg.arg2];
				String tableName = Constant.parentTableName[msg.arg1][msg.arg2];
				Intent intent = new Intent(context, RightChildActivity.class);
				intent.putExtra("name", name);
				intent.putExtra("tableName", tableName);
				View views = activityManager.startActivity(tableName, intent)
						.getDecorView();
				right_view.addView(views);

			}/*
			 * if(msg.what==Constant.FLAG_EXIT_ACTIVITY){ finish(); }
			 */

		}

	};

	public Context context;
	LocalActivityManager activityManager;
	public SystemAppcation appcation;

	public OSSClient client;

	// 上传File
	public void uploadalifile() {
		Thread t = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.i(TAG, "uploadalifile............"
						+ Environment.getExternalStorageDirectory().getPath());
				client.uploadObject("2013-3-4", "App.db", context
						.getDatabasePath("App.db").getPath());

			}
		};
		t.start();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_main);
		context = this;
		activityManager = getLocalActivityManager();
		initView();
		initEvent();
		appcation = (SystemAppcation) getApplication();
		appcation.setHandler(handler);

	//	client = new OSSClient();
	//	client.setAccessId("xBHuGeyNMb9Uba0F");
	//	client.setAccessKey("yDhhTzA8dCUhquv7ub8iI2jNGMX1l4");
	//	Log.i(TAG, "database path:"
	//			+ context.getDatabasePath("App.db").exists());

	//	uploadalifile();
	}

	public LinearLayout left_view;
	public RelativeLayout right_view;

	public void initView() {
		left_view = (LinearLayout) findViewById(R.id.view_left);
		right_view = (RelativeLayout) findViewById(R.id.view_right);

		Intent intent = new Intent(context, LeftMenuActivity.class);
		View views = activityManager.startActivity("LeftMenuActivity", intent)
				.getDecorView();
		left_view.addView(views);

	}

	public void initEvent() {

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ToastUtil.i(context, "destory");
		// System.exit(1);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		/*
		 * if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		 * { // do something... finish(); LogUtil.out("aa", "back000"); //
		 * SystemAppcation
		 * .getHandler().sendEmptyMessage(Constant.FLAG_EXIT_ACTIVITY); return
		 * true; }
		 */
		return super.onKeyDown(keyCode, event);
	}
}
