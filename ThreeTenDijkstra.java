import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

import java.awt.Color;

import java.util.*;

/**
 * 
 * @author Nooreldean Koteb
 *
 */
class ThreeTenDijkstra implements ThreeTenAlg {

	// ********************************************************************************
	// DO NOT EDIT ANYTHING IN THIS SECTION except to add JavaDocs
	// ********************************************************************************
	/**
	 * Instance Variable holding color value
	 */
	public static final Color COLOR_DONE_NODE = Color.GREEN;
	/**
	 * Instance Variable holding color value
	 */
	public static final Color COLOR_DONE_EDGE_1 = Color.GREEN.darker();
	/**
	 * Instance Variable holding color value
	 */
	public static final Color COLOR_DONE_EDGE_2 = Color.LIGHT_GRAY;
	/**
	 * Instance Variable holding color value
	 */
	public static final Color COLOR_ACTIVE_NODE_1 = Color.RED;
	/**
	 * Instance Variable holding color value
	 */
	public static final Color COLOR_ACTIVE_NODE_2 = Color.YELLOW;
	/**
	 * Instance Variable holding color value
	 */
	public static final Color COLOR_ACTIVE_EDGE = Color.ORANGE;
	/**
	 * Instance Variable holding color value
	 */
	public static final Color COLOR_NONE_NODE = Color.WHITE;
	/**
	 * Instance Variable holding color value
	 */
	public static final Color COLOR_NONE_EDGE = Color.BLACK;
	/**
	 * Instance Variable holding the infinity unicode
	 */
	public static final String INFINITY_SIGN = "\u221e";
	/**
	 * Instance variable holding algorithm status
	 */
	private boolean started = false;

	// You'll want to use this...
	/**
	 * instance variable of graph
	 */
	private Graph<ThreeTenNode, ThreeTenEdge> g;

	/**
	 * this method returns edge type
	 */
	public EdgeType graphEdgeType() {
		return EdgeType.DIRECTED;
	}

	// And you need to understand this...
	/**
	 * this method goes through one step of Dijkstra's algorithm
	 */
	public boolean step() {
		if (!started) {
			start();
			return true;
		}

		cleanUpLastStep();
		if (!setupNextMin()) {
			finish();
			return false;
		}
		doUpdates();

		return true;
	}

	// And this...
	/**
	 * this Method takes in a graph and resets it, algorithm setting status to false
	 * again
	 * 
	 * @param g
	 *            Graph (ThreeTenNode, ThreeTenEdge)
	 */
	public void reset(Graph<ThreeTenNode, ThreeTenEdge> g) {
		this.g = g;
		started = false;
	}

	// And this...
	/**
	 * This Method sets the new distance for a vertex
	 * 
	 * @param n
	 *            ThreeTenNode - vertex
	 * @param distance
	 *            Integer value of distance to vertex
	 */
	public void setNodeText(ThreeTenNode n, int distance) {
		String text = (distance == Integer.MAX_VALUE ? INFINITY_SIGN : "" + distance);
		n.setText(text);
	}

	// And this...
	/**
	 * This Method sets the update distance for a vertex
	 * 
	 * @param n
	 *            ThreeTenNode - vertex
	 * @param oldCost
	 *            old Integer value of distance to vertex
	 * @param newCost
	 *            new Integer value of distance to vertex
	 */
	public void setNodeText(ThreeTenNode n, int oldCost, int newCost) {
		String text = (oldCost == Integer.MAX_VALUE ? INFINITY_SIGN : "" + oldCost);
		n.setText("" + text + "->" + newCost);
	}

	// ********************************************************************************
	// END DO-NOT-EDIT SECTION
	// ********************************************************************************

	//
	/**
	 * Instance Variable holding nodes that are done
	 */
	private Collection<ThreeTenNode> doneNodes = new HashSet<ThreeTenNode>();
	/**
	 * Instance Variable holding nodes changed in the current step
	 */
	private HashMap<ThreeTenNode, Integer> currentStep = new HashMap<ThreeTenNode, Integer>();
	/**
	 * Instance Variable holding the current node
	 */
	private ThreeTenNode currentNode = null;

	//
	/**
	 * this constructor sets up the algorithm
	 */
	public ThreeTenDijkstra() {
		// do any setup you want here
	}

	/**
	 * This method finds and returns the node with the min-id
	 * 
	 * @return vertex Node with min-id
	 */
	public ThreeTenNode getMinimumIdNode() {
		// get the node with the minimum ID from the graph
		ArrayList<ThreeTenNode> vertices = new ArrayList<ThreeTenNode>(g.getVertices());
		if (vertices.size() <= 0) {
			return null;
		}

		ThreeTenNode vertex = vertices.get(0);

		if (vertices.size() > 1) {
			for (int i = 1; i < vertices.size(); i++) {
				if (vertex.compareTo(vertices.get(i)) > 0) {
					vertex = vertices.get(i);
				}
			}
		}
		return vertex;
	}

