package com.ZYKJ.buerhaitao.UI;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.view.UIDialog;
/**
 * 我的钱包 页
 * @author zyk
 *
 */
public class B5_13_MyPurse extends BaseActivity {
	/**
	 *返回
	 */
	ImageButton mypurse_back;
	/**
	 * 钱包余额
	 */
	TextView tv_remaining;
	/**
	 *输入的 充值金额
	 */
	EditText et_chagenumber;
	/**
	 * 立即充值 按钮
	 */
	Button btn_recharge;
	String recharge_numberString;//充值金额
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.b5_13_mypurse);
		mypurse_back = (ImageButton) findViewById(R.id.mypurse_back);
		tv_remaining = (TextView) findViewById(R.id.tv_remaining);
		et_chagenumber = (EditText) findViewById(R.id.et_chagenumber);
		et_chagenumber.setInputType(InputType.TYPE_CLASS_NUMBER);
		btn_recharge = (Button) findViewById(R.id.btn_recharge);
		setListener(mypurse_back,btn_recharge);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.mypurse_back://返回
			this.finish();
		break;
		case R.id.btn_recharge://立即充值
			recharge_numberString = et_chagenumber.getText().toString().trim();
			if (recharge_numberString.equals("")) {
				Toast.makeText(this, "请输入充值金额", 500).show();
			}else {//跳出来支付方式选择
				UIDialog.ForThreeBtn(this, new String[] { "微信", "支付宝","取消" }, this);
			}
		break;
		case R.id.dialog_modif_1:// 微信
			UIDialog.closeDialog();
			
			break;
		case R.id.dialog_modif_2:// 支付宝
			UIDialog.closeDialog();
			
			break;
		case R.id.dialog_modif_3:// 取消
			UIDialog.closeDialog();
			break;

		default:
			break;
		}
	}
}
