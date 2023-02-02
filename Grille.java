public class Grille {
    private int sizeX, sizeY;
    private int [][] tabCurrent;
    private int [][] tabTarget;
    private boolean finished;

    Grille(final int x, final int y) {
        sizeX = x;
        sizeY = y;
        tabCurrent = new int[x][y];
        tabTarget = new int[x][y];
        finished = false;

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tabCurrent[i][j] = -1; 
                tabTarget[i][j] = -1;
            }
        }
    }

    public boolean placeAgentCurrent(Position p, int numAgent) {
        if(isFree(p)) {
            tabCurrent[p.X()][p.Y()] = numAgent;
            return true;
        }
        return false;
    }

    public boolean placeAgentTarget(Position p, int numAgent) {
        if(tabTarget[p.X()][p.Y()] == -1) {
            tabTarget[p.X()][p.Y()] = numAgent;
            return true;
        }
        return false;
    }

    public int getSizeX(){
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public boolean isFinished() {
        return finished;
    }


    public void move(Position p1, Position p2) {
        tabCurrent[p2.X()][p2.Y()] = tabCurrent[p1.X()][p1.Y()];
        tabCurrent[p1.X()][p1.Y()] = -1;
    }

    public boolean isFree(Position p) {
        return tabCurrent[p.X()][p.Y()] == -1;
    }

    private void verif() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if(tabCurrent[i][j] != tabTarget[i][j]) {
                    finished = false;
                    return;
                }
            }
        }
        finished = true;
    }
}
