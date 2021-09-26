package GOL;

import java.util.*; 

/**
 * The hexagonal version of the GOL
 */
public class HexagonGOL
{
	/**
	 * Initializes the GOL grid
	 */
    public static void init(boolean[][] alive, double percentage)
    {
        //assume that alive is squared!
        int n = alive.length;
        for(int i = 0; i < n; ++i)
        {
            for(int j = 0; j < n; ++j)
            {
                alive[i][j] = Math.random() < percentage;
                //System.out.print(alive[i][j] +" ");
            }
            //System.out.println();
        }
    }

	/**
	 * Prints the GOL grid
	 */
    public static void print(boolean[][] alive)
    {
        //assume that alive is squared!
        int n = alive.length;
        int c = 0x2B22;
        char code = (char)c;
        int c2 = 0x2B21;
        char code2 = (char)c2;
        for(int i = 0; i < n; ++i)//because cells every other row
        {
            if (i%2 == 0)
            {
                for(int j = 0; j < n; ++j)
                {
                    if (alive[i][j])
                    {
                        System.out.print(code+" ");
                    }
                    else
                    {
                         System.out.print(code2+" ");
                    }
                }
                System.out.println();
            }
            else//i%2 == 1
            {
                System.out.print(" ");
                for(int j = 0; j < n; ++j)
                {
                    if (alive[i][j])
                    {
                        System.out.print(code+" ");
                    }
                    else
                    {
                         System.out.print(code2+" ");
                    }
                }
                System.out.println();
            }
        }
        System.out.println();
    }
    
	/**
	 * Returns whether a given cell is alive or not
	 */
    public static boolean isAlive(boolean[][] alive, int i, int j)
    {
        //assume that alive is squared!
        //assume that i > -n
        int n = alive.length;
        //i=-1
        //(x + kn) % n = x % n
        int x = (i + n) % n;
        int y = (j + n) % n;
        //if (i,j) is on the edge of the board, then it has to check x=-i,y=-j
        //thus, x and y become negative, so to prevent that
        //the modulus turns x = -1 into x = 9 by going to the other side
        return alive[x][y];
    }
    
	/**
	 * Returns the number of alive neighbors around a given cell
	 */
    public static int aliveNeigh(boolean[][] alive, int i, int j)
    {
        int aliveN = 0;
        //(i,j) are coordinates in a hexagonal plane
        //we are checking if the neighbors of (i,j) are alive
        int[][] neighborXY = getNeighs(alive, i, j);
        for (int x = 0; x <neighborXY.length; x++)
        {
            if(isAlive(alive,neighborXY[x][0],neighborXY[x][1]))
            {
                aliveN++;
            }
        }
        return aliveN;
    }

	/**
	 * Returns the neighbors in a hexagonal grid
	 */
    public static int[][] getNeighs(boolean[][] alive, int i, int j)
    {
        int[][] neighbors = new int[6][2];
        if (i%2 == 0)//even rows
        {
            neighbors[0][0] = i-1;
            neighbors[0][1] = j-1;
            neighbors[1][0] = i-1;
            neighbors[1][1] = j;
            neighbors[2][0] = i;
            neighbors[2][1] = j-1;
            neighbors[3][0] = i;
            neighbors[3][1] = j+1;
            neighbors[4][0] = i+1;
            neighbors[4][1] = j-1;
            neighbors[5][0] = i+1;
            neighbors[5][1] = j;
        }
        if (i%2 == 1)//odd rows
        {
            neighbors[0][0] = i-1;
            neighbors[0][1] = j;
            neighbors[1][0] = i-1;
            neighbors[1][1] = j+1;
            neighbors[2][0] = i;
            neighbors[2][1] = j-1;
            neighbors[3][0] = i;
            neighbors[3][1] = j+1;
            neighbors[4][0] = i+1;
            neighbors[4][1] = j;
            neighbors[5][0] = i+1;
            neighbors[5][1] = j+1;
        }
        return neighbors;
    }

	/**
	 * Update the grid/proceed to the next phase of the GOL
	 */
    public static void update(boolean[][] alive, int[] born, int[] surviving)
    {
        //assume that alive is squared!
        int n = alive.length;
        boolean[][] next = new boolean[n][n];
        for(int i = 0; i < n; ++i)
        {
            for(int j = 0; j < n; ++j)
            {
                int nAlive = aliveNeigh(alive, i, j);
                //current cell is alive
                if(alive[i][j])
                {
                    if(nAlive < surviving[0])
                        next[i][j] = false;
                    else if(nAlive == surviving[0] || nAlive == surviving[1])
                        next[i][j] = true;
                    else
                        next[i][j] = false;
                }
                else//the current cell is dead
                {
                    if(nAlive == born[0])
                        next[i][j] = true;
                    else
                        next[i][j] = false;
                }
                //System.out.print(nAlive);
            }
            //System.out.println();
        }

        for(int i = 0; i < n; ++i)
        {
            for(int j = 0; j < n; ++j)
            {
                alive[i][j] = next[i][j];
            }
        }
    }

	/**
	 * Starts the game and allows user to customize the game
	 */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter grid size, suggested: 10");
        int n = scanner.nextInt();
        boolean[][] alive = new boolean[n][n];//alive is the board
        System.out.println("Enter number of neighbors to survive, suggested: 2");
        int surviveN = scanner.nextInt();
        System.out.println("Enter another number of neighbors to survive, suggested: 3");
        int surviveN2 = scanner.nextInt();
        System.out.println("Enter number of neighbors to be born, suggested: 3");
        int bornN = scanner.nextInt();
        int[] surviving = {surviveN,surviveN2};
        int[] born = {bornN};//convert to array is needed for method "update"
        init(alive, 0.3);//0.3 start as alive
        // print(alive);
        // update(alive, born, surviving);
        // print(alive);
        while(true)
        {
        print(alive);
        update(alive, born, surviving);
        try
        {
        Thread.sleep(100);
        }
        catch(InterruptedException ex)
        {
        Thread.currentThread().interrupt();
        }
        //System.out.print("\033[H\033[2J");
        System.out.flush();
        }
    }
}
