<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tags:base title="Bem vindo" >
    <tags:menu />
    <tags:container>
        <c:if test="${message != null && !message.trim().isEmpty()}">
          <div class="row">
            <div class="col-md p-4">
              <div class="alert alert-info" role="alert">
                ${message}
              </div>
            </div>
          </div>
        </c:if>
        <div class="row">
          <div class="col-md p-4">
            <a class="btn btn-primary float-right" href="/detalhe" role="button">Criar nova anotação</a>
          </div>
        </div>
        <h1>Em aberto</h1>
        <hr/>
        <div class="row">
            <c:forEach var="p" items="${notas}">
                <div class="col-4 mb-4">
                    <div class="card">
                      <div class="card-body">
                        <h5 class="card-title">
                            Código anotação: ${p.id}
                            <small>
                                <fmt:parseDate value="${p.criacao}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>
                                <fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy"/>
                            </small>
                        </h5>
                        <p class="card-text">${p.descricao}</p>
                        <a href="/detalhe?codigo=${p.id}" class="btn btn-default">Editar</a>
                        <a href="/concluir?id=${p.id}" class="btn btn-primary">Concluir</a>
                      </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <h1>Concluídas</h1>
        <hr/>
        <div class="row">
            <c:forEach var="p" items="${notasConcluidas}">
                <div class="col-4 mb-4">
                    <div class="card">
                      <div class="card-body">
                        <h5 class="card-title">
                            Código anotação: ${p.id}
                            <small>
                                <fmt:parseDate value="${p.criacao}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>
                                <fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy"/>
                            </small>
                        </h5>
                        <p class="card-text">${p.descricao}</p>
                        <a href="/excluir?id=${p.id}" class="btn btn-danger">Excluir</a>
                      </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </tags:container>
</tags:base>
