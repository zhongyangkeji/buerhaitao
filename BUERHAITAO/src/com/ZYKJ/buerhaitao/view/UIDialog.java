package com.ZYKJ.buerhaitao.view;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.UI.R;

/**
 * 一些ui中Dialog中的使用
 * 
 * @author bin
 * 
 */
public class UIDialog {
	public static AlertDialog dialog;

	/** 3按键按钮dialog */
	public static void ForThreeBtn(Context context, String[] showtxt,
			OnClickListener lisener) {
		dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		Window window = dialog.getWindow();
		// *** 主要就是在这里实现这种效果的.
		// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		window.setContentView(R.layout.dialog_modif);

		Button m_btn_1 = (Button) window.findViewById(R.id.dialog_modif_1);
		Button m_btn_2 = (Button) window.findViewById(R.id.dialog_modif_2);
		Button m_btn_3 = (Button) window.findViewById(R.id.dialog_modif_3);

		m_btn_1.setText(showtxt[0]);
		m_btn_2.setText(showtxt[1]);
		m_btn_3.setText(showtxt[2]);

		m_btn_1.setOnClickListener(lisener);
		m_btn_2.setOnClickListener(lisener);
		m_btn_3.setOnClickListener(lisener);
	}

	/** notic提示dialog */
	public static void ForNotic(Context context, String text,
			OnClickListener lisener) {
		dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		Window window = dialog.getWindow();
		// *** 主要就是在这里实现这种效果的.
		// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		window.setContentView(R.layout.dialog_notic);

		Button m_btn_1 = (Button) window.findViewById(R.id.dialog_notic_1);
		if (lisener == null) {
			lisener = new OnClickListener() {
				public void onClick(View v) {
					UIDialog.closeDialog();
				}
			};
		}
		m_btn_1.setOnClickListener(lisener);
		// 设置内容
		TextView m_notic_content = (TextView) window
				.findViewById(R.id.dialog_notic_text);
		m_notic_content.setText(text);
	}
///**
// * YesOrNo 选择
// * 
// */
//	public static void YesOrNo(Context context,String text,
//			OnClickListener lisener) {
//		dialog = new AlertDialog.Builder(context).create();
//		dialog.show();
//		Window window = dialog.getWindow();
//		// *** 主要就是在这里实现这种效果的.
//		// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
//		window.setContentView(R.layout.dialog_yes_or_no);
////yes
//		Button btn_yes = (Button) window.findViewById(R.id.btn_yes);
//		if (lisener == null) {
//			lisener = new OnClickListener() {
//				public void onClick(View v) {
//					Intent intent_toYes=new Intent();
//					intent_toYes.setClass(context, targetContext.getClass());
//				}
//			};
//		}
//		btn_yes.setOnClickListener(lisener);
////no
//		Button btn_no = (Button) window.findViewById(R.id.btn_no);
//		if (lisener == null) {
//			lisener = new OnClickListener() {
//				public void onClick(View v) {
//					UIDialog.closeDialog();
//				}
//			};
//		}
//		btn_no.setOnClickListener(lisener);
//		
//		// 设置内容
//		TextView m_notic_content = (TextView) window
//				.findViewById(R.id.dialog_notic_text);
//		m_notic_content.setText(text);
//	}	
//	
	
	/**
	 * 关闭dialog
	 */
	public static void closeDialog() {
		if (dialog == null || !dialog.isShowing()) {
			return;
		}
		dialog.dismiss();

	}
}
