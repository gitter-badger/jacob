package org.wallerlab.jacob.data;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.wallerlab.jacob.domain.Benchmark;

@Repository
public interface BenchmarkRepository extends Neo4jRepository<Benchmark> {}
