package com.ZYKJ.buerhaitao.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.gui.CountryPage;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.ImageOptions;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * @author csh
 * 首页分类
 */
public class CityListActivity extends BaseActivity{
	
	private List<String> list = new ArrayList<String>();  
	private List<String> listTag = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_b2_classifyactivity);
		 
	    String city_name_list[] = CityListActivity.this.getResources()  
	            .getStringArray(R.array.city_description_list);  
	    String city_list_tag[] = CityListActivity.this.getResources()  
	            .getStringArray(R.array.city_group_list);  
	    // ///////////////////////////  
	    String cityTag[] = { "热门", "A", "B", "C", "D", "E", "F", "G", "H", "J",  
	            "K", "L", "M", "N", "Q", "S", "T", "W", "X", "Y", "Z" };  
	    int listsize[] = { 0, 19, 5, 6, 9, 7, 1, 3, 6, 13, 13, 5, 8, 5, 7, 7,  
	            10, 6, 11, 7, 11, 9 };  
	  
	    for (int j = 1; j < listsize.length; j++) {  
	        list.add(cityTag[j - 1]);  
	        listTag.add(cityTag[j - 1]);  
	        listsize[j] = listsize[j - 1] + listsize[j];  
	        for (int i = listsize[j - 1]; i < listsize[j]; i++) {  
	            list.add(city_name_list[i]);  
	            // System.out.println(city_list_tag[i]);  
	            //<span style="white-space:pre"></span>//city_group.add(city_list_tag[i]);
	        }
	    }
	}
}