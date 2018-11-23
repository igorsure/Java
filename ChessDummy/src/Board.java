import java.util.ArrayList;

public class Board {
	//static long counter=1;
	int[][] brd;  
	// color -1:black, 1:white
	//             123456
	String white=" PNBRQK";
	String black=" pnbrqk";
	String yRow="  ABCDEFGH ";
	ArrayList<Board> nextMoves;
	Board(){
		brd=new int[8][8];
		
	}
	Board(Board board) {
		// clone another board;
		brd=new int[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				brd[i][j]=board.brd[i][j];
			}
		}
		
	}
	void clean() {
		brd=new int[8][8];
		nextMoves=new ArrayList<Board>();
	}
	void init() {
		clean();
		for(int i=0;i<8;i++) {
			brd[6][i]=1;
			brd[1][i]=-1;
		}
		
		//Rooks
		brd[0][0]=-4;
		brd[0][7]=-4;
		brd[7][0]=4;
		brd[7][7]=4;
		
		//Knights
		brd[0][1]=-2;
		brd[0][6]=-2;
		brd[7][1]=2;
		brd[7][6]=2;
		
		//Bishops
		brd[0][2]=-3;
		brd[0][5]=-3;
		brd[7][2]=3;
		brd[7][5]=3;
		
		//Queens/Kings
		brd[0][3]=-5;
		brd[0][4]=-6;
		brd[7][3]=5;
		brd[7][4]=6;
		
	}
	void dump() {
		for(int i=0;i<8;i++) {
			System.out.print((8-i)+"[");
			for(int j=0;j<8;j++) {
				System.out.print(showPiece(brd[i][j]));
			}
			System.out.println("]");
		}
		System.out.println(yRow);
	}
	private char showPiece(int piece) {
		if (piece==0) return ' ';
		if (piece>0) return white.charAt(piece);
		return black.charAt(-piece);
	}
	boolean goodCoord(int coordX, int coordY) {
		return (coordX>=0 && coordX<8 && coordY>=0 && coordY<8); 
	}
	void getRookMoves(Piece piece) {
		int pieceValue=piece.value;
		int posX=piece.x;
		int posY=piece.y;
		getLongMove(pieceValue,posX+1,posY,1,0,posX,posY);
		getLongMove(pieceValue,posX-1,posY,-1,0,posX,posY);
		getLongMove(pieceValue,posX,posY+1,0,1,posX,posY);
		getLongMove(pieceValue,posX,posY-1,0,-1,posX,posY);
	}
	void getBishopMoves(Piece piece) {
		int pieceValue=piece.value;
		int posX=piece.x;
		int posY=piece.y;
		getLongMove(pieceValue,posX+1,posY+1,1,1,posX,posY);
		getLongMove(pieceValue,posX+1,posY-1,1,-1,posX,posY);
		getLongMove(pieceValue,posX-1,posY+1,-1,1,posX,posY);
		getLongMove(pieceValue,posX-1,posY-1,-1,-1,posX,posY);
	}
	void getQueenMoves(Piece piece) {
		getRookMoves(piece);
		getBishopMoves(piece);
	}
	void getKingMoves(Piece piece) {
		int pieceValue=piece.value;
		int posX=piece.x;
		int posY=piece.y;
		getMove(pieceValue,posX,posY,1,0,posX,posY);
		getMove(pieceValue,posX,posY,-1,0,posX,posY);
		getMove(pieceValue,posX,posY,0,1,posX,posY);
		getMove(pieceValue,posX,posY,0,-1,posX,posY);
		
		getMove(pieceValue,posX,posY,1,1,posX,posY);
		getMove(pieceValue,posX,posY,1,-1,posX,posY);
		getMove(pieceValue,posX,posY,-1,1,posX,posY);
		getMove(pieceValue,posX,posY,-1,-1,posX,posY);
		
	}
	void getLongMove(int pieceValue,int posX,int posY,int dirX,int dirY, int startX,int startY){
		if (!goodCoord(posX,posY)) return;
		if (pieceValue*brd[posX][posY]>0) {
			return;
		} 
		Board newBoard=new Board(this);
		newBoard.brd[startX][startY]=0;
		newBoard.brd[posX][posY]=pieceValue;
		if (pieceValue*brd[posX][posY]==0) {
			getLongMove(pieceValue,posX+dirX,posY+dirY,dirX,dirY,startX,startY);
		}
		return;
	}
	void getMove(int pieceValue,int posX,int posY,int dirX,int dirY, int startX,int startY){
		if (!goodCoord(posX,posY)) return;
		if (pieceValue*brd[posX][posY]>0) {
			return;
		} 
		Board newBoard=new Board(this);
		newBoard.brd[startX][startY]=0;
		newBoard.brd[posX][posY]=pieceValue;
		return;
	}
	
	public ArrayList<Piece> getPieces(int color) {
		ArrayList<Piece> pieces=new ArrayList<Piece>();
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if (color*brd[i][j]>0)
					pieces.add(new Piece(i,j,brd[i][j]));
			}
		}
		return pieces;
	}
	void initRookBoard() {
		clean();
		System.out.println("\n ******test Rook Boards*****");
		brd[5][3]=4;
		brd[7][3]=-3;
		brd[5][6]=3;
		
	}
	public static void main(String[] args) {
		Board board=new Board();
		board.init();
		board.dump();
		//System.out.println(board.getPieces(1));
		//System.out.println(board.getPieces(-1));
		
		
		board.initRookBoard();
		board.dump();
		board.getRookMoves(new Piece(4,5,3));
		for(Board nextBoard:board.nextMoves) {
			nextBoard.dump();
		}
	}
}
