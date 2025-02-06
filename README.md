# Threaded Prime Number Search  
Java program in partial fulfillment of the requirements for the course STDISCM.  
Last modified: 7 February 2025 by Nicole Jocson  

## Features
### Printing Schemes  
- `immediate` - Prints the prime numbers immediately, with the thread ID (i.e., what thread found it) and timestamp.  
- `wait` - Wait until all threads are done, then print the prime numbers.  

### Task Division Schemes  
- `linear` - Linear search where the threads are for divisibility testing of individual numbers.  
- `straight` - Straight division of search range (e.g., if x = 4 and y = 1000, then thread 1 searches for primes from 1 to 250, thread 2 searches for primes from 251 to 500, and so on).  

## Different program variations  
The different program variations are split into 4 folders in the repository:  
1. `immediate_linear` (ImmediateLinear.java)  
    - Prints primes **immediately**, using **linear** task division.  
2. `immediate_straight` (ImmediateStragiht.java)  
    - Prints primes **immediately**, using **straight** task division.  
3. `wait_linear` (WaitLinear.java)  
    - **Waits for all threads** to finish before printing primes, using **linear** task division.  
4. `wait_straight` (WaitStraight.java)  
    - **Waits for all threads** to finish before printing primes, using **straight** task division.  
  
Each folder contains:  
- The corresponding Java program.  
- A `config.txt` file for configuration.  
- A demo video.  

## `config.txt` parameters  
**`x`** - number of threads  
**`y`** - upper bound number for the prime number search  

## Compilation and Execution Instructions using Command-line interface (CLI)  
1. Open a terminal or command prompt.  
2. Navigate to the project folder.  
```
cd <program_variation_folder>
```
3. Compile the program:  
```
javac <ProgramVariation>.java
```
4. Run the program:  
```
java <ProgramVariation>
```