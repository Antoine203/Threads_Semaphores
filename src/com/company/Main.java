package com.company;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Antoine Jermaine Smith Jr.
// CSIS 3810
// Project 2

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Initializing the Repositories
        SemaphoreSystem.getInstance().fillProductionBuffers();
        SemaphoreSystem.getInstance().printProductionRepository();

        ExecutorService executor = Executors.newCachedThreadPool();

        // Executing 200 Batches of Threads
        // There is an area within the consumeProductsFromProductionBuffers() -- Method
        // Where you can increase the time in which the results are displayed
        for (int i = 0; i < 200; i++){
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try{
                        SemaphoreSystem.getInstance().consumeProductsFromProductionBuffers();
                    }catch (Exception e){
                        System.out.println(e);
                    }

                }
            });
        }
        executor.shutdown();

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
