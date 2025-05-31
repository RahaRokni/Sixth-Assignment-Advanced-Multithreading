###1. What output do you get from the program? Why?
The Output :
```
Atomic Counter: 2000000
Normal Counter: (less than 2000000, inconsistent)
```
* **Atomic Counter**: Always reaches 2_000_000 because AtomicInteger.incrementAndGet() is atomic and thread-safe.

* **Normal Counter**: Likely less than 2,000,000 because normalCounter++ is not thread-safe.

###2. What is the purpose of `AtomicInteger` in this code?

`AtomicInteger` is used to:

Safely perform atomic operations like incrementing in a multi-threaded environment.

### 3. What thread-safety guarantees does `atomicCounter.incrementAndGet()` provide?

* Atomically increments the value and returns the result.

* Ensures visibility, atomicity, and ordering.

### 4. In which situations would using a lock be a better choice than an atomic variable?

* Multiple variables need to be updated atomically together (atomic variables can't coordinate compound actions).

* You need fairness policies, which atomic classes do not support.

* You need blocking behavior or support for conditions

### 5. Besides `AtomicInteger`, what other data types are available in the `java.util.concurrent.atomic` package?

    * `AtomicLong`

    * `AtomicBoolean`

    * `AtomicReference<V>`
    
    * `AtomicIntegerArray`
    
    * `AtomicReferenceArray<E>`
    
    * `LongAdder`
    
### Monte Carlo Pi

    **Was the multi-threaded implementation always faster than the single-threaded one?**
        No, multi-threading is not always faster. Performs well with large workloads due to efficient CPU core utilization, but may experience slower performance on smaller tasks because of thread management overhead.


