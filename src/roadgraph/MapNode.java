/**
 * A class to represent a Node in a graph which is an intersection between strees on a map
 */
package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

/**
 * @author Tsegay Tekle
 *
 */
public class MapNode {
	private GeographicPoint location;
	private List<MapEdge> edges;

	//Initialize new MapNode with the given location argument
	public MapNode(GeographicPoint location){
		this.location=location;
		this.edges= new ArrayList<MapEdge>();
	}
	
	/**
	 * @return the neighbors
	 */
	public List<MapNode> getNeighbors(){
		List<MapNode> nodes = new ArrayList<MapNode>();
		for(MapEdge edge : this.edges){
			//for each edge on the node, add the node on the other end of the edge
			nodes.add(edge.getEnd());
		}
		
		return nodes;
	}
	
	
	/**
	 * @return geographic point of the node (latitude,longitude)
	 */
	public GeographicPoint getLocation() {
		return location;
	}

	
	/**
	 * @param location
	 */
	public void setLocation(GeographicPoint location) {
		this.location = location;
	}

	/**
	 * @return adges
	 */
	public List<MapEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<MapEdge> edges) {
		this.edges = edges;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return location.toString();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Two MapNode objects are equal to each other if they have the same location (longitude and latitude)
	 */
	@Override
	public boolean equals(Object o) {
		
		MapNode obj = (MapNode)o;
		
		return obj.getLocation().equals(this.location);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * 
	 * This override is necessary for the MapGraph class to function properly.
	 * 
	 */
	@Override
	public int hashCode() {
		return (int) (this.location.getX()*this.location.getY());
	}
	
	
}
