package me.megmilk.myecsite.controllers.sessions;

import me.megmilk.myecsite.controllers.filters.FlashMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Flash {
    /**
     * おしらせ用 フラッシュメッセージ (タイトル部分) を代入
     */
    public static void setInfoTitle(HttpServletRequest request, String text) {
        final HttpSession session = request.getSession();

        session.setAttribute(
            FlashMessage.FLASH_INFO_TITLE,
            text
        );
    }

    /**
     * エラー用 フラッシュメッセージ (タイトル部分) を代入
     */
    public static void setErrorTitle(HttpServletRequest request, String text) {
        final HttpSession session = request.getSession();

        session.setAttribute(
            FlashMessage.FLASH_ERROR_TITLE,
            text
        );
    }
}
