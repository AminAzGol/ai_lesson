package martijn.quoridor.brains;

import martijn.quoridor.model.Board;
import martijn.quoridor.model.Jump;
import martijn.quoridor.model.Move;
import martijn.quoridor.model.Player;

import java.util.ArrayList;

public class MinMax_single_file extends Brain {

    @Override
    public Move getMove(Board board) throws InterruptedException {
        MinMax minmax = new MinMax();
        return minmax.getNextMove(board);
    }
}

class MinMax {
    public int turn;
    public Move getNextMove(Board board){
        Board tempBorad = board.clone();
        int horizon = 2;
        turn = board.getTurnIndex();
        Node bestnode = min_max(tempBorad,false,horizon,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
        return bestnode.move;
    }
    public Node min_max(Board board, Boolean i_am_min, int horizon, double alpha, double beta){
        ArrayList<Node> childes = Successor.successor(board);
        ArrayList<Node> queue = new ArrayList<>();
        Node ret_node = childes.get(0);
        if( horizon == 0){
            for(int i =0 ; i < childes.size(); i++){
                Node node = new Node(childes.get(i).board, childes.get(i).move);
                double cost = Eval.evaluate(node.board, turn);
                if (node.move instanceof Jump)
                    cost++;
                node.setCost(cost);
                if(i_am_min && cost < ret_node.cost){
                    ret_node = node;
                }
                else if(!i_am_min && ret_node.cost < cost){
                    ret_node = node;
                }
            }
        }
        else{
            for (int i = 0; i < childes.size(); i++) {
                Node mychild = childes.get(i);
                if(!check_winner(mychild,turn)) {
                    Node retval = min_max(childes.get(i).board, !i_am_min, horizon - 1, alpha, beta);
                    mychild.setCost(retval.cost);
                }
                if(i_am_min && mychild.cost < beta){
                    beta  = mychild.cost;
                    ret_node = mychild;
                }
                else if (!i_am_min && mychild.cost > alpha){
                    alpha = mychild.cost;
                    ret_node = mychild;
                }
                if (alpha >= beta){
                    return ret_node;
                }
            }
        }
        return ret_node;
    }
    boolean check_winner(Node node,int turn){
        Board board = node.board;
        Player[] players = board.getPlayers();
        Player me,opponent;
        me = players[turn];
        opponent = players[1 - turn];
        if(me.isWinner()){
            node.cost = Double.POSITIVE_INFINITY;
            return true;
        }
        else if(opponent.isWinner()){
            node.cost = Double.NEGATIVE_INFINITY;
            return true;
        }
        return false;
    }
}

package martijn.quoridor.brains;

        import martijn.quoridor.model.Board;
        import martijn.quoridor.model.Orientation;
        import martijn.quoridor.model.Player;

public class Eval {
    public static double evaluate(Board board, int turn){
        Player[] players = board.getPlayers();
        Player me,opponent;
        me = players[turn];
        opponent = players[1- turn];
        if(me.isWinner())
            return Double.POSITIVE_INFINITY;
        else if(opponent.isWinner())
            return Double.NEGATIVE_INFINITY;
        Orientation[] my_steps = me.findGoal();
        Orientation[] opponent_steps = opponent.findGoal();
        return opponent_steps.length - my_steps.length;
    }
}
package martijn.quoridor.brains;

        import javafx.util.Pair;
        import martijn.quoridor.model.*;

        import javax.swing.*;
        import java.util.ArrayList;
        import java.util.Set;


public class Successor {
    public static ArrayList<Node> successor(Board board){
        Player player = board.getTurn();
        Board tempBoard ;
        tempBoard =  board.clone();
        ArrayList<Node>  childern = new ArrayList<>();
        Set<Position> jumps = player.getJumpPositions();
//        for (Position jump : jumps){
//            tempBoard =  board.clone();
//            Jump move = new Jump(jump);
//            tempBoard.move(move);
//            childern.add(new Pair<> (tempBoard,move));
//        }
        tempBoard =  board.clone();
        tempBoard.move(new Jump(player.stepToGoal()));
        childern.add(new Node(tempBoard,new Jump(player.stepToGoal())));

        for (int i=0; i< board.getWidth() ; i++){
            for (int j=0; j <board.getHeight();j++){
                Position position = new Position(j,i);
                if (tempBoard.containsWallPosition(position)) {
                    if (tempBoard.getWall(position) == null) {
                        tempBoard = board.clone();
                        tempBoard.setWall(position, Wall.HORIZONTAL);
                        PutWall move = new PutWall(position, Wall.HORIZONTAL);
//                        PutWall move = new PutWall(position, Wall.HORIZONTAL);
                        if (move.isLegal(board)){
                            tempBoard.move(move);
                            childern.add(new Node(tempBoard, move));
                        }

                        tempBoard = board.clone();
                        move = new PutWall(position, Wall.VERTICAL);
                        if(move.isLegal(board)) {
                            tempBoard.move(move);
                            childern.add(new Node(tempBoard, move));
                        }
                    }
                }
            }
        }
//        for (int k=0; k<board.getPlayers().length;k++){
//            Player[] players = board.getPlayers();
//            Position pos = players[k].getPosition();
//            int index = 1;
//            int i=pos.getX()-index;
//            if(i < 0)
//                i=0;
//            for ( ; i< pos.getX()+index ; i++){
//                int j=pos.getY()-index;
//                if(j<0)
//                    j=0;
//                for (; j <pos.getY()+index ;j++){
//                    Position position = new Position(j,i);
//                    if (tempBoard.containsWallPosition(position)) {
//                        if (tempBoard.getWall(position) == null) {
//                            tempBoard = board.clone();
//                            tempBoard.setWall(position, Wall.HORIZONTAL);
//                            PutWall move = new PutWall(position, Wall.HORIZONTAL);
//                            if (move.isLegal(board)){
//                                tempBoard.move(move);
//                                childern.add(new Node(tempBoard, move));
//                            }
//                            tempBoard = board.clone();
//                            move = new PutWall(position, Wall.VERTICAL);
//                            if(move.isLegal(board)) {
//                                tempBoard.move(move);
//                                childern.add(new Node(tempBoard, move));
//                            }
//                        }
//                    }
//                }
//            }
//        }

        if (childern.size() == 0)
            System.out.println("here");
        return childern;

    }


}
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
