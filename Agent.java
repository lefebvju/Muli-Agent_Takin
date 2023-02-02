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

    public Direction getDir(){
        Direction d=null;
        if (current.Y() < target.Y()){
            d=Direction.SOUTH;
        } else if (current.Y() > target.Y()) {
            d=Direction.NORTH;
        }

        if(current.X()< target.X()){
            if (d == Direction.NORTH){
                d=Direction.NE;
            } else if (d == Direction.SOUTH){
                d=Direction.SE;
            }else{
                d=Direction.EAST;
            }
        } else if (current.X() > target.X()) {
            if (d == Direction.NORTH){
                d=Direction.NW;
            } else if (d == Direction.SOUTH){
                d=Direction.SW;
            }else{
                d=Direction.WEST;
            }
        }
        return d;
    }



    @Override
    public void run() {
        System.out.println("Bonjour ! ");
    }


}
