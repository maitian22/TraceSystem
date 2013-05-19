package com.test.tracesystem.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.test.tracesystem.R;
import com.test.tracesystem.SystemAppcation;
import com.test.tracesystem.adapter.RightChildAdapter;
import com.test.tracesystem.adapter.RightChildAdapter.TableCell;
import com.test.tracesystem.adapter.RightChildAdapter.TableRow;
import com.test.tracesystem.database.DataBaseOperater;
import com.test.tracesystem.dialog.AddAndUpdateChildDialog;
import com.test.tracesystem.dialog.AddTableDialog;
import com.test.tracesystem.dialog.SepcialAddTableDialog;
import com.test.tracesystem.entity.TotalTableEntity;
import com.test.tracesystem.util.Constant;
import com.test.tracesystem.util.LogUtil;
import com.test.tracesystem.util.ToastUtil;


public class RightChildActivity extends Activity implements OnClickListener {

	Context context;
	SystemAppcation application;
	LayoutInflater inflater;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			if (msg.what == 1) {// refresh
				new TableInfoTask().execute();
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		context = this;
		application = (SystemAppcation) getApplication();
		inflater = LayoutInflater.from(context);
		setContentView(R.layout.right_menu_activity);
		initView();
		initEvent();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtil.out("aa", "onresume");
		new TableInfoTask().execute();
	}

	public TextView title_tx;
	public Button add_btn;
	String name;
	String tableName;
	LinearLayout tableLinearLayout;

	public void initView() {
		title_tx = (TextView) findViewById(R.id.title_text);
		add_btn = (Button) findViewById(R.id.title_button);
		tableLinearLayout = (LinearLayout) findViewById(R.id.addlinear_view);
		if (getIntent().getExtras() == null) {
			return;
		} else {
			Bundle b = getIntent().getExtras();
			name = b.getString("name");
			tableName = b.getString("tableName");
			title_tx.setText(name);
		}
	}

	public void initEvent() {
		add_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_button:
			ToastUtil.i(context, "添加表单被点击");
			int xmlId = Constant.hasSpecialAddTableDialog(name);
		if(xmlId!=-1){
				SepcialAddTableDialog spDialog = new SepcialAddTableDialog(context,name,tableName,xmlId,handler);
				spDialog.show();
			}else{
				AddTableDialog dialog = new AddTableDialog(context, name,
					tableName, handler);
				dialog.show();
			}
			break;
		}
	}

	ArrayList<ArrayList<String[]>> listData;
	ArrayList<TotalTableEntity> list;

	class TableInfoTask extends AsyncTask<String, Boolean, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub

			list = DataBaseOperater.getInstance(context).queryParentTable(name,
					tableName);
			if (list.size() == 0) {
				return false;
			} else {
				listData = new ArrayList<ArrayList<String[]>>();
				for (int i = 0; i < list.size(); i++) {
					ArrayList<String[]> data = DataBaseOperater.getInstance(
							context).queryChildTable(
							list.get(i).getChildEnglish());
					listData.add(data);
				}
			}

			return true;
		}

		@Override
		protected void onPreExecute() {

			application.showProgressDialog(context);
		}

		@Override
		protected void onPostExecute(Boolean flag) {

			application.dismissProgressDialog();
			if (flag) {
				add_btn.setEnabled(false);
				if (listData != null) {
					tableLinearLayout.removeAllViews();
					for (int i = 0; i < listData.size(); i++) {
						ArrayList<String[]> data = listData.get(i);
						initTableLinearlayout(data, list.get(i));
					}

				}

			}
			else{
				tableLinearLayout.removeAllViews();
				add_btn.setEnabled(true);
			}

		}
	}

	public void initTableLinearlayout(ArrayList<String[]> data,
			TotalTableEntity entity) {
		View tableChildView = inflater.inflate(R.layout.right_menu_child, null);
		initTableView(tableChildView, data, entity);
		tableLinearLayout.addView(tableChildView);
	}

	public void initTableView(View v, final ArrayList<String[]> data,
			final TotalTableEntity entitys) {
		ListView lv;
		lv = (ListView) v.findViewById(R.id.ListView01);
		ArrayList<TableRow> table = new ArrayList<TableRow>();
		int width = 210;
		LogUtil.out("aa", "data.size()--->" + data.size());

		for (int i = 0; i < data.size(); i++) {
			String[] entity = data.get(i);
			int length = entity.length - 1;
			TableCell[] titles = new TableCell[length];
			for (int j = 0; j < length; j++) {
				if (i == 0 && j == 0) {
					// titles[j] = new TableCell("_id", width,
					titles[j] = new TableCell(entitys.getChild(), width,
							(int) (width * 0.3), TableCell.STRING);
				} else if (i != 0 && j == 0) {
					titles[j] = new TableCell(
							"" + i, width,
							(int) (width * 0.3), TableCell.STRING);
				} else {
					// LogUtil.out("aa","entity[j]:"+Integer.valueOf(entity[j].toString().trim()));
					titles[j] = new TableCell(entity[j].toString(), width,
							(int) (width * 0.3), TableCell.STRING);
				}
			}
			table.add(new TableRow(titles));
		}
		RightChildAdapter tableAdapter = new RightChildAdapter(this, table);
		lv.setAdapter(tableAdapter);
	
		// lv.setOnItemClickListener(new ItemClickEvent());
		lv.setLayoutParams(new FrameLayout.LayoutParams(-1,
				(int) ((width * 0.32) * data.size()+1)));

	//	TextView tx = (TextView) v.findViewById(R.id.title_text);
		Button btnDel = (Button) v.findViewById(R.id.del_button);
		Button btnAdd = (Button) v.findViewById(R.id.title_button);
		
		//btnDel.setText(btnDel.getText()+entitys.getChild());
		
		//tx.setText(entitys.getChild());

		btnDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("确定删除此表单? " + entitys.getChild());
				builder.setPositiveButton("取消", new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
				builder.setNegativeButton("确定", new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						boolean result = DataBaseOperater.getInstance(context)
								.deleteTable(entitys.getChildEnglish());
						if (result) {
							ToastUtil.s(context, "删除成功");
							handler.sendEmptyMessage(1);
						} else {
							ToastUtil.s(context, "删除失败");
						}
					}
				});

				builder.show();

			}
		});

		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddAndUpdateChildDialog dialog = new AddAndUpdateChildDialog(
						context, entitys, "insert", null, data.get(0), handler);
				dialog.show();
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				AddAndUpdateChildDialog dialog = new AddAndUpdateChildDialog(
						context, entitys, "updata", data.get(position), data
								.get(0), handler);
				dialog.show();

			}
		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position != 0) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setTitle("对第"+(Integer.valueOf(data.get(position)[0])-1)+"行数据" + "操作？");
					final int index = position;
					builder.setPositiveButton("修改数据",
							new Dialog.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									AddAndUpdateChildDialog dialog2 = new AddAndUpdateChildDialog(
											context, entitys, "updata", data
													.get(index), data.get(0),
											handler);
									dialog2.show();
								}
							});
					builder.setNegativeButton("删除数据",
							new Dialog.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									boolean result = DataBaseOperater
											.getInstance(context).deleteChild(
													entitys.getChildEnglish(),
													data.get(index)[0]);
									if (result) {
										ToastUtil.s(context, "删除成功");
										handler.sendEmptyMessage(1);
									} else {
										ToastUtil.s(context, "删除失败");
									}
								}
							});

					builder.show();

				}
				return false;
			}
		});
	}

}