package zesty.squid.anxiety;

import android.util.Log;

public class GridInfo {
	private int _minX;
	private int _minY;
	private int _maxX;
	private int _maxY;
	private int _squareSide;
	
	private GridSquare [][] squares;
	
	//public GridInfo (int minX, int minY, int maxX, int maxY, int squareSide){
	public GridInfo (){
		/*
		squares = new GridSquare [ActGameScreen.NUM_COLS][ActGameScreen.NUM_ROWS];
		int startX = ActGameScreen.BOARD_MIN_X;
		int startY = ActGameScreen.BOARD_MIN_Y;
		int topX, topY = 0;
		
		// Instantiate GridSquares
		int x, y = -1;
		for (x = 0; x < ActGameScreen.NUM_COLS; x++){
			topX = startX + (x*ActGameScreen.SQUARE_SIDE);
			for (y = 0; y < ActGameScreen.NUM_ROWS; y++){
				topY = startY + (y*ActGameScreen.SQUARE_SIDE);
				boolean seat = buildAsSeat(x,y);
				Log.w("GRID_DEBUG", "GRID_DEBUG: Creating a GridSquare at (" + x + ", " + y +")");
				squares[x][y] = new GridSquare(topX, topY, seat);
			}
		}
		*/
		// [11][7]
		squares = new GridSquare [ActGameScreen.NUM_ROWS][ActGameScreen.NUM_COLS];
		
		// [3][4]
		char [][] testArray = { 
				{'A','B','C','D'},
				{'E','F','G','H'},
				{'I','J','K','L'}
		};
		
		int startX = ActGameScreen.BOARD_MIN_X;
		int startY = ActGameScreen.BOARD_MIN_Y;
		int topX, topY = 0;
		
		String output = "";
		for (int y = 0; y < ActGameScreen.NUM_ROWS; y++){
			for (int x = 0; x < ActGameScreen.NUM_COLS; x++){
				squares[y][x] = new GridSquare(0, 0);
			}
		}
		
		setSeats();
		//Log.w("GRID_DEBUG", "GRID_DEBUG: " + output);

		//Log.w("GRID_DEBUG", "GRID_DEBUG: Finishing X and Y were (" + x + ", " + y +")");
		//Log.w("GRID_DEBUG", "GRID_DEBUG: COLS and ROWS were (" + ActGameScreen.NUM_COLS + ", " + ActGameScreen.NUM_ROWS +")");
	}
	
	private boolean buildAsSeat(int x, int y){
		boolean decided = false;
		boolean seat = false;
		// Top row of seats
		if (decided == false && x == 0){
			// Top-left or top-right
			if ((y >= 0 && y <= 2) || (y >= 4 && y <= 6)){
				seat = true;
				decided = true;
			}
		}
		// Bottom row of seats
		if (decided == false && x == 10){
			// Bottom-left or Bottom-right
			if ((y >= 0 && y <= 2) || (y >= 4 && y <= 6)){
				seat = true;
				decided = true;
			}
		}
		// Quadrant 1 or 3
		if (decided == false && (x == 0 || x == 1)){
			// Quadrant 1
			if (decided == false && (y == 2 || y == 3)){
				seat = true;
				decided = true;
			}
			// Quadrant 3
			if (decided == false && (y == 7 || y == 8)){
				seat = true;
				decided = true;
			}
		}
		// Quadrant 2 or 4
		if (decided == false && (x == 5 || x == 6)){
			// Quadrant 2
			if (decided == false && (y == 2 || y == 3)){
				seat = true;
				decided = true;
			}
			// Quadrant 4
			if (decided == false && (y == 7 || y == 8)){
				seat = true;
				decided = true;
			}
		}
		return seat;
	}

	public String debugString(){
		String output = "";
		for (int y = 0; y < ActGameScreen.NUM_ROWS; y++){
			for (int x = 0; x < ActGameScreen.NUM_COLS; x++){
				output = output + squares[y][x].getChar();
			}
			output = output + ';';
		}
		return output;
	}
	
	private void setSeats(){
		// Top Row
		squares[0][0].setSeat(true);
		squares[0][1].setSeat(true);
		squares[0][2].setSeat(true);
		squares[0][4].setSeat(true);
		squares[0][5].setSeat(true);
		squares[0][6].setSeat(true);
		
		// Q1, Q2
		squares[2][0].setSeat(true);
		squares[2][1].setSeat(true);
		squares[2][5].setSeat(true);
		squares[2][6].setSeat(true);
		squares[3][0].setSeat(true);
		squares[3][1].setSeat(true);
		squares[3][5].setSeat(true);
		squares[3][6].setSeat(true);
		
		// Q3, Q4
		squares[7][0].setSeat(true);
		squares[7][1].setSeat(true);
		squares[7][5].setSeat(true);
		squares[7][6].setSeat(true);
		squares[8][0].setSeat(true);
		squares[8][1].setSeat(true);
		squares[8][5].setSeat(true);
		squares[8][6].setSeat(true);
		
		// Bottom Row
		squares[10][0].setSeat(true);
		squares[10][1].setSeat(true);
		squares[10][2].setSeat(true);
		squares[10][4].setSeat(true);
		squares[10][5].setSeat(true);
		squares[10][6].setSeat(true);
	}
}
