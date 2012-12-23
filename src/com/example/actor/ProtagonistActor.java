package com.example.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.RotateTo;
import com.example.model.BulletAction;
import com.example.model.Protagonist;
import com.example.utils.Utils;



public class ProtagonistActor extends Actor{
	
	private TextureRegion paTexture;
	private TextureRegion paTexture2;
	private TextureRegion paTexture3;
	private TextureRegion gunTexture;
	private World world;
	private Body b;
	private int rate=30;
	/*private float x1,y1,x2,y2,dxy;
    float px,py;
    final int p=400;*/
	private float stateTime;
	private Texture texture1;
    private Texture texture2;
    private Animation animation;
    private TextureRegion[] walksFrame;
    private Protagonist pg;//角色
    private RotateTo roll;
    
	private float gunWidth,gunHeight,originX,originY;
	private float paWidth,paHeight;
	private BulletAction bullet;
	private float gunX,gunY;
	
	public ProtagonistActor(String name,World world){
		super(name);
		this.world=world;
		paTexture = new TextureRegion(new Texture(Gdx.files.internal("11.png")));
		paTexture2 = new TextureRegion(new Texture(Gdx.files.internal("13.png")));
		paTexture3 = new TextureRegion(new Texture(Gdx.files.internal("14.png")));
        gunTexture = new TextureRegion(new Texture(Gdx.files.internal("12.png")));
        this.height = paTexture.getRegionHeight();
        this.width = paTexture.getRegionWidth();
        paHeight = paTexture.getRegionHeight();
        paWidth = paTexture.getRegionWidth();
        gunHeight = gunTexture.getRegionHeight();
        gunWidth = gunTexture.getRegionWidth();
        originX = gunWidth/2;
        originY = gunHeight/2;
        b = Utils.createPolygon(world, Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/2, paWidth/2f, paHeight/2f, false);

        //创建角色
        pg = new Protagonist();
        
        
      /*  
		texture1 = new Texture(Gdx.files.internal("1.png"));
        texture2 = new Texture(Gdx.files.internal("2.png"));
        this.height = texture1.getHeight();
        this.width = texture1.getWidth();
        TextureRegion region1;
        TextureRegion region2;
        region1 = new TextureRegion(texture1);
        region2 = new TextureRegion(texture2);
        walksFrame = new TextureRegion[30];
        for (int i = 0; i < 30; i++) {
            if (i % 2 == 0) {
                walksFrame[i] = region1;
            } else {
                walksFrame[i] = region2;
            }
        }
        
        animation = new Animation(0.25f, walksFrame);
		
		*/
		
		
	}
	
public void changeposition(){
		
		Actor actor = this.parent.findActor("pa"); 
		float x1 = b.getPosition().x;
        float y1 =b.getPosition().y;
        actor.x=x1*rate-this.width/2;
        actor.y=y1*rate-this.height/2;
       // Gdx.app.log("actor",actor.x+" "+actor.y );
		
	}
	
	
	public void run(){
//		float run = 0;
//		if(pg.isRun()){
//			switch (pg.getDirect()) {
//			case RIGHT:
//				run = Protagonist.SPEED;
//				break;
//			case RIGHT_UP:
//				run = Protagonist.SPEED;
//				break;
//			case LEFT_UP:
//				run = -Protagonist.SPEED;
//				break;
//			case LEFT:
//				run = -Protagonist.SPEED;
//				break;
//			default:
//				run = 0f ;
//				break;
//			}
//			
//			this.x += run;
//			if(jumping){
//				Vector2 vForce = new Vector2(run*10,0);
//				b.applyForce(vForce, b.getWorldCenter());
//			}else{
//				b.setAwake(false);
//				b.setTransform((this.x+64)/Utils.RATE, (this.y+64)/Utils.RATE, 0);
//			}
//		
//			Gdx.app.log("run",jumping+" "+ (this.x+64)/Utils.RATE+"  "+ (this.y+64)/Utils.RATE);
//		}
		
		float px = 0f ;
		float py = 0f ;
		if(pg.isRun()){
			b.setTransform((this.x+64)/Utils.RATE, (this.y+64)/Utils.RATE, 0);
			b.setActive(true);
			switch (pg.getDirect()) {
			case RIGHT:
				px = Protagonist.SPEED;
				break;
			case RIGHT_UP:
				px = Protagonist.SPEED;
				break;
			case LEFT_UP:
				px = -Protagonist.SPEED;
				break;
			case LEFT:
				px = -Protagonist.SPEED;
				break;
			default:
				px = 0f ;
				py = 0f ;
				break;
			}
		}else if(!jumping){
			b.setActive(false);
		}
		
		Vector2 vForce = new Vector2(px,py);
		b.applyForce(vForce, b.getWorldCenter());
	}
	
