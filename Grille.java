import java.security.Signature;
import java.text.DecimalFormat;

public class Grille {
    private final int sizeX;
    private final int sizeY;
    private final int[][] tabCurrent;
    private final int[][] tabTarget;
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
        synchronized (tabCurrent) {
            if (isFree(p)) {
                tabCurrent[p.X()][p.Y()] = numAgent;
                return true;
            }
            return false;
        }
    }

    public boolean placeAgentTarget(Position p, int numAgent) {
        synchronized (tabCurrent) {
            if (tabTarget[p.X()][p.Y()] == -1) {
                tabTarget[p.X()][p.Y()] = numAgent;
                return true;
            }
            return false;
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public boolean isFinished() {
        return finished;
    }


    public void move(Position p1, Position p2) {
        synchronized (tabCurrent) {
            tabCurrent[p2.X()][p2.Y()] = tabCurrent[p1.X()][p1.Y()];
            tabCurrent[p1.X()][p1.Y()] = -1;
            verif();
        }
    }

    public boolean isInTab(Position p){
        return (p.X()>=0 && p.Y()>=0 && p.X()<sizeX && p.Y()<sizeY);
    }

    public boolean isFree(Position p) {
        synchronized (tabCurrent) {
            if(p.X() >= 0 && p.X()<sizeX && p.Y()>= 0 && p.Y() < sizeY){
                return tabCurrent[p.X()][p.Y()] == -1;
            }
            return false;
            
        }
    }

    public int getId(Position p) {
        synchronized (tabCurrent) {

            return tabCurrent[p.X()][p.Y()];
        } 
    }

    private void verif() {
            for (int i = 0; i < sizeX; i++) {
                for (int j = 0; j < sizeY; j++) {
                    if (tabCurrent[i][j] != tabTarget[i][j]) {
                        finished = false;
                        return;
                    }
                }
            }
            finished = true;
    }

    @Override
    public String toString() {
        synchronized (tabCurrent) {
            String retour = "";
            DecimalFormat formatter = new DecimalFormat("00");
            for (int row = 0; row < sizeY; row++) {
                retour += "| ";
                for (int col = 0; col < sizeX; col++) {
                    if (tabCurrent[col][row] != -1) {
                        retour += formatter.format(tabCurrent[col][row]) + (" | ");
                    } else {
                        retour += ("   | ");
                    }
                }
                retour += "            -->            | ";

                for (int col = 0; col < sizeX; col++) {
                    if (tabTarget[col][row] != -1) {
                        retour += formatter.format(tabTarget[col][row]) + (" | ");
                    } else {
                        retour += ("   | ");
                    }
                }
                retour += "\n";
            }
            return retour;
        }
    }
}
