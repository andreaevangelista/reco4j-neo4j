/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reco4j.graph.neo4j;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.reco4j.graph.INode;
import org.reco4j.graph.neo4j.engine.RecommenderNeo4jEngine;
import org.reco4j.graph.neo4j.util.Neo4JPropertiesHandle;
import org.reco4j.session.RecommenderSessionManager;

/**
 *
 * @author ale
 */
public class Neo4jGraphTest extends TestCase
{

  private static Neo4jGraph learningDataSet;

  public Neo4jGraphTest(String testName)
  {
    super(testName);
  }

  public static Test suite()
  {
    setUpClass();
    return new TestSuite(Neo4jGraphTest.class);
  }

  public static void setUpClass()
  {
    System.out.println("Setup test... ");
    Properties properties = RecommenderNeo4jEngine.loadProperties();
    learningDataSet = new Neo4jGraph();
    learningDataSet.setProperties(properties);
    learningDataSet.initDatabase();
    RecommenderSessionManager.getInstance().setLearningDataSet(learningDataSet);
    //learningDataSet.optimizeGraph();
    System.out.println("... test setup ended!");
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

  /**
   * Test of getNodesByType method, of class Neo4jGraph.
   */ 
  public void testGetNodesByType()
  {


    System.out.println("getNodesByType");
    //Test Loading item
    long startTime = System.currentTimeMillis();
    List resultItem = learningDataSet.getNodesByType(Neo4JPropertiesHandle.getInstance().getItemType());
    long endTime = System.currentTimeMillis();
    System.out.println("Time Elapsed: " + (endTime - startTime));
    assertEquals(1682, resultItem.size());
    //Test Loading user
    startTime = System.currentTimeMillis();
    List resultUser = learningDataSet.getNodesByType(Neo4JPropertiesHandle.getInstance().getUserType());
    endTime = System.currentTimeMillis();
    System.out.println("Time Elapsed: " + (endTime - startTime));    
    assertEquals(943, resultUser.size());
  }

  /**
   * Test of getNodesMapByType method, of class Neo4jGraph.
   */
  public void testGetNodesMapByType()
  {
    System.out.println("getNodesMapByType");
    long startTime = System.currentTimeMillis();
    HashMap<String, INode> resultItem = learningDataSet.getNodesMapByType(
                                              Neo4JPropertiesHandle.getInstance().getItemType(),
                                              Neo4JPropertiesHandle.getInstance().getItemIdentifierName());
    long endTime = System.currentTimeMillis();
    System.out.println("Time Elapsed: " + (endTime - startTime));
    assertEquals(1682, resultItem.values().size());
    startTime = System.currentTimeMillis();
    HashMap<String, INode> resultUser = learningDataSet.getNodesMapByType(
                                              Neo4JPropertiesHandle.getInstance().getUserType(),
                                              Neo4JPropertiesHandle.getInstance().getUserIdentifierName());
    endTime = System.currentTimeMillis();
    System.out.println("Time Elapsed: " + (endTime - startTime));
    assertEquals(943, resultUser.values().size());
    
  }
}
