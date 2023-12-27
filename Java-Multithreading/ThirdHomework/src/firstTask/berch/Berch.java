package firstTask.berch;

import java.util.concurrent.atomic.AtomicBoolean;

import firstTask.ship.Ship;
import firstTask.ship.ShipType;
import firstTask.tunnel.ITunnelManager;

public class Berch implements Runnable {
	private ShipType type;
	private ITunnelManager tunnelManager;
	private AtomicBoolean stopped;
	
	public Berch(ITunnelManager tunnelManager, ShipType type, AtomicBoolean stopped) {
		this.type = type;
		this.tunnelManager = tunnelManager;
		this.stopped = stopped;
	}
	
	public void run() {
		while (!stopped.get()) {
			Ship ship = tunnelManager.take(type);
			if (ship != null) {
				ship.unload();
			}
		}
	}
}
