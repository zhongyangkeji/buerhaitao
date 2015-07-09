package com.ZYKJ.buerhaitao.adapter;

import org.json.JSONArray;
import org.json.JSONException;
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
import android.widget.TextView;

import com.ZYKJ.buerhaitao.R;



public class B5_3_MyShaiDanQuanPinglunAdapter extends BaseAdapter {

//	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	JSONArray quote = null;
	private Activity c;
	private LayoutInflater listContainer;
	ViewHolder viewHolder = null;
	String commentString;
//	ListView lv_comment;
	GridViewAdatper_myshaidanquan photosadapter;

	public B5_3_MyShaiDanQuanPinglunAdapter(Activity c, JSONArray quote) {
		this.c = c;
		this.quote = quote;
		listContainer = LayoutInflater.from(c);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return quote == null ? 0 : quote.length();
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
            convertView = mInflater.inflate(R.layout.ui_b5_3_myshaidanquanlpinglun_items, null);
            
            TextView tv_member_name;//评论者的昵称
			TextView tv_huifu;//“回复”这两个字
			TextView tv_reply_replyname;//被评论者的昵称
			TextView tv_reply_content;//评论的内容
			
            viewHolder.tv_member_name = (TextView) convertView.findViewById(R.id.tv_member_name);//评论者的昵称
            viewHolder.tv_huifu = (TextView) convertView.findViewById(R.id.tv_huifu);//“回复”这两个字
            viewHolder.tv_reply_replyname = (TextView) convertView.findViewById(R.id.tv_reply_replyname);//被评论者的昵称
            viewHolder.tv_reply_content = (TextView) convertView.findViewById(R.id.tv_reply_content);//评论的内容
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        try {
        	String member_name = ((JSONObject)quote.get(position)).getString("member_name");
        	String reply_content = ((JSONObject)quote.get(position)).getString("reply_content");
        	String reply_replyname = ((JSONObject)quote.get(position)).getString("reply_replyname");
        	
        	if (reply_replyname.equals("")) {//如果是直接回复圈主，则不显示“回复”，被回复的人的昵称
        		viewHolder.tv_huifu.setVisibility(View.GONE);
        		viewHolder.tv_reply_replyname.setVisibility(View.GONE);
			}else {
				viewHolder.tv_reply_replyname.setText(reply_replyname);
			}
        	viewHolder.tv_member_name.setText(member_name);
        	viewHolder.tv_reply_content.setText(reply_content);
        	
        } catch (JSONException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
		return convertView;
	}
	
//	class Comment implements View.OnClickListener {
//		int position;
//		public Comment(int position) {
//			this.position = position;
//		}
//		@Override
//		public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//			final AlertDialog.Builder builder = new AlertDialog.Builder(c);
//			final AlertDialog aDialog=builder.create();  
//			aDialog.show();
//			
//			Window window = aDialog.getWindow();
//			window.setContentView(R.layout.dialog_type_in);
//			aDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);//呼出输入法
//			final EditText mEditText=(EditText) window.findViewById(R.id.et_type_in);
//			Button btn_changeName=(Button) window.findViewById(R.id.btn_changeName);
//			btn_changeName.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					commentString=mEditText.getText().toString().trim();
//					 if (commentString.equals("")) {
////						 Toast.makeText(c, "昵称不能为空", Toast.LENGTH_LONG).show();
//						 aDialog.dismiss();
//					}else{
////						btn_login.setText(usernameString);//设置昵称并请求服务器
//						aDialog.dismiss();
//						//评论的网络请求
////						HttpUtils.editname(res_editName, usernameString, getSharedPreferenceValue("key"));
////						RequestDailog.showDialog(B5_MyActivity.this, "正在修改昵称，请稍后");
//					}
//					
//				}
//			});
//		}
//	}
	
	 private static class ViewHolder
	    {
			TextView tv_member_name;//评论者的昵称
			TextView tv_huifu;//“回复”这两个字
			TextView tv_reply_replyname;//被评论者的昵称
			TextView tv_reply_content;//评论的内容
	    }
	 
}
