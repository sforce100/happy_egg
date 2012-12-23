package com.example.ballactor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.example.utils.Utils;


public class Walls {
	
	private Body up,down,left,right;
	private float width ,height;
	private World world;
	private int rate=30;
	
	public  Walls(World world){
		this.world=world;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}
	
	
	public void drawwalls(){

        up = Utils.createPolygon(world, width/2, height, width/2, 5.0f, true);
        
        down = Utils.createPolygon(world, width/2, 5.0f, width/2, 5.0f, true);
        
        left = Utils.createPolygon(world, 5.0f, height/2, 5.0f, height/2, true);
        
        right = Utils.createPolygon(world, width-0.5f, height/2, 5.0f, height/2, true);
	}
	

}
