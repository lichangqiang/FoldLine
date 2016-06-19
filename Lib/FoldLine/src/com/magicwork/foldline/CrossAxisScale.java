package com.magicwork.foldline;

/**
 * 横轴刻度
 * @author licq
 *
 */
public class CrossAxisScale {
	private String title;//刻度名称
	private int percentPos;//以百分比标注横轴位置
	
	
	public CrossAxisScale(String title, int percentPos) {
		super();
		this.title = title;
		this.percentPos = percentPos;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPercentPos() {
		return percentPos;
	}
	public void setPercentPos(int percentPos) {
		this.percentPos = percentPos;
	}
	
	
}
