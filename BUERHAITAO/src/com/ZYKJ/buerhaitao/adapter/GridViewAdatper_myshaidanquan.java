package com.ZYKJ.buerhaitao.adapter;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GridViewAdatper_myshaidanquan extends BaseAdapter {

	private Activity c;
	private JSONObject obj;
	ViewHolder viewHolder = null;
	public GridViewAdatper_myshaidanquan(Activity c, JSONObject obj) {
		this.c = c;
		this.obj = obj;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return obj == null ? 0 : obj.length();
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
		if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(c);
            convertView = mInflater.inflate(R.layout.ui_b5_3_myshaidanquanl_photos_items, null);
            viewHolder.photo = (ImageView)convertView.findViewById(R.id.iv_photos);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }
		try {
//		String unsubString ;
		String pathString;
//		unsubString = spStr[position];//未切割的字符串
//		int start = unsubString.indexOf("\"");
//		int end = unsubString.lastIndexOf("\"");
//	    pathString = (String) unsubString.subSequence(start+1, end);//切割完毕之后的能够下载的图片路径
		
			pathString = obj.getString(position+"");
			Tools.Log("pathString="+pathString);
			ImageLoader.getInstance().displayImage(pathString, viewHolder.photo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return convertView;
	}
	private static class ViewHolder
    {
	    ImageView photo ;//图片
    }

}
