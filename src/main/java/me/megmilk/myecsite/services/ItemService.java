package me.megmilk.myecsite.services;

import me.megmilk.myecsite.models.Item;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class ItemService {
    /**
     * カテゴリと商品名部分部分一致で、商品を列挙する
     */
    public static List<Item> enumerate(HttpServletRequest request
    ) throws SQLException, NumberFormatException {
        final int categoryId = Integer.parseInt(
            request.getPathInfo().substring(1)
        );

        final String keyword = request.getParameter("keyword");

        // TODO limit, offset は仮。あとでパラメーターを参照して正式な値にすること
        final int PSEUDO_LIMIT = Integer.MAX_VALUE;
        final int PSEUDO_OFFSET = 0;

        if (null == keyword) {
            return Item.search(
                categoryId,
                PSEUDO_LIMIT,
                PSEUDO_OFFSET
            );
        }

        return Item.search(keyword, categoryId, PSEUDO_LIMIT, PSEUDO_OFFSET);
    }

    /**
     * 商品をプライマリキーで検索する
     */
    public static Item find(HttpServletRequest request
    ) throws SQLException, NumberFormatException {
        final int itemId = Integer.parseInt(
            request.getPathInfo().substring(1)
        );

        return Item.find(itemId);
    }
}
