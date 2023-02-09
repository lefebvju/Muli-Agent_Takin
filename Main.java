public class Main {

    final static int NBAGENT = 5;

    public static void main(String[] args) {

        Grille g = new Grille(5,5);

        
        g.placeAgentCurrent(new Position(0,0), 1);
        g.placeAgentCurrent(new Position(1,1), 2);
        g.placeAgentCurrent(new Position(1,3), 3);
        g.placeAgentCurrent(new Position(2,1), 4);
        g.placeAgentCurrent(new Position(3,4), 5);

        g.placeAgentTarget(new Position(0,4), 1);
        g.placeAgentTarget(new Position(1,4), 2);
        g.placeAgentTarget(new Position(2,4), 3);
        g.placeAgentTarget(new Position(3,4), 4);
        g.placeAgentTarget(new Position(4,4), 5);

        Agent a1 = new Agent(new Position(0,0), new Position(0,4), g, 1);
        Agent a2 = new Agent(new Position(1,1), new Position(1,4), g, 2);
        Agent a3 = new Agent(new Position(1,3), new Position(2,4), g, 3);
        Agent a4 = new Agent(new Position(2,1), new Position(3,4), g, 4);
        Agent a5 = new Agent(new Position(3,4), new Position(4,4), g, 5);

        a1.start();
        a2.start();
        a3.start();
        a4.start();
        a5.start();

        System.out.println(g.toString());
        System.out.println("Hello world !");
    }
}
