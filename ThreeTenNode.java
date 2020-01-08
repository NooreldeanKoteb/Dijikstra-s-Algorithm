import org.apache.commons.collections15.Factory;

import java.awt.Color;

import java.util.*;

/**
 * 
 * @author Katherine (Raven) Russell
 *
 */
class ThreeTenNode implements Comparable<ThreeTenNode> {
	/**
	 * Instance Variable holding node count
	 */
	public static int nodeCount = 0;
	/**
	 * Instance Variable holding id value
	 */
	private int id;
	/**
	 * Instance Variable holding node text
	 */
	private String text;
	/**
	 * Instance Variable holding node color
	 */
	private Color c = Color.WHITE;

	/**
	 * This constructor sets the id and text for the node
	 */
	public ThreeTenNode() {
		this.id = nodeCount++;
		this.text = "" + this.id;
	}

	/**
	 * This method returns the color of the node
	 * 
	 * @return c color of the node
	 */
	public Color getColor() {
		return c;
	}

	/**
	 * This method sets the node color
	 * 
	 * @param c
	 *            color of node
	 */
	public void setColor(Color c) {
		this.c = c;
	}

	/**
	 * This method returns the text in the node
	 * 
	 * @return text node text
	 */
	public String getText() {
		return text;
	}

	/**
	 * This method sets the node text
	 * 
	 * @param text
	 *            node text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * This method returns a string of the node text
	 * 
	 * @return text string of node text
	 */
	@Override
	public String toString() {
		return "" + text;
	}

	/**
	 * This method checks if node ids are equal
	 * 
	 * @param o
	 *            object to compare node id to
	 * @return true if they are equal
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof ThreeTenNode) {
			return this.id == ((ThreeTenNode) o).id;
		}
		return false;
	}

	/**
	 * This method compares node ids
	 * 
	 * @param n
	 *            Node to compare id to
	 * @return which node has a greater id
	 */
	@Override
	public int compareTo(ThreeTenNode n) {
		return this.id - n.id;
	}

	/**
	 * This method returns the hashCode
	 * 
	 * @return id of the node
	 */
	@Override
	public int hashCode() {
		return id;
	}

	/**
	 * This Method creates a node
	 * 
	 * @return calls node creation method
	 */
	public static Factory<ThreeTenNode> getFactory() {
		return new Factory<ThreeTenNode>() {
			public ThreeTenNode create() {
				return new ThreeTenNode();
			}
		};
	}
}
