package com.ZYKJ.buerhaitao.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.popupwindow.AddPopWindow;

/**
 * @author lss 2015年6月18日 搜索页
 *
 */
public class B1_a4_SearchActivity extends BaseActivity{
	//返回
	ImageButton a4_back;
	//宝贝/店铺
	TextView tv_a4_baobei;
	//默认，销量，价格，好评
	TextView tv_a4_moren,tv_a4_xiaoliang,tv_a4_jiage,tv_a4_haoping;
	LinearLayout ly_a4_xiaoliang,ly_a4_jiage;
	//销量，价格箭头
	ImageView im_a4_xiaoliangstate,im_a4_jiagestate;
	
	private int mrstate = 0;// 按默认排序的状态，0为第一次点击，1为多次点击
	private int hpstate = 0;// 按好评排序的状态
	private int xlstate = 0;// 按销量排序的状态，0为从高到低，1为从低到高
	private int jgstate = 0;// 按价格排序的状态，0为从低到高，1为从高到低

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_a4_search);
		initView();
	}
	
	private void initView(){
		a4_back = (ImageButton)findViewById(R.id.a4_back);
		tv_a4_baobei = (TextView)findViewById(R.id.tv_a4_baobei);
		tv_a4_moren = (TextView)findViewById(R.id.tv_a4_moren);
		tv_a4_xiaoliang = (TextView)findViewById(R.id.tv_a4_xiaoliang);
		tv_a4_jiage = (TextView)findViewById(R.id.tv_a4_jiage);
		tv_a4_haoping = (TextView)findViewById(R.id.tv_a4_haoping);
		im_a4_xiaoliangstate = (ImageView)findViewById(R.id.im_a4_xiaoliangstate);
		im_a4_jiagestate = (ImageView)findViewById(R.id.im_a4_jiagestate);
		ly_a4_xiaoliang = (LinearLayout)findViewById(R.id.ly_a4_xiaoliang);
		ly_a4_jiage = (LinearLayout)findViewById(R.id.ly_a4_jiage);
		setListener(a4_back,tv_a4_baobei,tv_a4_moren,tv_a4_haoping,ly_a4_xiaoliang,ly_a4_jiage);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.a4_back:
			B1_a4_SearchActivity.this.finish();
			break;
		case R.id.tv_a4_baobei:
			AddPopWindow addPopWindow = new AddPopWindow(B1_a4_SearchActivity.this,tv_a4_baobei);
			addPopWindow.showAtLocation(tv_a4_baobei, Gravity.LEFT | Gravity.TOP, 110,65 );
//			addPopWindow.showPopupWindow(tv_baobei);
			break;
		case R.id.tv_a4_moren:
			if (mrstate == 0) {
				tv_a4_moren.setTextColor(Color.parseColor("#73498b"));
				tv_a4_xiaoliang.setTextColor(Color.parseColor("#808080"));
				tv_a4_jiage.setTextColor(Color.parseColor("#808080"));
				tv_a4_haoping.setTextColor(Color.parseColor("#808080"));
				im_a4_xiaoliangstate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchmoren));
				im_a4_jiagestate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchmoren));
				
				mrstate = 1;
				hpstate = 0;
				xlstate = 0;
				jgstate = 0;
			} else {
				mrstate = 1;
				hpstate = 0;
				xlstate = 0;
				jgstate = 0;
			}
			break;
		case R.id.ly_a4_xiaoliang:
			tv_a4_moren.setTextColor(Color.parseColor("#808080"));
			tv_a4_xiaoliang.setTextColor(Color.parseColor("#73498b"));
			tv_a4_jiage.setTextColor(Color.parseColor("#808080"));
			tv_a4_haoping.setTextColor(Color.parseColor("#808080"));
			im_a4_jiagestate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchmoren));
			if (xlstate==0) {//销量最多
				im_a4_xiaoliangstate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searcjshang));
				xlstate = 1;
			}else{//销量最少
				im_a4_xiaoliangstate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchxia));
				xlstate = 0;
			}
			mrstate = 0;
			hpstate = 0;
			jgstate = 0;
			break;
		case R.id.ly_a4_jiage:
			tv_a4_moren.setTextColor(Color.parseColor("#808080"));
			tv_a4_xiaoliang.setTextColor(Color.parseColor("#808080"));
			tv_a4_jiage.setTextColor(Color.parseColor("#73498b"));
			tv_a4_haoping.setTextColor(Color.parseColor("#808080"));
			im_a4_xiaoliangstate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchmoren));
			if (jgstate==0) {//销量最多
				im_a4_jiagestate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searcjshang));
				jgstate = 1;
			}else{//销量最少
				im_a4_jiagestate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchxia));
				jgstate = 0;
			}
			mrstate = 0;
			hpstate = 0;
			xlstate = 0;
			break;
		case R.id.tv_a4_haoping:
			if (hpstate == 0) {
				tv_a4_moren.setTextColor(Color.parseColor("#808080"));
				tv_a4_xiaoliang.setTextColor(Color.parseColor("#808080"));
				tv_a4_jiage.setTextColor(Color.parseColor("#808080"));
				tv_a4_haoping.setTextColor(Color.parseColor("#73498b"));
				im_a4_xiaoliangstate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchmoren));
				im_a4_jiagestate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchmoren));
				
				mrstate = 0;
				hpstate = 1;
				xlstate = 0;
				jgstate = 0;
			} else {
				mrstate = 0;
				hpstate = 1;
				xlstate = 0;
				jgstate = 0;
			}
			break;

		default:
			break;
		}

	}
}