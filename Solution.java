//Venkata Poreddy
//Finds shortest path for a knight from one corner of MxN array to the other
import java.util.*;

class Solution {

    private int[][] A;
    private int[] x_coords = {-2, -2, -1, -1, 1, 1, 2, 2}; //to easily get the valid knight moves
    private int[] y_coords = {-1, 1, -2, 2, -2, 2, -1, 1};

    Solution (int[][] chessBoard) {
        A = chessBoard;
    }

    private class KnightPath {
        public int coords[]; //current position of this path
        public int count; //how many moves this path has taken
        public ArrayList<int[]> visited; //previously visited positions of this path

        KnightPath(int[] coordinates, int traversal_count, ArrayList<int[]> visited_positions) {
            coords = coordinates;
            count = traversal_count;
            visited = visited_positions;
        } 
    }

    private ArrayList<KnightPath> getMoves(KnightPath position) { //gets the valid moves from a position
        ArrayList<KnightPath> moves = new ArrayList<KnightPath>();

        for (int i = 0; i < 8; i++) { //a knight can move 8 ways
            int[] move = {position.coords[0] + x_coords[i], position.coords[1] + y_coords[i]}; 

            if (isValid(move) && isUnvisited(position.visited, move)) {
                ArrayList<int[]> new_visited = new ArrayList<int[]>(position.visited);
                new_visited.add(position.coords);
                moves.add(new KnightPath(move, position.count+1, new_visited));
            }
        }
        return moves;
    }


    private boolean isUnvisited(ArrayList<int[]> visited, int[] move) { //checks if a move is in visited
        boolean unvisited = true;
        for (int[] s : visited) {
            if (s[0] == move[0] && s[1] == move[1]) { 
                unvisited = false; 
            }
        }
        return unvisited;
    }

    private boolean isValid(int[] move) { //checks if this position is out of bounds or contains non-zero
        int x_coord = move[0]; int y_coord = move[1];
        return (x_coord >= 0 && A.length > x_coord 
                 && y_coord >= 0 && A[0].length > y_coord
                 && A[x_coord][y_coord] == 0);
    }

    private boolean isDestination(int[] move) { //checks if we have reached the opposite corner
        return (move[0] == A.length-1 && move[1] == A[0].length-1);
    }

    public int solution() {  //using a breadth-first search, gets the possible paths to the destination
        Queue<KnightPath> q = new ArrayDeque<KnightPath>();
        ArrayList<Integer> counts = new ArrayList<Integer>();

        int[] origin = {0, 0};
        KnightPath start_position = new KnightPath(origin, 0, new ArrayList<int[]>());

        q.add(start_position); //enqueue the starting point

        while(!q.isEmpty()) {
            ArrayList<KnightPath> moves = getMoves(q.remove()); //dequeue child moves one by one
            if (!moves.isEmpty()) {
                for (KnightPath move : moves) {
                    if (isDestination(move.coords)) {
                        counts.add(move.count);
                    }
                    
                    else {
                        q.add(move); //enqueue the child move if it's valid and not destination
                    }
                }
            }
        }

        Collections.sort(counts); //get the minimum count, AKA the number of moves of fastest path
        return counts.get(0);
    }

    public static void main(String[] args) {
        int[][] A = new int[4][3]; 
        A[0][0] = 0;    A[0][1] = 0;    A[0][2] = 0;
        A[1][0] = 0;    A[1][1] = 0;    A[1][2] = 1;
        A[2][0] = 1;    A[2][1] = 0;    A[2][2] = 0;
        A[3][0] = 0;    A[3][1] = 0;    A[3][2] = 0; //test case provided in email

        System.out.println((new Solution(A)).solution());
    }
}