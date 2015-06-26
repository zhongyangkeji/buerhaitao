package com.ZYKJ.buerhaitao.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.UI.B5_11_1_ExchangeDetail;
import com.ZYKJ.buerhaitao.UI.B5_MyActivity;
import com.ZYKJ.buerhaitao.UI.R;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.nostra13.universalimageloader.core.ImageLoader;

public class B5_11_2_ExchangeRecordAdapter extends BaseAdapter {
	
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private Activity c;
	private LayoutInflater listContainer;
	
	private String pgoods_name=null;//产品名字
	private String pgoods_body=null;//产品介绍
	private String pgoods_points=null;//产品积分
	private String point_addtime=null;//订单生成时间
	
	public B5_11_2_ExchangeRecordAdapter(Activity c, List<Map<String, String>> data) {
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
			cellView = listContainer.inflate(R.layout.ui_b5_11_2_exchangerecord_item, null);
		}
		TextView tv_recorddate=(TextView) cellView.findViewById(R.id.tv_recorddate);
		TextView tv_productName=(TextView) cellView.findViewById(R.id.tv_producName_re);
		TextView tv_productPoints=(TextView) cellView.findViewById(R.id.tv_productPoints_re);
		TextView tv_productIntro=(TextView) cellView.findViewById(R.id.tv_productIntro_re);
		
		ImageView iv_product=(ImageView) cellView.findViewById(R.id.iv_product_re);
		pgoods_name=data.get(position).get("point_goodsname");
		pgoods_points=data.get(position).get("point_goodspoints");
		point_addtime=data.get(position).get("point_addtime");
		pgoods_body=data.get(position).get("pgoods_body");
		tv_productIntro.setText(pgoods_body);
		tv_productName.setText(pgoods_name);
		tv_productPoints.setText(pgoods_points);
		tv_recorddate.setText(point_addtime);
		ImageLoader.getInstance().displayImage(data.get(position).get("point_goodsimage"), iv_product);
		return cellView;
	}
}
