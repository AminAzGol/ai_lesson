package martijn.quoridor.brains;

import javafx.util.Pair;
import martijn.quoridor.model.Board;
import martijn.quoridor.model.Jump;
import martijn.quoridor.model.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class MinMax {
    public Move getNextMove(Board board){
        Board tempBorad = board.clone();
        int horizon = 2;
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
                if(node.board.getHistory().size() == 2){
                    System.out.println("r");
                }
                int cost = Eval.evaluate(node.board);

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
                Node retval = min_max(childes.get(i).board, !i_am_min, horizon - 1, alpha, beta);

                Node mychild = childes.get(i);
                mychild.setCost(retval.cost);

                if(i_am_min && retval.cost < beta){
                    beta  = retval.cost;
                    ret_node = mychild;
                }
                else if (!i_am_min && retval.cost > alpha){
                    alpha = retval.cost;
                    ret_node = mychild;
                }
                if (alpha >= beta){
                    return ret_node;
                }
            }
        }
        return ret_node;
    }
}
