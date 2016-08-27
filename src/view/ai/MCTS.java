package view.ai;
import controller.Controller;
import view.AbstractPlayer;

/**
 * Created by Max on 19/08/2016.
 * An implemtation of a Monte Carlo Tree Search algorithm to determine the SmartAI's next step.
 */
public class MCTS {


    private Controller currentState = new Controller();  //current gamestate
    public Node root = new Node(); //global root Node

    /*
    initializes the tree
     */
    public void initializeMCTS() { //reset with start new game
        root = new Node();
    }

    /*
    chose the best Node
     */
    public void selectMove() { //finally decides for a move and sets the root to the next move
        //logic to select e.g. highest win rate or highest win count node
        // root = selectedNode
    }

    public void updateCurrentGameState() {
        currentState = currentState.deepCopy();
    }

    public void simulation(AbstractPlayer abstractPlayer) {
        if(moveAlreadyPerformed(root, currentState.getState().currentMove)) {
            root = getNodeOfAlreadyPerformedMove(root, currentState.getState().currentMove);
        }
        else {
            root = nodeUpdate(root, currentState.getState().currentMove);
        }
        simulationR(abstractPlayer, root);
    }

    private int simulationR(AbstractPlayer abstractPlayer, Node currentNode) {
        updateCurrentGameState();
        AiUtils.updateLists(currentState);
        System.out.println("recursion");
        Move treeMove = new Move(null, null);

        switch (currentState.getGamePhase()) {
            case Placing:
                treeMove.dst = AiUtils.selectRandomPlacing(currentState);  //dst for placing
                if (moveAlreadyPerformed(currentNode, treeMove)) {  //checks if that move has already been done
                    currentNode = getNodeOfAlreadyPerformedMove(currentNode, treeMove); //updates currentNode for the next recursive call
                    break;
                } else {
                    treeMove = AiUtils.place(currentState);
                    currentNode = nodeUpdate(currentNode, treeMove);
                    break;
                }
            case Moving:
                treeMove = AiUtils.selectRandomMove(currentState, abstractPlayer);
                if (moveAlreadyPerformed(currentNode, treeMove)) {
                    currentNode = getNodeOfAlreadyPerformedMove(currentNode, treeMove);
                    break;
                } else {
                    // todo: this is where i just left to get some fooderino
                    treeMove = AiUtils.moving(currentState, abstractPlayer);
                    currentNode = nodeUpdate(currentNode, treeMove);
                    break;
                }
            case RemovingStone:
                treeMove.src = AiUtils.selectRandomRemove(currentState, abstractPlayer);  //src for removing
                if (moveAlreadyPerformed(currentNode, treeMove)) {
                    currentNode = getNodeOfAlreadyPerformedMove(currentNode, treeMove);
                    break;
                }
                else {
                    treeMove = AiUtils.removeStone(currentState, abstractPlayer);
                    currentNode = nodeUpdate(currentNode, treeMove);
                }
                break;
        }
        int i = simulationR(abstractPlayer, currentNode);
        if (i == 1) currentNode.winCount += i;
        return i;
    }

    private Node getNodeOfAlreadyPerformedMove(Node currentNode, Move treeMove) {
        for (Node node : currentNode.listOfChildren) {
            if (node.move == treeMove) return node;
        }
        return null;
    }

    private boolean moveAlreadyPerformed (Node currentNode, Move treeMove) {
        for (Node node : currentNode.listOfChildren) {
            if (node.move == treeMove) {
                currentState = node.currenstate;
                return true;
            }
        }
        return false;
    }

    private Node nodeUpdate(Node currentNode, Move treeMove) {
        Node tmpNode = new Node();
        tmpNode.move = treeMove;
        tmpNode.currenstate = this.currentState;
        currentNode.listOfChildren.add(tmpNode);
        return tmpNode;
    }
}

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