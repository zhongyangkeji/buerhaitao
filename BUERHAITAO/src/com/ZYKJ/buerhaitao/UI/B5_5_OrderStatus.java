package com.ZYKJ.buerhaitao.UI;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.adapter.B5_5_OrderStatusAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.MyListView;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.loopj.android.http.JsonHttpResponseHandler;

public class B5_5_OrderStatus extends BaseActivity implements IXListViewListener {
	
//  order_state 订单状态（待付款:10,待发货:20,待收货:30,已收货:40）
    private static final int DAIFUKUAN  = 10;
    private static final int DAIFAHUO   = 20;
    private static final int DAISHOUHUO = 30;
    private static final int YISHOUHUO  = 40;
    
    private MyListView listview;
    B5_5_OrderStatusAdapter adapter;
    List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
    
    private int status=0;
    
	ImageButton orderstatus_back;
	TextView tv_title;
	View v101,v102,v103,v104;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b5_5_orderstatus);
		iniView();
		setListener(orderstatus_back);
		status = getIntent().getIntExtra("STATUS", 0);
		switch (status) {
		case DAIFUKUAN:
			tv_title.setText("待付款");
			v101.setVisibility(View.VISIBLE);
			v102.setVisibility(View.INVISIBLE);
			v103.setVisibility(View.INVISIBLE);
			v104.setVisibility(View.INVISIBLE);
//			HttpUtils.getOrderList(res_getOrderList, getSharedPreferenceValue("key"), DAIFUKUAN);
		break;
		case DAIFAHUO:
			tv_title.setText("待发货");
			v101.setVisibility(View.INVISIBLE);
			v102.setVisibility(View.VISIBLE);
			v103.setVisibility(View.INVISIBLE);
			v104.setVisibility(View.INVISIBLE);
		break;
		case DAISHOUHUO:
			tv_title.setText("待收货");
			v101.setVisibility(View.INVISIBLE);
			v102.setVisibility(View.INVISIBLE);
			v103.setVisibility(View.VISIBLE);
			v104.setVisibility(View.INVISIBLE);
		break;
		case YISHOUHUO:
			tv_title.setText("已收货");
			v101.setVisibility(View.INVISIBLE);
			v102.setVisibility(View.INVISIBLE);
			v103.setVisibility(View.INVISIBLE);
			v104.setVisibility(View.VISIBLE);
		break;

		default:
			break;
		}
		adapter = new B5_5_OrderStatusAdapter(this,dataList,status);
		listview.setAdapter(adapter);
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(true);
		listview.setXListViewListener(this, 0);
		listview.setRefreshTime();
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
		HttpUtils.getOrderList(res_getOrderList, getSharedPreferenceValue("key"), status);
	}

	private void iniView() {
		// TODO Auto-generated method stub
		orderstatus_back = (ImageButton) findViewById(R.id.orderstatus_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		v101 = findViewById(R.id.v101);
		v102 = findViewById(R.id.v102);
		v103 = findViewById(R.id.v103);
		v104 = findViewById(R.id.v104);
		listview = (MyListView) findViewById(R.id.listview_orderlist);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.orderstatus_back:
			this.finish();
			break;

		default:
			break;
		}
		super.onClick(v);
	}
	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 获得订单列表
	 */
	JsonHttpResponseHandler res_getOrderList = new JsonHttpResponseHandler()
	{

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("订单列表="+response);
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
//					org.json.JSONArray array = datas.getJSONArray("pro_list");//等收藏功能完善之后更改array的名字
//					Tools.Log("res_pointsMall_array="+array);
//					for (int i = 0; i < array.length(); i++) {
//						JSONObject jsonItem = array.getJSONObject(i);
//						Map<String, String> map = new HashMap();
//						map.put("goods_name", jsonItem.getString("goods_name"));
//						map.put("goods_image_url", jsonItem.getString("goods_image_url"));
//						map.put("goods_price", jsonItem.getString("goods_price"));
//						map.put("fav_time", jsonItem.getString("fav_time"));
//						map.put("fav_id", jsonItem.getString("fav_id"));
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
