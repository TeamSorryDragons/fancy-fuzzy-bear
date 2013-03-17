
public class Piece {
	public enum COLOR {red,blue,green,yellow,colorless};
	protected COLOR col;
	public Piece(){
		this.col = COLOR.colorless;
	}
	public Piece(COLOR color){
		this.col = color;
	}
}
