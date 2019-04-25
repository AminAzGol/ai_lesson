package martijn.quoridor.brains;

import javafx.util.Pair;
import martijn.quoridor.model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Successor2 {
    ExecutorService executor;
    public Successor2(){
        executor = Executors.newFixedThreadPool(100);
    }
    public Node[] successormethod(Board board){
        Player player = board.getTurn();
        Board tempBoard ;
        tempBoard =  board.clone();
        Node[]  childern = new Node[150];
        int curser = 0;
        Set<Position> jumps = player.getJumpPositions();
//        for (Position jump : jumps){
//            tempBoard =  board.clone();
//            Jump move = new Jump(jump);
//            tempBoard.move(move);
//            childern.add(new Pair<> (tempBoard,move));
//        }
        tempBoard =  board.clone();
        tempBoard.move(new Jump(player.stepToGoal()));
        childern[curser] = new Node(tempBoard,new Jump(player.stepToGoal()));
        curser++;

        for (int i=0; i< board.getWidth() ; i++){
            for (int j=0; j <board.getHeight();j++){
                Runnable worker = new WorkerThread(i,j,board,childern,curser,Wall.HORIZONTAL);
                executor.execute(worker);
                curser+=2;
//                Position position = new Position(j,i);
//                if (tempBoard.containsWallPosition(position)) {
//                    if (tempBoard.getWall(position) == null) {
//                        tempBoard = board.clone();
//                        tempBoard.setWall(position, Wall.HORIZONTAL);
//                        PutWall move = new PutWall(position, Wall.HORIZONTAL);
////                        PutWall move = new PutWall(position, Wall.HORIZONTAL);
//                        if (move.isLegal(board)){
//                            tempBoard.move(move);
//                            childern.add(new Node(tempBoard, move));
//                        }
//
//                        tempBoard = board.clone();
//                        move = new PutWall(position, Wall.VERTICAL);
//                        if(move.isLegal(board)) {
//                            tempBoard.move(move);
//                            childern.add(new Node(tempBoard, move));
//                        }
//                    }
//                }
            }
        }
//        executor.shutdown();
//        while (!executor.isTerminated()) {
//        }
//        for (int k=0; k<board.getPlayers().length;k++){
//            Player[] players = board.getPlayers();
//            Position pos = players[k].getPosition();
//            int index = 1;
//            int i=pos.getX()-index;
//            if(i < 0)
//                i=0;
//            for ( ; i< pos.getX()+index ; i++){
//                int j=pos.getY()-index;
//                if(j<0)
//                    j=0;
//                for (; j <pos.getY()+index ;j++){
//                    Position position = new Position(j,i);
//                    if (tempBoard.containsWallPosition(position)) {
//                        if (tempBoard.getWall(position) == null) {
//                            tempBoard = board.clone();
//                            tempBoard.setWall(position, Wall.HORIZONTAL);
//                            PutWall move = new PutWall(position, Wall.HORIZONTAL);
//                            if (move.isLegal(board)){
//                                tempBoard.move(move);
//                                childern.add(new Node(tempBoard, move));
//                            }
//                            tempBoard = board.clone();
//                            move = new PutWall(position, Wall.VERTICAL);
//                            if(move.isLegal(board)) {
//                                tempBoard.move(move);
//                                childern.add(new Node(tempBoard, move));
//                            }
//                        }
//                    }
//                }
//            }
//        }

        return childern;

    }


}


class WorkerThread implements Runnable {
    private int i,j;
    private Board board;
    private Node[] childern;
    private int workerid;
    Board tempBoard;
    Board tempBoard2;
    Wall dir;
    public WorkerThread( int i, int j, Board board, Node[] childern, int workerid, Wall dir){
        this.i = i;
        this.j = j;
        this.workerid = workerid;
        this.board = board;
        this.childern = childern;
        this.tempBoard = board.clone();
        this.tempBoard2 = board.clone();
        this.dir = dir;
    }

    @Override
    public void run() {
        Position position = new Position(j,i);
        if (board.containsWallPosition(position)) {
            if (tempBoard.getWall(position) == null) {
                PutWall move = new PutWall(position, Wall.HORIZONTAL);
                if (move.isLegal(tempBoard)) {
                    tempBoard.move(move);
                    childern[workerid] = new Node(tempBoard, move);
                }

                move = new PutWall(position, Wall.VERTICAL);
                if (move.isLegal(tempBoard2)) {
                    tempBoard2.move(move);
                    childern[workerid + 1] = new Node(tempBoard2, move);
                }
            }
        }
    }

}