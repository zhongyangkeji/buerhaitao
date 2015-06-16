package com.ZYKJ.buerhaitao.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ZYKJ.buerhaitao.adapter.B5_9_adressManageAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.MyListView;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.loopj.android.http.JsonHttpResponseHandler;
/**
 * 我的收货地址
 * @author zyk
 *
 */
public class B5_9_MyAddressManagement extends BaseActivity implements IXListViewListener {

	private MyListView listview_addresManagement;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	B5_9_adressManageAdapter adapter;
	Button btn_addNewAddress;
	ImageButton address_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_b5_9_myaddressmanagement);
		btn_addNewAddress=(Button) findViewById(R.id.btn_addNewAddress);
		address_back=(ImageButton) findViewById(R.id.address_back);
		setListener(btn_addNewAddress,address_back);
		listview_addresManagement=(MyListView) findViewById(R.id.listview_addresManagement);
		adapter = new B5_9_adressManageAdapter(this,data);
		listview_addresManagement.setAdapter(adapter);
		listview_addresManagement.setPullLoadEnable(true);
		listview_addresManagement.setPullRefreshEnable(true);
		listview_addresManagement.setXListViewListener(this, 0);
		listview_addresManagement.setRefreshTime();
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
		HttpUtils.getAddress(res_getAddress, getSharedPreferenceValue("key"));
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_addNewAddress://添加新地址
			Intent intent_toaddAddress = new Intent(this,B5_9_1_addAddress.class);
			intent_toaddAddress.putExtra("changeORadd", "add");
			startActivity(intent_toaddAddress);
			break;
		case R.id.address_back:
			this.finish();
			break;

		default:
			break;
		}
	}
	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		HttpUtils.getAddress(res_getAddress, getSharedPreferenceValue("key"));
	}
	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
		HttpUtils.getAddress(res_getAddress, getSharedPreferenceValue("key"));
	}
	
	JsonHttpResponseHandler res_getAddress = new JsonHttpResponseHandler()
	{
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("res_getAddress="+response);
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
//				try {
//					data.clear();
//					org.json.JSONArray array = datas.getJSONArray("pro_list");
//					Tools.Log("res_pointsMall_array="+array);
//					for (int i = 0; i < array.length(); i++) {
//						JSONObject jsonItem = array.getJSONObject(i);
//						Map<String, String> map = new HashMap();
//						map.put("pgoods_id", jsonItem.getString("pgoods_id"));
//						map.put("pgoods_image_small", jsonItem.getString("pgoods_image_small"));
//						map.put("pgoods_image_old", jsonItem.getString("pgoods_image_old"));
//						map.put("ex_state", jsonItem.getString("ex_state"));
//						map.put("pgoods_points", jsonItem.getString("pgoods_points"));
//						map.put("pgoods_body", jsonItem.getString("pgoods_body"));
//						map.put("pgoods_name", jsonItem.getString("pgoods_name"));
//						map.put("pgoods_image", jsonItem.getString("pgoods_image"));
//						data.add(map);
//					}
//					adapter.notifyDataSetChanged();
//				} 
//				catch (org.json.JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error+"");
//				Tools.Notic(B5_MyActivity.this, error+"", null);
			}
			
		}
		
		
	};
}
