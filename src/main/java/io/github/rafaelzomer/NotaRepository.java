package io.github.rafaelzomer;

import io.github.rafaelzomer.sql.SqlConnection;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class NotaRepository {

  public static void create() {
    final String sql = "" +
            "CREATE TABLE NOTAS (" +
            " ID INT," +
            " DESCRICAO VARCHAR(4000)," +
            " CRIACAO DATE," +
            " SITUACAO VARCHAR(10)" +
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
    if (nota == null) {
      return null;
    }
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

  public static boolean excluir(Long id) {
    if (id == null) {
      return false;
    }
    String sql = "DELETE FROM NOTAS WHERE ID = ?";
    try {
      Connection c = SqlConnection.getConnection();
      PreparedStatement ps = c.prepareStatement(sql);
      ps.setLong(1, id);
      ps.execute();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static Nota atualizar(Nota nota) {
    if (nota == null) {
      return null;
    }
    String sql = "UPDATE NOTAS SET DESCRICAO = ?, SITUACAO = ? WHERE ID = ?";
    try {
      Connection c = SqlConnection.getConnection();
      PreparedStatement ps = c.prepareStatement(sql);
      ps.setString(1, nota.getDescricao());
      ps.setString(2, nota.getSituacao().name());
      ps.setLong(3, nota.getId());
      ps.execute();
      return nota;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static List<Nota> getAll() {
    return getBySituacao(null);
  }

  public static List<Nota> getBySituacao(SituacaoNota situacaoNota) {
    List<Nota> notas = new ArrayList<>();
    String sql = "SELECT ID, DESCRICAO, CRIACAO, SITUACAO FROM NOTAS";
    if (situacaoNota != null) {
      sql += " WHERE SITUACAO = ?";
    }
    try {
      Connection c = SqlConnection.getConnection();
      PreparedStatement ps = c.prepareStatement(sql);
      if (situacaoNota != null) {
        ps.setString(1, situacaoNota.name());
      }
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        notas.add(new Nota(
                rs.getLong("ID"),
                rs.getString("DESCRICAO"),
                rs.getDate("CRIACAO").toLocalDate(),
                SituacaoNota.valueOf(rs.getString("SITUACAO"))
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return notas;
  }
}
