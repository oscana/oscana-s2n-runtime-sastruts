package oscana.s2n.seasar.framework.util;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * {@link FieldUtil}のテスト。
 *
 */
public class FieldUtilTest {

    public Object objectField;
    public int intField;
    public String stringField;
    public static final int INT_DATA = 987654321;
    public static final String STRING_DATA = "Hello World!";

    /**
     * {@link FieldUtil#get(java.lang.reflect.Field, java.lang.Object)}
     * のためのテスト・メソッド。</br>
     *
     * （移植元FM付属テストケース）
     */
    @Test
    public void testGet() {
        Field field;
        try {
            field = getClass().getField("objectField");
            Integer testData = new Integer(123);
            FieldUtil.set(field, this, testData);
            assertEquals("1", testData, FieldUtil.get(field, this));
        } catch (SecurityException e) {
            fail();
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * 指定したフィールドにアクセスできない場合、RuntimeExceptionの例外を戻すこと
     */
    @Test
    public void testGet_throwException() {
        try {
            Field field = Baz.class.getDeclaredField("sampleFiled5");
            FieldUtil.get(field, this);
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * {@link FieldUtil#getInt(java.lang.reflect.Field)}
     * のためのテスト・メソッド。</br>
     *
     * （移植元FM付属テストケース）
     */
    @Test
    public void testGetIntField() {
        Field field;
        try {
            field = getClass().getField("intField");
            int testData = 1234567890;
            FieldUtil.set(field, this, new Integer(testData));
            assertEquals("1", testData, FieldUtil.getInt(field, this));
        } catch (SecurityException e) {
            fail();
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * {@link FieldUtil#getInt(java.lang.reflect.Field, java.lang.Object)}
     * のためのテスト・メソッド。</br>
     *
     * （移植元FM付属テストケース）
     */
    @Test
    public void testGetIntFieldObject() {
        Field field;
        try {
            field = getClass().getField("INT_DATA");
            assertEquals("1", INT_DATA, FieldUtil.getInt(field));
        } catch (SecurityException e) {
            fail();
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * 指定したオブジェクトにアクセスできない場合、RuntimeExceptionの例外を戻すこと
     */
    @Test
    public void testGetIntFieldObject_throwException() {
        try {
            Field field = Baz.class.getDeclaredField("sampleFiled5");
            FieldUtil.getInt(field, this);
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * {@link FieldUtil#getString(java.lang.reflect.Field)}
     * のためのテスト・メソッド。</br>
     *
     * （移植元FM付属テストケース）
     */
    @Test
    public void testGetStringField() {
        Field field;
        try {
            field = getClass().getField("stringField");
            String testData = "Hello World!";
            FieldUtil.set(field, this, testData);
            assertEquals("1", testData, FieldUtil.getString(field, this));
        } catch (SecurityException e) {
            fail();
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * {@link FieldUtil#getString(java.lang.reflect.Field, java.lang.Object)}
     * のためのテスト・メソッド。</br>
     *
     * （移植元FM付属テストケース）
     */
    @Test
    public void testGetStringFieldObject() {
        Field field;
        try {
            field = getClass().getField("STRING_DATA");
            assertEquals("1", STRING_DATA, FieldUtil.getString(field));
        } catch (SecurityException e) {
            fail();
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * 指定したフィールドにアクセスできない場合、RuntimeExceptionの例外を戻すこと
     */
    @Test
    public void testGetStringFieldObject_throwException() {
        try {
            Field field = Baz.class.getDeclaredField("sampleFiled6");
            FieldUtil.getString(field, this);
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testSet() throws Exception {
        Field field = getClass().getField("intField");
        try {
            FieldUtil.set(field, this, "abc");
            fail();
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * インスタンスフィールドを取得できること
     */
    @Test
    public void testIsInstanceField() throws Exception {
        assertTrue(FieldUtil.isInstanceField(Baz.class
                .getField("list")));
        assertFalse(FieldUtil.isInstanceField(Baz.class
                .getField("sampleFiled1")));
        assertFalse(FieldUtil.isInstanceField(Baz.class
                .getField("sampleFiled2")));
        assertFalse(FieldUtil.isInstanceField(Baz.class
                .getField("sampleFiled3")));
    }

    /**
     * パブリックフィールドを取得できること
     */
    @Test
    public void testIsPublicField() throws Exception {
        assertTrue(FieldUtil.isPublicField(Baz.class
                .getField("list")));
        assertFalse(FieldUtil.isPublicField(Baz.class
                .getDeclaredField("sampleFiled4")));
    }

    /**
     * テスト用クラス
     */
    @SuppressWarnings({ "unused", "rawtypes" })
    public static class Baz {
        public List list;
        public Set set;
        public Collection collection;
        public final static long sampleFiled1 = 1000;
        public static long sampleFiled2 = 2000;
        public final long sampleFiled3 = 3000;
        protected int sampleFiled4 = 4;
        private int sampleFiled5 = 4;
        private String sampleFiled6 = "test";
    }
}
