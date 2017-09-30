package com.kidslearn;

import org.cocos2d.actions.ease.CCEaseElasticInOut;
import org.cocos2d.actions.ease.CCEaseInOut;
import org.cocos2d.actions.instant.CCCallFuncND;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.MotionEvent;

public class GameLayer extends CCLayer {
	public final static int kGameNumber = 0;
	public final static int kGameAlpha = 1;
	public final static int kGameColor = 2;
	public final static int kGameShape = 3;
	
	public int mGameKind = 0;
	public int mPreviewCount = 0;
	public int mCurrentPreviewIndex = 0;
	private CCSprite mSpritePreview;
	public static CCScene scene(int nGameKind)
	{
		CCScene scene = CCScene.node();
		GameLayer layer = new GameLayer();
		layer.setGameKind(nGameKind);
		scene.addChild(layer);		
		return scene;
	}
	protected GameLayer() {
		super();
		this.setScale(SimpleGame.mSceneScale);
		this.setIsTouchEnabled(true);		
		CGSize size = CCDirector.sharedDirector().winSize();
		CCSprite sprite = CCSprite.sprite("bg_game.png");
		sprite.setPosition(size.width/2, size.height/2);
		this.addChild(sprite);

		sprite = CCSprite.sprite("bg_board.png");
		sprite.setPosition(size.width/2, size.height/2-96);
		this.addChild(sprite);		

		sprite = CCSprite.sprite("bg_theme.png");
		sprite.setPosition(size.width/2, size.height/2+336);
		this.addChild(sprite);
		
        CCMenuItem item1 = CCMenuItemImage.item("btn_pause.png", "btn_pause.png", this, "pauseAction");
        CCMenu menu = CCMenu.menu(item1);
        menu.setPosition(CGPoint.make((size.width/2+280), (size.height/2+440)));
        addChild(menu);		
	}
	public void pauseAction(Object sender) {
		((SimpleGame)(CCDirector.theApp)).pauseAction();
	}
	public void setGameKind(int nGameKind) {
		mGameKind = nGameKind;

		switch(mGameKind) {
		case kGameNumber:
			mPreviewCount = 10;
			break;
		case kGameAlpha:
			mPreviewCount = 26;
			break;
		case kGameColor:
			mPreviewCount = 8;
			break;
		case kGameShape:
			mPreviewCount = 8;
			break;
		}
		
		CGSize size = CCDirector.sharedDirector().winSize();
		CCSprite sprite = CCSprite.sprite("txt_kind_"+mGameKind+".png");
		sprite.setPosition(size.width/2, size.height/2+336);
		this.addChild(sprite);

		CCSpriteFrame frame = CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(getPreviewBitmapName(0));
		mSpritePreview = CCSprite.sprite(frame);
		mSpritePreview.setPosition(size.width/2, size.height/2-96);
		this.addChild(mSpritePreview);
	}
	public String getPreviewBitmapName(int nIndex) {
		String strBitmapName = "";
		switch(mGameKind) {
		case kGameNumber:
			strBitmapName = "n"+nIndex+".png";
			break;
		case kGameAlpha:
			strBitmapName = "a"+nIndex+".png";
			break;
		case kGameColor:
			strBitmapName = "c"+nIndex+".png";
			break;
		case kGameShape:
			strBitmapName = "s"+nIndex+".png";
			break;
		default:
			break;
		}
		return strBitmapName;
	}
	private float mTouchPointX;
	private float mTouchPointY;
	private long mTouchTime;
	@Override
    public boolean ccTouchesBegan(MotionEvent event) {	
		mTouchPointX = event.getX();
		mTouchPointY = event.getY();
		mTouchTime = event.getEventTime();
		return true;
	}
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		double fDist = Math.abs(mTouchPointX-event.getX()) 
		;	
		int nScrollWay = (int)(event.getX() - mTouchPointX);
		long nRemainTime = (event.getEventTime()-mTouchTime);
		if (nRemainTime < 1000 && fDist > 50) {			
			if (nScrollWay > 0) { // Scroll To Right
				mCurrentPreviewIndex --;
				if (mCurrentPreviewIndex < 0) {
					mCurrentPreviewIndex = mPreviewCount - 1;
				}
				animatePreviewToRight();					
			} else {
				mCurrentPreviewIndex = (mCurrentPreviewIndex+1)%mPreviewCount;
				animatePreviewToLeft();
			}
		}
		return true;
	}
	public void animatePreviewToLeft() {
		CGSize size = CCDirector.sharedDirector().winSize();
        CCIntervalAction move = CCMoveTo.action(0.4f, CGPoint.make(-size.width/2, size.height/2-96));
        CCIntervalAction move_ease_inout1 = CCEaseElasticInOut.action(move.copy(), 2.0f);
        CCCallFuncND moveCompleteFunc = CCCallFuncND.action(this, "callback3", Float.valueOf(size.width+size.width/2));
        CCSequence action = CCSequence.actions(move_ease_inout1, moveCompleteFunc);
        mSpritePreview.runAction(action);
	}
	public void animatePreviewToRight() {
		CGSize size = CCDirector.sharedDirector().winSize();
        CCIntervalAction move = CCMoveTo.action(0.4f, CGPoint.make(size.width+size.width/2, size.height/2-96));
        CCIntervalAction move_ease_inout1 = CCEaseElasticInOut.action(move.copy(), 2.0f);
        CCCallFuncND moveCompleteFunc = CCCallFuncND.action(this, "callback3", Float.valueOf(-size.width/2));
        CCSequence action = CCSequence.actions(move_ease_inout1, moveCompleteFunc);
        mSpritePreview.runAction(action);
	}
	public void callback3(Object sender, Object data) {
		float x = (Float)data;
		CGPoint pos = mSpritePreview.getPosition();
		mSpritePreview.setPosition(x, pos.y);
		
		CCSpriteFrame frame = CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(getPreviewBitmapName(mCurrentPreviewIndex));
		mSpritePreview.setDisplayFrame(frame);

		CGSize size = CCDirector.sharedDirector().winSize();
		CCIntervalAction move = CCMoveTo.action(0.5f, CGPoint.make(size.width/2, size.height/2-96));
		CCIntervalAction move_ease_inout1 = CCEaseElasticInOut.action(move.copy(), 2.0f);
		mSpritePreview.runAction(move_ease_inout1);
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		return true;
	}
	@Override
	public boolean ccTouchesCancelled(MotionEvent event) {
		mTouchTime = 0;
		return true;
	}
}
