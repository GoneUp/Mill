package view.ai;

import controller.Controller;
import model.GamePhase;
import model.Pieces;
import view.AbstractPlayer;

import java.awt.*;
import java.util.*;
import java.util.List;

import static model.Utils.freeMoveAllowed;

/**
 * Created by Max on 25/08/2016.
 */
public class AiUtils {

    public static int getRandomNumber(){
        Random rn = new Random();
        return Math.abs(rn.nextInt());
    }

    public static Point randomMoveSource (Controller controller, AbstractPlayer abstractPlayer) { //selects a random piece to move this round
       List<Point> tmpList = new LinkedList<>();
        for (Pieces p : controller.getState().currentPieces) {
            if (abstractPlayer.getColor() == p.color && controller.getState().legalMoves.containsKey(p.field)) {
                if (controller.getState().legalMoves.get(p.field).size() > 0) {
                    Point tmpPoint = new Point(p.field.x, p.field.y);
                    tmpList.add(tmpPoint);
                }
            }
        }
        return tmpList.get(getRandomNumber() % tmpList.size());
    }

    public static Point selectRandomPlacing(Controller controller) { //random move while placing Pieces
        return controller.getState().legalPlacing.get(getRandomNumber() % controller.getState().legalPlacing.size());
    }

    public static Move selectRandomMove(Controller controller, AbstractPlayer abstractPlayer) { //random move while moving with more than 3 Pieces.
        Point tmpSrc = randomMoveSource(controller, abstractPlayer);
        List<Point> dstList = controller.getState().legalMoves.get(controller.getState().board[tmpSrc.x][tmpSrc.y]);
        Point tmpDst = new Point(dstList.get(getRandomNumber() % dstList.size()));
        return new Move(tmpSrc, tmpDst);
    }

    public static Point selectRandomRemove(Controller controller, AbstractPlayer abstractPlayer) { //randomly selected field of an enemy field to remove the piece.
        List<Point> enemyPieces = new LinkedList<Point>();
        for (model.Pieces p : controller.getState().currentPieces) {
            if (p.color != abstractPlayer.getColor() && !controller.getState().isInMill(p)) {
                Point tmpPoint = new Point(p.field.x, p.field.y);
                enemyPieces.add(tmpPoint);
            }
        }
        return new Point(enemyPieces.get(getRandomNumber() % enemyPieces.size()));
    }

    public static void updateLists(Controller controller) {
        controller.getState().legalPlacing.clear();
        controller.getState().legalMoves.clear();
        if (!freeMoveAllowed(controller, controller.getTurnColor())){
            controller.getState().updateLegalMoves();
        } else {
            controller.getState().updateFreeMovementLegalMoves();
        }
        controller.getState().updateLegalPlacing();
    }

    public static void place(Controller controller){
        controller.place(selectRandomPlacing(controller));
        //Copy State
        /*
        Controller copyCont = millController.deepCopy();
        if (myColor == Color.black)
            copyCont.setWhitePlayer(new DummyPlayer());
        else
            copyCont.setBlackPlayer(new DummyPlayer());
            */

        //init mtcs

        //simulate

        //make real call to controller
    }

    public static void moving(Controller controller, AbstractPlayer abstractPlayer) {
        Move tmpMove = selectRandomMove(controller, abstractPlayer);
        if (!freeMoveAllowed(controller, controller.getTurnColor())){
            controller.move(tmpMove.src, tmpMove.dst);
        } else {
            controller.setSleep(1000);
            controller.moveFreely(tmpMove.src, tmpMove.dst);
        }

    }

    public static void removeStone(Controller controller, AbstractPlayer abstractPlayer) {
        controller.removeStone(selectRandomRemove(controller, abstractPlayer));
    }

}
