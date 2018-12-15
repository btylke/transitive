package com.krepples.transitive.db.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.krepples.transitive.db.model.Dependency;

@Repository
public interface DependencyRepository extends CrudRepository<Dependency, Long> {
  @EntityGraph(value = "childDependency", type = EntityGraph.EntityGraphType.LOAD)
  Dependency findByNameAndVersion(String name, String version);

  @Query(
      value =
          "SELECT string_agg(tree, E'\n') AS tree \n"
              + "FROM\n"
              + " (\n"
              + "  WITH RECURSIVE hierarchy AS (\n"
              + "      SELECT \n"
              + "        null::::bigint as parent_id,\n"
              + "        D.id AS id,\n"
              + "        1 AS lvl,\n"
              + "        name || ' ' || version AS tree,\n"
              + "        name || ' ' || version || '->' AS path\n"
              + "      FROM dependencies D\n"
              + "      WHERE id = :parent\n"
              + "    UNION ALL\n"
              + "      SELECT \n"
              + "        DR.parent_id,\n"
              + "        E1.id as id,\n"
              + "        E2.lvl + 1 AS lvl,\n"
              + "        LPAD('', lvl * 2,' ') || '+' || LPAD('', lvl,'-') || '\\ ' || E1.name || ' ' || E1.version AS tree,\n"
              + "        E2.path || name || ' ' || version || '->' AS path\n"
              + "      FROM dependencies AS E1\n"
              + "      JOIN dependency_relations DR ON E1.id = DR.child_id\n"
              + "      JOIN hierarchy AS E2\n"
              + "        ON DR.parent_id = E2.id\n"
              + "  ) \n"
              + "  SELECT tree\n"
              + "  FROM hierarchy\n"
              + "  ORDER BY path\n"
              + ") AS branches;",
      nativeQuery = true)
  String getDependencyTree(@Param("parent") Long parent);
}
