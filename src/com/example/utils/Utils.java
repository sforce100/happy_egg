package com.example.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Utils {
	public static final int RATE = 30;

	/**
	 * 得到两点之间的弧度
	 * @param px1    第一个点的X坐标
	 * @param py1    第一个点的Y坐标
	 * @param px2    第二个点的X坐标
	 * @param py2    第二个点的Y坐标
	 * @return
	 */
	public static double getRad(float px1, float py1, float px2, float py2) {
		//得到两点X的距离  
		float x = px2 - px1;
		//得到两点Y的距离  
		float y = py2 - py1;
		//算出斜边长  
		float Hypotenuse = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		//得到这个角度的余弦值（通过三角函数中的定理 ：邻边/斜边=角度余弦值）  
		float cosAngle = x / Hypotenuse;
		//通过反余弦定理获取到其角度的弧度  
		double rad =  Math.acos(cosAngle);
		//当触屏的位置Y坐标<摇杆的Y坐标我们要取反值-0~-180  
		if (py2 < py1) {
			rad = -rad;
		}
		return rad;
	}
	
	/**
	 *获取两点间的度 
	 */
	public static float getDu(float px1, float py1, float px2, float py2){
		//得到两点X的距离  
		float x = px2 - px1;
		//得到两点Y的距离  
		float y = py2 - py1;
		//算出斜边长  
		float Hypotenuse = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		//得到这个角度的余弦值（通过三角函数中的定理 ：邻边/斜边=角度余弦值）  
		float cosAngle = x / Hypotenuse;
		//通过反余弦定理获取到其角度的弧度  
		float rad =  (float) Math.acos(cosAngle);
		//当触屏的位置Y坐标<摇杆的Y坐标我们要取反值-0~-180  
		if (py2 < py1) {
			rad = -rad;
		}
		float du = rad * 180 / 3.14f;
		return du;
	}
	/**
	 * 获取两点间的距离
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double getDistance(float x1, float y1, float x2, float y2){
		return Math.sqrt((x1+x2)*(x1+x2)+(y1+y2)*(y1+y2));
	}
	
	/**
	 * 创建物体
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isStatic
	 * @return
	 */
	public static Body createPolygon(World world , float x, float y, float width, float height,
			boolean isStatic) {

		BodyDef def = new BodyDef();
		def.position.set( x / RATE, y / RATE);// 设置刚体的坐标
		if(isStatic){
			def.type = BodyType.StaticBody;
		}else{
			def.type = BodyType.DynamicBody;
		}
		Body b = world.createBody(def); //通过world创建一个物体 
		PolygonShape p = new PolygonShape();//创建多边形
		p.setAsBox(width / RATE, height / RATE);
		//b.createFixture(p, 1f);
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = p;
		fixDef.density = 1.0f;
		fixDef.friction = 0.0f;
		fixDef.restitution=0.0f;
		b.createFixture(fixDef);
		
		return b;
	}
	
}
