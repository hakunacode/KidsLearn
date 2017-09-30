package com.kidslearn;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

public class LoadLayer extends CCLayer {
	protected int mNextScene = -1; 
	public static CCScene scene(int nNextScene)
	{
		CCScene scene = CCScene.node();
		LoadLayer layer = new LoadLayer();		
		layer.mNextScene = nNextScene;
		scene.addChild(layer);
		
		return scene;
	}
	protected LoadLayer() {
		super();
		this.setScale(SimpleGame.mSceneScale);
		mStartTime = System.currentTimeMillis();
		CGSize size = CCDirector.sharedDirector().winSize();
		CCSprite sprite = CCSprite.sprite("bg_title.png");
		sprite.setPosition(size.width/2, size.height/2);
		this.addChild(sprite);
		
		sprite = CCSprite.sprite("txt_loading.png");
		sprite.setPosition(size.width/2, 50);
		this.addChild(sprite);
		this.schedule("update");	
	}
	private static final float TRANSITION_DURATION = 1.2f;
	private long mStartTime = 0;
	private boolean mbTransition = false;
	public void update(float dt) {
		if ( (System.currentTimeMillis()-mStartTime) > 2000 && mbTransition == false) {
			mbTransition = true;
			CCScene scene;
			
			switch(mNextScene) {
			case GameLayer.kGameAlpha:
				CCTextureCache.sharedTextureCache().addImage("alpha.png");
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("alpha.plist");				
				CCTextureCache.sharedTextureCache().addImage("alpha1.png");
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("alpha1.plist");
				CCTextureCache.sharedTextureCache().addImage("alpha2.png");
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("alpha2.plist");

		        scene = GameLayer.scene(GameLayer.kGameAlpha);	         
		        CCDirector.sharedDirector().replaceScene(new CCFadeTransition(TRANSITION_DURATION, scene, new ccColor3B(0,0,0)));
				break;
			case GameLayer.kGameColor:				
				CCTextureCache.sharedTextureCache().addImage("color.png");
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("color.plist");
				CCTextureCache.sharedTextureCache().addImage("color1.png");
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("color1.plist");
				CCTextureCache.sharedTextureCache().addImage("color2.png");
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("color2.plist");
				CCTextureCache.sharedTextureCache().addImage("color3.png");
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("color3.plist");

		        scene = GameLayer.scene(GameLayer.kGameColor);	         
		        CCDirector.sharedDirector().replaceScene(new CCFadeTransition(TRANSITION_DURATION, scene, new ccColor3B(0,0,0)));
				break;
			case GameLayer.kGameNumber:
				CCTextureCache.sharedTextureCache().addImage("number.png");
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("number.plist");

		        scene = GameLayer.scene(GameLayer.kGameNumber);	         
		        CCDirector.sharedDirector().replaceScene(new CCFadeTransition(TRANSITION_DURATION, scene, new ccColor3B(0,0,0)));
				break;
			case GameLayer.kGameShape:
				CCTextureCache.sharedTextureCache().addImage("shape.png");			
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("shape.plist");
				CCTextureCache.sharedTextureCache().addImage("shape1.png");			
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("shape1.plist");
				CCTextureCache.sharedTextureCache().addImage("shape2.png");			
				CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames("shape2.plist");

		        scene = ShapeLayer.scene();	         
		        CCDirector.sharedDirector().replaceScene(new CCFadeTransition(TRANSITION_DURATION, scene, new ccColor3B(0,0,0)));
				break;
			default:
				CCTextureCache.sharedTextureCache().removeTexture("alpha.png");
				CCTextureCache.sharedTextureCache().removeTexture("alpha1.png");
				CCTextureCache.sharedTextureCache().removeTexture("alpha2.png");
				CCTextureCache.sharedTextureCache().removeTexture("color.png");
				CCTextureCache.sharedTextureCache().removeTexture("color1.png");
				CCTextureCache.sharedTextureCache().removeTexture("color2.png");
				CCTextureCache.sharedTextureCache().removeTexture("color3.png");
				CCTextureCache.sharedTextureCache().removeTexture("number.png");
				CCTextureCache.sharedTextureCache().removeTexture("shape.png");		
				CCTextureCache.sharedTextureCache().removeTexture("shape1.png");
				CCTextureCache.sharedTextureCache().removeTexture("shape2.png");
				
				CCSpriteFrameCache.sharedSpriteFrameCache().removeAllSpriteFrames();	
				
		        scene = TitleLayer.scene();	         
		        CCDirector.sharedDirector().replaceScene(new CCFadeTransition(TRANSITION_DURATION, scene, new ccColor3B(0,0,0)));
			}
		}
	}
}
