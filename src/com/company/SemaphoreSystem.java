package com.company;

import java.util.concurrent.*;
import java.util.Random;

// Antoine Jermaine Smith Jr.
// CSIS 3810
// Project 2

public class SemaphoreSystem {

    private static SemaphoreSystem instance = new SemaphoreSystem();

    private SemaphoreSystem() {

    }

    public static SemaphoreSystem getInstance(){
        return instance;
    }

    // Creating Semaphore Instances
    private Semaphore sem_Production = new Semaphore(1, true);


    // Initializing Threads
    // Producer Threads
    private Producer producer1 = new Producer();
    private Producer producer2 = new Producer();
    private Producer producer3 = new Producer();

    // Consumer Threads
    private Consumer consumer1 = new Consumer();
    private Consumer consumer2 = new Consumer();
    private Consumer consumer3 = new Consumer();
    private Consumer consumer4 = new Consumer();

    // Length of Producers Production List
    private int producer1Length = 0;
    private int producer2Length = 0;
    private int producer3Length = 0;

    // Length of Consumer List
    private int consumer1Length = 0;
    private int consumer2Length = 0;
    private int consumer3Length = 0;
    private int consumer4Length = 0;

    // Consumer Arrays
    Consumer consumer[] = {consumer1, consumer2, consumer3, consumer4};
    private int consumerIDs[] = {1,2,3,4};
    private int consumerLengths[] = {consumer1Length, consumer2Length, consumer3Length, consumer4Length};

    // Special Repository -- Area for Products can go and be Consumed
    private int producer1_ProductsBuffer[] = new int[10];
    private int producer2_ProductsBuffer[] = new int[20];
    private int producer3_ProductsBuffer[] = new int[30];
    private int openSpot_Buffer1 = 0;
    private int openSpot_Buffer2 = 0;
    private int openSpot_Buffer3 = 0;

    // Producers & Consumers List Tracker Variables
    private int producer1ListPosition = 0;
    private int producer2ListPosition = 0;
    private int producer3ListPosition = 0;

    private int consumer1ListPosition = 0;
    private int consumer2ListPosition = 0;
    private int consumer3ListPosition = 0;
    private int consumer4ListPosition = 0;

    private int roundOfBatch = 1;
    private int rounds1 = 1;
    private int rounds2 = 1;
    private int rounds3 = 1;

    private int consumersListPositions[] = {consumer1ListPosition, consumer2ListPosition, consumer3ListPosition, consumer4ListPosition};


    // ADDING NEW PRODUCTS TO EACH INDIVIDUAL BUFFER
    private void addProductsToProductionBuffers() throws InterruptedException {
        // Adding values to Each List
        updateProductionLists();
        // Production Shop 1
        if (openSpot_Buffer1 == 10){
            System.out.println("Production 1 buffer is filled. Wait until some products have been sold.\n");
        }else{
            producer1_ProductsBuffer[openSpot_Buffer1++] = producer1.production.get(producer1ListPosition++);
            System.out.println("Product "+ producer1.production.get(producer1ListPosition-1) + " is being added to Production 1 Buffer.\n");
        }
        // Production Shop 2
        if (openSpot_Buffer2 == 20){
            System.out.println("Production 2 buffer is filled. Wait until some products have been sold.\n");
        }else{
            producer2_ProductsBuffer[openSpot_Buffer2++] = producer2.production.get(producer2ListPosition++);
            System.out.println("Product "+ producer2.production.get(producer2ListPosition-1) + " is being added to Production 2 Buffer.\n");
        }
        // Production Shop 3
        if (openSpot_Buffer3 == 30){
            System.out.println("Production 3 buffer is filled. Wait until some products have been sold.\n");
        }
        else{
            producer3_ProductsBuffer[openSpot_Buffer3++] = producer3.production.get(producer3ListPosition++);
            System.out.println("Product "+ producer3.production.get(producer3ListPosition-1) + " is being added to Production 3 Buffer.\n");
        };
    }

