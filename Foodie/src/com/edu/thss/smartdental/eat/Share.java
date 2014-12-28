package com.edu.thss.smartdental.eat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Share extends Activity{
	private ImageButton button;
	private EditText shareText;
	private EditText usernameText;
	private EditText topicText;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share);
		
		getActionBar().hide();
		String score = getIntent().getExtras().getSerializable("score").toString();

		usernameText = (EditText) findViewById(R.id.usernameText);
		
		topicText = (EditText) findViewById(R.id.topicText);
		topicText.setText("吃货挑战，我得了" + score + "分，谁敢来战？");
		
		shareText = (EditText) findViewById(R.id.shareText);
		
		String content = "我刚刚吃了";
		String foodSizeStr = getIntent().getExtras().getSerializable("foodSize").toString();
		int foodSize = Integer.valueOf(foodSizeStr);
		for(int i = 0; i < foodSize; i++){
			content += getIntent().getExtras().getSerializable("food" + i).toString();
			content += "，";
		}
		content += "跑出了" + score + "分，谁能超过我？";
		shareText.setText(content);
		
		button = (ImageButton) findViewById(R.id.shareBtn);
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				Intent intent = new Intent("fourm");
				intent.putExtra("name", usernameText.getText().toString());
				intent.putExtra("topic", topicText.getText().toString());
				intent.putExtra("text", shareText.getText().toString());
				startActivity(intent);
			}
		});
	};	
}