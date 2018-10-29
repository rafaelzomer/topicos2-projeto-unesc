package io.github.rafaelzomer;

import io.github.rafaelzomer.request.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;

@WebServlet(urlPatterns = {
        NotasServlet.URL_SALVAR,
        NotasServlet.URL_EXCLUIR,
        NotasServlet.URL_LISTAGEM,
        NotasServlet.URL_CONCLUIR,
        "/editar",
        NotasServlet.URL_DETALHE
})
public class NotasServlet extends AbstractServlet {
  static final String URL_SALVAR = "/salvar";
  static final String URL_DETALHE = "/detalhe";
  static final String URL_CONCLUIR = "/concluir";
  static final String URL_EXCLUIR = "/excluir";
  static final String URL_LISTAGEM = "/";
  private static final String JSP_LISTAGEM = "./index.jsp";
  private static final String JSP_NOTA = "./detalhe.jsp";

  private void render(String view, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.getRequestDispatcher(view).forward(request, response);
  }

  private Nota getNota(List<Nota> notas, final Long codigo) {
    return notas.stream().filter(e -> e.getId().equals(codigo)).findFirst().orElse(null);
  }

  @Override
  public void post(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    if (constainsPath(request, URL_SALVAR)) {
      Long codigo = getParameter(request, "codigo", Long.class);
      Long id = null;
      List<Nota> notas = NotaRepository.getAll();
      Nota p = null;
      if (codigo == null) {
        id = notas.size() + 1L;
      } else {
        p = getNota(notas, codigo);
      }
      String descricao = getParameter(request, "descricao", String.class);
      if (descricao == null) {
        request.setAttribute("error", "Descrição obrigatória");
        render(JSP_NOTA, request, response);
        return;
      }
      if (p == null) {
        p = new Nota(id, descricao, LocalDate.now(), SituacaoNota.ABERTA);
        notas.add(p);
      } else {
        p.setDescricao(descricao);
      }
      if (codigo == null) {
        NotaRepository.salvar(p);
      } else {
        NotaRepository.atualizar(p);
      }
      response.sendRedirect(URL_LISTAGEM);
      return;
    }
    render(JSP_LISTAGEM, request, response);
  }

  @Override
  public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    List<Nota> notas = NotaRepository.getBySituacao(SituacaoNota.ABERTA);
    List<Nota> notasConcluidas = NotaRepository.getBySituacao(SituacaoNota.CONCLUIDA);
    request.getSession().removeAttribute("message");
    if (constainsPath(request, URL_DETALHE)) {
      Long codigo = getParameter(request, "codigo", Long.class);
      if (codigo != null) {
        Nota nota = getNota(notas, codigo);
        if (nota == null) {
          response.sendRedirect(URL_LISTAGEM);
          return;
        }
        request.setAttribute("id", nota.getId());
        request.setAttribute("descricao", nota.getDescricao());
      }
      render(JSP_NOTA, request, response);
      return;
    }
    if (constainsPath(request, URL_CONCLUIR)) {
      Long id = getParameter(request, "id", Long.class);
      if (id != null) {
        Nota nota = getNota(notas, id);
        if (nota != null) {
          nota.setSituacao(SituacaoNota.CONCLUIDA);
          NotaRepository.atualizar(nota);
          request.getSession().setAttribute("message", MessageFormat.format("Nota {0} concluída com sucesso!", id));
        }
        response.sendRedirect(URL_LISTAGEM);
        return;
      }
    }
    if (constainsPath(request, URL_EXCLUIR)) {
      Long id = getParameter(request, "id", Long.class);
      if (id != null) {
        NotaRepository.excluir(id);
        request.getSession().setAttribute("message", MessageFormat.format("Nota {0} excluída com sucesso!", id));
        response.sendRedirect(URL_LISTAGEM);
        return;
      }
    }
    request.setAttribute("notas", notas);
    request.setAttribute("notasConcluidas", notasConcluidas);
    render(JSP_LISTAGEM, request, response);
  }
}
