/*
 * GremlinGraphTraversal.java
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

package org.reco4j.graph.neo4j.gremlin;

import com.tinkerpop.gremlin.groovy.Gremlin;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.TransactionalGraph;
import org.neo4j.graphdb.GraphDatabaseService;
import org.reco4j.graph.neo4j.util.Neo4JPropertiesHandle;
import org.reco4j.graph.neo4j.Neo4JNode;
import com.tinkerpop.blueprints.Edge;
import org.reco4j.graph.neo4j.Neo4JEdge;
import com.tinkerpop.blueprints.Direction;
import org.reco4j.graph.IEdge
import org.reco4j.graph.IEdgeType
import org.reco4j.graph.INode;
import com.tinkerpop.blueprints.Index;
import com.tinkerpop.gremlin.java.GremlinPipeline;

/**
 *
 *Per compilare in maven: http://glaforge.appspot.com/article/building-your-groovy-2-0-projects-with-maven
 * @author ale
 */
class GremlinGraphTraversal 
{
  private Graph g;
  
  static
  { 
    Gremlin.load();
  }
  
  public void setGraph(GraphDatabaseService graphDB)
  {
    g = new com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph(graphDB);
  }
  
  public List<INode> getNodesByInEdge(IEdgeType edgesType) 
  { 
    List<INode> nodes = new ArrayList();
    
    String edges = edgesType.getEdgeName();
    Iterable<Vertex> movieResults = g.V.out(edges);
    int i = 0;
    for (Vertex item : movieResults.iterator())
    {
      INode node = new Neo4JNode();
      node.setNode(item);
      nodes.add(node);
    }
    return nodes;
  }
  
  public List<INode> getNodesByType(String type) 
  { 
    List<INode> nodes = new ArrayList();
    
    Index<Vertex> index = g.getIndex("idx_node_type", Vertex.class);
    Iterable<Vertex> movieResults;
    if (index == null)
      movieResults = g.V.filter{it.type==type};
    else 
    {
      movieResults = index.get("type", type);
    }
    for (Vertex item : movieResults.iterator())
    {
      INode node = new Neo4JNode();
      node.setNode(item);
      nodes.add(node);
    }
    return nodes;
  }
  
  public HashMap<String, INode> getNodesMapByType(String type, String identifier) 
  { 
    HashMap<String, INode> nodes = new HashMap<String, INode>();
    Index<Vertex> index = g.getIndex("idx_node_type", Vertex.class);
    
    Iterable<Vertex> movieResults;
    if (index == null)
      movieResults = g.V.filter{it.type==type};
    else 
    {
      movieResults = index.get("type", type);
    }
    for (Vertex item : movieResults.iterator())
    {
      INode node = new Neo4JNode();
      node.setNode(item);
      nodes.put((String)item.getProperty(identifier), node);
    }
    return nodes;
  }
  
  public static List<IEdge> getInEdge(Vertex node, IEdgeType edgesType) 
  { 
    List<IEdge> nodes = new ArrayList();
    
    String edges = edgesType.getEdgeName();
    Iterable<Edge> edgeList = node.inE(edges);
    for (Edge item : edgeList.iterator())
    {
      IEdge edge = new Neo4JEdge();
      edge.setEdge(item);
      nodes.add(edge);
      
      //edge.setDestination(node);
      //edge.setSource(item.get);
      //System.out.println("->>>>>>>>>>>>" + node.getProperty(Neo4JPropertiesHandle.getInstance().getItemIdentifierName()));
    }
    return nodes;

  }
  public static List<IEdge> getOutEdge(Vertex node, IEdgeType edgesType) 
  { 
    List<IEdge> edges = new ArrayList();
    
    String edgeName = edgesType.getEdgeName();
    Iterable<Edge> edgeList = node.outE(edgeName);
    //Iterable<Edge> edgeList = node.outE();
      
    //Iterable<Edge> edgeList = g.E.node.getEdges(Direction.IN,edgeName);
    for (Edge item : edgeList.iterator())
    {
      //System.out.println(item.getLabel());
      IEdge edge = new Neo4JEdge();
      edge.setEdge(item);
      edges.add(edge);
      //System.out.println(edge.getProperty("RankValue"));
      //edge.setDestination(node);
      //edge.setSource(item.get);
      //System.out.println("->>>>>>>>>>>>" + node.getProperty(Neo4JPropertiesHandle.getInstance().getItemIdentifierName()));
    }
    return edges;
  }
  public static Boolean isConnected(Vertex a, Vertex b, IEdgeType edgesType) 
  { 
    Iterable<Edge> edgeList = a.bothE(edgesType.getEdgeName()).filter{it.getVertex(Direction.IN).equals(b)};
    //Iterable<Edge> edgeList = a.bothE(edgesType.getEdgeName()).inV().filter{it.equals(b)};
    return edgeList.iterator().hasNext();    
  }
  
