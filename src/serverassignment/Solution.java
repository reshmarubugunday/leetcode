package serverassignment;
/*
Company: LinkedIn (Onsite)
You are given two 0-indexed integer arrays servers  and tasks  of lengths n and m respectively.
servers[i] is the weight of the ith server, and tasks[j] is the time needed to process the jth task in seconds.
Tasks are assigned to the servers using a task queue. Initially, all servers are free, and the queue is empty.

At second j, the jth task is inserted into the queue (starting with the 0th task being inserted at second 0).

As long as there are free servers and the queue is not empty, the task in the front of the queue will be
assigned to a free server with the smallest weight, and in case of a tie, it is assigned to a free server
with the smallest index.

If there are no free servers and the queue is not empty, we wait until a server becomes free and immediately
assign the next task.

If multiple servers become free at the same time, then multiple tasks from the queue will be assigned in order
of insertion following the weight and index priorities above.

A server that is assigned task j at second t will be free again at second t + tasks[j].

Build an array answer of length m, where ans[j] is the index of the server the jth task will be assigned to.

Example Input & Output
Input:  servers = [3,3,2], tasks = [1,2,3,2,1,2]
Output:  [2,2,0,2,1,2]

Explanation:

Events in chronological order go as follows:
- At second 0, task 0 is added and processed using server 2 until second 1.
- At second 1, server 2 becomes free. Task 1 is added and processed using server 2 until second 3.
- At second 2, task 2 is added and processed using server 0 until second 5.
- At second 3, server 2 becomes free. Task 3 is added and processed using server 2 until second 5.
- At second 4, task 4 is added and processed using server 1 until second 5.
- At second 5, all servers become free. Task 5 is added and processed using server 2 until second 7.
*/
import java.util.Arrays;
import java.util.PriorityQueue;

class Server {
    int weight;
    int index;

    public Server(int weight, int index) {
        this.weight = weight;
        this.index = index;
    }
}

class BusyServer {
    int freeTime;
    int weight;
    int index;

    public BusyServer(int freeTime, int weight, int index) {
        this.freeTime = freeTime;
        this.weight = weight;
        this.index = index;
    }
}

public class Solution {

    public static int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;
        int[] ans = new int[m];

        // PriorityQueue for free servers: sort by weight then index.
        PriorityQueue<Server> freeServers = new PriorityQueue<>((a, b) -> {
            if (a.weight == b.weight) {
                return a.index - b.index;
            }
            return a.weight - b.weight;
        });

        // Initialize free servers
        for (int i = 0; i < n; i++) {
            freeServers.offer(new Server(servers[i], i));
        }

        // PriorityQueue for busy servers: sort by freeTime, then weight then index.
        PriorityQueue<BusyServer> busyServers = new PriorityQueue<>((a, b) -> {
            if (a.freeTime == b.freeTime) {
                if (a.weight == b.weight) {
                    return a.index - b.index;
                }
                return a.weight - b.weight;
            }
            return a.freeTime - b.freeTime;
        });

        // Simulation time starts at 0.
        long time = 0; // Use long to avoid overflow in time calculation.

        // Process each task in order.
        for (int j = 0; j < m; j++) {
            // Each task j is queued at time j.
            time = Math.max(time, j);

            // Release all servers that have completed their tasks by current time.
            while (!busyServers.isEmpty() && busyServers.peek().freeTime <= time) {
                BusyServer bs = busyServers.poll();
                freeServers.offer(new Server(bs.weight, bs.index));
            }
            System.out.println(freeServers);

            // If no server is free, fast-forward time to the next available server.
            if (freeServers.isEmpty()) {
                BusyServer bs = busyServers.poll();
                time = bs.freeTime;
                freeServers.offer(new Server(bs.weight, bs.index));
                // Check if any other servers become free at this updated time.
                while (!busyServers.isEmpty() && busyServers.peek().freeTime <= time) {
                    bs = busyServers.poll();
                    freeServers.offer(new Server(bs.weight, bs.index));
                }
            }

            // Assign the next task to the best available server.
            Server server = freeServers.poll();
            ans[j] = server.index;
            busyServers.offer(new BusyServer((int)(time + tasks[j]), server.weight, server.index));
            System.out.println(busyServers);
        }

        return ans;
    }

    // Example testing with the given sample input.
    public static void main(String[] args) {
        int[] servers = {3, 3, 2};
        int[] tasks = {1, 2, 3, 2, 1, 2};
        int[] assignment = assignTasks(servers, tasks);
        System.out.println(Arrays.toString(assignment)); // Expected output: [2, 2, 0, 2, 1, 2]
    }
}