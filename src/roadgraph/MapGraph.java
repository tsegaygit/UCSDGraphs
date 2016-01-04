/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
/**
 * @author Tsegay Tekle
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 2
	
	/*
	 * graph vertices 
	 */
	private HashMap<GeographicPoint, MapNode> vertices;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2
		this.vertices = new HashMap<GeographicPoint,MapNode>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		return vertices.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		Set<GeographicPoint> result = vertices.keySet(); //create a copy of the vertices
		return result; //return the vertices without exposing the private elements of the graph
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		int numEdges =0;
		for(GeographicPoint key : this.vertices.keySet()){
			//for every geographic point add the number of edges
			numEdges+=this.vertices.get(key).getEdges().size(); 
		}
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
		if(location==null) //check for null parameter
			return false;
		//check if the node was already in the graph
		if(this.vertices.containsKey(location))
			return false;
		this.vertices.put(location, new MapNode(location));
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 2
		
		/*
		 * check if the geographic points have already been added, 
		 * otherwise throw IllegalArgumentException exception
		 * 
		 */
		if(!this.vertices.containsKey(from)){
			throw new IllegalArgumentException("The Geographic Point "+from+ " has not been added to the graph");
		}
		
		if(!this.vertices.containsKey(to)){
			throw new IllegalArgumentException("The Geographic Point "+from+ " has not been added to the graph");
		}
		//check if roadName argument is null
		if(roadName==null){
			throw new IllegalArgumentException("road name can not be null");
		}
		
		//check if roadtype is null
		if(roadType==null){
			throw new IllegalArgumentException("road type can not be null");
		}
		
		//check if length argument has a negative value
		if(length<0){
			throw new IllegalArgumentException("the distance between two edges can not be less than zero");
		}
		
		//add the new adge
		this.vertices.get(from).getEdges().add(new MapEdge(new MapNode(from),new MapNode(to),roadType,roadName,length));
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 2
		/*
		 * check if the geographic points have already been added, 
		 * otherwise throw IllegalArgumentException exception
		 * 
		 */
		if(!this.vertices.containsKey(start)){
			throw new IllegalArgumentException("The Geographic Point "+start+ " has not been added to the graph");
		}
		
		if(!this.vertices.containsKey(goal)){
			throw new IllegalArgumentException("The Geographic Point "+goal+ " has not been added to the graph");
		}
		
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		
		
		/*
		 * Initialize MapNode objects with the start and goal points and invoke the breadth first search method
		 * 
		 */
		boolean success = this.dfsSearch(new MapNode(start),new MapNode(goal),parentMap,nodeSearched);
		
		/*
		 * 
		 */
		if(!success){
			System.out.println("no path found!");
			return null;
		}
		/*
		 * At this point the breadth first search above have found a path
		 * 
		 * Reconstruct the path and return a list of nodes from start to the goal node
		 */
		return constructPath(new MapNode(start),new MapNode(goal),parentMap);
	}
	
	
	/**
	 * construct the path found by the breadth first search
	 * 
	 * @param start point of the path
	 * @param goal end point of the path
	 * @param parentMap tracks the parent node of every visited nodes
	 * @return 
	 */
	private List<GeographicPoint> constructPath(MapNode start, MapNode goal,
			HashMap<MapNode, MapNode> parentMap)
	{
		//
		/*
		 * Create an empty linked list to construct the path
		 * 
		 * 
		 */
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		
		//start from the goal node to construct the path
		MapNode curr = goal;
		
		/*
		 * loop until we get to the start point of the graph and construct the path.
		 * 
		 * the while loop below won't add the start point of the graph, 
		 * as the start point of the graph was not added to the parentMap when performing dfs search
		 * 
		 */
		while (!curr.equals(start)) {
			//add current node to the path
			path.addFirst(curr.getLocation());
			
			//update current node from the parent map 
			curr = parentMap.get(curr);
			
		}
		/*
		 * Add the start point of the graph. 
		 * 
		 * The line below is important, as the start point of the graph was not added to the parentMap when performing dfs search
		 */
		path.addFirst(start.getLocation());
		
		return path;
	}
	
	/**
	 * depth first search from start to goal
	 * 
	 * @param start point of the path
	 * @param goal end point of the path
	 * @param parentMap
	 * @param nodeSearched
	 * @return true if there is a path from the start to the goal point otherwise returns false
	 */
	private boolean dfsSearch(MapNode start, MapNode goal, 
			HashMap<MapNode, MapNode> parentMap, Consumer<GeographicPoint> nodeSearched)
	{
		//Initialize 
		HashSet<MapNode> visited = new HashSet<MapNode>();
		Queue<MapNode> toExplore = new LinkedList<MapNode>();

		//add start point to queue
		toExplore.add(start);

		boolean found = false;

		// Do the search
		while (!toExplore.isEmpty()) {
			/*
			 * Retrieve the head element, it will also removes the head of this queue.
			 */
			MapNode curr = toExplore.remove();
		
			nodeSearched.accept(curr.getLocation());
			//check if we are at the goal point
			if (curr.equals(goal)) {
				
				found = true; //yes we have the path
				break; // stop while loop and return true
			}
			/*
			 * get the current node neighbors
			 */
			List<MapNode> neighbors = this.vertices.get(curr.getLocation()).getNeighbors();
			
			/*
			 * create an iterator for the neighbors
			 * 
			 * set the cursor at the last element of the list
			 */
			ListIterator<MapNode> it = neighbors.listIterator(neighbors.size());
			
			/*
			 * continue if the list iterator has more elements when traversing the list in the reverse direction
			 */
			while (it.hasPrevious()) {
				MapNode next = it.previous();
				/*
				 * If the next element is already visited, skip it and continue to the next neighbor
				 * 
				 * override equals and hashCode methods of the Object class on MapNode class for the visited HashSet contains method to work properly
				 * 
				 * without the override, the contains method below won't find elements which already exist in the HashSet
				 * 
				 */
				if (!visited.contains(next)) {
					//add the current node to the visited list
					visited.add(next); 
					// create parent map
					parentMap.put(next,curr); 
					// add the next neighbor to the queue to be considered as the possible path to the goal
					toExplore.add(next); 
				}
			}
		}
		
		//
		return found;
		
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		
		System.out.println("Number of vertices: "+theMap.getNumVertices());
		System.out.println("Number of adges: "+theMap.getNumEdges());
		Consumer<GeographicPoint> temp = (x) -> {};
		
//		System.out.println(theMap.bfs(new GeographicPoint(6.5, 0.0), new GeographicPoint(5.0, 1.0),temp));
		System.out.println(theMap.bfs(new GeographicPoint(1, 1), new GeographicPoint(8,-1),temp));
		
	//	System.out.println(new MapNode(new GeographicPoint(8, -1)) == new MapNode(new GeographicPoint(8, -1)));
		// You can use this method for testing.  
		
		/* Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
