package me.megmilk.myecsite.services;

import me.megmilk.myecsite.models.User;

import java.sql.SQLException;

public class UserService {
    /**
     * プライマリキーによるユーザー検索
     */
    public static User find(int id) throws SQLException {
        final User user = User.find(id);

        // 存在しないプライマリキー
        if (null == user) {
            return null;
        }

        // 退会済みの利用者
        if (null != user.getDeleted_at()) {
            return null;
        }

        // アカウントが一時停止されている利用者
        if (user.getSuspended()) {
            return null;
        }

        return user;
    }
}
