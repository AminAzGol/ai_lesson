package martijn.quoridor.brains;

import martijn.quoridor.model.*;

public class Eval {
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
        if (node.move instanceof Jump && opponent.getWallCount() != 1)
            cost++;
        if(opponent.getWallCount() == 1 && node.move instanceof PutWall)
            cost++;

        return cost;
    }
}
