package com.ZYKJ.buerhaitao.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.UI.R;

public class B5_9_adressManageAdapter extends BaseAdapter {

	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private Activity c;
	private LayoutInflater listContainer;
	
	public B5_9_adressManageAdapter(Activity c, List<Map<String, String>> data) {
		this.c = c;
		this.data = data;
		listContainer = LayoutInflater.from(c);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View cellView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (cellView == null) {
			cellView = listContainer.inflate(R.layout.ui_b5_9_myaddressmanagement_items, null);
		}
		TextView tv_name=(TextView) cellView.findViewById(R.id.tv_name);
		TextView tv_phoneNumber=(TextView) cellView.findViewById(R.id.tv_phoneNumber);
		TextView tv_addressTAG=(TextView) cellView.findViewById(R.id.tv_addressTAG);
		TextView tv_addressDetail=(TextView) cellView.findViewById(R.id.tv_addressDetail);
		Button address_chose =(Button) cellView.findViewById(R.id.address_chose);
		address_chose.setOnClickListener(new ChoseAdressListener(position));
		
		return cellView;
	}
	/**
	 * 点击立即兑换按钮之后的跳转
	 * @author zyk
	 *
	 */
	class ChoseAdressListener implements View.OnClickListener {
		int position;
		public ChoseAdressListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(c, "position="+position, Toast.LENGTH_LONG).show();
		}

	}

}
