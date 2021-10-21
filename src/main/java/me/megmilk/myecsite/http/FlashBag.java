package me.megmilk.myecsite.http;

import me.megmilk.myecsite.models.Category;
import me.megmilk.myecsite.models.User;
import me.megmilk.myecsite.services.CartService;
import me.megmilk.myecsite.services.CategoryService;
import me.megmilk.myecsite.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class FlashBag {
    final public static String FLASH_INFO_TITLE = "flash_info_title";
    final public static String FLASH_ERROR_TITLE = "flash_error_title";
    final public static String MESSAGES_BAG = "messages_bag";

    private final HttpServletRequest request;
    private final HttpSession session;

    private String flashInfoTitle;
    private String flashErrorTitle;
    private HashMap<String, String> messagesBag = new HashMap<>();
    private List<Category> categories;

    private Integer _totalQuantity = null;
    private User _user = null;

    public FlashBag(HttpServletRequest request) throws SQLException {
        this.request = request;
        this.session = request.getSession();

        this
            .setFlashInfoTitle()
            .setFlashErrorTitle()
            .setCategories()
            .setMessagesBag();
    }

    /**
     * ログインしているユーザーの User インスタンスを得る
     */
    public User getUser() throws SQLException {
        if (null == session.getAttribute("userId")) {
            return null;
        }

        if (null == this._user) {
            final int userId = (int) session.getAttribute("userId");
            this._user = UserService.find(userId);
        }

        return this._user;
    }

    /**
     * カート内の商品数量の合計を計算
     */
    public int getTotalQuantity() throws SQLException {
        if (null == this._totalQuantity) {
            this._totalQuantity = CartService.totalQuantity(request);
        }

        return this._totalQuantity;
    }

    /**
     * おしらせ用 フラッシュメッセージ (タイトル部分) を返す
     */
    public String getFlashInfoTitle() {
        return flashInfoTitle;
    }

    /**
     * エラー用 フラッシュメッセージ (タイトル部分) を返す
     */
    public String getFlashErrorTitle() {
        return flashErrorTitle;
    }

    /**
     * すべてのカテゴリを返す
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * @return フォームの name 属性に該当するメッセージの有無を返す
     */
    public boolean hasMessage(String key) {
        final String message = messagesBag.get(key);

        return null != message;
    }

    /**
     * @return フォームの name 属性に該当するメッセージを返す
     */
    public String getMessage(String name) {
        return messagesBag.get(name);
    }

    /**
     * おしらせ用 フラッシュメッセージ (タイトル部分) を代入
     */
    public static void setInfoTitle(HttpServletRequest request, String infoTitle) {
        final HttpSession session = request.getSession();

        session.setAttribute(
            FLASH_INFO_TITLE,
            infoTitle
        );
    }

    /**
     * エラー用 フラッシュメッセージ (タイトル部分) を代入
     */
    public static void setErrorTitle(HttpServletRequest request, String errorTitle) {
        final HttpSession session = request.getSession();

        session.setAttribute(
            FLASH_ERROR_TITLE,
            errorTitle
        );
    }

    /**
     * フォーム内の入力項目毎のメッセージを代入
     *
     * @param request HttpServletRequest
     * @param name    フォームの name 属性
     * @param message 代入するメッセージ
     */
    public static void setMessagesBag(HttpServletRequest request, String name, String message) {
        final HttpSession session = request.getSession();
        HashMap<String, String> messagesBag = (HashMap<String, String>) session.getAttribute(MESSAGES_BAG);

        if (null == messagesBag) {
            messagesBag = new HashMap<>();
        }

        messagesBag.put(name, message);

        session.setAttribute(
            MESSAGES_BAG,
            messagesBag
        );
    }

    /**
     * @return フォームの入力項目に対して何かしら1つ以上メッセージがあるかどうか
     */
    public static boolean hasAnyMessage(HttpServletRequest request) {
        final HttpSession session = request.getSession();

        if (null == session.getAttribute(MESSAGES_BAG)) {
            return false;
        }

        final HashMap<String, String> messagesBag = (HashMap<String, String>) session.getAttribute(MESSAGES_BAG);

        return messagesBag.size() >= 1;
    }

    /**
     * おしらせ用 フラッシュメッセージ (タイトル部分) を代入
     */
    private FlashBag setFlashInfoTitle() {
        if (null == session.getAttribute(FLASH_INFO_TITLE)) {
            return this;
        }

        this.flashInfoTitle = (String) session.getAttribute(FLASH_INFO_TITLE);
        session.removeAttribute(FLASH_INFO_TITLE);

        return this;
    }

    /**
     * エラー用 フラッシュメッセージ (タイトル部分) を代入
     */
    private FlashBag setFlashErrorTitle() {
        if (null == session.getAttribute(FLASH_ERROR_TITLE)) {
            return this;
        }

        this.flashErrorTitle = (String) session.getAttribute(FLASH_ERROR_TITLE);
        session.removeAttribute(FLASH_ERROR_TITLE);

        return this;
    }

    /**
     * すべてのカテゴリを取得して、内部の変数に代入する
     */
    private FlashBag setCategories() throws SQLException {
        categories = CategoryService.enumerate();

        return this;
    }

    /**
     * フォーム内の入力項目毎のメッセージを代入
     */
    private FlashBag setMessagesBag() {
        if (null == session.getAttribute(MESSAGES_BAG)) {
            return this;
        }

        this.messagesBag = (HashMap<String, String>) session.getAttribute(MESSAGES_BAG);
        session.removeAttribute(MESSAGES_BAG);

        return this;
    }
}
