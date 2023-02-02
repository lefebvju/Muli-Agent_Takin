public class Grille {
    private int sizeX, sizeY;
    private int [][] tabCurrent;
    private int [][] tabTarget;
    private boolean finished;

    public boolean isFinished() {
        return finished;
    }

    Grille(final int x, final int y) {
        sizeX = x;
        sizeY = y;
        tabCurrent = new int[x][y];
        tabTarget = new int[x][y];
        finished = false;
    }

    public int getSizeX(){
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void move(Position p1, Position p2) {
        tabCurrent[p2.X()][p2.Y()] = tabCurrent[p1.X()][p1.Y()];
        tabCurrent[p1.X()][p1.Y()] = -1;
    }
}
