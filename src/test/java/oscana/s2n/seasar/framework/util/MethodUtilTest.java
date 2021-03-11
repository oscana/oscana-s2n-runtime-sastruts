package oscana.s2n.seasar.framework.util;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;

import oscana.s2n.seasar.framework.util.tiger.ReflectionUtil;

/**
 * {@link MethodUtil}のテスト
 *
 */
public class MethodUtilTest {

    private static final Method IS_SYNTHETIC_METHOD = getIsSyntheticMethod();

    private static Method getIsSyntheticMethod() {
        try {
            return Method.class.getMethod("isSynthetic", new Class[0]);
        } catch (final NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * 指定したオブジェクトに対して指定したパラメータで呼び出せること
     * @throws Exception
     */
    @Test
    public void testInvoke() throws Exception {
        Method method = Foo.class.getMethod("foo", new Class[0]);
        assertFalse(((Boolean) MethodUtil.invoke(IS_SYNTHETIC_METHOD, method, null)).booleanValue());

    }


    /**
     * アクセスできない場合、RuntimeExceptionの例外を戻すこと
     */
    @Test
    public void testInvokeThrowException() throws Exception {
        Method m = null;
        try {
            Foo foo = new Foo(0);
            m = ReflectionUtil.getDeclaredMethod(Foo.class, "setN", int.class);
            MethodUtil.invoke(m, foo, null);
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
            assertEquals(m.getDeclaringClass().getName(), e.getMessage());
        }
    }

    /**
     * Syntheticメソッドではない場合、falseを返すこと
     * @throws Exception
     */
    @Test
    public void testIsSyntheticMethod() throws Exception {
        Method method = Foo.class.getMethod("foo", new Class[0]);
        assertFalse((MethodUtil.isSyntheticMethod(method)));

    }

    /**
     * Bridgeメソッドではない場合、falseを返すこと
     * @throws Exception
     */
    @Test
    public void testIsBridgeMethod() throws Exception {
        Method method = Foo.class.getMethod("foo", new Class[0]);
        assertFalse((MethodUtil.isBridgeMethod(method)));

    }

    /**
    * テスト用クラス
    */
    public static class Foo {

        public void foo() {
        }

        private Foo(int n) {
            setN(n);
        }

        private int n;

        @SuppressWarnings("unused")
        private int getN() {
            return n;
        }

        private void setN(int n) {
            this.n = n;
        }

    }
}