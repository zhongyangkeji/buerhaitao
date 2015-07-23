package com.ZYKJ.buerhaitao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.data.ChildrenItem;
import com.ZYKJ.buerhaitao.data.GroupItem;
import com.ZYKJ.buerhaitao.view.UIDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

public class B3_JieSuanAdapter extends BaseExpandableListAdapter {
	private List<GroupItem> dataList;
	private LayoutInflater inflater;
	Context context;
	TextView tv_psfs;

	public B3_JieSuanAdapter(Context context, List<GroupItem> dataList) {
		this.dataList = dataList;
		this.context = context;
		inflater = LayoutInflater.from(context);
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
	public View getChildView(final int groupPosition, int childPosition,
			boolean isLastChild, View convertView, final ViewGroup parent) {
		final ChildrenItem childrenItem = (ChildrenItem) getChild(
				groupPosition, childPosition);

		ChildViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.ui_b3_jiesuanitem, null);
			viewHolder.childrenNameTV = (TextView) convertView
					.findViewById(R.id.children_name1);
			viewHolder.im_shangpuimg = (ImageView) convertView
					.findViewById(R.id.im_shangpuimg1);
			viewHolder.tv_spec = (TextView) convertView
					.findViewById(R.id.tv_spec1);
			viewHolder.tv_goods_price = (TextView) convertView
					.findViewById(R.id.tv_goods_price1);
			viewHolder.tv_goods_num = (TextView) convertView
					.findViewById(R.id.tv_goods_num1);
			viewHolder.rl_peisongfangshi = (RelativeLayout) convertView
					.findViewById(R.id.rl_peisongfangshi);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ChildViewHolder) convertView.getTag();
		}

		viewHolder.childrenNameTV.setText(childrenItem.getStore_name());
		ImageLoader.getInstance().displayImage(
				childrenItem.getGoods_image_url(), viewHolder.im_shangpuimg);
		viewHolder.tv_spec.setText(childrenItem.getGoods_spec());
		viewHolder.tv_goods_price.setText(childrenItem.getGoods_price());
		viewHolder.tv_goods_num.setText("x" + childrenItem.getGoods_num());
		tv_psfs = (TextView) convertView.findViewById(R.id.tv_psfs);
		viewHolder.rl_peisongfangshi
				.setOnClickListener(new PeiSongFangShiOnClickListener());
		return convertView;
	}

	class PeiSongFangShiOnClickListener implements View.OnClickListener {
		public PeiSongFangShiOnClickListener() {

		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.rl_peisongfangshi:
				UIDialog.ForThreeBtn(context,new String[] { "物流配送", "自提", "取消" }, this);
				break;
			case R.id.dialog_modif_1:
				UIDialog.closeDialog();
				tv_psfs.setText("物流配送");

				break;
			case R.id.dialog_modif_2:
				UIDialog.closeDialog();
				tv_psfs.setText("自提");

				break;
			case R.id.dialog_modif_3:
				UIDialog.closeDialog();

				break;
			default:
				break;
			}
		}
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
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		try {
			final GroupItem groupItem = dataList.get(groupPosition);

			GroupViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new GroupViewHolder();
				convertView = inflater.inflate(R.layout.group_item1, null);
				viewHolder.groupNameTV = (TextView) convertView
						.findViewById(R.id.tv_dianpuname);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (GroupViewHolder) convertView.getTag();
			}

			viewHolder.groupNameTV.setText(groupItem.getStore_name());

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
		return true;
	}

	final static class GroupViewHolder {
		TextView groupNameTV;
	}

	final static class ChildViewHolder {
		TextView childrenNameTV;
		ImageView im_shangpuimg;
		TextView tv_spec;
		TextView tv_goods_price;
		TextView tv_goods_num;
		RelativeLayout rl_peisongfangshi;
	}
}