package me.megmilk.myecsite.services;

import me.megmilk.myecsite.models.Item;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class IndexService {
    /** 1ページに表示する商品数 */
    static final int ITEMS_PER_PAGE = 20;

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
}
