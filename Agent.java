public class Agent extends Thread {
    private int currentX, currentY;
    private int targetX, targetY;
    private Grille grille;


    private void moveUp(){
        if (currentY>1) currentY--;
    }

    private void moveDown(){
        if (currentY< grille.getSizeY()) currentY++;
    }

    private void moveLeft(){
        if (currentX>1) currentX--;
    }

    private void moveRight(){
        if (currentY<grille.getSizeX()) currentX++;
    }


}
