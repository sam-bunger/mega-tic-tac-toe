package com.tictactoe.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tictactoe.game.main.TicTacToe;

public abstract class State {
	
	protected TicTacToe game;
	protected GameStateManager gsm;
	protected SpriteBatch sb;
	protected ShapeRenderer sr;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;
	
	protected int SCALE = TicTacToe.SCALE;
	protected int WIDTH = TicTacToe.WIDTH;
	protected int HEIGHT = TicTacToe.HEIGHT;
	
	protected State(GameStateManager gsm){
		this.gsm = gsm;
		game = gsm.game();
		sb = game.getSpriteBatch();
		sr = game.getShapeRenderer();
		cam = game.getCamera();
		hudCam = game.getHUDCamera();
	}
	
	protected abstract void handleInput();
	public abstract void update(float delta);
	public abstract void render();
	public abstract void dispose();

}

