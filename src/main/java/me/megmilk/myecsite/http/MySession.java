package me.megmilk.myecsite.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class MySession {
    private static String SESS_KEY_FORM_VALUES = "formValues";

    private final HttpSession session;

    /**
     * フォームに入力された値をセッション変数に保持する
     *
     * @param request HttpServletRequest
     * @param name    フォームの name 属性
     * @implNote 「ひとつのキーには、ひとつの文字列型のみ」という想定
     */
    public static void setFormValue(HttpServletRequest request, String name) {
        final HttpSession session = request.getSession();
        final HashMap<String, String> formValues;

        if (null == session.getAttribute(SESS_KEY_FORM_VALUES)) {
            formValues = new HashMap<>();
        } else
            formValues = (HashMap<String, String>) session.getAttribute(SESS_KEY_FORM_VALUES);

        formValues.put(
            name,
            request.getParameter(name)
        );

        session.setAttribute(
            SESS_KEY_FORM_VALUES,
            formValues
        );
    }

    /**
     * セッション変数に保持していたすべての入力値を削除する
     */
    public static void expungeAllValue(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final HashMap<String, String> formValues;

        if (null == session.getAttribute(SESS_KEY_FORM_VALUES)) {
            return;
        }

        session.setAttribute(SESS_KEY_FORM_VALUES, new HashMap<>());
    }

    /**
     * セッションIDを再発行する
     */
    public static void regenerate(HttpServletRequest request) {
        request
            .getSession()
            .invalidate();

        request
            .getSession(true);
    }

    public MySession(HttpServletRequest request) {
        this.session = request.getSession();
    }

    /**
     * @return セッション変数に保存していた値を返す
     * @implNote 「ひとつのキーには、ひとつの文字列型のみ」という想定
     */
    public String getFormValue(String name) {
        final HashMap<String, String> formValue =
            (HashMap<String, String>) session.getAttribute(SESS_KEY_FORM_VALUES);

        if (null == formValue) {
            return null;
        }

        return formValue.get(name);
    }
}
