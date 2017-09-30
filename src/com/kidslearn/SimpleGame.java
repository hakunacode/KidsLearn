package com.kidslearn;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class SimpleGame extends Activity
{
	protected CCGLSurfaceView _glSurfaceView;
	public static float mSceneScale = 1.0f;
	public static CGRect mWinRect = null;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		CGRect rt = getAppFrameRect(0.66666666666666666666666666666667f);
		mWinRect = rt;
		mSceneScale = CGRect.height(rt)/960.0f;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		_glSurfaceView = new CCGLSurfaceView(this);
		
		setContentView(_glSurfaceView);		
	}
    private CGRect getAppFrameRect(float targetRatio)
    {
    	 WindowManager w = getWindowManager();
    	 CGSize size = CGSize.make(w.getDefaultDisplay().getWidth(), w.getDefaultDisplay().getHeight());
         
         float currentRation = size.width / size.height;
         CGSize newSize = CGSize.make(size.width, size.height);
         CGPoint offset = CGPoint.make(0, 0);
         
         if (currentRation > targetRatio)
         {
        	 newSize.width = targetRatio * size.height;
        	 offset.x = (size.width - newSize.width) / 2;
         }
         	else if (currentRation < targetRatio)
         {
         		newSize.height = size.width / targetRatio;
         		offset.y = (size.height - newSize.height) / 2;
         }
         
         CGRect rect = CGRect.make(offset, newSize);
         
         return rect;
    }
	@Override
	public void onStart()
	{
		super.onStart();
		
		CCDirector.sharedDirector().attachInView(_glSurfaceView);
		
		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);
		
		CCDirector.sharedDirector().setDisplayFPS(false);
		
		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);
//		CCDirector.sharedDirector().setScreenSize(640, 960);
		CCScene scene = TitleLayer.scene();
		CCDirector.sharedDirector().runWithScene(scene);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		CCDirector.sharedDirector().pause();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		CCDirector.sharedDirector().resume();
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		
		CCDirector.sharedDirector().end();
	}
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) { 
	        Dialog dlg = new AlertDialog.Builder(CCDirector.theApp)
	        .setMessage("Do you want go to title screen?")
	        .setPositiveButton("YES" , new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) { 
	    	        CCScene scene = LoadLayer.scene(-1);	         
	    	        CCDirector.sharedDirector().replaceScene(new CCFadeTransition(1.2f, scene, new ccColor3B(0,0,0)));
	            }
	        })
	        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            }
	        })
	        .create();
	        dlg.show();		
		}
	};
	public void pauseAction() {
		mHandler.sendEmptyMessage(0);
	}
}