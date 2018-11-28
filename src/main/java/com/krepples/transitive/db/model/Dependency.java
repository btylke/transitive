package com.krepples.transitive.db.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@NamedEntityGraph(
    name = "childDependency",
    attributeNodes = @NamedAttributeNode("childDependencies"))
public class Dependency {

  public Dependency() {}

  public Dependency(String name, String version, Dependency parent) {
    this.name = name;
    this.version = version;
    this.parent = parent;
  }

  @Id
  @SequenceGenerator(
      name = "dependency_generator",
      sequenceName = "dependency_sequence",
      allocationSize = 1)
  @GeneratedValue(generator = "dependency_generator")
  private Long id;

  private String name;

  private String version;

  @ManyToOne
  @JoinColumn(name = "parent")
  private Dependency parent;

  // TODO - Remove eager loading
  @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
  private List<Dependency> childDependencies = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public void setParent(Dependency parent) {
    this.parent = parent;
  }

  public List<Dependency> getChildDependencies() {
    return childDependencies;
  }

  @Override
  public String toString() {
    return "Dependency [name=" + this.name + ", version=" + this.version + "]";
  }
}
