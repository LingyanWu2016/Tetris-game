package myTetris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class CvTetris extends Canvas implements ActionListener{
	public final int WIDTH = 10;
	public final int HEIGHT = 20;
	Block currentBlock;
	Block nextBlock;
	Block changeBlock;
	int[][] map = new int[WIDTH][HEIGHT];
	Color[][] c = new Color[WIDTH][HEIGHT];
	boolean flag = false;
	boolean isDroppingFinished = false;
	boolean isReachingTop = false;
	boolean isInsideTheFallingObject = false;
	int noFullLines = 0;
	int level = 1;
	int score = 0;
	int M = 10;
	int N =1;
	float S =0.2F;
	float FS = 10.0F;
	int millisPerCycle = 1000;
	int centerX, centerY;
	int posX,posY;
	float pixelSize, rWidth=600.F, rHeight=500.F;
	float x0,y0;
	
	Timer timer;
	CvTetris(){ 	
		
		addMouseListener(new MouseAdapter(){ 
			public void mouseClicked(MouseEvent e){
			int clicked = e.getButton();
			if(!flag){
				if(clicked == e.BUTTON1){
					if(isMovable(currentBlock,posX,posY,-1)) posX--;
				}
				else if(clicked == e.BUTTON3){
					if(isMovable(currentBlock,posX,posY,1)) posX++;
				}
				repaint();
			}	
		  }
		});
		addMouseWheelListener(new MouseAdapter()
		{ public void mouseWheelMoved(MouseWheelEvent evt){
			if(!flag){
				if(evt.getWheelRotation()<0){
					if(isRotation(4))
						currentBlock=currentBlock.C_clockwiseR();
				}
				if(evt.getWheelRotation()>0){
					if(isRotation(3))
						currentBlock=currentBlock.clockwiseR();
				}
				repaint();
			}
		  }
		});
		addMouseMotionListener(new MouseAdapter(){		    
			public void mouseMoved(MouseEvent e){
	            x0 = fx(e.getX());   
				y0 = fy(e.getY()); 
				int x1,x2,x3,x4,y1,y2,y3,y4;				
				x1 = posX + currentBlock.coordX(0);				
				x2 = posX + currentBlock.coordX(1);				
				x3 = posX + currentBlock.coordX(2);				
				x4 = posX + currentBlock.coordX(3);				
				y1 = posY + currentBlock.coordY(0);				
				y2 = posY + currentBlock.coordY(1);				
				y3 = posY + currentBlock.coordY(2);				
				y4 = posY + currentBlock.coordY(3);	
				//check if the cursor is inside the falling block,if inside,change the shape
			if ( ( x0 >= -220+20*x1 && x0 <= -200+20*x1 && y0 >= -200+20*y1 && y0 <=- 180+20*y1 ) ||
					 ( x0 >= -220+20*x2 && x0 <= -200+20*x2 && y0 >= -200+20*y2 && y0 <=- 180+20*y2 ) ||
					 ( x0 >= -220+20*x3 && x0 <= -200+20*x3 && y0 >= -200+20*y3 && y0 <=- 180+20*y3 ) ||
					 ( x0 >= -220+20*x4 && x0 <= -200+20*x4 && y0 >= -200+20*y4 && y0 <=- 180+20*y4 ) 
					)	
				  {
					if (!isInsideTheFallingObject) {
						isInsideTheFallingObject = true;
  			    		}														
					}				
				   else {
					
					if (isInsideTheFallingObject) {
						isInsideTheFallingObject = false;
  			    					   
					do {
						changeBlock.newBlock();
						if ( changeBlock.getColor() != currentBlock.getColor() && changeBlock.getColor() != nextBlock.getColor() ) { 
						currentBlock = changeBlock;
						score = score - level*M;
						break;
						}
						currentBlock = changeBlock;
						
					} while ( true );
			   }
			  }
			//check to see if the cursor is inside the main area,if yes,stop moving
			if(e.getX()>=iX(-200)&&e.getX()<=iX(0)&&e.getY()>=iY(200)&&e.getY()<=iY(-200)){
				flag = true;
				timer.stop();		
 			}			
			else{
				flag = false;
				timer.start();
			}
		    repaint();
			}
		});
		addMouseListener(new MouseAdapter(){		 
			public void mousePressed(MouseEvent e){		
			if(e.getX()>=iX(50)&&e.getX()<=iX(130)&&e.getY()<=iY(-170)&&e.getY()>=iY(-130)){
				System.exit(1);
 			}
		  }
		});
		posX=5;
		posY=19;
		timer = new Timer(millisPerCycle, this);
	    timer.start(); 
		currentBlock=new Block();
		nextBlock=new Block();
		currentBlock.newBlock();
		nextBlock.newBlock();
		changeBlock = new Block();
		changeBlock.newBlock();
		
	}
	/*convert logistic coordinate to digital coordinate*/
	int iX(float x){
		return Math.round(centerX + x/pixelSize);
		}
	int iY(float y){
		return Math.round(centerY - y/pixelSize);
		}
	/*covert digital coordinate to logistic coordinate*/
	float fx(int x){
		return (x - centerX) * pixelSize;
		}
	float fy(int y){
		return (centerY - y) * pixelSize;
		}
	/*when user successfully removed N rows,level up 1*/
	public void levelUp(){
		if(noFullLines==N){
			level=level+1;
			score+=M;
			FS = FS*(1+level*S);
			noFullLines = 0;
			millisPerCycle-=(int)FS;
			timer.setDelay(millisPerCycle);
		}
		repaint();
	}
	public boolean isGameOver(){		
		boolean flag = false;
		for(int i = 0;i<WIDTH-1;i++){
			if(map[i][HEIGHT-2]==1){
				flag=true;
				break;
			}
		}
		return flag;
	}
    /*remove the row if the row is full*/
	public void removeRow(){
		for(int i = 0;i<HEIGHT;i++){
			boolean isFullLine = true;
			for(int j=0;j<WIDTH;j++){
				if(map[j][i]==0){
					isFullLine = false;
					break;
				}
			}
			if(isFullLine){
				++noFullLines;
				for(int k = i;k<HEIGHT-1;k++){
					for(int j = 0;j<WIDTH-1;j++){
						map[j][k] = map[j][k+1];
					}
				}
			}
		}
	}
	/*change shape different from itself and "Next Shape"*/
	public void changeShape(){
		if(changeBlock==currentBlock||changeBlock==nextBlock){
			changeBlock.newBlock();
		}
		currentBlock = changeBlock;		
	}
	public void paint(Graphics g){
		Dimension d = getSize();
		int maxX = d.width - 1, maxY = d.height - 1;
		pixelSize = Math.max(rWidth/maxX, rHeight/maxY);
		centerX = maxX/2; centerY = maxY/2;
		/*draw the Rectangle of main area*/		
		g.drawRect(iX(-200),iY(200),Math.round(200/pixelSize),Math.round(400/pixelSize));
		/*draw the Rectangle of "Next Shape"*/	
		g.drawRect(iX(50),iY(200),Math.round(120/pixelSize),Math.round(80/pixelSize));
		/*draw component of the right area*/
		g.setColor(Color.white);
		g.setColor(Color.black);
		g.setFont(new Font("default",Font.BOLD,Math.round(16/pixelSize)));
		g.drawString("Level:            "+level, iX(50), iY(30));
		g.drawString("Lines:            "+noFullLines, iX(50), iY(-30));
		g.drawString("Score:            "+score, iX(50), iY(-90)); 
		g.drawRect(iX(50), iY(-130), Math.round(80/pixelSize), Math.round(40/pixelSize));
		g.setFont(new Font("default",Font.BOLD,Math.round(16/pixelSize)));
		g.drawString("QUIT", iX(70), iY(-155));
		/*draw the falling block*/
		for (int i = 0; i < 4; i++) {
            int x = posX + currentBlock.coordX(i);
            int y = posY + currentBlock.coordY(i);
            drawBlock(g,x,y,currentBlock.getColor());
		}
		/*draw next shape*/
		for (int i = 0; i < 4; ++i) {
            int x = nextBlock.coordX(i);
            int y = nextBlock.coordY(i);
            drawNextShape(g,x,y,nextBlock.getColor());
		}
		/*draw stored block*/
		for(int x=0;x<WIDTH;x++){
			for(int y=0;y<HEIGHT;y++){
				if(map[x][y]==1)
					drawBlock(g,x+1,y+1,c[x][y]);
			}
		}
		isBottom();
		if(isDroppingFinished){
			addStore();
		}
	}
	/*draw blocks in the main area*/	
	public void drawBlock(Graphics g,int x,int y,Color z){
		g.setColor(z);
		g.fill3DRect(iX(-220+20*x), iY(-200+20*y), Math.round(20/pixelSize), Math.round(20/pixelSize),false);
		g.setColor(Color.black);
		g.drawRect(iX(-220+20*x), iY(-200+20*y), Math.round(20/pixelSize), Math.round(20/pixelSize));
	}
	/*draw next shape*/
	public void drawNextShape(Graphics g,int x,int y,Color z){
		g.setColor(z);
		g.fill3DRect(iX(95+20*x), iY(165+20*y), Math.round(20/pixelSize), Math.round(20/pixelSize),false);
		g.setColor(Color.black);
		g.drawRect(x=iX(95+20*x), iY(165+20*y), Math.round(20/pixelSize), Math.round(20/pixelSize));
	}
	/*Add blocks into the board when a block reach the bottom.*/
	public void addStore(){
		for (int i = 0; i < 4; ++i) {
            int x = posX + currentBlock.coordX(i);
            int y = posY + currentBlock.coordY(i);
            map[x-1][y-1]=1;
            c[x-1][y-1]=currentBlock.getColor();
		}
	}	
	public void isBottom(){
		for (int i = 0; i < 4; ++i) {
            int x = posX + currentBlock.coordX(i);
            int y = posY + currentBlock.coordY(i);
            if(y==1){
            	isDroppingFinished=true;
            	break;
            }
            else if(map[x-1][y-2]==1){
            	isDroppingFinished=true;
            	break;
            }
        }
	}
	
	/*the method returns false if it has reached the boundaries or it is adjacent to the already fallen Tetris pieces*/
	public boolean isMovable(Block b,int posX,int posY,int move){
        for (int i = 0; i < 4; i++) {           
            int x = posX + b.coordX(i);
            int y = posY - b.coordY(i);   
            if(move==1){
            	if (x==WIDTH||map[x][y-1]==1) {
            		return false;
            	}
            }
            if(move==-1){
                 if(x==1||map[x-2][y-1]==1) {
            		return false;
            	}
            }
        }
        return true;
	} 
	public boolean isRotation(int movement){
		Block b = new Block();
		if(movement == 4)   {
			b = currentBlock.C_clockwiseR();
		}
		if(movement == 3)   {
			b = currentBlock.clockwiseR();
		}
		for(int i = 0;i<4;++i){
			int x = posX + b.coordX(i);
			int y = posY +b.coordY(i);
			if(x<1||x>WIDTH||y<1||y>HEIGHT||map[x-1][y-1]==1) {
				return false;
				}
			}		
		return true;		
	}
	/*check if the falling has finished,If so, a new block is created*/
	@Override
	public void actionPerformed(ActionEvent e) {
		if(isDroppingFinished){
			isDroppingFinished=false;
			currentBlock=nextBlock;
			nextBlock=new Block();
			nextBlock.newBlock();
			posX=5;
			posY=19;
		}
		if(isGameOver()){
			timer.stop();
		}
		else{
			posY--;
			removeRow();
			levelUp();					
		}
		repaint();
	}
	public void start(){
		currentBlock=new Block();
		nextBlock=new Block();
		currentBlock.newBlock();
		nextBlock.newBlock();
		timer.start();		
    }	
}