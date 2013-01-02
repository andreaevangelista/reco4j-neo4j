/*
 * Neo4JPropertiesHandle.java
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
package org.reco4j.graph.neo4j.util;

import java.util.Properties;
import org.reco4j.util.RecommenderPropertiesHandle;

/**
 *
 * @author ale
 */
public class Neo4JPropertiesHandle extends RecommenderPropertiesHandle
{
  private static Neo4JPropertiesHandle theInstance = new Neo4JPropertiesHandle();
  
  public static Neo4JPropertiesHandle getInstance()
  {
    return theInstance;
  }
  
  protected Neo4JPropertiesHandle()
  {
    
  }
  @Override
  public void setProperties(Properties properties)
  {
    this.properties = properties;
    RecommenderPropertiesHandle.getInstance().setProperties(properties);
  } 
  
  public String getDBPath()
  {
    return properties.getProperty("dbPath", null);
  }
  
  public String getMovieLensBasePath()
  {
    return properties.getProperty("movieLensBasePath", null);
  }
}
