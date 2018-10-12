package com.tictactoe.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MenuState extends State{
	
	private Texture background;
	private Texture blank;
	private BitmapFont font;
	
	private int currentChoice;
	
	private String[] options = {"Exit", "Single Player", "Multiplayer"};

	public MenuState(GameStateManager gsm) {
		super(gsm);
		background = new Texture(Gdx.files.internal("assets/menu/background.png"));
		blank = new Texture(Gdx.files.internal("assets/menu/blank.png"));
		
		font = new BitmapFont();
		currentChoice = options.length-1;
		
	}

	protected void handleInput() {
		if(Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.UP)){
			currentChoice++;
			if(currentChoice > options.length-1){
				currentChoice = 0;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.S) || Gdx.input.isKeyJustPressed(Keys.DOWN)){
			currentChoice--;
			if(currentChoice < 0){
				currentChoice = options.length - 1;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
			select();
		}
	}
	
	public void update(float delta) {
		handleInput();
	}

	public void render() {
		sb.begin();
		sb.draw(background, 0, 0, WIDTH, HEIGHT);
		sb.draw(blank, WIDTH/2.3f - 10, HEIGHT/2.3f - 35);
		
		for(int i = 0; i < options.length; i++){
			if(i == currentChoice){
				font.setColor(Color.BLUE);
			}else{
				font.setColor(Color.BLACK);
			}
			font.draw(sb, options[i], (int)(WIDTH/2.3), (int)(HEIGHT/2.3) + 20*i);
		}
		sb.end();
		
		
	}

	public void dispose() {
		background.dispose();
	}

	public void select(){
		if(currentChoice == 0){
			System.exit(0);
		}
		if(currentChoice == 1){
			gsm.set(new SinglePlayerState(gsm, 0));
		}
		if(currentChoice == 2){
			gsm.set(new GameState(gsm));
		}
	}
}
