CREATE TABLE dependency (
	id BIGSERIAL PRIMARY KEY,
	name text not null,
	version text
);

ALTER TABLE dependency ADD CONSTRAINT dependency_unique UNIQUE(name, version);

CREATE TABLE dependency_relations (
	parent_id BIGINT,
	child_id BIGINT
);

ALTER TABLE dependency_relations ADD CONSTRAINT dependency_relations_unique UNIQUE(parent_id, child_id);