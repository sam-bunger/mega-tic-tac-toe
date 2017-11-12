package com.tictactoe.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tictactoe.game.main.TicTacToe;

public class TicTacGame {
	
	private float x;
	private float y;
	
	private float mouseX;
	private float mouseY;
	
	private Texture X = new Texture(Gdx.files.internal("assets/game/x.png"));
	private Texture O = new Texture(Gdx.files.internal("assets/game/o.png"));
	
	private float spaceSize = 50;
	private final int size = 3;
	
	private int moveCount;
	
	private int[][] game;
	private boolean[][] mouseIn;
	
	private boolean gameActivated;
	private int gameFinished;
	
	private int currentTurn;

	public TicTacGame(float x, float y) {
		this.x = x;
		this.y = y;
		this.game = new int[size][size];
		mouseIn = new boolean[size][size];
		moveCount  = 0;
		gameActivated = true;
		gameFinished = -1;
		currentTurn = 1;
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				game[i][j] = -1;
				mouseIn[i][j] = false;
			}
		}
	}
	
	//returns -1 if move is valid
	//returns 3 if move is invalid (i.e. already played their);
	//returns 1 if X is winner
	//returns 0 if O is winner
	//returns 2 for draw
	public int addXO(int row, int col, int xo){
		if(game[row][col] == -1){
			game[row][col] = xo;
			moveCount++;
			return checkWinner(row, col, xo);
		}
		return 3;	
	}
	
	//returns -1 for no winner
	//return 0 if O is the winner
	// returns 1 if X is the winner
	public int checkWinner(int row, int col, int xo){
		
		//check column
		for(int i = 0; i < size; i++){
			if(game[row][i] != xo){
				break;
			}
			if(i == size-1){
				gameFinished = xo;
				return xo;
			}
		}
		//check row
		for(int i = 0; i < size; i++){
			if(game[i][col] != xo){
				break;
			}
			if(i == size-1){
				gameFinished = xo;
				return xo;
			}
		}
		
		if(row == col){
			for(int i = 0; i < size; i++){
				if(game[i][i] != xo){
					break;
				}
				if(i == size-1){
					gameFinished = xo;
					return xo;
				}
			}
		}
		
		if(row + col == size-1){
			for(int i = 0; i < size; i++){
				if(game[i][(size-1) - i] != xo){
					break;
				}
				if(i == size-1){
					gameFinished = xo;
					return xo;
				}
			}
		}
		 
		if(moveCount == (size*size)){
			gameFinished = 2;
			return 2;
		}
		
		return -1;
		
	}
	
	public void update(float delta){
		mouseX = Gdx.input.getX()/TicTacToe.SCALE;
		mouseY = TicTacToe.HEIGHT - (Gdx.input.getY()/TicTacToe.SCALE);
	}
	
	public void render(SpriteBatch sb, ShapeRenderer sr){
		
		sr.begin();
		
		sr.setColor(new Color(Color.BLACK));
		for(int i = 1; i < size; i++){
			sr.line(x + spaceSize*i, y, x + spaceSize*i, y+(size*spaceSize));
			sr.line(x, y + spaceSize*i, x + (size*spaceSize), y+spaceSize*i);
		}
		
		if(gameActivated){
			sr.setColor(new Color(Color.RED));
			sr.rect(x, y, (size*spaceSize), (size*spaceSize));
		}
		
		if(gameFinished == 0){
			sr.setColor(new Color(Color.BLUE));
			sr.circle(x + ((size*spaceSize)/2), y + ((size*spaceSize)/2), ((size*spaceSize)/2));
		}
		
		if(gameFinished == 1){
			sr.setColor(new Color(Color.BLUE));
			sr.line(x, y + (size*spaceSize), x + (size*spaceSize), y);
			sr.line(x, y, x + (size*spaceSize), y + (size*spaceSize));
		}
		
		if(gameFinished == 2){
			sr.setColor(new Color(Color.GREEN));
			sr.triangle(x, y, x + (size*spaceSize), y, x + (size*spaceSize)/2, y + (size*spaceSize));
		}
		
		sr.end();
		
		sb.begin();
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				mouseIn[i][j] = false;
				int num = game[i][j];
				//draw X's and 0's
				if(num == 0){
					sb.draw(O, x + (j*spaceSize)+10, y + (i*spaceSize)+10);
				}
				if(num == 1){
					sb.draw(X, x + (j*spaceSize)+10, y + (i*spaceSize)+10);
				}
				
				if(gameFinished == -1 && num == -1 && gameActivated && mouseX >= x + (j*spaceSize) && mouseX < x + ((j+1)*spaceSize) && mouseY >= y + (i*(spaceSize)) && mouseY < y + ((i+1)*(spaceSize))){
					mouseIn[i][j] = true;
					if(currentTurn == 0){
						sb.draw(O, x + ((j)*spaceSize) + 10, y + ((i)*spaceSize) + 10);
					}
					if(currentTurn == 1){
						sb.draw(X, x + ((j)*spaceSize) + 10, y + ((i)*spaceSize) + 10);
					}
	
				}
			}
		}
		
		sb.end();
		
	}
	
	public int[][] getGame(){ return game; }
	public void setActivation(boolean a){ gameActivated = a; }
	public boolean getActivation(){ return gameActivated; }
	public int getFinished(){ return gameFinished; }
	public boolean[][] getMouseIn(){ return mouseIn; }
	public void setTurn(int turn){ currentTurn = turn;}
	public Texture getXSprite(){ return X; }
	public Texture getOSprite(){ return O; }
	

}
