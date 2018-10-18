package io.github.rafaelzomer;

import io.github.rafaelzomer.sql.SqlConnection;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class NotaRepository {

  public static void init() {
    final String sql = "" +
            "CREATE TABLE NOTAS (" +
            " ID INT," +
            " DESCRICAO VARCHAR(4000)," +
            " CRIACAO DATE," +
            " SITUACAO VARCHAR(10)," +
            ");";
    try {
      Connection c = SqlConnection.getConnection();
      PreparedStatement ps = c.prepareStatement(sql);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Nota salvar(Nota nota) {
    String sql = "INSERT INTO NOTAS(ID, DESCRICAO, CRIACAO, SITUACAO) VALUES (?, ?, ?, ?)";
    try {
      Connection c = SqlConnection.getConnection();
      PreparedStatement ps = c.prepareStatement(sql);
      ps.setLong(1, nota.getId());
      ps.setString(2, nota.getDescricao());
      ps.setDate(3, Date.valueOf(nota.getCriacao()));
      ps.setString(4, nota.getSituacao().name());
      ps.execute();
      return nota;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static List<Nota> getAll() {
    List<Nota> notas = new ArrayList<>();
    String sql = "SELECT ID, DESCRICAO, CRIACAO, SITUACAO FROM NOTAS";
    try {
      Connection c = SqlConnection.getConnection();
      PreparedStatement ps = c.prepareStatement(sql);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          notas.add(new Nota(
                  rs.getLong("ID"),
                  rs.getString("DESCRICAO"),
                  rs.getDate("CRIACAO").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                  SituacaoNota.valueOf(rs.getString("SITUACAO"))
          ));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return notas;
  }
}
