package me.megmilk.myecsite.http;

public class PaginationManager {
    final private int totalCount;
    final private int itemsPerPage;
    final private int currentPage;
    final private int displayLinks;
    final private int endOfPage;

    /**
     * @param totalCount   すべての件数
     * @param itemsPerPage 1ページあたりの表示件数
     * @param currentPage  現在のページ番号。開始番号は1です。
     * @param displayLinks リンクとして表示する数
     */
    public PaginationManager(int totalCount, int itemsPerPage, int currentPage, int displayLinks) {
        this.totalCount = totalCount;
        this.itemsPerPage = itemsPerPage;
        this.currentPage = currentPage;
        this.displayLinks = displayLinks;

        this.endOfPage = (int) Math.ceil(
            (double) totalCount / itemsPerPage
        );
    }

    /**
     * 現在のページは、開始番号N件目になるか計算する
     */
    public int itemSince() {
        return (currentPage - 1) * itemsPerPage + 1;
    }
}
