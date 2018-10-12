package com.tictactoe.game.states;

import com.tictactoe.game.entities.MegaGame;

public class GameState extends State{
	
	private MegaGame game;

	public GameState(GameStateManager gsm) {
		super(gsm);
		game = new MegaGame(gsm);
	}

	protected void handleInput() {
		
	}

	public void update(float delta) {
		game.update(delta);
	}

	public void render() {
		sr.setAutoShapeType(true);
		game.render(sb, sr);
	}

	public void dispose() {

	}

}
