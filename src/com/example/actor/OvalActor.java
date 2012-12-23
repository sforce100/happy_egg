package com.example.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.example.model.BulletAction;
import com.example.model.Protagonist;
import com.example.utils.Utils;

public class OvalActor extends Actor{

	private Texture BigOval;
	private Texture SmallOval;
	private TextureRegion jbutton,fbutton;
	private float smallx,smally;
	private static final float bigx = 30.0f , bigy = 30.0f ;
	private boolean touchValid = false;
	private int width;
	private int heigh;
	private float powerx;
	private float powery;
	private Pixmap pixmap;
	private float smallCenterX , smallCenterY , smallCenterR ;
	private float BigCenterX , BigCenterY , BigCenterR ;
	private float touchX,touchY;
	private boolean jumping = false;
	//角色运动方向,枪口方向,运动速度
	private Protagonist protagonist;
	
	public OvalActor(Protagonist pt){
		BigOval = new Texture(Gdx.files.internal("1.png"));
		SmallOval = new Texture(Gdx.files.internal("2.png"));
		jbutton = new TextureRegion(new Texture(Gdx.files.internal("1.png")));
		fbutton = new TextureRegion(new Texture(Gdx.files.internal("1.png")));
		
		height=Gdx.graphics.getHeight();
		width=Gdx.graphics.getWidth();
		//做了一个简单的适配，powerx和powery分别当前设备分辨率的权重，以现在主流的800*480为基准
		powerx=Gdx.graphics.getWidth()/800f;
		powery=Gdx.graphics.getHeight()/480f;
		
		smallx = (BigOval.getWidth() - SmallOval.getWidth())/2 + bigx;
		smally = smallx;
		
		//转化摇杆信息
		smallCenterX = smallx + SmallOval.getWidth()/2;
		smallCenterY = smallCenterX;
		smallCenterR = SmallOval.getWidth()/2;
		BigCenterX = BigOval.getWidth()/2 + bigx;
		BigCenterY = BigCenterX;
		BigCenterR = BigOval.getWidth();
		
		protagonist = pt;
	}

	
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// x y w h
		batch.draw(BigOval, bigx, bigy,BigOval.getWidth(),BigOval.getHeight());
		batch.draw(SmallOval,smallx,smally,SmallOval.getWidth(),SmallOval.getHeight());
		batch.draw(jbutton, Gdx.graphics.getWidth() - 64, 0, 0, 0, 64, 64, 1, 1, 0);
		batch.draw(fbutton,  Gdx.graphics.getWidth() - 64, 64, 0, 0, 64, 64, 1, 1, 0);
		//System.out.println("x:"+smallx+" ,y:"+smally);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return this;
	}


	/**
	 * 触屏事件监听
	 */
	public boolean onTouchEvent(int n) {
		
		touchX = Gdx.input.getX(n);
		touchY = Gdx.graphics.getHeight() - Gdx.input.getY(n);
		
		if(touchX>bigx && touchX<BigOval.getWidth()+bigx && touchY>bigy && touchY<BigOval.getHeight()+bigy){
			
			touchValid = true;
		}
		//当用户手指抬起，应该恢复小圆到初始位置
		if (!Gdx.input.isTouched(n)) {
			smallx = (BigOval.getWidth() - SmallOval.getWidth())/2 + bigx;
			smally = smallx;
			protagonist.setRun(false);
			touchValid = false;
			Gdx.app.log("touch", "up");
		} else if(touchValid){
			
			//判断用户点击的位置是否在大圆内
			if (Math.sqrt(Math.pow((BigCenterX - touchX), 2) + Math.pow((BigCenterY - touchY), 2)) <= BigCenterR) {
				//让小圆跟随用户触点位置移动
				smallx = touchX;
				smally = touchY;
			} else {
				setSmallCircleXY(BigCenterX, BigCenterY, BigCenterR, Utils.getRad(BigCenterX, BigCenterY, touchX, touchY));
			}
	
			protagonist.calculate(BigCenterX, BigCenterY, touchX, touchY);
			protagonist.setRun(true);
			protagonist.setGunRota(Utils.getDu(BigCenterX, BigCenterY, touchX, touchY));
		}
		return true;
	}

	/** 
	 * 小圆针对于大圆做圆周运动时，设置小圆中心点的坐标位置
	 * @param centerX 
	 *            围绕的圆形(大圆)中心点X坐标
	 * @param centerY 
	 *            围绕的圆形(大圆)中心点Y坐标
	 * @param R
	 * 			     围绕的圆形(大圆)半径
	 * @param rad 
	 *            旋转的弧度 
	 */
	public void setSmallCircleXY(float centerX, float centerY, float R, double rad) {
		//获取圆周运动的X坐标   
		smallCenterX = (float) (R * Math.cos(rad)) + centerX;
		//获取圆周运动的Y坐标  
		smallCenterY = (float) (R * Math.sin(rad)) + centerY;
		//转化为摇杆坐标
		smallx = smallCenterX - SmallOval.getWidth()/2;
		smally = smallCenterY - SmallOval.getWidth()/2;
	}

	public Protagonist getProtagonist() {
		return protagonist;
	}

	public void setProtagonist(Protagonist protagonist) {
		this.protagonist = protagonist;
	}
	
	
}
