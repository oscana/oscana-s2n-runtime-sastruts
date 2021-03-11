package oscana.s2n.seasar.framework.convention.impl;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link PersistenceConventionImpl}のテスト。
 *
 * （移植元FM付属テストケース）
 *
 */
public class PersistenceConventionImplTest {

    private PersistenceConventionImpl convention = new PersistenceConventionImpl();

    /**
     * Test method for
     * {@link oscana.s2n.seasar.framework.convention.impl.PersistenceConventionImpl#fromTableNameToEntityName(java.lang.String)}.
     */
    @Test
    public void testFromTableNameToEntityName() {
        assertEquals("Emp", convention.fromTableNameToEntityName("EMP"));
        assertEquals("AaaBbb", convention.fromTableNameToEntityName("AAA_BBB"));
        convention.setIgnoreTablePrefix("AAA_");
        assertEquals("AAA_", convention.getIgnoreTablePrefix());
        assertEquals("Bbb", convention.fromTableNameToEntityName("AAA_BBB"));
        convention.setNoNameConversion(true);
        assertEquals(true, convention.isNoNameConversion());
        assertEquals("AAA_BBB", convention.fromTableNameToEntityName("AAA_BBB"));

    }

    /**
     * 例外テスト
     */
    @Test(expected = NullPointerException.class)
    public void testFromTableNameToEntityNameForException() {
        convention.fromTableNameToEntityName(null);
    }

    /**
     * Test method for
     * {@link oscana.s2n.seasar.framework.convention.impl.PersistenceConventionImpl#fromEntityNameToTableName(java.lang.String)}.
     */
    @Test
    public void testFromEntityNameToTableName() {
        assertEquals("EMP", convention.fromEntityNameToTableName("Emp"));
        assertEquals("AAA_BBB", convention.fromEntityNameToTableName("AaaBbb"));
        convention.setIgnoreTablePrefix("Aaa");
        assertEquals("Aaa", convention.getIgnoreTablePrefix());
        assertEquals("AaaAAA_BBB", convention.fromEntityNameToTableName("AaaBbb"));
        convention.setNoNameConversion(true);
        assertEquals(true, convention.isNoNameConversion());
        assertEquals("AaaBbb", convention.fromEntityNameToTableName("AaaBbb"));
    }

    /**
     * 例外テスト
     */
    @Test(expected = NullPointerException.class)
    public void testFromEntityNameToTableNameForException() {
        convention.fromEntityNameToTableName(null);
    }

    /**
     * Test method for
     * {@link oscana.s2n.seasar.framework.convention.impl.PersistenceConventionImpl#fromColumnNameToPropertyName(java.lang.String)}.
     */
    @Test
    public void testFromColumnNameToPropertyName() {
        assertEquals("aaa", convention.fromColumnNameToPropertyName("AAA"));
        assertEquals("aaaBbb", convention.fromColumnNameToPropertyName("AAA_BBB"));
        convention.setNoNameConversion(true);
        assertEquals(true, convention.isNoNameConversion());
        assertEquals("AAA_BBB", convention.fromColumnNameToPropertyName("AAA_BBB"));
    }

    /**
     * 例外テスト
     */
    @Test(expected = NullPointerException.class)
    public void testFromColumnNameToPropertyNameForException() {
        convention.fromColumnNameToPropertyName(null);
    }

    /**
     * Test method for
     * {@link oscana.s2n.seasar.framework.convention.impl.PersistenceConventionImpl#fromPropertyNameToColumnName(java.lang.String)}.
     */
    @Test
    public void testFromPropertyNameToColumnName() {
        assertEquals("AAA", convention.fromPropertyNameToColumnName("aaa"));
        assertEquals("AAA_BBB", convention
                .fromPropertyNameToColumnName("aaaBbb"));
        convention.setNoNameConversion(true);
        assertEquals(true, convention.isNoNameConversion());
        assertEquals("AAA_BBB", convention.fromPropertyNameToColumnName("AAA_BBB"));
    }

    /**
     * 例外テスト
     */
    @Test(expected = NullPointerException.class)
    public void testFromPropertyNameToColumnNameForException() {
        convention.fromPropertyNameToColumnName(null);
    }
}