	/**
	 * This method starts the algorithm
	 */
	public void start() {
		// sets all nodes to infinity sign except one with
		// the minimum id, which is set to 0
		if (started == false) {
			doneNodes.clear();
			currentStep.clear();
			currentNode = null;
		}
		started = true;

		for (ThreeTenNode v : g.getVertices()) {
			v.setText(INFINITY_SIGN);
		}

		ThreeTenNode minIdNode = getMinimumIdNode();

		setNodeText(minIdNode, 0);
		// Add/change anything you want.
	}

	/**
	 * This method returns a collection of unused edges
	 * 
	 * @return unusedEdges from the graph
	 */
	public Collection<ThreeTenEdge> getUnusedEdges() {
		// returns all the nodes in the graph that aren't being "used"
		// as part of Dijkstra's (these are the ones that are "greyed out"
		// at the end of the algorithm).
		ArrayList<ThreeTenEdge> edges = new ArrayList<ThreeTenEdge>(g.getEdges());
		Collection<ThreeTenEdge> unusedEdges = new HashSet<ThreeTenEdge>();
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).getColor().equals(COLOR_NONE_EDGE)) {
				unusedEdges.add(edges.get(i));
			}
		}
		return unusedEdges;
	}

	/**
	 * This method finishes the algorithm by setting unused edges to a grey color
	 */
	public void finish() {
		// sets all unused edges to grey color
		for (ThreeTenEdge e : getUnusedEdges()) {
			e.setColor(COLOR_DONE_EDGE_2);
		}

		// Add/change anything you want.
	}

	/**
	 * This method cleans up the last step by color coding nodes, edges, and
	 * changing distance
	 */
	public void cleanUpLastStep() {
		ArrayList<ThreeTenEdge> edges = new ArrayList<ThreeTenEdge>(g.getEdges());
		// mark the node picked on the _previous_ step to "done"
		// somehow... you choose how this works...
		// doneNodes.add(currentNode);

		// set that node's color to COLOR_DONE_NODE
		if (currentNode != null) {
			currentNode.setColor(COLOR_DONE_NODE);
		}

		// change all edges not being used from the orange highlight
		// (COLOR_ACTIVE_EDGE) back to COLOR_NONE_EDGE

		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).getColor().equals(COLOR_ACTIVE_EDGE)) {
				edges.get(i).setColor(COLOR_NONE_EDGE);
			}
		}
		// reset all non-finished nodes back to COLOR_NONE_NODE and
		// text representing their distance (not their update)
		for (ThreeTenNode node : currentStep.keySet()) {
			if (!doneNodes.contains(node)) {
				node.setColor(COLOR_NONE_NODE);
				setNodeText(node, currentStep.get(node));
			}
		}
	}

	/**
	 * This method finds the next smallest node
	 * 
	 * @return true if there is a next min value node
	 */
	public boolean setupNextMin() {
		ArrayList<ThreeTenNode> avalibleNodes = new ArrayList<ThreeTenNode>(g.getVertices());

		for (ThreeTenNode node : doneNodes) {
			avalibleNodes.remove(node);
		}

		// if there are no more nodes remaining in the algorithm
		// return false

		if (avalibleNodes.size() == 0) {
			return false;
		}

		// pick the next minimum node to look at (min-distance,
		// and min-ID if tied on distance)

		ThreeTenNode newNode = null;

		if (avalibleNodes.size() == 1) {
			newNode = avalibleNodes.get(0);
		}

		if (avalibleNodes.size() > 1) {
			newNode = avalibleNodes.get(0);

			for (int i = 0; i < avalibleNodes.size(); i++) {

				if (avalibleNodes.get(i).getText().equals(INFINITY_SIGN)) {

					if (newNode.getText().equals(INFINITY_SIGN)) {

						if (newNode.compareTo(avalibleNodes.get(i)) > 0) {
							newNode = avalibleNodes.get(i);
						}

					}

				} else {
					if (!newNode.getText().equals(INFINITY_SIGN)) {

						if (Integer.parseInt(newNode.getText()) > Integer.parseInt(avalibleNodes.get(i).getText())) {
							newNode = avalibleNodes.get(i);
						}

						if (Integer.parseInt(newNode.getText()) == Integer.parseInt(avalibleNodes.get(i).getText())) {

							if (newNode.compareTo(avalibleNodes.get(i)) > 0) {
								newNode = avalibleNodes.get(i);
							}
						}
					} else {
						newNode = avalibleNodes.get(i);
					}
				}
			}
		}

		// set color of the new node to COLOR_ACTIVE_NODE_1

		newNode.setColor(COLOR_ACTIVE_NODE_1);

		// set the edge between the node and it's parent to COLOR_DONE_EDGE_1
		// careful... if the node can't be reached from the starting node
		// there might not be a parent...

		// sets edges in update
		// if(g.findEdge(currentNode, newNode) != null) {
		// g.findEdge(currentNode, newNode).setColor(COLOR_DONE_EDGE_1);
		// }
		if (currentNode != null && !currentNode.getText().equals(INFINITY_SIGN)) {
			ArrayList<ThreeTenNode> successors = new ArrayList<ThreeTenNode>(g.getSuccessors(currentNode));

			for (int i = 0; i < successors.size(); i++) {

				if (g.findEdge(currentNode, successors.get(i)) != null) {

					ArrayList<ThreeTenEdge> successorEdges = new ArrayList<ThreeTenEdge>(
							g.getInEdges(successors.get(i)));
					ThreeTenNode node = null;

					for (int j = 0; j < successorEdges.size(); j++) {
						if (successorEdges.get(j).getColor().equals(COLOR_DONE_EDGE_1)) {
							node = g.getSource(successorEdges.get(j));
						}
					}
					if ((g.findEdge(currentNode, successors.get(i)).getWeight()
							+ Integer.parseInt(currentNode.getText())) == Integer
									.parseInt(successors.get(i).getText())) {

						if (node != null && currentNode.compareTo(node) < 0) {

						} else {
							if (node != null && currentNode.compareTo(node) > 0) {
								g.findEdge(node, successors.get(i)).setColor(COLOR_NONE_EDGE);
							}
							g.findEdge(currentNode, successors.get(i)).setColor(COLOR_DONE_EDGE_1);
						}

					}
				}
			}
		}
		// when you're done, return true
		currentNode = newNode;

		return true;
	}

	/**
	 * This method updates successor nodes of the current node
	 */
	public void doUpdates() {
		if (!currentNode.getText().equals(INFINITY_SIGN)) {
			// from the newly picked node
			ArrayList<ThreeTenNode> successors = new ArrayList<ThreeTenNode>(g.getSuccessors(currentNode));
			for (ThreeTenNode node : doneNodes) {
				successors.remove(node);
			}
			// (1) calculate a new cost for neighbours that can be updated
			//
			// (2) if the new cost is better, highlight the edges to that
			// node with COLOR_ACTIVE_EDGE and the neighbour with COLOR_ACTIVE_NODE_2
			//
			// (3) if the new cost is better, change the text of the neighbour
			// using setNodeText(node, currentDistance, newDistance)
			//
			for (int i = 0; i < successors.size(); i++) {

				ThreeTenEdge edge = g.findEdge(currentNode, successors.get(i));

				if (successors.get(i).getText() == INFINITY_SIGN) {

					edge.setColor(COLOR_ACTIVE_EDGE);
					successors.get(i).setColor(COLOR_ACTIVE_NODE_2);

					setNodeText(successors.get(i), Integer.MAX_VALUE,
							(edge.getWeight() + Integer.parseInt(currentNode.getText())));// infinity -> number

					currentStep.put(successors.get(i), (edge.getWeight() + Integer.parseInt(currentNode.getText())));

				} else {
					if (Integer.parseInt(successors.get(i).getText()) > (edge.getWeight()
							+ Integer.parseInt(currentNode.getText()))) {

						edge.setColor(COLOR_ACTIVE_EDGE);
						successors.get(i).setColor(COLOR_ACTIVE_NODE_2);

						setNodeText(successors.get(i), Integer.parseInt(successors.get(i).getText()),
								(edge.getWeight() + Integer.parseInt(currentNode.getText())));

						currentStep.put(successors.get(i),
								(edge.getWeight() + Integer.parseInt(currentNode.getText())));

						ArrayList<ThreeTenNode> predecessors = new ArrayList<ThreeTenNode>(
								g.getPredecessors(successors.get(i)));

						for (int j = 0; j < predecessors.size(); j++) {

							if (g.findEdge(predecessors.get(j), successors.get(i)).getColor()
									.equals(COLOR_DONE_EDGE_1)) {
								g.findEdge(predecessors.get(j), successors.get(i)).setColor(COLOR_NONE_EDGE);
							}
						}

						edge.setColor(COLOR_NONE_EDGE);
					}
				}
				// (4) perform any other tracking of these updates you need

				// Note: there is no infinity + 1... so neighbours of nodes set to
				// infinity won't update
			}

		}
		doneNodes.add(currentNode);

	}
}