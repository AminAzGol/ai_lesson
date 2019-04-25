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
