package oscana.s2n.seasar.framework.util.tiger;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

import oscana.s2n.seasar.framework.util.ArrayUtilTest;

/**
 * {@link ReflectionUtil}のテスト
 *
 */
public class ReflectionUtilTest {

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testForName() throws Exception {
        Class<Foo> clazz = ReflectionUtil.forName(getClass().getName() + "$Foo");
        assertNotNull(clazz);
    }

    /**
     * 指定されたクラスローダを使って、指定したクラスが存在しない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testForNameThrowException() {
        try {
            ReflectionUtil.forName("ArrayUtilTest");
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * 現在のスレッドのコンテキストクラスローダを使って、指定したクラスの関連オブジェクトを正常に戻すこと
     * @throws Exception
     */
    @Test
    public void testForNameNoException() throws Exception {
        Class<Foo> clazz = ReflectionUtil.forNameNoException(getClass().getName() + "$Foo");
        assertNotNull(clazz);
    }

    /**
     * 現在のスレッドのコンテキストクラスローダを使って、指定したクラスが見つからなかった場合は{@code null}を戻すこと
     * @throws Exception
     */
    @Test
    public void testForNameNoExceptionThrowNull() throws Exception {
        assertEquals(null, ReflectionUtil.forNameNoException("ArrayUtilTest"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testGetConstructor() throws Exception {
        Constructor<Foo> ctor = ReflectionUtil.getConstructor(Foo.class);
        assertNotNull(ctor);
    }

    /**
     * 指定したコンストラクタを見つけない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testGetConstructorThrowException() throws Exception {

        try {
            ReflectionUtil.getConstructor(ArrayUtilTest.class, Foo.class);
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testGetDeclaredConstructor() throws Exception {
        Constructor<Foo> ctor = ReflectionUtil.getDeclaredConstructor(
                Foo.class, int.class, String.class);
        assertNotNull(ctor);
    }

    /**
     * 指定したコンストラクタを見つけない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testGetDeclaredConstructorThrowException() throws Exception {

        try {
            ReflectionUtil.getDeclaredConstructor(ArrayUtilTest.class, Foo.class);
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testGetField() throws Exception {
        Field f = ReflectionUtil.getField(Foo.class, "s");
        assertNotNull(f);
    }

    /**
     * 指定したフィールドが存在しない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testGetFieldThrowException() throws Exception {

        try {
            ReflectionUtil.getField(ArrayUtilTest.class, "test");
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testGetDeclaredField() throws Exception {
        Field f = ReflectionUtil.getDeclaredField(Foo.class, "n");
        assertNotNull(f);
    }

    /**
     * 指定された宣言フィールドが存在しない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testGetDeclaredFieldThrowException() throws Exception {

        try {
            ReflectionUtil.getDeclaredField(ArrayUtilTest.class, "test");
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testGetMethod() throws Exception {
        Method m = ReflectionUtil.getMethod(Foo.class, "getS");
        assertNotNull(m);

        m = ReflectionUtil.getMethod(Foo.class, "setS", String.class);
        assertNotNull(m);
    }

    /**
     * 指定されたメンバメソッドが存在しない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testGetMethodThrowException() throws Exception {

        try {
            ReflectionUtil.getMethod(ArrayUtilTest.class, "test");
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testGetDeclaredMethod() throws Exception {
        Method m = ReflectionUtil.getDeclaredMethod(Foo.class, "getN");
        assertNotNull(m);

        m = ReflectionUtil.getDeclaredMethod(Foo.class, "setN", int.class);
        assertNotNull(m);
    }

    /**
     * 指定されたメンバメソッドが存在しない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testGetDeclaredMethodThrowException() throws Exception {

        try {
            ReflectionUtil.getDeclaredMethod(ArrayUtilTest.class, "test");
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testNewInstance() throws Exception {
        Foo foo = ReflectionUtil.newInstance(Foo.class);
        assertNotNull(foo);

        Constructor<Foo> ctor = ReflectionUtil.getDeclaredConstructor(
                Foo.class, int.class, String.class);
        ctor.setAccessible(true);
        foo = ReflectionUtil.newInstance(ctor, 10, "foo");
        assertNotNull(foo);
        assertEquals(10, foo.getN());
        assertEquals("foo", foo.getS());
    }

    /**
     * デフォルトコンストラクタのクラスの新しいインスタンスを生成できない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testClassNewInstanceThrowException() throws Exception {

        try {
            ReflectionUtil.newInstance(Bar.class);
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * コンストラクタの宣言クラスの新しいインスタンスを生成できない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testConstructorNewInstanceThrowException() throws Exception {
        try {
            Constructor<Foo> ctor = ReflectionUtil.getDeclaredConstructor(
                    Foo.class, int.class, String.class);
            ReflectionUtil.newInstance(ctor, 10, "foo");
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testGetValue() throws Exception {
        Foo foo = new Foo(10, "foo");

        Field f = ReflectionUtil.getDeclaredField(Foo.class, "n");
        f.setAccessible(true);
        int n = Integer.class.cast(ReflectionUtil.getValue(f, foo));
        assertEquals(10, n);

        f = ReflectionUtil.getDeclaredField(Foo.class, "s");
        String s = ReflectionUtil.getValue(f, foo);
        assertEquals("foo", s);
    }

    /**
     * 指定したフィールドにアクセスできない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testGetValueThrowException() throws Exception {

        try {
            Foo foo = new Foo(10, "foo");
            Field f = ReflectionUtil.getDeclaredField(Foo.class, "n");
            ReflectionUtil.getValue(f, foo);
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * 指定した対象はstatic型のフィールドの場合、正常に取得できること
     * @throws Exception
     */
    @Test
    public void testGetStaticValue() throws Exception {
        Field f = ReflectionUtil.getDeclaredField(Foo.class, "w");
        f.setAccessible(true);
        String str = String.class.cast(ReflectionUtil.getStaticValue(f));
        assertEquals(Foo.w, str);
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testSetValue() throws Exception {
        Foo foo = new Foo();

        Field f = ReflectionUtil.getDeclaredField(Foo.class, "n");
        f.setAccessible(true);
        ReflectionUtil.setValue(f, foo, 10);
        assertEquals(10, foo.n);

        f = ReflectionUtil.getField(Foo.class, "s");
        ReflectionUtil.setValue(f, foo, "foo");
        assertEquals("foo", foo.s);
    }

    /**
     * 指定されたオブジェクト引数のフィールドにアクセスできない場合、RuntimeExceptionの例外を戻すこと
     * @throws Exception
     */
    @Test
    public void testSetValueThrowException() throws Exception {

        try {
            Foo foo = new Foo();
            Field f = ReflectionUtil.getDeclaredField(Foo.class, "n");

            ReflectionUtil.setValue(f, foo, "foo");
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * 指定した対象はフィールドを、新しい値に設定する場合、新しい値を取得できること
     * @throws Exception
     */
    @Test
    public void testSetStaticValue() throws Exception {
        Field f = ReflectionUtil.getDeclaredField(Foo.class, "w");
        ReflectionUtil.setStaticValue(f, "foo");
        assertEquals("foo", Foo.w);
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testInvoke() throws Exception {
        Foo foo = new Foo();

        Method m = ReflectionUtil.getDeclaredMethod(Foo.class, "setN",
                int.class);
        m.setAccessible(true);
        ReflectionUtil.invoke(m, foo, 10);
        assertEquals(10, foo.n);

        m = ReflectionUtil.getMethod(Foo.class, "setS", String.class);
        ReflectionUtil.invoke(m, foo, "foo");
        assertEquals("foo", foo.s);
    }

    /**
     * 指定したメソッドにアクセスできない場合、RuntimeExceptionの例外を戻すこと
     */
    @Test
    public void testInvokeThrowException() throws Exception {

        try {
            Foo foo = new Foo();
            Method m = ReflectionUtil.getDeclaredMethod(Foo.class, "setN",
                    int.class);
            ReflectionUtil.invoke(m, foo, 10);
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * static型メソッドから指定したパラメータを取得できること
     * @throws Exception
     */
    @Test
    public void testInvokeStatic() throws Exception {
        Method m = ReflectionUtil.getDeclaredMethod(Foo.class, "setW",
                String.class);
        ReflectionUtil.invokeStatic(m, "w");
        assertEquals("w", Foo.w);
    }

    /**
     * テストクラス
     */
    public static class Foo {

        /** */
        public static String w;

        private int n;

        /** */
        public String s;

        /** */
        public Foo() {
        }

        private Foo(int n, String s) {
            setN(n);
            setS(s);
        }

        private int getN() {
            return n;
        }

        private void setN(int n) {
            this.n = n;
        }

        /**
         * @return
         */
        public String getS() {
            return s;
        }

        /**
         * @param s
         */
        public void setS(String s) {
            this.s = s;
        }

        public static String getW() {
            return w;
        }

        public static void setW(String w) {
            Foo.w = w;
        }
    }

    /**
     * テストクラス
     */
    abstract class Bar {
    }

    /**
     * テストクラス
     */
    abstract class Baz {
        Baz(String str) {
        }
    }

    /**
     * テストクラス
     */
    class Deffe extends Baz {

        Deffe(String str) {
            super(str);
        }

    }

}
