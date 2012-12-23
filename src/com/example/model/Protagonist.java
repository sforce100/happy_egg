package com.example.model;

import com.badlogic.gdx.Gdx;
import com.example.ballactor.Direct;
import com.example.utils.Utils;

/**
 * 角色属性
 * @author Administrator
 *
 */
public class Protagonist {

	public static final float SPEED = 20.0f;
	public static final float JUMPH = 4000.0f;
	private Direct direct = Direct.RIGHT;
	private boolean isDead = false;
	private boolean isJump = false;
	private boolean isSquat = false;
	private boolean isRun = false;
	private boolean isFire = false;
	private float gunRota = 1.0f;//枪的旋转角度
	public Protagonist(){
		
	}
	
	/**
	 * 滑动摇杆时,计算人物运动
	 * @param BigCenterX
	 * @param BigCenterY
	 * @param smallCenterX
	 * @param smallCenterY
	 */
	public void calculate(float BigCenterX, float BigCenterY, float smallCenterX, float smallCenterY){
		//通过两圆心距离计算速度
		//setSpeed((float) Utils.getDistance(BigCenterX, BigCenterY, smallCenterX, smallCenterY)/3);
		//计算摇杆角度
		double rad = Utils.getDu(BigCenterX, BigCenterY, smallCenterX, smallCenterY);
		if(rad>=-30 && rad<=30){
			direct = Direct.RIGHT;
		}else if(rad>30 && rad<=60){
			direct = Direct.RIGHT_UP;
		}else if(rad>60 && rad<=120){
			direct = Direct.UP;
		}else if(rad>120 && rad<=150){
			direct = Direct.LEFT_UP;
		}else if((rad>150 && rad<=180)||(rad<-150 && rad>=-180)){
			direct = Direct.LEFT;
		}else if(rad<-120 && rad>=-150){
			direct = Direct.LEFT_DOWN;
		}else if(rad<-60 && rad>=-120){
			direct = Direct.DOWN;
		}else if(rad<-30 && rad>=-60){
			direct = Direct.RIGHT_DOWN;
		}
		
		Gdx.app.log("dir", direct.toString()+"   rad:"+rad);
	}

	public Direct getDirect() {
		return direct;
	}
	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	public void setDirect(Direct d) {
		direct = d;
	}
	public boolean isDead() {
		return isDead;
	}
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	public boolean isJump() {
		return isJump;
	}
	public void setJump(boolean isJump) {
		this.isJump = isJump;
	}
	public boolean isSquat() {
		return isSquat;
	}
	public void setSquat(boolean isSquat) {
		this.isSquat = isSquat;
	}

	public float getGunRota() {
		return gunRota;
	}

	public void setGunRota(float gunRota) {
		this.gunRota = gunRota;
	}

	public boolean isFire() {
		return isFire;
	}

	public void setFire(boolean isFire) {
		this.isFire = isFire;
	}

	
	
	
}
