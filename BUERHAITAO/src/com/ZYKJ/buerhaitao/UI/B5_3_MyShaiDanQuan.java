package com.ZYKJ.buerhaitao.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.CircularImage;

/**
 * 晒单圈 （我的）
 * @author zyk
 *
 */
public class B5_3_MyShaiDanQuan extends BaseActivity{

	private ImageButton myshaidanquan_back;
	private Button btn_publish;
	private CircularImage img_head_myshaidanquan;
	private TextView tv_nickname_myshaidanquan;
	private LinearLayout ll_mypublish,ll_mycomment;
	private View v1,v2;
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		String headImgString=getSharedPreferenceValue("headImg_filename");
		String username=getSharedPreferenceValue("username");
		if (headImgString!=null) //如果登陆过，显示之前本地的头像
		{
//			File f = new File(headImgString); 
		    Bitmap bitmap_head=BitmapFactory.decodeFile(headImgString);
		    img_head_myshaidanquan.setImageBitmap(bitmap_head);
		}
		if (username!=null) {
			tv_nickname_myshaidanquan.setText(username);
		}
		super.onResume();
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b5_3_myshaidanquan);
		initView();
		setListener(myshaidanquan_back,btn_publish,ll_mypublish,ll_mycomment);
	}
	
	private void initView(){
		myshaidanquan_back=(ImageButton) findViewById(R.id.myshaidanquan_back);
		btn_publish=(Button)findViewById(R.id.btn_publish);
		img_head_myshaidanquan=(CircularImage) findViewById(R.id.img_head_myshaidanquan);
		tv_nickname_myshaidanquan=(TextView) findViewById(R.id.tv_nickname_myshaidanquan);
		ll_mypublish=(LinearLayout) findViewById(R.id.ll_mypublish);
		ll_mycomment=(LinearLayout) findViewById(R.id.ll_mycomment);
		v1=findViewById(R.id.v1);
		v2=findViewById(R.id.v2);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.myshaidanquan_back://返回
			this.finish();
		break;
		case R.id.btn_publish://发布
			Toast.makeText(this, "publish",  Toast.LENGTH_LONG).show();
		break;
			
		case R.id.ll_mypublish://我发表的
			v2.setVisibility(View.INVISIBLE);
			v1.setVisibility(View.VISIBLE);
			Toast.makeText(this, "我发表的",  Toast.LENGTH_LONG).show();
		break;
			
		case R.id.ll_mycomment://我评论的
			v2.setVisibility(View.VISIBLE);
			v1.setVisibility(View.INVISIBLE);
			Toast.makeText(this, "我评论的",  Toast.LENGTH_LONG).show();
		break;
		
		}

	}
}
