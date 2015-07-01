package com.ZYKJ.buerhaitao.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.adapter.B5_11_PointsMallAdapter.ExchangeListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class B5_10_MyCollectionAdapter extends BaseAdapter {

	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private Activity c;
	private LayoutInflater listContainer;
	String goods_name,goods_image_url,goods_price,fav_time,fav_id;
	Boolean  isEdit = false;
	
	public B5_10_MyCollectionAdapter(Activity c, List<Map<String, String>> data,Boolean  isEdit) {
		// TODO Auto-generated constructor stub
		this.c = c;
		this.data = data;
		this.isEdit = isEdit;
		listContainer = LayoutInflater.from(c);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		return data == null ? 0 : data.size();
		return 5;
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
		if (cellView == null) {
			cellView = listContainer.inflate(R.layout.ui_b5_10_collection_list_items, null);
		}
		
		TextView tv_productName=(TextView) cellView.findViewById(R.id.tv_productName);
		TextView tv_collectiondate=(TextView) cellView.findViewById(R.id.tv_collectiondate);
		TextView tv_productPoints=(TextView) cellView.findViewById(R.id.tv_productPoints);
		ImageView iv_product_collection=(ImageView)cellView.findViewById(R.id.iv_product_collection);	
		CheckBox cb_edit = (CheckBox) cellView.findViewById(R.id.cb_edit);
		if (isEdit == true) {
			cb_edit.setVisibility(View.VISIBLE);
		}
		//通过服务器返回的数据进行判断，是请求的产品收藏列表还是商铺收藏列表
		//由于产品收藏列表和商铺收藏列表的返回值的格式不同，顾两种列表的数据取法不同
//		if (condition) 
//		{
//			goods_name = data.get(position).get("goods_name");
//			goods_image_url = data.get(position).get("goods_image_url");
//			goods_price = data.get(position).get("goods_price");
//			fav_time = data.get(position).get("fav_time");
//			fav_id = data.get(position).get("fav_id");
//		}else {另一种取法
//			goods_name = data.get(position).get("goods_name");
//			goods_image_url = data.get(position).get("goods_image_url");
//			goods_price = data.get(position).get("goods_price");
//			fav_time = data.get(position).get("fav_time");
//			fav_id = data.get(position).get("fav_id");
//		}
		tv_productName.setText(goods_name);
		tv_productPoints.setText(goods_price);
		tv_collectiondate.setText(fav_time+"的收藏");
//		ImageLoader.getInstance().displayImage(data.get(position).get("pgoods_image"), iv_product_collection);
		return cellView;
	}
	

}
