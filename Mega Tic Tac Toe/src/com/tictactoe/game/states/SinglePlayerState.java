package com.tictactoe.game.states;

import com.tictactoe.game.entities.MegaGameSP;
import com.tictactoe.game.entities.TicTacGame;

public class SinglePlayerState extends State{
	
	private MegaGameSP game;
	private int AIXO;
	private int difficulty;

	public SinglePlayerState(GameStateManager gsm, int difficulty) {
		super(gsm);
		AIXO = 0;
		game = new MegaGameSP(gsm, AIXO);
		this.difficulty = difficulty;
	}

	protected void handleInput() {
		
	}

	public void update(float delta) {
		game.update(delta);
		if(game.getTurn() == AIXO){
			switch(difficulty){
				case 0: AITurnEasy();
				case 1: AITurnIntermediate();
			}
		}
	}

	public void render() {
		game.render(sb, sr);
	}

	public void dispose() {

	}
	
	public void AITurnEasy(){
		int countSpaces = 0;
		TicTacGame[][] TicTac = game.getGames();
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				
				if(TicTac[i][j].getActivation() == false) continue;
				int[][] TicGame = TicTac[i][j].getGame();
				
				for(int l = 0; l < 3; l++){
					for(int m = 0; m < 3; m++){
						if(TicGame[l][m] == -1){
							countSpaces++;
						}
					}
				}
				
			}
		}
		
		int randomNum = (int)(Math.random()*countSpaces);
		countSpaces = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				
				if(TicTac[i][j].getActivation() == false) continue;
				int[][] TicGame = TicTac[i][j].getGame();
				
				for(int l = 0; l < 3; l++){
					for(int m = 0; m < 3; m++){
						if(TicGame[l][m] == -1){
							countSpaces++;
						}
						if(countSpaces == randomNum){
							System.out.println("AI just went");
							if(!game.doTurn(i, j, l, m)){
								AITurnEasy();
							}
							return;
						}
					}
				}
			}
		}
	}
	
	public void AITurnIntermediate(){
		
	}

}
