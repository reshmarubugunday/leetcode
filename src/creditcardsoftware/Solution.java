package creditcardsoftware;

import java.util.HashMap;
import java.util.*;
import java.util.Map;
import java.util.TreeMap;

/*
Objective
You are developing software for a credit card provider. Your task is to implement a program that:
	• Adds new credit card accounts.
	• Processes charges and credits against the accounts.
	• Displays a summary of the account balances.

Input Specification

You are given a list of commands in the format:
	1.  Add <card_holder> <card_number> <limit>
	    •	Creates a new credit card for the given card_holder with the specified card_number and limit.
	    •	It is guaranteed that the card_holder did not have a credit card before this operation.
	    •	New cards start with a 0 balance.
	    •	Card Number Validation:
	    •	Must contain only digits [0-9].
	    •	Must be between 12 and 16 characters long.
	    •	(Bonus) Validate the card number using the Luhn 10 algorithm.
	2.	Charge <card_holder> <amount>
	    •	Increases the balance of the card associated with card_holder by amount.
	    •	If the charge would raise the balance above the limit, it is ignored.
	    •	Charges against invalid cards are ignored.
	3.	Credit <card_holder> <amount>
	    •	Decreases the balance of the card associated with card_holder by amount.
	    •	If a credit would drop the balance below 0, it results in a negative balance.
	    •	Credits against invalid cards are ignored.

Credit Card Validation

To ensure that a credit card number is valid, we apply the following checks:
	1.	The card number must contain only digits [0-9] and be between 12 and 16 characters long.
	2.	(Bonus) The card number should be validated using the Luhn 10 algorithm:
	    •	Start from the rightmost digit (check digit) and move left.
	    •	Double the value of every second digit.
	    •	If the result of doubling exceeds 9, sum the digits of the product.
	    •	Compute the total sum of all digits.
	    •	If (total sum) % 10 == 0, the number is valid. Otherwise, it is invalid.

Example Luhn validation for number = 79927398713:
Luhn(number) = 7+9+9+4+7+6+9+7+7 = 65
65 % 10 != 0, so the number is invalid.

Output Specification

Return an array of [card_holder, balance] pairs in lexicographical order of card_holder names.
	•	If the card is invalid, display "error" instead of the balance.
	•	Otherwise, show the final balance after all operations.

Constraints
	•	Execution Time Limit: 3 seconds (Java)
	•	Guaranteed Constraints:
	•	1 ≤ operations.length ≤ 10
	•	3 ≤ operations[i].length ≤ 4
	•	Each cardholder’s name is at most 10 characters long.
	•	Each card number is between 12-16 digits.
	•	Limits and amounts are within [1, 3000].
*/
public class Solution {
    private static class Account {
        String cardHolder;
        String accNum;
        Double limit;
        Double amountUsed;

        public Account(String cardHolder, String accNum, String limit) {
            this.accNum = accNum;
            this.cardHolder = cardHolder;
            this.limit = limit.isEmpty()? 0D : Double.parseDouble(limit);
            this.amountUsed = 0D;
        }
    }

    static TreeMap<String, Account> accountMap = new TreeMap<>();

    public static void main(String[] args) {
        add("Tom", "4111111111111111", "1000");
        add( "Lisa", "5454545454545454", "3000");
        add( "Quincy", "12345678901234", "2000");
        charge( "Tom", "500");
        charge("Tom", "800");
        charge("Lisa", "7");
        credit( "Lisa", "100");
        credit( "Quincy", "200");
        System.out.println(balance()); // output : [[Lisa, -93.0], [Quincy, error], [Tom, 500.0]]
    }

    public static void add(String cardHolder, String accNum, String limit){
        Account newAcc;
        if(!isAccNumValid(accNum)){
            newAcc = new Account(cardHolder, "", "");
        } else {
            newAcc = new Account(cardHolder, accNum, limit);
        }
        accountMap.put(cardHolder, newAcc);
    }

    public static void charge(String cardHolder, String amount){
        Account acc = accountMap.get(cardHolder);
        double dAmount = Double.parseDouble(amount);
        if(dAmount <= (acc.limit - acc.amountUsed)){
            acc.amountUsed += dAmount;
        }
        accountMap.put(cardHolder, acc);
    }

    public static void credit(String cardHolder, String amount){
        double dAmount = Double.parseDouble(amount);
        Account acc = accountMap.get(cardHolder);
        acc.amountUsed -= dAmount;
        accountMap.put(cardHolder, acc);
    }

    private static boolean isAccNumValid(String accNum) {
        if(accNum.length() < 12 || accNum.length() > 16){
            return false;
        }
        for(Character c : accNum.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return isLuhnValid(accNum);
    }

    private static boolean isLuhnValid(String accNum){
        int[] temp = new int[accNum.length()];
        int i=0;
        for(Character c:accNum.toCharArray()){
            temp[i] = Character.getNumericValue(c);
            i++;
        }
        i = accNum.length() - 1;
        boolean isSecondDigit = false;
        while(i >= 0){
            if(isSecondDigit){
                int tempDigit = temp[i] * 2;
                while(tempDigit > 9){
                    int newDigitOne = tempDigit / 10;
                    int newDigitTwo = tempDigit % 10;
                    tempDigit = newDigitOne + newDigitTwo;
                }
                temp[i] = tempDigit;
                i--;
                isSecondDigit = false;
            } else {
                isSecondDigit = true;
                i--;
            }
        }
        int sum = 0;
        for(int s : temp){
            sum+= s;
        }
        return (sum % 10) == 0;
    }

    public static List<List<String>> balance(){
        List<List<String>> result = new ArrayList<>();
        for(Map.Entry<String, Account> entry : accountMap.entrySet()){
            String cardHolder = entry.getKey();
            Account acc = entry.getValue();

            List<String> temp = new ArrayList<>();
            temp.add(cardHolder);
            if(acc.accNum.isEmpty()){
                temp.add("error");
            } else {
                temp.add(Double.toString(acc.amountUsed));
            }
            result.add(temp);
        }
        return result;
    }


}
