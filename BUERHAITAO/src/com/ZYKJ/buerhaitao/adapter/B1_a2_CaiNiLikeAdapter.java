package com.ZYKJ.buerhaitao.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.UI.R;
import com.ZYKJ.buerhaitao.adapter.B1_a3_MeiRiHaoDianAdapter.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author lss 2015年6月24日  猜你喜欢Adapter
 *
 */
public class B1_a2_CaiNiLikeAdapter   extends BaseAdapter {
	private Activity context;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();

	public B1_a2_CaiNiLikeAdapter(Activity context, List<Map<String, String>> data) {
		this.context = context;
		this.data=data;
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
			convertView=LinearLayout.inflate(context, R.layout.b1_a2_cainixihuanitem, null);
			ViewHolder.im_a2_pic=(ImageView) convertView.findViewById(R.id.im_a2_pic);
			ViewHolder.tv_a2_chanpinname=(TextView) convertView.findViewById(R.id.tv_a2_chanpinname);
			ViewHolder.tv_a2_juli=(TextView) convertView.findViewById(R.id.tv_a2_juli);
			ViewHolder.tv_a2_chanpinjianjie=(TextView) convertView.findViewById(R.id.tv_a2_chanpinjianjie);
			ViewHolder.tv_a2_jiage=(TextView) convertView.findViewById(R.id.tv_a2_jiage);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage((String)data.get(position).get("goods_image"), ViewHolder.im_a2_pic);
		ViewHolder.tv_a2_chanpinname.setText(data.get(position).get("goods_name"));
		ViewHolder.tv_a2_juli.setText(data.get(position).get("juli"));
		ViewHolder.tv_a2_chanpinjianjie.setText(data.get(position).get("goods_jingle"));
		ViewHolder.tv_a2_jiage.setText(data.get(position).get("goods_price"));
		return convertView;
	}
	public final class ViewHolder {  
        public ImageView im_a2_pic;
        public TextView tv_a2_chanpinname;
        public TextView tv_a2_juli;
        public TextView tv_a2_chanpinjianjie;
        public TextView tv_a2_jiage;
    }  
}
