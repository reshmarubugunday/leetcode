package gamestatus;
import java.util.*;
/*
### Problem Description

You need to implement a function that determines the status of a board game based on:
	•	A list of moves
	•	The size of the board

### Your Task

Your function should process the list of moves and return one of the following game statuses:
	•	"in progress"
	•	"player 1 is the winner"
	•	"player 2 is the winner"

Assume that all moves are valid and well-formed.
The board size will always be between 3×3 and 9×9.

You will be provided with some test cases to run and verify your implementation.

### Rules

Board Setup
    The board is always a square (e.g., 3×3, 6×6).

Turn Actions

    Each player’s turn consists of two types of actions, performed in this order:
        1.	Place: The player must add a piece to the board.
        2.	Topple: Zero or more times, the player redistributes pieces from a square they own in one direction.

Actions

    Place
        •	A piece may only be placed on:
        •	An open square (i.e., unoccupied).
        •	A square already occupied by that player.
        •	If the square is open, the player claims it by placing a piece.
        •	If the square already has pieces, an additional piece is added.

    Topple
        •	If a square contains 2 or more pieces, the player picks up all the pieces and distributes them one by one in a specific direction (left, right, up, or down).
        •	If a piece lands on a square occupied by an opponent, the piece is captured and now belongs to the player.
        •	Pieces that fall off the board are lost.

Game End

    The game ends when all pieces of one player are captured.

### Input Format

You will receive:
	1.	moveSet (array of strings) → List of moves
	2.	board_size (integer) → Size of the board

Move Types

Place Moves

    A place move is denoted by a "p" followed by the row and column coordinates:
	•	"p22" → Places a piece at (2,2).

Topple Moves

    A topple move is denoted by:
	•	"t" → Indicates a topple action.
	•	The row and column coordinates.
	•	The direction:
	•	"u" → Up
	•	"r" → Right
	•	"d" → Down
	•	"l" → Left

Example:
	•	"t00r" → The pieces at (0,0) topple to the right.
	•	If (0,0) had 3 pieces, they would end up at:
	•	(0,1)
	•	(0,2)
	•	(0,3)

Examples

Example 1

Input: ["p10", "p12", "p10", "t10r"]
Output: "player 1 is the winner"

Example 2

Input: ["p00", "p22", "p02", "p22", "t22u", "t02l"]
Output: "player 2 is the winner"

Constraints
	•	Execution time limit: 3 seconds (Java)
	•	Memory limit: 1 GB
	•	Input:
	•	array.string moveset
	•	integer board_size
	•	Output:
	•	string (Game status)

*/
/*
Go over each move set
Determine if it was a place or topple
Get coordinates
Place - Mark on board with each player pieces (Add coordinates to players map/array) - map<Int[], Integer>>
Topple - Get intended topple coordinates and check if that has a piece of the other player,
Remove coordinates from that player's map, add the coordinate to this player map/array
If player places & num of pieces > 1, check next turn for Place or topple. If place go to next players map, if topple perform topple operations

Monitor Player 1 or 2 using a flag - boolean
*/
public class Solution {
    public static String getGameStatus(String[] moveSet, int board_size) {
        Map<String, Integer> p1Coordinates = new HashMap<>();
        Map<String, Integer> p2Coordinates = new HashMap<>();

        int[][] board = new int[board_size][board_size];

        boolean isPlayer1Playing = false;

        for(String s: moveSet){
            char move = s.charAt(0);
            int i = s.charAt(1) - '0';
            int j = s.charAt(2) - '0';
            Character direction = null;
            if (move == 't'){
                direction = s.charAt(3);
            }
            String key = i + "," + j;
            if(move == 'p'){
                isPlayer1Playing = !isPlayer1Playing;
                if(isPlayer1Playing){
                    p1Coordinates.put(key, p1Coordinates.getOrDefault(key, 0) + 1);
                } else{
                    p2Coordinates.put(key, p2Coordinates.getOrDefault(key, 0) + 1);
                }
                board[i][j] += 1;
            }  else { // Topple Move
                int numOfPieces = board[i][j];
                if (numOfPieces < 2) continue; // Only topple if there are 2 or more pieces

                String playerKey = i + "," + j;

                // Remove all pieces from the original square
                board[i][j] = 0;
                if (isPlayer1Playing) {
                    p1Coordinates.remove(playerKey);
                } else {
                    p2Coordinates.remove(playerKey);
                }

                // Determine movement direction
                int di = 0, dj = 0;
                switch (direction) {
                    case 'r': dj = 1; break;
                    case 'l': dj = -1; break;
                    case 'u': di = -1; break;
                    case 'd': di = 1; break;
                    default:
                        break;
                }
                int r = i, c = j;
                for (int k = 0; k < numOfPieces; k++) {
                    r += di;
                    c += dj;
                    if (r < 0 || r >= board_size || c < 0 || c >= board_size) break; // Piece falls off

                    String newKey = r + "," + c;
                    board[r][c] += 1;

                    if (isPlayer1Playing) {
                        if (p2Coordinates.containsKey(newKey)) {
                            p2Coordinates.remove(newKey);
                        }
                        p1Coordinates.put(newKey, p1Coordinates.getOrDefault(newKey, 0) + 1);
                    } else {
                        if (p1Coordinates.containsKey(newKey)) {
                            p1Coordinates.remove(newKey);
                        }
                        p2Coordinates.put(newKey, p2Coordinates.getOrDefault(newKey, 0) + 1);
                    }
                }
            }
        }
        if(p1Coordinates.isEmpty()){
            return "Player 2 is the winner";
        } else if(p2Coordinates.isEmpty()){
            return "Player 1 is the winner";
        } else {
            return "in progress";
        }
    }

    public static void main(String[] args) {
        String[] moves1 = {"p10", "p12", "p10", "t10r"};
        String[] moves2 = {"p00", "p22", "p02", "p22", "t22u", "t02l"};
        String[] moves3 = {"p00", "p22", "p02", "p22", "t22u"};

        System.out.println(getGameStatus(moves1, 3)); // Output: player 1 is the winner
        System.out.println(getGameStatus(moves2, 3)); // Output: player 2 is the winner
        System.out.println(getGameStatus(moves3, 3)); // Output: in progress
    }
}
