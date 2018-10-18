package io.github.rafaelzomer.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
  public static Connection getConnection() throws SQLException {
    try {
      Class.forName("org.hsqldb.jdbcDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
//    jdbc:hsqldb:hsql://localhost/data
    return DriverManager.getConnection("jdbc:hsqldb:mem:MySpecialTestDb", "sa", "");
  }
}
