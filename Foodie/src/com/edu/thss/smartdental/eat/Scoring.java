package com.edu.thss.smartdental.eat;

import java.util.List;
import java.util.Random;

import com.edu.thss.smartdental.eat.R;
import com.edu.thss.smartdental.db.SQLRecipes;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.thss.smartdental.eat.getPoints;
import com.edu.thss.smartdental.eat.NutritionAdapter;
import com.edu.thss.smartdental.model.foodInRecipes;

public class Scoring extends Activity implements OnClickListener {
	private CircleBar circleBar;
	private Button buttonReset;
	private Button buttonBegin;
	private TextView feedback;
	private int ran;
	private String date;
	private double[] str;
	public SQLRecipes sqlrecipe;
	private Context context;
	//Intent intent = getIntent();

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scoring);
		
		Button btReturn = (Button) findViewById(R.id.button_back_1);
		btReturn.setOnClickListener(this);
		
		Button btShare = (Button) findViewById(R.id.button_share);
		btShare.setOnClickListener(this);
		
		Bundle bundle = this.getIntent().getExtras();
		date = bundle.getString("date");
		sqlrecipe = new SQLRecipes(this);

		//final Random random = new Random();
		//double[] temp = {80, 1000, 17.5, 350, 13, 0.75, 100, 0.005, 14, 1.5, 1.7, 1.9, 0.0024, 85};
		//double[] str = intent.getDoubleArrayExtra("nutr_list");
		str = bundle.getDoubleArray("nutr_list");
		final getPoints a = new getPoints(str);
		ran = Integer.parseInt(new java.text.DecimalFormat("0").format(a.score));
		
		feedback = (TextView) findViewById(R.id.scoring_feedback);
		circleBar = (CircleBar) findViewById(R.id.circle);
		circleBar.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				//扫过的角度，正值为顺时针旋转，负值为逆时针旋转（超过360度将作除以360的余数处理）
				circleBar.setSweepAngle(360 * ran / 100);
				//动画运行时间
				circleBar.setDuration(2000 * ran / 100);
				//分数字体大小
				circleBar.setTextSize(circleBar.getHeight() / 4);
				//圆环大小
				circleBar.setCircleWidth(20);
				//点击圆环时增加的大小
				circleBar.setPressWidth(10);
				circleBar.startCustomAnimation();
				//末状态分数
				circleBar.setText(String.valueOf(ran));
				//禁用点击
				circleBar.setClickable(false);
				//跑分结束后延时显示反馈
				new Handler().postDelayed(new Runnable() {
					public void run() {
						//feedback.setText("");
						feedback.setText(getFeedback(ran,a.feedback_score));
						feedback.setMovementMethod(ScrollingMovementMethod.getInstance());
						a.feedback_score = "";
					}
				}, 2000 * ran / 100 + 300);
			}
		});
		
		//android.app.ActionBar actionBar = getActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	public String getFeedback(int score, String fb_score){
		String feedback;
		if(score < 25) feedback =  "您的饮食很不健康" + fb_score;
		else if(score < 60) feedback = "您的饮食有问题" + fb_score;
		else if(score < 75) feedback = "您的饮食良好" + fb_score;
		else if(score < 85) feedback = "您的饮食很好" + fb_score;
	    else feedback = "您的饮食很完美" + fb_score;
		sqlrecipe.updateScore(date, score);
		sqlrecipe.updateFeedback(date, feedback);
		return feedback;
	}
	public void onClick(View view)
	{
		Intent intent = null;
		switch(view.getId())
		{
		case R.id.button_back_1:
			intent = new Intent(this, EatCalendar.class);
			intent.putExtra("date", date);
			startActivity(intent);
			break;
		case R.id.button_share:
    		intent = new Intent("share");
			List<foodInRecipes> food = sqlrecipe.findFoodInRecipes(date);
			for(int i = 0; i < food.size(); i++){
				intent.putExtra("food" + i, food.get(i).name);
			}
			intent.putExtra("foodSize", food.size());
    		intent.putExtra("score", ran);
    		startActivity(intent);
			break;
		default:
			break;
		}
	}
}
