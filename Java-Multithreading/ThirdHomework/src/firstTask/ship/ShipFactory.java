package firstTask.ship;

public class ShipFactory {
	public static Ship buildShip(int load, int id, ShipType type) throws UnknownShipTypeException {
		switch (type) {
		case Bread:
			return new BreadShip(load, id);
		case Banana:
			return new BananaShip(load, id);
		case Clothes:
			return new ClothesShip(load, id);
		default:
			throw new UnknownShipTypeException(type);
		}
	}
}
