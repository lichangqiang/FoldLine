package com.magicwork.foldline;

import java.util.ArrayList;
import java.util.List;

import com.magic.foldline.R;

import android.R.color;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 折线View
 * 
 * @author licq
 *
 */
public class FoldLineView extends View {

	List<CrossAxisScale> crossAxis = new ArrayList<CrossAxisScale>();// 横轴刻度
	List<VerticalAxisScale> verticalAxis = new ArrayList<VerticalAxisScale>();// 竖轴刻度
	List<FoldLinePoint> foldLinePoints = new ArrayList<FoldLinePoint>();// 折线点

	private int foldLineColor;// 折线的颜色
	private int verticalAxisColor;// 纵轴的颜色
	private int crossAxisColor;// 横轴的颜色
	private int crossAxisScaleTextColor;// 横轴刻度文字的颜色
	private int crossAxisScaleTextSize;// 横轴刻度文字的大小
	private int axisWidth;// 纵、横轴宽度
	private int axisScaleWidth = 50;// 纵、横刻度宽度
	private boolean isFillMode;// 是否是填充模式
	private boolean isDrawFoldLineCircle;// 是否绘制折线点的圆圈
	private int foldLineCircleRadius;// 折线点圆圈的半径
	private int foldLineCircleColor;// 折线点圆圈的颜色
	private int foldLineWidth = 5;// 折线的宽度
	private int foldLineOuterColor;

	private int foldLineCount = 10;// 每屏显示的折线数量
	private int startIndex;// 绘制折线点的起始位置
	private int endIndex;// 绘制折线点的结束位置
	private int foldLineDis;// 绘制的间隔距离

	private Paint verticalAxisPaint;
	private Paint crossAxisPaint;
	private Paint crossAxispScalePaint;
	private Paint verticalAxisTextPaint;
	private Paint crcossAxisTextPaint;
	private Paint foldLinePaint;
	private Paint foldLineOutPaint;
	private Paint foldLineCirclePaint;
	private Paint foldLineCircleInnerPaint;
	private Paint foldLinePointTextPaint;
	private int foldLineAreaWidth;// 折线图的宽度

	private int moveX = -100;

	private int lastTouchX;
	private int touchSlop;

	private int clickX;
	private int clickY;

	public interface onFoldPointSelctedListener {
		public void onFoldPointSelected(FoldLinePoint point);
	}

	public onFoldPointSelctedListener foldPointSelectedListner;

	public void setOnFoldPointSelectedListener(onFoldPointSelctedListener listener) {
		this.foldPointSelectedListner = listener;
	}

