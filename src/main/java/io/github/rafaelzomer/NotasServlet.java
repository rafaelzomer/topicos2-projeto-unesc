package io.github.rafaelzomer;

import io.github.rafaelzomer.request.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(urlPatterns = {
        NotasServlet.URL_SALVAR,
        "/excluir",
        NotasServlet.URL_LISTAGEM,
        "/editar",
        NotasServlet.URL_NOTA
})
public class NotasServlet extends AbstractServlet {
  static final String URL_SALVAR = "/salvar";
  static final String URL_NOTA = "/detalhe";
  static final String URL_LISTAGEM = "/";
  private static final String JSP_LISTAGEM = "./index.jsp";
  private static final String JSP_NOTA = "./detalhe.jsp";

  private void render(String view, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.getRequestDispatcher(view).forward(request, response);
  }

  @Override
  public void post(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    if (constainsPath(request, URL_SALVAR)) {
      Long codigo = getParameter(request, "codigo", Long.class);
      List<Nota> notas = getSessionList(request, "notas", Nota.class);
      Nota p = null;
      if (codigo == null) {
        codigo = notas.size() + 1L;
      } else {
//        p = getAtividade(notas, codigo); fixme
      }
      String descricao = getParameter(request, "descricao", String.class);
      if (descricao == null) {
        request.setAttribute("error", "Descrição obrigatória");
        render(JSP_NOTA, request, response);
        return;
      }
      if (p == null) {
        p = new Nota(codigo, descricao, LocalDate.now(), SituacaoNota.ABERTA);
        notas.add(p);
      } else {
        p.setDescricao(descricao);
      }
      NotaRepository.salvar(p);
//      saveNotas(request, notas);
      response.sendRedirect(URL_LISTAGEM);
      return;
    }
    render(JSP_LISTAGEM, request, response);
  }

  @Override
  public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    NotaRepository.init();
    List<Nota> notas = NotaRepository.getAll();
    request.getSession().removeAttribute("message");
    if (constainsPath(request, URL_NOTA)) {
      Long codigo = getParameter(request, "codigo", Long.class);
      if (codigo != null) {
        Nota atividade = new Nota(); //fixme getAtividade(notas, codigo);
        if (atividade == null) {
          response.sendRedirect(URL_LISTAGEM);
          return;
        }
        request.setAttribute("id", atividade.getId());
        request.setAttribute("descricao", atividade.getDescricao());
      }
      Integer concluir = getParameter(request, "concluir", Integer.class);
      if (concluir != null) {
//        Nota atividade = getAtividade(notas, concluir);
//        if (atividade != null && atividade.getEstagio() < 100) {
//          atividade.setDataConclusao(new Date());
//          atividade.setEstagio(100);
//          saveNotas(request, notas);
//          request.getSession().setAttribute("message", MessageFormat.format("Atividade {0} concluída com sucesso!", concluir));
//        }
        render(JSP_LISTAGEM, request, response);
        return;
      }
//      Integer excluir = getParameter(request, "excluir", Integer.class);
//      if (excluir != null) {
//        Atividade atividade = getAtividade(notas, excluir);
//        if (atividade != null) {
//          notas = notas.stream().filter(e -> !e.getCodigo().equals(atividade.getCodigo())).collect(Collectors.toList());
//          saveNotas(request, notas);
//          request.setAttribute("message", "Atividade excluída com sucesso!");
//        }
//        render(JSP_LISTAGEM, request, response);
//        return;
//      }
      render(JSP_NOTA, request, response);
      return;
    }
    request.setAttribute("notas", notas);
    render(JSP_LISTAGEM, request, response);
  }
}
