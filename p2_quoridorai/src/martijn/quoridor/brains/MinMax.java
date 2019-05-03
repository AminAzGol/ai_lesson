package martijn.quoridor.brains;

import javafx.util.Pair;
import martijn.quoridor.model.Board;
import martijn.quoridor.model.Jump;
import martijn.quoridor.model.Move;
import martijn.quoridor.model.Player;

import java.util.ArrayList;
import java.util.Timer;

public class MinMax {
    public int turn;
    public long timer;
    public Move getNextMove(Board board){
        Board tempBorad = board.clone();
        int horizon = 2;
        turn = board.getTurnIndex();
        timer = System.currentTimeMillis();
        Node bestnode = min_max(tempBorad,false,horizon,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
        System.out.println(System.currentTimeMillis() - timer);
        return bestnode.move;
    }
    public Node min_max(Board board, Boolean i_am_min, int horizon, double alpha, double beta){
        ArrayList<Node> childes = Successor.successor(board);
        ArrayList<Node> queue = new ArrayList<>();
        Node ret_node = childes.get(0);
        if( horizon == 0){
            for(int i =0 ; i < childes.size(); i++){
                Node node = new Node(childes.get(i).board, childes.get(i).move);

                double cost = Eval.evaluate(node, turn);

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
                if(System.currentTimeMillis() - timer > 4990){
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
