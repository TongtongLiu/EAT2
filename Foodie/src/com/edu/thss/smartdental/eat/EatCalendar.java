package com.edu.thss.smartdental.eat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.edu.thss.smartdental.eat.R;
import com.edu.thss.smartdental.db.SQLRecipes;
import com.edu.thss.smartdental.model.Recipes;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextPaint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toast;
//import android.view.View.OnClickListener;
//import android.widget.Button;

@SuppressLint("SimpleDateFormat")
public class EatCalendar extends FragmentActivity {
	protected static final String EXTRA_MESSAGE = null;
	private CaldroidFragment caldroidFragment;
	private CaldroidFragment dialogCaldroidFragment;
	private Date selectdate = null;
	private Date currentdate = null;
	private Button editButton;
	//private Button button;
	public SQLRecipes sqlRecipes = null;
	
	// Init Calendar Settings
	private void initCalendar() {
		Calendar cal = Calendar.getInstance();
		currentdate = cal.getTime();
		selectdate = currentdate;
		if (caldroidFragment != null) {
			caldroidFragment.setTextColorForDate(R.color.blue, currentdate);
			caldroidFragment.setBackgroundResourceForDate(R.drawable.date_pick, currentdate);
		}
	}

	private void setTextView(){
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		final TextView textView_today = (TextView) findViewById(R.id.textView_today);
		final TextView textView_suggestion = (TextView) findViewById(R.id.textView_suggestion);
		final ImageView imageView_feedback = (ImageView) findViewById(R.id.imageView_feedback);
		String text = formatter.format(selectdate);
		textView_today.setText(text);
		TextPaint tPaint = textView_today.getPaint();
		tPaint.setFakeBoldText(true);
		String suggestion;
		Recipes r = sqlRecipes.searchByTime(formatter.format(selectdate));
		if(r.time == null)
		{
			suggestion = "你还没有添加食谱哦~";
			imageView_feedback.setImageResource(R.drawable.btn_rating_star_on_disabled_focused_holo_dark);
		}
		else 
		{
			suggestion = "你得了"+r.score+"分哦~\n"+r.feedback;
			imageView_feedback.setImageResource(R.drawable.btn_rating_star_on_pressed_holo_dark);
		}
		textView_suggestion.setText(suggestion);
	}
	
	// Create Calendar
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		sqlRecipes = new SQLRecipes(this);
		caldroidFragment = new CaldroidFragment();	

		// If Activity is created after rotation
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		// If activity is created from fresh
		else {
			Bundle args = new Bundle();
			Calendar cal = Calendar.getInstance();
			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
			args.putBoolean(CaldroidFragment.ENABLE_CLICK_ON_DISABLED_DATES, false);
			caldroidFragment.setArguments(args);
		}

		// Attach to the activity
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();
		
		// Setup Listener
		final CaldroidListener listener = new CaldroidListener() {
			
			@Override
			public void onSelectDate(Date date, View view) {
				// 更改背景图片
				if (selectdate != null)
				{
					caldroidFragment.setBackgroundResourceForDate(R.color.white, selectdate);
				}
				caldroidFragment.setBackgroundResourceForDate(R.drawable.date_pick, date);
				selectdate = date;	

				caldroidFragment.refreshView();

				//读取数据库
				setTextView();
			}

			@Override
			public void onLongClickDate(Date date, View view) {
				Intent intent = new Intent(EatCalendar.this, MyDiet.class);
				//intent.putExtra(EXTRA_MESSAGE, formatter.format(date));
				intent.putExtra("date", formatter.format(date));
				EatCalendar.this.startActivity(intent);
			}
		};
		
		
		/*editButton = (Button)findViewById(R.id.editButton);
		editButton.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				//Intent intent = new Intent(EatCalendar.this, MyDiet.class);
				//intent.putExtra(EXTRA_MESSAGE, formatter.format(date));
				//intent.putExtra("date", formatter.format(selectdate));
				//startActivity(intent);
			}
		});*/
		// Setup Calendar
		initCalendar();
		setTextView();
		Intent intent = getIntent();
		//dateString = getIntent().getStringExtra("date");
		caldroidFragment.setCaldroidListener(listener);
	}

	 /* Save current states of the Caldroid here*/
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		if (caldroidFragment != null) {
			caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
		}

		if (dialogCaldroidFragment != null) {
			dialogCaldroidFragment.saveStatesToKey(outState,
					"DIALOG_CALDROID_SAVED_STATE");
		}
	}

}
