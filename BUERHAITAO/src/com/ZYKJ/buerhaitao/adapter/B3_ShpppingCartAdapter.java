package com.ZYKJ.buerhaitao.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.data.ChildrenItem;
import com.ZYKJ.buerhaitao.data.GroupItem;
import com.ZYKJ.buerhaitao.data.HttpAction;
import com.nostra13.universalimageloader.core.ImageLoader;

public class B3_ShpppingCartAdapter extends BaseExpandableListAdapter {
	private List<GroupItem> dataList;
	private LayoutInflater inflater;
	// 以选中的子列表项
	private List<String> checkedChildren = new ArrayList<String>();
	// 父列表项的选中状态：value值为1（选中）、2（部分选中）、3（未选中）
	private Map<String, Integer> groupCheckedStateMap = new HashMap<String, Integer>();
	int num=0;//数量
	//默认layout，编辑layout
	LinearLayout ll_b3shopmoren,ll_b3shopedit;
	//单个商品数量
	EditText editconunt;
	//加减数量
	Button jiabt,jianbt;
	int bianjistate = 0;
	TextView tv_bianji;
	Map<Integer, Boolean> listcarld;

	public B3_ShpppingCartAdapter(Context context, List<GroupItem> dataList) {
		this.dataList = dataList;
		inflater = LayoutInflater.from(context);	
		listcarld = new HashMap<Integer, Boolean>();
		// 默认设置所有的父列表项和子列表项都为选中状态
		int groupCount = getGroupCount();
		for (int groupPosition = 0; groupPosition < groupCount; groupPosition++) {
			try {
				GroupItem groupItem = dataList.get(groupPosition);
				if (groupItem == null || groupItem.getStore_list() == null
						|| groupItem.getStore_list().isEmpty()) {
					groupCheckedStateMap.put(groupItem.getStore_id(), 3);
					continue;
				}	
				groupCheckedStateMap.put(groupItem.getStore_id(), 1);
				List<ChildrenItem> childrenItems = groupItem.getStore_list();
				for (ChildrenItem childrenItem : childrenItems) {
					checkedChildren.add(childrenItem.getCart_id());			
				}
				listcarld.put(groupPosition,false);

			} catch (Exception e) {

			}
		}
		
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		final GroupItem groupItem = dataList.get(groupPosition);
		if (groupItem == null || groupItem.getStore_list() == null
				|| groupItem.getStore_list().isEmpty()) {
			return null;
		}
		return groupItem.getStore_list().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, int childPosition,boolean isLastChild, View convertView, final ViewGroup parent) {
		ChildrenItem childrenItem = (ChildrenItem) getChild(groupPosition,
				childPosition);

		ChildViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.ui_b3_shoppingcartitem,null);
			viewHolder.childrenNameTV = (TextView) convertView.findViewById(R.id.children_name);
			viewHolder.childrenCB = (CheckBox) convertView.findViewById(R.id.children_cb);
			viewHolder.im_shangpuimg = (ImageView) convertView.findViewById(R.id.im_shangpuimg);
			viewHolder.tv_spec = (TextView)convertView.findViewById(R.id.tv_spec);
			viewHolder.tv_goods_price = (TextView)convertView.findViewById(R.id.tv_goods_price);
			viewHolder.tv_goods_num = (TextView)convertView.findViewById(R.id.tv_goods_num);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ChildViewHolder) convertView.getTag();
		}
		editconunt = (EditText) convertView.findViewById(R.id.editconunt);
		jiabt = (Button)convertView.findViewById(R.id.jiabt);
		jianbt = (Button)convertView.findViewById(R.id.jianbt);
		ll_b3shopmoren = (LinearLayout)convertView.findViewById(R.id.ll_b3shopmoren);
		ll_b3shopedit = (LinearLayout)convertView.findViewById(R.id.ll_b3shopedit);

		viewHolder.childrenNameTV.setText(childrenItem.getStore_name());
		ImageLoader.getInstance().displayImage(childrenItem.getGoods_image_url(), viewHolder.im_shangpuimg);
		viewHolder.tv_spec.setText(childrenItem.getGoods_spec());
		viewHolder.tv_goods_price.setText(childrenItem.getGoods_price());
		viewHolder.tv_goods_num.setText("x"+childrenItem.getGoods_num());
		final String childrenId = childrenItem.getCart_id();
		if (listcarld.get(groupPosition)==true) {
			ll_b3shopmoren.setVisibility(View.GONE);
			ll_b3shopedit.setVisibility(View.VISIBLE);
		}else {
			ll_b3shopmoren.setVisibility(View.VISIBLE);
			ll_b3shopedit.setVisibility(View.GONE);
		}
		
		viewHolder.childrenCB
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							if (!checkedChildren.contains(childrenId)) {
								checkedChildren.add(childrenId);
							}
						} else {
							checkedChildren.remove(childrenId);
						}

						setGroupItemCheckedState(dataList.get(groupPosition));

						B3_ShpppingCartAdapter.this.notifyDataSetChanged();
					}
				});

		if (checkedChildren.contains(childrenId)) {
			viewHolder.childrenCB.setChecked(true);
		} else {
			viewHolder.childrenCB.setChecked(false);
		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		final GroupItem groupItem = dataList.get(groupPosition);
		if (groupItem == null || groupItem.getStore_list() == null
				|| groupItem.getStore_list().isEmpty()) {
			return 0;
		}
		return groupItem.getStore_list().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		if (dataList == null) {
			return null;
		}
		return dataList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		if (dataList == null) {
			return 0;
		}
		return dataList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		try {
			final GroupItem groupItem = dataList.get(groupPosition);

			GroupViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new GroupViewHolder();
				convertView = inflater.inflate(R.layout.group_item, null);
				viewHolder.groupNameTV = (TextView) convertView.findViewById(R.id.group_name);
				viewHolder.groupCBImg = (ImageView) convertView.findViewById(R.id.group_cb_img);
				viewHolder.groupCBLayout = (LinearLayout) convertView.findViewById(R.id.cb_layout);
				convertView.setClickable(true);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (GroupViewHolder) convertView.getTag();
			}
			
			tv_bianji = (TextView)convertView.findViewById(R.id.tv_bianji);
			tv_bianji.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (bianjistate>0) {
						bianjistate=0;
						listcarld.put(groupPosition, false);
						tv_bianji.setText("编辑");
						B3_ShpppingCartAdapter.this.notifyDataSetChanged();
						
//						ll_b3shopmoren.setVisibility(View.VISIBLE);
//						ll_b3shopedit.setVisibility(View.GONE);
					}else {
						bianjistate=1;
						listcarld.put(groupPosition, true);
						tv_bianji.setText("完成");
						B3_ShpppingCartAdapter.this.notifyDataSetChanged();
//						ll_b3shopmoren.setVisibility(View.GONE);
//						ll_b3shopedit.setVisibility(View.VISIBLE);
					}
					
				}
			});

			viewHolder.groupCBLayout.setOnClickListener(new GroupCBLayoutOnClickListener(groupItem));
			viewHolder.groupNameTV.setText(groupItem.getStore_name());
			int state = groupCheckedStateMap.get(groupItem.getStore_id());
			switch (state) {
			case 1:
				viewHolder.groupCBImg.setImageResource(R.drawable.ck_checked);
				break;
			case 2:
//				viewHolder.groupCBImg.setImageResource(R.drawable.ck_partial_checked);
				viewHolder.groupCBImg.setImageResource(R.drawable.ck_unchecked);
				break;
			case 3:
				viewHolder.groupCBImg.setImageResource(R.drawable.ck_unchecked);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	private void setGroupItemCheckedState(GroupItem groupItem) {
		List<ChildrenItem> childrenItems = groupItem.getStore_list();
		if (childrenItems == null || childrenItems.isEmpty()) {
			groupCheckedStateMap.put(groupItem.getStore_id(), 3);
			return;
		}

		int checkedCount = 0;
		for (ChildrenItem childrenItem : childrenItems) {
			if (checkedChildren.contains(childrenItem.getCart_id())) {
				checkedCount++;
			}
		}
		int state = 1;
		if (checkedCount == 0) {
			state = 3;
		} else if (checkedCount == childrenItems.size()) {
			state = 1;
		} else {
			state = 2;
		}

		groupCheckedStateMap.put(groupItem.getStore_id(), state);
	}

	final static class GroupViewHolder {
		TextView groupNameTV;
		ImageView groupCBImg;
		LinearLayout groupCBLayout;
	}

	final static class ChildViewHolder {
		TextView childrenNameTV;
		CheckBox childrenCB;
		ImageView im_shangpuimg; 
		TextView tv_spec;
		TextView tv_goods_price;
		TextView tv_goods_num;
	}
	
	//点击店铺的CheckBox点击 
	public class GroupCBLayoutOnClickListener implements OnClickListener {

		private GroupItem groupItem;

		public GroupCBLayoutOnClickListener(GroupItem groupItem) {
			this.groupItem = groupItem;
		}

		@Override
		public void onClick(View v) {
			List<ChildrenItem> childrenItems = groupItem.getStore_list();
			if (childrenItems == null || childrenItems.isEmpty()) {
				groupCheckedStateMap.put(groupItem.getStore_id(), 3);
				return;
			}
			int checkedCount = 0;
			for (ChildrenItem childrenItem : childrenItems) {
				if (checkedChildren.contains(childrenItem.getCart_id())) {
					checkedCount++;
				}
			}

			boolean checked = false;
			if (checkedCount == childrenItems.size()) {
				checked = false;
				groupCheckedStateMap.put(groupItem.getStore_id(), 3);
			} else {
				checked = true;
				groupCheckedStateMap.put(groupItem.getStore_id(), 1);
			}

			for (ChildrenItem childrenItem : childrenItems) {
				String holderKey = childrenItem.getCart_id();
				if (checked) {
					if (!checkedChildren.contains(holderKey)) {
						checkedChildren.add(holderKey);
					}
				} else {
					checkedChildren.remove(holderKey);
				}
			}

			B3_ShpppingCartAdapter.this.notifyDataSetChanged();
		}
	}

	public List<String> getCheckedRecords() {
		return checkedChildren;
	}

	public List<String> getCheckedChildren() {
		return checkedChildren;
	}	

}