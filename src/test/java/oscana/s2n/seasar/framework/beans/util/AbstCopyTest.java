package oscana.s2n.seasar.framework.beans.util;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * {@link AbstractCopy}のテスト。
 *
 */
public class AbstCopyTest {

    /**
     * @throws Exception
     */
    @Test
    public void testIncludes() throws Exception {
        MyCopy copy = new MyCopy();
        assertSame(copy, copy.includes(BeanNames.hoge()));
        assertEquals(1, copy.includePropertyNames.length);
        assertEquals("hoge", copy.includePropertyNames[0]);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExcludes() throws Exception {
        MyCopy copy = new MyCopy();
        assertSame(copy, copy.excludes(BeanNames.hoge()));
        assertEquals(1, copy.excludePropertyNames.length);
        assertEquals("hoge", copy.excludePropertyNames[0]);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testPrefix() throws Exception {
        MyCopy copy = new MyCopy();
        assertSame(copy, copy.prefix(BeanNames.search_()));
        assertEquals("search_", copy.prefix);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testBeanDelimiter() throws Exception {
        MyCopy copy = new MyCopy();
        assertSame(copy, copy.beanDelimiter('#'));
        assertEquals('#', copy.beanDelimiter);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testMapDelimiter() throws Exception {
        MyCopy copy = new MyCopy();
        assertSame(copy, copy.mapDelimiter('#'));
        assertEquals('#', copy.mapDelimiter);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty() throws Exception {
        MyCopy copy = new MyCopy();
        assertTrue(copy.isTargetProperty("hoge"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty_includes() throws Exception {
        MyCopy copy = new MyCopy().includes(BeanNames.hoge());
        assertTrue(copy.isTargetProperty("hoge"));
        assertFalse(copy.isTargetProperty("hoge2"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty_includes_prefix() throws Exception {
        MyCopy copy = new MyCopy().includes(BeanNames.search_aaa(),
                BeanNames.bbb()).prefix(BeanNames.search_());
        assertTrue(copy.isTargetProperty("search_aaa"));
        assertFalse(copy.isTargetProperty("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty_excludes() throws Exception {
        MyCopy copy = new MyCopy().excludes(BeanNames.hoge());
        assertFalse(copy.isTargetProperty("hoge"));
        assertTrue(copy.isTargetProperty("hoge2"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty_excludes_prefix() throws Exception {
        MyCopy copy = new MyCopy().prefix(BeanNames.abc_()).excludes(
                BeanNames.abc_exclude());
        assertTrue(copy.isTargetProperty("abc_value"));
        assertFalse(copy.isTargetProperty("abc_exclude"));
        assertFalse(copy.isTargetProperty("ab"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty_prefix() throws Exception {
        MyCopy copy = new MyCopy().prefix(BeanNames.search_());
        assertTrue(copy.isTargetProperty("search_aaa"));
        assertFalse(copy.isTargetProperty("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty_includes_excludes() throws Exception {
        MyCopy copy = new MyCopy();
        copy.includes(BeanNames.hoge(), BeanNames.hoge2());
        copy.excludes(BeanNames.hoge2(), BeanNames.hoge3());
        assertTrue(copy.isTargetProperty("hoge"));
        assertFalse(copy.isTargetProperty("hoge2"));
        assertFalse(copy.isTargetProperty("hoge3"));
        assertFalse(copy.isTargetProperty("hoge4"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testTrimPrefix() throws Exception {
        MyCopy copy = new MyCopy();
        assertEquals("aaa", copy.trimPrefix("aaa"));
        copy.prefix(BeanNames.search_());
        assertEquals("aaa", copy.trimPrefix("search_aaa"));
        assertEquals("aaa_aaa", copy.trimPrefix("aaa_aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean() throws Exception {
        SrcBean src = new SrcBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        src.ccc = "ccc";
        src.eee = "1";
        DestBean dest = new DestBean();
        new MyCopy().copyBeanToBean(src, dest);
        assertEquals("bbb", dest.bbb);
        assertEquals("ccc", dest.ccc);
        assertNull(dest.ddd);
        assertEquals(1, dest.eee);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_testNestBean() throws Exception {
        SrcBean src = new SrcBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        src.ccc = "ccc";
        src.eee = "1";
        src.nestBean = new NestBean("test1", "test2");
        src.bean = null;
        DestBean dest = new DestBean();
        new MyCopy().copyBeanToBean(src, dest);
        assertEquals("bbb", dest.bbb);
        assertEquals("ccc", dest.ccc);
        assertNull(dest.ddd);
        assertEquals(1, dest.eee);
        assertEquals("test1", dest.nestBean.getTest1());
        assertEquals("test2", dest.nestBean.getTest2());
        assertNull(dest.bean);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_includes() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        MyBean dest = new MyBean();
        new MyCopy().includes(BeanNames.aaa()).copyBeanToBean(src, dest);
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_excludes() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        MyBean dest = new MyBean();
        new MyCopy().excludes(BeanNames.bbb()).copyBeanToBean(src, dest);
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_null() throws Exception {
        SrcBean src = new SrcBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        src.ccc = null;
        DestBean dest = new DestBean();
        dest.ccc = "ccc";
        new MyCopy().copyBeanToBean(src, dest);
        assertNull(dest.ccc);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_excludesNull() throws Exception {
        SrcBean src = new SrcBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        src.ccc = null;
        DestBean dest = new DestBean();
        dest.ccc = "ccc";
        new MyCopy().excludesNull().copyBeanToBean(src, dest);
        assertEquals("ccc", dest.ccc);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_whitespace() throws Exception {
        SrcBean src = new SrcBean();
        src.ccc = " ";
        DestBean dest = new DestBean();
        new MyCopy().copyBeanToBean(src, dest);
        assertEquals(" ", dest.ccc);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_excludesWhitespace() throws Exception {
        SrcBean src = new SrcBean();
        src.bbb = " ";
        src.ccc = " ";
        DestBean dest = new DestBean();
        dest.ccc = "ccc";
        dest.bbb = "bbb";
        new MyCopy().excludesWhitespace().copyBeanToBean(src, dest);
        assertEquals("ccc", dest.ccc);
        assertEquals("bbb", dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_prefix() throws Exception {
        SrcBean src = new SrcBean();
        src.search_eee$fff = "hoge";
        src.search_noGet = "noGet";
        DestBean dest = new DestBean();
        new MyCopy().prefix(BeanNames.search_()).copyBeanToBean(src, dest);
        assertEquals("hoge", dest.eee$fff);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_includesAndNull() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = null;
        MyBean dest = new MyBean();
        new MyCopy().includes(BeanNames.aaa(), BeanNames.bbb()).copyBeanToBean(src, dest);
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_includesAndWhitespace() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = " ";
        MyBean dest = new MyBean();
        new MyCopy().includes(BeanNames.aaa(), BeanNames.bbb()).copyBeanToBean(src, dest);
        assertEquals("aaa", dest.aaa);
        assertEquals(" ", dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_excludesWhitespaceAndExcludesNull() throws Exception {
        MyBean5 src = new MyBean5();
        MyBean3 dest = new MyBean3();
        new MyCopy().excludesWhitespace().excludesNull().copyBeanToBean(src, dest);
        assertEquals("abc", dest.abc_value);
        assertEquals("exclude", dest.abc_exclude);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_beanToBean_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
        MyBean5 src = new MyBean5();
        src.setAbc_value("value");
        src.setAb(null);
        MyBean4 dest = new MyBean4();
        new MyCopy().excludesWhitespace().excludesNull().prefix(BeanNames.abc_()).copyBeanToBean(src, dest);
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
    public void testCopyBeanToBean_includesAndExcludesWhitespace() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = " ";
        MyBean dest = new MyBean();
        new MyCopy().includes("aaa").excludesWhitespace().copyBeanToBean(src, dest);
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_includesAndExcludesNull() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = null;
        MyBean dest = new MyBean();
        new MyCopy().includes("aaa").excludesNull().copyBeanToBean(src, dest);
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_excludesAndPrefix() throws Exception {
        MyBean3 src = new MyBean3();
        MyBean4 dest = new MyBean4();
        new MyCopy().excludes("abc_exclude").prefix(BeanNames.abc_()).copyBeanToBean(src, dest);
        assertEquals("abc", dest.value);
        assertEquals("exclude11111", dest.exclude);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_includesAndNullAndWhitespace() throws Exception {
        MyBean5 src = new MyBean5();
        MyBean3 dest = new MyBean3();
        new MyCopy().includes("ab", "abc_value", "abc_exclude").copyBeanToBean(src, dest);
        assertEquals(" ", dest.abc_value);
        assertEquals("ab", dest.ab);
        assertNull(dest.abc_exclude);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap() throws Exception {
        SrcBean src = new SrcBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        src.ccc = "ccc";
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().copyBeanToMap(src, dest);
        assertEquals("aaa", dest.get("aaa"));
        assertEquals("bbb", dest.get("bbb"));
        assertEquals("ccc", dest.get("ccc"));
        assertNull(dest.get("ddd"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_includes() throws Exception {
        SrcBean src = new SrcBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        src.ccc = "ccc";
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes(BeanNames.aaa()).copyBeanToMap(src, dest);
        assertEquals("aaa", dest.get("aaa"));
        assertNull(dest.get("ccc"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_excludes() throws Exception {
        SrcBean src = new SrcBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        src.ccc = "ccc";
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().excludes(BeanNames.ccc()).copyBeanToMap(src, dest);
        assertEquals("aaa", dest.get("aaa"));
        assertNull(dest.get("ccc"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_null() throws Exception {
        SrcBean src = new SrcBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        src.ccc = null;
        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("ccc", "ccc");
        new MyCopy().copyBeanToMap(src, dest);
        assertNull(dest.get("ccc"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_excludesNull() throws Exception {
        SrcBean src = new SrcBean();
        src.aaa = "aaa";
        src.bbb = "bbb";
        src.ccc = null;
        src.ddd_noGet = "ddd";
        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("ccc", "ccc");
        new MyCopy().excludesNull().copyBeanToMap(src, dest);
        assertEquals("ccc", dest.get("ccc"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_whitespace() throws Exception {
        SrcBean src = new SrcBean();
        src.ccc = " ";
        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("ccc", "ccc");
        new MyCopy().copyBeanToMap(src, dest);
        assertEquals(" ", dest.get("ccc"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_excludesWhitespace() throws Exception {
        SrcBean src = new SrcBean();
        src.ccc = " ";
        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("ccc", "ccc");
        new MyCopy().excludesWhitespace().copyBeanToMap(src, dest);
        assertEquals("ccc", dest.get("ccc"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_prefix() throws Exception {
        SrcBean src = new SrcBean();
        src.search_eee$fff = "hoge";
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().prefix(BeanNames.search_()).copyBeanToMap(src, dest);
        assertEquals("hoge", dest.get("eee.fff"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_includeAndNull() throws Exception {
        MyBean5 src = new MyBean5();
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes("abc_exclude", "other$fff").copyBeanToMap(src, dest);
        assertEquals(2, dest.size());
        assertNull(dest.get("abc_exclude"));
        assertEquals("other", dest.get("other.fff"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_includeAndWhitespace() throws Exception {
        MyBean5 src = new MyBean5();
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes("abc_value", "other$fff").copyBeanToMap(src, dest);
        assertEquals(2, dest.size());
        assertEquals(" ", dest.get("abc_value"));
        assertEquals("other", dest.get("other.fff"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_includesExcludes() throws Exception {
        MyBean3 src = new MyBean3();
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes("ab", "abc_exclude").excludes("abc_exclude").copyBeanToMap(src, dest);
        assertEquals("ab", dest.get("ab"));
        assertNull(dest.get("abc_exclude"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_includesAndExcludesWhitespace() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = " ";
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes("aaa").excludesWhitespace().copyBeanToMap(src, dest);
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_ExcludesWhitespaceAndexcludesNull() throws Exception {
        MyBean src = new MyBean();
        src.aaa = null;
        src.bbb = " ";
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().excludesWhitespace().excludesNull().copyBeanToMap(src, dest);
        assertEquals(0, dest.size());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
        MyBean5 src = new MyBean5();
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().excludesWhitespace().excludesNull().prefix(BeanNames.abc_()).copyBeanToMap(src, dest);
        assertEquals(0, dest.size());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_includesAndExcludesNull() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        src.bbb = null;
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes("aaa").excludesNull().copyBeanToMap(src, dest);
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyBeanToMap_excludesAndPrefix() throws Exception {
        MyBean3 src = new MyBean3();
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().excludes("abc_exclude").prefix(BeanNames.abc_()).copyBeanToMap(src, dest);
        assertEquals(1, dest.size());
        assertEquals("abc", dest.get("value"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put("bbb", "bbb");
        src.put("ccc", "ccc");
        DestBean dest = new DestBean();
        new MyCopy().copyMapToBean(src, dest);
        assertEquals("bbb", dest.bbb);
        assertEquals("ccc", dest.ccc);
        assertNull(dest.ddd);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_testNestBean() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put("bbb", "bbb");
        src.put("ccc", "ccc");
        src.put("nestBean", new NestBean("test1", "test2"));
        src.put("bean1", new Bean1("testBean1"));
        DestBean dest = new DestBean();
        new MyCopy().copyMapToBean(src, dest);
        assertEquals("bbb", dest.bbb);
        assertEquals("ccc", dest.ccc);
        assertNull(dest.ddd);
        assertEquals("test1", dest.nestBean.getTest1());
        assertEquals("test2", dest.nestBean.getTest2());
        assertEquals("testBean1", dest.bean1.getTestBean1());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_includes() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put("bbb", "bbb");
        src.put("ccc", "ccc");
        DestBean dest = new DestBean();
        new MyCopy().includes(BeanNames.bbb()).copyMapToBean(src, dest);
        assertEquals("bbb", dest.bbb);
        assertNull(dest.ccc);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_excludes() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put("bbb", "bbb");
        src.put("ccc", "ccc");
        DestBean dest = new DestBean();
        new MyCopy().excludes(BeanNames.ccc()).copyMapToBean(src, dest);
        assertEquals("bbb", dest.bbb);
        assertNull(dest.ccc);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_null() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put("bbb", "bbb");
        src.put("ccc", null);
        DestBean dest = new DestBean();
        dest.ccc = "ccc";
        new MyCopy().copyMapToBean(src, dest);
        assertNull(dest.ccc);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_excludesNull() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put("bbb", "bbb");
        src.put("ccc", null);
        DestBean dest = new DestBean();
        dest.ccc = "ccc";
        new MyCopy().excludesNull().copyMapToBean(src, dest);
        assertEquals("ccc", dest.ccc);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_whitespace() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("ccc", " ");
        DestBean dest = new DestBean();
        dest.ccc = "ccc";
        new MyCopy().copyMapToBean(src, dest);
        assertEquals(" ", dest.ccc);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_excludesWhitespace() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("ccc", " ");
        src.put("ddd", " ");
        DestBean dest = new DestBean();
        dest.ccc = "ccc";
        dest.ddd = "ddd";
        new MyCopy().excludesWhitespace().copyMapToBean(src, dest);
        assertEquals("ccc", dest.ccc);
        assertEquals("ddd", dest.ddd);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_excludesWhitespaceAndexcludesNull() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        src.put("ccc", null);
        src.put("ddd", "ddd");
        DestBean dest = new DestBean();
        dest.bbb = "bbb";
        dest.ccc = "ccc";
        new MyCopy().excludesWhitespace().excludesNull().copyMapToBean(src, dest);
        assertEquals("bbb", dest.bbb);
        assertEquals("ccc", dest.ccc);
        assertEquals("ddd", dest.ddd);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("abc_value", "aaa");
        src.put("ab", null);
        src.put("abc_exclude", " ");
        src.put("xyz_value", "value");
        MyBean4 dest = new MyBean4();
        new MyCopy().excludesWhitespace().excludesNull().prefix(BeanNames.abc_()).copyMapToBean(src, dest);
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
    public void testCopyMapToBean_prefix() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("search_eee.fff", "hoge");
        DestBean dest = new DestBean();
        new MyCopy().prefix(BeanNames.search_()).copyMapToBean(src, dest);
        assertEquals("hoge", dest.eee$fff);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_includesAndNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", null);
        MyBean dest = new MyBean();
        new MyCopy().includes("aaa", "bbb").copyMapToBean(src, dest);
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_includesAndWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        MyBean dest = new MyBean();
        new MyCopy().includes("aaa", "bbb").copyMapToBean(src, dest);
        assertEquals("aaa", dest.aaa);
        assertEquals(" ", dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_includesAndExcludes() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", "bbb");
        MyBean dest = new MyBean();
        new MyCopy().includes("aaa").excludes("bbb").copyMapToBean(src, dest);
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_includesAndExcludesWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        MyBean dest = new MyBean();
        new MyCopy().includes("aaa").excludesWhitespace().copyMapToBean(src, dest);
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_includesAndExcludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", null);
        MyBean dest = new MyBean();
        new MyCopy().includes("aaa").excludesNull().copyMapToBean(src, dest);
        assertEquals("aaa", dest.aaa);
        assertNull(dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_excludesAndPrefix() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("abc_bbb", "bbb");
        MyBean dest = new MyBean();
        new MyCopy().excludes("aaa").prefix("abc_").copyMapToBean(src, dest);
        assertEquals("bbb", dest.bbb);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToBean_includesAndNullAndWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("abc_value", null);
        src.put("xyz_value", " ");
        src.put("ab", "testab");
        MyBean3 dest = new MyBean3();
        new MyCopy().includes("ab", "xyz_value", "abc_value").copyMapToBean(src, dest);
        assertNull(dest.getAbc_value());
        assertEquals(" ", dest.getXyz_value());
        assertEquals("testab", dest.getAb());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put("bbb", new Date(0));
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().copyMapToMap(src, dest);
        assertEquals("aaa", dest.get("aaa"));
        assertEquals(new Date(0), dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_includes() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put("bbb", "bbb");
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes(BeanNames.aaa()).copyMapToMap(src, dest);
        assertEquals("aaa", dest.get("aaa"));
        assertNull(dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_excludes() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put("bbb", "bbb");
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().excludes(BeanNames.bbb()).copyMapToMap(src, dest);
        assertEquals("aaa", dest.get("aaa"));
        assertNull(dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_null() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put("bbb", null);
        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("bbb", "bbb");
        new MyCopy().copyMapToMap(src, dest);
        assertNull(dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_excludesNull() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put("bbb", null);
        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("bbb", "bbb");
        new MyCopy().excludesNull().copyMapToMap(src, dest);
        assertEquals("bbb", dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_whitespace() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("bbb", " ");
        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("bbb", "bbb");
        new MyCopy().copyMapToMap(src, dest);
        assertEquals(" ", dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_excludesWhitespace() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("bbb", " ");
        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("bbb", "bbb");
        new MyCopy().excludesWhitespace().copyMapToMap(src, dest);
        assertEquals("bbb", dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_prefix() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("search_eee.fff", "hoge");
        src.put("aaa", "test");
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().prefix(BeanNames.search_()).copyMapToMap(src, dest);
        assertEquals("hoge", dest.get("eee.fff"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_excludesWhitespaceAndexcludesNull() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", null);
        src.put("bbb", " ");
        src.put("ccc", "wed");
        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("aaa", "aaa");
        dest.put("bbb", "bbb");
        dest.put("ccc", "ccc");
        new MyCopy().excludesWhitespace().excludesNull().copyMapToMap(src, dest);
        assertEquals("aaa", dest.get("aaa"));
        assertEquals("bbb", dest.get("bbb"));
        assertEquals("wed", dest.get("ccc"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("abc_aaa", "aaa");
        src.put("abc_bbb", null);
        src.put("abc_ccc", " ");
        src.put("ddd", "ddd");
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().excludesWhitespace().excludesNull().prefix(BeanNames.abc_()).copyMapToMap(src, dest);
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_includesAndNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", null);
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes("aaa", "bbb").copyMapToMap(src, dest);
        assertEquals(2, dest.size());
        assertEquals("aaa", dest.get("aaa"));
        assertNull(dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_includesAndWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes("aaa", "bbb").copyMapToMap(src, dest);
        assertEquals(2, dest.size());
        assertEquals("aaa", dest.get("aaa"));
        assertEquals(" ", dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_includesAndExcludes() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("abc_bbb", "bbb");
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes("aaa").excludes("abc_bbb").copyMapToMap(src, dest);
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_includesAndExcludesWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("abc_bbb", " ");
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes("aaa").excludesWhitespace().copyMapToMap(src, dest);
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_includesAndExcludesNull() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("abc_bbb", null);
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes("aaa").excludesNull().copyMapToMap(src, dest);
        assertEquals(1, dest.size());
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_excludesAndPrefix() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("abc_bbb", "bbb");
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().excludes("aaa").prefix("abc_").copyMapToMap(src, dest);
        assertEquals(1, dest.size());
        assertEquals("bbb", dest.get("bbb"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopyMapToMap_includesAndNullAndWhitespace() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        src.put("bbb", " ");
        src.put("ccc", null);
        Map<String, Object> dest = new HashMap<String, Object>();
        new MyCopy().includes("aaa", "bbb", "ccc").copyMapToMap(src, dest);
        assertEquals(3, dest.size());
        assertEquals("aaa", dest.get("aaa"));
        assertEquals(" ", dest.get("bbb"));
        assertNull(dest.get("ccc"));
    }

    /**
     * 項目一致しないBeanのコピー
     * @throws Exception
     */
    @Test
    public void testCopyBeanToBean_notExistProp() throws Exception {
        MyBean6 src = new MyBean6();
        src.setAaa("abc");
        src.setBbb("bcd");
        src.setMyBean2(new MyBean2("a","b"));

        MyBean dest = new MyCopy().createAndCopyBeanToBean(src, MyBean.class);
        assertEquals("abc", dest.getAaa());
        assertEquals("bcd", dest.getBbb());
    }

    /**
     * テスト用クラス
     */
    private static class MyCopy extends AbstractCopy<MyCopy> {

    }

    /**
     * テスト用クラス
     */
    public static class SrcBean {

        private String aaa;

        private String bbb;

        private String ccc;

        public String eee;

        public String search_eee$fff;

        public String search_noGet;

        public String ddd_noGet;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public void setBbb(String bbb) {
            this.bbb = bbb;
        }

        public String getCcc() {
            return ccc;
        }

        public String getEee() {
            return eee;
        }

        public void setEee(String eee) {
            this.eee = eee;
        }

        public String getSearch_eee$fff() {
            return search_eee$fff;
        }

        public void setSearch_eee$fff(String search_eee$fff) {
            this.search_eee$fff = search_eee$fff;
        }

        public void setSearch_noGet(String search_noGet) {
            this.search_noGet = search_noGet;
        }

        public String getBbb() {
            return bbb;
        }

        public void setCcc(String ccc) {
            this.ccc = ccc;
        }

        public void setDdd_noGet(String ddd_noGet) {
            this.ddd_noGet = ddd_noGet;
        }

        public NestBean nestBean;

        public NestBean getNestBean() {
            return nestBean;
        }

        public void setNestBean(NestBean nestBean) {
            this.nestBean = nestBean;
        }

        public Bean bean;

        public void setBean(Bean bean) {
            this.bean = bean;
        }

        public Bean1 bean1;

        public Bean1 getBean1() {
            return bean1;
        }

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }
    }

    class NestBean {
        public String test1;
        public String test2;

        public NestBean(String test1, String test2) {
            this.test1 = test1;
            this.test2 = test2;
        }

        public String getTest1() {
            return test1;
        }

        public String getTest2() {
            return test2;
        }

    }

    /**
     * テスト用クラス
     */
    public static class DestBean {

        private String bbb;

        private String ccc;

        private String ddd;

        public int eee;

        public String eee$fff;

        public void setBbb(String bbb) {
            this.bbb = bbb;
        }

        public void setCcc(String ccc) {
            this.ccc = ccc;
        }

        public String getDdd() {
            return ddd;
        }

        public void setDdd(String ddd) {
            this.ddd = ddd;
        }

        public int getEee() {
            return eee;
        }

        public void setEee(int eee) {
            this.eee = eee;
        }

        public String getEee$fff() {
            return eee$fff;
        }

        public void setEee$fff(String eee$fff) {
            this.eee$fff = eee$fff;
        }

        public String getBbb() {
            return bbb;
        }

        public String getCcc() {
            return ccc;
        }

        public NestBean nestBean;

        public NestBean getNestBean() {
            return nestBean;
        }

        public void setNestBean(NestBean nestBean) {
            this.nestBean = nestBean;
        }

        public Bean bean;

        public Bean getBean() {
            return bean;
        }

        public void setBean(Bean bean) {
            this.bean = bean;
        }

        public Bean1 bean1;

        public Bean1 getBean1() {
            return bean1;
        }

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }
    }

    /**
     * テスト用クラス
     */
    public class Bean {
    }

    /**
     * テスト用クラス
     */
    public class Bean1 {
        public String testBean1;

        public Bean1(String testBean1) {
            this.testBean1 = testBean1;
        }

        public String getTestBean1() {
            return testBean1;
        }
    }

    /**
     * テスト用クラス
     *
     */
    public static class BeanNames {

        /**
         * CharSequenceを作成します。
         *
         * @param name
         * @return
         */
        protected static CharSequence createCharSequence(final String name) {
            return new CharSequence() {

                @Override
                public String toString() {
                    return name;
                }

                public char charAt(int index) {
                    return name.charAt(index);
                }

                public int length() {
                    return name.length();
                }

                public CharSequence subSequence(int start, int end) {
                    return name.subSequence(start, end);
                }

            };
        }

        /**
         * @return
         */
        public static CharSequence aaa() {
            return createCharSequence("aaa");
        }

        /**
         * @return
         */
        public static CharSequence bbb() {
            return createCharSequence("bbb");
        }

        /**
         * @return
         */
        public static CharSequence ccc() {
            return createCharSequence("ccc");
        }

        /**
         * @return
         */
        public static CharSequence hoge() {
            return createCharSequence("hoge");
        }

        /**
         * @return
         */
        public static CharSequence hoge2() {
            return createCharSequence("hoge2");
        }

        /**
         * @return
         */
        public static CharSequence hoge3() {
            return createCharSequence("hoge3");
        }

        /**
         * @return
         */
        public static CharSequence search_aaa() {
            return createCharSequence("search_aaa");
        }

        /**
         * @return
         */
        public static CharSequence abc_exclude() {
            return createCharSequence("abc_exclude");
        }

        /**
         * @return
         */
        public static CharSequence search_() {
            return createCharSequence("search_");
        }

        /**
         * @return
         */
        public static CharSequence abc_() {
            return createCharSequence("abc_");
        }

    }

}
