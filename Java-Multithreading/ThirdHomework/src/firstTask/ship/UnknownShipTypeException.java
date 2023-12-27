package firstTask.ship;

public class UnknownShipTypeException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnknownShipTypeException(ShipType type) {
		super("Unknown ship type: " + type);
	}
}
