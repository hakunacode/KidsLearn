package com.kidslearn;

import java.util.ArrayList;
import java.util.Random;

import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.content.Context;
import android.view.MotionEvent;

public class TitleLayer extends CCLayer
{
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		CCLayer layer = new TitleLayer();		
		scene.addChild(layer);
		
		return scene;
	}

	private static final float TRANSITION_DURATION = 1.2f;
	protected TitleLayer()
	{
		super();
		this.setScale(SimpleGame.mSceneScale);
		CGSize size = CCDirector.sharedDirector().winSize();
		CCSprite sprite = CCSprite.sprite("bg_title.png");
		sprite.setPosition(size.width/2, size.height/2);
		this.addChild(sprite);
		
		CCColorLayer colorLayer = CCColorLayer.node(new ccColor4B(0, 0, 0, 150));// new ColorLayer(new CCColor4B(0x33, 0x33, 0x33, 0x33), 100, 100);
		colorLayer.setScale(1/SimpleGame.mSceneScale);
		this.addChild(colorLayer);
		
        CCMenuItem item1 = CCMenuItemImage.item("btn_numbers.png", "btn_numbers1.png", this, "numberAction");
        CCMenuItem item2 = CCMenuItemImage.item("btn_alphabet.png", "btn_alphabet1.png", this, "alphabetAction");
        CCMenuItem item3 = CCMenuItemImage.item("btn_colors.png", "btn_colors1.png", this, "colorAction");
        CCMenuItem item4 = CCMenuItemImage.item("btn_shapes.png", "btn_shapes1.png", this, "shapeAction");
        CCMenu menu = CCMenu.menu(item1, item2, item3, item4);        
        menu.alignItemsVertically(20);
        addChild(menu);		
	}	
	public void alphabetAction(Object sender) {
        CCScene scene = LoadLayer.scene(GameLayer.kGameAlpha);     
        CCDirector.sharedDirector().replaceScene(new CCFadeTransition(TRANSITION_DURATION, scene, new ccColor3B(0,0,0)));
	}
	public void colorAction(Object sender) {
        CCScene scene = LoadLayer.scene(GameLayer.kGameColor);     
        CCDirector.sharedDirector().replaceScene(new CCFadeTransition(TRANSITION_DURATION, scene, new ccColor3B(0,0,0)));
	}
	public void shapeAction(Object sender) {
        CCScene scene = LoadLayer.scene(GameLayer.kGameShape);     
        CCDirector.sharedDirector().replaceScene(new CCFadeTransition(TRANSITION_DURATION, scene, new ccColor3B(0,0,0)));
	}
	public void numberAction(Object sender) {		
        CCScene scene = LoadLayer.scene(GameLayer.kGameNumber);     
        CCDirector.sharedDirector().replaceScene(new CCFadeTransition(TRANSITION_DURATION, scene, new ccColor3B(0,0,0)));
	}	
}
