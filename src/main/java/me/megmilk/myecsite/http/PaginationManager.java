package me.megmilk.myecsite.http;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaginationManager {
    final private int totalCount;
    final private int itemsPerPage;
    final private int currentPage;
    final private int displayLinks;
    final private int leftMargin;
    final private int endOfPage;

    /**
     * GETリクエストのパラメータ名とその値
     * <p>
     * 順序を保つため List でラッピングしています。
     */
    private final List<HashMap<String, String[]>> params = new ArrayList<>();

    /**
     * コンストラクタ
     *
     * @param totalCount   すべての件数
     * @param itemsPerPage 1ページあたりの表示件数
     * @param currentPage  現在のページ番号。開始番号は1です。
     * @param displayLinks リンクとして表示する数
     * @param leftMargin   読み去ったページを左側にリンクとして表示する数
     */
    public PaginationManager(
        int totalCount,
        int itemsPerPage,
        int currentPage,
        int displayLinks,
        int leftMargin
    ) {
        this.totalCount = totalCount;
        this.itemsPerPage = itemsPerPage;
        this.currentPage = currentPage;
        this.displayLinks = displayLinks;
        this.leftMargin = leftMargin;

        this.endOfPage = (int) Math.ceil(
            (double) totalCount / itemsPerPage
        );
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * パラメータ名と値を内部の変数に保持する
     */
    public void setParameters(HttpServletRequest request, String[] paramNames) {
        for (String paramName : paramNames) {
            final HashMap<String, String[]> param = new HashMap<>();
            param.put(paramName, request.getParameterValues(paramName));

            params.add(param);
        }
    }

    /**
     * パラメーター付きURLを組み立てる
     */
    public String buildUrl(int pageNumber) throws UnsupportedEncodingException {
        StringBuilder queryParam = new StringBuilder();

        for (final HashMap<String, String[]> param : this.params) {
            for (final String key : param.keySet()) {
                final String[] values = param.get(key);

                if (null == values) {
                    continue;
                }

                for (String value : values) {
                    queryParam
                        .append(key)
                        .append("=")
                        .append(URLEncoder.encode(value, "UTF-8"))
                        .append("&")
                    ;
                }
            }
        }

        queryParam.append("p=").append(pageNumber);

        return "?" + queryParam;
    }

    /**
     * 現在のページは、開始番号N件目になるか計算する
     */
    public int itemSince() {
        return (currentPage - 1) * itemsPerPage + 1;
    }

    /**
     * 現在のページは、終了番号N件目になるか計算する
     */
    public int itemUntil() {
        if (currentPage == endOfPage) {
            return totalCount;
        }

        return itemSince() + itemsPerPage - 1;
    }

    /**
     * リンクとして表示する最初のページ番号を計算する
     */
    public int calcStartPageNumber() {
        if (currentPage - leftMargin <= 0) {
            return 1;
        }

        if (currentPage > leftMargin && currentPage - leftMargin + displayLinks <= endOfPage) {
            return currentPage - leftMargin;
        }

        return endOfPage - displayLinks + 1;
    }

    /**
     * リンクとして表示する最後のページ番号を計算する
     */
    public int calcLastPageNumber() {
        return Math.min(
            calcStartPageNumber() + displayLinks - 1,
            endOfPage
        );
    }
}
