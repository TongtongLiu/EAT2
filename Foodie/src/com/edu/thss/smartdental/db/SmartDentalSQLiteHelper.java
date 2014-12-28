package com.edu.thss.smartdental.db;

import com.edu.thss.smartdental.eat.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SmartDentalSQLiteHelper extends SQLiteOpenHelper {
	private static SmartDentalSQLiteHelper mInstance = null;
	public static final String DATABASE_NAME = "smartdental.db";
	public static final int DATABASE_VERSION = 1;
	public static Context mContext = null;
	public SmartDentalSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}
	
	public static synchronized SmartDentalSQLiteHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SmartDentalSQLiteHelper(context);
        }
        mContext = context;
        return mInstance;
    }
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		createTables(database);
		insertIntoFoodCategory(database);
		insertIntoNutritionForDay(database);
		try {
			insertIntoFoodNutrition(database);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//用于测试
		//insertRecipe(database);
	}

	private  void insertIntoFoodNutrition(SQLiteDatabase database) throws IOException {
		
		String result = "";   
           InputStream in = mContext.getResources().openRawResource(R.raw.food);   
            //获取文件的字节数   
           BufferedReader buf = new BufferedReader(new InputStreamReader(in));
           while(true)
           {
           result = buf.readLine();
           if(result == null)
        	   break;
           String[] str = result.split(" ");
           Object[] ob = {str[0],str[1],str[2],str[3],str[4],str[5],str[6],str[7],str[8],str[9],str[10],str[11],str[12],str[13],str[14],str[15],str[16],str[17],str[18],str[19],str[20]};
           database.execSQL("INSERT INTO foodNutrition(fc_id,name,path,vitamin_A,vitamin_C,vitamin_D,vitamin_E,vitamin_B2,vitamin_B6,vitamin_B9,vitamin_B12,Ga,Fe,Mg,Ka,Zn ,fiber,protein,caloric,water, cholesterol)" +
					" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",ob);
           }
        in.close();
        buf.close();
   
		
	}

	private void insertIntoNutritionForDay(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL("INSERT INTO nutritionForDay(vitamin_A,vitamin_C,vitamin_D,vitamin_E,vitamin_B2,vitamin_B6,vitamin_B9,vitamin_B12,Ga,Fe,Mg,Zn,Ka ,fiber,protein,caloric,water, cholesterol)" +
				" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] {900,90,15,15,1,1,400,2,1000,8,400,11,4700,38,60,2513,0,0});
	}

	private void insertIntoFoodCategory(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		Object[] objects[] = {new Object[]{"主食",R.drawable.ic_launcher},new Object[]{"五谷",R.drawable.ic_launcher},new Object[]{"奶制品",R.drawable.ic_launcher},new Object[]{"干果",R.drawable.ic_launcher},new Object[]{"水产",R.drawable.ic_launcher},new Object[]{"水果",R.drawable.ic_launcher},
				new Object[]{"肉类",R.drawable.ic_launcher},new Object[]{"菌藻类",R.drawable.ic_launcher},new Object[]{"蔬菜",R.drawable.ic_launcher},new Object[]{"蛋类",R.drawable.ic_launcher},new Object[]{"调味品",R.drawable.ic_launcher},new Object[]{"豆制品",R.drawable.ic_launcher},
				new Object[]{"零食",R.drawable.ic_launcher},new Object[]{"饮料",R.drawable.ic_launcher}};
		int i=0;
		for(i = 0;i < objects.length;i++)
		{
			database.execSQL("INSERT INTO foodCategory(name,path) VALUES(?,?)",objects[i]);
		}

	}
			
	public void createTables(SQLiteDatabase database) {
		database.execSQL("create table IF NOT EXISTS recipe (id integer primary key, " +
															"time string not null," +
															"score int," +
															"feedback String)");
		database.execSQL("create table IF NOT EXISTS foodInRecipes (id integer primary key, " +
				"recipe_time string not null," +
				"name String not null," +
				"weight int not null);");
		database.execSQL("create table IF NOT EXISTS foodCategory(id integer primary key, " +
													"name String not null," +
													"path String);");
		database.execSQL("create table IF NOT EXISTS foodNutrition(id integer primary key," +
													"fc_id int not null," +
													"name String not null," +
													"path String," +
													"vitamin_A double default 0.0," +
													"vitamin_C double default 0.0," +
													"vitamin_D double default 0.0," +
													"vitamin_E double default 0.0," +
													"vitamin_B2 double default 0.0," +
													"vitamin_B6 double default 0.0," +
													"vitamin_B9 double default 0.0," +
													"vitamin_B12 double default 0.0," +
													"Ga double default 0.0," +
													"Fe double default 0.0," +
													"Mg double default 0.0," +
													"Ka double default 0.0," +
													"Zn double default 0.0," +
													"fiber double default 0.0," +
													"protein double default 0.0," +
													"caloric double default 0.0," +
													"water double default 0.0," +
													"cholesterol double default 0.0);");
		database.execSQL("create table IF NOT EXISTS nutritionForDay(id integer primary key," +
													"vitamin_A double default 0.0," +
													"vitamin_C double default 0.0," +
													"vitamin_D double default 0.0," +
													"vitamin_E double default 0.0," +
													"vitamin_B2 double default 0.0," +
													"vitamin_B6 double default 0.0," +
													"vitamin_B9 double default 0.0," +
													"vitamin_B12 double default 0.0," +
													"Ga double default 0.0," +
													"Fe double default 0.0," +
													"Mg double default 0.0," +
													"Ka double default 0.0," +
													"Zn double default 0.0," +
													"fiber double default 0.0," +
													"protein double default 0.0," +
													"caloric double default 0.0," +
													"water double default 0.0," +
													"cholesterol double default 0.0);");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS smartdental.db");
		onCreate(db);
	}
	
    /**
     * 删除数据库
     * @param context
     * @return
     */
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DATABASE_NAME);
    }

}
