package com.tictactoe.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tictactoe.game.states.GameStateManager;
import com.tictactoe.game.states.MenuState;

public class TicTacToe extends ApplicationAdapter {
	
	//Game Window Basics
	public static final int WIDTH = 640;
	public static final int HEIGHT = 640;
	public static int SCALE = 2;
	public static final String TITLE = "MEGA TIC TAC TOE";
	
	//Game Delta Time Variables
	private static final float STEP = 1 / 60f;
	private float accum;
	
	//Game State Manager
	private GameStateManager gsm;
	
	//Graphics
	private SpriteBatch sb;
	private ShapeRenderer sr;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	
	@Override
	public void create () {

		sr = new ShapeRenderer();
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, WIDTH, HEIGHT);
		sr.setAutoShapeType(true);
		
		
		gsm = new GameStateManager(this);
		//gsm.push(new DungeonState(gsm));
		gsm.push(new MenuState(gsm));
	}

	public void render () {
		
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP){
			
			Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			accum -= STEP;
			
			gsm.update(STEP);
			gsm.render();
			
			sb.setProjectionMatrix(cam.combined);
			sr.setProjectionMatrix(cam.combined);

		}
		
	}
	
	public void dispose () {

	}
	
	public SpriteBatch getSpriteBatch() { return sb; }
	public ShapeRenderer getShapeRenderer() { return sr; }
	public OrthographicCamera getCamera() { return cam; }
	public OrthographicCamera getHUDCamera() { return hudCam; }
	
}
