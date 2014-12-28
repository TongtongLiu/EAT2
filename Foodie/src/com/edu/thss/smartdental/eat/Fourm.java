package com.edu.thss.smartdental.eat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class Fourm extends Activity{
	private WebView fourmView;
	private Button button;
	private TextView textView;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fourm);
		
		getActionBar().hide();
		//new Thread(runnable).start();
		
		fourmView = (WebView) findViewById(R.id.fourmView);
		fourmView.getSettings().setJavaScriptEnabled(true);
		fourmView.getSettings().setDatabaseEnabled(true);
		fourmView.getSettings().setDomStorageEnabled(true);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
			fourmView.getSettings().setDatabasePath("/data/data/" + fourmView.getContext().getPackageName() + "/databases/");
		}
		fourmView.loadUrl("file:///android_asset/index.html");
	};
	
	//新线程的Handler，得到信息后可以刷新界面
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String val = data.getString("value");
			Log.i("mylog", "result ----->" + val);
		}
	};
	
	//子进程，负责发送post请求
	//因为google在新的android框架中不允许在主线程中进行网络操作
	//发送完请求得到回复后需要把信息发给Handler来刷新界面
	Runnable runnable = new Runnable(){
		@Override
		public void run(){
			//String response = postRequest("http://www.baidu.com");
			String response = postRequest(
					"http://59.66.137.62:8000/mergeJson",
					getIntent().getExtras().getSerializable("name").toString(),
					getIntent().getExtras().getSerializable("topic").toString(),
					getIntent().getExtras().getSerializable("text").toString());
			
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("value", response);
			msg.setData(data);
			handler.sendMessage(msg);
		}
	};
	
	//向指定的url发送post请求
	public static String postRequest(String url, String username, String topic, String content){
		try{
			//创建json对象
			JSONObject jsonObj = new JSONObject();
			//要加入的数据，可以根据需要添加，或者方到函数参数里面，再用put方法加入
			
			Date date = new Date();
			SimpleDateFormat spdate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			jsonObj.put("type", "food");
			jsonObj.put("title", topic);
			jsonObj.put("author", username);
			jsonObj.put("img", "img/avatar.png");
			jsonObj.put("favor", "0");
			jsonObj.put("content", content);
			jsonObj.put("time", spdate.format(date));
			
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			
			//发送请求并取得回复
			HttpResponse response = client.execute(httpPost);
			
			//HttpResponse不能直接显示文字数据，需要调用getEntity()得到HttpEntity
			//再调用HttpEntity的getContent()来取得InputStream
			//然后通过BufferedReader循环读取来取得文本形式的回复数据
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line;
			String result = "";
			while((line = reader.readLine()) != null){
				result += line;
			}
			return result;
		}
		catch(Exception e){
			e.printStackTrace();
			return "ERROR!";
		}
	}
}