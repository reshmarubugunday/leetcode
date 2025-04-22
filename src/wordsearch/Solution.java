package wordsearch;
// Company: Bloomberg (On-site)
//Problem Description

// Given a 2-D array of characters as a board, and a string word as the target, write a function to check if the target word can be found on the board. The word has to be composed by a series of adjacent characters (horizontally or vertically neighbors) on the board, and one character could only be used once.

// Sample inputs - Expected outputs
// Given board is:
// board = {
//   ['B', 'C', 'F', 'E'],
//   ['E', 'A', 'B', 'L'],
//   ['B', 'C', 'D', 'O'],
//   ['E', 'B', 'M', 'O'],
//   ['R', 'G', 'D', 'E'],
// }
// if word = 'BLOOMBERG', return true because it can be composed with these characters from board:
// // word = 'BLOOMBERG':
// {
//   [   ,    ,    ,    ],
//   [   ,    , 'B', 'L'],
//   [   ,    ,    , 'O'],
//   ['E', 'B', 'M', 'O'],
//   ['R', 'G',    ,    ],
// }
// if word = 'BOB', return false because it can not be composed by any combination of characters from board.
// if word = 'BEABLE', return true because it can be composed with these characters from board:
// // word = 'BEABLE':
// {
//   ['B',    ,    , 'E'],
//   ['E', 'A', 'B', 'L'],
//   [   ,    ,    ,    ],
//   [   ,    ,    ,    ],
//   [   ,    ,    ,    ],
// }
// Note if word = 'ABCD', return false because there's no consecutive adjacent characters that form 'ABCD'.
// if word = 'ABDC', return true because:
// // word = 'ABDC':
// {
//   [   ,    ,    ,    ],
//   [   , 'A', 'B',    ],
//   [   , 'C', 'D',    ],
//   [   ,    ,    ,    ],
//   [   ,    ,    ,    ],
// }

public class Solution {
    public static void main(String[] args){
        Solution s = new Solution();
        Character[][] board = new Character[][]{
                {'B', 'C', 'F', 'E'},
                {'E', 'A', 'B', 'L'},
                {'B', 'C', 'D', 'O'},
                {'E', 'B', 'M', 'O'},
                {'R', 'G', 'D', 'E'},
        };
        System.out.println(s.findWord("BLOOMBERG", board));
    }
    public boolean findWord(String word, Character[][] board){
        int k = 0;
        boolean[][] visited = new boolean[board.length][board[0].length];
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if(word.charAt(k) == board[i][j]){
                    if(dfs(board, visited, word, k, i, j)) return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(Character[][] board,  boolean[][] visited, String word, int k, int i, int j){
        if(k == word.length()) return true;
        if(i >= 0 && i < board.length && j >= 0 && j < board[0].length && board[i][j] == word.charAt(k) && !visited[i][j]){
            visited[i][j] = true;
            if(
                    dfs(board, visited, word, k + 1, i + 1, j) ||
                            dfs(board, visited, word, k + 1, i - 1, j) ||
                            dfs(board, visited, word, k + 1, i, j + 1) ||
                            dfs(board, visited, word, k + 1, i, j - 1)
            ) return true;
            visited[i][j] = false;
            return false;
        }
        return false;
    }
}
