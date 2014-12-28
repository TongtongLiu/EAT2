package com.edu.thss.smartdental.eat;

import java.util.ArrayList;
import java.util.List;

import com.edu.thss.smartdental.eat.ScrollListviewDelete.ItemClickListener;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class ScrollerDeleteActivity extends FragmentActivity implements ItemClickListener {

	private Toast mToast;
	private ScrollListviewDelete listviewDelete;
	private DeleteAdapter adapter;
	private String[] datas = { "1","2","3","4" };
	private List<String> listDatas = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_scroller_delete);
		int len = datas.length;
		for (int i = 0; i < len; i++) {
			listDatas.add(datas[i]);
		}
		listviewDelete = (ScrollListviewDelete) findViewById(android.R.id.list);
		//adapter = new DeleteAdapter(this, listDatas);
		//listviewDelete.setAdapter(adapter);
		//listviewDelete.setOnItemClickListener((com.caldroidsample.ScrollListviewDelete.ItemClickListener) this);
	}


	public void showInfo(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public void onItemClick(int position) {
		showInfo("onItemClick: "+adapter.getItem(position));
	}

}
