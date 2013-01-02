/*
 * Reco4jRecommender.java
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
//package org.reco4j.graph.neo4j.plugin;
//
//import org.neo4j.graphdb.Node;
//import org.neo4j.server.plugins.Description;
//import org.neo4j.server.plugins.Name;
//import org.neo4j.server.plugins.Parameter;
//import org.neo4j.server.plugins.PluginTarget;
//import org.neo4j.server.plugins.ServerPlugin;
//import org.neo4j.server.plugins.Source;
//import org.neo4j.tooling.GlobalGraphOperations;
//
///**
// *
// * @author Alessandro Negro <alessandro.negro at reco4j.org>
// */
//@Description( "An extension to the Neo4j Server for recommendation getting from reco4j")
//public class Reco4jRecommender extends ServerPlugin
//{
//  @Name( "get_all_nodes")
//  @Description( "Get recommendation for a user")
//  @PluginTarget( Node.class)
//  public Iterable<Node> getRecommendationForUser(@Source Node source,
//            @Description( "The type of recommender. Available types are: ..." )
//            @Parameter( name = "recommender" ) String recommender,
//            
//            @Description( "The relationship types to follow when searching for the shortest path(s). " +
//             "Order is insignificant, if omitted all types are followed." )
//            @Parameter( name = "types", optional = true ) String[] types,
//            @Description( "The maximum path length to search for, default value (if omitted) is 4." )
//            @Parameter( name = "depth", optional = true ) Integer depth)
//  {
//    return GlobalGraphOperations.at(graphDb).getAllNodes();
//  }
//}
