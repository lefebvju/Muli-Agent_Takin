import static java.lang.Math.abs;

public class Position {
    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int X(){
        return x;
    }

    public int Y(){
        return y;
    }

    public void up(){
        y--;
    }
    public void down(){
        y++;
    }
    public void left(){
        x--;
    }
    public void right(){
        x++;
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
}
