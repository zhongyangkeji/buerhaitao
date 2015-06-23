package com.ZYKJ.buerhaitao.UI;

/**
 * 首页界面 lss 6.17
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.popupwindow.AddPopWindow;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.ZYKJ.buerhaitao.view.ToastView;
import com.loopj.android.http.JsonHttpResponseHandler;

public class B1_HomeActivity extends BaseActivity {
	//首页中间八个大分类
	ImageView im_b1nvshi,im_b1nanshi,im_b1muying,im_b1huazhuang,im_b1shouji,im_b1bangong,im_b1shenghuo,im_b1techan;
	//天天特价,晒单圈,猜你喜欢，每日好店
	RelativeLayout rl_b1_a1tttj,b5_3_shaidanquan,rl_b1_a2_cnxh,rl_b1_a3_mrhd;
	//搜索选择
	RelativeLayout rl_sousuokuang;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_index);
		initView();
		HttpUtils.getFirstList(res_getSyList, "00","35","118");
	}
	
	JsonHttpResponseHandler res_getSyList = new JsonHttpResponseHandler()
	{
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("res_getAddress="+response);
			String error=null;
			JSONObject datas=null;
			try {
				 datas = response.getJSONObject("datas");
				 error = response.getString("error");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if (error==null)//成功
			{
				try {
					data.clear();
					org.json.JSONArray array = datas.getJSONArray("slide");
					for (int i = 0; i < array.length(); i++) {
//						JSONObject jsonItem = array.getJSONObject(i);
//						Map<String, String> map = new HashMap();
//						map.put("pic_name", jsonItem.getString("pic_name"));
//						map.put("pic_url", jsonItem.getString("pic_url"));
//						map.put("color", jsonItem.getString("color"));
//						map.put("pic_id", jsonItem.getString("pic_id"));
//						map.put("pic_img", jsonItem.getString("pic_img"));
//						data.add(map);
					}
				} 
				catch (org.json.JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error+"");
//				Tools.Notic(B5_MyActivity.this, error+"", null);
			}
			
		}
		
		
	};
	
	private void initView(){
		im_b1nvshi = (ImageView)findViewById(R.id.im_b1nvshi);
		im_b1nanshi = (ImageView)findViewById(R.id.im_b1nanshi);
		im_b1muying = (ImageView)findViewById(R.id.im_b1muying);
		im_b1huazhuang = (ImageView)findViewById(R.id.im_b1huazhuang);
		im_b1shouji = (ImageView)findViewById(R.id.im_b1shouji);
		im_b1bangong = (ImageView)findViewById(R.id.im_b1bangong);
		im_b1shenghuo = (ImageView)findViewById(R.id.im_b1shenghuo);
		im_b1techan = (ImageView)findViewById(R.id.im_b1techan);
		rl_b1_a1tttj = (RelativeLayout)findViewById(R.id.rl_b1_a1tttj);
		b5_3_shaidanquan = (RelativeLayout)findViewById(R.id.b5_3_shaidanquan);
		rl_b1_a2_cnxh = (RelativeLayout)findViewById(R.id.rl_b1_a2_cnxh);
		rl_b1_a3_mrhd = (RelativeLayout)findViewById(R.id.rl_b1_a3_mrhd);
		rl_sousuokuang = (RelativeLayout)findViewById(R.id.rl_sousuokuang);
		setListener(im_b1nvshi,im_b1nanshi,im_b1muying,im_b1huazhuang,im_b1shouji,im_b1bangong,im_b1shenghuo,im_b1techan,rl_b1_a1tttj,b5_3_shaidanquan,rl_b1_a2_cnxh,rl_b1_a3_mrhd,rl_sousuokuang);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		//女士服装
		case R.id.im_b1nvshi:
			Intent itnvshi = new Intent();
			itnvshi.setClass(B1_HomeActivity.this, B1_1_NvShiFuZhuang.class);
			startActivity(itnvshi);
			break;
		//男士服装
		case R.id.im_b1nanshi:
			Intent itnanshi = new Intent();
			itnanshi.setClass(B1_HomeActivity.this, B1_2_NanShiFuZhuang.class);
			startActivity(itnanshi);
			break;
		//母婴
		case R.id.im_b1muying:
			Intent itmuying = new Intent();
			itmuying.setClass(B1_HomeActivity.this, B1_3_MuYing.class);
			startActivity(itmuying);
			break;
		//化妆品
		case R.id.im_b1huazhuang:
			Intent ithuazhuang = new Intent();
			ithuazhuang.setClass(B1_HomeActivity.this, B1_4_HuaZhuangPin.class);
			startActivity(ithuazhuang);
			break;
		//手机数码
		case R.id.im_b1shouji:
			Intent itshouji = new Intent();
			itshouji.setClass(B1_HomeActivity.this, B1_5_ShouJiShuMa.class);
			startActivity(itshouji);
			break;
		//办公家电
		case R.id.im_b1bangong:
			Intent itbangong = new Intent();
			itbangong.setClass(B1_HomeActivity.this, B1_6_BanGongJiaDian.class);
			startActivity(itbangong);
			break;
		//生活服务
		case R.id.im_b1shenghuo:
			Intent itshenghuo = new Intent();
			itshenghuo.setClass(B1_HomeActivity.this, B1_7_ShengHuoFuWu.class);
			startActivity(itshenghuo);
			break;
		//特产
		case R.id.im_b1techan:
			Intent ittechan = new Intent();
			ittechan.setClass(B1_HomeActivity.this, B1_8_TeChan.class);
			startActivity(ittechan);
			break;
		//天天特价
		case R.id.rl_b1_a1tttj:
			Intent ittttj = new Intent();
			ittttj.setClass(B1_HomeActivity.this, B1_a1_TianTianTeJia.class);
			startActivity(ittttj);
			break;
		//晒单圈
		case R.id.b5_3_shaidanquan:
			Intent itshaidanquan = new Intent();
			itshaidanquan.setClass(B1_HomeActivity.this, B5_3_ShaiDanQuan.class);
			startActivity(itshaidanquan);
			break;
		//猜你喜欢
		case R.id.rl_b1_a2_cnxh:
			Intent itcnxh = new Intent();
			itcnxh.setClass(B1_HomeActivity.this, B1_a2_CaiNiXiHuan.class);
			startActivity(itcnxh);
			break;
			
		//每日好店
		case R.id.rl_b1_a3_mrhd:
			Intent itmrhd = new Intent();
			itmrhd.setClass(B1_HomeActivity.this, B1_a3_MeiRiHaoDian.class);
			startActivity(itmrhd);
			break;
		//宝贝
//		case R.id.tv_baobei:
//			AddPopWindow addPopWindow = new AddPopWindow(B1_HomeActivity.this);
//			addPopWindow.showAtLocation(tv_baobei, Gravity.LEFT | Gravity.TOP, 110,65 );
////			addPopWindow.showPopupWindow(tv_baobei);
//			break;
		case R.id.rl_sousuokuang:
			Intent itsydps = new Intent();
			itsydps.setClass(B1_HomeActivity.this, B1_a4_SearchActivity.class);
			startActivity(itsydps);
			break;
		
		case R.id.error_layout:// 错误页面的点击
			//htttp请求
			break;
		}

	}
	
	// 退出操作
	private boolean isExit = false;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				ToastView toast = new ToastView(getApplicationContext(),
						"再按一次退出程序");
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				Handler mHandler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						isExit = false;
					}
				};
				mHandler.sendEmptyMessageDelayed(0, 3000);
				return true;
			} else {
				android.os.Process.killProcess(android.os.Process.myPid());
				return false;
			}
		}
		return true;
	}

	/** 类型选择 */
	public void selectType(int type) {
//		Intent intent = new Intent(this, ShopList.class);
//		intent.putExtra("state", 1);
//		intent.putExtra("type", type);
//		startActivity(intent);
		Toast.makeText(this, type, Toast.LENGTH_LONG).show();
	}

}
