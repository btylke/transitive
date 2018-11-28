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

  @Override
  public Dependency findByNameAndVersion(String name, String version) {
    return repository.findByNameAndVersion(name, version);
  }

  @Transactional
  @Override
  public Dependency create(String name, String version, Dependency dependency) {
    Dependency child = new Dependency(name, version, dependency);
    return repository.save(child);
  }

  @Override
  public Dependency create(String name, String version, String parentName, String parentVersion) {
    Dependency dependency = findByNameAndVersion(parentName, parentVersion);

    if ((!StringUtils.isEmpty(parentName) || !StringUtils.isEmpty(parentVersion)) && dependency == null) {
      throw new EntityNotFoundException("Cannot locate dependency with name: '" + parentName + "' and version: '" + parentVersion + "'");
    } else {
      return create(name, version, dependency);
    }
  }

}

