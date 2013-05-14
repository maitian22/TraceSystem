package com.test.tracesystem.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.test.tracesystem.R;
import com.test.tracesystem.SystemAppcation;
import com.test.tracesystem.util.Constant;
import com.test.tracesystem.util.LogUtil;
import com.test.tracesystem.util.ToastUtil;

public class LeftMenuActivity extends Activity {

	Context context;
	
	String[] groupString=Constant.parentMenu;
	String[][] childString=Constant.childMenu;
	String[] thirdString = {
		"aaaaaaaaaaaaaa"	
	};
	String[] thirdChildString = {
			"1111111111","22222222","333333333"
	};
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
	//	super.onSaveInstanceState(outState);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.left_menu_activity);
		initView();
		initEvent();
	}

	ExpandableListView expandableListView;
	ExpandListAdapter adapter;
	ChildExpandListAdapter childadapter;
	
	AbsListView.LayoutParams lp;
	
	public void initView() {
		expandableListView = (ExpandableListView) findViewById(R.id.list);
		adapter=new ExpandListAdapter();
		expandableListView.setAdapter(adapter);
		/***
		 * 设置group展开的图标
		 */
//		expandableListView.setGroupIndicator(null);
	}

	public void initEvent() {
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				
				ToastUtil.i(context, childString[groupPosition][childPosition]);
				LogUtil.out("aa", "groupPosition:"+groupPosition+"childPosition:"+childPosition);
				String tableName=Constant.parentTableName[groupPosition][childPosition];
				
				Message msg=new Message();
				msg.what=Constant.FLAG_START_ACTIVITY;
				msg.arg1=groupPosition;
				msg.arg2=childPosition;
				
				SystemAppcation.getHandler().sendMessage(msg);
				
				return false;
			}
		});
	}
	
	class ChildExpandListAdapter extends BaseExpandableListAdapter{

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return thirdChildString[childPosition];
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			//return null;
			TextView tx=getTextView(40);
			tx.setText(thirdChildString[childPosition]);
			return tx;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return thirdString[groupPosition];
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView tx=getTextView(20);
			tx.setText(thirdString[groupPosition]);
			return tx;

		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
	
	class ExpandListAdapter extends BaseExpandableListAdapter {

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groupString.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return childString[groupPosition].length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return groupString[groupPosition];
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childString[groupPosition][childPosition];
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView tx=getTextView(0);
			tx.setText(groupString[groupPosition]);
			return tx;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(groupPosition == 0 && childPosition == 0){
				final ExpandableListView temp = new ExpandableListView(context);
				lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
				
				childadapter=new ChildExpandListAdapter();
				temp.setLayoutParams(lp); 
				temp.setAdapter(childadapter);
				
				temp.setOnGroupExpandListener(new OnGroupExpandListener(){
					@Override
					public void onGroupExpand(int groupPosition) {
						// TODO Auto-generated method stub
						lp = new AbsListView.LayoutParams(
								ViewGroup.LayoutParams.MATCH_PARENT,temp.getHeight()*4); 
						temp.setLayoutParams(lp);
					}	
				});
				temp.setOnGroupCollapseListener(new OnGroupCollapseListener(){
					@Override
					public void onGroupCollapse(int groupPosition) {
						// TODO Auto-generated method stub
						lp = new AbsListView.LayoutParams(
								ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT); 
						temp.setLayoutParams(lp);
					}
					
				});
				temp.setOnChildClickListener(new OnChildClickListener(){
					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						// TODO Auto-generated method stub
						return false;
					}
					
				});
				return temp;
				
			}
			TextView tx=getTextView(20);
			tx.setText(childString[groupPosition][childPosition]);
			return tx;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}

	TextView getTextView(int leftPadding) {
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 64);
		TextView textView = new TextView(context);
		textView.setLayoutParams(lp);
		textView.setGravity(Gravity.CENTER_VERTICAL);
		textView.setPadding(56+leftPadding, 0, 0, 0);
		textView.setTextSize(20);
		textView.setTextColor(Color.BLACK);
		return textView;
	}
}