  public static Edge getEdge(Vertex a, Vertex b, IEdgeType edgesType) 
  { 
      
    //System.out.println("a.id: " + a.getProperty("userId") + " type: " + a.getProperty("type"));
    //System.out.println("b.id: " + b.getProperty("movieId") + " type: " + b.getProperty("type"));
    Iterable<Edge> edgeList = a.bothE(edgesType.getEdgeName()).filter{it.getVertex(Direction.IN).equals(b)};
    if (edgeList.iterator().hasNext())
    for (Edge item : edgeList.iterator())
    return item;
    return null;
  }
  
  public static List<INode> getCommonNodes(Vertex a, IEdgeType edgesType) 
  { 
    List<INode> nodes = new ArrayList();
    //System.out.println("a.id: " + a.getProperty("movieId") + " type: " + a.getProperty("type"));
    
    Iterable<Vertex> nodesList = a.in(edgesType.getEdgeName()).out(edgesType.getEdgeName()).except([a]).dedup();
    for (Vertex item : nodesList.iterator())
    {
      INode node = new Neo4JNode(item);
      nodes.add(node);
    }
    return nodes;
  }
  public void addEdge(Vertex x, Vertex y, IEdgeType similarityEdgeType, String propertyName, String value)
  {
    Edge newEdge = g.addEdge(null, x, y, similarityEdgeType.getEdgeName());
    newEdge.setProperty(propertyName, value);
    g.stopTransaction(TransactionalGraph.Conclusion.SUCCESS);
  }
  
  public void setEdgeProperty(Edge edge, String propertyName, String value)
  {
    edge.setProperty(propertyName, value);
    g.stopTransaction(TransactionalGraph.Conclusion.SUCCESS);
  }
  
  public static int getInEdgeNumber(Vertex node, IEdgeType edgeType)
  {
    return node.in(edgeType.getEdgeName()).toList().size();
  }
  
  public List<IEdge> getEdgesByType(IEdgeType edgeType)
  {
    List<IEdge> edges = new ArrayList();
    String type = edgeType.getEdgeName();
    Iterable<Edge> edgeList = g.V.outE(type);
    for (Edge item : edgeList.iterator())
    {
      IEdge edge = new Neo4JEdge();
      edge.setEdge(item);
      edges.add(edge);
      //System.out.println(edge.getProperty("RankValue"));
      //edge.setDestination(node);
      //edge.setSource(item.get);
      //System.out.println("->>>>>>>>>>>>" + node.getProperty(Neo4JPropertiesHandle.getInstance().getItemIdentifierName()));
    }
    return edges;
  }
  
  public void optimizeGraph()
  {
    //This must be generalized|
    g.dropIndex("idx_node_type");
    Index<Vertex> nodeTypeIndex = g.createIndex("idx_node_type", Vertex.class);
    Iterable<Vertex> movieResults = g.V.filter{it.type=="Movie"};
    for (Vertex item : movieResults.iterator())
    {
      System.out.println("->>>>");
      nodeTypeIndex.put("type", "Movie", item);
    }
    Iterable<Vertex> userResults = g.V.filter{it.type=="User"};
    for (Vertex item : userResults.iterator())
    {
      System.out.println("->>>>>");
      nodeTypeIndex.put("type", "User", item);
    }
    g.stopTransaction(TransactionalGraph.Conclusion.SUCCESS);
  }
}

