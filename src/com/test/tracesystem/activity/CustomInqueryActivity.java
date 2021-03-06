package com.test.tracesystem.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.EventLog.Event;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.tracesystem.R;
import com.test.tracesystem.SystemAppcation;
import com.test.tracesystem.adapter.RightChildAdapter;
import com.test.tracesystem.adapter.RightChildAdapter.TableCell;
import com.test.tracesystem.adapter.RightChildAdapter.TableRow;
import com.test.tracesystem.database.DataBaseOperater;
import com.test.tracesystem.entity.TotalTableEntity;
import com.test.tracesystem.util.Constant;
import com.test.tracesystem.util.LogUtil;

public class CustomInqueryActivity extends Activity implements
		Button.OnClickListener {

	private Context mContext;
	private SystemAppcation application;
	private LayoutInflater inflater;
	private TextView mText;
	private EditText mInputText;
	private Button mSearch;
	LinearLayout tableLinearLayout;
	ArrayList<ArrayList<String[]>> listData;
	ArrayList<TotalTableEntity> list;
	ArrayList<TotalTableEntity> listname;

	private void initView() {
		mInputText = (EditText) findViewById(R.id.mInputNum);
		mSearch = (Button) findViewById(R.id.mSearch);

		tableLinearLayout = (LinearLayout) findViewById(R.id.addlinear_view);

	}
	
	private void initEvent() {
		mSearch.setOnClickListener(this);
		LogUtil.out("aa", "CustomInquery init Event............");
	}
	class TableInfoTask extends AsyncTask<String, Boolean, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			listData.clear();
			listname.clear();
			for (int i = 0; i < Constant.parentTableName.length; i++) {
				String[] arg = Constant.parentTableName[i];
				for (int j = 0; j < arg.length; j++) {
					list = DataBaseOperater.getInstance(mContext)
							.queryParentTable(
									Constant.childMenu[i][j],
									Constant.parentTableName[i][j]);
					if (list.size() == 0) {
						continue;
					} else {
						for (int k = 0; k < list.size(); k++) {
							LogUtil.out("aa", "Custom inquery list.get(k).getChild():"+list.get(k).getChild());
							if(list.get(k).getChild().equals(mInputText.getEditableText().toString())){
								ArrayList<String[]> data = DataBaseOperater
								.getInstance(mContext).queryChildTable(
										list.get(k).getChildEnglish());
								listData.add(data);
								listname.add(list.get(k));
								LogUtil.out("aa", "Custom inquery listData added");
							}
						}
					}
				}
			}

			return true;
		}

		@Override
		protected void onPreExecute() {

			application.showProgressDialog(mContext);
		}

		@Override
		protected void onPostExecute(Boolean flag) {

			application.dismissProgressDialog();
			if (flag) {
				if (listData != null) {
					tableLinearLayout.removeAllViews();
					LogUtil.out("aa", "remove all views .........");
					for (int i = 0; i < listData.size(); i++) {
						ArrayList<String[]> data = listData.get(i);
						LogUtil.out("aa", "listData.size:"+listData.size());
						initTableLinearlayout(data, listname.get(i));
					}

				}

			}

		}
	}
	
	public void initTableLinearlayout(ArrayList<String[]> data,
			TotalTableEntity entity) {
		LogUtil.out("aa", "custom inquery initTableLinearLayout....");
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
		LogUtil.out("aa", "width--->" + width);

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
							"" + (Integer.valueOf(entity[j].toString()
											.trim()) - 1), width,
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
		lv.setLayoutParams(new FrameLayout.LayoutParams(-1,
				(int) (width * 0.32 *data.size() + 1)));

		TextView tx = (TextView) v.findViewById(R.id.title_text);
		tx.setVisibility(android.view.View.VISIBLE);
		tx.setText(entitys.getName());
		
		Button btnDel = (Button) v.findViewById(R.id.del_button);
		btnDel.setVisibility(android.view.View.INVISIBLE);
		Button btnAdd = (Button) v.findViewById(R.id.title_button);
		btnAdd.setVisibility(android.view.View.INVISIBLE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mContext = this;
		application = (SystemAppcation) getApplication();
		inflater = LayoutInflater.from(mContext);
		setContentView(R.layout.custom_inquery);
		
		listname = new ArrayList<TotalTableEntity>();
		listData = new ArrayList<ArrayList<String[]>>();
		
		initView();
		initEvent();
	}

	public void DisplayInqueryMessage(String message) {
		new TableInfoTask().execute();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		LogUtil.out("aa", "custom inquery clicked .............");
		if (!mInputText.getText().toString().equals("")) {
			LogUtil.out("aa", "getEditText:"+mInputText.getEditableText().toString());
			DisplayInqueryMessage(mInputText.getText().toString());
		} else {
			Toast.makeText(this, "输入不正确", Toast.LENGTH_SHORT).show();   
		}
	}
}
