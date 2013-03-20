
public class Piece {
	public enum COLOR {red,blue,yellow,green,colorless};
	protected COLOR col;
	public Piece(){
		this.col = COLOR.colorless;
	}
	public Piece(COLOR color){
		this.col = color;
	}
}
