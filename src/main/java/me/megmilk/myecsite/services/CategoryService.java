package me.megmilk.myecsite.services;

import me.megmilk.myecsite.models.Category;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    /**
     * すべてのカテゴリを列挙
     */
    public static List<Category> enumerate() throws SQLException {
        return Category.enumerate();
    }

    /**
     * プライマリキーによる検索
     */
    public static Category find(HttpServletRequest request
    ) throws SQLException, NumberFormatException {
        final int categoryId = Integer.parseInt(
            request.getPathInfo().substring(1)
        );

        return Category.find(categoryId);
    }
}
