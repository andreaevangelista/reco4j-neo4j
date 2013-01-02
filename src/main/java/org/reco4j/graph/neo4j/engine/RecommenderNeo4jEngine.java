/*
 * RecommenderNeo4jEngine.java
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
import org.reco4j.graph.engine.RecommenderEngine;
import org.reco4j.graph.neo4j.Neo4jGraph;
import org.reco4j.graph.recommenders.IRecommender;
import org.reco4j.session.RecommenderSessionManager;

/**
 *
 * @author ale
 */
public class RecommenderNeo4jEngine
{
  private static Logger logger = Logger.getLogger(RecommenderNeo4jEngine.class);
  
  private Neo4jGraph learningDataSet;
  private Neo4jGraph testingDataSet;
  
  
  public void setUP(Properties properties)
  {
    learningDataSet = new Neo4jGraph();
    learningDataSet.setProperties(properties);
    learningDataSet.initDatabase();
    
    RecommenderSessionManager.getInstance().setLearningDataSet(learningDataSet);
    
    testingDataSet = new Neo4jGraph();
    testingDataSet.setProperties(properties);
    testingDataSet.setDatabase(learningDataSet.getGraphDB());
    testingDataSet.setIsTest(true);
    
    RecommenderSessionManager.getInstance().setTestingDataSet(testingDataSet);
  }
  
  public static void main(String[] args)
  {
    Properties properties = loadProperties();
    RecommenderNeo4jEngine reco = new RecommenderNeo4jEngine();
    reco.setUP(properties);
    IRecommender recommender = RecommenderEngine.buildRecommender(reco.getLearningDataSet(), properties);
    //IRecommender recommender = RecommenderEngine.loadRecommender(reco.getLearningDataSet(), properties);
    //RecommenderPropertiesHandle.getInstance().setProperties(properties);
    RecommenderEngine.evaluateRecommender(reco.getTestingDataSet(), recommender);
  }

  public static Properties loadProperties()
  {
    // TODO code application logic here
    Properties properties = new Properties();
    try
    {
      properties.load(RecommenderNeo4jEngine.class.getResourceAsStream("init.properties"));
    }
    catch (IOException ex)
    {
      logger.error("Error while loading properties", ex);
    }
    return properties;
  }

  public Neo4jGraph getLearningDataSet()
  {
    return learningDataSet;
  }

  public void setLearningDataSet(Neo4jGraph learningDataSet)
  {
    this.learningDataSet = learningDataSet;
  }

  public Neo4jGraph getTestingDataSet()
  {
    return testingDataSet;
  }

  public void setTestingDataSet(Neo4jGraph testingDataSet)
  {
    this.testingDataSet = testingDataSet;
  }
  
}
