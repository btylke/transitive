package com.krepples.transitive.db.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class DependencyUT {
    private static final long ID = 1L;
    private static final String NAME = "dependency name";
    private static final String VERSION = "version of dependency";

    @Test
    public void testNoArgConstructor() {
        new Dependency();
    }

    @Test
    public void testConstructorWithArgs() {
        Dependency d = new Dependency(NAME, VERSION);
        assertTrue(d.getName().equals(NAME));
        assertTrue(d.getVersion().equals(VERSION));
    }

    @Test
    public void testAccessorAndMutator_Id() {
        Dependency d = new Dependency();
        assertNull(d.getId());
        d.setId(ID);
        assertTrue(d.getId().equals(ID));
    }

    @Test
    public void testAccessorAndMutator_Name() {
        Dependency d = new Dependency();
        assertNull(d.getName());
        d.setName(NAME);
        assertTrue(d.getName().equals(NAME));
    }

    @Test
    public void testAccessorAndMutator_Version() {
        Dependency d = new Dependency();
        assertNull(d.getVersion());
        d.setVersion(VERSION);
        assertTrue(d.getVersion().equals(VERSION));
    }

    @Test
    public void testAccessorAndMutator_ChildDependencies() {
        Dependency d = new Dependency();
        Dependency child = new Dependency(NAME, VERSION);

        assertTrue(d.getChildDependencies().isEmpty());
        d.getChildDependencies().add(child);
        assertTrue(d.getChildDependencies().size() == 1);
        assertTrue(d.getChildDependencies().contains(child));
    }

    @Test
    public void testAccessorAndMutator_Multiple_ChildDependencies() {
        Dependency d = new Dependency();
        Dependency childOne = new Dependency(NAME, "");
        Dependency childTwo = new Dependency("", VERSION);
        Dependency childThree = new Dependency(NAME, VERSION);

        assertTrue(d.getChildDependencies().isEmpty());
        d.getChildDependencies().add(childOne);
        d.getChildDependencies().add(childTwo);
        d.getChildDependencies().add(childThree);

        // Will not be added to set
        d.getChildDependencies().add(childThree);

        assertTrue(d.getChildDependencies().size() == 3);
        assertTrue(d.getChildDependencies().contains(childOne));
        assertTrue(d.getChildDependencies().contains(childTwo));
        assertTrue(d.getChildDependencies().contains(childThree));
    }



    @Test
    public void testEquals() {
        Dependency d = new Dependency(NAME, VERSION);
        Dependency e = new Dependency(NAME, VERSION);
        Dependency f = new Dependency();

        assertTrue(d.equals(d));
        assertTrue(d.equals(e));

        assertFalse(d.equals(f));
        assertFalse(d.equals(""));
        assertFalse(d.equals(null));
    }

}
