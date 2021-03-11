package oscana.s2n.seasar.struts.taglib;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import mockit.Expectations;
import oscana.s2n.handler.HttpResourceHolder;
import oscana.s2n.handler.HttpResourceHolderHandler;
import oscana.s2n.testCommon.S2NBaseTest;
import oscana.s2n.testCommon.S2NMockHttpRequest;

/**
 * {@link OscanaFunctions}のテスト。
 *
 */
public class OscanaFunctionsTest extends S2NBaseTest {

    S2NMockHttpRequest request = new S2NMockHttpRequest();

    /**
     * 入力値がnullの場合、空を戻すこと
     * @throws Exception
     */
    @Test
    public void testHForNull() throws Exception {
        Object input = null;
        assertEquals("", OscanaFunctions.h(input));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testHForCharArray() throws Exception {
        assertEquals("[1]", OscanaFunctions.h(new char[] { '1' }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testHForByteArray() throws Exception {
        assertEquals("[1]", OscanaFunctions.h(new byte[] { 1 }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testHForShortArray() throws Exception {
        assertEquals("[1]", OscanaFunctions.h(new short[] { 1 }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testHForIntArray() throws Exception {
        assertEquals("[1]", OscanaFunctions.h(new int[] { 1 }));
    }

    /**
     * 入力値がLongの場合、正常に実行できること
     * @throws Exception
     */
    @Test
    public void testHForLongArray() throws Exception {
        assertEquals("[1]", OscanaFunctions.h(new long[] { 1 }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testHForFloatArray() throws Exception {
        assertEquals("[1.0]", OscanaFunctions.h(new float[] { 1 }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testHForDoubleArray() throws Exception {
        assertEquals("[1.0]", OscanaFunctions.h(new double[] { 1 }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testHForBooleanArray() throws Exception {
        assertEquals("[true]", OscanaFunctions.h(new boolean[] { true }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testHForStringArray() throws Exception {
        assertEquals("[1]", OscanaFunctions.h(new String[] { "1" }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testHForObjectArray() throws Exception {
        assertEquals("[1]", OscanaFunctions.h(new Integer[] { Integer.valueOf(1) }));
    }

    /**
     * 入力値がStringの場合、正常に実行できること
     * @throws Exception
     */
    @Test
    public void testHForNotArray() throws Exception {
        assertEquals("test", OscanaFunctions.h("test"));
    }

    /**
     * 指定した文字列をHTMLエスケープに変換できること
     * @throws Exception
     */
    @Test
    public void testEscape_patter1() throws Exception {
        assertEquals("&lt;test", OscanaFunctions.escape("<test"));
    }

    /**
     * 指定した文字列をHTMLエスケープに変換できること
     * @throws Exception
     */
    @Test
    public void testEscape_patter2() throws Exception {
        assertEquals("te&lt;test", OscanaFunctions.escape("te<test"));
    }

    /**
     * 指定したURLをエスケープに変換できること
     * @throws Exception
     */
    @Test
    public void testU() throws Exception {
        assertEquals("%2Ftest", OscanaFunctions.u("/test"));
    }

    /**
     * 指定した文字列がnullの場合、空を戻すこと
     * @throws Exception
     */
    @Test
    public void testUForNull() throws Exception {
        String input = null;
        assertEquals("", OscanaFunctions.u(input));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testDate() throws Exception {
        assertNotNull(OscanaFunctions.date("20080131", "yyyyMMdd"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testDate_valueIsNull() throws Exception {
        assertNull(OscanaFunctions.date(null, "yyyyMMdd"));
    }

    /**
     * 指定したinputが空白のStringの場合、nullを戻すこと
     * @throws Exception
     */
    @Test
    public void testDate_valueIsKara() throws Exception {
        assertNull( OscanaFunctions.date("","yyyyMMdd"));
    }

    /**
     * 指定したinputがjava.util.Dateの場合、inputを戻すこと
     * @throws Exception
     */
    @Test
    public void testDate_valueIsDate() throws Exception {
        java.util.Date input = new java.util.Date();
        assertEquals(input, OscanaFunctions.date(input,"yyyyMMdd"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testDate_patternIsNull() throws Exception {
        try {
            OscanaFunctions.date("20080131", null);
            fail();
        } catch (Throwable e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("pattern", e.getMessage());
        }
    }

    /**
     * 日付のフォーマットに合わない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testDate_throwException() throws Exception {
        try {
            OscanaFunctions.date("2008/01/31", "yyyyMMdd");
            fail();
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }


    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testNumber() throws Exception {
        assertEquals("1000", OscanaFunctions.number("1000", "####").toString());
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testNumber_valueIsNull() throws Exception {
        assertNull(OscanaFunctions.number(null, "####"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testNumber_patternIsNull() throws Exception {
        try {
            OscanaFunctions.number("1000", null);
            fail();
        } catch (Throwable e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("pattern", e.getMessage());
        }
    }

    /**
     * 指定したフォーマットに合わない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testNumber_throwException() throws Exception {
        try {
            OscanaFunctions.number("aaaa", "#.##");
            fail();
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testBrForCRLF() throws Exception {
        assertEquals("<br />", OscanaFunctions.br("\r\n"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testBrForCR() throws Exception {
        assertEquals("<br />", OscanaFunctions.br("\r"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testBrForLF() throws Exception {
        assertEquals("<br />", OscanaFunctions.br("\n"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testBrForNull() throws Exception {
        assertEquals("", OscanaFunctions.br(null));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testNbsp() throws Exception {
        assertEquals("&nbsp;&nbsp;", OscanaFunctions.nbsp("  "));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testNbspForNull() throws Exception {
        assertEquals("", OscanaFunctions.nbsp(null));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testUrlForNull() throws Exception {
        new Expectations() {{
            httpServletRequest.getPathInfo();
            result = "/add/index.jsp";
            minTimes = 0;

            httpServletResponse.encodeURL(anyString);
            result = "/add/";
            minTimes = 0;
        }};
        setExecutionContext();

        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertEquals("/add/",OscanaFunctions.url(null));
            return null;
        }));

    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testUrlForAction() throws Exception {
        new Expectations() {{

            httpServletRequest.getPathInfo();
            result = "/add/index.jsp";
            minTimes = 0;

            httpServletRequest.getContextPath();
            result = "test";

            httpServletResponse.encodeURL("test/foreach");
            result = "/test/foreach";
            minTimes = 0;
        }};
        setExecutionContext();
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertEquals("/test/foreach",OscanaFunctions.url("/foreach"));
            return null;
        }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testUrlForParameter() throws Exception {
        new Expectations() {{
            httpServletRequest.getPathInfo();
            result = "/add/index.jsp";
            minTimes = 0;

            httpServletResponse.encodeURL("/add/edit/1");
            result = "/test/add/edit/1";
            minTimes = 0;
        }};
        setExecutionContext();

        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertEquals("/test/add/edit/1",OscanaFunctions.url("edit/1"));
            return null;
        }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testUrlForParameter1() throws Exception {
        new Expectations() {{
            httpServletRequest.getPathInfo();
            result = "/add/index.jsp";
            minTimes = 0;

            httpServletResponse.encodeURL("/edit/1");
            result = "/test/edit/1";
            minTimes = 0;
        }};
        setExecutionContext();
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertEquals("/test/edit/1",OscanaFunctions.url("/edit/1"));
            return null;
        }));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testLabelUsingMap() throws Exception {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("value", 1);
        m.put("label", "one");
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        dataList.add(m);
        Map<String, Object> m2 = new HashMap<String, Object>();
        m2.put("value", 2);
        m2.put("label", "two");
        dataList.add(m2);
        assertEquals("two", OscanaFunctions.label(2, dataList, "value", "label"));
        assertEquals("", OscanaFunctions.label(0, dataList, "value", "label"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testLabelUsingMap_null_null() throws Exception {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("value", null);
        m.put("label", "one");
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        dataList.add(m);
        assertEquals("one", OscanaFunctions.label(null, dataList, "value", "label"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testLabelUsingMap_empty_null() throws Exception {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("value", null);
        m.put("label", "one");
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        dataList.add(m);
        assertEquals("one", OscanaFunctions.label("", dataList, "value", "label"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testLabelUsingMap_empty_empty() throws Exception {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("value", "");
        m.put("label", "one");
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        dataList.add(m);
        assertEquals("one", OscanaFunctions.label("", dataList, "value", "label"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testLabelUsingMap_null_empty() throws Exception {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("value", "");
        m.put("label", "one");
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        dataList.add(m);
        assertEquals("one", OscanaFunctions.label(null, dataList, "value", "label"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testLabelUsingMap_string_integer() throws Exception {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("value", 1);
        m.put("label", "one");
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        dataList.add(m);
        assertEquals("one", OscanaFunctions.label("1", dataList, "value", "label"));
    }

    /**
     * 値がnullの場合、NullPointerExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testLabel_nullPattern1() throws Exception {
        try {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("value", 1);
            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
            dataList.add(m);
            OscanaFunctions.label("1", dataList, null, "label");
        } catch (Throwable e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("valueName", e.getMessage());
        }
    }

    /**
     * ラベルがnullの場合、NullPointerExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testLabel_nullPattern2() throws Exception {
        try {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("value", 1);
            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
            dataList.add(m);
            OscanaFunctions.label("1", dataList, "value", null);
        } catch (Throwable e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("labelName", e.getMessage());
        }
    }

    /**
     * リストがnullの場合、NullPointerExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testLabel_nullPattern3() throws Exception {
        try {
            OscanaFunctions.label("1", null, "value", "label");
        } catch (Throwable e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("dataList", e.getMessage());
        }
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testLabelUsingJavaBeans() throws Exception {
        Foo foo = new Foo();
        foo.id = 1;
        foo.name = "one";
        List<Foo> dataList = new ArrayList<Foo>();
        dataList.add(foo);
        assertEquals("one", OscanaFunctions.label(1, dataList, "id", "name"));
        assertEquals("", OscanaFunctions.label(2, dataList, "id", "name"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testEquals_pattern1() throws Exception {
        Foo foo = new Foo();
        foo.id = null;
        foo.name = "one";
        List<Foo> dataList = new ArrayList<Foo>();
        dataList.add(foo);
        assertEquals("", OscanaFunctions.label(2, dataList, "id", "name"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testEquals_pattern2() throws Exception {
        Foo foo = new Foo();
        foo.id = 1;
        foo.name = "one";
        List<Foo> dataList = new ArrayList<Foo>();
        dataList.add(foo);
        assertEquals("", OscanaFunctions.label(null, dataList, "id", "name"));
    }

    /**
     * テスト用クラス
     */
    public class Foo {
        public Integer id;

        public String name;

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }


    @Override
    protected void setClassToRegist() {
        registClassList = Arrays.asList(HttpResourceHolder.class);
    }

}