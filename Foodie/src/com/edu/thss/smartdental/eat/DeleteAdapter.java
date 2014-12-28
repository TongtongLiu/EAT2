package com.edu.thss.smartdental.eat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.thss.smartdental.db.SQLRecipes;
import com.edu.thss.smartdental.model.Recipes;
import com.edu.thss.smartdental.model.foodInRecipes;
import com.edu.thss.smartdental.model.foodNutrition;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.R.integer;
import android.R.string;
import android.app.Activity;

public class DeleteAdapter extends BaseAdapter {

	public static ListItemDelete itemDelete = null;
	private ArrayList<Map<String,Object>> listDatas = new ArrayList<Map<String,Object>>();	
	private Context context;
	private String date;
	private SQLRecipes sqlRecipes = null;
	private NutritionAdapter nutrAdapter;

	public DeleteAdapter(Context context, String dateString, NutritionAdapter na) {
		//this.listDatas = data;
		this.date = dateString;
		this.context = context;
		this.sqlRecipes = new SQLRecipes(context);
		this.nutrAdapter = na;
		updateData();
	}
	private void updateData() 
	{
		// TODO Auto-generated method stub
		Recipes r = sqlRecipes.searchByTime(date);
		if(r.time!=null){
			List<foodInRecipes> fir = sqlRecipes.findFoodInRecipes(date);			
			int i=0;
			for(i=0;i<fir.size();i++)
			{
				Map <String, Object> item = new HashMap <String, Object>();
				item.put("name",fir.get(i).name);
				item.put("number",(Integer)fir.get(i).weight);
				foodNutrition fn = sqlRecipes.findOneFoodNutritionByName(fir.get(i).name);
				int resID = context.getResources().getIdentifier(fn.path, "drawable", "com.edu.thss.smartdental.eat");
				item.put("Logo",resID);
				item.put("unit", "g");
				listDatas.add(item);
			}
		}
	}
	
	@Override
	public int getCount() {
		return listDatas == null ? 0 : listDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return listDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		String inflater = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(inflater);
		//缁欓鐗╂潯鐩甶tem_food涓浇鍏ヤ俊鎭�
		convertView = (ListItemDelete) layoutInflater.inflate(R.layout.item_food, null);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText((String)listDatas.get(position).get("name"));
		
		ImageView Logo = (ImageView) convertView.findViewById(R.id.Logo);
		Logo.setImageResource((Integer) listDatas.get(position).get("Logo"));
		
		TextView number = (TextView) convertView.findViewById(R.id.number);
		number.setText(Integer.toString((Integer) listDatas.get(position).get("number")));
		
		TextView unit = (TextView) convertView.findViewById(R.id.unit);
		unit.setText((String)listDatas.get(position).get("unit"));
		
		Button btnDelete = (Button) convertView	.findViewById(R.id.btnDelete);
		Button btnNao = (Button) convertView.findViewById(R.id.btnNao);
		
		//鍒犻櫎鎸夐挳鐐瑰嚮浜嬩欢	
		btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = (String) listDatas.get(position).get("name");
				showInfo("删除" + name);
				listDatas.remove(position);					
				sqlRecipes.deleteFood(date, name);
				notifyDataSetChanged();
				ItemDeleteReset();				
				nutrAdapter.update();								
				
			}
		});

		//淇敼鎸夐挳鐐瑰嚮浜嬩欢
		btnNao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showInfo("淇敼"+ listDatas.get(position).get("name"));
				/*
				 * TODO: 鐣岄潰璺宠浆
				 */
			}
		});
		return convertView;
	}
	private Toast mToast;

	public void showInfo(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static void ItemDeleteReset() {
		if (itemDelete != null) {
			itemDelete.reSet();
		}
	}
}
