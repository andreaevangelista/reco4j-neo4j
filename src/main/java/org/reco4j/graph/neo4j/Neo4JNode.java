/*
 * Neo4JNode.java
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

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import java.util.List;
import org.reco4j.graph.BasicNode;
import org.reco4j.graph.IEdge;
import org.reco4j.graph.IEdgeType;
import org.reco4j.graph.INode;
import org.reco4j.graph.neo4j.gremlin.GremlinGraphTraversal;


/**
 *
 * @author ale
 */
public class Neo4JNode extends BasicNode
{
  private Vertex node;

  public Neo4JNode(Vertex node)
  {
    this.node = node;
  }

  public void setNode(Vertex node)
  {
    this.node = node;
  }

  public Vertex getNode()
  {
    return this.node;
  }

  @Override
  public void setProperty(String name, String value)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public String getProperty(String name)
  {
    Object value = node.getProperty(name);
    if (value instanceof String)
      return (String) value;
    else if (value instanceof Integer)
      return ((Integer) value).toString();
    else
    {
      if (value == null)
      {
        StringBuilder exception = new StringBuilder();
        exception.append("getProperty conversion data not supported! Not available property '").append(name).append("'on node!\n");
        exception.append("Available properties are:\n");
        for (String key : node.getPropertyKeys())
          exception.append(key).append("\n");
        throw new RuntimeException(exception.toString());
      }
      else
      {
        throw new RuntimeException("getProperty conversion data not supported! Not supported data type for property '" + name + "': " + value.getClass());
      }

    }
  }

  @Override
  public Boolean isConnected(INode b, IEdgeType edgeType)
  {
    return GremlinGraphTraversal.isConnected(this.node, ((Neo4JNode) b).getNode(), edgeType);
  }

  @Override
  public Boolean isConnected(INode node, List<IEdgeType> edgeTypes)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Boolean isConnectedIn(INode node, List<IEdgeType> edgeTypes)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Boolean isConnectedOut(INode node, List<IEdgeType> edgeTypes)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public IEdge getEdge(INode node, IEdgeType edgeType)
  {
    Edge edge = GremlinGraphTraversal.getEdge(this.node, ((Neo4JNode) node).getNode(), edgeType);
    if (edge != null)
    {
      Neo4JEdge neo4jEdge = new Neo4JEdge();
      neo4jEdge.setEdge(edge);
      return neo4jEdge;
    }
    return null;
  }

  @Override
  public List<IEdge> getInEdge(IEdgeType edgeType)
  {
    return GremlinGraphTraversal.getInEdge(node, edgeType);
  }

  @Override
  public List<IEdge> getOutEdge(IEdgeType edgeType)
  {
    return GremlinGraphTraversal.getOutEdge(node, edgeType);
  }

  @Override
  public List<INode> getCommonNodes(IEdgeType edgeType)
  {
    return GremlinGraphTraversal.getCommonNodes(node, edgeType);
    
  }

  @Override
  public int getInEdgeNumber(IEdgeType edgeType)
  {
    return GremlinGraphTraversal.getInEdgeNumber(node, edgeType);
  }
}
