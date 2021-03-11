package oscana.s2n.seasar.framework.beans.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * {@link Copy}のテスト。
 *
 */
public class CopyTest {

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToBean() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        MyBean dest = new MyBean();
        new Copy(src, dest).execute();
        assertEquals("aaa", dest.aaa);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_beanToMap() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        Map<String, Object> dest = new HashMap<String, Object>();
        new Copy(src, dest).execute();
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToBean() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        MyBean dest = new MyBean();
        new Copy(src, dest).execute();
        assertEquals("aaa", dest.aaa);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute_mapToMap() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        Map<String, Object> dest = new HashMap<String, Object>();
        new Copy(src, dest).execute();
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopy_srcThrowException() {
        MyBean src = null;
        MyBean dest = new MyBean();
        try {
            new Copy(src, dest);
        } catch (Throwable e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("src", e.getMessage());
        }
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopy_destThrowException() {
        MyBean src = new MyBean();
        MyBean dest = null;
        try {
            new Copy(src, dest);
        } catch (Throwable e) {
            assertTrue(e instanceof NullPointerException);
            assertEquals("dest", e.getMessage());
        }
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopy_beanToBean() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        MyBean dest = new MyBean();
        new Copy(src, dest).execute();
        assertEquals("aaa", dest.aaa);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopy_beanToMap() throws Exception {
        MyBean src = new MyBean();
        src.aaa = "aaa";
        BeanMap dest = new BeanMap();
        new Copy(src, dest).execute();
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopy_mapToBean() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        MyBean dest = new MyBean();
        new Copy(src, dest).execute();
        assertEquals("aaa", dest.aaa);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testCopy_mapToMap() throws Exception {
        BeanMap src = new BeanMap();
        src.put("aaa", "aaa");
        BeanMap dest = new BeanMap();
        new Copy(src, dest).execute();
        assertEquals("aaa", dest.get("aaa"));
    }

    /**
     * beanToBeanにおいて、src,destが同一の場合
     */
    @Test
    public void testCopy_beanToBean_theSameBean() throws Exception {
        Bean1 src = new Bean1();
        src.aaa = "aaa";
        src.bbb = 1;
        src.ccc = (long)1234567890;
        src.ddd = true;
        src.nestBean = new NestBean("test");
        Bean1 dest = new Bean1();
        new Copy(src, dest).execute();
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
    public void testCopy_beanToBean_theSpecifiedPropertiesFromSrc() throws Exception {
        Bean1 src = new Bean1();
        src.aaa = "aaa";
        src.bbb = 1;
        src.ccc = (long)1234567890;
        src.ddd = true;
        src.nestBean = new NestBean("test");
        Bean2 dest = new Bean2();
        new Copy(src, dest).execute();
        assertEquals(1, dest.bbb);
        assertEquals(1234567890, dest.ccc);
        assertEquals(true, dest.ddd);
        assertEquals("test", dest.nestBean.aaa);
    }

    /**
     * beanToBeanにおいて、destにしかないフィールドがある場合
     */
    @Test
    public void testCopy_beanToBean_theSpecifiedPropertiesFromDest() throws Exception {
        Bean1 src = new Bean1();
        src.aaa = "aaa";
        src.bbb = 1;
        src.ccc = (long)1234567890;
        src.ddd = true;
        src.nestBean = new NestBean("test");
        Bean3 dest = new Bean3();
        dest.setEee(2);
        new Copy(src, dest).execute();
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
    public void testCopy_beanToBean_dataTypeIsDiff() throws Exception {
        Bean1 src = new Bean1();
        src.aaa = "aaa";
        src.bbb = 1;
        src.ccc = (long)1234567890;
        src.ddd = true;
        src.nestBean = new NestBean("test");
        Bean4 dest = new Bean4();
        new Copy(src, dest).execute();
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
    public void testCopy_beanToBean_copyFromLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new Bean6();
        new Copy(src, dest).execute();
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
    public void testCopy_mapToBean_theSameBean() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", new NestBean("test"));

        Bean1 dest = new Bean1();
        new Copy(src, dest).execute();
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
    public void testCopy_mapToBean_theSpecifiedPropertiesFromSrc() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", new NestBean("test"));

        Bean2 dest = new Bean2();
        new Copy(src, dest).execute();
        assertEquals(1, dest.bbb);
        assertEquals(1234567890, dest.ccc);
        assertEquals(true, dest.ddd);
        assertEquals("test", dest.nestBean.aaa);
    }

    /**
     * mapToBeanにおいて、destにしかないフィールドがある場合
     */
    @Test
    public void testCopy_mapToBean_theSpecifiedPropertiesFromDest() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", new NestBean("test"));

        Bean3 dest = new Bean3();
        dest.setEee(123);
        new Copy(src, dest).execute();
        assertEquals("aaa", dest.aaa);
        assertEquals(1, dest.bbb);
        assertEquals(1234567890, dest.ccc);
        assertEquals(true, dest.ddd);
        assertEquals("test", dest.nestBean.aaa);
        assertEquals(123, dest.eee);
    }

    /**
     * mapToBeanにおいて、srcとdestでデータ型が違う場合
     */
    @Test
    public void testCopy_mapToBean_dataTypeIsDiff() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("aaa", "aaa");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", new NestBean("test"));

        Bean4 dest = new Bean4();
        new Copy(src, dest).execute();
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
    public void testCopy_mapToBean_copyFromLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("AAA", "aaa");
        src.put( "bbb", 1);
        src.put( "ccc", 1234567890);
        src.put( "ddd", true);
        src.put( "nestBean", new NestBean("test"));

        Bean1 dest = new Bean1();
        new Copy(src, dest).execute();

        assertNull(dest.aaa);
        assertEquals(1, dest.bbb);
        assertEquals(1234567890, dest.ccc);
        assertEquals(true, dest.ddd);
        assertEquals("test", dest.nestBean.aaa);
    }

    /**
     * beanToBeanにおいて、prefix対象フィールドが1個の場合
     */
    @Test
    public void testCopy_beanToBean_prefixOnePropertie() throws Exception {
        Bean5 src = new Bean5();
        Bean7 dest = new Bean7();
        new Copy(src, dest).prefix("xyz_").execute() ;
        assertEquals("test1", dest.value1);
        assertEquals("test2", dest.value2);
        assertEquals("xyz", dest.value);
    }

    /**
     * beanToBeanにおいて、prefix対象フィールドが複数の場合
     */
    @Test
    public void testCopy_beanToBean_prefixMultipleProperties() throws Exception {
        Bean5 src = new Bean5();
        Bean7 dest = new Bean7();
        new Copy(src, dest).prefix("abc_").execute() ;
        assertEquals(" ", dest.value1);
        assertEquals(" ", dest.value2);
        assertEquals("xyz2", dest.value);
    }

    /**
     * beanToBeanにおいて、prefix対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCopy_beanToBean_prefixWithLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        Bean9 dest = new Bean9();
        new Copy(src, dest).prefix("abc_").execute() ;
        assertEquals("test1", dest.valUE1);
        assertEquals("test2", dest.VAlue2);
        assertEquals("xyz2", dest.VALUE);
    }

    /**
     * beanToBeanにおいて、excludesWhitespace対象フィールドが1個の場合
     */
    @Test
    public void testCopy_beanToBean_excludesWhitespaceOnePropertie() throws Exception {
        Bean5 src = new Bean5();
        src.setAbc_value1("value1");
        Bean6 dest = new Bean6();
        new Copy(src, dest).excludesWhitespace().execute() ;
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
    public void testCopy_beanToBean_excludesWhitespaceMultipleProperties() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new Bean6();
        new Copy(src, dest).excludesWhitespace().execute() ;
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
    public void testCopy_beanToBean_excludesWhitespaceWithLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        src.setAb(" ");
        Bean6 dest = new Bean6();
        new Copy(src, dest).excludesWhitespace().execute() ;
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
    public void testCopy_beanToBean_excludesNullOnePropertie() throws Exception {
        Bean5 src = new Bean5();
        src.setAbc_exclude1("value1");
        Bean6 dest = new Bean6();
        new Copy(src, dest).excludesNull().execute() ;
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
    public void testCopy_beanToBean_excludesNullMultipleProperties() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new Bean6();
        new Copy(src, dest).excludesNull().execute() ;
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
    public void testCopy_beanToBean_excludesNullWithLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        src.setAb(null);
        Bean6 dest = new Bean6();
        new Copy(src, dest).excludesNull().execute() ;
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
    public void testCopy_beanToBean_excludesOnePropertie() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new Bean6();
        new Copy(src, dest).excludes("abc_value1").execute() ;
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
    public void testCopy_beanToBean_excludesMultipleProperties() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new Bean6();
        new Copy(src, dest).excludes("abc_value1", "abc_value2").execute() ;
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
    public void testCopy_beanToBean_excludesWithLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new Bean6();
        new Copy(src, dest).excludes("abc_value1", "abc_value2", "ab").execute() ;
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
    public void testCopy_beanToBean_includesOnePropertie() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new Bean6();
        new Copy(src, dest).includes("abc_value1").execute() ;
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
    public void testCopy_beanToBean_includesMultipleProperties() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new Bean6();
        new Copy(src, dest).includes("abc_value1", "abc_value2").execute() ;
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
    public void testCopy_beanToBean_includesWithLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        Bean6 dest = new Bean6();
        new Copy(src, dest).includes("abc_value1", "abc_value2", "ab").execute() ;
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
    public void testCopy_beanToBean_nestBeanWithMultipleProperties() throws Exception {
        Bean10 src = new Bean10();
        src.nestBean1 = new NestBean("test1");
        src.nestBean2 = new NestBean("test2");
        Bean10 dest = new Bean10();
        new Copy(src, dest).execute() ;
        assertEquals("test1", dest.nestBean1.getAaa());
        assertEquals("test2", dest.nestBean2.getAaa());
    }

    /**
     * beanToBeanにおいて、ネストビーン対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCopy_beanToBean_nestBeanWithLowerUpperCase() throws Exception {
        Bean10 src = new Bean10();
        src.nestBean1 = new NestBean("test1");
        src.nestBean2 = new NestBean("test2");
        Bean11 dest = new Bean11();
        new Copy(src, dest).execute() ;
        assertNull(dest.neStBeAn1);
        assertNull(dest.NESTBEAN2);
    }

    /**
     * mapToMapに対して、大文字小文字の差が存在する際のコピー
     *
     * 差が存在する場合、コピーしないこと
     *
     */
    @Test
    public void testCopy_mapToMap_copyFromLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("AAA", "KKKK");

        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("aaa", "test");
        new Copy(src, dest).execute();

        assertEquals("test", dest.get("aaa"));
    }

    /**
     * beanToMapに対して、大文字小文字の差が存在する際のコピー
     *
     * 差が存在する場合、コピーしないこと
     *
     */
    @Test
    public void testCopy_beanToMap_copyFromLowerUpperCase() throws Exception {
        Bean5 src = new Bean5();
        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("XYZ_VALUE", "test1");
        dest.put("AB", "test2");
        new Copy(src, dest).execute();

        assertEquals(" ", dest.get("abc_value1"));
        assertEquals(" ", dest.get("abc_value2"));
        assertEquals("test1", dest.get("XYZ_VALUE"));
        assertEquals("other", dest.get("other.fff"));
        assertNull(dest.get("abc_exclude1"));
        assertNull(dest.get("abc_exclude2"));
        assertEquals("test2", dest.get("AB"));
    }

    /**
     * mapToBeanの時、prefix対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCopy_mapToBean_prefixWithLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", "test");
        src.put("abc_value", "xyz");
        Bean9 dest = new Bean9();
        new Copy(src, dest).prefix("abc_").execute() ;
        assertEquals("test1", dest.valUE1);
        assertEquals("test2", dest.VAlue2);
        assertEquals("xyz2", dest.VALUE);
    }



    /**
     * mapToBeanの時、excludesWhitespace対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCopy_mapToBean_excludesWhitespaceWithLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", " ");
        src.put("xyz_value", "xyz");
        src.put("other$fff", "other");
        src.put("abc_exclude1", null);
        src.put("abc_exclude2", null);
        src.put("ab", " ");
        Bean6 dest = new Bean6();
        new Copy(src, dest).excludesWhitespace().execute() ;
        assertEquals("test1", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * mapToBeanの時、excludesNull対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCopy_mapToBean_excludesNullWithLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("ab", null);
        Bean6 dest = new Bean6();
        new Copy(src, dest).excludesNull().execute() ;
        assertEquals("test1", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz2", dest.xyz_value);
        assertEquals("other2", dest.other$fff );
        assertEquals("abc1", dest.abc_exclude1);
        assertEquals("abc2", dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * mapToBeanの時、excludes対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCopy_mapToBean_excludesWithLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", " ");
        src.put("xyz_value", "xyz");
        src.put("other$fff", "other");
        src.put("abc_exclude1", null);
        src.put("abc_exclude2", null);
        src.put("ab", "ab");
        Bean6 dest = new Bean6();
        new Copy(src, dest).excludes("abc_value1", "abc_value2", "ab").execute() ;
        assertEquals("test1", dest.abc_value1);
        assertEquals("test2", dest.abc_value2);
        assertEquals("xyz", dest.xyz_value);
        assertEquals("other", dest.other$fff );
        assertNull(dest.abc_exclude1);
        assertNull(dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * mapToBeanの時、includes対象フィールドに大文字小文字の差分がある
     */
    @Test
    public void testCopy_mapToBean_includesWithLowerUpperCase() throws Exception {
        Map<String, Object> src = new HashMap<String, Object>();
        src.put("abc_value1", " ");
        src.put("abc_value2", " ");
        src.put("xyz_value", "xyz");
        src.put("other$fff", "other");
        src.put("abc_exclude1", null);
        src.put("abc_exclude2", null);
        src.put("ab", "ab");
        Bean6 dest = new Bean6();
        new Copy(src, dest).includes("abc_value1", "abc_value2", "ab").execute() ;
        assertEquals(" ", dest.abc_value1);
        assertEquals(" ", dest.abc_value2);
        assertEquals("xyz2", dest.xyz_value);
        assertEquals("other2", dest.other$fff );
        assertEquals("abc1", dest.abc_exclude1);
        assertEquals("abc2", dest.abc_exclude2);
        assertEquals("abc", dest.AB);
    }

    /**
     * BeanToMapにおいて、srcとdestでデータ型が違う場合
     */
    @Test
    public void testCopy_BeanToMap_dataTypeIsDiff() throws Exception {
        Bean4 src = new Bean4();
        src.setBbb("2");
        src.setCcc("1234567891");
        src.setDdd("false");
        src.setNestBean("test");

        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("aaa", "aaa");
        dest.put( "bbb", 1);
        dest.put( "ccc", 1234567890);
        dest.put( "ddd", true);
        dest.put( "nestBean", new NestBean("test"));

        new Copy(src, dest).execute();
        assertNull(dest.get("aaa"));
        assertEquals("2", dest.get("bbb"));
        assertEquals("1234567891", dest.get("ccc"));
        assertEquals("false", dest.get("ddd"));
        assertEquals("test", dest.get("nestBean"));
    }

    /**
     * BeanToMapにおいて、src,destが同一の場合
     */
    @Test
    public void testCopy_BeanToMap_theSameBean() throws Exception {
        Bean1 src = new Bean1();
        src.setAaa("Bean");
        src.setBbb(2);
        src.setCcc(1234567891);
        src.setDdd(false);
        src.setNestBean(new NestBean("test"));

        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("aaa", "aaa");
        dest.put( "bbb", 1);
        dest.put( "ccc", 1234567890);
        dest.put( "ddd", true);
        dest.put( "nestBean", null);

        new Copy(src, dest).execute();
        assertEquals("Bean", dest.get("aaa"));
        assertEquals(2, dest.get("bbb"));
        assertEquals((long)1234567891, dest.get("ccc"));
        assertEquals(false, dest.get("ddd"));
        assertNotNull(dest.get("nestBean"));
    }

    /**
     * BeanToMapにおいて、srcにしかないフィールドがある場合
     */
    @Test
    public void testCopy_BeanToMap_theSpecifiedPropertiesFromSrc() throws Exception {
        Bean2 src = new Bean2();
        src.setBbb(2);
        src.setCcc(1234567891);
        src.setDdd(false);
        src.setNestBean(new NestBean("test"));

        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put( "ccc", 1234567890);
        dest.put( "ddd", true);
        dest.put( "nestBean", null);

        new Copy(src, dest).execute();
        assertEquals(2, dest.get("bbb"));
        assertEquals((long)1234567891, dest.get("ccc"));
        assertEquals(false, dest.get("ddd"));
        assertNotNull(dest.get("nestBean"));

    }

    /**
     * BeanToMapにおいて、destにしかないフィールドがある場合
     */
    @Test
    public void testCopy_BeanToMap_theSpecifiedPropertiesFromDest() throws Exception {
        Bean2 src = new Bean2();
        src.setBbb(2);
        src.setCcc(1234567891);
        src.setDdd(false);
        src.setNestBean(new NestBean("test"));

        Map<String, Object> dest = new HashMap<String, Object>();
        dest.put("aaa", "aaa");
        dest.put( "bbb", 1);
        dest.put( "ccc", 1234567890);
        dest.put( "ddd", true);
        dest.put( "nestBean", null);

        new Copy(src, dest).execute();
        assertEquals("aaa", dest.get("aaa"));
        assertEquals(2, dest.get("bbb"));
        assertEquals((long)1234567891, dest.get("ccc"));
        assertEquals(false, dest.get("ddd"));
        assertNotNull(dest.get("nestBean"));
    }

    /**
     * BeanToMapの場合、excludesNullとexcludesWhitespaceの組み合わせ
     */
    @Test
    public void testCopy_BeanToMap_excludesWhitespaceAndExcludesNull() throws Exception {
        Bean1 src = new Bean1();
        src.setAaa("");
        src.setBbb(1);
        src.setCcc(0);
        src.setDdd(false);
        src.setNestBean(null);

        Map<String, Object> dest = new HashMap<String, Object>();

        new Copy(src, dest).excludesWhitespace().excludesNull().execute();
        assertEquals(3, dest.size());
        assertEquals(1, dest.get("bbb"));
        assertEquals((long)0, dest.get("ccc"));
        assertEquals(false, dest.get("ddd"));
    }

   /**
    * BeanToMapの場合、prefix、excludesNullとexcludesWhitespaceの組み合わせ
    */
   @Test
   public void testCopy_BeanToMap_prefixAndExcludesWhitespaceAndExcludesNull() throws Exception {
       Bean8 src = new Bean8();
       Map<String, Object> dest = new HashMap<String, Object>();
       new Copy(src, dest).excludesWhitespace().excludesNull().prefix("other_").execute();

       assertEquals(1, dest.size());
       assertEquals("test", dest.get("value"));
   }

}
