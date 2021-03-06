/**
 * Exception for attempting to create a coordinate not on the board.
 * 
 * @author sturgedl. Created Mar 25, 2013.
 */
@SuppressWarnings("serial")
public class CoordinateOffOfBoardException extends IllegalArgumentException {
	/**
	 * If you can't figure out what this does, please don't use it.
	 * 
	 * @param message
	 */
	public CoordinateOffOfBoardException(String message) {
		super(message);
	}

}
