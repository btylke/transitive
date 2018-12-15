package com.krepples.transitive.services.impl;

import com.krepples.transitive.db.model.Dependency;
import com.krepples.transitive.db.repositories.DependencyRepository;
import com.krepples.transitive.services.DependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
public class DependencyServiceImpl implements DependencyService {
  @Autowired
  private DependencyRepository repository;

  @Transactional
  @Override
  public Dependency findByNameAndVersion(String name, String version) {
    return repository.findByNameAndVersion(name, version);
  }

  public String getDependencyTree(Dependency dependency) {
    return repository.getDependencyTree(dependency.getId());
  }


  @Transactional
  @Override
  public Dependency create(String name, String version, Dependency parent) {
    // This dependency may exist, look it up to see if it exists
    Dependency child = findByNameAndVersion(name, version);
    if (child == null) {
      child = new Dependency(name, version);
      child = repository.save(child);
    }

    setParent(parent, child);

    return child;
  }

  @Transactional
  @Override
  public Dependency create(String name, String version, String parentName, String parentVersion) {
    Dependency dependency = findByNameAndVersion(parentName, parentVersion);

    if ((!StringUtils.isEmpty(parentName) || !StringUtils.isEmpty(parentVersion)) && dependency == null) {
      throw new EntityNotFoundException("Cannot locate dependency with name: '" + parentName + "' and version: '" + parentVersion + "'");
    }

    return create(name, version, dependency);

  }

  @Transactional
  @Override
  public void setParent(Dependency parent, Dependency child) {
    // for top level parents, this will be null
    if (parent != null) {
      parent.getChildDependencies().add(child);
      parent = repository.save(parent);
    }
  }

}

