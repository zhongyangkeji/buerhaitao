package com.ZYKJ.buerhaitao.UI;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.loopj.android.http.JsonHttpResponseHandler;
/**
 * 增加收货地址
 * @author zyk
 *
 */
public class B5_9_1_addAddress extends BaseActivity {

	private ImageButton address_back;
	private EditText et_name,et_phone,et_postcode,et_addressDetail;
	private TextView tv_choseOrigen,tv_yesorchange,tv_delate;
	private Button btn_setDefaultAddress;
	private String typeString;//通过typeString来判断是修改还是添加地址 change   add
	
	private int GetArea=1;
	

	String province,city,area;
	String city_id ,area_id,area_info;
	
//	zip 邮编
	String true_name,address,zip,mob_phone;
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Intent intent =getIntent();
//		typeString=intent.getStringExtra("changeORadd");
//		if (typeString.equals("change")) {
//			Toast.makeText(this, "此时获取传递过来的地址信息并显示", Toast.LENGTH_LONG).show();
//		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_b5_9_1_addaddress);
		
		address_back=(ImageButton) findViewById(R.id.address_back);
		et_name=(EditText) findViewById(R.id.et_name);
		et_phone=(EditText) findViewById(R.id.et_phone);
		et_postcode=(EditText) findViewById(R.id.et_postcode);
		et_addressDetail=(EditText) findViewById(R.id.et_addressDetail);
		tv_choseOrigen=(TextView) findViewById(R.id.tv_choseOrigen);
		tv_yesorchange=(TextView) findViewById(R.id.tv_yesorchange);
		tv_delate=(TextView) findViewById(R.id.tv_delate);
		btn_setDefaultAddress=(Button) findViewById(R.id.btn_setDefaultAddress);
		
		setListener(address_back,tv_yesorchange,tv_choseOrigen,tv_delate,btn_setDefaultAddress);
		
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.address_back://返回键
			this.finish();
			break;
		case R.id.tv_yesorchange://确定（修改）
			true_name=et_name.getText().toString();
			mob_phone=et_phone.getText().toString();
			zip=et_postcode.getText().toString();
			address=et_addressDetail.getText().toString();
			RequestDailog.showDialog(this, "正在保存地址");
			HttpUtils.addAddress(res_addAddress, getSharedPreferenceValue("key"), true_name, city_id, area_id, area_info, "", address, zip, mob_phone);
			break;
		case R.id.tv_choseOrigen://选择地区
//			HttpUtils.getArea(res_getArea,getSharedPreferenceValue("key"),"");
			//弹出地址选择页面
			Intent intent_to_chose =new Intent(this,B5_9_1_getArea.class);
			startActivityForResult(intent_to_chose, GetArea);
			break;
		case R.id.tv_delate://删除收货地址
			this.finish();
			break;
		case R.id.btn_setDefaultAddress://设为默认收货地址
			this.finish();
			break;

		default:
			break;
		}
	}
	

	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode==GetArea) {
			province=data.getStringExtra("province");
			city=data.getStringExtra("city");
			area=data.getStringExtra("area");
			area_info=province+city+area;
			city_id=data.getStringExtra("city_id");
			area_id=data.getStringExtra("area_id");
			tv_choseOrigen.setText(province+" "+city+" "+area);
		}
		
	};
	
	JsonHttpResponseHandler res_addAddress = new JsonHttpResponseHandler()
	{
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			Tools.Log("res_addAddress_response="+response);
			RequestDailog.closeDialog();
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
				B5_9_1_addAddress.this.finish();
				
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error+"");
			}
		}
		
		
	};
}
