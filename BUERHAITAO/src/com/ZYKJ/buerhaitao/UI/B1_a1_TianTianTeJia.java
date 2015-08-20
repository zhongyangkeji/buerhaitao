package com.ZYKJ.buerhaitao.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.adapter.B1_a1_DaySpecialAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.MyListView;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.alibaba.fastjson.JSONException;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * @author lss 2015年6月17日 天天特价
 *
 */
public class B1_a1_TianTianTeJia extends BaseActivity implements IXListViewListener  {
	//返回
	private ImageButton b1_a1_back1;
	private MyListView listview_b1_a1_dayspecial;
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private B1_a1_DaySpecialAdapter dayspecialadapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_b1_a1_tiantiantejia);

		b1_a1_back1 = (ImageButton)findViewById(R.id.b1_a1_back1);
		listview_b1_a1_dayspecial = (MyListView)findViewById(R.id.listview_b1_a1_dayspecial);
		dayspecialadapter = new B1_a1_DaySpecialAdapter(B1_a1_TianTianTeJia.this,data);
		listview_b1_a1_dayspecial.setAdapter(dayspecialadapter);
		listview_b1_a1_dayspecial.setPullLoadEnable(true);
		listview_b1_a1_dayspecial.setPullRefreshEnable(true);
		listview_b1_a1_dayspecial.setXListViewListener(this, 0);
		listview_b1_a1_dayspecial.setRefreshTime();
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
//		HttpUtils.getDaySpecial(res_dayspecial, "0","88","1","1");
		HttpUtils.getDaySpecial(res_dayspecial, "0",getSharedPreferenceValue("cityid"),getSharedPreferenceValue("lng"),getSharedPreferenceValue("lat"));
		listview_b1_a1_dayspecial.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent itdayspec = new Intent();
				String goid = data.get(position-1).get("goods_id");
				itdayspec.putExtra("goods_id",goid);
				itdayspec.setClass(B1_a1_TianTianTeJia.this, Sp_GoodsInfoActivity.class);
				startActivity(itdayspec);
			}
		});
		setListener(b1_a1_back1);
	}
	
	/**
	 * 天天特价
	 */
	JsonHttpResponseHandler res_dayspecial = new JsonHttpResponseHandler()
	{

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			
			String error=null;
			JSONObject datas=null;
			try {
				 datas = response.getJSONObject("datas");
				 error = response.getString("error");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (org.json.JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (error==null)//成功
			{
				try {
					org.json.JSONArray array = datas.getJSONArray("day_special");
					Tools.Log("res_Points_array="+array);
					data.clear();
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonItem = array.getJSONObject(i);
						Map<String, String> map = new HashMap();
						map.put("goods_id", jsonItem.getString("goods_id"));
						map.put("goods_click", jsonItem.getString("goods_click"));
						map.put("goods_salenum", jsonItem.getString("goods_salenum"));
						map.put("goods_storage", jsonItem.getString("goods_storage"));
						map.put("goods_name", jsonItem.getString("goods_name"));
						map.put("goods_jingle", jsonItem.getString("goods_jingle"));
						map.put("goods_price", jsonItem.getString("goods_price"));
						map.put("goods_promotion_price", jsonItem.getString("goods_promotion_price"));
						map.put("goods_image", jsonItem.getString("goods_image"));
						map.put("juli", jsonItem.getString("juli"));
						data.add(map);
					}
					dayspecialadapter.notifyDataSetChanged();
				} 
				catch (org.json.JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error+"");
			}
		}
	};
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.b1_a1_back1:
			B1_a1_TianTianTeJia.this.finish();
			break;
			
		}
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
		HttpUtils.getDaySpecial(res_dayspecial, "0",getSharedPreferenceValue("cityid"),getSharedPreferenceValue("lng"),getSharedPreferenceValue("lat"));
	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
//		Toast.makeText(this, "只有这么多数据", Toast.LENGTH_LONG).show();
	}
}
