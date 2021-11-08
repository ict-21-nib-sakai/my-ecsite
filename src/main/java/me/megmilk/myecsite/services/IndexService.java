package me.megmilk.myecsite.services;

import me.megmilk.myecsite.http.PaginationManager;
import me.megmilk.myecsite.models.Item;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class IndexService {
    /** 1ページに表示する商品数 */
    static final int ITEMS_PER_PAGE = 20;

    /** ページネーションのリンクとして表示する数 */
    static final int DISPLAY_LINKS = 5;

    /** 読み去ったページを左側にリンクとして表示する数 */
    static final int LEFT_MARGIN = 2;

    /**
     * 商品名の部分一致による商品検索
     */
    public static List<Item> search(HttpServletRequest request) throws SQLException {
        String keyword = request.getParameter("keyword");
        if (null == keyword) {
             keyword = "";
        }

        int page = 0;
        try {
            page = Integer.parseInt(
                request.getParameter("page")
            );
        } catch (NumberFormatException ignored) {
        }

        return Item.search(
            keyword,
            ITEMS_PER_PAGE,
            page * ITEMS_PER_PAGE
        );
    }

    /**
     * ページネーション管理のインスタンスを返す
     */
    public static PaginationManager paginate(HttpServletRequest request) throws SQLException {
        final int totalCount = count(request);
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(request.getParameter("p"));
        } catch (NumberFormatException ignored) {
        }

        return new PaginationManager(
            totalCount,
            ITEMS_PER_PAGE,
            currentPage,
            DISPLAY_LINKS,
            LEFT_MARGIN
        );
    }

    /**
     * @return 商品名の部分一致による商品検索結果が、全部で何件か
     */
    public static int count(HttpServletRequest request) throws SQLException {
        String keyword = request.getParameter("keyword");

        if (null == keyword) {
            keyword = "";
        }

        return Item.count(keyword);
    }
}
