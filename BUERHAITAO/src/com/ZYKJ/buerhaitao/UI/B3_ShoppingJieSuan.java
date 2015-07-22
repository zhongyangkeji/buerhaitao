package com.ZYKJ.buerhaitao.UI;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.adapter.B3_JieSuanAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.data.GroupItem;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.ZYKJ.buerhaitao.view.UIDialog;
import com.loopj.android.http.JsonHttpResponseHandler;

public class B3_ShoppingJieSuan extends BaseActivity {
	// 购物车list
	private ExpandableListView expandableList;
	// 返回
	private ImageView im_jiesuan_back;
	// 购物车数据
	private B3_JieSuanAdapter adapter;
	private List<GroupItem> dataList = new ArrayList<GroupItem>();
	private int groupCount;
	private TextView tv_jiesuan;
	private RelativeLayout rl_zhifufangshi;
	private TextView tv_zffs;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b3_shoppingjiesuan);
		initView();
	}

	private void initView() {
		/*
		 * tv_sp_title = (TextView)findViewById(R.id.tv_sp_title);
		 */
		expandableList = (ExpandableListView) findViewById(R.id.list_shoppingcar1);
		expandableList.setGroupIndicator(null);
		im_jiesuan_back = (ImageView) findViewById(R.id.im_jiesuan_back);
		tv_jiesuan = (TextView) findViewById(R.id.tv_jiesuanqueren);
		rl_zhifufangshi = (RelativeLayout) findViewById(R.id.rl_zhifufangshi);
		tv_zffs = (TextView) findViewById(R.id.tv_zffs);
		// HttpUtils.getShoppingCarInfoList(res_ShoppingCarInfo,getSharedPreferenceValue("key"));
		HttpUtils.getShoppingCarInfoList(res_ShoppingCarInfo,
				"3ae653eb52824dbc4ba977de343e2e12");

		setListener(im_jiesuan_back, rl_zhifufangshi);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_jiesuan_back:
			B3_ShoppingJieSuan.this.finish();
			break;
		case R.id.rl_zhifufangshi:
			UIDialog.ForFiveBtn(this, new String[] { "钱包支付", "微信支付", "支付宝支付",
					"货到付款", "取消" }, this);
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
		default:
			break;
		}
	}

	/**
	 * 初始化Adapter数据
	 * 
	 * @Title:
	 * @Description:
	 * @return void 返回类型
	 * @author zhangxiaolei
	 * @throws
	 */
	private void initData() {
		adapter = new B3_JieSuanAdapter(this, dataList);
		expandableList.setAdapter(adapter);

		groupCount = expandableList.getCount();

		for (int i = 0; i < groupCount; i++) {

			expandableList.expandGroup(i);

		}
	}

	/**
	 * 获取购物车详细列表
	 */
	JsonHttpResponseHandler res_ShoppingCarInfo = new JsonHttpResponseHandler() {
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String error = null;
			JSONObject datas = null;
			Tools.Log("res_shaidanquanfabu=" + response);
			try {
				datas = response.getJSONObject("datas");
				error = response.getJSONObject("datas").getString("error");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (error == null)// 成功
			{
				try {
					JSONArray array = datas.getJSONArray("cart_list");
					dataList = com.alibaba.fastjson.JSONArray.parseArray(
							array.toString(), GroupItem.class);
					initData();

					adapter.notifyDataSetChanged();
				} catch (org.json.JSONException e) {
					e.printStackTrace();
				}
			} else// 失败
			{
				Tools.Log("res_Points_error=" + error + "");
			}
		};

	};

}