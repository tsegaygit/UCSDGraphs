/**
 * A class to represent an Edge on a graph which is a street that connects two adjacent nodes
 */
package roadgraph;

/**
 * @author Tsegay Tekle
 *
 */
public class MapEdge {
	private MapNode start;
	private MapNode end;
	private String roadName;
	private double length;
	private String roadType;

	//Initialize a new MapEdge
	/**
	 * @param start point for the edge
	 * @param end point for the edge
	 * @param roadType road type of the edge
	 * @param roadName road name
	 * @param length 
	 */
	public MapEdge(MapNode start, MapNode end, String roadType, String roadName, double length) {
		this.start = start;
		this.end = end;
		this.length = length;
		this.roadName = roadName;
		this.roadType = roadType;

	}

	/**
	 * @return start node
	 */
	public MapNode getStart() {
		return start;
	}

	public void setStart(MapNode start) {
		this.start = start;
	}

	/**
	 * @return end node
	 */
	public MapNode getEnd() {
		return end;
	}

	public void setEnd(MapNode end) {
		this.end = end;
	}

	/**
	 * @return road name
	 */
	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	/**
	 * @return road length
	 */
	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * @return road type
	 */
	public String getRoadType() {
		return roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * 
	 * The interface contract for Object requires that if two objects are equal according to equals(), 
	 * then they must have the same hashCode() value.
	 */
	@Override
	public int hashCode() {
		return (int)(this.start.hashCode()*end.hashCode());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Two MapEdge objects are equal if both edges have equal (start & end) geographic points
	 */
	@Override
	public boolean equals(Object obj) {
		MapEdge edge = (MapEdge)(obj);
		return edge.start.equals(this.start) && edge.end.equals(this.end);
	}
	
	

}
