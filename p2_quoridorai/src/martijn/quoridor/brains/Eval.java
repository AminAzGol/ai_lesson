package martijn.quoridor.brains;

import martijn.quoridor.model.Board;
import martijn.quoridor.model.Orientation;
import martijn.quoridor.model.Player;

public class Eval {
    public static int evaluate(Board board){
        Player[] players = board.getPlayers();
        Player me,opponent;
        if ( players[0].isTurn() ) {
            me = players[0];
            opponent = players[1];
        }
        else {
            me = players[1];
            opponent = players[0];
        }
        Orientation[] my_steps = me.findGoal();
        Orientation[] opponent_steps = opponent.findGoal();

        return opponent_steps.length - my_steps.length;
    }
}
