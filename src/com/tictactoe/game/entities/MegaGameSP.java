package com.tictactoe.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tictactoe.game.main.TicTacToe;
import com.tictactoe.game.states.GameStateManager;
import com.tictactoe.game.states.MenuState;

public class MegaGameSP{
	
	private TicTacGame[][] game;
	private final int size = 3;
	private int currentTurn;
	private int moveCount;
	private int winner;
	private int AIXO;
	
	private GameStateManager gsm;
	
	private final Texture xWins = new Texture(Gdx.files.internal("assets/game/x wins.png"));
	private final Texture oWins = new Texture(Gdx.files.internal("assets/game/o wins.png"));
	private final Texture draw = new Texture(Gdx.files.internal("assets/game/draw.png"));

	public MegaGameSP(GameStateManager gsm, int AIXO) {
		game = new TicTacGame[size][size];
		currentTurn = 1;
		moveCount = 0;
		winner = -1;
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				game[i][j] = new TicTacGame(i * 170 + 65, j * 170 + 65);
			}
		}
		this.gsm = gsm;
		this.AIXO = AIXO;
	}
	
	public void render(SpriteBatch sb, ShapeRenderer sr){
		Gdx.gl.glClearColor(1, 1, 1, 1);
		sr.begin();
		sr.setColor(new Color(Color.GRAY));
		int spacing = 170;
		for(int i = 1; i < size; i++){
			sr.rectLine(55 + spacing*i, 55, 55 + spacing*i, 55+(size*spacing), 3f);
			sr.rectLine(55, 55 + spacing*i, 55 + (size*spacing), 55 + spacing*i, 3f);
		}
		sr.end();

		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				game[i][j].render(sb, sr);
			}	
		}
		
		sb.begin();
		if(currentTurn == 1){
			sb.draw(game[0][0].getXSprite(), 600, 600);
		}else{
			sb.draw(game[0][0].getOSprite(), 600, 600);
		}
		
		if(winner == 2){
			sb.draw(draw, (TicTacToe.WIDTH/3f), TicTacToe.HEIGHT - 60);
		}
		
		if(winner == 1){
			sb.draw(xWins, (TicTacToe.WIDTH/3f), TicTacToe.HEIGHT - 60);
		}
		
		if(winner == 0){
			sb.draw(oWins, (TicTacToe.WIDTH/3f), TicTacToe.HEIGHT - 60);
		}
		
		sb.end();
	}
	
	public void update(float delta){
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				game[i][j].update(delta);
			}	
		}
		
		if(winner != -1){
			for(int i = 0; i < size; i++){
				for(int j = 0; j < size; j++){
					game[i][j].setActivation(false);
				}
			}
			if(Gdx.input.justTouched()){
				gsm.set(new MenuState(gsm));
			}
			return;
		}
		
		if(Gdx.input.justTouched()){
			int[] a = getClickedBox();
			if(a.length != 1){
				boolean works;
				do{
					works = doTurn(a[0], a[1], a[2], a[3]);
				}while(!works);
				
			}
		}
	}
	
	public int[] getClickedBox(){
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				
				if(game[i][j].getActivation()){
					for(int k = 0; k < size; k++){
						for(int l = 0; l < size; l++){
							boolean[][] mouse = game[i][j].getMouseIn();
							if(mouse[l][k] == true){
								int[] result = {i, j, k, l};
								return result;						
							}
						}
					}
				}
				
			}
		}
		int[] result = {-1};
		return result;
	}
	
	public void switchTurn(){
		if(currentTurn == 1){
			currentTurn = 0;
		}else{
			currentTurn = 1;
		}
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				game[i][j].setTurn(currentTurn);
			}
		}
		
	}
	
	public int checkWinner(int row, int col, int xo){
		
		//check column
		for(int i = 0; i < size; i++){
			if(game[row][i].getFinished() != xo){
				break;
			}
			if(i == size-1){
				return xo;
			}
		}
		//check row
		for(int i = 0; i < size; i++){
			if(game[i][col].getFinished() != xo){
				break;
			}
			if(i == size-1){
				return xo;
			}
		}
		
		if(row == col){
			for(int i = 0; i < size; i++){
				if(game[i][i].getFinished() != xo){
					break;
				}
				if(i == size-1){
					return xo;
				}
			}
		}
		
		if(row + col == size-1){
			for(int i = 0; i < size; i++){
				if(game[i][(size-1) - i].getFinished() != xo){
					break;
				}
				if(i == size-1){
					return xo;
				}
			}
		}
		 
		if(moveCount == (size*size)){
			return 2;
		}
		
		return -1;
		
	}
	
	public int getTurn(){ return currentTurn; }
	public void AIMove(int gRow, int gCol, int row, int col){
		game[gRow][gCol].addXO(row, col, AIXO);
	}
	public TicTacGame[][] getGames(){ return game;}
	
	public boolean doTurn(int row1, int col1, int row2, int col2){ 
		int x = game[row1][col1].addXO(col2, row2, currentTurn);
		
		if(x == 3) return false;

		if(x == 0){
			switchTurn();
			int num = checkWinner(row1, col1, 0);
			if(num == 0){
				winner = 0;
			}else if(num == 2){
				winner = 2;
			}
		}else if(x == 1){
			switchTurn();
			int num = checkWinner(row1, col1, 1);
			if(num == 1){
				winner = 1;
			}else if(num == 2){
				winner = 2;
			}
		}else if(x == 2){
			switchTurn();
			int num = checkWinner(row1, col1, 0);
			if(num == 0){
				winner = 0;
			}else if(num == 2){
				winner = 2;
			}
			num = checkWinner(row1, col1, 1);
			if(num == 1){
				winner = 1;
			}else if(num == 2){
				winner = 2;
			}
		}else{
			switchTurn();
		}
		
		if(game[row2][col2].getFinished() == -1){
			for(int i = 0; i < size; i++){
				for(int j = 0; j < size; j++){
					game[i][j].setActivation(false);
				}
			}
			game[row2][col2].setActivation(true);
		}else{
			for(int i = 0; i < size; i++){
				for(int j = 0; j < size; j++){
					if(game[i][j].getFinished() != -1){
						game[i][j].setActivation(false);
					}else{
						game[i][j].setActivation(true);
					}
				}
			}
		}
		
		return true;
		
	}

}
