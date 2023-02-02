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

    private void move() {
        Direction d = getDir();
        if(d==null)
            return;
        Position p;
        Position p2;
        try {
            p = (Position) current.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        switch (d){
            case NORTH:
                p.up();
                if(grille.isFree(p)) {
                    grille.move(current, p);
                    moveUp();
                }
                break;
            case SOUTH:
                p.down();
                if(grille.isFree(p)) {
                    grille.move(current, p);
                    moveDown();
                }

                break;
            case EAST:
                p.right();
                if(grille.isFree(p)) {
                    grille.move(current, p);
                    moveRight();
                }
                break;
            case WEST:
                p.left();
                if(grille.isFree(p)) {
                    grille.move(current, p);
                    moveLeft();
                }
                break;
            case NE:
                try {
                    p2 = (Position) p.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                p.up();
                p2.right();
                if(grille.isFree(p)) {
                    grille.move(current, p);
                    moveUp();
                }else if(grille.isFree(p2)) {
                    grille.move(current, p2);
                    moveRight();
                }
                break;
            case NW:
                try {
                    p2 = (Position) p.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                p.up();
                p2.left();
                if(grille.isFree(p)) {
                    grille.move(current, p);
                    moveUp();
                }else if(grille.isFree(p2)) {
                    grille.move(current, p2);
                    moveLeft();
                }
                break;
            case SE:
                try {
                    p2 = (Position) p.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                p.down();
                p2.right();
                if(grille.isFree(p)) {
                    grille.move(current, p);
                    moveDown();
                }else if(grille.isFree(p2)) {
                    grille.move(current, p2);
                    moveRight();
                }
                break;
            case SW:
                try {
                    p2 = (Position) p.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                p.down();
                p2.left();
                if(grille.isFree(p)) {
                    grille.move(current, p);
                    moveDown();
                }else if(grille.isFree(p2)) {
                    grille.move(current, p2);
                    moveLeft();
                }
                break;
        }
    }


    @Override
    public void run() {
        System.out.println("Bonjour ! ");
    }


}
