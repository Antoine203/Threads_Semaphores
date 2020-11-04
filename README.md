# Design and Programming Project Assignment 2 
# CSIS 3810
## Introduction
### You must provide a methodology, design, and Java implementation for this assignment (see Coding Problem below). Submit your answers in a ZIP file. The submission will include Word or Text files and the Java source and class files. Each deliverable must be clearly labeled, including your name and course title. See the Deliverables section for details.
### Problem Description
### There is a system where there are three producer threads A, B, and C, each creating output of a unique size (IE. A creates size X, B creates size Y, and C creates size Z). This system also has four identical consumer threads D, E, F, and G, which can consume any of the produced elements of size X, Y, or Z. A special repository can hold up to N elements from A of size X, M elements from B of size Y, and L elements from C of size Z.
### Your task is to write a program in Java using threads and semaphores that coordinates the producer and consumer thread activity such that a deadlock or indefinite postponement will not occur. Within the solution to this problem, you must use the semaphore primitives (acquire and release) to synchronize the producer and consumer threads. The producer and consumer code must not use any other synchronization technique outside of the semaphore class primitives of acquire and release. Assume all threads run at random speeds and production and consumption are infinite.
### In your implementation each producer and consumer are required to run as separate threads. Include print statements that show the state of your system such that the events can be traced from the output. Use indentation, blank lines and specific labels to show your output in a clear and concise manner.
#### The finished assignment must include the following (in a ZIP file that extracts as a single directory for this problem):
#### A detailed design
#### The Producer/Consumer Implementation
#### The Java Source Code
#### The Class files
#### The system must compile using the command: javac *.java o The system must run using the command: java repository
