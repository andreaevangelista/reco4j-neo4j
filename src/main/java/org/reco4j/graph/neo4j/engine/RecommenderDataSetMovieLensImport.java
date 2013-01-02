/*
 * RecommenderDataSetMovieLensImport.java
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
package org.reco4j.graph.neo4j.engine;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.reco4j.graph.neo4j.Neo4jGraph;
import org.reco4j.graph.neo4j.gremlin.GremlinGraphLoadingUtil;
import org.reco4j.graph.neo4j.util.Neo4JPropertiesHandle;
/**
 *
 * @author ale
 */
public class RecommenderDataSetMovieLensImport
{
  private static Logger logger = Logger.getLogger(RecommenderDataSetMovieLensImport.class);
  /**
   * @param args the command line arguments
   */
  
  public static void main(String[] args)
  {
    Properties properties = loadProperties();
    
    Neo4jGraph graphDB = new Neo4jGraph();
    graphDB.setProperties(properties);
    graphDB.initDatabase();
    graphDB.loadGraph();
    
    GremlinGraphLoadingUtil.loadMovieLensDataSet(graphDB.getGraphDB(), Neo4JPropertiesHandle.getInstance().getMovieLensBasePath());
  }

  private static Properties loadProperties()
  {
    // TODO code application logic here
    Properties properties = new Properties();
    try
    {
      properties.load(RecommenderDataSetMovieLensImport.class.getResourceAsStream("init.properties"));
    }
    catch (IOException ex)
    {
      logger.error("Error while loading properties", ex);
    }
    return properties;
  }
}
