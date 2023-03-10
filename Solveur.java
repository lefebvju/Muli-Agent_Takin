import java.lang.reflect.Array;
import java.util.Arrays;

import static java.lang.Math.abs;

public class Solveur {

    public static boolean soluble(int[][] current, int[][] target, int sizeX, int sizeY){
        int[][] tabCurrent = copie(current,sizeX,sizeY);
        int[][] tabTarget = copie(target,sizeX,sizeY);
        int nb=dist(find(tabCurrent,-1,sizeX,sizeY),find(tabTarget,-1,sizeX,sizeY));
        for (int i = 0; i<sizeX*sizeY; i++){
                Position init = find(tabCurrent,i,sizeX,sizeY);
                Position fin = find(tabTarget,i,sizeX,sizeY);
                if(init.X() != fin.X() || init.Y() !=fin.Y()){
                    permutation(init.X(),init.Y(),fin.X(),fin.Y(),tabCurrent);
                    nb++;
                }
        }
        //System.out.println("Solveur permut : "+nb);
        return (nb%2)==0;
    }


    private static void permutation(int i, int j, int x, int y, int[][] tab){
        int tmp = tab[i][j];
        tab[i][j] = tab[x][y];
        tab[x][y] = tmp;

    }

    private static Position find(int[][] tab, int s, int sizeX, int sizeY) {
        for (int i =0; i< sizeX;i++ ){
            for (int j =0; j<sizeY; j++){
                if(tab[i][j] == s) return new Position(i,j);
            }
        }
        return new Position(0,0);
    }

    private static int[][] copie(int[][] tab, int sizeX, int sizeY){
        int[][] copie = new int[sizeX][sizeY];
        for (int i=0;i<sizeX;i++){
            for(int j=0;j<sizeY;j++){
                copie[i][j]=tab[i][j];
            }
        }
        return copie;
    }

    private static int dist (Position p1, Position p2) {
        return abs(p1.X()-p2.X()) + abs(p1.Y()-p2.Y());
    }
}
