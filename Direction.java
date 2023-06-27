import java.util.ArrayList;
import java.util.List;

public enum Direction {
    NORTH,SOUTH,EAST,WEST,NW,SW,NE,SE;

    public boolean equivalent(Direction d){
        if(this==d) {
            return true;
        }
        switch (this){
            case NORTH:
                return (d==NW || d==NE);
            case SOUTH:
                return (d==SW || d==SE);
            case EAST:
                return (d==NE || d==SE);
            case WEST:
                return (d==NW || d==SW);

        }
        return true;
    }

    public Direction[] perpendiculair(){
        Direction[] listDir = new Direction[9];
        switch (this){
            case NORTH:
            case SOUTH:
                listDir[0]=EAST;
                listDir[1]=WEST;
                listDir[2]=EAST;
                listDir[3]=WEST;
                listDir[4]=EAST;
                listDir[5]=WEST;
                listDir[6]=EAST;
                listDir[7]=WEST;
                break;
            case EAST:
            case WEST:
                listDir[0]=NORTH;
                listDir[1]=SOUTH;
                listDir[2]=NORTH;
                listDir[3]=SOUTH;
                listDir[4]=NORTH;
                listDir[5]=SOUTH;
                listDir[6]=NORTH;
                listDir[7]=SOUTH;
                break;
        }
        listDir[8]=this;
        return listDir;
    }
}
