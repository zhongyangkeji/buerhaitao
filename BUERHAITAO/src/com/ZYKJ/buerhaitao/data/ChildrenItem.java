package com.ZYKJ.buerhaitao.data;

/**
 * @author lss 2015年7月14日 购物车商品信息
 *
 */
public class ChildrenItem {

	private String id;
	private String name;
	
	public ChildrenItem() {
	}

	
	public ChildrenItem(String id,String name) {
		this.id = id;
		this.name = name;
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
	
	
	
}
