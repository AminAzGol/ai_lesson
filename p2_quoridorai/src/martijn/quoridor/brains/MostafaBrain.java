package martijn.quoridor.brains;

import martijn.quoridor.model.*;

import java.util.ArrayList;

public class MostafaBrain extends Brain {
    //    private Move selectedMove = null;
    boolean isFirst = true;
    Player me;
    Player op;

    @Override
    public Move getMove(Board board) throws InterruptedException {
        if (board.getHistory().size() == 1){
            isFirst = false;
        }
        MinMaxResult minMaxResult = minmax(board, 3, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (minMaxResult.move == null)
            minMaxResult.move = new Jump(board.getTurn().getPosition().move(board.getTurn().findGoal()[0]));
        return minMaxResult.move;
    }

    private class MinMaxResult {
        int score;
        Move move;
        boolean possible;

        public MinMaxResult(int score, Move move, boolean possible) {
            this.score = score;
            this.move = move;
            this.possible = possible;
        }

        public MinMaxResult(int score, Move move) {
            this.score = score;
            this.move = move;
            this.possible = true;
        }
    }

    public MinMaxResult minmax(Board board, int dept, boolean maximizer, int alpha, int beta) {
        if (dept == 0)
            return new MinMaxResult(evaluator(board), null);

        ArrayList<Board> possibleMoves = successor(board);
//        possibleMoves.addAll(walls);

        if (maximizer) {
            int maxVal = Integer.MIN_VALUE;
            Move selectedMove = null;
            for (Board tempBoard : possibleMoves) {
                MinMaxResult eval = minmax(tempBoard, dept - 1, false, alpha, beta);
                if (eval.score > maxVal) {
                    maxVal = eval.score;
                    selectedMove = tempBoard.getHistory().peek();
                }
                alpha = Math.max(maxVal, alpha);
                if (beta <= alpha) {
                    break;
                }
            }

            return new MinMaxResult(maxVal, selectedMove);
        } else {
            int minValue = Integer.MAX_VALUE;
            Move selectedMove = null;
            for (Board tempBoard : possibleMoves) {
                MinMaxResult eval = minmax(tempBoard, dept - 1, true, alpha, beta);
                if (eval.score < minValue) {
                    minValue = eval.score;
                    selectedMove = tempBoard.getHistory().peek();
                }
                beta = Math.min(beta, minValue);
                if (beta <= alpha) {
                    break;
                }
            }
            return new MinMaxResult(minValue, selectedMove);
        }
    }

    private int evaluator(Board board) {
        int hn = 0;
        Player op = board.getTurn();
        Player cur = op == board.getPlayers()[0] ? board.getPlayers()[1] : board.getPlayers()[0];
        int oplen = op.findGoal().length;
        int curlen = cur.findGoal().length;
        hn += (oplen - curlen);
        if (cur.getWallCount() > 3 && op.getWallCount() ==0)
            hn += cur.getWallCount() * 2;
        else {
            if (cur.getWallCount() - op.getWallCount() == 2) {
//            hn += isFirst ? 2 : 7;
                hn += 2;
            } else if (cur.getWallCount() - op.getWallCount() == 3) {
                hn += 3;
            } else if (cur.getWallCount() - op.getWallCount() >= 4) {
                hn += 4;
            }
        }
        if (curlen - oplen > 7)
            hn -= 3 * 6;

            Board tmp2 = board.clone();
            tmp2.undo();
        if (board.getHistory().peek() instanceof PutWall) {
            tmp2.undo();
            Player oldcur = tmp2.getTurn() == tmp2.getPlayers()[0] ? tmp2.getPlayers()[1] : tmp2.getPlayers()[0];
            int oldcurlen = oldcur.findGoal().length;
            if (curlen - oldcurlen >= 4) {
                hn -= ( ((curlen - oldcurlen) * 4) +( isFirst ? 6 : 8 ));
            }
        }
            Board tmp = board.clone();
            if (tmp.getHistory().peek() instanceof PutWall) {
                tmp.undo();
                Player oldop = tmp.getTurn() == tmp.getPlayers()[0] ? tmp.getPlayers()[1] : tmp.getPlayers()[0];
                int oldoplen = oldop.findGoal().length;
//                Player curold = oldop == tmp.getPlayers()[0] ? tmp.getPlayers()[1] : tmp.getPlayers()[0];
                hn += (oplen - oldoplen ) >= (6) ? (oplen - oldoplen) * (isFirst ? 2 : 3) : 0;
            }
            if (op.getWallCount() - cur.getWallCount() >= 3)
                hn -= 4*2;

        return hn;
    }

    private ArrayList<Board> successor(Board board) {
        ArrayList<Board> arrayList = new ArrayList<>();
        Board tempBoard = board.clone();
//        Jump jump = new Jump(board.getTurn().stepToGoal());
//        if (jump.isLegal(board)){
//            tempBoard.move(new Jump(board.getTurn().stepToGoal()));
//                arrayList.add(tempBoard);
////                tempBoard = board.clone();
//        }
        Player cur = board.getTurn();
        Player op = cur == board.getPlayers()[0] ? board.getPlayers()[1] : board.getPlayers()[0];

        for (Position jumpPosition : board.getTurn().getJumpPositions()) {
            Jump jump = new Jump(jumpPosition);
            if (jump.isLegal(board)) {
                tempBoard.move(jump);
                arrayList.add(tempBoard);
                tempBoard = board.clone();
            }
        }
        for (int i = op.getPosition().getX() - 4; i < op.getPosition().getX() + 4; i++) {
            for (int j = op.getPosition().getY() - 4; j < op.getPosition().getY() + 4; j++) {
                PutWall putWall = new PutWall(new Position(i, j), Wall.HORIZONTAL);
                if (putWall.isLegal(board)) {
                        tempBoard = board.clone();
                        tempBoard.move(putWall);
                        arrayList.add(tempBoard);
                }
                putWall = new PutWall(new Position(i, j), Wall.VERTICAL);
                if (putWall.isLegal(board)) {
                        tempBoard = board.clone();
                        tempBoard.move(putWall);
                        arrayList.add(tempBoard);
                }

            }
        }
        for (int i = cur.getPosition().getX() - 2; i < cur.getPosition().getX() + 2; i++) {
            for (int j = cur.getPosition().getY() - 2; j < cur.getPosition().getY() + 2; j++) {
                PutWall putWall = new PutWall(new Position(i, j), Wall.HORIZONTAL);
                if (putWall.isLegal(board)) {
                        tempBoard = board.clone();
                        tempBoard.move(putWall);
                        arrayList.add(tempBoard);
                }
                putWall = new PutWall(new Position(i, j), Wall.VERTICAL);
                if (putWall.isLegal(board)) {
                        tempBoard = board.clone();
                        tempBoard.move(putWall);
                        arrayList.add(tempBoard);
                }

            }
        }

        return arrayList;
    }
}
