package canessa.shortest.path;

public class Solution {

	/**
	 * number of nodes in the graph
	 */
	static final int V = 9;

	/**
	 * Display the shortestDist array.
	 */
	static void displayDist(int shortestDist[]) {
		System.out.print("shortestDist: ");
		for (int i = 0; i < shortestDist.length; i++) {
			if (shortestDist[i] == Integer.MAX_VALUE)
				System.out.printf("  .");
			else
				System.out.printf("%3d", shortestDist[i]);
		}
		System.out.println();
	}

	/**
	 * Display the Shortest Path Tree (SPT) set.
	 */
	static void displaySPTSet(Boolean sptSet[]) {
		System.out.print("sptSet: ");
		for (int i = 0; i < sptSet.length; i++) {
			if (sptSet[i] == true)
				System.out.print("  T");
			else
				System.out.print("  F");
		}
		System.out.println();
	}

	/**
	 * 
	 */
	static void printSolution(int src, int[] shortestDist) {
		System.out.println("From\tto\tDistance");
		for (int i = 0; i < V; i++)
			System.out.println(src + "\t" + i + "\t" + shortestDist[i]);
	}

	/**
	 * Utility function to find the vertex with minimum distance value, from the set
	 * of vertices not yet included in shortest path tree.
	 */
	static int minDistance(int shortestDist[], Boolean sptSet[]) {

		int min = Integer.MAX_VALUE;
		int minIndex = -1;

		for (int v = 0; v < V; v++) {
			if ((sptSet[v] == false) && (shortestDist[v] <= min)) {
				min = shortestDist[v];
				minIndex = v;
			}
		}
		System.out.println("   min:   " + min);

		return minIndex;
	}

	/**
	 * Function that implements Dijkstra's single source shortest path algorithm for
	 * a graph represented using an adjacency matrix representation.
	 * 
	 * Given a graph and a source vertex in the graph, find shortest paths from
	 * source to all vertices in the given graph.
	 */
	static void dijkstra(int graph[][], int src) {

		int shortestDist[] = new int[V]; // holds the shortest distance from src to i
		Boolean sptSet[] = new Boolean[V]; // true if vertex i is included in shortest path tree
		// or shortest distance from src to i is finalized
		int prevV[] = new int[V]; // holds the previous vertex to this one

		// **** initialize all distances as INFINITE and stpSet[] as false ****
		for (int i = 0; i < V; i++) {
			shortestDist[i] = Integer.MAX_VALUE; // distance from source
			sptSet[i] = false; // SPT (shortest path tree)
			prevV[i] = Integer.MIN_VALUE; // no previous path
		}

		// **** distance of source vertex from itself is always 0 ****
		shortestDist[src] = 0;

		// **** find shortest path for all vertices ****
		for (int count = 0; count < V - 1; count++) {

			// **** pick the minimum distance vertex from the set of vertices not yet
			// processed. u is always equal to src in first iteration ****
			displaySPTSet(sptSet);
			displayDist(shortestDist);
			int u = minDistance(shortestDist, sptSet);
			System.out.println("     u:   " + u);

			// **** include u to sptSet ****
			sptSet[u] = true;

			// **** update distance value of all adjacent vertices of u ****
			for (int v = 0; v < V; v++) {
				if (!sptSet[v] && // not included in Shortest Path Tree (SPT)
						graph[u][v] != 0 && // adjacent
						shortestDist[u] != Integer.MAX_VALUE && // distance has been previously computed
						shortestDist[u] + graph[u][v] < shortestDist[v]) // sum of distance value of u and weight of edge u-v,
					// is less than the distance value of v
					shortestDist[v] = shortestDist[u] + graph[u][v];
			}
			displayDist(shortestDist);
			System.out.println();
		}

		// **** print solution ****
		printSolution(src, shortestDist);
	}

	/**
	 * Test scaffolding or driver code.
	 */
	public static void main(String[] args) {

		// **** define graph ****
		int graph[][] = new int[][] {
				// TO: 0 1 2 3 4 5 6 7 9 FROM:
				{ 0, 4, 0, 0, 0, 0, 0, 8, 0 }, // 0
				{ 4, 0, 8, 0, 0, 0, 0, 11, 0 }, // 1
				{ 0, 8, 0, 7, 0, 4, 0, 0, 2 }, // 2
				{ 0, 0, 7, 0, 9, 14, 0, 0, 0 }, // 3
				{ 0, 0, 0, 9, 0, 10, 0, 0, 0 }, // 4
				{ 0, 0, 4, 14, 10, 0, 2, 0, 0 }, // 5
				{ 0, 0, 0, 0, 0, 2, 0, 1, 6 }, // 6
				{ 8, 11, 0, 0, 0, 0, 1, 0, 7 }, // 7
				{ 0, 0, 2, 0, 0, 0, 6, 7, 0 } // 8
		};

		// **** compute shortest distance from specified node to all others ****
		dijkstra(graph, 0);
	}

}
