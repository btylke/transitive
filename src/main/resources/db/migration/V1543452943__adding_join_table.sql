CREATE TABLE dependency_relations (
	parent_id BIGINT,
	child_id BIGINT
);

ALTER TABLE dependency_relations ADD CONSTRAINT dependency_relations_unique UNIQUE(parent_id, child_id);

INSERT INTO dependency_relations (parent_id, child_id)
  SELECT parent, id FROM dependency;

ALTER TABLE dependency DROP COLUMN parent;