package com.krepples.transitive;

import com.krepples.transitive.db.model.Dependency;
import com.krepples.transitive.services.DependencyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
public class TransitiveController {

  @Autowired
  private DependencyService dependencyService;

  @GetMapping("/{dependency}")
  @ResponseBody
  public Dependency getDependencies(
    @PathVariable(name="dependency") Dependency dependency
  ) {
    if (dependency != null) {
      // Avoid lazy loading issues
      dependency = dependencyService.findByNameAndVersion(dependency.getName(), dependency.getVersion());
      return dependency;
    } else {
      throw new EntityNotFoundException("Cannot locate dependency with that id.");
    }
  }

  @GetMapping("/")
  @ResponseBody
  public Dependency getDependencies(
    @RequestParam(name="name") String name,
    @RequestParam(name="version") String version
  ) {
    Dependency dependency = dependencyService.findByNameAndVersion(name, version);

    if (dependency != null) {
      return dependency;
    } else {
      throw new EntityNotFoundException("Cannot locate dependency with that id.");
    }
  }

  @PostMapping("/{parentDependency}")
  @ResponseBody
  public Dependency createDependency(
    @PathVariable(name="parentDependency") Dependency dependency,
    @RequestParam(name="name") String name,
    @RequestParam(name="version") String version
  ) {
    if (dependency == null) {
      throw new EntityNotFoundException("Cannot locate dependency with that id.");
    }

    dependency = dependencyService.findByNameAndVersion(dependency.getName(), dependency.getVersion());
    return dependencyService.create(name, version, dependency);
  }

  @PostMapping("/")
  @ResponseBody
  public Dependency createDependency(
    @RequestParam(name="parentName", required = false) String parentName,
    @RequestParam(name="parentVersion", required = false) String parentVersion,
    @RequestParam(name="name") String name,
    @RequestParam(name="version") String version
  ) {
    return dependencyService.create(name, version, parentName, parentVersion);
  }
}