    // INITIALIZING THE BUFFERS
    public void fillProductionBuffers(){
        int i = 0;

        while (i < 30){
            // Starter Values for the Production Lists
            updateProductionLists();
            // Production 3 Items being moved to it's Shop
            if (i < 30){
                producer3_ProductsBuffer[openSpot_Buffer3++] = producer3.production.get(producer3ListPosition++);
                // Production 2 Items being moved to it's Shop
                if (i < 20){
                    producer2_ProductsBuffer[openSpot_Buffer2++] = producer2.production.get(producer2ListPosition++);
                    // Production 1 Items being moved to it's Shop
                    if (i < 10){
                        producer1_ProductsBuffer[openSpot_Buffer1++] = producer1.production.get(producer1ListPosition++);
                    }
                }
            }
            i++;
        }
        System.out.println("\n\nBuffer is filled.\n");
    }

    // THE CONSUMER IS CONSUMING THE PRODUCTS FROM THE Shops/Repositories/Buffers
    public void consumeProductsFromProductionBuffers() throws InterruptedException{

        sem_Production.acquire();
        // Consumers Consuming Production 1 Shop is Items
        synchronized (this){
            System.out.println("______________________________________________________________\n\nConsumption in Production Shop 1 is Commencing......\n\n______________________________________________________________\n");
            // Consumer P1
            consumer1.consumptions.add(producer1_ProductsBuffer[openSpot_Buffer1 - 1]); // Consuming Products
            System.out.println("Consumer 1 Consumption: "+ consumer1.consumptions.get(consumer1ListPosition++));
            openSpot_Buffer1--;
            consumer1Length++;

            // Consumer P2
            consumer2.consumptions.add(producer1_ProductsBuffer[openSpot_Buffer1 - 1]); // Consuming Products
            System.out.println("Consumer 2 Consumption: "+ consumer2.consumptions.get(consumer2ListPosition++));
            openSpot_Buffer1--;
            consumer2Length++;

            // Consumer P3
            consumer3.consumptions.add(producer1_ProductsBuffer[openSpot_Buffer1 - 1]); // Consuming Products
            System.out.println("Consumer 3 Consumption: "+ consumer3.consumptions.get(consumer3ListPosition++));
            openSpot_Buffer1--;
            consumer3Length++;

            // Consumer P4
            consumer4.consumptions.add(producer1_ProductsBuffer[openSpot_Buffer1 - 1]); // Consuming Products
            System.out.println("Consumer 4 Consumption: "+ consumer4.consumptions.get(consumer4ListPosition++));
            openSpot_Buffer1--;
            consumer4Length++;


            // Completion of Consumption and Updates
            System.out.println("\n\nConsumption in Production Shop 1\nRound "+ rounds1 + " is Completed......");
            System.out.println("Updating....\n\n______________________________________________________________\n");
            rounds1++;
        }

        // Consuming Production 2
        synchronized (this) {
            System.out.println("\nConsumption in Production Shop 2 is Commencing......");
            consumer1.consumptions.add(producer2_ProductsBuffer[openSpot_Buffer2 - 1]); // Consuming Products
            System.out.println("Consumer 1 Consumption: "+ consumer1.consumptions.get(consumer1ListPosition));
            openSpot_Buffer2--;
            consumer1Length++;

            consumer2.consumptions.add(producer2_ProductsBuffer[openSpot_Buffer2 - 1]); // Consuming Products
            System.out.println("Consumer 2 Consumption: "+ consumer2.consumptions.get(consumer2ListPosition));
            openSpot_Buffer2--;
            consumer2Length++;

            consumer3.consumptions.add(producer2_ProductsBuffer[openSpot_Buffer2 - 1]); // Consuming Products
            System.out.println("Consumer 3 Consumption: "+ consumer3.consumptions.get(consumer3ListPosition));
            openSpot_Buffer2--;
            consumer3Length++;

            consumer4.consumptions.add(producer2_ProductsBuffer[openSpot_Buffer2 - 1]); // Consuming Products
            System.out.println("Consumer 4 Consumption: "+ consumer4.consumptions.get(consumer4ListPosition));
            openSpot_Buffer2--;
            consumer4Length++;

            // Completion of Consumption and Updates
            System.out.println("\n\nConsumption in Production Shop 2\nRound "+ rounds2 + " is Completed......");
            System.out.println("Updating....\n");
            rounds2++;
        }


        // Consuming Production 3
        synchronized (this) {
            System.out.println("______________________________________________________________\n\nConsumption in Production Shop 3 is Commencing......");
            consumer1.consumptions.add(producer3_ProductsBuffer[openSpot_Buffer3 - 1]); // Consuming Products
            System.out.println("Consumer 1 Consumption: "+ consumer1.consumptions.get(consumer1ListPosition));
            openSpot_Buffer3--;
            consumer1Length++;

            consumer2.consumptions.add(producer3_ProductsBuffer[openSpot_Buffer3 - 1]); // Consuming Products
            System.out.println("Consumer 2 Consumption: "+ consumer2.consumptions.get(consumer2ListPosition));
            openSpot_Buffer3--;
            consumer2Length++;

            consumer3.consumptions.add(producer3_ProductsBuffer[openSpot_Buffer3 - 1]); // Consuming Products
            System.out.println("Consumer 3 Consumption: "+ consumer3.consumptions.get(consumer3ListPosition));
            openSpot_Buffer3--;
            consumer3Length++;

            consumer4.consumptions.add(producer3_ProductsBuffer[openSpot_Buffer3 - 1]); // Consuming Products
            System.out.println("Consumer 4 Consumption: "+ consumer4.consumptions.get(consumer4ListPosition));
            openSpot_Buffer3--;
            consumer4Length++;

            // Completion of Consumption and Updates
            System.out.println("\n\nConsumption in Production Shop 3\nRound "+ rounds3 + " is Completed......");
            System.out.println("Updating....\n");
            rounds3++;
        }
        // Brief Pause
        Thread.sleep(3000);

        // Releasing the lock
        sem_Production.release();

        // Reloading the Shops with more Products for Consumption
        if (roundOfBatch == 200){
            System.out.println("\n\n______________________________________________________________\nRound " + (roundOfBatch) + " of the Batch is now Completed.");
            System.out.println("All three shops are now closed for the day. Come back during business hours.");
        }
        else{
            synchronized (this){
                int i = 0;
                System.out.println("\n\n______________________________________________________________\n\nRound " + (roundOfBatch++) + " of the Batch is now Completed.\n\n______________________________________________________________\n");
                System.out.println("Items are now being Added into all Three Shops.\nLoading....\n______________________________________________________________\n");
                // Used for Increasing and Decreasing the display Time
                TimeUnit.SECONDS.sleep(5);
                while (i < 4){
                    addProductsToProductionBuffers();
                    i++;
                }
            }
        }
    }

