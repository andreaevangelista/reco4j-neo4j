/*
 * Neo4jGraph.java
 * 
 * Copyright (C) 2012 Alessandro Negro <alessandro.negro at reco4j.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.reco4j.graph.neo4j;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.reco4j.graph.IEdge;
import org.reco4j.graph.IEdgeType;
import org.reco4j.graph.IGraph;
import org.reco4j.graph.INode;
import org.reco4j.graph.neo4j.gremlin.GremlinGraphTraversal;
import org.reco4j.graph.neo4j.util.Neo4JPropertiesHandle;

/**
 *
 * @author ale
 */
public class Neo4jGraph implements IGraph
{
  private EmbeddedGraphDatabase graphDB;
  private boolean isTest = false;
  private GremlinGraphTraversal graphTraversal = new GremlinGraphTraversal();
  
  public Neo4jGraph()
  {
  }

  public void initDatabase()
  {
    String dbPath = Neo4JPropertiesHandle.getInstance().getDBPath();
    graphDB = (EmbeddedGraphDatabase) new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
    registerShutdownHook(graphDB);
    graphTraversal.setGraph(graphDB);
  }
  
  public void setDatabase(EmbeddedGraphDatabase graphDB)
  {
    this.graphDB = graphDB;
    graphTraversal.setGraph(graphDB);
  }

  @Override
  public List<INode> getNeighbours(List<IEdgeType> edgesType)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public List<INode> getNodesByType(String type)
  {
    return graphTraversal.getNodesByType(type);
  }
  
  @Override
  public List<INode> getNodesByInEdge(IEdgeType edgesType)
  {
    return graphTraversal.getNodesByInEdge(edgesType);
  }

  @Override
  public void setProperties(Properties properties)
  {
    Neo4JPropertiesHandle.getInstance().setProperties(properties);
  }

  @Override
  public void loadGraph()
  {
    
  }

  private static void registerShutdownHook(final GraphDatabaseService graphDb)
  {
    Runtime.getRuntime().addShutdownHook(new Thread()
    {
      @Override
      public void run()
      {
        System.out.println("Shuting down neo4j db instance ...");
        graphDb.shutdown();
        System.out.println("... done!");
      }
    });
  }

  public EmbeddedGraphDatabase getGraphDB()
  {
    return graphDB;
  }

  public boolean isIsTest()
  {
    return isTest;
  }

  public void setIsTest(boolean isTest)
  {
    this.isTest = isTest;
  }
  
  @Override
  public void addEdge(INode x, INode y, IEdgeType similarityEdgeType, String propertyName, String value)
  {
    graphTraversal.addEdge(((Neo4JNode)x).getNode(), ((Neo4JNode)y).getNode(), similarityEdgeType, propertyName, value);
  }

  @Override
  public void setEdgeProperty(IEdge edge, String propertyName, String value)
  {
    graphTraversal.setEdgeProperty(((Neo4JEdge)edge).getEdge(), propertyName, value);
  }

  @Override
  public List<IEdge> getEdgesByType(IEdgeType edgesType)
  {
    return graphTraversal.getEdgesByType(edgesType);
  }

  @Override
  public HashMap<String, INode> getNodesMapByType(String type, String identifier)
  {
    return graphTraversal.getNodesMapByType(type, identifier);
  }
  
  public void optimizeGraph()
  {
    graphTraversal.optimizeGraph();
  }
  
  public void tidyUpGraph()
  {
    //This function should tidy up the graph, deleting all added relation
    
  }
}
