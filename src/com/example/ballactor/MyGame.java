package com.example.ballactor;


import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RotateTo;
import com.example.actor.OvalActor;
import com.example.actor.ProtagonistActor;
import com.example.model.Protagonist;
import com.example.utils.MulPoints;


public class MyGame implements ApplicationListener,InputProcessor{

	protected OrthographicCamera camera; 
    private World world;
    private Walls walls;
    private Stage stage; 
    private ProtagonistActor pa;
    private float x1,y1,x2,y2,dxy;
    float px,py;
    final int p=300;
    
    private RotateTo roll;
    private Protagonist pg;
    protected Box2DDebugRenderer renderer; // 测试用绘制器 
    
    //UI
    private OvalActor ovalActor;
    private Texture butTexture;
    private Texture fireTexture;
    private NinePatch butNP;
    private NinePatch fireNP;
    
    //获得多点触控
    private MulPoints mp;
    
	@Override
	public void create() {
		
		 mp = new MulPoints();
		
		 camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); 
	     camera.position.set(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2, 0);
	     renderer = new Box2DDebugRenderer();
	     //world
	     world = new World(new Vector2(0, -9.8f), true); // 一般标准重力场 
	   
	     //body
	     walls =new Walls(world);
	     stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true); 
	     walls.drawwalls();
	     
	    
	     pa = new ProtagonistActor("pa",world);
	     ovalActor = new OvalActor(pa.getPg());
	     //button
	     butTexture = new Texture(Gdx.files.internal("2.png"));
	     butNP = new NinePatch(butTexture);
	     
	     stage.addActor(pa); 
	     stage.addActor(ovalActor);
	     //Gdx.input.setInputProcessor(stage); 
	     
	     InputMultiplexer multiplexer = new InputMultiplexer();
		 multiplexer.addProcessor(this);
		 multiplexer.addProcessor(stage);
		 Gdx.input.setInputProcessor(multiplexer);
					     
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		worldStep();
		gameLogic();
		
		GL10 gl = Gdx.app.getGraphics().getGL10(); 
		gl = Gdx.app.getGraphics().getGL10();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);   
        stage.act(Gdx.graphics.getDeltaTime()); 
        
        pa.getB().setUserData(pa);
        
        pa.changeposition();
                   
        stage.draw();
        camera.update(); 
        camera.apply(gl); 
        renderer.render(world, camera.combined); 

		
		
	}
	
	private void worldStep(){
		float timeStep = 0.05f;
		int velocityIterations = 6;
		int positionIterations = 2;
		world.step(timeStep,velocityIterations, positionIterations);
		world.clearForces();

	}
	
	private void gameLogic(){
		Map points = mp.getPoints();
		ovalActor.onTouchEvent(points.containsKey("a")?(Integer) points.get("a"):0);
		pa.paLogic(points.containsKey("b")?(Integer)points.get("b"):0);
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub	
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}

//world.setContactListener(new ContactListener() {
//	
//	@Override
//	public void preSolve(Contact contact, Manifold oldManifold) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	@Override
//	public void postSolve(Contact contact, ContactImpulse impulse) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	@Override
//	public void endContact(Contact contact) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	@Override
//	public void beginContact(Contact contact) {
//		// TODO Auto-generated method stub
//		Fixture first = contact.getFixtureB();
//		Body body = first.getBody();					
//		if(body.equals(pa.getB())){
//			Gdx.app.log("contact", "X:"+body.getPosition().x);
//			body.setAwake(false);						
//		}
//	}
//});