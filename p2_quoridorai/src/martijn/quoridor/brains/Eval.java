package martijn.quoridor.brains;

import martijn.quoridor.model.Board;
import martijn.quoridor.model.Orientation;
import martijn.quoridor.model.Player;

public class Eval {
    public static int evaluate(Board board){
        Player[] players = board.getPlayers();
        Player me,opponent;
        me = players[1 - board.getTurnIndex()];
        opponent = players[board.getTurnIndex()];
        if(me.isWinner())
            return (int)Double.POSITIVE_INFINITY;
        else if(opponent.isWinner())
            return (int)Double.NEGATIVE_INFINITY;


        if(board.getHistory().size() == 2){
            System.out.println("r");
        }

        Orientation[] my_steps = me.findGoal();
        Orientation[] opponent_steps = opponent.findGoal();
        return opponent_steps.length - my_steps.length;
    }
}
