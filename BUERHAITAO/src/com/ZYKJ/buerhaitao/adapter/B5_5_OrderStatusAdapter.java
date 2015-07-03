package com.ZYKJ.buerhaitao.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.loopj.android.http.JsonHttpResponseHandler;

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
        viewHolder.tv_storename.setText(data.get(position).get("store_name").toString());//店铺名
        JSONArray extend_order_goods = (JSONArray) ((Map) data.get(position)).get("extend_order_goods");
        viewHolder.tv_ordergoodsnumber.setText("共有"+extend_order_goods.length()+"件商品");//订单中商品的数量
        viewHolder.tv_orderprice.setText("实付:￥"+data.get(position).get("order_amount").toString());
        adapter = new B5_5_OrderStatuslistviewAdapter(c,extend_order_goods);
        viewHolder.listView.setAdapter(adapter);
        
        viewHolder.btn_deletetheorder.setOnClickListener(new DeletetheorderListener(position,data.get(position).get("order_id").toString()));
        
		return convertView;
	}
	/**
	 * 点击立即兑换按钮之后的跳转
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
			Tools.Log("key="+key);
			RequestDailog.showDialog(c, "正在取订单，请稍后");
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
				Tools.Notic(c, "取消成功,请刷新该页面查看剩余订单", new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
					}
				});
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error);
				Tools.Notic(c, "取消失败,请重试", null);
//				Tools.Notic(B5_MyActivity.this, error+"", null);
			}
			
		}
	};
	

}
