package com.ZYKJ.buerhaitao.UI;

import android.os.Bundle;
import android.view.View;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.base.BaseActivity;

/**
 * @author lss 2015年7月1日 购物车
 *
 */
public class B3_ShoppingCartActivity extends BaseActivity{
	//标题
// 	private TextView tv_sp_title;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b3_shoppingcart);
		initView();
	}
	
	private void initView(){
		/*tv_sp_title = (TextView)findViewById(R.id.tv_sp_title);
		
		setListener(im_sp_back,im_sp_gouwuche);*/
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		/*case R.id.im_sp_back:
			break;*/
		default:
			
			break;
		}

	}
}