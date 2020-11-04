package com.company;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Antoine Jermaine Smith Jr.
// CSIS 3810
// Project 2

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Obtaining the desired number of threads the user wants executed
        Scanner input = new Scanner(System.in);
        System.out.println("How many threads would you like to have executed? (INTEGERS ONLY)");
        int userThreadNumber = input.nextInt();
        System.out.println("");
        SemaphoreSystem.getInstance().getUserThreadCount(userThreadNumber);

        // Initializing the Repositories
        SemaphoreSystem.getInstance().fillProductionBuffers();
        SemaphoreSystem.getInstance().printProductionRepository();

        ExecutorService executor = Executors.newCachedThreadPool();
        // There is an area within the consumeProductsFromProductionBuffers() -- Method
        // Where you can increase the time in which the results are displayed
        for (int i = 0; i < userThreadNumber; i++){
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
