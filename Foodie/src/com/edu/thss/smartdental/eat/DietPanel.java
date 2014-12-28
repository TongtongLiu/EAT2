package com.edu.thss.smartdental.eat;

import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.content.Intent;

import com.edu.thss.smartdental.db.SQLRecipes;
import com.edu.thss.smartdental.eat.NutritionAdapter;
import com.edu.thss.smartdental.eat.ScrollListviewDelete.ItemClickListener;

public class DietPanel extends FragmentActivity implements ItemClickListener, OnClickListener{

	public SQLRecipes sqlRecipes = null;
	String dateString = null;

	//View鎺т欢
	private ListView nutrList;
	private ScrollListviewDelete listviewDelete;	
	
	//Adapter閫傞厤鍣�
	private DeleteAdapter deleteAdapter = null;
	private NutritionAdapter nutritionAdapter = null;
	
	@SuppressLint("UseValueOf") @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diet_panel);			

		//Textview 鑾峰彇鏃堕棿
		dateString = getIntent().getStringExtra("date");
		TextView textView = (TextView) findViewById(R.id.textView_diet);
		textView.setText(dateString);

				
		//鎸夐挳锛氭坊鍔犻鐗�
		Button addFood = (Button) findViewById(R.id.button_addFood);
		addFood.setOnClickListener(this);
		
		Button scoring = (Button) findViewById(R.id.button_scoring);
		scoring.setOnClickListener(this);
		
		Button btReturn = (Button) findViewById(R.id.button_back);
		btReturn.setOnClickListener(this);
		
		
		//鍒楄〃锛氫竴澶╃殑钀ュ吇 	
		nutrList = (ListView) findViewById(R.id.listView_nItem);
		nutritionAdapter = new NutritionAdapter(this, dateString);
		nutrList.setAdapter(nutritionAdapter);		
		nutrList.setOnItemClickListener(isc_nutrition);	
		
		//鍒楄〃锛氫竴澶╃殑椋熺墿
		listviewDelete = (ScrollListviewDelete) findViewById(android.R.id.list);
		deleteAdapter = new DeleteAdapter(this, dateString, nutritionAdapter);
		listviewDelete.setAdapter(deleteAdapter);
		listviewDelete.setOnItemClickListener(isc_food);		
							    
	}
	
	public void onClick(View view)
	{
		Intent intent = null;
		switch(view.getId())
		{
		case R.id.button_addFood:
			intent = new Intent(this, FoodPicker.class);
			intent.putExtra("date", dateString);
			startActivity(intent);
			break;
		case R.id.button_scoring:
			Bundle bundle = new Bundle();
			intent = new Intent("scoring");
			//intent.putExtra("date", dateString);
			nutritionAdapter.get_nutr_list();
			bundle.putString("date", dateString);
			bundle.putDoubleArray("nutr_list",nutritionAdapter.nutr_list);
			intent.putExtras(bundle);
	
			startActivity(intent);
			break;
		case R.id.button_back:
			intent = new Intent(this, EatCalendar.class);
			intent.putExtra("date", dateString);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	//娣诲姞椋熺墿鎸夐挳add_food鐐瑰嚮浜嬩欢
	public OnItemClickListener isc_food = new OnItemClickListener(){
		@Override
		public void onItemClick( AdapterView<?> parent, View view, int position, long id)
		{
			Log.e("position", Integer.toString(position));	
			Log.e("long id", Long.toString(id));
			/* TODO: */
		}
	};

	//鏌ョ湅钀ュ吇鍏冪礌缁勬垚鐐瑰嚮浜嬩欢
	private OnItemClickListener isc_nutrition = new OnItemClickListener(){
		@Override
		public void onItemClick( AdapterView<?> parent, View view, int position, long id)
		{					
			Log.e("position", Integer.toString(position));	
			Log.e("long id", Long.toString(id));
			Map <String, Object> click = nutritionAdapter.getItem(position);
			int itemNo = 0;
			for(int i = 0; i < nutritionAdapter.nutrition.length; i++)
			{
				if(click.get("name").equals(nutritionAdapter.nutrition[i]))
				{
					itemNo = i;
					break;
				}
			}
			if(click.get("Logo").equals(R.drawable.expander_open_holo_light)){			
				nutritionAdapter.clickOpen(position, itemNo);
			}
			else if(click.get("Logo").equals(R.drawable.expander_close_holo_light)){	
				nutritionAdapter.clickClose(position, itemNo);
			}
		}
	};

	@Override
	public void onItemClick(int position) {
		// TODO Auto-generated method stub
		
	}
	
}
