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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.adapter.B5_10_MyCollectionAdapter;
import com.ZYKJ.buerhaitao.adapter.B5_11_PointsMallAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.MyListView;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.loopj.android.http.JsonHttpResponseHandler;
/**
 * 我的收藏 页面
 * @author zyk
 *
 */
public class B5_10_MyCollection extends BaseActivity implements IXListViewListener {

	ImageButton mycollection_back;//返回
	TextView tv_edit;//编辑
	LinearLayout ll_product,ll_store;//产品，商铺
	RelativeLayout rl_delete;//删除按钮
	View v1,v2;
	MyListView listview;
	B5_10_MyCollectionAdapter adapter;
	List<Map<String, String>> data = new ArrayList<Map<String,String>>();
	Boolean  isEdit = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_5_10_mycolection);
		mycollection_back = (ImageButton) findViewById(R.id.mycollection_back);
		tv_edit = (TextView) findViewById(R.id.tv_edit);
		ll_product = (LinearLayout) findViewById(R.id.ll_product);
		ll_store = (LinearLayout) findViewById(R.id.ll_store);
		rl_delete = (RelativeLayout) findViewById(R.id.rl_delete);
		setListener(mycollection_back,tv_edit,ll_product,ll_store);
		v1=findViewById(R.id.v1_s);
		v2=findViewById(R.id.v2_s);
		listview = (MyListView) findViewById(R.id.listview_colllection);
		adapter = new B5_10_MyCollectionAdapter(B5_10_MyCollection.this,data,isEdit);
		listview.setAdapter(adapter);
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(true);
		listview.setXListViewListener(this, 0);
		listview.setRefreshTime();
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
		HttpUtils.getFavoriteProduct(res_getFavoriteProduct, getSharedPreferenceValue("key"));
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.mycollection_back://返回
			this.finish();
			break;
		case R.id.tv_edit://编辑按钮
			if (isEdit == true) {
				tv_edit.setText("编辑");
				isEdit = false;
				rl_delete.setVisibility(View.GONE);
			}else {
				isEdit = true;
				tv_edit.setText("完成");
				rl_delete.setVisibility(View.VISIBLE);
				rl_delete.bringToFront();//显示在最上层
			}
			Tools.Log("编辑");
			adapter = new B5_10_MyCollectionAdapter(B5_10_MyCollection.this,data,isEdit);
			listview.setAdapter(adapter);
			
			break;
		case R.id.ll_product://产品
			Tools.Log("产品");
			v2.setVisibility(View.INVISIBLE);
			v1.setVisibility(View.VISIBLE);
			RequestDailog.showDialog(this, "正在加载数据，请稍后");
			HttpUtils.getFavoriteProduct(res_getFavoriteProduct, getSharedPreferenceValue("key"));
			
			break;
		case R.id.ll_store://商铺
			Tools.Log("商铺");
			v2.setVisibility(View.VISIBLE);
			v1.setVisibility(View.INVISIBLE);
			RequestDailog.showDialog(this, "正在加载数据，请稍后");
			HttpUtils.getFavoriteStore(res_getFavoriteStore, getSharedPreferenceValue("key"));
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
	 * 我收藏的产品
	 */
	JsonHttpResponseHandler res_getFavoriteProduct = new JsonHttpResponseHandler()
	{

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("产品response="+response);
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
				try {
					data.clear();
					org.json.JSONArray array = datas.getJSONArray("pro_list");//等收藏功能完善之后更改array的名字
					Tools.Log("res_pointsMall_array="+array);
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonItem = array.getJSONObject(i);
						Map<String, String> map = new HashMap();
						map.put("goods_name", jsonItem.getString("goods_name"));
						map.put("goods_image_url", jsonItem.getString("goods_image_url"));
						map.put("goods_price", jsonItem.getString("goods_price"));
						map.put("fav_time", jsonItem.getString("fav_time"));
						map.put("fav_id", jsonItem.getString("fav_id"));
						data.add(map);
					}
					adapter.notifyDataSetChanged();
				} 
				catch (org.json.JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error+"");
//				Tools.Notic(B5_MyActivity.this, error+"", null);
			}
			
		}
		
		
	};
	/**
	 * 我收藏的店铺
	 */
	JsonHttpResponseHandler res_getFavoriteStore = new JsonHttpResponseHandler()
	{
		
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("店铺response="+response);
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
				try {
					data.clear();
					org.json.JSONArray array = datas.getJSONArray("favorites_list");//等收藏功能完善之后更改array的名字
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonItem = array.getJSONObject(i);
						Map<String, String> map = new HashMap();
						map.put("store_id", jsonItem.getString("store_id"));
						map.put("store_name", jsonItem.getString("store_name"));
						map.put("fav_time_text", jsonItem.getString("fav_time_text"));
						map.put("fav_time", jsonItem.getString("fav_time"));
						map.put("goods_count", jsonItem.getString("goods_count"));
						map.put("store_collect ", jsonItem.getString("store_collect "));
						map.put("store_avatar", jsonItem.getString("store_avatar"));
						map.put("store_avatar_url", jsonItem.getString("store_avatar_url"));
						data.add(map);
					}
					adapter.notifyDataSetChanged();
				} 
				catch (org.json.JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error+"");
//				Tools.Notic(B5_MyActivity.this, error+"", null);
			}
			
		}
		
		
	};
	
}
