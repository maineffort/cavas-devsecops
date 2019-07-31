package com.anchore.jenkins.plugins.anchore;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class DatabaseTest {
    @Test
    public void testConnection() {
        VulnerabilityDAO dao = new VulnerabilityDAO();
        assertNotEquals(dao.getConnection(), null);
    }
}
