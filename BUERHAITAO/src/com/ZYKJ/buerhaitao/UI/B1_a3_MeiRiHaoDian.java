package com.ZYKJ.buerhaitao.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.adapter.B1_a3_MeiRiHaoDianAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.MyListView;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.alibaba.fastjson.JSONException;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * @author lss 2015年6月17日 每日好店
 *
 */
public class B1_a3_MeiRiHaoDian extends BaseActivity implements IXListViewListener  {
	//返回
	private ImageButton b1_a3_goodstoreback;
	private MyListView listview_b1_a3_goodstore;
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private B1_a3_MeiRiHaoDianAdapter goodstoredapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_b1_a3_meirihaodian);

		b1_a3_goodstoreback = (ImageButton)findViewById(R.id.b1_a3_goodstoreback);
		listview_b1_a3_goodstore = (MyListView)findViewById(R.id.listview_b1_a3_goodstore);
		goodstoredapter = new B1_a3_MeiRiHaoDianAdapter(B1_a3_MeiRiHaoDian.this,data);
		listview_b1_a3_goodstore.setAdapter(goodstoredapter);
		listview_b1_a3_goodstore.setPullLoadEnable(true);
		listview_b1_a3_goodstore.setPullRefreshEnable(true);
		listview_b1_a3_goodstore.setXListViewListener(this, 0);
		listview_b1_a3_goodstore.setRefreshTime();
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
		HttpUtils.getGoodStore(res_goodstore, "5","0","88","80", "100");
		
		setListener(b1_a3_goodstoreback);
	}
	
	/**
	 * 每日好店
	 */
	JsonHttpResponseHandler res_goodstore = new JsonHttpResponseHandler()
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
					org.json.JSONArray array = datas.getJSONArray("store_list");
					Tools.Log("res_Points_array="+array);
					data.clear();
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonItem = array.getJSONObject(i);
						Map<String, String> map = new HashMap(); 
						map.put("store_name", jsonItem.getString("store_name"));
						map.put("store_evaluate_count", jsonItem.getString("store_evaluate_count"));
						map.put("sc_name", jsonItem.getString("sc_name"));
						map.put("area_info", jsonItem.getString("area_info"));
						map.put("store_address", jsonItem.getString("store_address"));
						map.put("store_label", jsonItem.getString("store_label"));
						map.put("store_desccredit", jsonItem.getString("store_desccredit"));
						map.put("juli", jsonItem.getString("juli"));
						data.add(map);
					}
					goodstoredapter.notifyDataSetChanged();
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
		case R.id.b1_a3_goodstoreback:
			B1_a3_MeiRiHaoDian.this.finish();
			break;
			
		}
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
		HttpUtils.getGoodStore(res_goodstore, "5","0","88","80", "100");
	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
//		Toast.makeText(this, "只有这么多数据", Toast.LENGTH_LONG).show();
	}
}