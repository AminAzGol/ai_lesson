package martijn.quoridor.brains;

import javafx.util.Pair;
import martijn.quoridor.model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Set;


public class Successor {
    public static ArrayList<Node> successor(Board board){
        Player player = board.getTurn();
        Board tempBoard ;
        tempBoard =  board.clone();
        ArrayList<Node>  childern = new ArrayList<>();
        Set<Position> jumps = player.getJumpPositions();
//        for (Position jump : jumps){
//            tempBoard =  board.clone();
//            Jump move = new Jump(jump);
//            tempBoard.move(move);
//            childern.add(new Pair<> (tempBoard,move));
//        }
        tempBoard =  board.clone();
        tempBoard.move(new Jump(player.stepToGoal()));
        childern.add(new Node(tempBoard,new Jump(player.stepToGoal())));

//        for (int i=0; i< board.getWidth() ; i++){
//            for (int j=0; j <board.getHeight();j++){
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
//            }
//        }
        for (int k=0; k<board.getPlayers().length;k++){
            Player[] players = board.getPlayers();
            Position pos = players[k].getPosition();
            int index = 3;
            int i=pos.getX()-index;
            int j=pos.getY()-index;
            if(i < 0){
                i=0;
            }
            if(j<0){
                j=0;
            }
            for ( ; i< pos.getX()+index ; i++){
                for (; j <pos.getY()+index ;j++){
                    Position position = new Position(j,i);
                    if (tempBoard.containsWallPosition(position)) {
                        if (tempBoard.getWall(position) == null) {
                            tempBoard = board.clone();
                            tempBoard.setWall(position, Wall.HORIZONTAL);
                            PutWall move = new PutWall(position, Wall.HORIZONTAL);
    //                        PutWall move = new PutWall(position, Wall.HORIZONTAL);
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
        }

        if (childern.size() == 0)
            System.out.println("here");
        return childern;

    }


}
