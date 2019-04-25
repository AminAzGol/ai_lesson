package martijn.quoridor.brains;

import martijn.quoridor.model.Board;
import martijn.quoridor.model.Move;

import java.util.Comparator;

public class Node implements Comparable{
    public Board board;
    public Move move;
    public double cost;
    public boolean isJump;
    public Node(Board board, Move move){
        this.board = board;
        this.move = move;
    }
    public void setCost(double cost){
        this.cost  = cost;
    }

    public int compareTo(Object o) {
        Node node =(Node)o;
        if(this.cost  > node.cost)
            return 1;
        else
            return 0;
    }

}