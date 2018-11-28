package com.krepples.transitive.db.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.krepples.transitive.db.model.Dependency;

@Repository
public interface DependencyRepository extends CrudRepository<Dependency, Long> {
  @EntityGraph(value = "childDependency", type = EntityGraph.EntityGraphType.LOAD)
  Dependency findByNameAndVersion(String name, String version);
}
