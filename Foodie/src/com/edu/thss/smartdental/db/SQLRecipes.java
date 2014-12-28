package com.edu.thss.smartdental.db;

import java.util.ArrayList;
import java.util.List;

import com.edu.thss.smartdental.model.Recipes;
import com.edu.thss.smartdental.model.foodCategory;
import com.edu.thss.smartdental.model.foodInRecipes;
import com.edu.thss.smartdental.model.foodNutrition;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLRecipes {
	private SmartDentalSQLiteHelper helper;

	public SQLRecipes(Context context){
		// TODO Auto-generated constructor stub
		helper = SmartDentalSQLiteHelper.getInstance(context);
	}

	/*
	 * 插入一个饮食记录
	 */

	public void insert(Recipes p) {
		SQLiteDatabase dbDatabase = helper.getWritableDatabase();
		dbDatabase.execSQL("INSERT INTO recipe(time,score,feedback) VALUES(?,?,?)",
				new Object[] { p.time,p.score,p.feedback });
		//dbDatabase.close();
	}
	
	//根据时间删除一个饮食记录
	
	public void delete(String time)
	{
		SQLiteDatabase database = helper.getWritableDatabase();
		Recipes r = searchByTime(time);
		database.execSQL("delete from recipe where time = ?", new Object[]{time});
		database.execSQL("delete from foodInRecipes where recipe_time = ?", new Object[]{r.time});
	}
	
	//根据时间修改饮食记录的分数
	public void updateScore(String time,int score)
	{
		SQLiteDatabase database = helper.getWritableDatabase();
		database.execSQL("update recipe set score = "+score+" where time = ?", new Object[]{time});
	}
	
	//根据时间修改饮食记录的反馈
	public void updateFeedback(String time,String feedback)
	{
		SQLiteDatabase database = helper.getWritableDatabase();
		database.execSQL("update recipe set feedback = ? where time = ?", new Object[]{feedback,time});
	}
	
	
	//根据食谱id查找食谱
	public Recipes searchById(int id){
		SQLiteDatabase database = helper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from recipe where id = ?",new String[]{id+""});
		Recipes recipes = new Recipes();
		if(cursor.moveToFirst())
		{
			recipes.id = cursor.getInt(cursor.getColumnIndex("id"));
			recipes.time = cursor.getString(cursor.getColumnIndex("time"));
			recipes.score = cursor.getInt(cursor.getColumnIndex("score"));
			recipes.feedback = cursor.getString(cursor.getColumnIndex("feedback"));
		}
		cursor.close();
		return recipes;
				
	}
	
	//根据食谱时间查找食谱
	public Recipes searchByTime(String time){
		SQLiteDatabase database = helper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from recipe where time = ?",new String[]{time});
		Recipes recipes = new Recipes();
		if(cursor.moveToFirst())
		{
			recipes.id = cursor.getInt(cursor.getColumnIndex("id"));
			recipes.time = cursor.getString(cursor.getColumnIndex("time"));
			recipes.score = cursor.getInt(cursor.getColumnIndex("score"));
			recipes.feedback = cursor.getString(cursor.getColumnIndex("feedback"));
		}
		cursor.close();
		
		return recipes;
				
	}
	
	//根据食谱时间查找食谱所含的所有食物条目
	public List<foodInRecipes> findFoodInRecipes(String time)
	{
		List<foodInRecipes> fir = new ArrayList<foodInRecipes>();
		SQLiteDatabase database = helper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from foodInRecipes where recipe_time = ?", new String[]{time});
		while(cursor.moveToNext()){
			foodInRecipes f = new foodInRecipes();
			f.id = cursor.getInt(cursor.getColumnIndex("id"));
			f.name = cursor.getString(cursor.getColumnIndex("name"));
			f.recipe_time = cursor.getString(cursor.getColumnIndex("recipe_time"));
			f.weight = cursor.getInt(cursor.getColumnIndex("weight"));
			fir.add(f);
		}
		cursor.close();
		return fir;
	}
	
	//插入饮食记录的一个食物条目
		public void insertFood(foodInRecipes p) {
			SQLiteDatabase dbDatabase = helper.getWritableDatabase();
			dbDatabase.execSQL("INSERT INTO foodInRecipes(recipe_time,name,weight) VALUES(?,?,?)",
					new Object[] {p.recipe_time,p.name,p.weight });
		}
	
	//根据时间和食物名称删除饮食记录的一个条目
		public void deleteFood(String time,String name){
			SQLiteDatabase database = helper.getWritableDatabase();
			database.execSQL("delete from foodInRecipes where recipe_time = ? and name = ?" , new Object[]{time,name});
		}
		
	//根据时间和食物名称修改饮食记录的重量
		public void updateFoodWeight(String time,String name,int weight){
			SQLiteDatabase database = helper.getWritableDatabase();
			database.execSQL("update foodInRecipes set weight = " + weight + " where recipe_time = ? and name = ?" , new Object[]{time,name});
		}
	//根据食物名字获取食物信息
		public foodNutrition findOneFoodNutritionByName(String name){
			foodNutrition f = new foodNutrition();
			SQLiteDatabase database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery("select * from foodNutrition where name = ?", new String[]{name});
			if(cursor.moveToFirst()){
				f.fc_id = cursor.getInt(cursor.getColumnIndex("fc_id"));
				f.name = cursor.getString(cursor.getColumnIndex("name"));
				f.path = cursor.getString(cursor.getColumnIndex("path"));
				f.vitamin_A = cursor.getDouble(cursor.getColumnIndex("vitamin_A"));
				f.vitamin_C = cursor.getDouble(cursor.getColumnIndex("vitamin_C"));
				f.vitamin_D = cursor.getDouble(cursor.getColumnIndex("vitamin_D"));
				f.vitamin_E = cursor.getDouble(cursor.getColumnIndex("vitamin_E"));
				f.vitamin_B2 = cursor.getDouble(cursor.getColumnIndex("vitamin_B2"));
				f.vitamin_B6 = cursor.getDouble(cursor.getColumnIndex("vitamin_B6"));
				f.vitamin_B9 = cursor.getDouble(cursor.getColumnIndex("vitamin_B9"));
				f.vitamin_B12 = cursor.getDouble(cursor.getColumnIndex("vitamin_B12"));
				f.Ga = cursor.getDouble(cursor.getColumnIndex("Ga"));
				f.Ka = cursor.getDouble(cursor.getColumnIndex("Ka"));
				f.Fe = cursor.getDouble(cursor.getColumnIndex("Fe"));
				f.Mg = cursor.getDouble(cursor.getColumnIndex("Mg"));
				f.Zn = cursor.getDouble(cursor.getColumnIndex("Zn"));
				f.fiber = cursor.getDouble(cursor.getColumnIndex("fiber"));
				f.protein = cursor.getDouble(cursor.getColumnIndex("protein"));
				f.caloric = cursor.getDouble(cursor.getColumnIndex("caloric"));
				f.water = cursor.getDouble(cursor.getColumnIndex("water"));
				f.cholesterol = cursor.getDouble(cursor.getColumnIndex("cholesterol"));
			}
			cursor.close();
			return f;
		}
	//根据食物名称获取食物所属种类
		public String findFoodCategoryByName(String name){
			foodNutrition fn = findOneFoodNutritionByName(name);
			foodCategory f = new foodCategory();
			SQLiteDatabase database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery("select * from foodCategory where id = ?",new String[]{fn.fc_id+""});
			while(cursor.moveToNext())
			{
				f.id = cursor.getInt(cursor.getColumnIndex("id"));
				f.name = cursor.getString(cursor.getColumnIndex("name"));
				f.path = cursor.getString(cursor.getColumnIndex("path"));
			}
			cursor.close();
			return f.name;
		}
	//获取所有的饮食种类
		public List<foodCategory> findFoodCategory(){
			List<foodCategory> fc = new ArrayList<foodCategory>();
			SQLiteDatabase database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery("select * from foodCategory",null);
			while(cursor.moveToNext())
			{
				foodCategory f = new foodCategory();
				f.id = cursor.getInt(cursor.getColumnIndex("id"));
				f.name = cursor.getString(cursor.getColumnIndex("name"));
				f.path = cursor.getString(cursor.getColumnIndex("path"));
				fc.add(f);
			}
			cursor.close();
			return fc;
		}
	
	//根据饮食种类名称获取这个种类所含有的食物
		public List<foodNutrition> findFoodNutritionByName(String name)
		{
			SQLiteDatabase database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery("select * from foodCategory where name = ?", new String[]{name});
			int id = 1;
			if(cursor.moveToFirst())
			{
				id = cursor.getInt(cursor.getColumnIndex("id"));
			}
			cursor.close();
			List<foodNutrition> fn = new ArrayList<foodNutrition>();
			cursor = database.rawQuery("select * from foodNutrition where fc_id = ?", new String[]{id+""});
			while(cursor.moveToNext())
			{
				foodNutrition f = new foodNutrition();
				f.fc_id = cursor.getInt(cursor.getColumnIndex("fc_id"));
				f.name = cursor.getString(cursor.getColumnIndex("name"));
				f.path = cursor.getString(cursor.getColumnIndex("path"));
				f.vitamin_A = cursor.getDouble(cursor.getColumnIndex("vitamin_A"));
				f.vitamin_C = cursor.getDouble(cursor.getColumnIndex("vitamin_C"));
				f.vitamin_D = cursor.getDouble(cursor.getColumnIndex("vitamin_D"));
				f.vitamin_E = cursor.getDouble(cursor.getColumnIndex("vitamin_E"));
				f.vitamin_B2 = cursor.getDouble(cursor.getColumnIndex("vitamin_B2"));
				f.vitamin_B6 = cursor.getDouble(cursor.getColumnIndex("vitamin_B6"));
				f.vitamin_B9 = cursor.getDouble(cursor.getColumnIndex("vitamin_B9"));
				f.vitamin_B12 = cursor.getDouble(cursor.getColumnIndex("vitamin_B12"));
				f.Ga = cursor.getDouble(cursor.getColumnIndex("Ga"));
				f.Ka = cursor.getDouble(cursor.getColumnIndex("Ka"));
				f.Fe = cursor.getDouble(cursor.getColumnIndex("Fe"));
				f.Mg = cursor.getDouble(cursor.getColumnIndex("Mg"));
				f.Zn = cursor.getDouble(cursor.getColumnIndex("Zn"));
				f.fiber = cursor.getDouble(cursor.getColumnIndex("fiber"));
				f.protein = cursor.getDouble(cursor.getColumnIndex("protein"));
				f.caloric = cursor.getDouble(cursor.getColumnIndex("caloric"));
				f.water = cursor.getDouble(cursor.getColumnIndex("water"));
				f.cholesterol = cursor.getDouble(cursor.getColumnIndex("cholesterol"));
				fn.add(f);
			}
			cursor.close();
			return fn;
		}
		
		//根据饮食种类id获取所含有的所有食物
		public List<foodNutrition> findFoodNutritionById(int id)
		{
			SQLiteDatabase database = helper.getReadableDatabase();
			List<foodNutrition> fn = new ArrayList<foodNutrition>();
			Cursor cursor = database.rawQuery("select * from foodNutrition where fc_id = ?", new String[]{id+""});
			while(cursor.moveToNext())
			{
				foodNutrition f = new foodNutrition();
				f.fc_id = cursor.getInt(cursor.getColumnIndex("fc_id"));
				f.name = cursor.getString(cursor.getColumnIndex("name"));
				f.path = cursor.getString(cursor.getColumnIndex("path"));
				f.vitamin_A = cursor.getDouble(cursor.getColumnIndex("vitamin_A"));
				f.vitamin_C = cursor.getDouble(cursor.getColumnIndex("vitamin_C"));
				f.vitamin_D = cursor.getDouble(cursor.getColumnIndex("vitamin_D"));
				f.vitamin_E = cursor.getDouble(cursor.getColumnIndex("vitamin_E"));
				f.vitamin_B2 = cursor.getDouble(cursor.getColumnIndex("vitamin_B2"));
				f.vitamin_B6 = cursor.getDouble(cursor.getColumnIndex("vitamin_B6"));
				f.vitamin_B9 = cursor.getDouble(cursor.getColumnIndex("vitamin_B9"));
				f.vitamin_B12 = cursor.getDouble(cursor.getColumnIndex("vitamin_B12"));
				f.Ga = cursor.getDouble(cursor.getColumnIndex("Ga"));
				f.Ka = cursor.getDouble(cursor.getColumnIndex("Ka"));
				f.Fe = cursor.getDouble(cursor.getColumnIndex("Fe"));
				f.Mg = cursor.getDouble(cursor.getColumnIndex("Mg"));
				f.Zn = cursor.getDouble(cursor.getColumnIndex("Zn"));
				f.fiber = cursor.getDouble(cursor.getColumnIndex("fiber"));
				f.protein = cursor.getDouble(cursor.getColumnIndex("protein"));
				f.caloric = cursor.getDouble(cursor.getColumnIndex("caloric"));
				f.water = cursor.getDouble(cursor.getColumnIndex("water"));
				f.cholesterol = cursor.getDouble(cursor.getColumnIndex("cholesterol"));
				fn.add(f);
			}
			cursor.close();
			return fn;
		}
		
}