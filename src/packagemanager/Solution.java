package packagemanager;


import java.util.ArrayList;
import java.util.*;
import java.util.HashMap;

/*
* A : B, C
* D : E, F, A
*
*
* input List<String> = A, D
* output List<String> = B, C, A, E, F, D
*
*
* */
public class Solution {
    static HashMap<String, List<String>> map = new HashMap<>();
    public static void main(String[] args) {

        map.put("A",new ArrayList<>(Arrays.asList("B", "C")));
        map.put("D",new ArrayList<>(Arrays.asList("E", "F", "A")));

        System.out.println(installApps(new ArrayList<>(Arrays.asList("A", "D"))));
    }

    public static List<String> installApps(List<String> input){
        List<String> result = new ArrayList<>();
        Map<String, Boolean> visited = new HashMap<>();
        Queue<String> q = new ArrayDeque<>(input);

        while(!q.isEmpty()){
            String val = q.poll();
            List<String> dVal = map.get(val);
            if(dVal != null){
                for(String d: dVal){
                    if(!visited.getOrDefault(d, false)) {
                        result.add(d);
                        q.add(d);
                    }
                }
                if(!visited.getOrDefault(val, false)){
                    result.add(val);
                }
            }
            visited.put(val, true);
        }
        return result;
    }
}
