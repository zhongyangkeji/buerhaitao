package com.ZYKJ.buerhaitao.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.adapter.B3_ShpppingCartAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.data.ChildrenItem;
import com.ZYKJ.buerhaitao.data.GroupItem;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * @author lss 2015年7月1日 购物车
 *
 */
public class B3_ShoppingCartActivity extends BaseActivity{
	//标题
// 	private TextView tv_sp_title;
	//购物车list
	private ExpandableListView expandableList;
	//购物车数据
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	List<Map<String, String>> data1 = new ArrayList<Map<String, String>>();
	private B3_ShpppingCartAdapter adapter;
	private List<GroupItem> dataList = new ArrayList<GroupItem>();
	private int groupCount;
	private Button showBtn;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b3_shoppingcart);
		initView();
	}
	
	private void initView(){
		/*tv_sp_title = (TextView)findViewById(R.id.tv_sp_title);
		
		setListener(im_sp_back,im_sp_gouwuche);*/
		expandableList = (ExpandableListView)findViewById(R.id.list_shoppingcar);
		expandableList.setGroupIndicator(null);
		showBtn = (Button) findViewById(R.id.showBtn);
		showBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showCheckedItems();
			}
		});
		initData();
		HttpUtils.getShoppingCarInfoList(res_ShoppingCarInfo,getSharedPreferenceValue("key"));
		adapter = new B3_ShpppingCartAdapter(this, dataList);
		expandableList.setAdapter(adapter);

		groupCount = expandableList.getCount();

		for (int i = 0; i < groupCount; i++) {

			expandableList.expandGroup(i);

		}
		
		
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		/*case R.id.im_sp_back:
			break;*/
		default:
			
			break;
		}

	}

	private void showCheckedItems() {
		String checkedItems = "";
		List<String> checkedChildren = adapter.getCheckedChildren();
		if (checkedChildren != null && !checkedChildren.isEmpty()) {
			for (String child : checkedChildren) {
				if (checkedItems.length() > 0) {
					checkedItems += "\n";
				}

				checkedItems += child;
			}
		}

		final Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("已选中项(无排序)：");
		builder.setMessage(checkedItems);
		builder.setPositiveButton("关闭", null);
		builder.setCancelable(true);
		builder.create().show();
	}

	/**
	 * 初始化Adapter数据
	 * 
	 * @Title:
	 * @Description:
	 * @return void 返回类型
	 * @author zhangxiaolei
	 * @throws
	 */
	private void initData() {
		List<ChildrenItem> list1 = new ArrayList<ChildrenItem>();
		list1.add(new ChildrenItem("1子id", "1子name"));
		list1.add(new ChildrenItem("2子id", "2子name"));
		list1.add(new ChildrenItem("3子id", "3子name"));

		GroupItem groupItem1 = new GroupItem("1组id", "1组name", list1);
		dataList.add(groupItem1);

		List<ChildrenItem> list2 = new ArrayList<ChildrenItem>();
		list2.add(new ChildrenItem("4子id", "4子name"));
		list2.add(new ChildrenItem("5子id", "5子name"));

		GroupItem groupItem2 = new GroupItem("2组id", "2组name", list2);
		dataList.add(groupItem2);

		List<ChildrenItem> list3 = new ArrayList<ChildrenItem>();
		list3.add(new ChildrenItem("6子id", "6子name"));
		list3.add(new ChildrenItem("7子id", "7子name"));
		list3.add(new ChildrenItem("8子id", "8子name"));

		GroupItem groupItem3 = new GroupItem("3组id", "3组name", list3);
		dataList.add(groupItem3);
	}
	
	/**
	 * 获取购物车详细列表
	 */
	JsonHttpResponseHandler res_ShoppingCarInfo = new JsonHttpResponseHandler()
	{
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String error=null;
			JSONObject datas=null;
			Tools.Log("res_shaidanquanfabu="+response);
//			Toast.makeText(PublishedActivity.this, response+"", 400).show();
			try {
				datas = response.getJSONObject("datas");
				 error = response.getJSONObject("datas").getString("error");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (error==null)//成功
			{
				try {
					data.clear();
					data1.clear();
					JSONArray array = datas.getJSONArray("cart_list");
					for (int i = 0; i < array.length(); i++) {
						
						JSONObject jsonItem = array.getJSONObject(i);
						
						
						JSONArray storearry = jsonItem.getJSONArray("store_list");
						for (int j = 0; j < storearry.length(); j++) {
							JSONObject jsonItem1 = array.getJSONObject(i);
							Map<String, String> map1 = new HashMap();
							map1.put("cart_id", jsonItem1.getString("cart_id"));
							map1.put("buyer_id", jsonItem1.getString("buyer_id"));
							map1.put("store_id", jsonItem1.getString("store_id"));
							map1.put("store_name", jsonItem1.getString("store_name"));
							map1.put("goods_id", jsonItem1.getString("goods_id"));
							map1.put("goods_name", jsonItem1.getString("goods_name"));
							map1.put("goods_price", jsonItem1.getString("goods_price"));
							map1.put("goods_num", jsonItem1.getString("goods_num"));
							map1.put("state", jsonItem1.getString("state"));
							map1.put("goods_jingle", jsonItem1.getString("goods_jingle"));
							map1.put("goods_spec", jsonItem1.getString("goods_spec"));
							map1.put("goods_freight", jsonItem1.getString("goods_freight"));
							map1.put("goods_storage", jsonItem1.getString("goods_storage"));
							map1.put("goods_image_url", jsonItem1.getString("goods_image_url"));
							map1.put("goods_sum", jsonItem1.getString("goods_sum"));
							map1.put("goods_total", jsonItem1.getString("goods_total"));
							data1.add(map1);
						}
						
						Map<String, String> map = new HashMap();
						map.put("store_id", jsonItem.getString("store_id"));
						map.put("store_name", jsonItem.getString("store_name"));
						data.add(map);
						
					}
					
					
					/*for (int i = 0; i < array.length(); i++) {
						JSONObject jsonItem = array.getJSONObject(i);
						Map<String, String> map = new HashMap();
						map.put("pgoods_id", jsonItem.getString("pgoods_id"));
						map.put("pgoods_image_small", jsonItem.getString("pgoods_image_small"));
						map.put("pgoods_image_old", jsonItem.getString("pgoods_image_old"));
						map.put("ex_state", jsonItem.getString("ex_state"));
						map.put("pgoods_points", jsonItem.getString("pgoods_points"));
						map.put("pgoods_body", jsonItem.getString("pgoods_body"));
						map.put("pgoods_name", jsonItem.getString("pgoods_name"));
						map.put("pgoods_image", jsonItem.getString("pgoods_image"));
						data.add(map);
					}*/
					
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
			
			
		};
		
	};
}