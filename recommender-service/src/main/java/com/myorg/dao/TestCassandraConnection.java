package com.myorg.dao;

import java.util.ArrayList;

import com.myorg.model.Movie;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class TestCassandraConnection {

	public static void main(String[] args) {
		
		Session session=null;
		Cluster cluster = Cluster.builder().addContactPoint("35.194.178.225").build();
	     session = cluster.connect();
	     System.out.println("Dome");
	     
	     StringBuilder sb = 
			      new StringBuilder("SELECT * FROM  movies_keyspace.movies  where title='Titanic' order by count desc");
			 
			    String query = sb.toString();
			    ResultSet rs = session.execute(query);
			    
			    for (Row rows: rs){
			        System.out.println(rows.getString("also_viewed_title"));
			        
			    } 

	}

}
