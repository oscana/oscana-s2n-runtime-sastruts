package oscana.s2n.seasar.framework.beans.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import oscana.s2n.seasar.framework.beans.util.AbstCopyTest.BeanNames;

/**
 * {@link CreateAndCopy}のテスト。
 *
 */
public class CreateAndCopyTest {

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = " ";
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).execute();
        assertEquals("aaa", dest.aaa);
        assertEquals(" ", dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_testNestBean() throws Exception {
        MyBean6 src = new MyBean6();
        src.aaa = "aaa";
        src.bbb = " ";
        src.myBean2 = new MyBean2("test1", "test2");
        MyBean6 dest = new CreateAndCopy<MyBean6>(MyBean6.class, src).execute();
        assertEquals("aaa", dest.aaa);
        assertEquals(" ", dest.bbb);
        assertEquals("test1", dest.myBean2.getTest1());
        assertEquals("test2", dest.myBean2.getTest2());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_includes() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).includes("aaa").execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_excludes() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).excludes("bbb").execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_null() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = null;
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_excludesNull() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = null;
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).excludesNull().execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_whitespace() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = " ";
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).execute();
        assertEquals("aaa", dest.aaa);
        assertEquals(" ", dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_prefix() throws Exception {
        MyBean3 src = new MyBean3();
        MyBean4 dest = new CreateAndCopy<MyBean4>(MyBean4.class, src).prefix(BeanNames.abc_()).execute();
        assertEquals("abc", dest.value);
        assertEquals("exclude", dest.exclude);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_excludesWhitespace() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = " ";
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).excludesWhitespace().execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_excludesWhitespaceAndExcludesNull() throws Exception {
        MyBean src = new MyBean();
        src.aaa = null;
        src.bbb = " ";
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).excludesWhitespace().excludesNull().execute();
        assertNull(dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
        MyBean5 src = new MyBean5();
        src.setAbc_value("value");
        src.setAb(null);
        MyBean4 dest = new CreateAndCopy<MyBean4>(MyBean4.class, src).excludesWhitespace().excludesNull()
                .prefix(BeanNames.abc_()).execute();
        assertEquals("value", dest.value);
        assertEquals(" ", dest.xyz_value);
        assertNull(dest.other$fff);
        assertEquals("exclude11111", dest.exclude);
        assertEquals("ab11111", dest.ab);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_includesNull() throws Exception {
        MyBean src = new MyBean();
        src.aaa = null;
        src.bbb = "bbb";
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).includes("aaa").execute();
        assertNull(dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_includesWhitespace() throws Exception {
        MyBean src = new MyBean();
        src.aaa = " ";
        src.bbb = "bbb";
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).includes("aaa").execute();
        assertEquals(" ", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_includesAndExcludesWhitespace() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = " ";
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).includes("aaa").excludesWhitespace().execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_includesAndExcludesNull() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = null;
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).includes("aaa").excludesNull().execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_excludesAndPrefix() throws Exception {
        MyBean3 src = new MyBean3();
        MyBean4 dest = new CreateAndCopy<MyBean4>(MyBean4.class, src).excludes("abc_exclude").prefix(BeanNames.abc_())
                .execute();
        assertEquals("abc", dest.value);
        assertEquals("exclude11111", dest.exclude);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean_includesAndNullAndWhitespace() throws Exception {
        MyBean5 src = new MyBean5();
        MyBean3 dest = new CreateAndCopy<MyBean3>(MyBean3.class, src).includes("ab", "abc_value", "abc_exclude")
                .execute();
        assertEquals(" ", dest.abc_value);
        assertEquals("ab", dest.ab);
        assertNull(dest.abc_exclude);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCreateAndCopy_srcThrowException() {
        MyBean src = null;
        try {
            new CreateAndCopy<MyBean>(MyBean.class, src);
        } catch (Throwable e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("src", e.getMessage());
        }
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCreateAndCopy_destThrowException() {
        MyBean src = new MyBean();
        Class<MyBean> dest = null;
        try {
            new CreateAndCopy<MyBean>(dest, src);
        } catch (Throwable e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("destClass", e.getMessage());
        }
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).execute();
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    @SuppressWarnings("rawtypes")
    public void testExecute_beanToMap_hashmap() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        HashMap dest = new CreateAndCopy<HashMap>(HashMap.class, src).execute();
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_exclude_prefix() throws Exception {
        MyBean3 src = new MyBean3();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).prefix(
                "abc_").excludes("abc_exclude").execute();
        assertEquals(1, dest.size());
        assertEquals("abc", dest.get("value"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_include() throws Exception {
        MyBean3 src = new MyBean3();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("abc_exclude", "other$fff").execute();
        assertEquals(2, dest.size());
        assertEquals("exclude", dest.get("abc_exclude"));
        assertEquals("other", dest.get("other.fff"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_excludes() throws Exception {
        MyBean3 src = new MyBean3();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludes("abc_exclude", "other$fff").execute();
        assertEquals(3, dest.size());
        assertEquals("abc", dest.get("abc_value"));
        assertEquals("xyz", dest.get("xyz_value"));
        assertEquals("ab", dest.get("ab"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_prefix() throws Exception {
        MyBean3 src = new MyBean3();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).prefix(BeanNames.abc_()).execute();
        assertEquals(2, dest.size());
        assertEquals("abc", dest.get("value"));
        assertEquals("exclude", dest.get("exclude"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_excludesWhitespace() throws Exception {
        MyBean5 src = new MyBean5();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesWhitespace().execute();
        assertEquals(4, dest.size());
        assertEquals("xyz", dest.get("xyz_value"));
        assertEquals("other", dest.get("other.fff"));
        assertEquals("ab", dest.get("ab"));
        assertNull(dest.get("abc_exclude"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_excludesNull() throws Exception {
        MyBean5 src = new MyBean5();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesNull().execute();
        assertEquals(4, dest.size());
        assertEquals("xyz", dest.get("xyz_value"));
        assertEquals("other", dest.get("other.fff"));
        assertEquals("ab", dest.get("ab"));
        assertEquals(" ", dest.get("abc_value"));
    }

    /**
     * excludesNullの場合、ゲットメソッドが宣言されてないプロパティに対して、コピーしないこと。
     *
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_excludesNull_noGetMethed() throws Exception {
        MyBean7 src = new MyBean7();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesNull().execute();
        assertEquals(3, dest.size());
        assertEquals("xyz", dest.get("xyz_value"));
        assertEquals("other", dest.get("other.fff"));
        assertEquals(" ", dest.get("abc_value"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_excludesWhitespaceAndExcludesNull() throws Exception {
        MyBean5 src = new MyBean5();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesWhitespace().excludesNull().execute();
        assertEquals(3, dest.size());
        assertEquals("xyz", dest.get("xyz_value"));
        assertEquals("other", dest.get("other.fff"));
        assertEquals("ab", dest.get("ab"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
        MyBean5 src = new MyBean5();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesWhitespace().excludesNull()
                .prefix(BeanNames.abc_()).execute();
        assertEquals(0, dest.size());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_nullOrWhitespace() throws Exception {
        MyBean5 src = new MyBean5();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).execute();
        assertEquals(5, dest.size());
        assertEquals("xyz", dest.get("xyz_value"));
        assertEquals("other", dest.get("other.fff"));
        assertEquals("ab", dest.get("ab"));
        assertEquals(" ", dest.get("abc_value"));
        assertNull(dest.get("abc_exclude"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_includeAndNull() throws Exception {
        MyBean5 src = new MyBean5();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("abc_exclude", "other$fff").execute();
        assertEquals(2, dest.size());
        assertNull(dest.get("abc_exclude"));
        assertEquals("other", dest.get("other.fff"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_includeAndWhitespace() throws Exception {
        MyBean5 src = new MyBean5();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("abc_value", "other$fff").execute();
        assertEquals(2, dest.size());
        assertEquals(" ", dest.get("abc_value"));
        assertEquals("other", dest.get("other.fff"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_includesAndExcludesWhitespace() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = " ";
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("aaa").excludesWhitespace().execute();
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_includesAndExcludesNull() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = null;
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("aaa").excludesNull().execute();
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_excludesAndPrefix() throws Exception {
        MyBean3 src = new MyBean3();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludes("abc_exclude").prefix(BeanNames.abc_())
                .execute();
        assertEquals(1, dest.size());
        assertEquals("abc", dest.get("value"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap_includesAndNullAndWhitespace() throws Exception {
        MyBean5 src = new MyBean5();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("ab", "abc_value", "abc_exclude")
                .execute();
        assertEquals(" ", dest.get("abc_value"));
        assertEquals("ab", dest.get("ab"));
        assertNull(dest.get("abc_exclude"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean() throws Exception {
        BeanMap src = new BeanMap();
        src.put("ab", "aaa");
        src.put("other.fff", "other");
        MyBean3 dest = new CreateAndCopy<MyBean3>(MyBean3.class, src).execute();
        assertEquals("aaa", dest.ab);
        assertEquals("other", dest.other$fff);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_testNestBean() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", "other");
        src.put("myBean2", new MyBean2("test1", "test2"));
        MyBean6 dest = new CreateAndCopy<MyBean6>(MyBean6.class, src).execute();
        assertEquals("aaa", dest.aaa);
        assertEquals("other", dest.bbb);
        assertEquals("test1", dest.myBean2.getTest1());
        assertEquals("test2", dest.myBean2.getTest2());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_includes() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).includes("aaa").execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_excludes() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).excludes("aaa").execute();
        assertEquals(" ", dest.bbb);
        assertNull(dest.aaa);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_prefix() throws Exception {
        BeanMap src = new BeanMap();
        src.put("ab", "aaa");
        src.put("abc_value", "bbb");
        MyBean4 dest = new CreateAndCopy<MyBean4>(MyBean4.class, src).prefix("abc_").execute();
        assertEquals("bbb", dest.value);
        assertEquals("ab11111", dest.ab);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_excludesWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).excludesWhitespace().execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_excludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("abc_value", "aaa");
        src.put("ab", null);
        MyBean3 dest = new CreateAndCopy<MyBean3>(MyBean3.class, src).excludesNull().execute();
        assertEquals("aaa", dest.abc_value);
        assertEquals("ab", dest.ab);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_excludesWhitespaceAndExcludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("abc_value", "aaa");
        src.put("ab", null);
        src.put("abc_exclude", " ");
        MyBean3 dest = new CreateAndCopy<MyBean3>(MyBean3.class, src).excludesWhitespace().excludesNull().execute();
        assertEquals("aaa", dest.abc_value);
        assertEquals("ab", dest.ab);
        assertEquals("exclude", dest.abc_exclude);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("abc_value", "aaa");
        src.put("ab", null);
        src.put("abc_exclude", " ");
        src.put("xyz_value", "value");
        MyBean4 dest = new CreateAndCopy<MyBean4>(MyBean4.class, src).excludesWhitespace().excludesNull()
                .prefix(BeanNames.abc_()).execute();
        assertEquals("aaa", dest.value);
        assertEquals(" ", dest.xyz_value);
        assertNull(dest.other$fff);
        assertEquals("exclude11111", dest.exclude);
        assertEquals("ab11111", dest.ab);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_null() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", null);
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_whitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).execute();
        assertEquals("aaa", dest.aaa);
        assertEquals(" ", dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_includesAndNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", null);
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).includes("aaa", "bbb").execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_includesAndWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).includes("aaa", "bbb").execute();
        assertEquals("aaa", dest.aaa);
        assertEquals(" ", dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_includesAndExcludes() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", "bbb");
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).includes("aaa").excludes("bbb").execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_includesAndExcludesWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).includes("aaa").excludesWhitespace().execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_includesAndExcludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", null);
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).includes("aaa").excludesNull().execute();
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_excludesAndPrefix() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("abc_bbb", "bbb");
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).excludes("aaa").prefix("abc_").execute();
        assertEquals("bbb", dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean_includesAndNullAndWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("abc_value", null);
        src.put("xyz_value", " ");
        src.put("ab", "testab");
        MyBean3 dest = new CreateAndCopy<MyBean3>(MyBean3.class, src).includes("ab", "xyz_value", "abc_value")
                .execute();
        assertNull(dest.getAbc_value());
        assertEquals(" ", dest.getXyz_value());
        assertEquals("testab", dest.getAb());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).execute();
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    @SuppressWarnings("rawtypes")
    public void testExecute_mapToMap_hashMap() throws Exception {
        HashMap<String, String> src = new HashMap<>();
        src.put("aaa", "aaa");
        HashMap dest = new CreateAndCopy<HashMap>(HashMap.class, src).execute();
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_includes() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", "bbb");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("aaa").execute();
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_excludes() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", "bbb");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludes("aaa").execute();
        assertEquals("bbb", dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_prefix() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("abc_bbb", "bbb");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).prefix("abc_").execute();
        assertEquals(1, dest.size());
        assertEquals("bbb", dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_excludesWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesWhitespace().execute();
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_excludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", null);
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesNull().execute();
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_excludesWhitespaceAndExcludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", null);
        src.put("ccc", " ");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesWhitespace().excludesNull().execute();
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("abc_aaa", "aaa");
        src.put("abc_bbb", null);
        src.put("abc_ccc", " ");
        src.put("ddd", "ddd");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesWhitespace().excludesNull()
                .prefix(BeanNames.abc_()).execute();
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_null() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", null);
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).execute();
        assertEquals(2, dest.size());
        assertEquals("aaa", dest.get("aaa"));
        assertNull(dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_whitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).execute();
        assertEquals(2, dest.size());
        assertEquals("aaa", dest.get("aaa"));
        assertEquals(" ", dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_includesAndNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", null);
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("aaa", "bbb").execute();
        assertEquals(2, dest.size());
        assertEquals("aaa", dest.get("aaa"));
        assertNull(dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_includesAndWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("aaa", "bbb").execute();
        assertEquals(2, dest.size());
        assertEquals("aaa", dest.get("aaa"));
        assertEquals(" ", dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_includesAndExcludes() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("abc_bbb", "bbb");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("aaa").excludes("abc_bbb").execute();
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_includesAndExcludesWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("abc_bbb", " ");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("aaa").excludesWhitespace().execute();
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_includesAndExcludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("abc_bbb", null);
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("aaa").excludesNull().execute();
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_excludesAndPrefix() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("abc_bbb", "bbb");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludes("aaa").prefix("abc_").execute();
        assertEquals(1, dest.size());
        assertEquals("bbb", dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap_includesAndNullAndWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        src.put("ccc", null);
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).includes("aaa", "bbb", "ccc").execute();
        assertEquals(3, dest.size());
        assertEquals("aaa", dest.get("aaa"));
        assertEquals(" ", dest.get("bbb"));
        assertNull(dest.get("ccc"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCreateAndCopy_beanToBean() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).execute();
        assertEquals("aaa", dest.aaa);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCreateAndCopy_beanToMap() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).execute();
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCreateAndCopy_mapToBean() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        MyBean dest = new CreateAndCopy<MyBean>(MyBean.class, src).execute();
        assertEquals("aaa", dest.aaa);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCreateAndCopy_mapToMap() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).execute();
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * beanToBeanにおいて、src,destが同一の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_theSameBean() throws Exception {
        Bean1 src = new Bean1();
        src.aaa = "aaa";
        src.bbb = 1;
        src.ccc = (long)1234567890;
        src.ddd = true;
        src.nestBean = new NestBean("test");
        Bean1 dest = new CreateAndCopy<Bean1>(Bean1.class, src).execute();
        assertEquals("aaa", dest.aaa);
        assertEquals(1, dest.bbb);
        assertEquals(1234567890, dest.ccc);
        assertEquals(true, dest.ddd);
        assertEquals("test", dest.nestBean.aaa);
    }

    /**
     * beanToBeanにおいて、srcにしかないフィールドがある場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_theSpecifiedPropertiesFromSrc() throws Exception {
        Bean1 src = new Bean1();
        src.aaa = "aaa";
        src.bbb = 1;
        src.ccc = (long)1234567890;
        src.ddd = true;
        src.nestBean = new NestBean("test");
        Bean2 dest = new CreateAndCopy<Bean2>(Bean2.class, src).execute();
        assertEquals(1, dest.bbb);
        assertEquals(1234567890, dest.ccc);
        assertEquals(true, dest.ddd);
        assertEquals("test", dest.nestBean.aaa);
    }

    /**
     * beanToBeanにおいて、destにしかないフィールドがある場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_theSpecifiedPropertiesFromDest() throws Exception {
        Bean1 src = new Bean1();
        src.aaa = "aaa";
        src.bbb = 1;
        src.ccc = (long)1234567890;
        src.ddd = true;
        src.nestBean = new NestBean("test");
        Bean3 dest = new CreateAndCopy<Bean3>(Bean3.class, src).execute();
        dest.setEee(2);
        assertEquals("aaa", dest.aaa);
        assertEquals(1, dest.bbb);
        assertEquals(1234567890, dest.ccc);
        assertEquals(true, dest.ddd);
        assertEquals("test", dest.nestBean.aaa);
        assertEquals(2, dest.eee);
    }

    /**
     * beanToBeanにおいて、srcとdestでデータ型が違う場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_dataTypeIsDiff() throws Exception {
        Bean1 src = new Bean1();
        src.aaa = "aaa";
        src.bbb = 1;
        src.ccc = (long)1234567890;
        src.ddd = true;
        src.nestBean = new NestBean("test");
        Bean4 dest = new CreateAndCopy<Bean4>(Bean4.class, src).execute();
        assertNull(dest.aaa);
        assertEquals("1", dest.bbb);
        assertEquals("1234567890", dest.ccc);
        assertEquals("1", dest.ddd);
        assertEquals(src.nestBean.toString(), dest.nestBean);
    }

    /**
     * beanToBeanにおいて、大文字小文字の差の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_copyFromLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).execute();
        assertEquals(" ", dest.abc_value1);
        assertEquals(" ", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * mapToBeanにおいて、src,destが同一の場合
     */
    @Test
    public void testCreateAndCopy_mapToBean_theSameBean() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", new NestBean("test"));
        Bean1 dest = new CreateAndCopy<Bean1>(Bean1.class, src).execute();
        assertEquals("aaa", dest.aaa);
        assertEquals(1, dest.bbb);
        assertEquals(1234567890, dest.ccc);
        assertEquals(true, dest.ddd);
        assertEquals("test", dest.nestBean.aaa);
    }

    /**
     * mapToBeanにおいて、srcにしかないフィールドがある場合
     */
    @Test
    public void testCreateAndCopy_mapToBean_theSpecifiedPropertiesFromSrc() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", new NestBean("test"));

        Bean2 dest = new CreateAndCopy<Bean2>(Bean2.class, src).execute();
        assertEquals(1, dest.bbb);
        assertEquals(1234567890, dest.ccc);
        assertEquals(true, dest.ddd);
        assertEquals("test", dest.nestBean.aaa);
    }

    /**
     * mapToBeanにおいて、destにしかないフィールドがある場合
     */
    @Test
    public void testCreateAndCopy_mapToBean_theSpecifiedPropertiesFromDest() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", new NestBean("test"));

        Bean3 dest = new CreateAndCopy<Bean3>(Bean3.class, src).execute();
        assertEquals("aaa", dest.aaa);
        assertEquals(1, dest.bbb);
        assertEquals(1234567890, dest.ccc);
        assertEquals(true, dest.ddd);
        assertEquals("test", dest.nestBean.aaa);
        assertEquals(0, dest.eee);
    }

    /**
     * mapToBeanにおいて、srcとdestでデータ型が違う場合
     */
    @Test
    public void testCreateAndCopy_mapToBean_dataTypeIsDiff() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", new NestBean("test"));

        Bean4 dest = new CreateAndCopy<Bean4>(Bean4.class, src).execute();
        assertNull(dest.aaa);
        assertEquals("1", dest.bbb);
        assertEquals("1234567890", dest.ccc);
        assertEquals("1", dest.ddd);
        assertEquals(src.get("nestBean").toString(), dest.nestBean);
    }

    /**
     * mapToBeanにおいて、大文字小文字の差の場合
     */
    @Test
    public void testCreateAndCopy_mapToBean_copyFromLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("AAA", "aaa");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", new NestBean("test"));

        Bean1 dest = new CreateAndCopy<Bean1>(Bean1.class, src).execute();

        assertNull(dest.aaa);
        assertEquals(1, dest.bbb);
        assertEquals(1234567890, dest.ccc);
        assertEquals(true, dest.ddd);
        assertEquals("test", dest.nestBean.aaa);
    }


    /**
     * beanToMapにおいて、src,destが同一の場合
     */
    @Test
    public void testCreateAndCopy_beanToMap_theSameBean() throws Exception {
        Bean1 src = new Bean1();
        src.aaa = "aaa";
        src.bbb = 1;
        src.ccc = (long)1234567890;
        src.ddd = true;
        src.nestBean = new NestBean("test");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).execute();

        assertEquals("aaa", dest.get("aaa"));
        assertEquals(1, dest.get("bbb"));
        assertEquals((long)1234567890, dest.get("ccc"));
        assertEquals(true, dest.get("ddd"));
        assertEquals(src.nestBean, dest.get("nestBean"));
    }

    /**
     * mapToMapにおいて、src,destが同一の場合
     */
    @Test
    public void testCreateAndCopy_mapToMap_theSameBean() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", new NestBean("test"));
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).execute();

        assertEquals("aaa", dest.get("aaa"));
        assertEquals(1, dest.get("bbb"));
        assertEquals(1234567890, dest.get("ccc"));
        assertEquals(true, dest.get("ddd"));
        assertEquals(src.get("nestBean"), dest.get("nestBean"));
    }

    /**
     * beanToBeanにおいて、prefix対象フィールドが1個の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_prefixOnePropertie() throws Exception {
        Bean5 src = new Bean5();
        Bean7 dest = new CreateAndCopy<Bean7>(Bean7.class, src).prefix("xyz_").execute();
        assertEquals("test1", dest.value1);
        assertEquals("test2", dest.value2);
        assertEquals("xyz", dest.value);
    }

    /**
     * beanToBeanにおいて、prefix対象フィールドが複数の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_prefixMultipleProperties() throws Exception {
        Bean5 src = new Bean5();
        Bean7 dest = new CreateAndCopy<Bean7>(Bean7.class, src).prefix("abc_").execute();
        assertEquals(" ", dest.value1);
        assertEquals(" ", dest.value2);
        assertEquals("xyz2", dest.value);
    }

    /**
     * beanToBeanにおいて、prefix対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCreateAndCopy_beanToBean_prefixWithLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        Bean9 dest = new CreateAndCopy<Bean9>(Bean9.class, src).prefix("abc_").execute();
        assertEquals("test1", dest.valUE1);
        assertEquals("test2", dest.VAlue2);
        assertEquals("xyz2", dest.VALUE);
    }

    /**
     * beanToBeanにおいて、excludesWhitespace対象フィールドが1個の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_excludesWhitespaceOnePropertie() throws Exception {
        Bean5 src = new Bean5();
        src.setAbc_value1("value1");
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludesWhitespace().execute();
        assertEquals("value1", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * beanToBeanにおいて、excludesWhitespace対象フィールドが複数の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_excludesWhitespaceMultipleProperties() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new CreateAndCopy<>(Bean6.class, src).excludesWhitespace().execute();
        assertEquals("test1", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * beanToBeanにおいて、excludesWhitespace対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCreateAndCopy_beanToBean_excludesWhitespaceWithLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        src.setAb(" ");
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludesWhitespace().execute();
        assertEquals("test1", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * beanToBeanにおいて、excludesNull対象フィールドが1個の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_excludesNullOnePropertie() throws Exception {
        Bean5 src = new Bean5();
        src.setAbc_exclude1("value1");
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludesNull().execute();
        assertEquals(" ", dest.abc_value1);
        assertEquals(" ", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertEquals("value1", dest.abc_exclude1);
        assertEquals("abc2", dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * beanToBeanにおいて、excludesNull対象フィールドが複数の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_excludesNullMultipleProperties() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludesNull().execute();
        assertEquals(" ", dest.abc_value1);
        assertEquals(" ", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertEquals("abc1", dest.abc_exclude1);
        assertEquals("abc2", dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * beanToBeanにおいて、excludesNull対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCreateAndCopy_beanToBean_excludesNullWithLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        src.setAb(null);
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludesNull().execute();
        assertEquals(" ", dest.abc_value1);
        assertEquals(" ", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertEquals("abc1", dest.abc_exclude1);
        assertEquals("abc2", dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * beanToBeanにおいて、excludes対象フィールドが1個の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_excludesOnePropertie() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludes("abc_value1").execute();
        assertEquals("test1", dest.abc_value1);
        assertEquals(" ", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * beanToBeanにおいて、excludes対象フィールドが複数の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_excludesMultipleProperties() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludes("abc_value1", "abc_value2").execute();
        assertEquals("test1", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * beanToBeanにおいて、excludes対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCreateAndCopy_beanToBean_excludesWithLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludes("abc_value1", "abc_value2", "ab").execute() ;
        assertEquals("test1", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * beanToBeanにおいて、includes対象フィールドが1個の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_includesOnePropertie() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).includes("abc_value1").execute();
        assertEquals(" ", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz2", dest.xyz_value);
        assertEquals("other2", dest.other$fff );
        assertEquals("abc1", dest.abc_exclude1);
        assertEquals("abc2", dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * beanToBeanにおいて、includes対象フィールドが複数の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_includesMultipleProperties() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).includes("abc_value1", "abc_value2").execute() ;
        assertEquals(" ", dest.abc_value1);
        assertEquals(" ", dest.abc_value2);
        assertEquals("xyz2", dest.xyz_value);
        assertEquals("other2", dest.other$fff );
        assertEquals("abc1", dest.abc_exclude1);
        assertEquals("abc2", dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * beanToBeanにおいて、includes対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCreateAndCopy_beanToBean_includesWithLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).includes("abc_value1", "abc_value2", "ab").execute() ;
        assertEquals(" ", dest.abc_value1);
        assertEquals(" ", dest.abc_value2);
        assertEquals("xyz2", dest.xyz_value);
        assertEquals("other2", dest.other$fff );
        assertEquals("abc1", dest.abc_exclude1);
        assertEquals("abc2", dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * beanToBeanにおいて、ネストビーン対象フィールドが複数の場合
     */
    @Test
    public void testCreateAndCopy_beanToBean_nestBeanWithMultipleProperties() throws Exception {
        Bean10 src = new Bean10();
        src.nestBean1 = new NestBean("test1");
        src.nestBean2 = new NestBean("test2");
        Bean10 dest = new CreateAndCopy<Bean10>(Bean10.class, src).execute() ;
        assertEquals("test1", dest.nestBean1.getAaa());
        assertEquals("test2", dest.nestBean2.getAaa());
    }

    /**
     * beanToBeanにおいて、ネストビーン対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCreateAndCopy_beanToBean_nestBeanWithLowerUpperCase() throws Exception {
        Bean10 src = new Bean10();
        src.nestBean1 = new NestBean("test1");
        src.nestBean2 = new NestBean("test2");
        Bean11 dest = new CreateAndCopy<Bean11>(Bean11.class, src).execute() ;
        assertNull(dest.neStBeAn1);
        assertNull(dest.NESTBEAN2);
    }

    /**
     * beanToBeanの場合、excludesNullとexcludesWhitespaceの組み合わせ
     */
    @Test
    public void testCreateAndCopy_beanToBean_excludesWhitespaceAndExcludesNull() throws Exception {
        Bean1 src = new Bean1();
        src.setAaa("");
        src.setBbb(1);
        src.setCcc(0);
        src.setDdd(false);
        src.setNestBean(null);

        Bean1 dest = new CreateAndCopy<Bean1>(Bean1.class, src).excludesWhitespace().excludesNull().execute();
        assertNull(dest.getAaa());
        assertEquals(1, dest.getBbb());
        assertEquals(0, dest.getCcc());
        assertEquals(false, dest.isDdd());
        assertNull(dest.getNestBean());
    }

    /**
     * beanToMapの場合、excludesNullとexcludesWhitespaceの組み合わせ
     */
    @Test
    public void testCreateAndCopy_beanToMap_excludesWhitespaceAndExcludesNull() throws Exception {
        Bean1 src = new Bean1();
        src.setAaa("");
        src.setBbb(1);
        src.setCcc(0);
        src.setDdd(false);
        src.setNestBean(null);

        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesWhitespace().excludesNull().execute();
        assertEquals(3, dest.size());
        assertEquals(1, dest.get("bbb"));
        assertEquals((long)0, dest.get("ccc"));
        assertEquals(false, dest.get("ddd"));
    }

    /**
     * mapToBeanの場合、excludesNullとexcludesWhitespaceの組み合わせ
     */
    @Test
    public void testCreateAndCopy_mapToBean_excludesWhitespaceAndExcludesNull() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", null);

        Bean1 dest = new CreateAndCopy<Bean1>(Bean1.class, src).excludesWhitespace().excludesNull().execute();
        assertNull(dest.getAaa());
        assertEquals(1, dest.getBbb());
        assertEquals(1234567890, dest.getCcc());
        assertEquals(true, dest.isDdd());
        assertNull(dest.getNestBean());
    }

    /**
     * mapToMapの場合、excludesNullとexcludesWhitespaceの組み合わせ
     */
    @Test
    public void testCreateAndCopy_mapToMap_excludesWhitespaceAndExcludesNull() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", null);

        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesWhitespace().excludesNull().execute();
        assertEquals(3, dest.size());
        assertEquals(1, dest.get("bbb"));
        assertEquals(1234567890, dest.get("ccc"));
        assertEquals(true, dest.get("ddd"));
    }

    /**
     * beanToBeanの場合、prefix、excludesNullとexcludesWhitespaceの組み合わせ
     */
    @Test
    public void testCreateAndCopy_beanToBean_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
        Bean8 src = new Bean8();
        Bean7 dest = new CreateAndCopy<Bean7>(Bean7.class, src).excludesWhitespace().excludesNull()
                .prefix("other_").execute();
        assertEquals("test1", dest.value1);
        assertEquals("test2", dest.value2);
        assertEquals("test", dest.value);
    }

    /**
     * beanToMapの場合、prefix、excludesNullとexcludesWhitespaceの組み合わせ
     */
    @Test
    public void testCreateAndCopy_beanToMap_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
        Bean8 src = new Bean8();
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesWhitespace().excludesNull()
                .prefix("other_").execute();

        assertEquals(1, dest.size());
        assertEquals("test", dest.get("value"));
    }

    /**
     * mapToBeanの場合、prefix、excludesNullとexcludesWhitespaceの組み合わせ
     */
    @Test
    public void testCreateAndCopy_mapToBean_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("other_valUe1", "aaa");
        src.put("other_value2", null);
        src.put("other_value", " ");
        Bean7 dest = new CreateAndCopy<Bean7>(Bean7.class, src).excludesWhitespace().excludesNull()
                .prefix("other_").execute();
        assertEquals("test1", dest.value1);
        assertEquals("test2", dest.value2);
        assertEquals("xyz2", dest.value);
    }

    /**
     * mapToMapの場合、prefix、excludesNullとexcludesWhitespaceの組み合わせ
     */
    @Test
    public void testCreateAndCopy_mapToMap_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("other_value1", "aaa");
        src.put("other_value2", null);
        src.put("other_value", " ");
        BeanMap dest = new CreateAndCopy<BeanMap>(BeanMap.class, src).excludesWhitespace().excludesNull()
                .prefix("other_").execute();
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("value1"));
    }

    /**
     * mapToBeanの時、prefix対象フィールドが複数の場合
     */
    @Test
    public void testCreateAndCopy_mapToBean_prefixMultipleProperties() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_valUE1", " ");
        src.put("abc_VAlue2", "test");
        src.put("VALUE", "xyz");
        Bean9 dest = new CreateAndCopy<Bean9>(Bean9.class, src).prefix("abc_").execute() ;
        assertEquals(" ", dest.valUE1);
        assertEquals("test", dest.VAlue2);
        assertEquals("xyz2", dest.VALUE);
    }

    /**
     * mapToBeanの時、prefix対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCreateAndCopy_mapToBean_prefixWithLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", "test");
        src.put("abc_value", "xyz");
        Bean9 dest = new CreateAndCopy<Bean9>(Bean9.class, src).prefix("abc_").execute() ;
        assertEquals("test1", dest.valUE1);
        assertEquals("test2", dest.VAlue2);
        assertEquals("xyz2", dest.VALUE);
    }

    /**
     * mapToBeanの時、excludesWhitespace対象フィールドが複数の場合
     */
    @Test
    public void testCreateAndCopy_mapToBean_excludesWhitespaceMultipleProperties() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", " ");
        src.put("xyz_value", "xyz");
        src.put("other$fff", "other");
        src.put("abc_exclude1", null);
        src.put("abc_exclude2", null);
        src.put("ab", "ab");
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludesWhitespace().execute() ;
        assertEquals("test1", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * mapToBeanの時、excludesWhitespace対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCreateAndCopy_mapToBean_excludesWhitespaceWithLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", " ");
        src.put("xyz_value", "xyz");
        src.put("other$fff", "other");
        src.put("abc_exclude1", null);
        src.put("abc_exclude2", null);
        src.put("ab", " ");
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludesWhitespace().execute() ;
        assertEquals("test1", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * mapToBeanの時、excludesNull対象フィールドが複数の場合
     */
    @Test
    public void testCreateAndCopy_mapToBean_excludesNullMultipleProperties() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", " ");
        src.put("xyz_value", "xyz");
        src.put("other$fff", "other");
        src.put("abc_exclude1", null);
        src.put("abc_exclude2", null);
        src.put("ab", "ab");
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludesNull().execute() ;
        assertEquals(" ", dest.abc_value1);
        assertEquals(" ", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertEquals("abc1", dest.abc_exclude1);
        assertEquals("abc2", dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * mapToBeanの時、excludesNull対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCreateAndCopy_mapToBean_excludesNullWithLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", " ");
        src.put("xyz_value", "xyz");
        src.put("other$fff", "other");
        src.put("abc_exclude1", null);
        src.put("abc_exclude2", null);
        src.put("ab", null);
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludesNull().execute() ;
        assertEquals(" ", dest.abc_value1);
        assertEquals(" ", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertEquals("abc1", dest.abc_exclude1);
        assertEquals("abc2", dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * mapToBeanの時、excludes対象フィールドが複数の場合
     */
    @Test
    public void testCreateAndCopy_mapToBean_excludesMultipleProperties() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", " ");
        src.put("xyz_value", "xyz");
        src.put("other$fff", "other");
        src.put("abc_exclude1", null);
        src.put("abc_exclude2", null);
        src.put("ab", "ab");
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludes("abc_value1", "abc_value2").execute() ;
        assertEquals("test1", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * mapToBeanの時、excludes対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCreateAndCopy_mapToBean_excludesWithLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", " ");
        src.put("xyz_value", "xyz");
        src.put("other$fff", "other");
        src.put("abc_exclude1", null);
        src.put("abc_exclude2", null);
        src.put("ab", "ab");
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).excludes("abc_value1", "abc_value2", "ab").execute() ;
        assertEquals("test1", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * mapToBeanの時、includes対象フィールドが複数の場合
     */
    @Test
    public void testCreateAndCopy_mapToBean_includesMultipleProperties() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", " ");
        src.put("xyz_value", "xyz");
        src.put("other$fff", "other");
        src.put("abc_exclude1", null);
        src.put("abc_exclude2", null);
        src.put("ab", "ab");
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).includes("abc_value1", "abc_value2").execute() ;
        assertEquals(" ", dest.abc_value1);
        assertEquals(" ", dest.abc_value2);
        assertEquals("xyz2", dest.xyz_value);
        assertEquals("other2", dest.other$fff );
        assertEquals("abc1", dest.abc_exclude1);
        assertEquals("abc2", dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * mapToBeanの時、includes対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCreateAndCopy_mapToBean_includesWithLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", " ");
        src.put("xyz_value", "xyz");
        src.put("other$fff", "other");
        src.put("abc_exclude1", null);
        src.put("abc_exclude2", null);
        src.put("ab", "ab");
        Bean6 dest = new CreateAndCopy<Bean6>(Bean6.class, src).includes("abc_value1", "abc_value2", "ab").execute() ;
        assertEquals(" ", dest.abc_value1);
        assertEquals(" ", dest.abc_value2);
        assertEquals("xyz2", dest.xyz_value);
        assertEquals("other2", dest.other$fff );
        assertEquals("abc1", dest.abc_exclude1);
        assertEquals("abc2", dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }


}