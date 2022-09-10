package com.myorg.dao;

import org.jongo.Jongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class CassandraConnectionBuilderImpl implements InitializingBean,
CassandraConnectionBuilder
		 {
	private final Logger LOGGER = LoggerFactory
			.getLogger(CassandraConnectionBuilderImpl.class);


	@Value("${myorg-paas-api.db.host}")
	private String dbHost;

	@Value("${myorg-paas-api.db.port}")
	private int dbPort;

	@Value("${myorg-paas-api.db.databaseName}")
	private String databaseName;


	   /** Cassandra Cluster. */
	   private Cluster cluster;
	   /** Cassandra Session. */
	   private Session session;

	@Override
	public void afterPropertiesSet() throws Exception {
         System.out.println("Setting connection...");
		 this.cluster = Cluster.builder().addContactPoint(dbHost).withPort(dbPort).build();
	      final Metadata metadata = cluster.getMetadata();
	      LOGGER.info("Connected to cluster: %s\n", metadata.getClusterName());
	      for (final Host host : metadata.getAllHosts())
	      {
	         LOGGER.info("Datacenter: %s; Host: %s; Rack: %s\n",
	            host.getDatacenter(), host.getAddress(), host.getRack());
	      }
	      session = cluster.connect();


	}

	  /**
	    * Provide my Session.
	    *
	    * @return My session.
	    */
	   public Session getSession()
	   {
	      return this.session;
	   }
	   /** Close cluster. */
	   public void close()
	   {
	      cluster.close();
	   }


}