    // Used for Printing the Production Repositories/Shops
    public void printProductionRepository() throws InterruptedException {
        int i = 0;
        System.out.println("______________________________________________________________\n\nProduction Repositories: \n______________________________________________________________");
        while (i < producer3_ProductsBuffer.length){
            if (i < producer1_ProductsBuffer.length){
                System.out.println("Producer1 Products: " + producer1_ProductsBuffer[i]);
            }
            if (i < producer2_ProductsBuffer.length){
                System.out.println("Producer2 Products: " + producer2_ProductsBuffer[i]);
            }
            System.out.println("Producer3 Products: " + producer3_ProductsBuffer[i]);
            System.out.println("");
            i++;
        }
        System.out.println("______________________________________________________________\n\nAll of the Current Products in all Three Shops -- Shown Above\n\n");
    }

    // Random Value Generator
    private int randomProductValues(){
        Random rand = new Random();
        int max = 100;
        // Generates a number from 0 - 100
        int randomNumber = rand.nextInt(max);
        return randomNumber;
    }



    // Updates the Production values for the three Producer Threads
    private void updateProductionLists(){
        // Add Products to Each Producers Storage
        producer1.production.add(randomProductValues());
        producer2.production.add(randomProductValues());
        producer3.production.add(randomProductValues());
        // Length of Producer list
        producer1Length++;
        producer2Length++;
        producer3Length++;
    }



}
