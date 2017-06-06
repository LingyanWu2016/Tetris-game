package myTetris;

import java.awt.*;
import java.util.Random;

public class Block {
	/*The Pieces enum holds all seven type blocks and  the empty block. */
	protected enum Pieces{Zero,I,L,T,O,S,J,Z};
	private Pieces curPiece;
	private int coordinate[][];
	private int shape[][][];	
	
    /*public Pieces getBlock()  { 
    	return curPiece;
    	}*/
    private int randInRage(int lo, int hi) {
		return lo + (int) (Math.random() * ((hi - lo) + 1));
	}
    public void newBlock() {       
        Random r = new Random();
		//int type = r.nextInt(7)+1;;
        int type = randInRage(1,7);
        Pieces[] values = Pieces.values();
        setBlock(values[type]);
    }  
    /*Constructor of the class, The coordinate array holds the actual coordinates of a Tetris block*/
	public Block(){
		coordinate = new int[4][2];
		setBlock(Pieces.Zero);
	}  
    public void setBlock(Pieces piece) {
        /*the array holds all possible coordinate values of the tetris blocks*/
        shape = new int[][][] {
        	 { { 0,  0 },  { 0, 0 },  { 0, 0 },   { 0, 0 } },
        	 { { -1, 0 },  { 0, 0 },  { 1, 0 },   { 2, 0 } },            
             { { -1, 1 },  { -1, 0},  { 0, 0 },   { 1, 0 } },             
             { { -1, 0 },  { 0, 0 },  { 1, 0 },   { 0, 1 } },
             { { -1, 1 },  { -1, 0 }, { 0, 0 },  { 0, 1 } },
             { { -1, 0 },  { 0, 0 },  { 0, 1 },   { 1, 1 } },
             { { -1, 0 },  { 0, 0 },  { 1, 0 },   { 1, 1 } },
             { { -1, 1 },  { 0, 1 },  { 0, 0 },   { 1, 0 } }      
       };
       for (int i = 0; i < 4 ; i++) {           
           for (int j = 0; j < 2; ++j) {               
               coordinate[i][j] = shape[piece.ordinal()][i][j];
           }
       }      
       curPiece = piece;
   }
    /*set each type of block a specific color*/
    public Color getColor(){
    	switch(curPiece.ordinal()){
    	case 1:
    		return Color.decode("#00FF00");
    	case 2:
    		return Color.decode("#FF4500");
    	case 3:
    		return Color.decode("#ADFF2F");
    	case 4:
    		return Color.decode("#00BFFF");
    	case 5:
    		return Color.decode("#BA55D3");
    	case 6:
    		return Color.decode("#FF1493");
    	case 7:
    		return Color.decode("#8A2BE2");
    	default:
    		return Color.decode("#FFFFFF");  	
    	}
    }
    /*rotates to the right,the square does not have to be rotated*/
    public Block clockwiseR() {
    	if (curPiece == Pieces.O){
            return this;
    	}
        Block b = new Block();
        b.curPiece = curPiece;
        for (int i = 0; i < 4; ++i) {
            b.coordinate[i][0] = -coordY(i);
            b.coordinate[i][1] = coordX(i);
        }       
        return b;       
    }   
    /*counter_clockwise rotation*/
    public Block C_clockwiseR() {
    	if (curPiece == Pieces.O){
            return this;
    	}
        Block b = new Block();
        b.curPiece = curPiece;
        for (int i = 0; i < 4; ++i) {            
        	b.coordinate[i][0] = coordY(i);        	
        	b.coordinate[i][1] = -coordX(i);
        }       
        return b;        
    }
    public int coordX(int i) { 
    	return coordinate[i][0]; 
    	}
    public int coordY(int i) { 
    	return coordinate[i][1]; 
    	}
}

