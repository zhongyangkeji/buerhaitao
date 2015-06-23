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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.ImageOptions;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * @author csh
 * 首页分类
 */
public class B2_ClassifyActivity extends BaseActivity implements OnEditorActionListener{
	
	private TextView cl_address,search_detail;
	private EditText search_input;
	private RadioGroup category_list;
	private GridView product_grid;
    private RadioGroup.LayoutParams mRadioParams;
    private List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_b2_classifyactivity);
		
		initView();
		requestData();
	}
	
	/**
	 * 初始化页面
	 */
	private void initView(){
		cl_address = (TextView)findViewById(R.id.classify_address);//地址选择
		search_input = (EditText)findViewById(R.id.search_input);//搜索输入框
		search_detail = (TextView)findViewById(R.id.search_detail);//宝贝
		category_list = (RadioGroup)findViewById(R.id.category_list);
		product_grid = (GridView)findViewById(R.id.product_grid);
        mRadioParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        cl_address.setOnClickListener(this);
        search_detail.setOnClickListener(this);
        search_input.setOnEditorActionListener(this);
	}
	
	/**
	 * 请求服务器数据----一级分类
	 */
	private void requestData(){
		HttpUtils.getGoodsClass(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				try {
					int code = response.getInt("code");
					if(code != 200){
						Toast.makeText(B2_ClassifyActivity.this, "请求失败", Toast.LENGTH_LONG).show();
						return;
					}
					JSONObject data = response.getJSONObject("datas");
					JSONArray jsonArray = data.getJSONArray("class_list");
	                for (int i = 0; i < jsonArray.length(); i++) {
	                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
	                    RadioButton radioButton = new RadioButton(B2_ClassifyActivity.this);
	                    radioButton.setId(Integer.valueOf(jsonObject.getString("gc_id")));
	                    radioButton.setText(jsonObject.getString("gc_name"));
	                    radioButton.setTextSize(18f);
	                    radioButton.setPadding(20, 35, 20, 35);
	                    radioButton.setTextColor(getResources().getColorStateList(R.drawable.classify_text));
	                    radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
	                    radioButton.setBackgroundResource(R.drawable.checked_classify);
	                    radioButton.setChecked(i==0?true:false);
	                    category_list.addView(radioButton,mRadioParams);
	                    if(i==0){
		                    getTwoCateByOne(jsonObject.getString("gc_id"));
	                    }
	                }
	                category_list.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
	                    @Override
	                    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
	                        //oneid = String.valueOf(checkedId);
	                        getTwoCateByOne(String.valueOf(checkedId));
	                    }
	                });
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		},null);
	}
	
	/**
	 * 请求服务器数据----二级分类
	 */
	private void getTwoCateByOne(String gc_id){
		HttpUtils.getGoodsClass(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				try {
					int code = response.getInt("code");
					list.clear();
					if(code != 200){
						Toast.makeText(B2_ClassifyActivity.this, "请求失败", Toast.LENGTH_LONG).show();
						return;
					}
					JSONObject data = response.getJSONObject("datas");
					JSONArray jsonArray = data.getJSONArray("class_list");
	                for (int i = 0; i < jsonArray.length(); i++) {
	                	HashMap<String,String> map = new HashMap<String,String>();
	                    JSONObject twoClassify = jsonArray.getJSONObject(i);
	                    map.put("gc_id", twoClassify.getString("gc_id"));
	                    map.put("gc_name", twoClassify.getString("gc_name"));
	                    map.put("image", twoClassify.getString("image"));
	                    list.add(map);
	                }
	                product_grid.setAdapter(new BaseAdapter() {
						
						@Override
						public int getCount() { return list.size(); }
						
						@Override
						public HashMap<String,String> getItem(int postion) { return list.get(postion); }
						
						@Override
						public long getItemId(int arg0) { return 0; }
						
						@Override
						public View getView(int postion, View convertView, ViewGroup arg2) {
							convertView = LayoutInflater.from(B2_ClassifyActivity.this).inflate(R.layout.ui_b2_item_classify, null);
							
	                        TextView name= (TextView) convertView.findViewById(R.id.classify_name);
	                        ImageView image= (ImageView) convertView.findViewById(R.id.classify_image);
	                        name.setText(list.get(postion).get("gc_name"));
	                        ImageOptions.displayImage2Circle(image, list.get(postion).get("image"), 10f);
	                        
							return convertView;
						}
					});
	                product_grid.setCacheColorHint(0);
	                
	                product_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
							String gc_id = list.get(position).get("gc_id");
							Toast.makeText(B2_ClassifyActivity.this, gc_id, Toast.LENGTH_LONG).show();
							Intent intent = new Intent(B2_ClassifyActivity.this,B2_ShopByClassifyActivity.class);
							intent.putExtra("gc_id", gc_id);
							startActivity(intent);
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		},gc_id);
	}

	@Override
	public void onClick(View view) {
        switch (view.getId()){
            case R.id.classify_address:
                break;
            case R.id.search_detail:
                break;
        }
	}

	@Override
	public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
		switch(actionId){
		case EditorInfo.IME_ACTION_SEARCH:
			Toast.makeText(B2_ClassifyActivity.this, "搜索", Toast.LENGTH_LONG).show();
		}
		return true;
	}
	
}