package com.edu.thss.smartdental.eat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.thss.smartdental.db.SQLRecipes;
import com.edu.thss.smartdental.model.Recipes;
import com.edu.thss.smartdental.model.foodInRecipes;
import com.edu.thss.smartdental.model.foodNutrition;

import android.R.integer;
import android.content.Context;
import android.content.ClipData.Item;
import android.hardware.ConsumerIrManager;
import android.provider.ContactsContract.Contacts.Data;
import android.text.GetChars;
import android.text.TextPaint;
import android.text.style.UpdateAppearance;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NutritionAdapter extends BaseAdapter{
	private ArrayList<Map<String,Object>> mData = new ArrayList<Map<String,Object>>(); 
	private Context context;
	private SQLRecipes sqlRecipes;
	private String date;
	public double[] nutr_list= new double[14]; 
	
	String[] nutrition = {"维生素","矿物质","其他"};
	String[][] nutrData = {			
			{"A","C","D","E","B1","B2","B9","B12"},
			{"钙"   ,"钾"  ,"铁"  ,"镁" ,"锌"},
			{"纤维素","蛋白质","卡路里"}
	};
	
	int[][] consume = {
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0},
		{0,0,0}
	};
	
	int[][] standard = {
			{700,75,15,15,1,1,400,2},
			{1000,4700,18,310,8},
			{38,2513,2513}
	};
	String[][] unit ={ 
			{"μg","mg","μg","mg","mg","mg","μg","μg"},
			{"mg","mg","mg","mg","mg"},
			{"g","g","kcal"}
	};

	public NutritionAdapter(Context c, String time)
	{
		this.context = c;
		this.date = time;
		sqlRecipes = new SQLRecipes(c);	
		calculate();
		summary();		
	}
	
	public ArrayList<Map<String,Object>> getData()
	{
		return mData;
	}
	
	@Override
	public int getCount() {
		return mData.size();
	};
	public void remove(int position)
	{
		mData.remove(position);
		//notifyDataSetChanged();
	}
	public void add(Map<String, Object> item)
	{
		mData.add(item);
		//notifyDataSetChanged();
	}
	public void add(int position, Map<String, Object> item)
	{
		mData.add(position, item);
		//notifyDataSetChanged();
	}
	public void set(int position, Map<String, Object> item)
	{
		mData.set(position, item);
		//notifyDataSetChanged();
	}
	
	//��ʼ������Ӫ������Ϊ0
	private void init()
	{
		for(int i = 0; i < consume.length; i++)
		{
			for(int j = 0; j < consume[i].length; j++)
				consume[i][j] = 0;
		}
	}
	
	//���㵱��Ӫ��������
	private void calculate()
	{
		String name;
		foodNutrition nutr;	
		int weight;
		init();
		Recipes recipes = sqlRecipes.searchByTime(date);
		if(recipes.time!=null)
		{
			List<foodInRecipes> foods = sqlRecipes.findFoodInRecipes(date);			
			for(int i = 0; i < foods.size(); i++)
			{
				name = foods.get(i).name;
				weight = foods.get(i).weight;
				nutr = sqlRecipes.findOneFoodNutritionByName(name);
				consume[0][0] += nutr.vitamin_A * weight;
				consume[0][1] += nutr.vitamin_C * weight;
				consume[0][2] += nutr.vitamin_D* weight;
				consume[0][3] += nutr.vitamin_E * weight;
				consume[0][4] += nutr.vitamin_B6 * weight;
				consume[0][5] += nutr.vitamin_B2 * weight; 
				consume[0][6] += nutr.vitamin_B9 * weight;
				consume[0][7] += nutr.vitamin_B12 * weight;
				consume[1][0] += nutr.Ga * weight;
				consume[1][1] += nutr.Ka * weight;
				consume[1][2] += nutr.Fe* weight;
				consume[1][3] += nutr.Mg * weight;
				consume[1][4] += nutr.Zn * weight;			
				consume[2][0] += nutr.fiber * weight;
				consume[2][1] += nutr.protein * weight;
				consume[2][2] += nutr.caloric* weight;	
			}
			get_nutr_list();
		}
	}
	
	private void get_nutr_list()
	{
		for(int i = 0; i<consume[1].length;i++)
			nutr_list[i] = consume[1][i];
		for(int i = 0; i<consume[0].length; i++)
			nutr_list[consume[1].length+i] = consume[0][i];
		nutr_list[13] = consume[2][1];
	}
	
	private void summary()
	{
		for(int i = 0; i < nutrition.length; i++)
		{
			Map <String, Object> item = new HashMap <String, Object>();
			item.put("Logo", R.drawable.expander_open_holo_light);
			item.put("name", nutrition[i]);
			int w = 0;
			int r = 0;
			item.put("number", 100);
			for(int j = 0; j < nutrData[i].length; j++)
				r += consume[i][j];		
			item.put("consume", r/100/nutrData[i].length);
			item.put("unit", "%");
			mData.add(item);
		}
	}
	
	public void update()
	{
		mData.clear();
		calculate();
		summary();
		notifyDataSetChanged();
	}
	
	@Override
	public View getView (int position, View convertView, ViewGroup parent)
	{
		String inflater = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(inflater);
		LinearLayout linearLayout = null;
		linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.item_nutrition,null);
		
		ImageView logo = (ImageView) linearLayout.findViewById(R.id.Logo);	
		logo.setImageResource((Integer) mData.get(position).get("Logo"));
		int n = (Integer)mData.get(position).get("number");
		TextView number = (TextView) linearLayout.findViewById(R.id.number);
		number.setText(Integer.toString(n));
		int c = (Integer)mData.get(position).get("consume");
		TextView consume = (TextView) linearLayout.findViewById(R.id.consume);
		consume.setText(Integer.toString(c));
		TextView name = (TextView) linearLayout.findViewById(R.id.name);
		name.setText((String)mData.get(position).get("name"));
		TextView unit = (TextView) linearLayout.findViewById(R.id.unit);
		unit.setText((String)mData.get(position).get("unit"));
		MyProgressBar pb = (MyProgressBar) linearLayout.findViewById(R.id.progressBar1);
		pb.setProgress(c*100/n);		
		return linearLayout;
	}
	public void clickOpen(int position, int itemNo) {
		
		int r = 0;
		int len = 0;
		Map <String, Object> itemm = new HashMap <String, Object>();
		//�����ܼ���Ŀ
		itemm.put("Logo", R.drawable.expander_close_holo_light);
		itemm.put("name",  nutrition[itemNo]);
		itemm.put("number",100);
		len = nutrData[itemNo].length;
		for(int j = 0; j < len; j++)
			r +=  consume[itemNo][j];
		itemm.put("consume", r/len/100);
		itemm.put("unit", "%");
		set(position, itemm);
		//���չ����Ŀ
		len = nutrData[itemNo].length;
		for(int i = 0; i < len; i++)
		{
			Map <String, Object> item = new HashMap <String, Object>();
			item.put("Logo", R.drawable.blank);
			item.put("name",  nutrData[itemNo][i]);
			item.put("number", standard[itemNo][i]);
			item.put("consume", consume[itemNo][i]*standard[itemNo][i]/100);
			item.put("unit",  unit[itemNo][i]);
			add(position+1+i, item);
		}
		notifyDataSetChanged();
	}
	public void clickClose(int position, int itemNo) {
		//�����ܼ���Ŀ
		int r = 0;
		int len = 0;
		Map <String, Object> itemm = new HashMap <String, Object>();
		itemm.put("Logo", R.drawable.expander_open_holo_light);
		itemm.put("name", nutrition[itemNo]);
		itemm.put("number",100);
		len = nutrData[itemNo].length;
		for(int j = 0; j < len; j++)
			r +=  consume[itemNo][j];
		itemm.put("consume", r/len/100);
		itemm.put("unit","%");
		set(position, itemm);
		//ɾ����ϸ��Ŀ
		len = nutrData[itemNo].length;
		for(int i = 0; i < len; i++)
		{
			remove(position+1);
		}
		notifyDataSetChanged();
	}
	
	@Override
	public Map<String,Object> getItem(int position) {
		return mData.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}

