package firstTask.tunnel;

import firstTask.ship.ShipType;

public class TunnelManagerFactory {
	public static ITunnelManager buildBlockingTunnelManager(ShipType[] types) {
		return new BlockingTunnelManager(types);
	}
}
