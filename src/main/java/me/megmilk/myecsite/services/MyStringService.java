package me.megmilk.myecsite.services;

/**
 * オリジナルの文字列の操作クラス
 */
public class MyStringService {
    /**
     * 主に v-model 用に文字列を置換
     * <pre>
     * \ → \\
     * ' → \'
     * " → \"
     * \t → 半角スペース1文字
     * </pre>
     */
    public static String convertQuotation(String input) {
        if (null == input) {
            return "";
        }

        return input
            .replace("\\", "\\\\")
            .replace("'", "\\'")
            .replace("\"", "\\")
            .replace("\t", " ");
    }
}
