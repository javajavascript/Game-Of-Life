package GOL;

import java.util.*; 

/**
 * The square (traditional) version of the GOL
 */
public class SquareGOL
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
            }
        }
    }

	/**
	 * Prints the GOL grid
	 */
    public static void print(boolean[][] alive)
    {//this print method will add spaces between spaces, both horizontally and vertically
        //this is because the unicode character has a space above and below the square
        //so, a space is needed between the squares so that the board looks equally spaced
        //assume that alive is squared!
        int n = alive.length;
        int c = 0x25A0;
        char code = (char)c;
        int c2 = 0x25A1;
        char code2 = (char)c2;
        for(int i = 0; i < n; ++i)
        {
            for(int j = 0; j < n; ++j)
            {
                if(alive[i][j])
                {
                    System.out.print(code + " ");
                }
                else
                {
                    System.out.print(code2+ " ");
                }
            }
            System.out.println();
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
        //(i,j) are coordinates in a rectangular plane
        //we are checking if the neighbors of (i,j) are alive
        int[][] neighborXY = getNeighs(alive, i, j);
        for (int r = 0; r<neighborXY.length; r++)
        {
            if(isAlive(alive,neighborXY[r][0],neighborXY[r][1]))
            {
                aliveN++;
            }
        }
        return aliveN;
    }

	/**
	 * Returns the neighbors in a square grid
	 */
    public static int[][] getNeighs(boolean[][] alive, int i, int j)
    {
        int[][] neighbors = new int[8][2];
        neighbors[0][0] = i-1;
        neighbors[0][1] = j-1;
        neighbors[1][0] = i-1;
        neighbors[1][1] = j;
        neighbors[2][0] = i-1;
        neighbors[2][1] = j+1;
        neighbors[3][0] = i;
        neighbors[3][1] = j+1;
        neighbors[4][0] = i+1;
        neighbors[4][1] = j+1;
        neighbors[5][0] = i+1;
        neighbors[5][1] = j;
        neighbors[6][0] = i+1;
        neighbors[6][1] = j-1;
        neighbors[7][0] = i;
        neighbors[7][1] = j-1;
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
            }
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
