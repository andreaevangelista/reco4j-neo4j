/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reco4j.graph.neo4j;

import com.tinkerpop.blueprints.Vertex;
import java.util.List;
import java.util.Properties;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.reco4j.graph.EdgeTypeFactory;
import org.reco4j.graph.IEdge;
import org.reco4j.graph.IEdgeType;
import org.reco4j.graph.INode;
import org.reco4j.graph.neo4j.engine.RecommenderNeo4jEngine;
import org.reco4j.graph.neo4j.util.Neo4JPropertiesHandle;
import org.reco4j.session.RecommenderSessionManager;

/**
 *
 * @author ale
 */
public class Neo4JNodeTest extends TestCase
{
  private static Neo4jGraph learningDataSet;

  
  public Neo4JNodeTest(String testName)
  {
    super(testName);
  }
  
  @Override
  protected void setUp() throws Exception
  {
    super.setUp();
  }
  
  @Override
  protected void tearDown() throws Exception
  {
    super.tearDown();
  }
  
  public static Test suite()
  {
    setUpClass();
    return new TestSuite(Neo4JNodeTest.class);
  }

  public static void setUpClass()
  {
    System.out.println("Setup test... ");
    Properties properties = RecommenderNeo4jEngine.loadProperties();
    learningDataSet = new Neo4jGraph();
    learningDataSet.setProperties(properties);
    learningDataSet.initDatabase();
    RecommenderSessionManager.getInstance().setLearningDataSet(learningDataSet);
    System.out.println("... test setup ended!");
  }

  /**
   * Test of isConnected method, of class Neo4JNode.
   */
  public void testIsConnected()
  {
    System.out.println("isConnected");
    List<INode> users = learningDataSet.getNodesByType(Neo4JPropertiesHandle.getInstance().getUserType());
    List<INode> items = learningDataSet.getNodesByType(Neo4JPropertiesHandle.getInstance().getItemType());
    Long startTime = System.currentTimeMillis();
    int hit = 0;
    int processed = 0;
    for (INode user : users)
      for (INode item : items)
      {
        boolean res = user.isConnected(item, EdgeTypeFactory.getEdgeType(IEdgeType.EDGE_TYPE_RANK));
        if (res)
          hit++;
        processed++;
        
      }
    Long endTime = System.currentTimeMillis();
    System.out.println("Elapsed time: " + (endTime - startTime) + " Processed: " + processed + " Succeded: " + hit);
    assertTrue(true);
  }

//  /**
//   * Test of getEdge method, of class Neo4JNode.
//   */
//  public void testGetEdge()
//  {
//    System.out.println("getEdge");
//    INode node = null;
//    IEdgeType edgeType = null;
//    Neo4JNode instance = null;
//    IEdge expResult = null;
//    IEdge result = instance.getEdge(node, edgeType);
//    assertEquals(expResult, result);
//    // TODO review the generated test code and remove the default call to fail.
//    fail("The test case is a prototype.");
//  }
//
//  /**
//   * Test of getInEdge method, of class Neo4JNode.
//   */
//  public void testGetInEdge()
//  {
//    System.out.println("getInEdge");
//    IEdgeType edgeType = null;
//    Neo4JNode instance = null;
//    List expResult = null;
//    List result = instance.getInEdge(edgeType);
//    assertEquals(expResult, result);
//    // TODO review the generated test code and remove the default call to fail.
//    fail("The test case is a prototype.");
//  }
//
//  /**
//   * Test of getOutEdge method, of class Neo4JNode.
//   */
//  public void testGetOutEdge()
//  {
//    System.out.println("getOutEdge");
//    IEdgeType edgeType = null;
//    Neo4JNode instance = null;
//    List expResult = null;
//    List result = instance.getOutEdge(edgeType);
//    assertEquals(expResult, result);
//    // TODO review the generated test code and remove the default call to fail.
//    fail("The test case is a prototype.");
//  }
//
//  /**
//   * Test of getCommonNodes method, of class Neo4JNode.
//   */
//  public void testGetCommonNodes()
//  {
//    System.out.println("getCommonNodes");
//    IEdgeType edgeType = null;
//    Neo4JNode instance = null;
//    List expResult = null;
//    List result = instance.getCommonNodes(edgeType);
//    assertEquals(expResult, result);
//    // TODO review the generated test code and remove the default call to fail.
//    fail("The test case is a prototype.");
//  }
//
//  /**
//   * Test of getInEdgeNumber method, of class Neo4JNode.
//   */
//  public void testGetInEdgeNumber()
//  {
//    System.out.println("getInEdgeNumber");
//    IEdgeType edgeType = null;
//    Neo4JNode instance = null;
//    int expResult = 0;
//    int result = instance.getInEdgeNumber(edgeType);
//    assertEquals(expResult, result);
//    // TODO review the generated test code and remove the default call to fail.
//    fail("The test case is a prototype.");
//  }
}
