package martijn.quoridor.brains;

import martijn.quoridor.model.*;

import java.util.*;

public class Jeniale extends Brain {

    public ArrayList<Move> sucessor(Board board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        Player player1 = board.getTurn();
        Player player2 = board.getPlayers()[1 - board.getTurnIndex()];


        Set<Position> positions = new HashSet<>();
        Position position = player2.getPosition();
        positions.add(position);
        positions.addAll(player2.getJumpPositions());
        moves.add(new Jump(player1.stepToGoal()));
        Position newPosition = position;
        Orientation[] orientations = player2.findGoal();
        int ctr = 0;
        for (Orientation orientation : orientations) {
            if (ctr == 8)
                break;
            newPosition = newPosition.move(orientation);
            player2.setPosition(newPosition);
            positions.addAll(player2.getJumpPositions());
            ctr++;
        }

            player2.setPosition(position);

            Object[] objects = positions.toArray();

        for (int i = 0; i < positions.size(); i++) {
            PutWall putWall;
            putWall = new PutWall((Position)objects[i], Wall.HORIZONTAL);
            if (putWall.isLegal(board)) {
                moves.add(putWall);
            }
            putWall = new PutWall((Position)objects[i], Wall.VERTICAL);
            if (putWall.isLegal(board)) {
                moves.add(putWall);
            }
        }
        return moves;
    }


    public int minimax(Board board, int depth, boolean isMaximizingPlayer, double alpha, double beta) {
        Player player1 = board.getTurn();
        Player player2 = board.getPlayers()[1 - board.getTurnIndex()];

        if (player1.isWinner()) {
            return 1000;
        } else if (player2.isWinner()) {
            return -1000;
        }

        if (depth == 4) {
            return evaluation(board);
        }

        ArrayList<Move> moves;
        moves = sucessor(board);

        if (isMaximizingPlayer) {
            int best = -210000000;
            int minimax;

            for (Move move : moves) {
                board.move(move);
                minimax = minimax(board, depth + 1, false, alpha, beta);
                board.undo();
                best = Math.max(best, minimax);
                alpha = Math.max(alpha, best);
                if (beta <= alpha)
                    break;
            }
            return best;
        } else {
            int best = 210000000;
            int minimax;
            for (Move move : moves) {
                board.move(move);
                minimax = minimax(board, depth + 1, true, alpha, beta);
                board.undo();
                best = Math.min(best, minimax);
                beta = Math.min(beta, best);
                if (beta <= alpha)
                    break;
            }
            return best;
        }
    }

    //    Random random = new Random();
    public int evaluation(Board board) {
        Player player1 = board.getTurn();
        Player player2 = board.getPlayers()[1 - board.getTurnIndex()];
        int v = 2 * (player2.findGoal().length - player1.findGoal().length) + (player1.getWallCount() - player2.getWallCount());
        if (player1.isWinner()) {
            return -1000;
        } else if (player2.isWinner()) {
            return 1000;
        } else {
            return v;
        }
    }
    long startTime;
    @Override
    public Move getMove(Board board) throws InterruptedException {
        startTime = System.currentTimeMillis();

        Player player1 = board.getTurn();
        Player player2 = board.getPlayers()[1 - board.getTurnIndex()];
        ArrayList<MinimaxReturnValue> minimaxReturnValues = new ArrayList<>();
        int minimax, best = -210000000, alpha = -2100000000, beta = 210000000;


        ArrayList<Move> moves;
        moves = sucessor(board);

        int a = 0;

        for (Move move : moves) {
            if(System.currentTimeMillis() - startTime >= 4800){
                break;
            }
            board.move(move);
            minimax = minimax(board, 1, false, alpha, beta);
            board.undo();
            minimaxReturnValues.add(new MinimaxReturnValue(minimax, move));
            a++;
        }
        Collections.sort(minimaxReturnValues);

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(a);
        System.out.println(totalTime);

        return minimaxReturnValues.get(0).move;
    }
}

class MinimaxReturnValue implements Comparable {
    int value;
    Move move;

    public MinimaxReturnValue(int value, Move move) {
        this.value = value;
        this.move = move;
    }

    public int compareTo(Object o) {
        MinimaxReturnValue m = (MinimaxReturnValue) o;
        return m.value - this.value;
    }

}