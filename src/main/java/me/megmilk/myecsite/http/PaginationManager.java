package me.megmilk.myecsite.http;

public class PaginationManager {
    final private int totalCount;
    final private int itemsPerPage;
    final private int currentPage;
    final private int displayLinks;
    final private int leftMargin;
    final private int endOfPage;

    /**
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

    /**
     * 現在のページは、開始番号N件目になるか計算する
     */
    public int itemSince() {
        return (currentPage - 1) * itemsPerPage + 1;
    }

    /**
     * リンクとして表示する最初のページ番号を計算する
     */
    public int calcStartPageNumber() {
        if (currentPage - leftMargin <= 0) {
            return 1;
        }

        return currentPage - leftMargin;
    }

    /**
     * リンクとして表示する最後のページ番号を計算する
     */
    public int calcLastPageNumber() {
        return calcStartPageNumber() + displayLinks - 1;
    }
}
