package com.ZYKJ.buerhaitao.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.adapter.JieSuanAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.MyListView;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.ZYKJ.buerhaitao.view.UIDialog;
import com.loopj.android.http.JsonHttpResponseHandler;

public class JieSuan1Activity extends BaseActivity{
	// 返回
	private ImageButton im_jiesuan_back;
	//列表
    private MyListView listview;
    private JieSuanAdapter adapter;
    private String key;
	private List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
	private List<Map<String, String>> dataList1 = new ArrayList<Map<String,String>>();
    //地址
    private TextView tv_buyer_name,tv_buyer_number,tv_buyer_address;
	//结算
	private RelativeLayout rl_zhifufangshi;
	private TextView tv_zffs;
	//是否支持货到付款
	private String ifshow_offpay;
	private String xzhdgg;
	private String allpri;
	private TextView tv_sumgoods1;
	private TextView tv_jiesuanqueren;
	private String address_id;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b3_jiesuaninfo);
		iniView();
		
	}

	private void iniView() {
		im_jiesuan_back = (ImageButton)findViewById(R.id.im_jiesuan_back);
		listview = (MyListView) findViewById(R.id.list_shoppingcar1);
		tv_buyer_name = (TextView) findViewById(R.id.tv_buyer_name);//收货人姓名
		tv_buyer_number = (TextView) findViewById(R.id.tv_buyer_number);//收货人电话
		tv_buyer_address = (TextView) findViewById(R.id.tv_buyer_address);//收货地址
		rl_zhifufangshi = (RelativeLayout) findViewById(R.id.rl_zhifufangshi);
		tv_sumgoods1 = (TextView)findViewById(R.id.tv_sumgoods1);//总价格
		tv_jiesuanqueren = (TextView)findViewById(R.id.tv_jiesuanqueren);//确认
		tv_zffs = (TextView) findViewById(R.id.tv_zffs);
		allpri = getIntent().getStringExtra("allpri");
		key = getSharedPreferenceValue("key");
		tv_sumgoods1.setText(allpri);
		xzhdgg = getIntent().getStringExtra("xzhdgg");
		String allche = xzhdgg+"|1";
		HttpUtils.getBuyFirst(res_ShoppingCarInfo,key,allche,"0");
		
		
		adapter = new JieSuanAdapter(this,dataList,dataList1,key);
		listview.setAdapter(adapter);
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(true);
		listview.setRefreshTime();
		
		
		setListener(im_jiesuan_back,rl_zhifufangshi,tv_jiesuanqueren);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_jiesuan_back:
			JieSuan1Activity.this.finish();
			break;
		case R.id.rl_zhifufangshi:
			String a = ifshow_offpay;
			if (ifshow_offpay=="true") {
				UIDialog.ForFiveBtn(this, new String[] { "钱包支付", "微信支付", "支付宝支付","货到付款", "取消" }, this);
			}else {
				UIDialog.ForFourBtn(this, new String[] { "钱包支付", "微信支付", "支付宝支付", "取消" }, this);
			}
			break;
		case R.id.dialog_five_modif_1:
			tv_zffs.setText("钱包支付");
			UIDialog.closeDialog();

			break;
		case R.id.dialog_five_modif_2:
			tv_zffs.setText("微信支付");
			UIDialog.closeDialog();

			break;
		case R.id.dialog_five_modif_3:
			tv_zffs.setText("支付宝支付");
			UIDialog.closeDialog();

			break;
		case R.id.dialog_five_modif_4:
			tv_zffs.setText("货到付款");
			UIDialog.closeDialog();

			break;
		case R.id.dialog_five_modif_5:
			tv_zffs.setText("");
			UIDialog.closeDialog();

			break;
		case R.id.dialog_four_modif_1:
			tv_zffs.setText("钱包支付");
			UIDialog.closeDialog();
			break;
		case R.id.dialog_four_modif_2:
			tv_zffs.setText("微信支付");
			Object asss = new Object();
			UIDialog.closeDialog();

			break;
		case R.id.dialog_four_modif_3:
			tv_zffs.setText("支付宝支付");
			UIDialog.closeDialog();

			break;
		case R.id.dialog_four_modif_4:
			tv_zffs.setText("");
			UIDialog.closeDialog();

			break;
		case R.id.tv_jiesuanqueren:
			String zffs = "";
			if (tv_zffs.equals("钱包支付")) {
//				HttpUtils.getBuySecond(res_BuySecond,"3ae653eb52824dbc4ba977de343e2e12","1",allcheckinfo,address_id  ,1);
			}else{
//				HttpUtils.getBuySecond(res_BuySecond,"3ae653eb52824dbc4ba977de343e2e12","1",allcheckinfo,address_id  ,0);
			}
			
			break;
		default:
			break;

		}
	}
	

	/**
	 * 获得订单列表
	 */
	JsonHttpResponseHandler res_ShoppingCarInfo = new JsonHttpResponseHandler()
	{
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String error=null;
			JSONObject datas=null;
			try {
				 datas = response.getJSONObject("datas");
//				 Tools.Log("datas="+datas);
				 error = response.getString("error");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if (error==null)//成功
			{
				try {
					dataList.clear();
//					address_info 
					JSONObject address_object = datas.getJSONObject("address_info");
					tv_buyer_name.setText("收货人："+address_object.getString("true_name"));
					tv_buyer_number.setText(address_object.getString("mob_phone"));
					tv_buyer_address.setText("收货地址："+ address_object.getString("address"));
					address_id = address_object.getString("address_id");
					ifshow_offpay = datas.getString("ifshow_offpay");
					
					JSONArray store_cart_list = datas.getJSONArray("store_cart_list");
					for (int i = 0; i < store_cart_list.length(); i++) {
						JSONObject jsonItem = store_cart_list.getJSONObject(i); 
						Map<String, String> map1 = new HashMap();
						Map<String, Object> map = new HashMap();
						map1.put("store_name", jsonItem.getString("store_name"));
						map1.put("store_goods_total", jsonItem.getString("store_goods_total"));
						map1.put("store_freight_price", jsonItem.getString("store_freight_price"));
						map.put("goods_list", jsonItem.getJSONArray("goods_list"));
						dataList.add(map);
						dataList1.add(map1);
					}
					/*JSONArray order_group_list = datas.getJSONArray("order_group_list");//
					Tools.Log("order_group_list="+order_group_list);
					for (int j = 0; j < order_group_list.length(); j++) {
						Map<String, Object> map = new HashMap();
						JSONObject order_group_list1 = (JSONObject) order_group_list.get(j);
						JSONArray order_list = order_group_list1.getJSONArray("order_list");
						JSONObject order_list1 =((JSONObject) order_list.get(0));
						
						map.put("store_name", order_list1.getString("store_name"));
						map.put("store_phone", order_list1.getString("store_phone"));
						map.put("order_id", order_list1.getString("order_id"));
						map.put("pay_sn", order_list1.getString("pay_sn"));
						map.put("order_amount", order_list1.getString("order_amount"));
						map.put("extend_order_goods", order_list1.getJSONArray("extend_order_goods"));
						dataList.add(map);
					}*/
					adapter.notifyDataSetChanged();
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
	
	
}
