package me.megmilk.myecsite.controllers;

import me.megmilk.myecsite.controllers.filters.FlashMessage;
import me.megmilk.myecsite.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 一般側ログアウト
 */
@WebServlet(name = "logout", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {
    public void init() {
    }

    @Override
    public void destroy() {
    }

    /**
     * GET でのログアウトは想定外
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        // Web サイトのトップページに遷移するだけ
        response.sendRedirect(
            request.getContextPath()
        );
    }

    /**
     * ログアウト後は Web サイトのトップページに移動
     */
    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        UserService.logout(request);

        request.getSession().setAttribute(
            FlashMessage.FLASH_INFO_TITLE,
            "ログアウトしました。ご利用ありがとうございました。"
        );

        response.sendRedirect(
            request.getContextPath()
        );
    }
}
