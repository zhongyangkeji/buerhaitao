package com.ZYKJ.buerhaitao.adapter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class B0_StoreInfoAdapter extends BaseAdapter {
	private Activity context;
	List<Map<String, Object>> data;

	public B0_StoreInfoAdapter(Activity context, List<Map<String, Object>> data1) {
		this.context = context;
		this.data=data1;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder ViewHolder=null;
		if(convertView==null){
			ViewHolder=new ViewHolder();
			convertView=LinearLayout.inflate(context, R.layout.item_b0_storeinfo, null);
			ViewHolder.im_a3_pic1=(ImageView) convertView.findViewById(R.id.im_a3_pic1);
			ViewHolder.tv_a3_storename1=(TextView) convertView.findViewById(R.id.tv_a3_storename1);
			ViewHolder.tv_chanpinjianjie=(TextView) convertView.findViewById(R.id.tv_chanpinjianjie);
			ViewHolder.tv_spzhj=(TextView) convertView.findViewById(R.id.tv_spzhj);
			ViewHolder.tv_spyj=(TextView) convertView.findViewById(R.id.tv_spyj);
//			ViewHolder.im_shenglve=(ImageView) convertView.findViewById(R.id.im_shenglve);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage((String)data.get(position).get("goods_image_url"), ViewHolder.im_a3_pic1);
		ViewHolder.tv_a3_storename1.setText(data.get(position).get("goods_name").toString());
		ViewHolder.tv_chanpinjianjie.setText(data.get(position).get("goods_jingle").toString());
//		ViewHolder.comment_rating_bar1.setRating(Float.parseFloat(data.get(position).get("evaluation_good_star")));
		ViewHolder.tv_spzhj.setText(data.get(position).get("goods_price").toString());
		ViewHolder.tv_spyj.setText(data.get(position).get("goods_promotion_price").toString());
//		ViewHolder.tv_a3_address1.setText(data.get(position).get("store_address"));
		return convertView;
	}
	public final class ViewHolder {  
        public ImageView im_a3_pic1;
        public TextView tv_a3_storename1;
        public TextView tv_chanpinjianjie;
        public TextView tv_spzhj;
        public TextView tv_spyj;
//        public ImageView im_shenglve;
    }  
}
