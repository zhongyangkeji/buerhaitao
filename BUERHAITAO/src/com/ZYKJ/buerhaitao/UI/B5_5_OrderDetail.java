package com.ZYKJ.buerhaitao.UI;


import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.adapter.B5_5_OrderDetailAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.ZYKJ.buerhaitao.view.UIDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.pingplusplus.android.PaymentActivity;
/**
 * 订单详情
 * @author zyk
 *
 */
public class B5_5_OrderDetail extends BaseActivity {

	ImageView order_back;
	TextView tv_buyer_name,tv_buyer_number,tv_buyer_address,tv_storename_or,
	tv_ordergoodsnumber_or,tv_orderprice_or,tv_shipping_fee,tv_payment_name,tv_order_sn,tv_liuyan;
	ListView listview_goodslist_or;
	B5_5_OrderDetailAdapter adapter;
	Button btn_deletetheorder;//取消订单
	Button btn_paytheorder;//付款
	Button btn_delete_this;//删除订单
	Button btn_tocomment;//待评价
	Button btn_tuihuanhuo;//退换货
	Button btn_querenshouhuo;//确认收货
	String pay_sn,order_id,key;
	private int status=0;
    private static final String CHANNEL_WECHAT = "wx";//通过微信支付
    private static final String CHANNEL_ALIPAY = "alipay";//通过支付宝支付
    private static final int REQUEST_CODE_PAYMENT = 1;
//  order_state 订单状态（待付款:10,待发货:20,待收货:30,已收货:40）
    private static final int DAIFUKUAN  = 10;
    private static final int DAIFAHUO   = 20;
    private static final int DAISHOUHUO = 30;
    private static final int YISHOUHUO  = 40;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b5_5_orderdetail);
		key = getSharedPreferenceValue("key");
		Intent intent = getIntent();
		order_id = intent.getStringExtra("order_id");
		pay_sn = intent.getStringExtra("pay_sn");
		status = intent.getIntExtra("status", 0);
		Tools.Log("status="+status);
		initView();
		setListener(order_back,btn_deletetheorder,btn_paytheorder);
		RequestDailog.showDialog(this, "正在获取订单详情");
		HttpUtils.getOrderDetail(res_getOrderDetail, key, order_id);
	}
	private void initView() {
		// TODO Auto-generated method stub
		order_back = (ImageView) findViewById(R.id.order_back);
		tv_buyer_name = (TextView) findViewById(R.id.tv_buyer_name);//收货人姓名
		tv_buyer_number = (TextView) findViewById(R.id.tv_buyer_number);//收货人电话
		tv_buyer_address = (TextView) findViewById(R.id.tv_buyer_address);//收货地址
		tv_ordergoodsnumber_or = (TextView) findViewById(R.id.tv_ordergoodsnumber_or);//共XX件商品
		tv_orderprice_or = (TextView) findViewById(R.id.tv_orderprice_or);//实付XX元
		tv_shipping_fee = (TextView) findViewById(R.id.tv_shipping_fee);//运费
		tv_storename_or = (TextView) findViewById(R.id.tv_storename_or);
		tv_payment_name = (TextView) findViewById(R.id.tv_payment_name);//付款方式
		tv_liuyan = (TextView) findViewById(R.id.tv_liuyan);//卖家留言
		tv_order_sn = (TextView) findViewById(R.id.tv_order_sn);//订单编号
		btn_deletetheorder = (Button) findViewById(R.id.btn_deletetheorder_or);
		btn_paytheorder = (Button) findViewById(R.id.btn_paytheorder_or);
		btn_delete_this = (Button) findViewById(R.id.btn_delete_this_or);
		btn_tocomment = (Button) findViewById(R.id.btn_tocomment_or);
		btn_tuihuanhuo = (Button) findViewById(R.id.btn_tuihuanhuo_or);
		btn_querenshouhuo = (Button) findViewById(R.id.btn_querenshouhuo_or);
		listview_goodslist_or = (ListView) findViewById(R.id.listview_goodslist_or);
		
//		订单状态（待付款:10,待发货:20,待收货:30,已收货:40）
        switch (status) {
			case DAIFUKUAN:
				 btn_paytheorder.setVisibility(View.VISIBLE);
				 btn_deletetheorder.setVisibility(View.VISIBLE);
				 btn_delete_this.setVisibility(View.GONE);
				 btn_tocomment.setVisibility(View.GONE);
				 btn_tuihuanhuo.setVisibility(View.GONE);
				 btn_querenshouhuo.setVisibility(View.GONE);
				break;
			case DAIFAHUO:
				btn_deletetheorder.setVisibility(View.VISIBLE);
				btn_paytheorder.setVisibility(View.GONE);
				btn_delete_this.setVisibility(View.GONE);
				btn_tocomment.setVisibility(View.GONE);
				btn_tuihuanhuo.setVisibility(View.GONE);
				btn_querenshouhuo.setVisibility(View.GONE);
				break;
			case DAISHOUHUO:
				btn_tuihuanhuo.setVisibility(View.VISIBLE);
				btn_querenshouhuo.setVisibility(View.VISIBLE);
				btn_deletetheorder.setVisibility(View.GONE);
				btn_paytheorder.setVisibility(View.GONE);
				btn_delete_this.setVisibility(View.GONE);
				btn_tocomment.setVisibility(View.GONE);
				break;
			case YISHOUHUO:
				btn_tocomment.setVisibility(View.VISIBLE);//待评价根据服务器取出的数据进行判断，判断订单是否已经被评价过
				btn_delete_this.setVisibility(View.VISIBLE);
				btn_tuihuanhuo.setVisibility(View.VISIBLE);
				btn_querenshouhuo.setVisibility(View.GONE);
				btn_deletetheorder.setVisibility(View.GONE);
				btn_paytheorder.setVisibility(View.GONE);
				break;
	
			default:
				break;
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.order_back:
			this.finish();
			break;
		case R.id.btn_deletetheorder_or://取消订单
			RequestDailog.showDialog(this, "正在取消订单");
			HttpUtils.cancelOrder(res_cancelOrder, key, order_id);
			break;
		case R.id.btn_paytheorder_or://付款
			UIDialog.ForThreeBtn(this, new String[] { "微信", "支付宝","取消" }, new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					switch (v.getId()) {
					case R.id.dialog_modif_1:// 微信
						UIDialog.closeDialog();
						Log.e("key=", key);
						Log.e("pay_sn=", pay_sn);
						Log.e("CHANNEL_WECHAT=", CHANNEL_WECHAT);
						HttpUtils.payTheOrder(res_payTheOrder, key, pay_sn, CHANNEL_WECHAT);
						break;
					case R.id.dialog_modif_2:// 支付宝
						UIDialog.closeDialog();
						HttpUtils.payTheOrder(res_payTheOrder, key, pay_sn, CHANNEL_ALIPAY);
						break;
					case R.id.dialog_modif_3:// 取消
						UIDialog.closeDialog();
						break;

					default:
						break;
					}
				}
			});
			break;

		default:
			break;
		}
		super.onClick(v);
	}
	/**
	 * 订单详情
	 */
	JsonHttpResponseHandler res_getOrderDetail = new JsonHttpResponseHandler()
	{
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("订单详情="+response);
			String error=null;
			JSONObject datas=null;
			JSONObject order_info=null;
			JSONObject extend_order_common=null;
			JSONObject reciver_info=null;
			try {
				 datas =response.getJSONObject("datas");
				 error = response.getString("error");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (error==null)//成功
			{
				try {
					order_info = datas.getJSONObject("order_info");
					extend_order_common = order_info.getJSONObject("extend_order_common");
					reciver_info = extend_order_common.getJSONObject("reciver_info");
	//				Tools.Notic(c, "取消成功,请刷新该页面查看剩余订单", null);
					tv_buyer_name.setText(extend_order_common.getString("reciver_name"));
					tv_buyer_number.setText(reciver_info.getString("mob_phone"));
					tv_buyer_address.setText("收货地址:"+reciver_info.getString("address"));
					tv_ordergoodsnumber_or.setText("共"+order_info.getString("goods_count")+"件商品");
					tv_orderprice_or.setText("实付:￥"+order_info.getString("order_amount")+"元");
					tv_shipping_fee.setText("￥"+order_info.getString("shipping_fee"));//运费
					tv_payment_name.setText(order_info.getString("payment_name"));//付款方式
					tv_storename_or.setText(order_info.getString("store_name"));//店铺名称
					tv_order_sn.setText(order_info.getString("order_sn"));//订单编号
					if (extend_order_common.getString("order_message").equals("null")) {
						tv_liuyan.setVisibility(View.GONE);
					}else {
						tv_liuyan.setText(extend_order_common.getString("order_message"));//留言
					}
					adapter = new B5_5_OrderDetailAdapter(B5_5_OrderDetail.this, order_info.getJSONArray("goods_list"));
					listview_goodslist_or.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error);
				Tools.Notic(B5_5_OrderDetail.this, "取消失败,请重试", null);
//				Tools.Notic(B5_MyActivity.this, error+"", null);
			}
			
		}
	};
	/**
	 * 删除订单
	 */
	JsonHttpResponseHandler res_cancelOrder = new JsonHttpResponseHandler()
	{
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("删除订单="+response);
			String error=null;
			JSONObject datas=null;
			try {
				datas =response.getJSONObject("datas");
				error = response.getString("error");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (error==null)//成功
			{
				Tools.Notic(B5_5_OrderDetail.this, "订单已取消",new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						B5_5_OrderDetail.this.finish();
					}
				});
			}
			else//失败 
			{
				Tools.Notic(B5_5_OrderDetail.this, "取消失败,请重试", null);
//				Tools.Notic(B5_MyActivity.this, error+"", null);
			}
			
		}
	};
	
	/**
	 * 付款
	 */
	JsonHttpResponseHandler res_payTheOrder = new JsonHttpResponseHandler()
	{

		@Override
		public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Log.e("付款", response+"");
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
				Intent intent = new Intent();
	            String packageName = getPackageName();
	            ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
	            intent.setComponent(componentName);
	            intent.putExtra(PaymentActivity.EXTRA_CHARGE, response.toString());
	            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error+"");
//				Tools.Notic(B5_MyActivity.this, error+"", null);
			}
			
		}
		
		
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
//                Tools.Log("支付结果="+result);
//                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
//                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
//                showMsg(result, errorMsg, extraMsg);
                if (result.equals("success")) {
					Tools.Notic(this, "您已经付款成功", new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent intent_toMy = new Intent(B5_5_OrderDetail.this,B5_MyActivity.class);
							startActivity(intent_toMy);
							finish();
						}
					});
				}else if (result.equals("fail")) {
					Tools.Notic(this, "支付失败，请重试", null);
				}else if (result.equals("cancel")) {
					Tools.Notic(this, "支付取消", null);
				}else if (result.equals("invalid")) {
					Tools.Notic(this, "支付失败，请重新支付", null);
					
				}
            } else if (resultCode == Activity.RESULT_CANCELED) {
            	Tools.Notic(this, "支付取消", null);
            }
        }
    }
	
}
