package bankingservice;
/**
 Company: Airbnb
 Bank account system

 - To implement a class

 credit(account_id, timestamp, amount)
 debit(account_id, timestamp, amount)
 current(account_id)

 balance_change(account_id, start_ts, end_ts)
 Returns the net change in the account value between the two given timestamps
 O(log M) - where M is the number of transactions

 current(1) - expect to return 0
 credit(1, 100, 10) -- bal : 10
 credit(1, 200, 30) -- bal : 40
 debit(1, 300, 5) -- bal : 35
 credit(1, 400, 20) -- bal: 55
 credit(1, 500, 10) -- bal : 65
 current(1) - 65

 balance_change(1, 150, 450) - expect to return 45
 balance_change(1, 200, 400) - expect to return 15
 balance_change(1, 0, 600) - expect to return 65

 input - int
 credit/debit - returns void
 current - int
 balance_change - int

 */

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    class Account {
        int account_id;
        List<List<Integer>> transactions;
        int balance;

        Account(int account_id){
            this.account_id = account_id;
            this.transactions = new ArrayList<>();
            this.transactions.add(List.of(0,0));
        }
    }

    Map<Integer, Account> accounts;

    Solution(){
        this.accounts = new HashMap<>();
    }


    public void credit(int account_id, int timestamp, int amount){
        if(!accounts.containsKey(account_id)){
            accounts.put(account_id, new Account(account_id));
        }

        Account a = accounts.get(account_id);
        a.balance += amount;
        a.transactions.add(List.of(timestamp, a.balance));
    }

    public void debit(int account_id, int timestamp, int amount){
        if(!accounts.containsKey(account_id)){
            throw new IllegalArgumentException("account doesn't exist");
        }
        Account a = accounts.get(account_id);
        if(a.balance < amount){
            throw new IllegalArgumentException("insufficient balance");
        }
        a.balance -= amount;
        a.transactions.add(List.of(timestamp, a.balance));
    }

    public int current(int account_id){
        if(!accounts.containsKey(account_id)){
            accounts.put(account_id, new Account(account_id));
        }

        return accounts.get(account_id).balance;
    }

    public int balance_change(int account_id, int start_ts, int end_ts){
        if(!accounts.containsKey(account_id)){
            accounts.put(account_id, new Account(account_id));
            return 0;
        }

        Account a = accounts.get(account_id);
        int start_balance = binary_search(a.transactions, start_ts);
        int end_balance = binary_search(a.transactions, end_ts);
        return end_balance - start_balance;
    }

    public int binary_search(List<List<Integer>> transactions, int timestamp){
        int n = transactions.size();
        int index = -1;
        int start = 0, end = n-1; // 0 5

        while (start <= end) {
            int mid = start + (end - start) / 2;
            int midTimestamp = transactions.get(mid).getFirst();

            if (midTimestamp <= timestamp) {
                // mid is a valid candidate, store it and search to the right for a later candidate.
                index = mid;
                start = mid + 1;
            } else {
                // midTimestamp > timestamp, so search in the left half.
                end = mid - 1;
            }
        }
        return transactions.get(index).get(1);
    }

    // static int addNumbers(int a, int b) {
    //     return a+b;
    // }

    public static void main(String[] args) {
        Solution s = new Solution();

        System.out.println(s.current(1)); // 0
        s.credit(1, 100, 10); // -- bal : 10
        s.credit(1, 200, 30); //-- bal : 40
        s.debit(1, 300, 5); //-- bal : 35
        s.credit(1, 400, 20); //-- bal: 55
        s.credit(1, 500, 10);//-- bal : 65
        System.out.println(s.current(1)); // 65
        System.out.println("--balance " + s.balance_change(1, 150, 450)); // - expect to return 45
        System.out.println("--balance " + s.balance_change(1, 200, 400)); //- expect to return 15
        System.out.println("--balance " + s.balance_change(1, 0, 600)); //- expect to return 65

    }
}
