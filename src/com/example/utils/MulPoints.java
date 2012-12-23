package com.example.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;

/**
 * 分辨多点得触控类
 * @author Administrator
 *
 */
public class MulPoints {
	
	private Map<String,Integer> points ;

	public MulPoints(){
		points = new HashMap<String,Integer>();
	}
	
	public Map getPoints() {
		int i = 0;
		//points.clear();
		while(i<2){
			if(Gdx.input.isTouched(i)){
				float x = Gdx.input.getX(i);
				float y = Gdx.input.getY(i);
				if(x>=0 && x<=Gdx.graphics.getWidth()/2){
					points.put("a", i);
				}else if(x>Gdx.graphics.getWidth()/2){
					points.put("b",i);
				}
			}
		
			i++;
		}
		return points;
	}

	public void setPoints(Map points) {
		this.points = points;
	}
	
	
}
