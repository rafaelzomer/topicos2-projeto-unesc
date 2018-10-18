<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:base title="Bem vindo" >
    <tags:menu />
    <tags:container>
        <div class="row">
          <div class="col-md p-4">
            <a class="btn btn-primary float-right" href="/detalhe" role="button">Criar nova anotação</a>
          </div>
        </div>
        <div class="mt-4 list-group">
            <a href="/exercicio-adivinhar" class="list-group-item list-group-item-action">
                Adivinhação
            </a>
            <a href="/exercicio-aniversario" class="list-group-item list-group-item-action">
                Seu aniversário
            </a>
            <a href="/exercicio-imc" class="list-group-item list-group-item-action">
                IMC - Boa Forma
            </a>
            <a href="/exercicio-produtos/listar" class="list-group-item list-group-item-action">
                Produtos
            </a>
            <a href="/exercicio-atividades/listar" class="list-group-item list-group-item-action">
                Atividades
            </a>
        </div>
    </tags:container>
</tags:base>
