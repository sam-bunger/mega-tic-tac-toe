package com.tictactoe.game.states;

import java.util.Stack;
import com.tictactoe.game.states.State;
import com.tictactoe.game.main.TicTacToe;

public class GameStateManager {
	
	private TicTacToe game;
	
	private Stack<State> states;
	
	public GameStateManager(TicTacToe game){
		this.game = game;
		states = new Stack<State>();
	}
	
	public void push(State state){
		states.push(state);
	}
	
	public void pop(){
		State s = states.pop();
		s.dispose();
	}
	
	public void set(State state){
		pop();
		push(state);
	}
	
	public void update(float delta){
		states.peek().update(delta);
	}
	
	public void render(){
		states.peek().render();
	}
	
	public TicTacToe game(){ return game;}

}

