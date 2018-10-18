<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:base title="Nova anotação">
  <tags:menu />
  <tags:container>
    <div class="row mt-5 justify-content-md-center">
      <div class="col col-lg-4 mt-5">
        <a href="/" role="button">Voltar para listagem</a>
        <form action="/salvar" method="POST">
          <h4>
            Cadastro de
            <b>anotação</b>
          </h4>
          <hr />
          <div class="form-group">
            <label for="descricao">Descrição *</label>
            <textarea name="descricao" value="${descricao}" type="text" class="form-control" placeholder="Descrição da atividade" required="required"></textarea>
          </div>
          <c:if test="${error != null && !error.trim().isEmpty()}">
            <div class="alert alert-danger" role="alert">
              ${error}
            </div>
          </c:if>
          <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Salvar</button>
          </div>
        </form>
      </div>
    </div>
  </tags:container>
</tags:base>