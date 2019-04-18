package martijn.quoridor.brains;

import martijn.quoridor.model.Board;
import martijn.quoridor.model.Jump;
import martijn.quoridor.model.Move;

public class MyBrain extends Brain {

    @Override
    public Move getMove(Board board) throws InterruptedException {
        MinMax minmax = new MinMax();
        return minmax.getNextMove(board);
    }
}
