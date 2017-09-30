package com.kidslearn;

import org.cocos2d.actions.ease.CCEaseElasticInOut;
import org.cocos2d.actions.instant.CCCallFuncND;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

public class ShapeLayer extends CCLayer {
	CCSprite mPreviewSprite;
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		CCLayer layer = new ShapeLayer();
		scene.addChild(layer);		
		return scene;
	}
	protected ShapeLayer() {
		super();
		this.setScale(SimpleGame.mSceneScale);
		CGSize size = CCDirector.sharedDirector().winSize();
		CCSprite sprite = CCSprite.sprite("bg_game.png");
		sprite.setPosition(size.width/2, size.height/2);
		this.addChild(sprite);

		sprite = CCSprite.sprite(CCSpriteFrameCache.spriteFrameByName("shapesback.png"));
		sprite.setPosition(size.width/2, size.height/2);
		this.addChild(sprite);
		
		mPreviewSprite = CCSprite.sprite(CCSpriteFrameCache.spriteFrameByName("s0.png"));
		mPreviewSprite.setPosition(size.width/2, size.height/2);
		mPreviewSprite.setScale(0);
		this.addChild(mPreviewSprite);
		
		CCMenuItem item1 = CCMenuItemImage.item("btn_pause.png", "btn_pause.png", this, "pauseAction");
        CCMenu menu = CCMenu.menu(item1);
        menu.setPosition(CGPoint.make((size.width/2+280), (size.height/2)));
        addChild(menu);
        
        item1 = getMenuItemFromSpriteFrame("b1.png", "b1.png", "selectAction");
		item1.setTag(0);
        menu = CCMenu.menu(item1);
        menu.setPosition(CGPoint.make((size.width/2-160), (size.height/2+420)));
        addChild(menu);

		item1 = getMenuItemFromSpriteFrame("b2.png", "b2.png", "selectAction");
		item1.setTag(1);
        menu = CCMenu.menu(item1);
        menu.setPosition(CGPoint.make((size.width/2+160), (size.height/2+420)));
        addChild(menu);

		item1 = getMenuItemFromSpriteFrame("b3.png", "b3.png", "selectAction");
		item1.setTag(2);
        menu = CCMenu.menu(item1);
        menu.setPosition(CGPoint.make((size.width/2-160), (size.height/2+320)));
        addChild(menu);

		item1 = getMenuItemFromSpriteFrame("b4.png", "b4.png", "selectAction");
		item1.setTag(3);
        menu = CCMenu.menu(item1);
        menu.setPosition(CGPoint.make((size.width/2+160), (size.height/2+320)));
        addChild(menu);

		item1 = getMenuItemFromSpriteFrame("b5.png", "b5.png", "selectAction");
		item1.setTag(4);
        menu = CCMenu.menu(item1);
        menu.setPosition(CGPoint.make((size.width/2-160), (size.height/2-320)));
        addChild(menu);

		item1 = getMenuItemFromSpriteFrame("b6.png", "b6.png", "selectAction");
		item1.setTag(5);
        menu = CCMenu.menu(item1);
        menu.setPosition(CGPoint.make((size.width/2+160), (size.height/2-320)));
        addChild(menu);

		item1 = getMenuItemFromSpriteFrame("b7.png", "b7.png", "selectAction");
		item1.setTag(6);
        menu = CCMenu.menu(item1);
        menu.setPosition(CGPoint.make((size.width/2-160), (size.height/2-420)));
        addChild(menu);

		item1 = getMenuItemFromSpriteFrame("b8.png", "b8.png", "selectAction");
		item1.setTag(7);
        menu = CCMenu.menu(item1);
        menu.setPosition(CGPoint.make((size.width/2+160), (size.height/2-420)));
        addChild(menu);
	}
	protected CCMenuItemSprite getMenuItemFromSpriteFrame(String strNormalSpriteFrame, String strFocusSpriteFrame, String selector) {
        return CCMenuItemSprite.item(CCSprite.sprite(CCSpriteFrameCache.spriteFrameByName(strNormalSpriteFrame)),
				CCSprite.sprite(CCSpriteFrameCache.spriteFrameByName(strFocusSpriteFrame)),
				this, selector);
		
	}
	public void pauseAction(Object sender) {
		((SimpleGame)(CCDirector.theApp)).pauseAction();
	}
	public void selectAction(Object sender) {	
		int nTag = ((CCMenuItemSprite)sender).getTag();
		CGSize size = CCDirector.sharedDirector().winSize();
        CCIntervalAction scaleToZero = CCScaleTo.action(0.5f, 0);
        CCIntervalAction scale_ease_inout1 = CCEaseElasticInOut.action(scaleToZero.copy(), 2.0f);
        CCCallFuncND moveCompleteFunc = CCCallFuncND.action(this, "callback3", Integer.valueOf(nTag));        
        CCIntervalAction scaleToFull = CCScaleTo.action(0.5f, 1);
        CCIntervalAction scale_ease_inout2 = CCEaseElasticInOut.action(scaleToFull.copy(), 2.0f);
        CCSequence action = CCSequence.actions(scale_ease_inout1, moveCompleteFunc, scale_ease_inout2);
        mPreviewSprite.runAction(action);		
	}
	public void callback3(Object sender, Object data) {
		int nTag = (Integer)data;
		String strBitmapName = "s"+nTag+".png";
		mPreviewSprite.setDisplayFrame(CCSpriteFrameCache.spriteFrameByName(strBitmapName));
	}
}
