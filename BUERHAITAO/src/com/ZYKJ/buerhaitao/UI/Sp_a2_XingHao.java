package com.ZYKJ.buerhaitao.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.base.BaseActivity;

/**
 * @author lss 2015年7月4日选择型号
 *
 */
public class Sp_a2_XingHao extends BaseActivity{
	//三个退出
	private LinearLayout ll_sp_a1_back1;
	private View view_sp_a1_back2;
	private ImageView im_sp_a1_back3;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_sp_a2_xinghao);
		initView();
	}
	
	private void initView(){
		ll_sp_a1_back1 = (LinearLayout)findViewById(R.id.ll_sp_a1_back1);
		view_sp_a1_back2 = (View)findViewById(R.id.view_sp_a1_back2);
		im_sp_a1_back3 = (ImageView)findViewById(R.id.im_sp_a1_back3);
		
		setListener(ll_sp_a1_back1,view_sp_a1_back2,im_sp_a1_back3);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.ll_sp_a1_back1:
			super.finish();  
			this.overridePendingTransition(R.anim.activity_close,0); 
//			finish();
			break;
		case R.id.view_sp_a1_back2:
			finish();
			break;
		case R.id.im_sp_a1_back3:
			finish();
			break;
		
		default:
			break;
		}
	}
	
	@Override  
	public void finish() {
	    // TODO Auto-generated method stub  
	    super.finish();  
	    //关闭窗体动画显示  
	    this.overridePendingTransition(R.anim.activity_close,0);  
	} 
}