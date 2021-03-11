package oscana.s2n.seasar;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import oscana.s2n.seasar.extension.sql.SemicolonNotAllowedRuntimeException;
import oscana.s2n.seasar.framework.beans.util.BeanMap;

/**
 * {@link LiteralReplacementSyntaxConverter}のテスト。
 *
 */
public class LiteralReplacementSyntaxConverterTest {

    /**
     * 引数がある場合、リテラル置換を行うこと
     */
    @Test
    public void testConvert() {

        String sql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      /*$whereku*/\r\n" +
                "   }";

        String expectedSql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      ATESAKISHUBETSU = '1'\r\n" +
                "   }";

        BeanMap where = new BeanMap();
        where.put("whereku", "ATESAKISHUBETSU = '1'");

        String actualSql = new LiteralReplacementSyntaxConverter().convert(sql, where);
        assertEquals(expectedSql, actualSql);

    }

    /**
     * 埋め込み変数コメントが複数の場合、固定の文字列を置換すること
     */
    @Test
    public void testConvert2() {

        String sql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      /*$whereku1*/\r\n" +
                "   }" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      /*$whereku2*/\r\n" +
                "   }";

        String expectedSql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      ATESAKISHUBETSU1 = '1'\r\n" +
                "   }"+
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      ATESAKISHUBETSU2 = '1'\r\n" +
                "   }";

        BeanMap where = new BeanMap();
        where.put("whereku1", "ATESAKISHUBETSU1 = '1'");
        where.put("whereku2", "ATESAKISHUBETSU2 = '1'");

        String actualSql = new LiteralReplacementSyntaxConverter().convert(sql, where);
        assertEquals(expectedSql, actualSql);

    }

    /**
     * 同一の埋め込み変数コメントが複数個所に埋め込まれている場合、すべての埋め込み変数コメントが変換されること
     */
    @Test
    public void testConvert3() {

        String sql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      /*$whereku*/\r\n" +
                "   }" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      /*$whereku*/\r\n" +
                "   }";

        String expectedSql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      ATESAKISHUBETSU = '1'\r\n" +
                "   }"+
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      ATESAKISHUBETSU = '1'\r\n" +
                "   }";

        BeanMap where = new BeanMap();
        where.put("whereku", "ATESAKISHUBETSU = '1'");

        String actualSql = new LiteralReplacementSyntaxConverter().convert(sql, where);
        assertEquals(expectedSql, actualSql);

    }

    /**
     * 引数が空文字の場合、固定の文字列を置換すること
     */
    @Test
    public void testConvertNull01() {

        String sql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      /*$whereku*/\r\n" +
                "   }";

        String expectedSql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "       1 = 1 \r\n" +
                "   }";

        BeanMap where = new BeanMap();
        where.put("whereku", "");

        String actualSql = new LiteralReplacementSyntaxConverter().convert(sql, where);
        assertEquals(expectedSql, actualSql);
    }

    /**
     * 引数がnullの場合、固定の文字列を置換すること
     */
    @Test
    public void testConvertNull02() {

        String sql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      /*$whereku*/\r\n" +
                "   }";

        String expectedSql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "       1 = 1 \r\n" +
                "   }";

        BeanMap where = new BeanMap();
        where.put("whereku", null);

        String actualSql = new LiteralReplacementSyntaxConverter().convert(sql, where);
        assertEquals(expectedSql, actualSql);

    }

    /**
     * 引数に複数行のSQL断片（改行付き）を与えたときに、正しく、文字列の置換ができること
     */
    @Test
    public void testConvertWithNewLine() {

        String sql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      /*$whereku*/\r\n" +
                "   }";

        String expectedSql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      ATESAKISHUBETSU1 = '1'" + "\r\n" +
                " AND FAXBANNGOU ='2'"+ "\r\n" +
                "   }";

        BeanMap where = new BeanMap();
        where.put("whereku", "ATESAKISHUBETSU1 = '1'\r\n AND FAXBANNGOU ='2'");

        String actualSql = new LiteralReplacementSyntaxConverter().convert(sql, where);
        assertEquals(expectedSql, actualSql);

    }

    /**
     * SQLに「$」がない場合、リテラル置換を行わないこと
     */
    @Test
    public void testConvertNull03() {

        String sql = "SELECT\r\n" +
                "    FAXBANNGOU,\r\n" +
                "    KIINN,\r\n" +
                "    ATESAKISHUBETSU\r\n" +
                "FROM\r\n" +
                "    TBJTXCENTERFAXBNG\r\n" +
                "WHERE\r\n" +
                "    SAKUJOFLAG IS NOT NULL\r\n" +
                "    AND\r\n" +
                "$if(whereku) {\r\n" +
                "      /*whereku*/\r\n" +
                "   }";

        BeanMap where = new BeanMap();
        where.put("whereku", null);

        String actualSql = new LiteralReplacementSyntaxConverter().convert(sql, where);
        assertEquals(sql, actualSql);

    }

    /**
     * セミコロンが入る場合、エラーをスローすること
     */
    @Test(expected = SemicolonNotAllowedRuntimeException.class)
    public void testSqlSemicolon() {

        String sql = "SELECT * FROM OPE\r\n" +
                "WHERE\r\n" +
                "1 = 1\r\n" +
                "AND\r\n" +
                "$if(sqlParts)  {\r\n" +
                "     /*$sqlParts*/\r\n" +
                "}";

        Map<String, String> map = new HashMap<>();
        map.put("sqlParts", "USER_ID = '0000000001';");

        new LiteralReplacementSyntaxConverter().convert(sql, map);
    }
}
