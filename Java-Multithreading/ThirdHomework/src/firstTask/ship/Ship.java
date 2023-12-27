package firstTask.ship;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Ship {
	protected Logger logger;
	protected int load;
	protected int id;
	
	public Ship(int load, int id) {
		this.load = load;
		this.id = id;
		logger = Logger.getLogger(Ship.class.getName());
	}
	
	public void unload() {}
	public ShipType getType() {
		return null;
	}
	
	public int getId() {
		return id;
	}
}

class BreadShip extends Ship {	
	public BreadShip(int load, int id) {
		super(load, id);
	}
	
	@Override
	public void unload() {
		logger.log(Level.INFO, "Начинаем разгружать корабль " + id + " с хлебом, общая загруженность - " + load + " шт.");
		for (int i = 0; i < load; i += 10) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				logger.log(Level.WARNING, e.getMessage());
			}
			logger.log(Level.INFO, "Корабль " + id + ", разгружено " + i + " шт. хлеба");
		}
		logger.log(Level.INFO, "Корабль " + id + " с хлебом полностью разгружен!");
	}
	
	@Override
	public ShipType getType() {
		return ShipType.Bread;
	}
}

class BananaShip extends Ship {
	public BananaShip(int load, int id) {
		super(load, id);
	}
	
	@Override
	public void unload() {
		logger.log(Level.INFO, "Начинаем разгружать корабль " + id + " с бананами, общая загруженность - " + load + " шт.");
		for (int i = 0; i < load; i += 10) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				logger.log(Level.WARNING, e.getMessage());
			}
			logger.log(Level.INFO, "Корабль " + id + ", разгружено " + i + " шт. бананов");
		}
		logger.log(Level.INFO, "Корабль " + id + " с бананами полностью разгружен!");
	}
	
	@Override
	public ShipType getType() {
		return ShipType.Banana;
	}
}

class ClothesShip extends Ship {
	public ClothesShip(int load, int id) {
		super(load, id);
	}
	
	@Override
	public void unload() {
		logger.log(Level.INFO, "Начинаем разгружать корабль " + id + " с одеждой, общая загруженность - " + load + " шт.");
		for (int i = 0; i < load; i += 10) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				logger.log(Level.WARNING, e.getMessage());
			}
			logger.log(Level.INFO, "Корабль " + id + ", разгружено " + i + " у.е. одежды");
		}
		logger.log(Level.INFO, "Корабль " + id + " с одеждой полностью разгружен!");
	}
	
	@Override
	public ShipType getType() {
		return ShipType.Clothes;
	}
}