	public FoldLineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.foldline);
		foldLineColor = typeArray.getColor(R.styleable.foldline_foldLineColor, color.black);
		verticalAxisColor = typeArray.getColor(R.styleable.foldline_verticalAxisColor, color.black);
		crossAxisColor = typeArray.getColor(R.styleable.foldline_crossAxisColor, color.black);
		axisWidth = typeArray.getDimensionPixelSize(R.styleable.foldline_axisWidth, 10);
		axisScaleWidth = typeArray.getDimensionPixelSize(R.styleable.foldline_axisScaleWidth, 10);
		isFillMode = typeArray.getBoolean(R.styleable.foldline_isFillMode, true);
		isDrawFoldLineCircle = typeArray.getBoolean(R.styleable.foldline_isDrawFoldLineCircle, true);
		foldLineCircleRadius = typeArray.getDimensionPixelSize(R.styleable.foldline_foldLineCircleRadius, 10);
		foldLineCircleColor = typeArray.getColor(R.styleable.foldline_foldLineCircleColor, Color.WHITE);
		crossAxisScaleTextColor = typeArray.getColor(R.styleable.foldline_crossAxisScaleTextColor, Color.BLACK);
		crossAxisScaleTextSize = typeArray.getColor(R.styleable.foldline_crossAxisScaleTextSize, 20);
		foldLineOuterColor = typeArray.getColor(R.styleable.foldline_foldLineOuterColor, Color.WHITE);

		touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		typeArray.recycle();
		initPaint();
	}

	public void initPaint() {
		verticalAxisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		crossAxisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		verticalAxisTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		crcossAxisTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		crossAxispScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		foldLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		foldLineOutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		foldLineCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		foldLineCircleInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		foldLinePointTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		verticalAxisPaint.setColor(crossAxisColor);
		verticalAxisPaint.setStyle(Style.STROKE);
		verticalAxisPaint.setStrokeWidth(axisWidth);
		// 横轴参数初始化
		crossAxisPaint.setColor(crossAxisColor);
		crossAxisPaint.setStyle(Style.STROKE);
		crossAxisPaint.setStrokeWidth(axisWidth);
		// 横轴刻度参数初始化
		crossAxispScalePaint.setColor(crossAxisColor);
		crossAxispScalePaint.setStyle(Style.STROKE);
		crossAxispScalePaint.setStrokeWidth(axisWidth);
		// 横轴刻度文字
		crcossAxisTextPaint.setColor(crossAxisScaleTextColor);
		crcossAxisTextPaint.setTextSize(crossAxisScaleTextSize);
		// 纵轴刻度文字
		verticalAxisTextPaint.setColor(crossAxisScaleTextColor);
		verticalAxisTextPaint.setTextSize(crossAxisScaleTextSize);
		// 折线初始化
		foldLinePaint.setColor(foldLineColor);
		foldLinePaint.setStrokeWidth(foldLineWidth);
		foldLinePaint.setStyle(Style.FILL_AND_STROKE);
		// 折线轮廓
		foldLineOutPaint.setColor(foldLineOuterColor);
		foldLineOutPaint.setStrokeWidth(foldLineWidth);
		foldLineOutPaint.setStyle(Style.STROKE);

		// 折线外圆圈
		foldLineCirclePaint.setColor(foldLineOuterColor);
		foldLineCirclePaint.setStrokeWidth(foldLineWidth / 2);
		foldLineCirclePaint.setStyle(Style.STROKE);
		foldLineCirclePaint.setXfermode(new PorterDuffXfermode(Mode.SRC_OVER));
		// 折线内圆圈
		foldLineCircleInnerPaint.setColor(Color.WHITE);
		foldLineCircleInnerPaint.setStrokeWidth(foldLineWidth / 2);
		foldLineCircleInnerPaint.setStyle(Style.FILL);
		foldLineCircleInnerPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_OVER));
		// 折线点被选中时
		foldLinePointTextPaint.setColor(crossAxisScaleTextColor);
		foldLinePointTextPaint.setTextSize(crossAxisScaleTextSize);
	}

	public void initData(List<CrossAxisScale> crossAxisScales, List<VerticalAxisScale> verticalAxisScales,
			List<FoldLinePoint> points) {
		crossAxis = crossAxisScales;
		verticalAxis = verticalAxisScales;
		foldLinePoints = points;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		foldLineDis = getRealWidth() / foldLineCount;
		drawCrossAxis(canvas);
		drawVerticalAxis(canvas);
		drawFoldLine(canvas);
	}

	/**
	 * 绘制横坐标轴
	 * 
	 * @param canvas
	 */
	private void drawCrossAxis(Canvas canvas) {
		canvas.save();
		Rect rectArea = new Rect(getAxisStartX(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(),
				getMeasuredHeight());
		canvas.clipRect(rectArea);
		Path axisPath = new Path();
		Path axisScalePath = new Path();
		FontMetrics fontMetrics = crcossAxisTextPaint.getFontMetrics();
		int fontHeight = (int) (fontMetrics.descent - fontMetrics.ascent);// 字体的高度
		int startY = getAxisStartY();
		int startX = getAxisStartX();
		// 绘制横轴
		axisPath.setLastPoint(startX, startY);
		axisPath.lineTo(getMeasuredWidth() - getPaddingRight(), startY);

		// 绘制横轴刻度
		if (crossAxis != null) {// 若横轴有自定义刻度则绘制
			for (int i = 0; i < crossAxis.size(); i++) {
				CrossAxisScale axis = crossAxis.get(i);
				int x = startX + (int) ((axis.getPercentPos() * 0.01f * getRealWidth() - axisWidth / 2));
				axisScalePath.moveTo(x, startY);
				axisScalePath.lineTo(x, startY + axisScaleWidth);// 绘制刻度
				canvas.drawText(axis.getTitle(), x - crcossAxisTextPaint.measureText(axis.getTitle()) / 2,
						startY + axisScaleWidth + fontHeight, crcossAxisTextPaint);// 绘制刻度单位
			}
		} else {// 若横轴无定义则根据折线点绘制
			int lastEndX = getCrossStartX() + moveX;// 首先计算出偏移，当已有的不能填充满屏幕时需要偏移到坐标起始位置

			for (int i = foldLinePoints.size() - 1; i >= 0; i--) {
				FoldLinePoint foldPonit = foldLinePoints.get(i);
				axisScalePath.moveTo(lastEndX, startY);
				axisScalePath.lineTo(lastEndX, startY + axisScaleWidth);// 绘制刻度
				canvas.drawText(foldPonit.getScaleTip(),
						lastEndX - crcossAxisTextPaint.measureText(foldPonit.getScaleTip()) / 2,
						startY + axisScaleWidth + fontHeight, crcossAxisTextPaint);// 绘制刻度单位
				lastEndX -= foldLineDis;
			}
		}

		canvas.drawPath(axisPath, crossAxisPaint);
		canvas.drawPath(axisScalePath, crossAxispScalePaint);
		canvas.restore();
	}

	/**
	 * 获取横轴的第一个点的开始坐标
	 * 
	 * @return
	 */
	private int getCrossStartX() {
		return getMeasuredWidth() - getPaddingRight()
				- Math.max((getRealWidth() - (foldLinePoints.size() * foldLineDis)), 0);
	}
	
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int currentTouchX = (int) event.getX();
		int deltaX;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastTouchX = (int) event.getX();
			clickX = currentTouchX;
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_MOVE:
			deltaX = currentTouchX - lastTouchX;
			if (Math.abs(deltaX) > 8) {
				moveX += deltaX;
				fillMoveValue();
				clickX = currentTouchX;
				clickY = (int) event.getY();
				postInvalidate();
				lastTouchX = currentTouchX;
			}
			break;
		case MotionEvent.ACTION_UP:
			clickX = currentTouchX;
			clickY = (int) event.getY();
			lastTouchX = 0;
			postInvalidate();
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 获取当向左边滑动时，左边坐标的最小值（防止坐标滑动超过坐标轴）
	 * 
	 * @return
	 */
	private int getLeftMoveMinCrossX() {
		return getAxisStartX() + foldLineDis;
	}

	/**
	 * 获取当向右滑动时，右边坐标的最大值（防止坐标滑动超过坐标轴）
	 * 
	 * @return
	 */
	private int getRightMoveMaxCrossX() {
		return getFoldLineLength() - getAxisStartX();
	}

	/**
	 * 对move的值进行修正，防止过度滑动
	 * 
	 * @return
	 */
	private void fillMoveValue() {
		int expectX = getCrossStartX() + moveX;
		if (expectX < getLeftMoveMinCrossX()) {
			moveX = getLeftMoveMinCrossX() - getCrossStartX();
		}

		int max = getRightMoveMaxCrossX();
		if (expectX > getRightMoveMaxCrossX()) {
			moveX = getRightMoveMaxCrossX() - getCrossStartX();
		}
	}

	private int getFoldLineLength() {
		return foldLinePoints.size() * foldLineDis;
	}

	private void drawVerticalAxis(Canvas canvas) {
		Path axisPath = new Path();
		Path axisScalePath = new Path();
		FontMetrics fontMetrics = verticalAxisTextPaint.getFontMetrics();
		int fontHeight = (int) (fontMetrics.descent - fontMetrics.ascent);// 字体的高度
		int startY = getAxisStartY();
		int startX = getAxisStartX();
		// 绘制纵轴
		axisPath.setLastPoint(startX, startY);
		axisPath.lineTo(startX, getPaddingTop());
		canvas.drawPath(axisPath, verticalAxisPaint);

		for (int i = 0; i < verticalAxis.size(); i++) {
			VerticalAxisScale axis = verticalAxis.get(i);
			int y = startY - (int) ((axis.getPercentPos() * 0.01f * getRealHeight() - axisWidth / 2));
			axisScalePath.moveTo(startX, y);
			axisScalePath.lineTo(startX - axisScaleWidth, y);
			canvas.drawText(axis.getTitle(), startX - crcossAxisTextPaint.measureText(axis.getTitle()), y + fontHeight,
					crcossAxisTextPaint);// 绘制刻度单位
		}

		canvas.drawPath(axisScalePath, crossAxispScalePaint);

	}

	private void drawFoldLine(Canvas canvas) {
		canvas.save();
		Rect areaFold = new Rect(getAxisStartX(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(),getAxisStartY());// 折线图的绘制范围
		canvas.clipRect(areaFold);
		Path foldPath = new Path();
		Path foldeLinePath = new Path();
		Path circlePath = new Path();
		Path circleSelectPath=new Path();
		FoldLinePoint slectedPoint = null;
		int lastEndX = getCrossStartX() + moveX;// 首先计算出偏移，当已有的不能填充满屏幕时需要偏移到坐标起始位置

		int fisrtPointX = lastEndX;// 最左边的X坐标
		int firstPointY = 0;// 最左边的Y坐标
		for (int i = foldLinePoints.size() - 1; i >= 0; i--) {
			FoldLinePoint foldPonit = foldLinePoints.get(i);
			int y = (int) (foldPonit.getPercentY() * 0.01f * getRealHeight());
			if (i == foldLinePoints.size() - 1) {
				firstPointY = y;
				foldPath.moveTo(fisrtPointX, y);
				foldeLinePath.moveTo(fisrtPointX, y);
			}

			foldPath.lineTo(lastEndX, y);
			foldeLinePath.lineTo(lastEndX, y);
			Rect rect = new Rect(lastEndX - foldLineDis / 2, getPaddingTop(), lastEndX + foldLineDis / 2,
					getMeasuredHeight());
			if (rect.contains(clickX, clickY)) {
				slectedPoint=foldPonit;
				FontMetrics fontMetricx=foldLinePointTextPaint.getFontMetrics();
				int textWidth=(int) foldLinePointTextPaint.measureText(slectedPoint.getTip());
				circleSelectPath.addRect(lastEndX-textWidth/2, y, lastEndX+textWidth/2, y+fontMetricx.descent-fontMetricx.ascent, Direction.CW);
				circlePath.addCircle(lastEndX, y, 30, Direction.CCW);// 获取圆点的路径
				if (foldPointSelectedListner != null) {
					foldPointSelectedListner.onFoldPointSelected(foldPonit);// 折线点被选择
				}
			} else {
				circlePath.addCircle(lastEndX, y, 10, Direction.CCW);// 获取圆点的路径
			}
			// 绘制刻度单位
			lastEndX -= foldLineDis;
		}

		foldPath.lineTo(lastEndX + foldLineDis, getAxisStartY() - axisWidth * 2);
		foldPath.lineTo(fisrtPointX, getAxisStartY() - axisWidth);
		foldPath.lineTo(fisrtPointX, firstPointY);

		canvas.drawPath(foldPath, foldLinePaint);
		canvas.drawPath(foldeLinePath, foldLineOutPaint);
		canvas.drawPath(circlePath, foldLineCirclePaint);
		canvas.drawPath(circlePath, foldLineCircleInnerPaint);
		if(slectedPoint!=null){
			canvas.drawTextOnPath(slectedPoint.getTip(), circleSelectPath, 0, foldLinePointTextPaint.descent(), foldLinePointTextPaint);
		}
		canvas.restore();
	}

	private void drawFoldPointText(Canvas canvas,FoldLinePoint foldPoint) {
		canvas.drawText(foldPoint.getTip(), getMeasuredWidth()-getPaddingRight()-100, getPaddingTop()+200, foldLinePointTextPaint);
	}

	/**
	 * 轴的起始X坐标
	 * 
	 * @return
	 */
	private int getAxisStartX() {
		FontMetrics fontMetris = verticalAxisTextPaint.getFontMetrics();
		int fontWidth = (int) verticalAxisTextPaint.measureText(verticalAxis.get(verticalAxis.size() - 1).getTitle());
		return getPaddingLeft() + fontWidth;
	}

	/**
	 * 轴的起始Y坐标
	 * 
	 * @return
	 */
	private int getAxisStartY() {
		FontMetrics fontMetrics = crcossAxisTextPaint.getFontMetrics();
		int fontHeight = (int) (fontMetrics.descent - fontMetrics.ascent);// 字体的高度
		return getMeasuredHeight() - getPaddingBottom() - axisWidth / 2 - axisScaleWidth - fontHeight;
	}

	/**
	 * 绘制内容区域的宽度
	 * 
	 * @return
	 */
	private int getRealWidth() {
		return getMeasuredWidth() - getAxisStartX() - getPaddingRight();
	}

	/**
	 * 绘制内容区域的高度
	 * 
	 * @return
	 */
	private int getRealHeight() {
		return getAxisStartY() - getPaddingRight();
	}
}
