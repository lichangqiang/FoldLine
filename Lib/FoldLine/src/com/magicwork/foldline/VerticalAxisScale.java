package com.magicwork.foldline;

/**
 * 竖轴刻度
 * @author licq
 *
 */
public class VerticalAxisScale {
	private String title;//刻度名称
	private int percentPos;//以百分比标注竖轴位置
	
	
	public VerticalAxisScale(String title, int postionPercent) {
		super();
		this.title = title;
		this.percentPos = postionPercent;
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
