package inmemoryqueuing;
/*
Company: Tesla
 In Memory Queueing
 Given an incoming stream of data received over a websocket, implement an internal queueing mechanism.

 {"queue_name":"a","value:":"some_value"}

 {"queue_name":"a","value":"some_value_2"}

 {"queue_name":"b","value":"some_value”}

 Create a queue mechanism to keep a local queue of at most N items per queue. You only need to store the “value” as part of the item stored in the queue.

 a| some_value, some_value_2
 b| some_value

*/

import java.util.*;

class Solution {
    Map<String, Queue<String>> queueMap;
    int QueueCapacity;
    Solution(int capacity){
        this.queueMap = new HashMap<>();
        this.QueueCapacity = capacity;
    }
    public void queue(String queueName, String value){
        if(!queueMap.containsKey(queueName)){
            queueMap.put(queueName, new LinkedList<>());
        }

        Queue<String> q = queueMap.get(queueName);
        if(q.size() == this.QueueCapacity){
            q.poll();
        }
        q.add(value);
        return;
    }
    public static void main(String[] args) {
        Solution s = new Solution(5);

        s.queue("a", "value1");
        s.queue("a","some_value_2");
        s.queue("a","some_value_3");
        s.queue("b","some_value_1");
        s.queue("b","some_value_2");
        s.queue("a","some_value_4");
        s.queue("a","some_value_5");
        s.queue("a","some_value_6");

        System.out.println(s.queueMap);
        System.out.println(s.queueMap.get("a"));
        System.out.println(s.queueMap.get("b"));
    }
}
