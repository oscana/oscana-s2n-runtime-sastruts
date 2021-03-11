package oscana.s2n.seasar.framework.container.factory;

import static org.junit.Assert.*;

import org.junit.Test;

import oscana.s2n.seasar.framework.container.OscanaContainer;

/**
 * {@link OscanaSingletonContainerFactory}のテスト。
 *
 */
public class OscanaSingletonContainerFactoryTest {

    /**
     * コンテナが存在する場合、「true」を返却すること
     */
    @Test
    public void testHasContainer() {
        OscanaSingletonContainerFactory.getContainer();
        assertTrue(OscanaSingletonContainerFactory.hasContainer());
    }

    /**
     * コンテナが存在しない場合、「false」を返却すること
     */
    @Test
    public void testHasContainerNo() {
        boolean result = OscanaSingletonContainerFactory.hasContainer();
        if(result) {
            assertTrue(OscanaSingletonContainerFactory.hasContainer());
        } else {
            assertFalse(OscanaSingletonContainerFactory.hasContainer());
        }
    }

    /**
     * コンテナの取得検証
     */
    @Test
    public void testGetContainer() {
        OscanaContainer s2Container1 = OscanaSingletonContainerFactory.getContainer();
        assertNotNull(s2Container1);

        OscanaContainer s2Container2 = OscanaSingletonContainerFactory.getContainer();
        assertNotNull(s2Container2);

        assertEquals(s2Container1.hashCode(),s2Container2.hashCode());
    }

}