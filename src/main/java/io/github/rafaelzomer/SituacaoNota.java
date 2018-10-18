package io.github.rafaelzomer;

public enum SituacaoNota {
  ABERTA("Aberta"),
  CONCLUIDA("Concluída");

  private String descricao;

  SituacaoNota(String descricao) {
    this.descricao = descricao;
  }

  @Override
  public String toString() {
    return descricao;
  }
}
