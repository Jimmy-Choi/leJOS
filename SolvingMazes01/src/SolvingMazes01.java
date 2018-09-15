import java.util.List;
import java.util.Stack;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.utility.Delay;

public class SolvingMazes01 {
	static public final int NODE_MAX = 25;
	static public final int EDGE_MAX = 2;
	private static int adj[][] = new int[NODE_MAX][EDGE_MAX];
	private static Stack<Integer> root = new Stack<Integer>();
	private static int rowCnt = 0;
	
	private static PathFinder pf;

	public static void main(String[] args) {
		FileAccess fa = new FileAccess("maze.txt");
		if (!fa.fileExists()) {
			return;			
		}
		
		List<String> maze = fa.readData();
		int len = maze.get(0).length();
		rowCnt = len / 2 + 1;
		initMazes(maze);
		
		pf = new PathFinder();
        
        System.out.println("Line Follower\n");
        
        Button.LEDPattern(4);    // flash green led and 
        Sound.beepSequenceUp();  // make sound when ready.

        System.out.println("Press any key to start");
        
        Button.waitForAnyPress();
        findRoot(0, 0);
        pf.goNext();

        Delay.msDelay(1000);
        
        // stop motors with brakes on. And free up resources.
        pf.stop();       
        Sound.beepSequence(); // we are done.*/
	}
	
	public static void initMazes(List<String> maze) {
		int len = maze.get(0).length();
		rowCnt = len / 2 + 1;
		
		for (int i = 0; i < maze.size(); i++) {
			if (i % 2 == 0) {
				for (int j = 0; j < len - 1; j = j + 2) {
					if (maze.get(i).substring(j + 1, j + 2).equals("-")) {
						int node =  (i / 2 * (len / 2 + 1)) + (j / 2);			
						adj[node][0] = 1;
					}
				}
			} else {
				for (int j = 0; j < len; j = j + 2) {
					if (maze.get(i).substring(j, j + 1).equals("|")) {
						int node =  (i / 2 * (len / 2 + 1)) + (j / 2);
						adj[node][1] = 1;
					}
				}
			}
		}
	}
	
	public static boolean findRoot(int idx, int dir) {
		root.push(idx);
		if (idx == NODE_MAX - 1) {
			return true;
		}
		
		boolean isEdge = false;
		int lastEdge = -1;
	    for (int i = 0; i < EDGE_MAX; i++) {
	    	if (adj[idx][i] == 1) {
	    		if (i == 0) {
	    			pf.goNext();
	    			if (dir == 0) {
	    				pf.goStraight();
	    			} else {
	    				pf.turnLeft();
	    			}
	    			isEdge = findRoot(idx + 1, 0);
	    			lastEdge = 0;
	    		} else {
	    			if (!isEdge) {
		    			pf.goNext();
	    				if (adj[idx][0] == 1) {
	    					pf.turnLeft();
	    				} else {
		    				if (dir == 0) {
		    					pf.turnRight();
			    			} else {
			    				pf.goStraight();
			    			}
	    				}
	    				isEdge = findRoot(idx + rowCnt, 1);
	    				lastEdge = 1;
	    			}
	    		}
	    	}
	    }
	    if (!isEdge) {
	    	pf.goNext();
	    	if (lastEdge == -1) {
	    		pf.uTurn();
	    	} else if (lastEdge == 0) {
	    		if (dir == 0) {
	    			pf.goStraight();
	    		} else {
	    			pf.turnRight();
	    		}
	    	} else {
	    		if (dir == 0) {
	    			pf.turnLeft();
	    		} else {
	    			pf.goStraight();
	    		}
	    	}
	    	root.pop();
	    }
	    return isEdge;
	}
}
