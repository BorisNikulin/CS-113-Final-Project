
package edu.miracosta.cs113.final_project.group_b.model.test;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.function.BiFunction;
import org.junit.Before;
import org.junit.Test;
import edu.miracosta.cs113.final_project.group_b.model.AStar;
import edu.miracosta.cs113.final_project.group_b.model.Graph;
import edu.miracosta.cs113.final_project.group_b.model.GraphUtility;

/**
 * GraphTest.java: A brief suite of tests to ensure path-finding algorithm
 * and graph-reading functions are working as expected.
 * 
 * @author Marina Mizar
 */
public class GraphTest
{
	Graph<Point> testGraph;
	GraphUtility<Point> graphReader;
	BiFunction<Integer, Integer, Point> pointSetter;
	AStar<Point> algorithm;

	/**
	 * Initializes helper object with text file containing graph info.
	 * Creates an anonymous method to set points in generic GraphUtility
	 * class.
	 * 
	 * @throws Exception
	 * 		   If file does not exist
	 */
	@Before
	public void setUp() throws Exception
	{
		class PointSetter implements BiFunction<Integer, Integer, Point> {
			@Override
			public Point apply(Integer x, Integer y) {
				return new Point(x, y);
			}
		}		
		pointSetter = new PointSetter();
		graphReader = new GraphUtility<Point>("src/edu/miracosta/cs113/final_project/group_b/model/test/pokemap.txt", pointSetter);
	}
	
	/**
	 * Tests Graph, Edge, and GraphUtility classes by creating a graph from file,
	 * checking for specified edge & weight, and testing remove method.
	 */
	@Test
	public void readGraphTest() {
		Point a, b;
		a = new Point(300,1050);
		b = new Point(470,1050);
		testGraph = graphReader.getGraph();
		assertTrue(testGraph.isEdge(a, b));
		assertEquals((Double)120.0, (Double)testGraph.getEdge(a, b).getWeight());
		assertTrue(testGraph.removeEdge(a, b));
	}
	
	/**
	 * Tries out AStar method on random graph to ensure it properly creates a path.
	 * Prints path to console.
	 * @throws Exception
	 *         If file with test graph data is not found
	 */
	@Test
	public void aStarTest() throws Exception {
		Point start = new Point(100,165);
		Point end = new Point(470,1050);
		graphReader = new GraphUtility<Point>("C:\\Users\\W7104673\\Desktop\\pokemap.txt", pointSetter);
		testGraph = graphReader.getGraph();
		algorithm = new AStar<Point>(testGraph, start, end, (a, b) -> Math.abs(a.getX() - b.getX()) +
				                                                      Math.abs(a.getY() - b.getY()));
		algorithm.getPathTo(end);
		algorithm.printPath();
	}
}
