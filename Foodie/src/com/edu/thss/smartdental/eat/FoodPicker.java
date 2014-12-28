/**
 * 新增食物选择页面
 * 
 * @author Liu Tongtong
 * @date Dec 26, 2014
 * 
 */

package com.edu.thss.smartdental.eat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
// import android.widget.Toast;

import com.edu.thss.smartdental.db.SQLRecipes;
import com.edu.thss.smartdental.eat.R;
import com.edu.thss.smartdental.eat.WheelView.onSelectListener;
import com.edu.thss.smartdental.model.Recipes;
import com.edu.thss.smartdental.model.foodCategory;
import com.edu.thss.smartdental.model.foodInRecipes;
import com.edu.thss.smartdental.model.foodNutrition;

public class FoodPicker extends Activity {
	private SQLRecipes mSqlRecipes = null;
	private String mDate = null;
	private String mName = null;
	private int mWeight = 0;
	private class CategorySet {
		String categoryString = null;
		List<String> nameList = new ArrayList<String>();
	}
	private List<String> mFoodCategoryStringLIst = new ArrayList<String>();
	private List<CategorySet> mFoodNameList = new ArrayList<CategorySet>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_picker);
		
		// 时间初始化
		setDate(getIntent().getStringExtra("date"));
		
		// 数据库初始化
		setSqlRecipes(new SQLRecipes(this));
		
		// 种类名称、事物名称初始化
		setFoodItems();
		
		// 重量事件设置
		setWeightBarListener();
		
		// 按钮事件设置
		setSaveButtonListener();
		setContinueButtonListener();
	}
	
	private void setDate(String date) {
		this.mDate = date;
		TextView textView = (TextView) findViewById(R.id.textView_food_date);
		textView.setText(this.mDate);
	}

	public void setSqlRecipes(SQLRecipes sqlRecipes) {
		this.mSqlRecipes = sqlRecipes;
	}
	
	public void setFoodItems() {
		WheelView wvFoodCategory = (WheelView) findViewById(R.id.wheelView_type);
		
		// 设置种类名称列表
		List<foodCategory> tmpFoodCategoryList = mSqlRecipes.findFoodCategory();
		for (int i = 0; i < tmpFoodCategoryList.size(); i++) {
			mFoodCategoryStringLIst.add(tmpFoodCategoryList.get(i).name);
		}
		
		// 设置每一个种类对应的食物名称列表
		List<foodNutrition> tmpFoodNutritionList = null;
		CategorySet tmpCategorySet = null;
		for (int i = 0; i < mFoodCategoryStringLIst.size(); i++) {
			tmpFoodNutritionList = new ArrayList<foodNutrition>();
			tmpFoodNutritionList = mSqlRecipes.findFoodNutritionByName(mFoodCategoryStringLIst.get(i));
			tmpCategorySet = new CategorySet();
			tmpCategorySet.categoryString = mFoodCategoryStringLIst.get(i);
			for (int j = 0; j < tmpFoodNutritionList.size(); j++) {
				tmpCategorySet.nameList.add(tmpFoodNutritionList.get(j).name);
			}
			mFoodNameList.add(tmpCategorySet);
		}
		
		// 设置wvFoodCategory的数据和选择事件
		wvFoodCategory.setData(mFoodCategoryStringLIst);
		wvFoodCategory.setOnSelectListener(new onSelectListener() 
		{
			@Override
			public void onSelect(String text) {
				// Toast.makeText(FoodPicker.this, "要吃 " + text, Toast.LENGTH_SHORT).show();
				// 根据类别名称设置wvFoodName的数据
				int i;
				for (i = 0; i < mFoodNameList.size(); i++) {
					if (mFoodNameList.get(i).categoryString == text)
						break;
				}
				WheelView wvFoodName = (WheelView) findViewById(R.id.wheelView_name);
				wvFoodName.setData(mFoodNameList.get(i).nameList);
				mName = mFoodNameList.get(i).nameList.get(0);
			}
		});
		
		WheelView wvFoodName = (WheelView) findViewById(R.id.wheelView_name);
		
		// 设置wvFoodName的数据和选择事件
		mName = mFoodNameList.get(0).nameList.get(0);
		wvFoodName.setData(mFoodNameList.get(0).nameList);
		wvFoodName.setOnSelectListener(new onSelectListener() {
			@Override
			public void onSelect(String text) {
				// Toast.makeText(FoodPicker.this, "吃了 " + text, Toast.LENGTH_SHORT).show();
				mName = text;
			}
		});
	}
	
	private void setWeightBarListener() {
		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar_food_weight);
		
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
				mWeight = progress;
				TextView textView = (TextView) findViewById(R.id.textView_weight);
				textView.setText(getResources().getString(R.string.food_picker_weight) + ": " + mWeight + "g");
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekbar) {
				return;
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekbar) {
				return;
			}
		});
	}
	
	private void insertFoodItem(SQLRecipes sqlRecipes, String date, String name, int weight) {
		if (weight <= 0) {
			return;
		}
		
		Recipes recipes = sqlRecipes.searchByTime(date);
		if (recipes.time == null) {
			recipes.time = date;
			recipes.score = 0;
			recipes.feedback = "";
			sqlRecipes.insert(recipes);
		}
		
		foodInRecipes foodItem = new foodInRecipes();
		foodItem.recipe_time = date;
		foodItem.name = name;
		foodItem.weight = weight;
		sqlRecipes.insertFood(foodItem);
	}
	
	private void setSaveButtonListener() {
		Button btSave = (Button) findViewById(R.id.button_food_save);
		
		btSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				insertFoodItem(mSqlRecipes, mDate, mName, mWeight);
				startNewIntent(new Intent("scoring"), mDate);
			}
		});
	}
	
	private void setContinueButtonListener() {
		Button btContinue = (Button) findViewById(R.id.button_food_continue);
		
		btContinue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				insertFoodItem(mSqlRecipes, mDate, mName, mWeight);
				startNewIntent(new Intent(FoodPicker.this, FoodPicker.class), mDate);
				// startNewIntent(new Intent("scoring"), mDate);
			}
		});
	}
	
	private void startNewIntent(Intent intent, String date) {
		intent.putExtra("date", mDate);
		startActivity(intent);
		this.finish();
	}
}

