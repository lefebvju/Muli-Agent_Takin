public class Agent extends Thread {
    private Position current;
    private Position target;
    private Grille grille;


    private void moveUp(){
        if (current.Y() > 1) current.up();
    }

    private void moveDown(){
        if (current.Y() < grille.getSizeY()) current.down();
    }

    private void moveLeft(){
        if (current.X() > 1) current.left();
    }

    private void moveRight(){
        if (current.X() < grille.getSizeX()) current.right();
    }


}
