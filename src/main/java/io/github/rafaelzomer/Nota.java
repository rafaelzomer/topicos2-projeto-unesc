package io.github.rafaelzomer;


import java.time.LocalDate;

public class Nota {
  private Long id;
  private String descricao;
  private LocalDate criacao;
  private SituacaoNota situacao;

  public Nota() {
  }

  public Nota(Long id, String descricao, LocalDate criacao, SituacaoNota situacao) {
    this.id = id;
    this.descricao = descricao;
    this.criacao = criacao;
    this.situacao = situacao;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public SituacaoNota getSituacao() {
    return situacao;
  }

  public void setSituacao(SituacaoNota situacao) {
    this.situacao = situacao;
  }

  public LocalDate getCriacao() {
    return criacao;
  }

  public void setCriacao(LocalDate criacao) {
    this.criacao = criacao;
  }
}
