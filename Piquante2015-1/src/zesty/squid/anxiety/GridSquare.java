package zesty.squid.anxiety;

public class GridSquare {
	
	private int _topX;
	private int _topY;
	
	private boolean _occupied;
	private boolean _seat;
	private char _debugChar;
	
	public GridSquare(int topX, int topY){
		_topX = topX;
		_topY = topY;
		_debugChar = 'G';
	}
	
	public boolean isOccupied(){return _occupied;}
	public boolean isSeat(){return _seat;}
	public void setSeat(boolean value){
		_seat = value;
		if (_seat)
			_debugChar = 'X';
		else 
			_debugChar = 'O';
	}
	public char getChar(){return _debugChar;}
}