	/**
	 * 判断是否跳
	 */
	private float jy ;//刚体跳过程中的y坐标
	private boolean jumping=true;//是否正在条
	private int n;//第N个触控点
	public void isJump(){
		Actor actor = this.parent.findActor("pa"); 
		if(Gdx.input.isTouched(0) && !jumping){
			
			int x = Gdx.input.getX(n);
			int y = Gdx.graphics.getHeight() - Gdx.input.getY(n);
			if(x>Gdx.graphics.getWidth() - 64 && x<Gdx.graphics.getWidth() 
					&& y>0 && y<64){
				jy = actor.y;
				jumping = true;
				pg.setJump(true);
				b.setActive(true);
			}
		}else if(Math.abs(jy - actor.y)<0.005f){
			Gdx.app.log("jump", "ok");
			jumping = false;
		}else{
			Gdx.app.log("jump", "wait"+(jy - actor.y));
			jumping = true;
			jy = actor.y;
		}		
		
	}
	
	public void fire(){
		if(Gdx.input.justTouched()){
			int x = Gdx.input.getX(n);
			int y = Gdx.graphics.getHeight() - Gdx.input.getY(n);
			if(x>Gdx.graphics.getWidth() - 64 && x<Gdx.graphics.getWidth() 
					&& y>64 && y<128){
				pg.setFire(true);
			}
		}
	}
	

	public void jump(){
		isJump();
		if(pg.isJump()){
			Gdx.app.log("isJump", "jump");
			pg.setJump(false);
			Vector2 vForce = new Vector2(0,Protagonist.JUMPH);
			b.applyForce(vForce, b.getWorldCenter());
		}
	}
	
	public void paLogic(int n){
		this.n = n;
		fire();
		run();
		jump();
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		switch (pg.getDirect()) {
		case RIGHT:
			drawR(batch,paTexture);
			break;
		case RIGHT_UP:
			drawR(batch,paTexture2);
			break;
		case UP:
			drawR(batch,paTexture3);
			break;
		case LEFT_UP:
			drawL(batch,paTexture2);
			break;
		case LEFT:
			drawL(batch,paTexture);
			break;
		case RIGHT_DOWN:
			drawR(batch,paTexture);
			break;
		case LEFT_DOWN:
			drawL(batch,paTexture);
			break;
		case DOWN:
			drawR(batch,paTexture);
		default:		
			break;
		}
    
		//开炮
		if(pg.isFire()){
			Gdx.app.log("fire", "fire!!"+"rota:"+pg.getGunRota()+
					" x:"+(gunX+originX+originX*(float)Math.cos(Math.toRadians(pg.getGunRota())))+
					" y:"+(gunY+originY+originY*(float)Math.sin(Math.toRadians(pg.getGunRota()))));
			bullet = new BulletAction();
			bullet.fire(this.getStage(), 
					gunX+originX+originX*(float)Math.cos(Math.toRadians(pg.getGunRota())), 
					gunY+originY+originX*(float)Math.sin(Math.toRadians(pg.getGunRota())),
					pg.getGunRota());
			pg.setFire(false);
		}
	}
	
	private void drawR(SpriteBatch batch,TextureRegion pa){
		batch.draw(pa , this.x , this.y , 0 , 0 , 
				pa.getRegionWidth(), pa.getRegionHeight(), 
				1, 1, 0);
		gunX =  this.x+gunWidth/4;
		gunY = this.y+pa.getRegionHeight()/4;
		batch.draw(gunTexture, gunX, gunY,				
				originX, originY, 
				gunWidth, gunHeight, 
				1, 1, pg.getGunRota());
	}

	private void drawL(SpriteBatch batch,TextureRegion pa){
		batch.draw(pa , this.x+pa.getRegionWidth() , this.y , 0 , 0 , 
				pa.getRegionWidth(), pa.getRegionHeight(), 
				-1, 1, 0);
		gunX =  this.x-gunWidth/4;
		gunY = this.y+pa.getRegionHeight()/4;
		batch.draw(gunTexture, gunX, gunY,
				originX, originY,  
				gunWidth, gunHeight, 
				1, -1, pg.getGunRota());
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return this;
	}

	public Body getB() {
		return b;
	}

	public void setB(Body b) {
		this.b = b;
	}

	public Protagonist getPg() {
		return pg;
	}

	public void setPg(Protagonist pg) {
		this.pg = pg;
	}
	
	
}
