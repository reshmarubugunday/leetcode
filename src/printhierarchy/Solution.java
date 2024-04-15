package printhierarchy;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/*
Input:
Employee ID, Manager ID, Employee Name
[
    [8, 8, "Alice"],
    [2, 8, "Bob"],
    [3, 2, "Emp3"],
    [4, 3, "Emp4"],
    [5, 4, "Emp5"],
    [6, 3, "Emp6"],
    [7, 8, "Emp7"]
]

hashMap - EmpID : Name

hashMap - empID -> List of people working under them

8 -> [2, 7]
2 -> [3]
3 -> [4, 6]
4 -> [5]



Output:
Alice
  Bob
    Emp3
      Emp4
        Emp5
      Emp6
  Emp7

Given:
- Input is list(lists)
- print statements - void
*/

public class Solution {
    public static void main(String[] args) {

        List<List<String>> employees = new LinkedList<>();
        employees.add(new LinkedList<>());
        employees.get(0).add("8");
        employees.get(0).add("8");
        employees.get(0).add("Alice");
        employees.add(new LinkedList<>());
        employees.get(1).add("2");
        employees.get(1).add("8");
        employees.get(1).add("Bob");
        employees.add(new LinkedList<>());
        employees.get(2).add("3");
        employees.get(2).add("2");
        employees.get(2).add("Emp3");
        employees.add(new LinkedList<>());
        employees.get(3).add("4");
        employees.get(3).add("3");
        employees.get(3).add("Emp4");
        employees.add(new LinkedList<>());
        employees.get(4).add("5");
        employees.get(4).add("4");
        employees.get(4).add("Emp5");
        employees.add(new LinkedList<>());
        employees.get(5).add("6");
        employees.get(5).add("3");
        employees.get(5).add("Emp6");
        employees.add(new LinkedList<>());
        employees.get(6).add("7");
        employees.get(6).add("8");
        employees.get(6).add("Emp7");

        printHierarchy(employees);
    }

    public static void printHierarchy(List<List<String>> employees){
        HashMap<String, String> employeeDetails = new HashMap<>();
        HashMap<String, Set<String>> hierarchy = new HashMap<>();
        List<String> managers = new LinkedList<>();

        for(List<String> emp : employees) {
            employeeDetails.put(emp.get(0), emp.get(2));
            if (emp.get(0).equals(emp.get(1))) {
                managers.add(emp.get(0));
            } else {
                hierarchy.putIfAbsent(emp.get(1), new HashSet<>());
                hierarchy.get(emp.get(1)).add(emp.get(0));
            }
        }

        for(String manager: managers) {
            dfs(hierarchy, employeeDetails, manager, "");
        }

    }

    public static void dfs(Map<String, Set<String>> map, Map<String, String> empDeets, String key, String space){
        System.out.println(space+empDeets.get(key));
        for(String emp : map.getOrDefault(key, new HashSet<>())) {
            dfs(map, empDeets, emp, space + " ");
        }
    }

}
