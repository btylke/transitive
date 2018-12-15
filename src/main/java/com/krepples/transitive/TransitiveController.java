package com.krepples.transitive;

import com.krepples.transitive.db.model.Dependency;
import com.krepples.transitive.services.DependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/tree/{dependency}")
  @ResponseBody
  public String getDependencyTree(
          @PathVariable(name="dependency") Dependency dependency
  ) {
    if (dependency != null) {
      // Avoid lazy loading issues
      dependency = dependencyService.findByNameAndVersion(dependency.getName(), dependency.getVersion());
      return dependencyService.getDependencyTree(dependency).replaceAll("\n", "<br/>");
    } else {
      throw new EntityNotFoundException("Cannot locate dependency with that id.");
    }
  }

  @GetMapping("/tree/")
  @ResponseBody
  public String getDependencyTree(
          @RequestParam(name="name") String name,
          @RequestParam(name="version") String version
  ) {
    Dependency dependency = dependencyService.findByNameAndVersion(name, version);

    if (dependency != null) {
      return dependencyService.getDependencyTree(dependency).replaceAll("\n", "<br/>");
    } else {
      throw new EntityNotFoundException("Cannot locate dependency with that id.");
    }
  }

  @GetMapping("/tree/export/{dependency}")
  @ResponseBody
  public ResponseEntity exportDependencyTree(
          @PathVariable(name="dependency") Dependency dependency
  ) {
    if (dependency != null) {
      // Avoid lazy loading issues
      dependency = dependencyService.findByNameAndVersion(dependency.getName(), dependency.getVersion());
      String tree = dependencyService.getDependencyTree(dependency);

      return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=dependency_tree.txt")
              .contentType(MediaType.TEXT_PLAIN)
              .contentLength(tree.length())
              .body(tree);
    } else {
      throw new EntityNotFoundException("Cannot locate dependency with that id.");
    }
  }

  @GetMapping("/tree/export/")
  @ResponseBody
  public ResponseEntity exportDependencyTree(
          @RequestParam(name="name") String name,
          @RequestParam(name="version") String version
  ) {
    Dependency dependency = dependencyService.findByNameAndVersion(name, version);

    if (dependency != null) {
      String tree = dependencyService.getDependencyTree(dependency);

      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=dependency_tree.txt")
          .contentType(MediaType.TEXT_PLAIN)
          .contentLength(tree.length())
          .body(tree);
    } else {
      throw new EntityNotFoundException("Cannot locate dependency with that id.");
    }
  }
}
