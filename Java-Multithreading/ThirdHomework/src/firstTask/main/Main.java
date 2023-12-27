package firstTask.main;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import firstTask.berch.Berch;
import firstTask.ship.Ship;
import firstTask.ship.ShipFactory;
import firstTask.ship.ShipType;
import firstTask.ship.UnknownShipTypeException;
import firstTask.tunnel.ITunnelManager;
import firstTask.tunnel.TunnelManagerFactory;

public class Main {
	private ArrayList<Berch> berches;
	private ITunnelManager tunnelManager;
	private int numBerches;
	private Logger logger;
	
	private final int[] possibleLoads = {10, 50, 100};
	private ShipType[] shipTypes = {
		ShipType.Banana,
		ShipType.Bread,
		ShipType.Clothes
	};
	
	private AtomicBoolean processStopped;
	
	public Main(int numBerches) {
		logger = System.getLogger(this.getClass().getName());
		tunnelManager = TunnelManagerFactory.buildBlockingTunnelManager(shipTypes);
		berches = new ArrayList<Berch>();
		processStopped = new AtomicBoolean(true);
		this.numBerches = numBerches;

		for (int i = 0; i < numBerches; i++) {
			berches.add(new Berch(tunnelManager, shipTypes[i], processStopped));
		}
	}
	
	public static void main(String[] args) {
		int numBerches = 3;

		Main firstTask = new Main(numBerches);
		try {
			firstTask.run();
		} catch (InterruptedException e) {
			firstTask.logger.log(Level.ERROR, e.getMessage());
		}
		firstTask.logger.log(Level.INFO, "App finished!");
	}

	public void run() throws InterruptedException {
		Random random = new Random();
		
		int shipId = 0;
		ExecutorService berchesThreadPool = Executors.newFixedThreadPool(numBerches);
		processStopped.set(false);
			
		for (Berch berch : berches) {
			berchesThreadPool.execute(berch);
		}
					
		int shipsAmount = 30;
		ArrayList<Ship> ships = new ArrayList<Ship>();
		for (int j = 0; j < shipsAmount; j++) {
			int load = possibleLoads[random.ints(0, possibleLoads.length).findFirst().getAsInt()];
			ShipType type = shipTypes[random.ints(0, shipTypes.length).findFirst().getAsInt()];

			try {
				ships.add(ShipFactory.buildShip(load, shipId, type));
			} catch (UnknownShipTypeException e) {
				logger.log(Level.ERROR, e.getMessage());
			}
			shipId++;
		}
			
		tunnelManager.putMany(ships);
			
		while (!tunnelManager.empty());
		processStopped.set(true);
		berchesThreadPool.shutdown();
		while (!berchesThreadPool.awaitTermination(5, TimeUnit.SECONDS)) {
			logger.log(Level.WARNING, "Ждем-с...");
		}
		
	}
}
