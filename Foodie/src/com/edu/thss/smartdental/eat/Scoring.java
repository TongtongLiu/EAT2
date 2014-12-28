package com.edu.thss.smartdental.eat;

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

public class Scoring extends Activity {
	private CircleBar circleBar;
	private Button buttonReset;
	private Button buttonBegin;
	private TextView feedback;
	private int ran;
	private String date;
	public SQLRecipes sqlrecipe;
	private Context context;
	Intent intent = getIntent();

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scoring);
		
		date = getIntent().getStringExtra("date");
		sqlrecipe = new SQLRecipes(this);
		//final Random random = new Random();
		double[] temp = {80, 1000, 17.5, 350, 13, 0.75, 100, 0.005, 14, 1.5, 1.7, 1.9, 0.0024, 85};
		//double[] str = intent.getDoubleArrayExtra("nutr_list");
		final getPoints a = new getPoints(temp);
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
				circleBar.setCircleWidth(60);
				//点击圆环时增加的大小
				circleBar.setPressWidth(6);
				circleBar.startCustomAnimation();
				//末状态分数
				circleBar.setText(String.valueOf(ran));
				//禁用点击
				circleBar.setClickable(false);
				//跑分结束后延时显示反馈
				new Handler().postDelayed(new Runnable() {
					public void run() {
						feedback.setText(getFeedback(ran,a.feedback_score));
					}
				}, 2000 * ran / 100 + 300);
			}
		});
		
		//android.app.ActionBar actionBar = getActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	public String getFeedback(int score, String fb_score){
		String feedback;
		if(score < 25) feedback =  "您的饮食很不健康\n" + fb_score;
		else if(score < 60) feedback = "您的饮食有问题\n" + fb_score;
		else if(score < 75) feedback = "您的饮食良好\n" + fb_score;
		else if(score < 85) feedback = "您的饮食很好\n" + fb_score;
	    else feedback = "您的饮食很完美\n" + fb_score;
		sqlrecipe.updateScore(date, score);
		sqlrecipe.updateFeedback(date, feedback);
		return feedback;
	}
	/*
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        MenuInflater inflater = getMenuInflater();  
        inflater.inflate(R.menu.scoring_menu, menu);
        return super.onCreateOptionsMenu(menu);  
    }  
   
    @SuppressLint("NewApi") @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
    	switch(item.getItemId()){
    	case R.id.food2:
    		setTitle("你是吃货！");
    		feedback.setText(Integer.toHexString(R.id.food2));
    		return true;
    	case R.id.food3:
    		setTitle("你就是吃货！！");
    		feedback.setText(Integer.toHexString(R.id.food3));
    		return true;
    	case android.R.id.home:
            Intent upIntent = NavUtils.getParentActivityIntent(this);  
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {  
                TaskStackBuilder.create(this)
                	.addNextIntentWithParentStack(upIntent)
                	.startActivities();
            } 
            else {
                upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
                NavUtils.navigateUpTo(this, upIntent);  
            }
    		return true;
		default:
    		setTitle("你不是吃货～");
            return true;
    	}

    } */
}
