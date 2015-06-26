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
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.adapter.B1_a2_CaiNiLikeAdapter;
import com.ZYKJ.buerhaitao.adapter.B1_a3_MeiRiHaoDianAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.popupwindow.AddPopWindow;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.ZYKJ.buerhaitao.view.ToastView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class B1_HomeActivity extends BaseActivity {
	//首页中间八个大分类
	ImageView im_b1nvshi,im_b1nanshi,im_b1muying,im_b1huazhuang,im_b1shouji,im_b1bangong,im_b1shenghuo,im_b1techan;
	//天天特价,晒单圈,猜你喜欢，每日好店
	RelativeLayout rl_b1_a1tttj,b5_3_shaidanquan,rl_b1_a2_cnxh,rl_b1_a3_mrhd;
	//搜索选择
	RelativeLayout rl_sousuokuang;
	//每日好店
	ListView list_meirihaodian,list_cainilike;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	List<Map<String, String>> data1 = new ArrayList<Map<String, String>>();
	//天天特价
	ImageView im_b1_a1_pic;
	TextView tv_b1_a1_chanpinname,tv_b1_a1_chanpinjianjie,tv_b1_a1_zhehoujia,tv_b1_a1_yuanjia;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_index);
		initView();
		HttpUtils.getFirstList(res_getSyList, "88","80","100");
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
					//每日好店
					org.json.JSONArray array = datas.getJSONArray("good_store");
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonItem = array.getJSONObject(i);
						Map<String, String> map = new HashMap<String, String>();
						map.put("store_name", jsonItem.getString("store_name"));
						map.put("sc_name", jsonItem.getString("sc_name"));
						map.put("area_info", jsonItem.getString("area_info"));
						map.put("store_address", jsonItem.getString("store_address"));
						map.put("store_label", jsonItem.getString("store_label"));
						map.put("store_desccredit", jsonItem.getString("store_desccredit"));
						map.put("juli", jsonItem.getString("juli"));						
						data.add(map);
					}
					B1_a3_MeiRiHaoDianAdapter goodadapter = new B1_a3_MeiRiHaoDianAdapter(B1_HomeActivity.this, data);
					list_meirihaodian.setAdapter(goodadapter);
					//猜你喜欢
					data1.clear();
					org.json.JSONArray array1 = datas.getJSONArray("goods_like");
					for (int i = 0; i < array1.length(); i++) {
						JSONObject jsonItem1 = array1.getJSONObject(i);
						Map<String, String> map1 = new HashMap<String, String>();
						map1.put("goods_jingle", jsonItem1.getString("goods_jingle"));
						map1.put("goods_name", jsonItem1.getString("goods_name"));
						map1.put("nc_distinct", jsonItem1.getString("nc_distinct"));
						map1.put("juli", jsonItem1.getString("juli"));
						map1.put("goods_price", jsonItem1.getString("goods_price"));
						map1.put("goods_image", jsonItem1.getString("goods_image"));
						map1.put("goods_id", jsonItem1.getString("goods_id"));						
						data1.add(map1);
					}
					B1_a2_CaiNiLikeAdapter cainilikeadapter = new B1_a2_CaiNiLikeAdapter(B1_HomeActivity.this, data1);
					list_cainilike.setAdapter(cainilikeadapter);
					
					//天天特价
					org.json.JSONArray arr = datas.getJSONArray("day_special");
					JSONObject jsonIt = array1.getJSONObject(0);
					ImageLoader.getInstance().displayImage(jsonIt.getString("goods_image"), im_b1_a1_pic);
					tv_b1_a1_chanpinname.setText(jsonIt.getString("goods_name"));
					tv_b1_a1_chanpinjianjie.setText(jsonIt.getString("goods_jingle"));
					tv_b1_a1_zhehoujia.setText(jsonIt.getString("goods_price"));
					tv_b1_a1_yuanjia.setText(jsonIt.getString("goods_promotion_price"));
					tv_b1_a1_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); 
				} 
				catch (org.json.JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}/*circle*/
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
		list_meirihaodian = (ListView)findViewById(R.id.list_meirihaodian);
		list_cainilike = (ListView)findViewById(R.id.list_cainilike);
		im_b1_a1_pic = (ImageView)findViewById(R.id.im_b1_a1_pic);
		tv_b1_a1_chanpinname = (TextView)findViewById(R.id.tv_b1_a1_chanpinname);
		tv_b1_a1_chanpinjianjie = (TextView)findViewById(R.id.tv_b1_a1_chanpinjianjie);
		tv_b1_a1_zhehoujia = (TextView)findViewById(R.id.tv_b1_a1_zhehoujia);
		tv_b1_a1_yuanjia = (TextView)findViewById(R.id.tv_b1_a1_yuanjia);
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
