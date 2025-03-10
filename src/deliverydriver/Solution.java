package deliverydriver;

/*
PART 1
Food delivery companies employ tens of thousands of delivery drivers who each submit hundreds of deliveries
per week. Delivery details are automatically sent to the system immediately after the delivery

Delivery drivers have different hourly payment rates, depending on their performance.
Drivers can take on, and be paid for, multiple deliveries simultaneously.

If a driver is paid $10.00 per hour, and a delivery takes 1 hour and 30 minutes, the driver is paid $15.00
for that delivery.

We are building a dashboard to show a single number - the total cost of all deliveries - on screens in the
accounting department

At first, we want the following functions:
* `addDriver(driverId [integer], usdHourlyRate [float])`
    - The given driver will not already be in the system
* `recordDelivery (driverId [integer], startTime, endTime)`
    - Discuss the time format you choose
    - Times require minimum one-second precision
    - The given driver will already.bę in the system
    - All deliveries will be recorded immediately after the delivery is completed -
    - No delivery will exceed 3 hours
* `getTotalCost()`
    - Return the total, aggregated cost of all drivers' deliveries recorded in the system.
    - For example, return 135.30 if one driver is in the system and has a total cost of 100.30 USD and another
    driver is in the system and has a total cost of 35.00 USD.
    - This will be used for a live dashboard
    - Do not worry about exact formatting
All inputs will be valid.

Share any decisions or assumptions you make.
If you do anything differently in this interview than you would in production, share that.

Before coding, let's discuss how you will store the time data and why.

We want to see good OOP practices.
You may look up syntax using a search engine, as long as you are sharing your screen.
*/

/*
PART 2

The analytics team uses the live dashboard function you built to see how much was paid and how much was left unpaid

Add the following function

* `payUpTo(endTime)`
    - Pay all drivers for the recorded deliveries which ended up to and including the end time

* `getTotalCostUnpaid()`
    - Return the total, aggregated cost of all the drivers' deliveries which have not been paid out

The solution does not have to be thread-safe or handle concurrency
*/

/*
PART 3

Some cities have restrictions on the total number of drivers can be active at any time.

As one part of understanding our compliance with restrictions, our team needs to understand
the maximum number of drivers who have been actively making deliveries simultaneously.

For one of our tasks in this large project, write a new function:

* `maxSimultaneousDrivers24hBefore(long endTime)
    - Returns the maximum number of drivers who were active in the 24 hours before the given
    end time.

The solution does not need to be thread-safe or handle concurrency.
*/


import java.util.HashMap;
import java.util.*;
import java.math.*;

public class Solution {
    static BigDecimal totalCost;
    static BigDecimal paidCost;
    static Map<Integer, BigDecimal> rateMap;
    static Map<Integer, BigDecimal> costMap;
    List<Delivery> deliveries;
    TreeMap<Long, Integer> overlapMap;

    static class Delivery{
        int driverId;
        long startTime;
        long endTime;
        BigDecimal cost;

        Delivery(int driverId, long startTime, long endTime, BigDecimal cost){
            this.driverId = driverId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.cost = cost;
        }
    }

    public Solution() {
        totalCost = BigDecimal.valueOf(0f);
        paidCost = BigDecimal.valueOf(0f);
        deliveries = new ArrayList<>();
        overlapMap = new TreeMap<>();
        rateMap = new HashMap<>();
        costMap = new HashMap<>();
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        s.addDriver(1, 35.10f);
        s.addDriver(2, 15.15f);
        s.addDriver(3, 8.55f);
        s.addDriver(4, 11.28f);

        s.recordDelivery(1, 5460, 9060);
        s.recordDelivery(2, 5700, 11100);
        s.recordDelivery(2, 11100, 12900);
        System.out.println("Total Cost: " + s.getTotalCost());
        System.out.println("Paid Cost: " + s.payUpTo(11100));
        System.out.println("Unpaid Cost: " + s.getTotalUnpaidCost());

        s.recordDelivery(2, 97500, 99300);
        s.recordDelivery(3, 98000, 102000);
        s.recordDelivery(3, 97051, 104445);
        s.recordDelivery(1,104450, 104600);
        s.recordDelivery(4, 99000, 105000);

        System.out.println(s.maxSimultaneousDrivers24hBefore(183600));
        System.out.println(s.maxSimultaneousDrivers24hBefore(104700));
        System.out.println(s.maxSimultaneousDrivers24hBefore(1));
    }

    public void addDriver(int driverId, float usdHourlyRate) {
        rateMap.put(driverId, BigDecimal.valueOf(usdHourlyRate));
    }

    public void recordDelivery(int driverId, long startTime, long endTime) {
        BigDecimal hoursWorked = BigDecimal.valueOf((endTime - startTime) / (60.0f * 60.0f));
        BigDecimal cost = (rateMap.get(driverId).multiply(hoursWorked));
        totalCost = totalCost.add(cost);
        costMap.put(driverId, cost.add(costMap.getOrDefault(driverId, BigDecimal.valueOf(0.0f))));
        deliveries.add(new Delivery(driverId, startTime, endTime, cost));

        overlapMap.put(startTime, overlapMap.getOrDefault(startTime, 0) + 1);
        overlapMap.put(endTime, overlapMap.getOrDefault(endTime, 0) - 1);
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public BigDecimal payUpTo(long endTime){
        BigDecimal amountToBePaid = BigDecimal.valueOf(0f);
        for(int i=0; i<deliveries.size(); i++){
            Delivery d = deliveries.get(i);
            if(d.endTime <= endTime){
                amountToBePaid = amountToBePaid.add(d.cost);
                deliveries.remove(i);
            } else break;
        }
        paidCost = paidCost.add(amountToBePaid);
        return amountToBePaid;
    }

    public BigDecimal getTotalUnpaidCost(){
        return totalCost.subtract(paidCost);
    }

    public int maxSimultaneousDrivers24hBeforeNotEfficient(long endTime) {
        long startWindow = endTime - 24 * 60 * 60; // 24 hours before in seconds
        int maxDrivers = 0;
        int currentDrivers = 0;
        List<Long> startTimes = new ArrayList<>();
        List<Long> endTimes = new ArrayList<>();

        for (Delivery d : deliveries) {
            if (d.endTime > startWindow && d.startTime < endTime) {
                startTimes.add(d.startTime);
                endTimes.add(d.endTime);
            }
        }

        int i=0 , j=0;

        while(i < startTimes.size() && j < endTimes.size()){
            if(startTimes.get(i) < endTimes.get(j)){
                currentDrivers++;
                i++;
            } else if(startTimes.get(i) > endTimes.get(j)){
                currentDrivers--;
                j++;
            }
            maxDrivers = Math.max(maxDrivers, currentDrivers);
        }
        return maxDrivers;
    }

    public int maxSimultaneousDrivers24hBefore(long endTime) {
        long startTime = endTime - (24 * 60 * 60);
        int currentDrivers = 0, maxDrivers = 0;
        for (Map.Entry<Long, Integer> entry : overlapMap.entrySet()) {
            long time = entry.getKey();
            int change = entry.getValue();
            if (time > endTime) break;
            if (time >= startTime) {
                currentDrivers += change;
                maxDrivers = Math.max(maxDrivers, currentDrivers);
            }
        }

        return currentDrivers > 0 ? (maxDrivers - currentDrivers) : maxDrivers;
    }
}