package me.megmilk.myecsite.services;

import me.megmilk.myecsite.models.Category;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    /**
     * すべてのカテゴリを列挙
     */
    public static List<Category> enumerate() throws SQLException {
        return Category.enumerate();
    }
}
