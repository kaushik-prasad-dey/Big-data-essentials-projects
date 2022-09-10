package com.myorg.dao;

import com.datastax.driver.core.Session;

public interface CassandraConnectionBuilder {



	public Session getSession();

}
