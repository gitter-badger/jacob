package org.wallerlab.jacob.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.data.neo4j.repository.config.EnableExperimentalNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "org.wallerlab.jacob")
@EnableExperimentalNeo4jRepositories(basePackages = "org.wallerlab.jacob.data")
@EnableTransactionManagement
public class DatabaseConfig {

	private final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

	@Bean
	public SessionFactory sessionFactory() {
		log.info("Initialising Session Factory");
		return new SessionFactory("org.wallerlab.jacob.domain");
	}

	@Bean
	public Neo4jTransactionManager transactionManager() {
		log.info("Initialising Neo4j Transaction Manager");
		return new Neo4jTransactionManager(sessionFactory());
	}

}
