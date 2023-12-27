package secondTask.data;

public class DataPackage {
    private final int destinationNode;
    
    private final String data;

    private final long startTime;
    
    private long totalTime;

    public DataPackage(int destinationNode, String data) {
        this.destinationNode = destinationNode;
        
        this.data = data;
        
        // Фиксируется время, когда создаётся пакет данных. Необходимо для
        // вычисления времени доставки до узла назначения.
        startTime = System.nanoTime();
    }


    public int getDestinationNode() {
    	return destinationNode;
    }

    public long getStartTime() {
    	return startTime;
    }
    
    public String getData(){
        return data;
    }
    
    public void setTime(long totalTime) {
    	this.totalTime = totalTime;
    }
    
    public long getTotalTime() {
    	return totalTime;
    }
}

