package io.github.rafaelzomer.request;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractServlet extends HttpServlet {

  private <T> T getValue(Object value, Class<T> tClass) {
    if (value == null) {
      if (tClass == List.class) {
        return tClass.cast(new ArrayList<>());
      }
      return null;
    }
    if (tClass == Integer.class) {
      return (T) Integer.valueOf(value.toString());
    }
    if (tClass == Double.class) {
      return (T) Double.valueOf(value.toString());
    }
    if (tClass == List.class) {
      return tClass.cast(value);
    }
    return tClass.cast(value);
  }

  protected boolean constainsPath(HttpServletRequest request, String path) {
    return request != null && request.getServletPath().contains(path);
  }

  protected <T> T getParameter(HttpServletRequest request, String name, Class<T> tClass) {
    try {
      return getValue(request.getParameter(name), tClass);
    } catch (Exception e) {
      return null;
    }
  }

  protected <T> List<T> getSessionList(HttpServletRequest request, String name, Class<T> tClass) {
    try {
      return getValue(request.getSession().getAttribute(name), List.class);
    } catch (Exception e) {
      return null;
    }
  }

  protected <T> T getSession(HttpServletRequest request, String name, Class<T> tClass) {
    try {
      return getValue(request.getSession().getAttribute(name), tClass);
    } catch (Exception e) {
      return null;
    }
  }

  protected <T> T getCookie(HttpServletRequest request, String name, Class<T> tClass) {
    Cookie cookie = getCookie(request, name);
    if (Objects.nonNull(cookie)) {
      return getValue(cookie.getValue(), tClass);
    }
    return null;
  }

  protected Cookie getCookie(HttpServletRequest request, String name) {
    for (Cookie cookie : request.getCookies()) {
      if (name.equals(cookie.getName())) {
        return cookie;
      }
    }
    return null;
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    get(resp);
    get(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    post(req);
    post(req, resp);
  }


  public void post(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

  }

  public void post(HttpServletRequest request) throws IOException, ServletException {

  }

  public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

  }

  public void get(HttpServletResponse response) throws IOException, ServletException {

  }

  ;
}
