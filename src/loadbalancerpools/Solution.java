package loadbalancerpools;

import java.io.*;
import java.util.*;
/*
QUESTION
This id pool is fairly simple, with just 2 public methods in its interface:
- acquire()
    returns the minimum available id in the pool
    - returns Integer
    - pool ids - positive integers

- release(id)
    releases an id back to the pool
    - accepts an integer

Discussion
ID Pool
Implement an object that manages a pool of ids.
1, 2, 3, 4 ,5,6 ,7 ,8 ,9 ,10

 1
 2
 3
 4
 5
 6

r - 2
r - 3
r - 6


2
3
6

*/

// Main class should be named 'Solution' and should not be public.
class pool{
     int[] arr;

    pool(int n){
        if(n <=0){
            n = 100;
        }
        this.arr = new int[n];
    }

    public int acquire(){ // O(n)
        for(int i = 0; i< arr.length; i++){
            if(arr[i] == 0){
                arr[i] = 1;
                return i;
            }
        }
        return -1;
    }

    public boolean release(int x){ // O(1)
        if(x < arr.length && arr[x] == 1){
            arr[x] = 0;
            return true;
        }
        return false;
    }
}

class Solution {

    public static void main(String[] args) {
        System.out.println("Hello, World");

        pool p = new pool(100);

        System.out.println("Acquire: "+ p.acquire()); // 0
        System.out.println("Acquire: "+ p.acquire()); // 1
        System.out.println("Acquire: "+ p.acquire()); // 2
        System.out.println("Acquire: "+ p.acquire()); // 3
        System.out.println("Release: "+ p.release(1)); // true
        System.out.println("Release: "+ p.release(1)); // false
        System.out.println("Release: "+ p.release(1)); // false
        System.out.println("Release: "+ p.release(3)); // true
        System.out.println("Acquire: "+ p.acquire()); // 1
        System.out.println("Acquire: "+ p.acquire()); // 3
        System.out.println("Release: "+ p.release(4)); // false
        System.out.println("Release: "+ p.release(101)); // false
    }
}
