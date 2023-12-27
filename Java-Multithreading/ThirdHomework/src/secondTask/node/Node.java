package secondTask.node;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import secondTask.buffer.Buffer;
import secondTask.data.DataPackage;

public class Node implements Runnable {
    private final int nodeId;
    
    private final int parallel;
    
    private Buffer<DataPackage> buffer = new Buffer<>();
    
    private ExecutorService nodeExecutor;
        
    private AtomicBoolean stopped;
    
    private Node nextNode;
    
    private Node coordinator;
    
    private List<DataPackage> allData;
    
    private ReentrantLock locker;
    
    private Logger logger;
    
    public Node(
    		int nodeId,
    		int parallel,
    		AtomicBoolean stopped,
    		Node nextNode,
    		Logger logger,
    		List<DataPackage> allData
    ) {
        this.nodeId = nodeId;
                
        this.stopped = stopped;
        
        this.parallel = parallel;
        
        this.nextNode = nextNode;
        
        this.allData = allData;
                
        this.logger = logger;
        
        this.locker = new ReentrantLock();
        
        nodeExecutor = Executors.newFixedThreadPool(parallel);        
    }
    
    public void setNextNode(Node nextNode) {
    	this.nextNode = nextNode;
    }
    
    public void setCoordinator(Node coordinator) {
    	this.coordinator = coordinator;
    }
    
    public void run() {
    	while (!stopped.get()) {
    		DataPackage nextPackage = buffer.get();
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.log(Level.WARNING, e.getMessage());
			}
    		
    		if (nextPackage == null)
    			continue;
 
    		if (nextPackage.getDestinationNode() == nodeId) {
    			long totalTime = System.nanoTime() - nextPackage.getStartTime();
    			logger.log(Level.INFO, "Package with data " + nextPackage.getData() + " has been transported in " + totalTime + " ns");
    			nextPackage.setTime(totalTime);
    			coordinator.sendData(nextPackage, totalTime);
    		} else {
    			logger.log(Level.INFO, "Package with data " + nextPackage.getData() + " is being transported from " + nodeId + " to " + nextNode.getId());
    			nextNode.setData(nextPackage);
    		}
    	}
    }
    
    public void start() {
    	for (int i = 0; i < parallel; i++) {
    		nodeExecutor.execute(this);
    	}
    }

    public long getId() {
		return nodeId;
    }
    
    public void sendData(DataPackage pkg, long pkgTime) {
    	locker.lock();
    	allData.add(pkg);
    	
    	locker.unlock();
    }

    public void setData(DataPackage dataPackage) {
    	buffer.put(dataPackage);
    }

    public DataPackage getData() {
		return buffer.get();
    }
    
    public void join() {
    	nodeExecutor.shutdown();
    	try {
			while (!nodeExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
				logger.log(Level.WARNING, "Ждем-с...");
			}
		} catch (InterruptedException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
    }

    public Buffer<DataPackage> getBuffer() {
    	return buffer;
    }
    
    public int getDataAmount() {
    	return buffer.size();
    }
}
