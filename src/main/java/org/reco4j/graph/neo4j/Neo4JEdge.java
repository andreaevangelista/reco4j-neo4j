/*
 * Neo4JEdge.java
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

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import java.util.Iterator;
import org.reco4j.graph.BasicEdge;
import org.reco4j.graph.INode;

/**
 *
 * @author ale
 */
public class Neo4JEdge extends BasicEdge
{
  private Edge edge;
  private INode source;
  private INode destination;

  @Override
  public void setProperty(String name, String value)
  {
    throw new UnsupportedOperationException("Not supported yet.");
    //edge.
  }

  @Override
  public String getProperty(String name)
  {
    Object value = edge.getProperty(name);
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
        for (Iterator<String> it = edge.getPropertyKeys().iterator(); it.hasNext();)
        {
          String key = it.next();
          exception.append(key).append("\n");
        }
        throw new RuntimeException(exception.toString());
      }
      else
      {
        throw new RuntimeException("getProperty conversion data not supported! Not supported data type for property '" + name + "': " + value.getClass());
      }

    }
  }

  @Override
  public INode getSource()
  {
    return source;
  }

  @Override
  public INode getDestination()
  {
    return destination;
  }

  public Edge getEdge()
  {
    return edge;
  }

  public void setEdge(Edge edge)
  {
    this.edge = edge;
    this.destination = new Neo4JNode(edge.getVertex(Direction.OUT));
    this.source = new Neo4JNode(edge.getVertex(Direction.IN));
  }

  public void setSource(INode source)
  {
    this.source = source;
  }

  public void setDestination(INode destination)
  {
    this.destination = destination;
  }

  @Override
  public String getPermissiveProperty(String name)
  {
    Object value = edge.getProperty(name);
    if (value instanceof String)
      return (String) value;
    else if (value instanceof Integer)
      return ((Integer) value).toString();
    else
    {
      if (value == null)
        return null;
      else
        throw new RuntimeException("getProperty conversion data not supported! Not supported data type for property '" + name + "': " + value.getClass());
    }
  }
}
