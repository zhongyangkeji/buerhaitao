package com.ZYKJ.buerhaitao.data;

import java.util.List;

/**
 * @author lss 2015年7月14日 购物车店铺信息
 *
 */
public class GroupItem {

	private String id;
	private String name;
	private List<ChildrenItem> childrenItems;
	
	
	public GroupItem() {
	}

	
	public GroupItem(String id,String name,List<ChildrenItem> childrenItems) {
		this.id = id;
		this.name = name;
		this.childrenItems = childrenItems;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<ChildrenItem> getChildrenItems() {
		return childrenItems;
	}


	public void setChildrenItems(List<ChildrenItem> childrenItems) {
		this.childrenItems = childrenItems;
	}

	
	
	
}
