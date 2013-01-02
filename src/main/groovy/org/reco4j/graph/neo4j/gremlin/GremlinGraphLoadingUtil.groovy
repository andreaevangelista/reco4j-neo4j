/*
 * GremlinGraphLoadingUtil.java
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

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.gremlin.groovy.Gremlin;
import org.neo4j.graphdb.GraphDatabaseService;
import com.tinkerpop.blueprints.Index;
/**
 *
 * @author ale
 */
class GremlinGraphLoadingUtil 
{
  static 
  { 
    Gremlin.load()
  }
  
  public static Map<Vertex, Integer> eigenvectorRank(Graph g) 
  {  
    Map<Vertex,Integer> m = [:]; 
    int c = 0;
    g.V.out.groupCount(m).loop(2) {c++ < 1000}.iterate();
    return m;
  }
  
  public static loadMovieLensDataSet(GraphDatabaseService graphDB, String basePath)
  {
    Graph g = new com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph(graphDB);
    g.dropIndex("idx_movie");
    g.dropIndex("idx_user");
    g.dropIndex("idx_node_type");
    //g.setMaxBufferSize(1000);

    loadMovieData(g, basePath + "/u.item");
    loadUserData(g, basePath + "/u.user");
    loadRatingData(g, basePath + "/u1.base");
    loadRatingTestData(g, basePath + "/u1.test");    
  }
  
  public static loadMovieData(Graph g, String datasource)
  {
    System.out.println("loadMovieData ...");
    Index<Vertex> movieIndex = g.createIndex("idx_movie", Vertex.class);
    Index<Vertex> nodeTypeIndex = g.createIndex("idx_node_type", Vertex.class);
    new File(datasource).eachLine {def line ->
      def components = line.split('\\|');
      def movieVertex = g.addVertex(['type':'Movie', 'movieId':components[0].toInteger(), 'title':components[1]], 'releaseDate': components[2]);
      movieIndex.put("movieId", components[0].toInteger(), movieVertex);
      nodeTypeIndex.put("type", "Movie", movieVertex);
      
    }
    System.out.println("... fine!");
    g.stopTransaction(TransactionalGraph.Conclusion.SUCCESS);
  }
  public static loadUserData(Graph g, String datasource)
  {
    System.out.println("loadUserData ...");
    Index<Vertex> userIndex = g.createIndex("idx_user", Vertex.class);
    Index<Vertex> nodeTypeIndex = g.getIndex("idx_node_type", Vertex.class);
    new File(datasource).eachLine {def line ->
      def components = line.split('\\|');
      def userVertex = g.addVertex(['type':'User', 'userId':components[0].toInteger(), 'gender':components[2], 'age':components[1].toInteger()]);
      userIndex.put("userId", components[0].toInteger(), userVertex);
      nodeTypeIndex.put("type", "User", userVertex);
    }
    System.out.println("... fine!");
    g.stopTransaction(TransactionalGraph.Conclusion.SUCCESS);
  }
  public static loadRatingData(Graph g, String datasource)
  {
    System.out.println("loadRatingData ...");
      Index<Vertex> movieIndex = g.getIndex("idx_movie", Vertex.class);
    Index<Vertex> userIndex = g.getIndex("idx_user", Vertex.class);
    new File(datasource).eachLine {def line ->
      def components = line.split('\\t');
      Iterable<Vertex> movieResults = movieIndex.get("movieId", components[1].toInteger());
      Iterable<Vertex> userResults = userIndex.get("userId", components[0].toInteger());
      def ratedEdge = g.addEdge(userResults.iterator().next(), movieResults.iterator().next(), 'rated');
      ratedEdge.setProperty('RankValue', components[2].toInteger());
    }
    System.out.println("... fine!");
    g.stopTransaction(TransactionalGraph.Conclusion.SUCCESS);
  }
  
  public static loadRatingTestData(Graph g, String datasource)
  {
    System.out.println("loadRatingTestData ...");
    Index<Vertex> movieIndex = g.getIndex("idx_movie", Vertex.class);
    Index<Vertex> userIndex = g.getIndex("idx_user", Vertex.class);
    new File(datasource).eachLine {def line ->
      def components = line.split('\\t');
      Iterable<Vertex> movieResults = movieIndex.get("movieId", components[1].toInteger());
      Iterable<Vertex> userResults = userIndex.get("userId", components[0].toInteger());
      def ratedEdge = g.addEdge(userResults.iterator().next(), movieResults.iterator().next(), 'ratedTest');
      ratedEdge.setProperty('RankValue', components[2].toInteger());
    }
    System.out.println("... fine!");
    g.stopTransaction(TransactionalGraph.Conclusion.SUCCESS);
  }

}

