package secondTask.ring;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import secondTask.data.DataPackage;
import secondTask.node.Node;

/**
 * В конструкторе кольцо инициализируется, то есть создаются все узлы и данные на узлах.
 * В методе {@link RingProcessor#startProcessing()} запускается работа кольца - данные начинают
 * обрабатываться по часовой стрелке. Также производится логгирование в {@link RingProcessor#logs}.
 * Вся работа должна быть потокобезопасной и с обработкой всех возможных исключений. Если необходимо,
 * разрешается создавать собственные классы исключений.
 */
public class RingProcessor {

    private final int nodesAmount;
   
    private final int dataAmount;
    
    private int coordinatorNumber;
    
    private long totalTime;

    private final File logs;

    private List<Node> nodeList;
    
    private List<DataPackage> allData;
        
    private AtomicBoolean stopped;

    private final Logger logger;

    /**
     * Сюда идёт запись времени прохода каждого пакета данных.
     * Используется в {@link RingProcessor#averageTime()} для подсчета среднего времени
     * прохода данных к координатору.
     */

    List<Long> timeList;

    public RingProcessor(int nodesAmount, int dataAmount, File logs){
        this.nodesAmount = nodesAmount;

        this.dataAmount = dataAmount;

        this.logs = logs;
                
        this.stopped = new AtomicBoolean();
        
        this.allData = new LinkedList<DataPackage>();
        
        this.nodeList = new LinkedList<Node>();
        
        coordinatorNumber = 0;
        
        totalTime = 0;

        logger = Logger.getLogger("ringLogger");

        init();
    }

    // Считается среднее время прохода.
    private long averageTime() {
        return 0;
    }

    private void init() {
    	int threadsOnNode = 3;
        for (int i = 0; i < nodesAmount; i++) {
        	Node nextNode = null;
        	if (!nodeList.isEmpty()) {
        		nextNode = nodeList.get(nodeList.size() - 1);
        	}
        	nodeList.add(new Node(i, threadsOnNode, stopped, nextNode, logger, allData));
        }
        
        nodeList.get(0).setNextNode(nodeList.get(nodeList.size() - 1));
        generatePackages();
    }
    
    private void generatePackages() {
    	Random random = new Random();
    	coordinatorNumber = random.ints(0, nodeList.size()).findFirst().getAsInt();
    	
    	logger.log(Level.INFO, "Coordinator node is " + coordinatorNumber);
    	logger.log(Level.INFO, "Nodes amount is " + nodesAmount);
    	
    	Node coordinator = nodeList.get(coordinatorNumber);
    	for (int i = 0; i < nodesAmount; i++)
    		nodeList.get(i).setCoordinator(coordinator);
    		
    	for (int i = 0; i < dataAmount; i++) {
    		int src = random.ints(0, nodeList.size()).findFirst().getAsInt();
    		int dest = random.ints(0, nodeList.size()).findFirst().getAsInt();
    		logger.log(Level.INFO, "Package #" + i + " transport: " + src + " -> " + dest);
    		DataPackage pkg = new DataPackage(dest, "Package #" + i);
    		nodeList.get(src).setData(pkg);
    	}
    	
    	for (Node node : nodeList)
    		logger.log(Level.INFO, "Data packages on node #" + node.getId() + " is " + node.getDataAmount());
    }

    public void startProcessing(){
    	for (Node node : nodeList) {
    		node.start();
    	}
    }
    
    public void joinAndStopProcessing() {
    	while (allData.size() < dataAmount) {
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.log(Level.WARNING, e.getMessage());
			}
    	}
    	
    	stopped.set(true);
    	for (Node node : nodeList) {
    		node.join();
    	}
    	
    	long totalTime = 0;
    	for (DataPackage pkg : allData) {
    		totalTime += pkg.getTotalTime();
    	}
    	double averageTime = (double)totalTime / (double) dataAmount;
    	logger.log(Level.INFO, "Average time of transporting is " + averageTime + " ns");
    	logger.log(Level.INFO, "Completed!");
    }
}
