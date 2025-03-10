package inmemorydatastore;
/*

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
*/

import java.util.*;

public class Solution {

    static Map<String, String> db = new HashMap<>();
    static Map<String, String> tempMap;
    static Map<String, String> deletedKeys;
    static boolean isTransactionInProgress;

    public static void set(String key, String val){
        if(isTransactionInProgress){
            if(deletedKeys.containsKey(key)){
                deletedKeys.remove(key);
            }
            tempMap.put(key, val);
        } else {
            db.put(key, val);
        }
    }

    public static String get(String key){
        if(isTransactionInProgress){
            if(deletedKeys.containsKey(key)){
                return null;
            }
            String s = tempMap.get(key);
            if (s == null) {
                return db.get(key);
            }
            return s;
        }
        return db.get(key);
    }

    public static void delete(String key){
        if(isTransactionInProgress){
            tempMap.remove(key);
            deletedKeys.put(key, "");
        } else {
            db.remove(key);
        }
    }

    public static void begin(){
        tempMap = new HashMap<>();
        deletedKeys = new HashMap<>();
        isTransactionInProgress = true;
    }

    public static void commit(){
        isTransactionInProgress = false;
        for(Map.Entry<String, String> entry: tempMap.entrySet()){
            System.out.println("key: " + entry.getKey() + " val " + entry.getValue());
            db.put(entry.getKey(), entry.getValue());
        }

        for(Map.Entry<String, String> entry: deletedKeys.entrySet()){
            delete(entry.getKey());
        }
    }

    public static void rollback(){
        isTransactionInProgress = false;
        tempMap = null;
        deletedKeys = null;
    }

    public static void main(String[] args) {
        set("abc", "def");
        System.out.println(get("abc")); // def
        System.out.println(get("efg")); // null
        delete("abc");
        delete("efg");
        System.out.println(get("abc")); // null

        // begin get set commit get
        begin();
        System.out.println(get("abc")); // null
        set("abc", "def");
        System.out.println(get("abc"));
        commit();
        System.out.println(get("abc")); // def

        // begin get set rollback get
        begin();
        System.out.println(get("abc")); // null
        set("abc", "def");
        System.out.println(get("abc"));
        rollback();
        System.out.println(get("abc")); // def
        // begin delete commit

        // begin delete rollback
    }
}
