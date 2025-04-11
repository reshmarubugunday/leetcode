package inmemorydatastore;
/*
Company: Rippling
Q1
Design and implement an in-memory key value data store.
This data store should be able to support some basic operations such as Get, Set and Delete for string keys and values.

I would like to see test cases as well as implementation code.
You can assume that the input operations are always valid, but the key to operate on may be non-existent.

We won't worry about concurrent access to the database.
You can handle errors however you think is best.
Let's start with the data structure of this key value store.

Implement methods for Get, Set and Delete.

For example:

Database DB = new Database();

DB.set("key1", "val1");
System.out.println(DB.get("key1")); // val1
DB.delete("key1");

// input - Strings
// get - key might not exist

Q2
Add support for transactions - Begin, Commit and Rollback.

A transaction is created with the Begin command and creates a context for the other operations to happen.
Until the active transaction is committed using the Commit command, those operations do not persist.
The Rollback command throws away any changes made by those operations in the context of the active transaction.

`Commit()` and `Rollback()` will only happen when inside a transaction, and they both end the transaction.
Note: We won't worry about concurrency for this part of the question.

The following examples demonstrate how this might work:

Example 1
---------

* Set `key0` to `val0`
* Get `key0`
  * Expect `val0`
* Begin transaction
* Within transaction: Get `key0`
  * Expect `val0`
* Within transaction: Set `key1` to `val1`
* Within transaction: Get `key1`
  * Expect `val1`
* Commit transaction
* Outside the transaction: Get `key1`
  * Expect `val1`

Example 2
---------

* Begin transaction
* Set `key2` to `val2`
* Get `key2`
  * Expect `val2`
* Rollback
* Get `key2`
* Expect an error case as `key2` is not set

Q3
How might you add support for nested transactions?

The newly spawned transaction inherits the variables from its parent transaction.

Changes made within a child transaction will apply to the parent transaction once commited.

A commit on a child transaction does not affect the global state.
Once commit is called on the parent transaction, all transactions will be persisted to the global state
If the parent is rolled back, global state should remain unchanged.

The following example demonstrates how this might work:

* Begin transaction (create a parent transaction_
* Set `key1` to `val1`
* Set `key2` to `val2`
* Begin (create a child transaction)
* Get `key1` within child transaction
  * Expect `val1`
* Set `key1` to `val1_child` within the child transaction
* Commit the child transaction
* Get `key1` with parent transaction
  * Expect `val1_child`
* Get `key2` with parent transaction
  * Expect `val2`
* Commit parent transaction to global state

*/

import java.util.*;

public class Solution {

    private HashMap<String, String> store;
    private Stack<HashMap<String, String>> transactionStack;
    private boolean inTransaction;

    public Solution() {
        this.store = new HashMap<>();
        this.transactionStack = new Stack<>();
        this.inTransaction = false;
    }

    // Set a key-value pair
    public void set(String key, String value) {
        if (inTransaction) {
            transactionStack.peek().put(key, value);
        } else {
            store.put(key, value);
        }
    }

    // Get the value associated with a key
    public String get(String key) {
        if (inTransaction) {
            for (int i = transactionStack.size() - 1; i >= 0; i--) {
                if (transactionStack.get(i).containsKey(key)) {
                    return transactionStack.get(i).get(key);
                }
            }
        }
        return store.getOrDefault(key, null);
    }

    // Delete a key from the store
    public void delete(String key) {
        if (inTransaction) {
            transactionStack.peek().remove(key);
        } else {
            store.remove(key);
        }
    }

    // Begin a new transaction (nested if already in a transaction)
    public void begin() {
        HashMap<String, String> newTransaction = new HashMap<>();
        if (!transactionStack.isEmpty()) {
            newTransaction.putAll(transactionStack.peek()); // Inherit parent transaction state
        }
        transactionStack.push(newTransaction);
        inTransaction = true;
    }

    // Commit the current transaction
    public void commit() {
        if (!inTransaction) {
            throw new IllegalStateException("No active transaction to commit.");
        }
        HashMap<String, String> committedTransaction = transactionStack.pop();
        if (transactionStack.isEmpty()) {
            store.putAll(committedTransaction);
            inTransaction = false;
        } else {
            transactionStack.peek().putAll(committedTransaction); // Merge with parent transaction
        }
    }

    // Rollback the current transaction
    public void rollback() {
        if (!inTransaction) {
            throw new IllegalStateException("No active transaction to rollback.");
        }
        transactionStack.pop();
        if (transactionStack.isEmpty()) {
            inTransaction = false;
        }
    }

    public static void main(String[] args) {
        Solution db = new Solution();

        // Test set and get functionality
        db.set("key1", "val1");
        assert "val1".equals(db.get("key1")) : "Test Failed: Expected val1";
        System.out.println("Test 1 Passed");

        // Test overwrite behavior
        db.set("key1", "val2");
        assert "val2".equals(db.get("key1")) : "Test Failed: Expected val2";
        System.out.println("Test 2 Passed");

        // Test delete functionality
        db.delete("key1");
        assert db.get("key1") == null : "Test Failed: Expected null";
        System.out.println("Test 3 Passed");

        // Test getting a non-existent key
        assert db.get("non_existent") == null : "Test Failed: Expected null";
        System.out.println("Test 4 Passed");

        // Example 1: Commit Transaction
        db.set("key0", "val0");
        assert "val0".equals(db.get("key0")) : "Test Failed: Expected val0";
        db.begin();
        assert "val0".equals(db.get("key0")) : "Test Failed: Expected val0 in transaction";
        db.set("key1", "val1");
        assert "val1".equals(db.get("key1")) : "Test Failed: Expected val1 in transaction";
        db.commit();
        assert "val1".equals(db.get("key1")) : "Test Failed: Expected val1 after commit";
        System.out.println("Transaction Commit Test Passed");

        // Example 2: Rollback Transaction
        db.begin();
        db.set("key2", "val2");
        assert "val2".equals(db.get("key2")) : "Test Failed: Expected val2 in transaction";
        db.rollback();
        assert db.get("key2") == null : "Test Failed: Expected null after rollback";
        System.out.println("Transaction Rollback Test Passed");

        // Example: Nested Transaction Commit
        db.begin();
        db.set("key1", "val1");
        db.set("key2", "val2");
        db.begin();
        assert "val1".equals(db.get("key1")) : "Test Failed: Expected val1 in child transaction";
        db.set("key1", "val1_child");
        db.commit();
        assert "val1_child".equals(db.get("key1")) : "Test Failed: Expected val1_child in parent transaction after commit";
        assert "val2".equals(db.get("key2")) : "Test Failed: Expected val2 in parent transaction";
        db.commit();
        assert "val1_child".equals(db.get("key1")) : "Test Failed: Expected val1_child in global state after final commit";
        System.out.println("Nested Transaction Commit Test Passed");

    }
}

