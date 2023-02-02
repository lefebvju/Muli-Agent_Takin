public class Agent extends Thread {
    private Position current;
    private Position target;
    private Grille grille;

    public Agent(Position c, Position t, Grille g) {
        current = c;
        target = t;
        grille = g;
    }

    private void moveUp(){
        if (current.Y() > 0) current.up();
    }

    private void moveDown(){
        if (current.Y() < grille.getSizeY() - 1) current.down();
    }

    private void moveLeft(){
        if (current.X() > 0) current.left();
    }

    private void moveRight(){
        if (current.X() < grille.getSizeX() - 1) current.right();
    }

    @Override
    public void run() {
        System.out.println("Bonjour ! ");
    }


}
