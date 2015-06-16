package com.ZYKJ.buerhaitao.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 兑换详情页面
 * @author zyk
 *
 */
public class B5_11_1_ExchangeDetail extends BaseActivity {
	ImageButton set_back;
	RelativeLayout choseAddress;
	ImageView iv_product;
	TextView tv_producName,tv_productIntro,tv_productPoints;
	Button btn_exchange;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_b5_11_1_exchangedetail);
		
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();

        
		set_back=(ImageButton) findViewById(R.id.set_back);
		choseAddress=(RelativeLayout) findViewById(R.id.choseAddress);
		iv_product=(ImageView) findViewById(R.id.iv_product);
		tv_producName=(TextView) findViewById(R.id.tv_producName);
		tv_productIntro=(TextView) findViewById(R.id.tv_productIntro);
		tv_productPoints=(TextView) findViewById(R.id.tv_productPoints);
		btn_exchange=(Button) findViewById(R.id.btn_exchange);
		
		
		ImageLoader.getInstance().displayImage(bundle.getString("pgoods_image"), iv_product);
		tv_producName.setText(bundle.getString("pgoods_name"));
		tv_productIntro.setText(bundle.getString("pgoods_body"));
		tv_productPoints.setText(bundle.getString("pgoods_points"));
		
		setListener(set_back,choseAddress,btn_exchange);
		
        
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.set_back://返回
			this.finish();
			break;
		case R.id.choseAddress://选择收货地址
			this.finish();
			break;
		case R.id.btn_exchange://立即兑换
			this.finish();
			break;

		default:
			break;
		}
	}
}
