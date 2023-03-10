import java.security.Signature;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class Grille extends Observable {
    private final int sizeX;
    private final int sizeY;
    private final int[][] tabCurrent;
    private final int[][] tabTarget;
    private boolean finished;
    private int nbDep;

    // --- Utile pour le MVC ---
    Agent[] tabAgent;
    boolean run;

    public int getNbDep() {
        return nbDep;
    }

    Grille(final int x, final int y) {
        sizeX = x;
        sizeY = y;
        tabCurrent = new int[x][y];
        tabTarget = new int[x][y];
        finished = true;
        nbDep = 0;
        run = false;

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tabCurrent[i][j] = -1;
                tabTarget[i][j] = -1;
            }
        }
    }
    Grille(Grille g){
        sizeX = g.getSizeX();
        sizeY = g.getSizeY();
        tabCurrent = new int[sizeX][sizeY];
        tabTarget = new int[sizeX][sizeY];
        finished = g.finished;
        nbDep = g.getNbDep();
        run = g.run;

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tabCurrent[i][j] = g.tabCurrent[i][j];
                tabTarget[i][j] = g.tabTarget[i][j];
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
        verif();
        return finished;
    }


    public void move(Position p1, Position p2) {
        synchronized (tabCurrent) {
            tabCurrent[p2.X()][p2.Y()] = tabCurrent[p1.X()][p1.Y()];
            tabCurrent[p1.X()][p1.Y()] = -1;
            verif();
            nbDep++;
            setChanged();
            notifyObservers();  
        }
    }

    public boolean isInTab(Position p){
        return (p.X()>=0 && p.Y()>=0 && p.X()<sizeX && p.Y()<sizeY);
    }

    public boolean isInTab(Position p, Direction d){
        Position ptmp = p.clone();
        switch (d) {
            case NORTH:
                ptmp.up();
                return isInTab(ptmp);
            case SOUTH:
                ptmp.down();
                return isInTab(ptmp);
            case EAST:
                ptmp.right();
                return isInTab(ptmp);
            case WEST:
                ptmp.left();
                return isInTab(ptmp);
            default:
                System.out.println("erreur");
        }
        return false;
    }

    public boolean isFree(Position p) {
        synchronized (tabCurrent) {
            if(p.X() >= 0 && p.X()<sizeX && p.Y()>= 0 && p.Y() < sizeY){
                return tabCurrent[p.X()][p.Y()] == -1;
            }
            return false;
            
        }
    }

    public boolean isFree(Position p, Direction d) {
        Position ptmp = p.clone();
        switch (d) {
            case NORTH:
                ptmp.up();
                return isFree(ptmp);
            case SOUTH:
                ptmp.down();
                return isFree(ptmp);
            case EAST:
                ptmp.right();
                return isFree(ptmp);
            case WEST:
                ptmp.left();
                return isFree(ptmp);
            default:
                System.out.println("erreur isFree()");
        }
        return false;
    }

    public boolean isEdgeCorrect(Position p){
        if(p.X()==0) {
            return edgeCorrect(Direction.NORTH);
        } if(p.X()==sizeX-1) {
            return edgeCorrect(Direction.SOUTH);
        } if(p.Y()==0) {
            return edgeCorrect(Direction.WEST);
        } if(p.Y()==sizeY-1) {
            return edgeCorrect(Direction.EAST);
        }
        return false;
    }

    private boolean edgeCorrect(Direction d) {
        synchronized (tabCurrent) {
            synchronized (tabTarget) {
                int[][] current;
                int[][] target;
                switch (d) {
                    case NORTH:
                        for (int i = 0; i < sizeY; i++) {
                            if (tabCurrent[0][i] != tabTarget[0][i] || tabTarget[0][i] == -1) {
                                return false;
                            }
                        }
                        current = reformate(tabCurrent,1,sizeX,0,sizeY);
                        target = reformate(tabTarget,1,sizeX,0,sizeY);
                        return Solveur.soluble(current,target,sizeX-1,sizeY);
                    case SOUTH:
                        for (int i = 0; i < sizeY; i++) {
                            if (tabCurrent[sizeX - 1][i] != tabTarget[sizeX - 1][i] || tabTarget[sizeX-1][i] == -1) {
                                return false;
                            }
                        }
                        current = reformate(tabCurrent,0,sizeX-1,0,sizeY);
                        target = reformate(tabTarget,0,sizeX-1,0,sizeY);
                        return Solveur.soluble(current,target,sizeX-1,sizeY);
                    case WEST:
                        for (int i = 0; i < sizeX; i++) {
                            if (tabCurrent[i][0] != tabTarget[i][0] || tabTarget[i][0] == -1) {
                                return false;
                            }
                        }
                        current = reformate(tabCurrent,0,sizeX,1 ,sizeY);
                        target = reformate(tabTarget,0,sizeX,1,sizeY);
                        return Solveur.soluble(current,target,sizeX,sizeY-1);
                    case EAST:
                        for (int i = 0; i < sizeX; i++) {
                            if (tabCurrent[i][sizeY - 1] != tabTarget[i][sizeY - 1] || tabTarget[i][sizeY-1] == -1) {
                                return false;
                            }
                        }
                        current = reformate(tabCurrent,0,sizeX,0,sizeY-1);
                        target = reformate(tabTarget,0,sizeX,0,sizeY-1);
                        return Solveur.soluble(current,target,sizeX,sizeY-1);
                    default:
                        return false;
                }
            }
        }
    }

    private int[][] reformate(int[][] tab, int x1, int x2, int y1, int y2) {
        int[][] copie = new int[x2-x1][y2-y1];
        for (int i=x1;i<x2;i++){
            for(int j=y1;j<y2;j++){
                copie[i-x1][j-y1]=tab[i][j];
            }
        }
        return copie;
    }

    public boolean isFreeTarget(Position p) {
        synchronized (tabTarget) {
            if(p.X() >= 0 && p.X()<sizeX && p.Y()>= 0 && p.Y() < sizeY){
                return tabTarget[p.X()][p.Y()] == -1;
            }
            return false;

        }
    }

    public int getId(Position p) {
        synchronized (tabCurrent) {

            return tabCurrent[p.X()][p.Y()];
        } 
    }

    public int getId(int x, int y) {
        synchronized (tabCurrent) {
            return tabCurrent[x][y];
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
        run = false;
    }

    @Override
    public String toString() {
        synchronized (tabCurrent) {
            String retour = "";
            DecimalFormat formatter = new DecimalFormat("00");
            for (int col = 0; col < sizeX; col++) {
                retour += "| ";
                for (int row = 0; row < sizeY; row++) {
                    if (tabCurrent[col][row] != -1) {
                        retour += formatter.format(tabCurrent[col][row]) + (" | ");
                    } else {
                        retour += ("   | ");
                    }
                }
                retour += "            -->            | ";


                    for (int row = 0; row < sizeY; row++) {
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

    public void init(int nbAgent) {
        //if(!run) {
            finished = false;
            nbDep = 0;
            System.out.println("RUN ====== " + run + "   FINISH ===== " + finished);

            do {
                for (int i = 0; i < sizeX; i++) {
                    for (int j = 0; j < sizeY; j++) {
                        tabCurrent[i][j] = -1;
                        tabTarget[i][j] = -1;
                    }
                }

                // Gestion des agents
                Messagerie m = new Messagerie();
                tabAgent = new Agent[nbAgent];

                // Tirage position target
                int nbTrou = getSizeX() * getSizeY() - nbAgent;
                Random rand = new Random();
                int ind;
                ArrayList<Integer> targetVal = new ArrayList<Integer>();
                for (int i = 0; i < nbAgent; i++) {
                    targetVal.add(i);
                }
                for (int i = 0; i < nbTrou; i++) {
                    ind = rand.nextInt(targetVal.size() + 1);
                    targetVal.add(ind, -1);
                }
                System.out.println(targetVal);


                for (int i = 0; i < nbAgent; i++) {
                    Position p1;
                    do {
                        p1 = Position.random(getSizeX(), getSizeY());
                    } while (!isFree(p1));
                    Position p2;
                    int ind1D;
                    //do {
                    //p2 = Position.random(getSizeX(), getSizeY());
                    ind1D = targetVal.indexOf(i);
                    p2 = new Position(ind1D / sizeY, ind1D % sizeY);
                    //System.out.println("i= " + i +" " + p2);
                    //}while(!isFreeTarget(p2));
                    tabAgent[i] = new Agent(p1, p2, this, i, m);

                }
            }while(!Solveur.soluble(tabCurrent,tabTarget,sizeX,sizeY));

            System.out.println(toString());
            setChanged();
            notifyObservers();
        //}
    }

    public void start(int nbAgent) {
        System.out.println("RUN ====== " + run + "   FINISH ===== " + finished);
        if(!isFinished()){
            if(!run) {
                run = true;
                for(int i=0; i<nbAgent;i++){
                    tabAgent[i].start();
                }
            }
        }
        
    }   

    public int getIdTarget(int x, int y) {
        synchronized (tabTarget) {
            return tabTarget[x][y];
        } 
    }

}
