package me.megmilk.myecsite.http;

import me.megmilk.myecsite.models.Category;
import me.megmilk.myecsite.models.User;
import me.megmilk.myecsite.services.CartService;
import me.megmilk.myecsite.services.CategoryService;
import me.megmilk.myecsite.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class FlashBag {
    final public static String FLASH_INFO_TITLE = "flash_info_title";
    final public static String FLASH_ERROR_TITLE = "flash_error_title";

    private final HttpServletRequest request;
    private final HttpSession session;

    private String flashInfoTitle;
    private String flashErrorTitle;
    private List<Category> categories;

    private Integer _totalQuantity = null;
    private User _user = null;

    public FlashBag(HttpServletRequest request) throws SQLException {
        this.request = request;
        this.session = request.getSession();

        this
            .setFlashInfoTitle()
            .setFlashErrorTitle()
            .setCategories();
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
}
