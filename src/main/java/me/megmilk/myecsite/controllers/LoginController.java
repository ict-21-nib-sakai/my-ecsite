package me.megmilk.myecsite.controllers;

import me.megmilk.myecsite.models.Item;
import me.megmilk.myecsite.models.User;
import me.megmilk.myecsite.services.IndexService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 一般側ログイン
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    public void init() {
    }

    @Override
    public void destroy() {
    }

    /**
     * ログインフォームを表示
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        // このページはWebブラウザにキャシュさせない
        response.addHeader("Cache-Control", "no-cache, no-store");

        final User user = (User) request.getAttribute("user");

        // ログイン済みの場合、Web サイトのトップページにリダイレクトする
        if (null != user) {
            response.sendRedirect("/");

            return;
        }

        // ログインしていない場合、ログインフォームを表示する
        request
            .getRequestDispatcher("/WEB-INF/views/login.jsp")
            .forward(request, response);
    }

    /**
     * ログイン試行
     *
     * ログインが成立したら、Webサイトのトップページにリダイレクトする
     */
    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
    }
}
