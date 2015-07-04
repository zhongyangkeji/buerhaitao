package com.ZYKJ.buerhaitao.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.adapter.B0_StoreInfoAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.MyListView;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.alibaba.fastjson.JSONException;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author lss 2015年6月29日 店铺详情
 *
 */
public class BX_DianPuXiangQingActivity extends BaseActivity implements IXListViewListener  {
	// 返回
	private ImageButton im_storeback;
	private MyListView listvsinf;
	private List<Map<String, Object>> datax = new ArrayList<Map<String, Object>>();
//	private String re;
	private B0_StoreInfoAdapter stoadapter;
	//店铺ID
	private String store_id;
	//key 登录令牌
	private String key;
	//店铺名称
//	private TextView tv_storename;
	//分项
	private ImageView im_storeshare;
	//收藏
	private ImageView im_storeshoucang;
	//轮播图
	private ImageView im_storeinfobg;
	//店铺图片
	private ImageView im_xiao_xqback;
	//店铺名称
	private TextView tv_dpxq_storename;
	//店铺评分
	private RatingBar rb_dpxiangqing_rating_bar;
	//店铺地址
	private TextView tv_store_address;
	//店铺电话
	private ImageView im_store_phone;
	public int a =0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_dianpuxiangqing);

        store_id = getIntent().getStringExtra("store_id");
        key = getSharedPreferenceValue("key");
//		tv_storename = (TextView)findViewById(R.id.tv_storename);
		im_storeshare = (ImageView)findViewById(R.id.im_storeshare);
		im_storeshoucang = (ImageView)findViewById(R.id.im_storeshoucang);
		im_storeinfobg = (ImageView)findViewById(R.id.im_storeinfobg);
		im_xiao_xqback = (ImageView)findViewById(R.id.im_xiao_xqback);
		tv_dpxq_storename = (TextView)findViewById(R.id.tv_dpxq_storename);
		rb_dpxiangqing_rating_bar = (RatingBar)findViewById(R.id.rb_dpxiangqing_rating_bar);
		tv_store_address = (TextView)findViewById(R.id.tv_store_address);
		im_store_phone = (ImageView)findViewById(R.id.im_store_phone);
		im_storeback = (ImageButton) findViewById(R.id.im_storeback);
		listvsinf = (MyListView) findViewById(R.id.lv_store_info);
		if (a==0) {
			
		}else {
			stoadapter = new B0_StoreInfoAdapter(BX_DianPuXiangQingActivity.this,datax);
			listvsinf.setAdapter(stoadapter);
		}
		listvsinf.setPullLoadEnable(true);
		listvsinf.setPullRefreshEnable(true);
		listvsinf.setXListViewListener(this, 0);
		listvsinf.setRefreshTime();
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
		HttpUtils.getStoreInfo(res_storeinfo, store_id, key);

		setListener(im_storeback,im_storeshare,im_storeshoucang,im_store_phone);
	}

	/**
	 * 获取店铺详情
	 */
	JsonHttpResponseHandler res_storeinfo = new JsonHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();

			String error = null;
			JSONObject datas = null;
			try {
				datas = response.getJSONObject("datas");
//				re = datas.getString("rec_goods_list_count");
//				Toast.makeText(BX_DianPuXiangQingActivity.this, re, Toast.LENGTH_LONG).show();
				error = response.getString("error");
			} catch (JSONException 	e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (org.json.JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (error == null)// 成功
			{
				try {
					JSONObject obj = datas.getJSONObject("store_info");
					tv_dpxq_storename.setText(obj.getString("store_name"));
					if (obj.getString("is_favorate")=="false") {
						
					}else {
						im_storeshoucang.setImageDrawable(getResources().getDrawable(R.drawable.storeyishoucang));
					}
					ImageLoader.getInstance().displayImage(obj.getString("store_avatar"), im_xiao_xqback);
					rb_dpxiangqing_rating_bar.setRating(Float.parseFloat(obj.getString("store_credit_composite")));
					tv_store_address.setText(obj.getString("location"));
//					.put("store_id",obj.getString("store_id"));
//					.put("store_phone",obj.getString("store_phone"));
//					.put("store_credit_text", obj.getString("store_credit_text"));
//					.put("mb_sliders", obj.getString("mb_sliders"));
					a=1;
					JSONArray array = datas.getJSONArray("goods_list");
					datax.clear();
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonItem = array.getJSONObject(i);
						Map<String, Object> map = new HashMap<String, Object>(); 
						map.put("goods_id", jsonItem.getString("goods_id"));
						map.put("goods_commend", jsonItem.getString("goods_commend"));
						map.put("goods_jingle", jsonItem.getString("goods_jingle"));
						map.put("goods_name", jsonItem.getString("goods_name"));
						map.put("goods_price", jsonItem.getString("goods_price"));
						map.put("goods_salenum", jsonItem.getString("goods_salenum"));
						map.put("evaluation_good_star", jsonItem.getString("evaluation_good_star"));
						map.put("goods_promotion_price", jsonItem.getString("goods_promotion_price"));
						map.put("evaluation_count", jsonItem.getString("evaluation_count"));
						map.put("goods_image_url", jsonItem.getString("goods_image_url"));
						map.put("is_special", jsonItem.getInt("is_special"));
						datax.add(map);
					}
//					stoadapter.notifyDataSetChanged();
				} catch (org.json.JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else// 失败
			{
				Tools.Log("res_Points_error=" + error + "");
			}
		}
	};

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_storeback:
			 BX_DianPuXiangQingActivity.this.finish();
			break;
		case R.id.im_storeshare:
			
			break;
		case R.id.im_storeshoucang:
			
			break;
		case R.id.im_store_phone:
			
			break;

		default:
			break;
		}
	}

	@Override
	public void onRefresh(int id) {
		// TODO Auto-generated method stub
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
		HttpUtils.getStoreInfo(res_storeinfo, store_id, key);
	}

	@Override
	public void onLoadMore(int id) {
		// TODO Auto-generated method stub
		// Toast.makeText(this, "只有这么多数据", Toast.LENGTH_LONG).show();
	}
}
