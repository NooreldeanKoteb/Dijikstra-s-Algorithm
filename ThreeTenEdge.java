import org.apache.commons.collections15.Factory;

import java.awt.Color;

import java.util.*;

/**
 * 
 * @author Katherine (Raven) Russell
 *
 */
class ThreeTenEdge {
	/**
	 * Instance Variable holding edge count
	 */
	public static int edgeCount = 0;
	/**
	 * Instance Variable holding a random value
	 */
	private static Random r = new Random(0);
	/**
	 * Instance Variable holding edge id
	 */
	private int id;
	/**
	 * Instance Variable holding edge weight
	 */
	private int weight = 0;
	/**
	 * Instance Variable holding color value
	 */
	private Color c = Color.BLACK;

	/**
	 * This constructor sets edge id and weight
	 */
	public ThreeTenEdge() {
		this.id = edgeCount++;
		this.weight = r.nextInt(10) + 1;
	}

	/**
	 * This constructor sets edge id and weight
	 * 
	 * @param weight
	 *            weight of edge
	 */
	public ThreeTenEdge(int weight) {
		this.id = edgeCount++;
		this.weight = weight;
	}

	/**
	 * This Method returns the weight of the edge
	 * 
	 * @return weight of the edge
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * This Method sets the weight of the edge
	 * 
	 * @param weight
	 *            weight of edge
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * This Method returns the color of the edge
	 * 
	 * @return c color of the edge
	 */
	public Color getColor() {
		return c;
	}

	/**
	 * This Method sets the weight of the edge
	 * 
	 * @param c
	 *            color of edge
	 */
	public void setColor(Color c) {
		this.c = c;
	}

	/**
	 * This Method checks if an object is equal to the edge
	 * 
	 * @param o
	 *            Object to compare to
	 * @return true if they are equal
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof ThreeTenEdge) {
			return this.id == ((ThreeTenEdge) o).id;
		}
		return false;
	}

	/**
	 * This Method returns a hashcode
	 * 
	 * @return hashcode of edge
	 */
	@Override
	public int hashCode() {
		return id;
	}

	/**
	 * This Method creates an edge
	 * 
	 * @return calls edge creation method
	 */
	public static Factory<ThreeTenEdge> getFactory() {
		return new Factory<ThreeTenEdge>() {
			public ThreeTenEdge create() {
				return new ThreeTenEdge();
			}
		};
	}

	/**
	 * This Method returns the weight as a string
	 * 
	 * @return weight of the edge in string
	 */
	@Override
	public String toString() {
		return "" + weight;
	}
}
