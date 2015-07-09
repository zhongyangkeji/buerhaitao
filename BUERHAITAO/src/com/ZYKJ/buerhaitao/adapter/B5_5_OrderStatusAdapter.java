package com.ZYKJ.buerhaitao.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.UI.B5_13_MyPurse;
import com.ZYKJ.buerhaitao.UI.B5_MyActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.ZYKJ.buerhaitao.view.UIDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.pingplusplus.android.PaymentActivity;

public class B5_5_OrderStatusAdapter extends BaseAdapter {
	
	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private Activity c;
	private int status=0;
	String key;
//  order_state 订单状态（待付款:10,待发货:20,待收货:30,已收货:40）
    private static final int DAIFUKUAN  = 10;
    private static final int DAIFAHUO   = 20;
    private static final int DAISHOUHUO = 30;
    private static final int YISHOUHUO  = 40;
	
	String price,pay_sn;                                     //订单金额
    private static final String CHANNEL_WECHAT = "wx";//通过微信支付
    private static final String CHANNEL_ALIPAY = "alipay";//通过支付宝支付
    private static final int REQUEST_CODE_PAYMENT = 1;
    
    B5_5_OrderStatuslistviewAdapter adapter;
    

	public B5_5_OrderStatusAdapter(Activity c, List<Map<String, Object>> data,int status,String key) {
		this.c = c;
		this.data = data;
		this.status = status;
		this.key = key;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data == null ? 0 : data.size();
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

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(c);
            convertView = mInflater.inflate(R.layout.ui_b5_5_orderlist_list_items, null);

            viewHolder.tv_storename = (TextView) convertView.findViewById(R.id.tv_storename);
            viewHolder.tv_orderprice = (TextView) convertView.findViewById(R.id.tv_orderprice);
            viewHolder.tv_ordergoodsnumber = (TextView) convertView.findViewById(R.id.tv_ordergoodsnumber);
            viewHolder.tv_orderstatus = (TextView) convertView.findViewById(R.id.tv_orderstatus);
            viewHolder.btn_deletetheorder = (Button) convertView.findViewById(R.id.btn_deletetheorder);
            viewHolder.btn_paytheorder = (Button) convertView.findViewById(R.id.btn_paytheorder);
            viewHolder.btn_delete_this = (Button) convertView.findViewById(R.id.btn_delete_this);
            viewHolder.btn_tocomment = (Button) convertView.findViewById(R.id.btn_tocomment);
            viewHolder.btn_tuihuanhuo = (Button) convertView.findViewById(R.id.btn_tuihuanhuo);
            viewHolder.btn_querenshouhuo = (Button) convertView.findViewById(R.id.btn_querenshouhuo);
            
            viewHolder.listView = (ListView) convertView.findViewById(R.id.listview_goodslist);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//      订单状态（待付款:10,待发货:20,待收货:30,已收货:40）
        switch (status) {
			case DAIFUKUAN:
				 viewHolder.tv_orderstatus.setText("待付款");
				 viewHolder.btn_paytheorder.setVisibility(View.VISIBLE);
				 viewHolder.btn_deletetheorder.setVisibility(View.VISIBLE);
				 viewHolder.btn_delete_this.setVisibility(View.GONE);
				 viewHolder.btn_tocomment.setVisibility(View.GONE);
				 viewHolder.btn_tuihuanhuo.setVisibility(View.GONE);
				 viewHolder.btn_querenshouhuo.setVisibility(View.GONE);
				 
				break;
			case DAIFAHUO:
				viewHolder.tv_orderstatus.setText("买家已付款");
				
				viewHolder.btn_deletetheorder.setVisibility(View.VISIBLE);
				viewHolder.btn_paytheorder.setVisibility(View.GONE);
				viewHolder.btn_delete_this.setVisibility(View.GONE);
				viewHolder.btn_tocomment.setVisibility(View.GONE);
				viewHolder.btn_tuihuanhuo.setVisibility(View.GONE);
				viewHolder.btn_querenshouhuo.setVisibility(View.GONE);
				break;
			case DAISHOUHUO:
				viewHolder.tv_orderstatus.setText("卖家已发货");
				viewHolder.btn_tuihuanhuo.setVisibility(View.VISIBLE);
				viewHolder.btn_querenshouhuo.setVisibility(View.VISIBLE);
				viewHolder.btn_deletetheorder.setVisibility(View.GONE);
				viewHolder.btn_paytheorder.setVisibility(View.GONE);
				viewHolder.btn_delete_this.setVisibility(View.GONE);
				viewHolder.btn_tocomment.setVisibility(View.GONE);
				break;
			case YISHOUHUO:
				viewHolder.tv_orderstatus.setText("买家已收货");
				
				viewHolder.btn_tocomment.setVisibility(View.VISIBLE);//待评价根据服务器取出的数据进行判断，判断订单是否已经被评价过
				viewHolder.btn_delete_this.setVisibility(View.VISIBLE);
				viewHolder.btn_tuihuanhuo.setVisibility(View.VISIBLE);
				viewHolder.btn_querenshouhuo.setVisibility(View.GONE);
				viewHolder.btn_deletetheorder.setVisibility(View.GONE);
				viewHolder.btn_paytheorder.setVisibility(View.GONE);
				
				break;
	
			default:
				break;
		}
        
        pay_sn = data.get(position).get("pay_sn").toString();
        price = data.get(position).get("order_amount").toString();
        viewHolder.tv_storename.setText(data.get(position).get("store_name").toString());//店铺名
        JSONArray extend_order_goods = (JSONArray) ((Map) data.get(position)).get("extend_order_goods");
        viewHolder.tv_ordergoodsnumber.setText("共有"+extend_order_goods.length()+"件商品");//订单中商品的数量
        viewHolder.tv_orderprice.setText("实付:￥"+price);
        adapter = new B5_5_OrderStatuslistviewAdapter(c,extend_order_goods);
        viewHolder.listView.setAdapter(adapter);
        
        viewHolder.btn_deletetheorder.setOnClickListener(new DeletetheorderListener(position,data.get(position).get("order_id").toString()));
        viewHolder.btn_paytheorder.setOnClickListener(new PaytheorderListener(position,pay_sn));
		return convertView;
	}
	/**
	 * 取消订单
	 * @author zyk
	 *
	 */
	class DeletetheorderListener implements View.OnClickListener {
		int position;
	    String orderidString;
		public DeletetheorderListener(int position,String orderidString) {
			this.position = position;
			this.orderidString = orderidString;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			Tools.Log("key="+key);
			RequestDailog.showDialog(c, "正在取订单，请稍后");
//			Log.e("key", key+"");
			Log.e("orderidString", orderidString);
			HttpUtils.cancelOrder(res_cancelOrder, key, orderidString);
//			Tools.Notic(c, "是否取消该订单？", new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//				}
//			});
		}

	}
	/**
	 * 付款
	 * @author zyk
	 */
	class PaytheorderListener implements View.OnClickListener {
		int position;
		String pay_sn;
		public PaytheorderListener(int position,String pay_sn) {
			this.position = position;
			this.pay_sn = pay_sn;
		}
		@Override
		public void onClick(View v) {
			UIDialog.ForThreeBtn(c, new String[] { "微信", "支付宝","取消" }, new OnClickListener() {
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
		}
		
	}

	private static class ViewHolder
    {
         
        TextView tv_storename;
        TextView tv_orderprice;
        TextView tv_ordergoodsnumber;
        TextView tv_orderstatus;
        Button btn_deletetheorder;//取消订单
        Button btn_paytheorder;//付款
        Button btn_delete_this;//删除订单
        Button btn_tocomment;//待评价
        Button btn_tuihuanhuo;//退换货
        Button btn_querenshouhuo;//确认收货
        ListView listView;
    }
	
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
			
			Tools.Log("取消订单="+response);
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
				Tools.Notic(c, "取消成功,请刷新该页面查看剩余订单", null);
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error);
				Tools.Notic(c, "取消失败,请重试", null);
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
	            String packageName = c.getPackageName();
	            ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
	            intent.setComponent(componentName);
	            intent.putExtra(PaymentActivity.EXTRA_CHARGE, response.toString());
	            c.startActivityForResult(intent, REQUEST_CODE_PAYMENT);
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
					Tools.Notic(c, "您已经付款成功", new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent intent_toMy = new Intent(c,B5_MyActivity.class);
							c.startActivity(intent_toMy);
							c.finish();
						}
					});
				}else if (result.equals("fail")) {
					Tools.Notic(c, "支付失败，请重试", null);
				}else if (result.equals("cancel")) {
					Tools.Notic(c, "支付取消", null);
				}else if (result.equals("invalid")) {
					Tools.Notic(c, "支付失败，请重新支付", null);
					
				}
            } else if (resultCode == Activity.RESULT_CANCELED) {
            	Tools.Notic(c, "支付取消", null);
            }
        }
    }
}
