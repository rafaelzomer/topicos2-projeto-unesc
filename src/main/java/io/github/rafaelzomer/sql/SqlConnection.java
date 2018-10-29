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
    return DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/topicos2", "sa", "");
  }
}
