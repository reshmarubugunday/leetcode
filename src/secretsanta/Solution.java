package secretsanta;
import java.util.*;
/**
Company: Nextdoor
 The feed infra team has decided to participate in a “Secret Santa” style gift exchange this holiday season. Your task is to write some code that makes coordinating this effort easier. The rules of Secret Santa are as follows:

 Each gift giver must be assigned one gift recipient

 A recipient can be assigned only once

 A gift giver cannot be their own recipient

 Write a function that takes in a collection of gift givers and assigns each one a gift recipient.

 Sample Input: ['Frank', 'Amy', 'Brian', 'Aditi', 'Gireesh', 'Justin', 'Xiao', 'Nick' ]

 output : giver : recipient
 F : Amy
 Amy :  Brian
 B : Adi
 Adi : G
 G : J
 J : X
 X : N
 N : K
 K : F

 Input : List<String>
 Output : Map<String, String>

 A, B, C, D
 A->B, B->C, C->A, D


 {
 'Frank': ['Aditi'],
 'Amy': ['Nick'],
 'Aditi': ['Justin'],
 'Brian': ['Amy'],
 'Xiao': ['Gireesh'],
 'Gireesh': ['Frank'],
 'Justin': ['Brian'],
 'Nick': ['Xiao'],
 }

 -For each person, keep a set of valid recipients (all recipients - assigned - blocklist)
 -Order each person on size of valid recipients (pickiest -> least picky)
 -Find recipient for pickiest person first
 -When we assign a person, refresh every person's valid set + re-sort by pickiest


 */
// Main class should be named 'Solution' and should not be public.

public class Solution {

    /**
     * Assigns each participant a recipient while respecting block lists.
     *
     * @param participants List of participant names.
     * @param blockLists   Mapping from a giver to a list of recipients they cannot gift to.
     * @return A map of gift giver to gift recipient.
     */
    public static Map<String, String> secretSanta(List<String> participants, Map<String, List<String>> blockLists) {
        if (participants == null || participants.size() < 2) {
            throw new IllegalArgumentException("Need at least two participants for Secret Santa.");
        }
        Map<String, String> assignments = new HashMap<>();
        if (!assignRecipients(participants, blockLists, assignments)) {
            throw new RuntimeException("No valid Secret Santa assignment found.");
        }
        return assignments;
    }

    /**
     * Recursive backtracking method to assign recipients.
     *
     * @param participants List of participant names.
     * @param blockLists   Mapping of giver to blocked recipients.
     * @param assignments  Current assignments mapping.
     * @return true if a complete assignment is found, false otherwise.
     */
    private static boolean assignRecipients(List<String> participants,
                                            Map<String, List<String>> blockLists,
                                            Map<String, String> assignments) {
        // Base case: every giver has been assigned a recipient.
        if (assignments.size() == participants.size()) {
            return true;
        }

        // Determine the unassigned giver with the fewest valid recipients (pickiest).
        String pickiest = null;
        List<String> availableForPickiest = null;
        int minOptions = Integer.MAX_VALUE;

        for (String giver : participants) {
            if (assignments.containsKey(giver)) {
                continue;
            }
            List<String> available = new ArrayList<>();
            for (String candidate : participants) {
                // A giver cannot be assigned to themselves.
                if (candidate.equals(giver)) {
                    continue;
                }
                // Check block list: if candidate is blocked for this giver, skip.
                List<String> blocks = blockLists.getOrDefault(giver, Collections.emptyList());
                if (blocks.contains(candidate)) {
                    continue;
                }
                // If candidate is already assigned as a recipient, skip.
                if (assignments.containsValue(candidate)) {
                    continue;
                }
                available.add(candidate);
            }
            // If no valid recipient exists for this giver, backtrack.
            if (available.isEmpty()) {
                return false;
            }
            // Update the pickiest giver.
            if (available.size() < minOptions) {
                minOptions = available.size();
                pickiest = giver;
                availableForPickiest = available;
            }
        }

        // Try assigning each available candidate to the pickiest giver.
        if(availableForPickiest != null){
            for (String candidate : availableForPickiest) {
                assignments.put(pickiest, candidate);
                if (assignRecipients(participants, blockLists, assignments)) {
                    return true;
                }
                // Backtrack: remove the assignment and try the next candidate.
                assignments.remove(pickiest);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // Sample list of participants.
        List<String> participants = Arrays.asList(
                "Frank", "Amy", "Brian", "Aditi", "Gireesh", "Justin", "Xiao", "Nick"
        );

        // Block list mapping: each key's list contains the names they cannot give a gift to.
        Map<String, List<String>> blockLists = new HashMap<>();
        blockLists.put("Frank", List.of("Aditi", "Nick", "Justin","Amy", "Gireesh","Brian"));
        blockLists.put("Amy", List.of("Nick"));
        blockLists.put("Aditi", List.of("Justin"));
        blockLists.put("Brian", List.of("Amy"));
        blockLists.put("Xiao", List.of("Gireesh"));
        blockLists.put("Gireesh", List.of("Frank"));
        blockLists.put("Justin", List.of("Brian"));
        blockLists.put("Nick", List.of("Xiao"));

        // Get the assignment.
        Map<String, String> assignments = secretSanta(participants, blockLists);
        System.out.println("Secret Santa Assignment with Block Lists:");
        for (Map.Entry<String, String> entry : assignments.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}