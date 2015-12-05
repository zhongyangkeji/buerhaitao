package com.ZYKJ.buerhaitao.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CommonUtils {

	private static final double EARTH_RADIUS = 6378137;//地球半径
	/**
	* 动态设置ListView的高度
	* @param listView
	*/
	public static void setListViewHeightBasedOnChildren(ListView listView) { 
	    if(listView == null) return;

	    ListAdapter listAdapter = listView.getAdapter(); 
	    if (listAdapter == null) { 
	        // pre-condition 
	        return; 
	    } 

	    int totalHeight = 0; 
	    for (int i = 0; i < listAdapter.getCount(); i++) { 
	        View listItem = listAdapter.getView(i, null, listView); 
	        listItem.measure(0, 0); 
	        totalHeight += listItem.getMeasuredHeight(); 
	    } 

	    ViewGroup.LayoutParams params = listView.getLayoutParams(); 
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
	    listView.setLayoutParams(params); 
	}
	
	private static double rad(double d){
		return d * Math.PI / 180.0;
	}
	
	public static void showPic(String name,ImageView imageview){
		if(!StringUtil.isEmpty(name)){
			ImageLoader.getInstance().displayImage(name, imageview);
		}
	}
	
	//退出操作
	public static void exitkey(int keyCode, Context context) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回按钮
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("提示").setMessage("您确定退出当前应用").setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					try {
						// 判断是否存在临时创建的文件
						File temp_file = new File(Environment.getExternalStorageDirectory() + File.separator + "heyi_dir");
						Tools.Log("文件是否存在：" + temp_file.exists());
						if (temp_file.exists()) {
							File[] file_detail = temp_file.listFiles();
							for (File file_del : file_detail) {
								file_del.delete();
							}
							temp_file.delete();
						}
					} catch (Exception e) {
					}
					System.exit(0);
				}
			}).setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					dialog.dismiss();
				}

			}).show();
		}
	}
	
}
