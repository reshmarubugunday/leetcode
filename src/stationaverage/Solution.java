package stationaverage;
/* Company: Bloomberg
Keep track of average transit times between stations in a subway or other public transportation system.
sample transit map:
86th Street Station timestamp
      |
      |
o 59th Street Station
      |
      |
o-----------o Grand Central
Times Square   |
     |
   o 14th Street Station

Each rider has a transit card, which they swipe in when entering the transit system through the first station AND swipe out when exiting the transit system through the last station.

The cards have unique numbers, and we can assume that riders do not share or lose cards.

We wish to implement a class that uses entry/exit swipe information (card, time, station) to answer queries of average transit time between pairs of stations.

card details
timestamp
station

cardNumber - station in , timestamp - station out, timestamp
station - map<station, average time>
for station 1- no. of people started here
for station 2- no. of people left
class Journey{
startTime
endTime
startStation
endStation
}
Map<Station, Map<Station>AvgTime>>
users = map<card, Journey()>
start(card, time, station)
end(card, time, station)
*/
import java.util.*;


public class Solution {
    Map<String, Map<String,List<Integer>>> stationsMap;
    Map<Integer, Journey> activeUsers;
    static class Journey{
        int startTime;
        String startStation;

        Journey(int startTime, String startStation){
            this.startStation = startStation;
            this.startTime = startTime;
        }
    }
    Solution(){
        stationsMap = new HashMap<>();
        activeUsers = new HashMap<>();
    }

    public void startJourney(int card, int time, String station){
        if(activeUsers.containsKey(card)){
            throw new IllegalArgumentException("user already in transit");
        }

        activeUsers.put(card, new Journey(time, station));
        if(!stationsMap.containsKey(station)){
            stationsMap.put(station, new HashMap<>());
        }
    }

    public void endJourney(int card, int endTime, String endStation){
        if(!activeUsers.containsKey(card)){
            throw new IllegalArgumentException("user start time not found");
        }

        Journey j = activeUsers.get(card);
        int duration = endTime - j.startTime;
        Map<String, List<Integer>> endStations = stationsMap.get(j.startStation);
        if(!endStations.containsKey(endStation)){
            endStations.put(endStation, new ArrayList<>());
        }
        endStations.get(endStation).add(duration);
        activeUsers.remove(card);
    }

    public float getAvgBetweenStations(String startStation, String endStation){
        Map<String, List<Integer>> stations = stationsMap.getOrDefault(startStation, new HashMap<>());
        List<Integer> durations = stations.getOrDefault(endStation, new ArrayList<>());
        int sum = 0;
        for(int d: durations){
            sum += d;
        }
        return (float) sum / durations.size();
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        s.startJourney(1, 100, "A");
        s.startJourney(2, 200, "A");
        s.startJourney(3, 300, "A");
        s.endJourney(1, 300, "B");
        s.endJourney(2, 400, "B");
        s.endJourney(3, 500, "B");
        s.startJourney(1, 150, "A");
        s.startJourney(2, 200, "A");
        s.startJourney(3, 300, "A");
        s.endJourney(1, 300, "C");
        s.endJourney(2, 450, "C");
        s.endJourney(3, 400, "C");


        System.out.println("Average time to get from A to B: " + s.getAvgBetweenStations("A", "B"));
        System.out.println("Average time to get from A to C: " + s.getAvgBetweenStations("A", "C"));


    }
}
