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
	 * �õ�����֮��Ļ���
	 * @param px1    ��һ�����X����
	 * @param py1    ��һ�����Y����
	 * @param px2    �ڶ������X����
	 * @param py2    �ڶ������Y����
	 * @return
	 */
	public static double getRad(float px1, float py1, float px2, float py2) {
		//�õ�����X�ľ���  
		float x = px2 - px1;
		//�õ�����Y�ľ���  
		float y = py2 - py1;
		//���б�߳�  
		float Hypotenuse = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		//�õ�����Ƕȵ�����ֵ��ͨ�����Ǻ����еĶ��� ���ڱ�/б��=�Ƕ�����ֵ��  
		float cosAngle = x / Hypotenuse;
		//ͨ�������Ҷ����ȡ����ǶȵĻ���  
		double rad =  Math.acos(cosAngle);
		//��������λ��Y����<ҡ�˵�Y��������Ҫȡ��ֵ-0~-180  
		if (py2 < py1) {
			rad = -rad;
		}
		return rad;
	}
	
	/**
	 *��ȡ�����Ķ� 
	 */
	public static float getDu(float px1, float py1, float px2, float py2){
		//�õ�����X�ľ���  
		float x = px2 - px1;
		//�õ�����Y�ľ���  
		float y = py2 - py1;
		//���б�߳�  
		float Hypotenuse = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		//�õ�����Ƕȵ�����ֵ��ͨ�����Ǻ����еĶ��� ���ڱ�/б��=�Ƕ�����ֵ��  
		float cosAngle = x / Hypotenuse;
		//ͨ�������Ҷ����ȡ����ǶȵĻ���  
		float rad =  (float) Math.acos(cosAngle);
		//��������λ��Y����<ҡ�˵�Y��������Ҫȡ��ֵ-0~-180  
		if (py2 < py1) {
			rad = -rad;
		}
		float du = rad * 180 / 3.14f;
		return du;
	}
	/**
	 * ��ȡ�����ľ���
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
	 * ��������
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
		def.position.set( x / RATE, y / RATE);// ���ø��������
		if(isStatic){
			def.type = BodyType.StaticBody;
		}else{
			def.type = BodyType.DynamicBody;
		}
		Body b = world.createBody(def); //ͨ��world����һ������ 
		PolygonShape p = new PolygonShape();//���������
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
