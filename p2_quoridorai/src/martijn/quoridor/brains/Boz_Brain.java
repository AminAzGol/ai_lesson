package martijn.quoridor.brains;

import martijn.quoridor.model.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.Timer;

public class Boz_Brain extends Brain {

    @Override
    public Move getMove(Board board) throws InterruptedException {
        MinMax minmax = new MinMax();
        return minmax.getNextMove(board);
    }
}

class Successor {
    public static ArrayList<Node> successor(Board board){
        Player player = board.getTurn();
        Board tempBoard ;
        tempBoard =  board.clone();
        ArrayList<Node>  childern = new ArrayList<>();
        Set<Position> jumps = player.getJumpPositions();
        tempBoard =  board.clone();
        tempBoard.move(new Jump(player.stepToGoal()));
        childern.add(new Node(tempBoard,new Jump(player.stepToGoal())));

        for (int i=1; i< board.getWidth() ; i++){
            for (int j=1; j <board.getHeight();j++){
                Position position = new Position(j,i);
                if (tempBoard.containsWallPosition(position)) {
                    if (tempBoard.getWall(position) == null) {
                        tempBoard = board.clone();
                        tempBoard.setWall(position, Wall.HORIZONTAL);
                        PutWall move = new PutWall(position, Wall.HORIZONTAL);
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
        if (childern.size() == 0)
            System.out.println("here");
        return childern;
    }


}


class Node implements Comparable{
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


class Eval {
    public static double evaluate(Node node, int turn){
        Board board = node.board;
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
        double cost= opponent_steps.length - my_steps.length;
        if (node.move instanceof Jump )
            cost++;
        if(opponent.getWallCount() == 1 && node.move instanceof PutWall)
            cost++;

        return cost * 100;// + new Random().nextInt(10);
    }
}


class MinMax {
    public int turn;
    public long timer;
    public int the_horizon = 2;
    public int temp_horizon = the_horizon;
    public Move getNextMove(Board board){
        Board tempBorad = board.clone();
        int horizon = temp_horizon;
        turn = board.getTurnIndex();
        timer = System.currentTimeMillis();
        Node bestnode = min_max(tempBorad,false,horizon,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY, true);
        System.out.println(System.currentTimeMillis() - timer);
        return bestnode.move;
    }
    public Node min_max(Board board, Boolean i_am_min, int horizon, double alpha, double beta,Boolean i_am_top_layer){
        ArrayList<Node> childes = succ2(board);
        ArrayList<Node> same_scores = new ArrayList<>();
        Node ret_node = childes.get(0);
        if(childes.size() <=40 && temp_horizon == the_horizon)
            temp_horizon = the_horizon + 1;
        else{
            temp_horizon = the_horizon;
        }
        if(i_am_min)
            ret_node.cost = Double.POSITIVE_INFINITY;
        else
            ret_node.cost = Double.NEGATIVE_INFINITY;
        if( horizon == 0){
            for(int i =0 ; i < childes.size(); i++){
                Node node = new Node(childes.get(i).board, childes.get(i).move);
                double cost;
                if(node.board.getPlayers()[1-turn].findGoal().length == 1){
                    cost = Double.NEGATIVE_INFINITY;
                }
                else{

                    cost = Eval.evaluate(node, turn);
                }

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
                if(horizon == temp_horizon){
//                    System.out.println("size: "+childes.size() + " i: "+i + " h: "+horizon);
                }
                if(!check_winner(mychild,turn)) {
                    Node retval = min_max(childes.get(i).board, !i_am_min, horizon - 1, alpha, beta, false);
                    mychild.setCost(retval.cost);
                }
                if(i_am_min && mychild.cost < beta){
                    beta  = mychild.cost;
                    ret_node = mychild;
                    same_scores.clear();
                    same_scores.add(mychild);
                }
                else if(i_am_min && mychild.cost == beta){
                    same_scores.add(mychild);
                }
                else if (!i_am_min && mychild.cost > alpha){
                    alpha = mychild.cost;
                    ret_node = mychild;
                    same_scores.clear();
                    same_scores.add(mychild);
                }
                else if (!i_am_min && mychild.cost == alpha){
                    same_scores.add(mychild);
                }
                if (alpha >= beta){
                    return ret_node;
                }
                if(System.currentTimeMillis() - timer > 4990){
                    return ret_node;
                }
            }
        }
        if(i_am_top_layer){
//            System.out.println("Hello");
            int new_horizon = the_horizon;
            while(System.currentTimeMillis() - timer < 4990 && same_scores.size() > 1){
                Node new_best = same_scores.get(0);
                ArrayList<Node> same_scores_again = new ArrayList<>();
                new_horizon++;
                System.out.println("same size:   "+same_scores.size());
                System.out.println("hor : "+new_horizon);
                for(int i =0; i < same_scores.size(); i++){
                    Node child = same_scores.get(i);
                    Node retval = min_max(childes.get(i).board, !i_am_min, new_horizon, alpha, beta, false);
                    child.setCost(retval.cost);
                    if(i_am_min && child.cost < beta){
                        beta  = child.cost;
                        new_best = child;
                    }
                    else if(i_am_min && child.cost == beta){
                        same_scores_again.add(child);
                    }
                    else if (!i_am_min && child.cost > alpha){
                        alpha = child.cost;
                        new_best = child;
                        same_scores_again.clear();
                        same_scores_again.add(child);
                    }
                    else if (!i_am_min && child.cost == alpha){
                        same_scores_again.add(child);
                    }
                    if(System.currentTimeMillis() - timer > 4990){
                        return new_best;
                    }
                }
                ret_node = new_best;
                same_scores = same_scores_again;
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
    public void count_wall_2(ArrayList<Move> moves, Board board,ArrayList<Node> children,Wall[][] walls){
        for (int x = 0; x < board.getWidth() - 1; x++) {
            for (int y = 0; y < board.getHeight() - 1; y++) {
                Position pos = new Position(x, y);
                Wall the_wall =board.getWall(pos);
                if (the_wall != null) {
                    if(the_wall == Wall.HORIZONTAL){
                        check_and_insert(new PutWall(pos.west().west(), Wall.HORIZONTAL) , children ,walls,board);
                        check_and_insert(new PutWall(pos.east().east(), Wall.HORIZONTAL) , children ,walls,board);
                        check_and_insert(new PutWall(pos.west(), Wall.VERTICAL) , children ,walls,board);
                        check_and_insert(new PutWall(pos.east(), Wall.VERTICAL) , children ,walls,board);

                        check_and_insert(new PutWall(pos.south(), Wall.VERTICAL) , children ,walls,board);
                        check_and_insert(new PutWall(pos.north(), Wall.VERTICAL) , children ,walls,board);

                        check_and_insert(new PutWall(pos.west().south(), Wall.VERTICAL) , children ,walls,board);
                        check_and_insert(new PutWall(pos.west().north(), Wall.VERTICAL) , children ,walls,board);

                        check_and_insert(new PutWall(pos.east().south(), Wall.VERTICAL) , children ,walls,board);
                        check_and_insert(new PutWall(pos.east().north(), Wall.VERTICAL) , children ,walls,board);
                    }
                    else{
                        check_and_insert(new PutWall(pos.south().south(), Wall.VERTICAL) , children ,walls,board);
                        check_and_insert(new PutWall(pos.north().north(), Wall.VERTICAL) , children ,walls,board);;

                        check_and_insert(new PutWall(pos.west(), Wall.HORIZONTAL) , children ,walls,board);
                        check_and_insert(new PutWall(pos.east(), Wall.HORIZONTAL) , children ,walls,board);

                        check_and_insert(new PutWall(pos.north(), Wall.HORIZONTAL) , children ,walls,board);
                        check_and_insert(new PutWall(pos.south(), Wall.HORIZONTAL) , children ,walls,board);

                        check_and_insert(new PutWall(pos.north().west(), Wall.HORIZONTAL) , children ,walls,board);
                        check_and_insert(new PutWall(pos.north().east(), Wall.HORIZONTAL) , children ,walls,board);

                        check_and_insert(new PutWall(pos.south().west(), Wall.HORIZONTAL) , children ,walls,board);
                        check_and_insert(new PutWall(pos.south().south(), Wall.HORIZONTAL) , children ,walls,board);
                    }
                }
            }

        }
    }
    public void check_and_insert(Move move,ArrayList<Node> children,Wall[][] walls,Board board){
        if (move.isLegal(board)) {
            if (move instanceof PutWall) {
                Wall wall = ((PutWall) move).getWall();
                Position pos = ((PutWall) move).getPosition();
                if (walls[pos.getX()][pos.getY()] != wall) {
                    walls[pos.getX()][pos.getY()] = wall;
                    Board tboard = board.clone();
                    tboard.move(move);
                    children.add(new Node(tboard, move));
                } else
                    return;
            }
        }

    }
    public ArrayList<Node> succ2(Board board){
        Player player = board.getTurn();
        Board tempBoard ;
        ArrayList<Node>  childern = new ArrayList<>();
        tempBoard =  board.clone();
        tempBoard.move(new Jump(player.stepToGoal()));
        childern.add(new Node(tempBoard,new Jump(player.stepToGoal())));
        Player players[] = board.getPlayers();
        int player_range = 1;
        ArrayList<Move>  moves = new ArrayList<>();
        boolean count_the_walls_2 = true;
        Wall walls[][] = new Wall[board.getWidth() - 1][board.getHeight() - 1];
        for(int i = 0; i < players.length; i++){
            Player plr = players[i];
            for(int x = plr.getPosition().getX() -player_range; x < plr.getPosition().getX() + player_range; x++){
                for(int y = plr.getPosition().getY() - player_range; y < plr.getPosition().getY() + player_range; y++){
                    Position position = new Position(x,y);
                    if (tempBoard.containsWallPosition(position)) {
                        if (tempBoard.getWall(position) == null) {
                            check_and_insert(new PutWall(position, Wall.HORIZONTAL),childern,walls,board);
                            check_and_insert(new PutWall(position, Wall.VERTICAL),childern,walls,board);
                        }
                    }
                }
            }
        }
        if(count_the_walls_2){
            count_wall_2(moves,board,childern,walls);
        }
        if (childern.size() == 0)
            System.out.println("here");
        return childern;

    }
}