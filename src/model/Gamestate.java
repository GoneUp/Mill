package model;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Max on 16/08/2016.
 */
public class Gamestate {

    public HashSet<Pieces> millPieces= new HashSet<Pieces>();
    public Playfield[][] board = new Playfield[8][3];

    public List<Pieces> currentPieces = new LinkedList<>();
    public Map<Playfield, List<Playfield>> playfieldNeighbors = new HashMap<>();

    private String millType;
    private int millCount = 0;
    private int currentMillCount = 0;

    public Gamestate(Playfield[][] board) {
        this.board = board;
        createNeighbors();
    }

    private void piecesMillSet(int i, int j) {   //indices for fields in mill.
            int t = 0;
            int z = 0;
            switch (millType) {
                case "inward":
                    break;
                case "up":
                    break;
                case "right":
                    break;
                case "down":

                    break;
                case "left":
                    break;

                default:
                    break;
            }
        }



    public boolean millCheck() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                if (!board[i][j].empty) {
                    Color color = board[i][j].piece.color;
                    if (i % 2 == 1) {   //to check if its one of the four legit outwards to inward rows
                        if ((board[i][0].piece.color == board[i][1].piece.color) // 3pieces of the same color from outwards to inwards
                                && (board[i][0].piece.color == board[i][2].piece.color)){
                           millType = "inward";
                            return true;
                        }
                    }if (i >= 0 && i < 3) { //upper quarter of the playfield
                        if ((board[0][j].piece.color == board[1][j].piece.color) //3 pieces of the same color next to each other  on the upper quarter
                                && (board[0][j].piece.color == board[2][j].piece.color)) {
                            millType = "up";
                            return true;
                        }
                    }if (i >= 2 && i < 5) {  // right quarter
                        if ((board[2][j].piece.color == board[3][j].piece.color) //3 pieces of the same color next to each other  on the right quarter
                                && (board[2][j].piece.color == board[4][j].piece.color)) {
                            millType = "right";
                            return true;
                        }
                    }if (i >= 4 && i < 7) {
                        if ((board[4][j].piece.color == board[5][j].piece.color) //3 pieces of the same color next to each other  on the lower quarter
                                && (board[4][j].piece.color == board[6][j].piece.color)) {
                            millType = "down";
                            return true;
                        }
                    }if (i >= 6) {
                        if ((board[6][j].piece.color == board[7][j].piece.color) //3 pieces of the same color next to each other  on the left quarter
                                && (board[6][j].piece.color == board[0][j].piece.color)) {
                            millType = "left";
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public Point findFreePlayfield(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].empty)
                    return new Point(i, j);
            }
        }
        return null;
    }


    private void createNeighbors(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                Playfield base = board[i][j];

                for (int i2 = 0; i2 < 8; i2++) {
                    for (int j2 = 0; j2 < 3; j2++) {
                        Playfield toCompare = board[i2][j2];
                        if (base.isNeighbour(toCompare)) {
                            if (!playfieldNeighbors.containsKey(base))
                                playfieldNeighbors.put(base, new LinkedList<>());

                            playfieldNeighbors.get(base).add(toCompare);
                        }
                    }
                }


            }
        }
    }

    public List<Playfield> getNeighbors(Playfield field){
        return playfieldNeighbors.get(field);
    }

    public int getPieceCount(){
        int count = 0;
        for (Pieces p : currentPieces) {
            if (p.field != null)
                count++;
        }
        return count;
    }
}


