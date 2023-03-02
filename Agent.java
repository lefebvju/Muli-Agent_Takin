import java.util.Random;

public class Agent extends Thread {
    private static final Random PRNG = new Random();
    private Integer id;
    private Position current;
    private Position target;
    private Grille grille;
    private Messagerie boite;
    private MessageDemande moveMsg = null;
    private Direction nextMove = null;


    public Agent(Position c, Position t, Grille g, Integer _id, Messagerie b) {
        id = _id;
        current = c;
        target = t;
        grille = g;
        boite = b;

        g.placeAgentCurrent(new Position(c.X(), c.Y()), id);
        g.placeAgentTarget(new Position(t.X(), t.Y()), id);
        b.initAgent(id);
    }

    private boolean moveUp() {
        if (current.Y() > 0) {
            Position p = current.clone();
            current.up();
            grille.move(p, current);
            return true;
        }
        return false;
    }

    private boolean moveDown() {
        if (current.Y() < grille.getSizeY() - 1) {
            Position p = current.clone();
            current.down();
            grille.move(p, current);
            return true;
        }
        return false;
    }

    private boolean moveLeft() {
        if (current.X() > 0) {
            Position p = current.clone();
            current.left();
            grille.move(p, current);
            return true;
        }
        return false;
    }

    private boolean moveRight() {
        if (current.X() < grille.getSizeX() - 1) {
            Position p = current.clone();
            current.right();
            grille.move(p, current);
            return true;
        }
        return false;
    }

    private boolean moveNSWE(Direction d) {
        if (d == Direction.NORTH) {
            return moveUp();
        } else if (d == Direction.SOUTH) {
            return moveDown();
        } else if (d == Direction.WEST) {
            return moveLeft();
        } else if (d == Direction.EAST) {
            return moveRight();
        } else {
            System.err.println("Direction impossible, fonction moveNSWE()");
            System.exit(0);
        }
        return false;
    }

    /**
     * @param p1   position temporaire 1
     * @param p2   position temporaire 2
     * @param dir1 direction temporaire 1
     * @param dir2 direction temporaire 2
     * @param d    direction globale (NE,SE,SW,NW)
     * @return
     */
    private boolean choiceBetween2move(Position p1, Position p2, Direction dir1, Direction dir2,
                                       Direction d) {
        int choixDep = PRNG.nextInt(2);
        Direction d1;
        Direction d2;

        if (choixDep == 0) {
            d1 = dir1;
            d2 = dir2;
        } else {
            d1 = dir2;
            d2 = dir1;
        }
        p1.deplacement(d1);
        p2.deplacement(d2);
        if (grille.isFree(p1)) {
            return moveNSWE(d1);
        } else if (grille.isFree(p2)) {
            return moveNSWE(d2);
        } else {
            if (choixDep == 0 && grille.isInTab(p1)) {
                boite.addMessage(grille.getId(p1), new MessageDemande(id, current, d1, p1));
            } else if (grille.isInTab(p2)) {
                boite.addMessage(grille.getId(p2), new MessageDemande(id, current, d2, p2));
            }
        }
        return false;
    }

    private Direction getDir() {
        Direction d = null;
        if (current.Y() < target.Y()) {
            d = Direction.SOUTH;
        } else if (current.Y() > target.Y()) {
            d = Direction.NORTH;
        }

        if (current.X() < target.X()) {
            if (d == Direction.NORTH) {
                d = Direction.NE;
            } else if (d == Direction.SOUTH) {
                d = Direction.SE;
            } else {
                d = Direction.EAST;
            }
        } else if (current.X() > target.X()) {
            if (d == Direction.NORTH) {
                d = Direction.NW;
            } else if (d == Direction.SOUTH) {
                d = Direction.SW;
            } else {
                d = Direction.WEST;
            }
        }
        return d;
    }

    private boolean gestion_move(Direction d) {
        synchronized (grille) {
            if (d == null) {
                return false;
            }
            Position p;
            Position p2;
            p = current.clone();

            switch (d) {
                case NORTH:
                    p.up();
                    if (grille.isFree(p)) {
                        return moveUp();
                    } else {
                        if (grille.isInTab(p)) {
                            boite.addMessage(grille.getId(p),
                                    new MessageDemande(id, current, d, p));
                        }
                    }
                    break;
                case SOUTH:
                    p.down();
                    if (grille.isFree(p)) {
                        return moveDown();
                    } else {
                        if (grille.isInTab(p)) {
                            boite.addMessage(grille.getId(p),
                                    new MessageDemande(id, current, d, p));
                        }
                    }

                    break;
                case EAST:
                    p.right();
                    if (grille.isFree(p)) {
                        return moveRight();
                    } else {
                        if (grille.isInTab(p)) {
                            boite.addMessage(grille.getId(p),
                                    new MessageDemande(id, current, d, p));
                        }
                    }
                    break;
                case WEST:
                    p.left();
                    if (grille.isFree(p)) {
                        return moveLeft();
                    } else {
                        if (grille.isInTab(p)) {
                            boite.addMessage(grille.getId(p),
                                    new MessageDemande(id, current, d, p));
                        }
                    }
                    break;
                case NE:
                    p2 = p.clone();
                    return choiceBetween2move(p, p2, Direction.NORTH, Direction.EAST, Direction.NE);
                case NW:
                    p2 = p.clone();
                    return choiceBetween2move(p, p2, Direction.NORTH, Direction.WEST, Direction.NW);
                case SE:
                    p2 = p.clone();
                    return choiceBetween2move(p, p2, Direction.SOUTH, Direction.EAST, Direction.SE);
                case SW:
                    p2 = p.clone();
                    return choiceBetween2move(p, p2, Direction.SOUTH, Direction.WEST, Direction.SW);
            }
            return false;
        }
    }


    @Override
    public void run() {
        moveMsg = null;
        while (!grille.isFinished()) {
            Direction d = getDir();
            Position postmp;

            MessageDemande m = (MessageDemande) boite.getFirstMessage(id);
            if (moveMsg == null || grille.getId(moveMsg.sourcePosition) == moveMsg.source) {
                moveMsg = null;

                if (m != null) {
                    boite.removeAllMessages(id);
                    if (m.destinationPosition.equals(current)) {
                        Direction[] directions = Direction.NW.perpendiculair(m.sourceDirection);
                        Direction dtmp = directions[PRNG.nextInt(directions.length)];

                        if (gestion_move(dtmp)) {
                            moveMsg = m;
                        } else {
                            if (grille.isInTab(current,dtmp)) {
                                nextMove = dtmp;
                            }
                        }
                        //System.out.println(grille.toString());
                    }
                } else {
                    if (nextMove != null) {
                        if (gestion_move(nextMove)) {
                            nextMove = null;
                        }
                    } else {
                        gestion_move(d);
                    }
                }
            }
            //System.out.println(grille.toString());
        }
        System.out.println("end of thread " + id);
    }


}
