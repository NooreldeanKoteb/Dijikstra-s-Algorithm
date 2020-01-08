import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * 
 * @author Katherine (Raven) Russell
 *
 */
interface ThreeTenAlg {
	/**
	 * GraphEdgeType method
	 * 
	 * @return graph edge type
	 */
	public EdgeType graphEdgeType();

	/**
	 * start method
	 */
	public void start();

	/**
	 * Step Method
	 * 
	 * @return true if successful
	 */
	public boolean step();

	/**
	 * finish method
	 */
	public void finish();

	/**
	 * reset method
	 * 
	 * @param g
	 *            graph
	 */
	public void reset(Graph<ThreeTenNode, ThreeTenEdge> g);
}