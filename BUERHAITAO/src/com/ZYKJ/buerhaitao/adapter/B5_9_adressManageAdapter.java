package com.ZYKJ.buerhaitao.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.UI.B5_9_1_addAddress;
import com.ZYKJ.buerhaitao.UI.R;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.loopj.android.http.JsonHttpResponseHandler;

@SuppressLint("NewApi") public class B5_9_adressManageAdapter extends BaseAdapter {

	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private Activity c;
	private LayoutInflater listContainer;
	private String key;
	
	public B5_9_adressManageAdapter(Activity c, List<Map<String, String>> data,String key) {
		this.c = c;
		this.data = data;
		listContainer = LayoutInflater.from(c);
		this.key=key;
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
		
		tv_name.setText(data.get(position).get("true_name"));
		tv_phoneNumber.setText(data.get(position).get("mob_phone"));
		tv_addressDetail.setText(data.get(position).get("area_info")+data.get(position).get("address"));
		address_chose.setTag(data.get(position).get("address_id"));
		
		if (data.get(position).get("is_default").equals("1")) {
			tv_addressTAG.setAlpha(1);//
		}else {
			tv_addressTAG.setAlpha(0);//如果不是默认的地址，就隐藏掉[默认]
		}
		address_chose.setOnClickListener(new ChoseAdressListener(position));
		tv_addressDetail.setOnClickListener(new ChoseAdressListener(position));
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
//			Toast.makeText(c,"position="+position, Toast.LENGTH_LONG).show();
			switch (v.getId()) {
			case R.id.address_chose:
				v.setBackgroundResource(R.drawable.address_chosed);
				break;
			case R.id.tv_addressDetail:
				Intent intent_to_changeAddress=new Intent(c,B5_9_1_addAddress.class);
				intent_to_changeAddress.putExtra("true_name", data.get(position).get("true_name"));
				intent_to_changeAddress.putExtra("mob_phone", data.get(position).get("mob_phone"));
				intent_to_changeAddress.putExtra("zip", data.get(position).get("zip"));
				intent_to_changeAddress.putExtra("area_info", data.get(position).get("area_info"));
				intent_to_changeAddress.putExtra("address", data.get(position).get("address"));
				intent_to_changeAddress.putExtra("address_id", data.get(position).get("address_id"));
				intent_to_changeAddress.putExtra("change","change");
				c.startActivity(intent_to_changeAddress);
				break;

			default:
				break;
			}
		}

	}
	JsonHttpResponseHandler res_setDefaultAddress = new JsonHttpResponseHandler()
	{
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("res_setDefaultAddress="+response);
			String error=null;
			JSONObject datas=null;
			try {
				 datas = response.getJSONObject("datas");
				 error = response.getString("error");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if (error==null)//成功
			{
				  Tools.Notic(c, "设置默认地址成功", null);
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error+"");
//				Tools.Notic(B5_MyActivity.this, error+"", null);
			}
			
		}
		
	};
}
