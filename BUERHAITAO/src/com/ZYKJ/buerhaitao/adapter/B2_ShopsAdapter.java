package com.ZYKJ.buerhaitao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.data.Shop;
import com.ZYKJ.buerhaitao.utils.ImageOptions;

public class B2_ShopsAdapter extends BaseAdapter {

	private List<Shop> list;
    private LayoutInflater inflater;
	
	
	public B2_ShopsAdapter(Context context, List<Shop> list) {
        inflater = LayoutInflater.from(context);
		this.list=list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Shop getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
        if(convertView == null){
            convertView=inflater.inflate(R.layout.ui_b2_item_goods,null);
            holder=new ViewHolder();
            holder.good_image= (ImageView) convertView.findViewById(R.id.good_image);//图片
            holder.good_name= (TextView) convertView.findViewById(R.id.good_name);//名称
            holder.good_juli= (TextView) convertView.findViewById(R.id.good_juli);//距离
            holder.good_jingle= (TextView) convertView.findViewById(R.id.good_jingle);//简介
            holder.goods_price= (TextView) convertView.findViewById(R.id.goods_price);//价格
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Shop shop = list.get(position);
        
        ImageOptions.displayImage2Circle(holder.good_image, shop.getStore_address(), 10f);
        holder.good_name.setText(shop.getSc_name()+"");
        holder.good_juli.setText(shop.getJuli()+"");
        holder.good_jingle.setText(shop.getArea_info()+"");
        holder.goods_price.setText(shop.getStore_avatar()+"");
		return convertView;
	}
	
	class ViewHolder{
        ImageView good_image;//图片
        TextView good_name;//名称
        TextView good_juli;//距离
        TextView good_jingle;//简介
        TextView goods_price;//价格
    }
}

