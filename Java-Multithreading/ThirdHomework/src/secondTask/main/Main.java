package secondTask.main;

import java.io.File;

import secondTask.ring.RingProcessor;

public class Main {

    public static void main(String[] args) {
        RingProcessor processor = new RingProcessor(10, 3, new File("logPath"));

        processor.startProcessing();
        processor.joinAndStopProcessing();
    }
}
