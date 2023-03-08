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

    public void init(int nbAgent) {
        if(!run) {
            finished = false;
            nbDep = 0;
            System.out.println("RUN ====== " + run + "   FINISH ===== " + finished);

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



            for(int i=0; i<nbAgent; i++){
                Position p1;
                do {
                    p1 = Position.random(getSizeX(), getSizeY());
                }while(!isFree(p1));
                Position p2;
                int ind1D;
                //do {
                    //p2 = Position.random(getSizeX(), getSizeY());
                    ind1D = targetVal.indexOf(i);
                    p2 = new Position(ind1D%sizeY,ind1D/sizeX);
                    //System.out.println("i= " + i +" " + p2);
                //}while(!isFreeTarget(p2));
                tabAgent[i]=new Agent(p1,p2,this,i,m);

            }

            System.out.println(toString());
            setChanged();
            notifyObservers();
        }
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
