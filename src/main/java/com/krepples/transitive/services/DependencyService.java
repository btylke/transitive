package com.krepples.transitive.services;

import com.krepples.transitive.db.model.Dependency;

public interface DependencyService {
  Dependency findByNameAndVersion(String name, String version);
  Dependency create(String name, String version, Dependency dependency);
  Dependency create(String name, String version, String parentName, String parentVersion);
  void setParent(Dependency parent, Dependency child);
}
