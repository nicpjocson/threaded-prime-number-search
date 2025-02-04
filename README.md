# Threaded Prime Number Search
Last modified: 4 February 2025 by Nicole Jocson

## `config.txt` PARAMETERS
x - number of threads
y - upper bound number for the prime number search

## Windows compilation instructions
1. Open terminal
2. Type `cd "<path\to\folder>" && javac Main.java && java Main

## Different program variations
The different program variations are split into 4 folders in the repository:
1. `immediate_linear`
2. `immediate_straight`
3. `wait_linear`
4. `wait_straight`

### Printing Schemes
`immediate` - Prints the prime numbers immediately, with the thread ID (i.e., what thread found it) and timestamp.
`wait` - Wait until all threads are done, then print the prime numbers.

### Task Division Schemes
`linear` - Linear search where the threads are for divisibility testing of individual numbers.
`straight` - Straight division of search range (e.g., if x = 4 and y = 1000, then thread 1 searches for primes from 1 to 250, thread 2 searches for primes from 251 to 500, and so on).
