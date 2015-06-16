package com.ZYKJ.buerhaitao.data;

import java.io.Serializable;
import java.util.Date;

public class Goods implements Serializable {
    private String tbid;

    private String goodsname;

    private String goodsimg;

    private String mdpi;

    private String ldpi;

    private String shopId;

    private Long typeid;

    private String content;

    private Date time;

    private String price;

    private int recommand;
    
    //商铺对象
    private Shop shop;
    //商品分类对象
    private GoodsType goodsType;

    public String getTbid() {
        return tbid;
    }

    public Goods setTbid(String tbid) {
        this.tbid = tbid;
        return this;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public Goods setGoodsname(String goodsname) {
        this.goodsname = goodsname;
        return this;
    }

    public String getGoodsimg() {
        return goodsimg;
    }

    public Goods setGoodsimg(String goodsimg) {
        this.goodsimg = goodsimg;
        return this;
    }

    public String getMdpi() {
        return mdpi;
    }

    public Goods setMdpi(String mdpi) {
        this.mdpi = mdpi;
        return this;
    }

    public String getLdpi() {
        return ldpi;
    }

    public Goods setLdpi(String ldpi) {
        this.ldpi = ldpi;
        return this;
    }

    public String getShopId() {
        return shopId;
    }

    public Goods setShopId(String shopId) {
        this.shopId = shopId;
        return this;
    }

    public Long getTypeid() {
        return typeid;
    }

    public Goods setTypeid(Long typeid) {
        this.typeid = typeid;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Goods setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Goods setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public Goods setPrice(String price) {
        this.price = price;
        return this;
    }

    public int getRecommand() {
        return recommand;
    }

    public Goods setRecommand(int recommand) {
        this.recommand = recommand;
        return this;
    }

	public Shop getShop() {
		return shop;
	}

	public Goods setShop(Shop shop) {
		this.shop = shop;
		return this;
	}

	public GoodsType getGoodsType() {
		return goodsType;
	}

	public Goods setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
		return this;
	}
    
}