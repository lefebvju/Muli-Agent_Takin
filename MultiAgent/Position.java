import java.util.Random;

import static java.lang.Math.abs;

public class Position {
    private int x, y;

    private static final Random PRNG = new Random();

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position random(int X, int Y){
        return new Position(PRNG.nextInt(X), PRNG.nextInt(Y));
    }

    public int X(){
        return x;
    }

    public int Y(){
        return y;
    }

    public void up(){
        x--;
    }
    public void down(){
        x++;
    }
    public void left(){
        y--;
    }
    public void right(){
        y++;
    }

    public void deplacement(Direction d) {
        if(d==Direction.NORTH) {
            up();
        }else if (d==Direction.SOUTH) {
            down();
        }else if (d==Direction.WEST) {
            left();
        } else if(d==Direction.EAST) {
            right();
        } else {
            System.err.println("Direction impossible, fonction deplacement(), ligne 50");
            System.exit(0);
        }
    }

    public Boolean equals(Position p){
        return (x==p.x && y==p.y);
    }

    public Boolean equals(int _x, int _y){
        return (x==_x && y==_y);
    }

    public int distance(Position p){
        return (abs(x-p.x)+abs(y-p.y));
    }

    public int distance(int _x, int _y){
        return (abs(x-_x)+abs(y-_y));
    }

    protected Position clone() {
        return new Position(x,y);
    }

    @Override
    public String toString() {
        return "("+x+":"+y+")";
    }
}
