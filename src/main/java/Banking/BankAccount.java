package Banking;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private final int id;
    private int balance;
    private final Lock lock = new ReentrantLock();

    public BankAccount(int id, int initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public int getId(){
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public Lock getLock() {
        return lock;
    }

    public void deposit(int amount) {

        lock.lock();
        balance += amount;
        lock.unlock();
    }

    public void withdraw(int amount) {
        lock.lock();
            balance -= amount;
            lock.unlock();
    }

    public void transfer(BankAccount target, int amount) {

        BankAccount firstLock = this.id < target.id ? this : target;
        BankAccount secondLock = this.id < target.id ? target : this;

        firstLock.lock.lock();
        secondLock.lock.lock();
        this.balance -= amount;
        target.balance += amount;
        secondLock.lock.unlock();

        firstLock.lock.unlock();
    }
}