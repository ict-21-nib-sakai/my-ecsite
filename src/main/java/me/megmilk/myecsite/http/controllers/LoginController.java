package me.megmilk.myecsite.http.controllers;

import me.megmilk.myecsite.http.FlashBag;
import me.megmilk.myecsite.http.MySession;
import me.megmilk.myecsite.models.User;
import me.megmilk.myecsite.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

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

        // ログイン済みの場合、Web サイトのトップページにリダイレクトする
        if (loggedIn(request)) {
            response.sendRedirect(
                request.getContextPath()
            );

            return;
        }

        // ログインしていない場合、ログインフォームを表示する
        request
            .getRequestDispatcher("/WEB-INF/views/login.jsp")
            .forward(request, response);
    }

    /**
     * ログイン試行
     * <p>
     * ログインが成立したら、Webサイトのトップページにリダイレクトする
     */
    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        if (loggedIn(request)) {
            response.sendRedirect(
                request.getContextPath()
            );

            return;
        }

        // ログイン済みの場合は、Web サイトトップページを表示
        final FlashBag flashBag = (FlashBag) request.getAttribute("flashBag");
        User user = null;

        try {
            user = UserService.authenticate(request);
        } catch (SQLException e) {
            // TODO ログ, エラーページを表示
            e.printStackTrace();
        }

        // ログインが失敗した場合、再度ログインフォームを表示
        if (null == user) {
            request.getSession().removeAttribute("userId");

            FlashBag.setErrorTitle(
                request,
                "ログインできませんでした。メールアドレスとパスワードを再度入力してください。"
            );

            response.sendRedirect(
                request.getContextPath() + "/login"
            );

            return;
        }

        // ログイン成立後はセッションIDを再発行する
        MySession.regenerate(request);

        // セッションスコープにユーザーIDを保持する
        request.getSession().setAttribute(
            "userId", user.getId()
        );

        FlashBag.setInfoTitle(
            request,
            "いらっしゃいませ " + user.getName() + " さん。"
        );

        // Webサイトのトップページを表示
        response.sendRedirect(
            request.getContextPath()
        );
    }

    /**
     * @return すでにログイン済みであるか
     */
    private boolean loggedIn(HttpServletRequest request) {
        final FlashBag flashBag = (FlashBag) request.getAttribute("flashBag");
        User user = null;

        try {
            user = flashBag.getUser();
        } catch (SQLException e) {
            // TODO ログ, エラーページ表示
            e.printStackTrace();
        }

        return null != user;
    }
}
