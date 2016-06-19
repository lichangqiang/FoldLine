package com.magicwork.foldline;

import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

import com.magic.foldline.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {
	FoldLineView foldLineView;
	List<CrossAxisScale> crosssAxis=new ArrayList<CrossAxisScale>();
	List<VerticalAxisScale> verticalAxis=new ArrayList<VerticalAxisScale>();
	List<FoldLinePoint> foldLinePoints = new ArrayList<FoldLinePoint>();// ’€œﬂµ„

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		foldLineView=(FoldLineView) findViewById(R.id.fl_content);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void init(){
		crosssAxis.add(new CrossAxisScale("8:00",10));
		crosssAxis.add(new CrossAxisScale("9:00",20));
		crosssAxis.add(new CrossAxisScale("10:00",30));
		crosssAxis.add(new CrossAxisScale("11:00",40));
		crosssAxis.add(new CrossAxisScale("12:00",50));
		
		verticalAxis.add(new VerticalAxisScale("25%",25));
		verticalAxis.add(new VerticalAxisScale("50%",50));
		verticalAxis.add(new VerticalAxisScale("75%",75));
		verticalAxis.add(new VerticalAxisScale("100%",100));

		foldLinePoints.add(new FoldLinePoint(30, "30%", "12:00", true) );
		foldLinePoints.add(new FoldLinePoint(50, "50%", "11:00", true) );
		foldLinePoints.add(new FoldLinePoint(40, "40%", "10:00", true) );
		foldLinePoints.add(new FoldLinePoint(60, "60%", "9:00", true) );
		foldLinePoints.add(new FoldLinePoint(90, "90%", "8:00", true) );
		foldLinePoints.add(new FoldLinePoint(30, "30%", "7:00", true) );
		foldLinePoints.add(new FoldLinePoint(30, "30%", "12:00", true) );
		foldLinePoints.add(new FoldLinePoint(50, "50%", "11:00", true) );
		foldLinePoints.add(new FoldLinePoint(40, "40%", "10:00", true) );
		foldLinePoints.add(new FoldLinePoint(60, "60%", "9:00", true) );
		foldLinePoints.add(new FoldLinePoint(90, "90%", "8:00", true) );
		foldLinePoints.add(new FoldLinePoint(30, "30%", "7:00", true) );
		foldLinePoints.add(new FoldLinePoint(30, "30%", "12:00", true) );
		foldLinePoints.add(new FoldLinePoint(50, "50%", "11:00", true) );
		foldLinePoints.add(new FoldLinePoint(40, "40%", "10:00", true) );
		foldLinePoints.add(new FoldLinePoint(60, "60%", "9:00", true) );
		foldLinePoints.add(new FoldLinePoint(90, "90%", "8:00", true) );
		foldLinePoints.add(new FoldLinePoint(30, "30%", "7:00", true) );
		foldLineView.initData(null, verticalAxis, foldLinePoints);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
