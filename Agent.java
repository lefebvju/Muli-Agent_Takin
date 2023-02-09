import java.util.Random;

public class Agent extends Thread {
    private Integer id;
    private Position current;
    private Position target;
    private Grille grille;
    private Messagerie boite;

    private static final Random PRNG = new Random();


    public Agent(Position c, Position t, Grille g, Integer _id, Messagerie b) {
        id = _id;
        current = c;
        target = t;
        grille = g;
        boite=b;

        g.placeAgentCurrent(new Position(c.X(), c.Y()), id);
        g.placeAgentTarget(new Position(t.X(), t.Y()), id);
        b.initAgent(id);
    }

    private boolean moveUp(){
        if (current.Y() > 0){
            current.up();
            return true;
        } 
        return false;
    }

    private boolean moveDown(){
        if (current.Y() < grille.getSizeY() - 1) {
            current.down();
            return true;
        }
        return false;
    }

    private boolean moveLeft(){
        if (current.X() > 0){
            current.left();
            return true;
        }
        return false;
    }

    private boolean moveRight(){
        if (current.X() < grille.getSizeX() - 1){
            current.right();
            return true;
        }
        return false;
    }

    private Direction getDir(){
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

    private boolean move(Direction d) {        
        if(d==null)
            return false;
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
                if(grille.isFree(p) && moveUp()) {
                    grille.move(current, p);
                    return true;
                }
                else {
                    boite.addMessage(grille.getId(p), new MessageDemande(id, current, d, p));
                }
                break;
            case SOUTH:
                p.down();
                if(grille.isFree(p) && moveDown()) {
                    grille.move(current, p);
                    return true;
                }
                else {
                    boite.addMessage(grille.getId(p), new MessageDemande(id, current, d, p));
                }

                break;
            case EAST:
                p.right();
                if(grille.isFree(p) && moveRight()) {
                    grille.move(current, p);
                    return true;
                }
                else {
                    boite.addMessage(grille.getId(p), new MessageDemande(id, current, d, p));
                }
                break;
            case WEST:
                p.left();
                if(grille.isFree(p) && moveLeft()) {
                    grille.move(current, p);
                    return true;
                }
                else {
                    boite.addMessage(grille.getId(p), new MessageDemande(id, current, d, p));
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
                if(grille.isFree(p) && moveUp()) {
                    grille.move(current, p);
                    return true;
                }else if(grille.isFree(p2) && moveRight()) {
                    grille.move(current, p2);
                    return true;
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
                if(grille.isFree(p) && moveUp()) {
                    grille.move(current, p);
                    return true;
                }else if(grille.isFree(p2) && moveLeft()) {
                    grille.move(current, p2);
                    return true;
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
                if(grille.isFree(p) && moveDown()) {
                    grille.move(current, p);
                    return true;
                }else if(grille.isFree(p2) && moveRight()) {
                    grille.move(current, p2);
                    return true;
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
                if(grille.isFree(p) && moveDown()) {
                    grille.move(current, p);
                    return true;
                }else if(grille.isFree(p2) && moveLeft()) {
                    grille.move(current, p2);
                    return true;
                }
                break;
        }
        return false;
    }


    @Override
    public void run() {
        while(!grille.isFinished()) {
            Direction d = getDir();
            Position postmp;

            Message m = boite.getFirstMessage(id);            
            if(m!=null) {
                boite.removeFirstMessage(id);
                if (m.destinationPosition.equals(current)) {
                    Direction[] directions = Direction.values();
                    Direction dtmp = directions[PRNG.nextInt(directions.length)];

                    move(dtmp);
                    System.out.println(grille.toString());
                }
            }
            
            move(d);
            System.out.println(grille.toString());
        }
    }


}
