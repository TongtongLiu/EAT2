package com.edu.thss.smartdental.eat;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class ScrollListviewDelete extends ListView implements OnScrollListener{

	private float minDis = 10;
	private float mLastMotionX;
	private float mLastMotionY;
	private boolean isLock = false;
	
	public interface ItemClickListener{
		void onItemClick(int position);
	}
	
  
    @Override  
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
    } 	
	
	private ItemClickListener onItemClickListener;
	
	public void setOnItemClickListener(ItemClickListener onItemClickListener){
		this.onItemClickListener = onItemClickListener;
	}
	
	public ScrollListviewDelete(Context context) {  
        super(context);  
        setOnScrollListener(this);
    }  
	
	public ScrollListviewDelete(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnScrollListener(this);
	}
  
    public ScrollListviewDelete(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        setOnScrollListener(this);
    } 	
	

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (!isIntercept(ev)) {
			DeleteAdapter.ItemDeleteReset();
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		boolean dte = super.dispatchTouchEvent(event);
		if (MotionEvent.ACTION_UP == event.getAction() && !dte) {//onItemClick
			int position = pointToPosition((int)event.getX(), (int)event.getY());
			if (onItemClickListener != null) {
				onItemClickListener.onItemClick(position);
			}
		}
		return dte;
	}

	private boolean isIntercept(MotionEvent ev) {
		float x = ev.getX();
		float y = ev.getY();
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.e("test", "isIntercept  ACTION_DOWN  "+isLock);
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			Log.e("test", "isIntercept  ACTION_MOVE  "+isLock);
			if (!isLock) {
				float deltaX = Math.abs(mLastMotionX - x);
				float deltay = Math.abs(mLastMotionY - y);
				mLastMotionX = x;
				mLastMotionY = y;
				if (deltaX > deltay && deltaX > minDis) {
					isLock = true;
					return false;
				}
			} else {
				return false;
			}
			break;
		case MotionEvent.ACTION_UP:
			Log.e("test", "isIntercept  ACTION_UP  "+isLock);
			isLock = false;
			break;
		case MotionEvent.ACTION_CANCEL:
			Log.e("test", "isIntercept  ACTION_CANCEL  "+isLock);
			isLock = false;
			break;
		}
		return true;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState != OnScrollListener.SCROLL_STATE_IDLE) {
			DeleteAdapter.ItemDeleteReset();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}

}
