package com.example.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.OnActionCompleted;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveTo;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BulletAction{
	
	private static final float SPEED = 200.0f;

	private Image image;
	private Action action;
	private float targetX;
	private float targetY;
	private float duration;
	
	public void fire(Stage stage, float x, float y, float angle){
		//y-y1=k(x-x1)  k=tan(angle)  k=(y2-y1)/(x2-x1) s=vt
		double k = Math.tan(Math.toRadians(angle));
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		if(angle>0 && angle<90){
			if(k*(w-x)+y<h){
				Gdx.app.log("fire", "1");
				targetX = w;
				targetY = (float) (k*(w-x)+y);
			}
			else if((h-y)/k+x<w){
				Gdx.app.log("fire", "2");
				targetX = (float) ((h-y)/k+x);
				targetY = h;
			}
		}
		else if(angle>90 && angle<180){
			
			if(k*(0-x)+y<h){
				targetX = 0;
				targetY = (float) (k*(0-x)+y);
			}
			else if((h-y)/k+x>0){
				targetX = (float) ((h-y)/k+x);
				targetY = h;
			}
		}
		else if(angle<0 && angle>-90){
			if(k*(w-x)+y>0){
				targetX = w;
				targetY = (float) (k*(w-x)+y);
			}
			else if((0-y)/k+x<w){
				targetX = (float) ((0-y)/k+x);
				targetY = 0;
			}

		}
		else if(angle<-90 && angle >-180){
			if(k*(0-x)+y>0){
				targetX = 0;
				targetY = (float) (k*(0-x)+y);
			}
			else if((0-y)/k+x>0){
				targetX = (float) ((0-y)/k+x);
				targetY = 0;
			}

		}
		
		duration = (float) (Math.sqrt((x-targetX)*(x-targetX)+(y-targetY)*(y-targetY))/SPEED);
		
		image = new Image(new Texture(Gdx.files.internal("8px.png")));
		image.x = x;
		image.y = y;
	
		Gdx.app.log("fire", "targetx:"+targetX+" targety:"+targetY+" rota:"+angle+" k:"+k);
		action = MoveTo.$(targetX, targetY, duration);
		action.setCompletionListener(new OnActionCompleted() {
			
			@Override
			public void completed(Action action) {
				// TODO Auto-generated method stub
				image.remove();
			}
		});
		
		image.action(action);
		stage.addActor(image);
	}
	
}
