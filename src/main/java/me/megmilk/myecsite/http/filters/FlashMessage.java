package me.megmilk.myecsite.http.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

/**
 * セッションスコープ内のフラッシュメッセージを、リクエストスコープに移動する
 */
@WebFilter(filterName = "FlashMessage")
public class FlashMessage implements Filter {
    final public static String FLASH_INFO_TITLE = "flash_info_title";
    final public static String FLASH_ERROR_TITLE = "flash_error_title";
    final public static String FLASH_INFO_MESSAGES = "flash_info_messages";
    final public static String FLASH_ERROR_MESSAGES = "flash_error_messages";
    final public static String ERROR_MESSAGES_BAG = "error_messages_bag";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        setupFlashes(httpRequest);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    /**
     * セッションスコープ内のフラッシュメッセージを、リクエストスコープに移動する
     */
    private void setupFlashes(HttpServletRequest request) {
        this
            .setupFlashInfoTitle(request)
            .setupFlashErrorTitle(request)
            .setupFlashInfoMessages(request)
            .setupFlashErrorMessages(request)
            .setupErrorMessagesBag(request);
    }

    private FlashMessage setupFlashInfoTitle(HttpServletRequest request) {
        final HttpSession session = request.getSession();

        if (null == session.getAttribute(FLASH_INFO_TITLE)) {
            return this;
        }

        request.setAttribute(
            FLASH_INFO_TITLE,
            (String) session.getAttribute(FLASH_INFO_TITLE)
        );

        session.removeAttribute(FLASH_INFO_TITLE);

        return this;
    }

    private FlashMessage setupFlashErrorTitle(HttpServletRequest request) {
        final HttpSession session = request.getSession();

        if (null == session.getAttribute(FLASH_ERROR_TITLE)) {
            return this;
        }

        request.setAttribute(
            FLASH_ERROR_TITLE,
            (String) session.getAttribute(FLASH_ERROR_TITLE)
        );

        session.removeAttribute(FLASH_ERROR_TITLE);

        return this;
    }

    private FlashMessage setupFlashInfoMessages(HttpServletRequest request) {
        final HttpSession session = request.getSession();

        if (null == session.getAttribute(FLASH_INFO_MESSAGES)) {
            return this;
        }

        request.setAttribute(
            FLASH_INFO_MESSAGES,
            (String) session.getAttribute(FLASH_INFO_MESSAGES)
        );

        session.removeAttribute(FLASH_INFO_MESSAGES);

        return this;
    }

    private FlashMessage setupFlashErrorMessages(HttpServletRequest request) {
        final HttpSession session = request.getSession();

        if (null == session.getAttribute(FLASH_ERROR_MESSAGES)) {
            return this;
        }

        request.setAttribute(
            FLASH_ERROR_MESSAGES,
            (String) session.getAttribute(FLASH_ERROR_MESSAGES)
        );

        session.removeAttribute(FLASH_ERROR_MESSAGES);

        return this;
    }

    private FlashMessage setupErrorMessagesBag(HttpServletRequest request) {
        final HttpSession session = request.getSession();

        if (null == session.getAttribute(ERROR_MESSAGES_BAG)) {
            return this;
        }

        request.setAttribute(
            ERROR_MESSAGES_BAG,
            (HashMap<String, String>) session.getAttribute(ERROR_MESSAGES_BAG)
        );

        session.removeAttribute(ERROR_MESSAGES_BAG);

        return this;
    }
}
