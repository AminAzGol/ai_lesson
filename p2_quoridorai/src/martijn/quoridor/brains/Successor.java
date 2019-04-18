package martijn.quoridor.brains;

import javafx.util.Pair;
import martijn.quoridor.model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Set;


public class Successor {
    public static ArrayList<Pair<Board,Move>> successor(Board board){
        Player player = board.getTurn();
        Board tempBoard ;
        tempBoard =  board.clone();
        ArrayList<Pair<Board,Move>> childern = new ArrayList<>();
        Set<Position> jumps = player.getJumpPositions();
        for (Position jump : jumps){
            tempBoard =  board.clone();
            tempBoard.getTurn().setPosition(jump);
            Jump move = new Jump(jump);
            if (move.isLegal(board))
                childern.add(new Pair<> (tempBoard,move));
        }
        for (int i=0; i< board.getWidth() ; i++){
            for (int j=0; j <board.getHeight();j++){
                Position position = new Position(j,i);
                if (tempBoard.containsWallPosition(position)) {

                    if (tempBoard.getWall(position) == null) {
                        tempBoard = board.clone();
                        tempBoard.setWall(position, Wall.HORIZONTAL);
                        PutWall move = new PutWall(position, Wall.HORIZONTAL);
                        if (move.isLegal(board))
                            childern.add(new Pair<>(tempBoard, move));

                        tempBoard = board.clone();
                        tempBoard.setWall(position, Wall.VERTICAL);
                        move = new PutWall(position, Wall.VERTICAL);
                        if(move.isLegal(board))
                            childern.add(new Pair<>(tempBoard, move));
                    }
                }
            }
        }
        if (childern.size() == 0)
            System.out.println("here");
        return childern;

    }


}
