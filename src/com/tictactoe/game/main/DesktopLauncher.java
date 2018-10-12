package com.tictactoe.game.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {

	public static void main(String[] args){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = TicTacToe.WIDTH * TicTacToe.SCALE;
		config.height = TicTacToe.HEIGHT * TicTacToe.SCALE;
		config.title = TicTacToe.TITLE;
		
		new LwjglApplication(new TicTacToe(), config);
	}
}
