public class Main {

    final static int NBAGENT = 15;

    public static void main(String[] args) {

        Grille g = new Grille(5,5);
        Messagerie m = new Messagerie();
        Agent[] tabAgent = new Agent[NBAGENT];

        for(int i=0; i<NBAGENT; i++){
            Position p1;
            do {
                p1 = Position.random(g.getSizeX(), g.getSizeY());
            }while(!g.isFree(p1));
            Position p2;
            do {
                p2 = Position.random(g.getSizeX(), g.getSizeY());
            }while(!g.isFreeTarget(p2));
            tabAgent[i]=new Agent(p1,p2,g,i,m);

        }

        for(int i=0; i<NBAGENT;i++){
            tabAgent[i].start();
        }

        /*

        Agent a1 = new Agent(new Position(0,0), new Position(0,4), g, 1, m);
        Agent a2 = new Agent(new Position(0,4), new Position(0,0), g, 2, m);
        Agent a3 = new Agent(new Position(1,3), new Position(2,4), g, 3, m);
        Agent a4 = new Agent(new Position(2,1), new Position(3,4), g, 4, m);
        Agent a5 = new Agent(new Position(3,4), new Position(4,4), g, 5, m);

        a1.start();
        a2.start();
        a3.start();
        a4.start();
        a5.start();

         */

        System.out.println(g.toString());
        System.out.println("Hello world !");
    }
}
