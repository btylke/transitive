package com.krepples.transitive.db.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.SequenceGenerator;

@Entity
@NamedEntityGraph(
    name = "childDependency",
    attributeNodes = @NamedAttributeNode("childDependencies"))
public class Dependency {

  public Dependency() {}

  public Dependency(String name, String version) {
    this.name = name;
    this.version = version;
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

  // TODO - Remove eager loading
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name="dependency_relations",
    joinColumns=@JoinColumn(name="parent_id", referencedColumnName="id"),
    inverseJoinColumns=@JoinColumn(name="child_id", referencedColumnName="id"))
  private Set<Dependency> childDependencies = new HashSet<>();

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

  public Set<Dependency> getChildDependencies() {
    return childDependencies;
  }

  @Override
  public String toString() {
    return "Dependency [name=" + this.name + ", version=" + this.version + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (o.getClass() != getClass()) {
      return false;
    }
    Dependency rhs = (Dependency)o;
    return new EqualsBuilder()
      .append(name, rhs.name)
      .append(version, rhs.version)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .append(name)
      .append(version)
      .toHashCode();
  }
}
