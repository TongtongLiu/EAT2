/*
 * Author: Liu Tongtong
 * Date: Dec 26, 2014
 */

package com.edu.thss.smartdental.eat;

import com.edu.thss.smartdental.eat.R;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.thss.smartdental.db.SQLRecipes;
import com.edu.thss.smartdental.eat.WheelView.onSelectListener;
import com.edu.thss.smartdental.model.Recipes;
import com.edu.thss.smartdental.model.foodCategory;
import com.edu.thss.smartdental.model.foodInRecipes;
import com.edu.thss.smartdental.model.foodNutrition;

class CategoryList {
	String name = null;
	List<String> NameList = new ArrayList<String>();
}

public class MyFood extends Activity implements OnClickListener{
	
	WheelView wv_type;
	WheelView wv_name;
	SQLRecipes sql_recipes = null;
	List<String> food_category_str_list = new ArrayList<String>();
	List<CategoryList> food_name_str_list = new ArrayList<CategoryList>();
	String food_name = null;
	String date = null;
	int weight = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_food);			
		sql_recipes = new SQLRecipes(this);
		//Textview 获取时间
		date = getIntent().getStringExtra("date");
		TextView textView = (TextView) findViewById(R.id.add_food_textView_date);
		textView.setText(date+"日食谱");
		
		wv_type = (WheelView) findViewById(R.id.type_wv);
		wv_name = (WheelView) findViewById(R.id.name_wv);
		
		List<foodCategory> food_category_list = sql_recipes.findFoodCategory();
		for (int i = 0; i < food_category_list.size(); i++) {
			food_category_str_list.add(food_category_list.get(i).name);
		}
		
		List<foodNutrition> food_name_list = null;
		CategoryList tmp_name_list = null;
		for (int i = 0; i < food_category_str_list.size(); i++) {
			food_name_list = new ArrayList<foodNutrition>();
			food_name_list = sql_recipes.findFoodNutritionByName(food_category_str_list.get(i));
			tmp_name_list = new CategoryList();
			tmp_name_list.name = food_category_str_list.get(i);
			for (int j = 0; j < food_name_list.size(); j++) {
				tmp_name_list.NameList.add(food_name_list.get(j).name);
			}
			food_name_str_list.add(tmp_name_list);
		}
		
		wv_type.setData(food_category_str_list);
		wv_type.setOnSelectListener(new onSelectListener()
		{
			@Override
			public void onSelect(String text)
			{
				Toast.makeText(MyFood.this, "要吃 " + text, Toast.LENGTH_SHORT).show();
				int i;
				for (i = 0; i < food_name_str_list.size(); i++) {
					if (food_name_str_list.get(i).name == text)
						break;
				}
				wv_name.setData(food_name_str_list.get(i).NameList);
				food_name = food_name_str_list.get(i).NameList.get(0);
			}
		});
		
		food_name = food_name_str_list.get(0).NameList.get(0);
		wv_name.setData(food_name_str_list.get(0).NameList);
		wv_name.setOnSelectListener(new onSelectListener()
		{
			@Override
			public void onSelect(String text)
			{
				Toast.makeText(MyFood.this, "吃了 " + text, Toast.LENGTH_SHORT).show();
				food_name = text;
			}
		});
		
		Button bt_save = (Button) findViewById(R.id.add_food_button_save);
		Button bt_continue = (Button) findViewById(R.id.add_food_button_continue);
		bt_save.setOnClickListener((OnClickListener) this);
		bt_continue.setOnClickListener((OnClickListener) this);
		
		//final TextView textseek = (TextView) findViewById(R.id.textseek);
		//textseek.setText("0");
		SeekBar seekBar = (SeekBar) findViewById(R.id.add_food_seekBar_weight);
		
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
				//textseek.setText(Integer.toString(progress));
				weight = progress;
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekbar){
				return;
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekbar){
				return;
			}
		});
	}
	
	@Override
	public void onClick(View view)
	{
		Intent intent = null;
		foodInRecipes food = new foodInRecipes();
		food.name = food_name;
		food.recipe_time = date;
		food.weight = weight;
		Recipes recipe = sql_recipes.searchByTime(date);
		if (recipe.time == null) {
			recipe.time = date;
			recipe.score = 0;
			recipe.feedback = "";
			sql_recipes.insert(recipe);
		}
		sql_recipes.insertFood(food);
		
		switch(view.getId())
		{
			case R.id.add_food_button_save:
				//intent = new Intent(MyFood.this, MyDiet.class);
				//MyFood.this.startActivity(intent);
				intent = new Intent("scoring");
				intent.putExtra("date", date);
				startActivity(intent);
				break;
			case R.id.add_food_button_continue:
				intent = new Intent(MyFood.this, MyFood.class);
				intent.putExtra("date", date);
				MyFood.this.startActivity(intent);
				//intent = new Intent("add_food");
				//intent.putExtra("date", date);
				//startActivity(intent);
				break;
			default:
				break;
		}
		
		//this.finish();
	}
}

