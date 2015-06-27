package com.ZYKJ.buerhaitao.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.UI.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.NoScrollGridView;

public class B5_3_MyShaiDanQuanAdapter extends BaseAdapter {

	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private Activity c;
	private LayoutInflater listContainer;
	ViewHolder viewHolder = null;
	String commentString;
//	ListView lv_comment;
	GridViewAdatper_myshaidanquan photosadapter;

	public B5_3_MyShaiDanQuanAdapter(Activity c, List<Map<String, Object>> data) {
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

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		
//		ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(c);
            convertView = mInflater.inflate(R.layout.ui_b5_3_myshaidanquanl_items, null);

            viewHolder.iv_head_img = (ImageView) convertView.findViewById(R.id.iv_head_img);//头像
            viewHolder.tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);//昵称
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);//发布的时间
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);//发布的内容
            viewHolder.tv_num_look = (TextView) convertView.findViewById(R.id.tv_num_look);//多少人看过
            viewHolder.tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);//赞数
            viewHolder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);//评论数
            viewHolder.gridView = (NoScrollGridView) convertView.findViewById(R.id.gridView);
//          viewHolder.comment0 = (LinearLayout) convertView.findViewById(R.id.comment0);
//          viewHolder.tv_checkallcomment = (TextView) convertView.findViewById(R.id.tv_checkallcomment);//查看所有3条评论
//          viewHolder.comment_container = (LinearLayout) convertView.findViewById(R.id.comment_container);//查看所有3条评论
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        JSONObject obj = (JSONObject) data.get(position).get("image");
//        Tools.Log("obj.length="+obj.length());
        if (obj.length()!=0) {
        	viewHolder.gridView.setVisibility(View.VISIBLE);
        	photosadapter = new GridViewAdatper_myshaidanquan(c,obj);
        	viewHolder.gridView.setAdapter(photosadapter);
		}else {
			Tools.Log("没有上传图片");
		}
    	
		String replys = data.get(position).get("replys").toString();//评论数
		
		viewHolder.tv_nickname.setText(data.get(position).get("member_name").toString());//昵称
		viewHolder.tv_content.setText(data.get(position).get("description").toString());//内容
		viewHolder.tv_date.setText(data.get(position).get("addtime").toString());//日期
		viewHolder.tv_zan.setText(data.get(position).get("praise").toString());//赞个数
		viewHolder.tv_comment.setText(replys);//评论数
		viewHolder.tv_num_look.setText(data.get(position).get("views")+"人浏览过");//123人浏览过
//		viewHolder.tv_checkallcomment.setText("查看所有"+replys+"条评论");//查看所有3条评论
//		viewHolder.tv_checkallcomment.setOnClickListener(new Checkallcomment(position));
//		viewHolder.comment0.setOnClickListener(new Comment(position));
		ImageLoader.getInstance().displayImage(data.get(position).get("avatar").toString(), viewHolder.iv_head_img);
//		ImageLoader.getInstance().displayImage(data.get(position).get("image").toString(), viewHolder.iv_head_img);
		
		return convertView;
	}
//	/**
//	 * 点击查看所有3条评论
//	 * @author zyk
//	 *
//	 */
//	class Checkallcomment implements View.OnClickListener {
//		int position;
//		public Checkallcomment(int position) {
//			this.position = position;
//		}
//		@Override
//		public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//			viewHolder.comment_container.setVisibility(View.VISIBLE);
//		}
//	}
	class Comment implements View.OnClickListener {
		int position;
		public Comment(int position) {
			this.position = position;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			final AlertDialog.Builder builder = new AlertDialog.Builder(c);
			final AlertDialog aDialog=builder.create();  
			aDialog.show();
			
			Window window = aDialog.getWindow();
			window.setContentView(R.layout.dialog_type_in);
			aDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);//呼出输入法
			final EditText mEditText=(EditText) window.findViewById(R.id.et_type_in);
			Button btn_changeName=(Button) window.findViewById(R.id.btn_changeName);
			btn_changeName.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					commentString=mEditText.getText().toString().trim();
					 if (commentString.equals("")) {
//						 Toast.makeText(c, "昵称不能为空", Toast.LENGTH_LONG).show();
						 aDialog.dismiss();
					}else{
//						btn_login.setText(usernameString);//设置昵称并请求服务器
						aDialog.dismiss();
						//评论的网络请求
//						HttpUtils.editname(res_editName, usernameString, getSharedPreferenceValue("key"));
//						RequestDailog.showDialog(B5_MyActivity.this, "正在修改昵称，请稍后");
					}
					
				}
			});
		}
	}
	
	 private static class ViewHolder
	    {
		    ImageView iv_head_img ;//头像
			TextView tv_nickname ;//昵称
			TextView tv_date;//发布的时间
			TextView tv_content ;//发布的内容
			TextView tv_num_look ;//多少人看过
			TextView tv_zan ;//赞数
			TextView tv_comment;//评论数
//			TextView tv_checkallcomment ;//查看所有3条评论
			LinearLayout comment_container;//所有评论内容
//			LinearLayout comment0;//所有评论内容
			LinearLayout commentnew;//所有评论内容
			
			NoScrollGridView gridView;//显示上传图片
	    }
	
}
