package com.ZYKJ.buerhaitao.UI;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 兑换记录
 * @author zyk
 *
 */
public class B5_11_2_ExchangeRecord extends BaseActivity {
	ImageButton record_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_b5_11_2_exchangerecord);
		record_back=(ImageButton) findViewById(R.id.record_back);
		setListener(record_back);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.record_back://返回
			this.finish();
			break;
		default:
			break;
		}
	}
	JsonHttpResponseHandler res_addPointsOrder = new JsonHttpResponseHandler()
	{
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("res_addPointsOrder="+response);
			Toast.makeText(B5_11_2_ExchangeRecord.this, response+"", Toast.LENGTH_LONG).show();
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
				Toast.makeText(B5_11_2_ExchangeRecord.this, "兑换成功", Toast.LENGTH_LONG).show();
				B5_11_2_ExchangeRecord.this.finish();
//				Tools.Log("datas="+datas);
/*				try {
					Tools.Notic(B5_11_1_ExchangeDetail.this, "datas="+datas.getString("error"), null);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
			else//失败 
			{
//				Tools.Log("res_Points_error="+error+"");
				Tools.Notic(B5_11_2_ExchangeRecord.this, "error="+error, null);
			}
			
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				Throwable throwable, JSONObject errorResponse) {
			// TODO Auto-generated method stub
			Tools.Notic(B5_11_2_ExchangeRecord.this, "网络连接失败，请重试", null);
			super.onFailure(statusCode, headers, throwable, errorResponse);
		}
	
	};
}
