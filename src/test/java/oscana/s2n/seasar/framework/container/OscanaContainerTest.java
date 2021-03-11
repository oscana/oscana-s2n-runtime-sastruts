package oscana.s2n.seasar.framework.container;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import nablarch.fw.dicontainer.exception.ComponentNotFoundException;
import oscana.s2n.handler.HttpResourceHolder;
import oscana.s2n.handler.HttpResourceHolderHandler;
import oscana.s2n.testCommon.S2NBaseTest;

/**
 * {@link OscanaContainer}のテスト。
 */
public class OscanaContainerTest extends S2NBaseTest {

    static final String COMPONENT_NO_HAS_NAME = "oscana.s2n.seasar.framework.util.tiger.ReflectionUtil";

    static final String COMPONENT_HAS_NAME = "oscana.s2n.handler.HttpResourceHolder";

    OscanaContainer s2Container = new OscanaContainer();

    /**
     * 指定したクラスがDIコンテナに登録されている場合、取得できること
     */
    @Test
    public void testHasComponentDef() {

        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertNotNull(s2Container.hasComponentDef(COMPONENT_HAS_NAME));
            return null;
        }));

    }

    /**
     * 指定したクラスが見つけない場合、例外が出力すること
     */
    @Test
    public void testHasComponentDef_ThrowException() {
        try {
            this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
                s2Container.hasComponentDef(COMPONENT_NO_HAS_NAME);
                return null;
            }));

            fail();
        } catch(ComponentNotFoundException exception) {
            assertEquals(ComponentNotFoundException.class, exception.getClass());
            assertEquals("key=" + COMPONENT_NO_HAS_NAME, exception.getMessage().toString());
        }
    }

    /**
     * 指定したクラスがDIコンテナに登録されてない場合、「false」を返却すること
     */
    @Test
    public void testHasComponentDef_ThrowClassNotFoundException() {
        boolean test = s2Container.hasComponentDef("Foo");
        assertFalse(test);
    }

    /**
     * String型のパラメータを渡され、コンポーネントが見つかった場合、コンポーネントを返すこと
     */
    @Test
    public void testGetComponentByString() {
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertNotNull(s2Container.getComponent(COMPONENT_HAS_NAME));
            return null;
        }));
    }

    /**
     * Class型のパラメータを渡され、コンポーネントが見つかった場合、コンポーネントを返すこと
     */
    @Test
    public void testGetComponentByClass() {
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertNotNull(s2Container.getComponent(HttpResourceHolder.class));
            return null;
        }));
    }

    /**
     * コンポーネントが見つからない場合、例外を送出すること
     */
    @Test(expected = RuntimeException.class)
    public void testGetComponent03() {
        s2Container.getComponent(1111);
    }

    @Override
    protected void setClassToRegist() {
        registClassList = Arrays.asList(HttpResourceHolder.class);
    }

}
