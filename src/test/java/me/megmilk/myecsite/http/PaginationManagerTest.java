package me.megmilk.myecsite.http;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaginationManagerTest {
    /**
     * ページネーション コンストラクタ
     */
    @Test
    void PaginationManagerTest1() throws NoSuchFieldException, IllegalAccessException {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        final int TEST_CURRENT_PAGE = 1;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        // private な変数を参照する
        final Field field = PaginationManager.class.getDeclaredField("endOfPage");
        field.setAccessible(true);

        final int EXPECTED_END_OF_PAGE = 11;
        assertEquals(EXPECTED_END_OF_PAGE, field.getInt(paginator));
    }

    /**
     * 現在のページは、開始番号N件目になるか計算する
     */
    @Test
    void itemSinceTest1() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        final int TEST_CURRENT_PAGE = 1;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_SINCE = 1;
        assertEquals(EXPECTED_SINCE, paginator.itemSince());
    }

    /**
     * 現在のページは、開始番号N件目になるか計算する
     */
    @Test
    void itemSinceTest2() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        final int TEST_CURRENT_PAGE = 2;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_SINCE = 21;
        assertEquals(EXPECTED_SINCE, paginator.itemSince());
    }

    /**
     * 現在のページは、開始番号N件目になるか計算する
     */
    @Test
    void itemSinceTest3() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        final int TEST_CURRENT_PAGE = 11;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_SINCE = 201;
        assertEquals(EXPECTED_SINCE, paginator.itemSince());
    }

    /**
     * 現在のページは、終了番号N件目になるか計算する
     */
    @Test
    void itemUntilTest1() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        final int TEST_CURRENT_PAGE = 1;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_UNTIL = 20;
        assertEquals(EXPECTED_UNTIL, paginator.itemUntil());
    }

    /**
     * 現在のページは、終了番号N件目になるか計算する
     */
    @Test
    void itemUntilTest2() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        final int TEST_CURRENT_PAGE = 2;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_UNTIL = 40;
        assertEquals(EXPECTED_UNTIL, paginator.itemUntil());
    }

    /**
     * 現在のページは、終了番号N件目になるか計算する
     */
    @Test
    void itemUntilTest3() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        final int TEST_CURRENT_PAGE = 10;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_UNTIL = 200;
        assertEquals(EXPECTED_UNTIL, paginator.itemUntil());
    }

    /**
     * 現在のページは、終了番号N件目になるか計算する
     */
    @Test
    void itemUntilTest4() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        final int TEST_CURRENT_PAGE = 11;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_UNTIL = 202;
        assertEquals(EXPECTED_UNTIL, paginator.itemUntil());
    }

    /**
     * リンクとして表示する最初のページ番号を計算する
     */
    @Test
    void sincePageNumberTest1() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「1」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 1;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 1;
        assertEquals(EXPECTED_NUMBER, paginator.calcStartPageNumber());
    }

    /**
     * リンクとして表示する最初のページ番号を計算する
     */
    @Test
    void sincePageNumberTest2() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「2」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 2;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 1;
        assertEquals(EXPECTED_NUMBER, paginator.calcStartPageNumber());
    }

    /**
     * リンクとして表示する最初のページ番号を計算する
     */
    @Test
    void sincePageNumberTest3() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「3」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 3;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 1;
        assertEquals(EXPECTED_NUMBER, paginator.calcStartPageNumber());
    }

    /**
     * リンクとして表示する最初のページ番号を計算する
     */
    @Test
    void sincePageNumberTest4() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「4」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 4;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 2;
        assertEquals(EXPECTED_NUMBER, paginator.calcStartPageNumber());
    }

    /**
     * リンクとして表示する最初のページ番号を計算する
     */
    @Test
    void sincePageNumberTest5() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「9」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 9;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 7;
        assertEquals(EXPECTED_NUMBER, paginator.calcStartPageNumber());
    }

    /**
     * リンクとして表示する最初のページ番号を計算する
     */
    @Test
    void sincePageNumberTest6() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「10」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 10;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 7;
        assertEquals(EXPECTED_NUMBER, paginator.calcStartPageNumber());
    }

    /**
     * リンクとして表示する最初のページ番号を計算する
     */
    @Test
    void sincePageNumberTest7() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「11」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 11;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 7;
        assertEquals(EXPECTED_NUMBER, paginator.calcStartPageNumber());
    }

    /**
     * リンクとして表示する最初のページ番号を計算する
     */
    @Test
    void sincePageNumberTest8() {
        final int TEST_TOTAL_COUNT = 174;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「5」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 5;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 3;
        assertEquals(EXPECTED_NUMBER, paginator.calcStartPageNumber());
    }

    /**
     * リンクとして表示する最初のページ番号を計算する
     */
    @Test
    void sincePageNumberTest9() {
        final int TEST_TOTAL_COUNT = 174;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「6」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 6;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 4;
        assertEquals(EXPECTED_NUMBER, paginator.calcStartPageNumber());
    }

    /**
     * リンクとして表示する最後のページ番号を計算する
     */
    @Test
    void calcLastPageNumberTest1() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「1」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 1;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 5;
        assertEquals(EXPECTED_NUMBER, paginator.calcLastPageNumber());
    }

    /**
     * リンクとして表示する最後のページ番号を計算する
     */
    @Test
    void calcLastPageNumberTest2() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「2」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 2;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 5;
        assertEquals(EXPECTED_NUMBER, paginator.calcLastPageNumber());
    }

    /**
     * リンクとして表示する最後のページ番号を計算する
     */
    @Test
    void calcLastPageNumberTest3() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「3」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 3;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 5;
        assertEquals(EXPECTED_NUMBER, paginator.calcLastPageNumber());
    }

    /**
     * リンクとして表示する最後のページ番号を計算する
     */
    @Test
    void calcLastPageNumberTest4() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「4」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 4;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 6;
        assertEquals(EXPECTED_NUMBER, paginator.calcLastPageNumber());
    }

    /**
     * リンクとして表示する最後のページ番号を計算する
     */
    @Test
    void calcLastPageNumberTest5() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「5」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 5;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 7;
        assertEquals(EXPECTED_NUMBER, paginator.calcLastPageNumber());
    }

    /**
     * リンクとして表示する最後のページ番号を計算する
     */
    @Test
    void calcLastPageNumberTest6() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「9」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 9;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 11;
        assertEquals(EXPECTED_NUMBER, paginator.calcLastPageNumber());
    }

    /**
     * リンクとして表示する最後のページ番号を計算する
     */
    @Test
    void calcLastPageNumberTest7() {
        final int TEST_TOTAL_COUNT = 202;
        final int TEST_ITEMS_PER_PAGE = 20;
        // 現在閲覧中のページは「10」ページ目と想定したテスト
        final int TEST_CURRENT_PAGE = 10;
        final int TEST_DISPLAY_LINKS = 5;
        final int TEST_MARGIN = 2;

        PaginationManager paginator = new PaginationManager(
            TEST_TOTAL_COUNT,
            TEST_ITEMS_PER_PAGE,
            TEST_CURRENT_PAGE,
            TEST_DISPLAY_LINKS,
            TEST_MARGIN
        );

        final int EXPECTED_NUMBER = 11;
        assertEquals(EXPECTED_NUMBER, paginator.calcLastPageNumber());
    }
}
