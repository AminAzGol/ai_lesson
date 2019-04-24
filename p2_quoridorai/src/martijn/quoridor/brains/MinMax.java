package martijn.quoridor.brains;

import javafx.util.Pair;
import martijn.quoridor.model.Board;
import martijn.quoridor.model.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class MinMax {
    public Move getNextMove(Board board){
        Board tempBorad = board.clone();
        int horizon = 1;
        Node bestnode = min_max(tempBorad,false,horizon,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
        return bestnode.move;
    }
    public Node min_max(Board board, Boolean i_am_min, int horizon, double alpha, double beta){
        ArrayList<Pair<Board,Move>> childes = Successor.successor(board);
        ArrayList<Node> queue = new ArrayList<>();

        if( horizon == 0){
            for(int i =0 ; i < childes.size(); i++){
                Node node = new Node(childes.get(i).getKey(), childes.get(i).getValue());
                node.setCost(Eval.evaluate(node.board));
                queue.add(node);
            }
        }
        else{
            for (int i = 0; i < childes.size(); i++) {
                Node retval = min_max(childes.get(i).getKey(), !i_am_min, horizon - 1, alpha, beta);
                if(i_am_min && retval.cost < beta){
                    beta  = retval.cost;
                }
                else if (retval.cost > alpha){
                    alpha = retval.cost;
                }
                if (alpha > beta){
                    return retval;
                }
                Node mychild = new Node(childes.get(i).getKey(),childes.get(i).getValue());
                mychild.setCost(retval.cost);
                queue.add(mychild);
            }
        }
        Collections.sort(queue, new Node_Comparator());
        if(queue.size() == 0)
            System.out.printf("HI");
        if (i_am_min){
            return queue.get(0);
        }
        else{
            return queue.get(queue.size() - 1);
        }

    }
}
