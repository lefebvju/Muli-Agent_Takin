import java.util.ArrayList;
import java.util.List;

public enum Direction {
    NORTH,SOUTH,EAST,WEST,NW,SW,NE,SE;

    public Direction[] perpendiculair(Direction d){
        Direction[] listDir = new Direction[3];
        switch (d){
            case NORTH:
            case SOUTH:
                listDir[0]=EAST;
                listDir[1]=WEST;
                break;
            case EAST:
            case WEST:
                listDir[0]=NORTH;
                listDir[1]=SOUTH;
                break;
        }
        listDir[2]=d;
        return listDir;
    }
}
