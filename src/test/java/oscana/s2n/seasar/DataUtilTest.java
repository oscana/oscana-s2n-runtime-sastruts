package oscana.s2n.seasar;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import nablarch.core.db.connection.DbConnectionContext;
import nablarch.core.db.statement.ResultSetIterator;
import nablarch.core.db.statement.SqlRow;
import nablarch.test.core.db.DbAccessTestSupport;
import oscana.s2n.common.ParamFilter;
import oscana.s2n.seasar.framework.beans.util.BeanMap;


/**
 * {@link DataUtil}のテスト
 */
public class DataUtilTest extends DbAccessTestSupport {

    /**
     * SqlRowのリストをBeanMapのリストに正しく変換できること(一行のデータ)
     */
    @Test
    public void testConvertOneRowToBeanMapList() {

        List<SqlRow> sqlRows = new ArrayList<SqlRow>();
        sqlRows.add(new SqlRow(new HashMap<String, Object>() {{ put("user_id", "1"); }}, null));

        List<BeanMap> beanMaps = DataUtil.convertToBeanMapList(sqlRows);

        assertEquals(sqlRows.size(), beanMaps.size());
        assertEquals("1", beanMaps.get(0).get("user_id"));
    }

    /**
     * SqlRowのリストをBeanMapのリストに正しく変換できること(データカラムが複数)
     */
    @Test
    public void testConvertMultiColToBeanMapList() {

        List<SqlRow> sqlRows = new ArrayList<SqlRow>();
        Map<String, Object> rowData = new HashMap<String, Object>();
        rowData.put("user_id", "1");
        rowData.put("user_name", "A");
        SqlRow sqlRow = new SqlRow(rowData, null);
        sqlRows.add(sqlRow);

        List<BeanMap> beanMaps = DataUtil.convertToBeanMapList(sqlRows);

        assertEquals(sqlRows.size(), beanMaps.size());
        assertEquals("1", beanMaps.get(0).get("user_id"));
        assertEquals("A", beanMaps.get(0).get("user_name"));
    }

    /**
     * SqlRowのリストをBeanMapのリストに正しく変換できること(複数行のデータ)
     */
    @Test
    public void testConvertMultiRowToBeanMapListWith() {

        List<SqlRow> sqlRows = new ArrayList<SqlRow>();
        sqlRows.add(new SqlRow(new HashMap<String, Object>() {{ put("user_id", "2"); }}, null));
        sqlRows.add(new SqlRow(new HashMap<String, Object>() {{ put("user_id", "3"); }}, null));
        sqlRows.add(new SqlRow(new HashMap<String, Object>() {{ put("user_id", "4"); }}, null));

        List<BeanMap> beanMaps = DataUtil.convertToBeanMapList(sqlRows);

        assertEquals(sqlRows.size(), beanMaps.size());
        assertEquals("2", beanMaps.get(0).get("user_id"));
        assertEquals("3", beanMaps.get(1).get("user_id"));
        assertEquals("4", beanMaps.get(2).get("user_id"));
    }

    /**
     * SqlRowのリストをBeanMapのリストに正しく変換できること(空白のデータ)
     */
    @Test
    public void testConvertKaraToBeanMapList() {

        List<SqlRow> sqlRows = new ArrayList<SqlRow>();

        List<BeanMap> beanMaps = DataUtil.convertToBeanMapList(sqlRows);

        assertEquals(sqlRows.size(), beanMaps.size());

    }

    /**
     * SqlRowをBeanMapに正しく変換できること
     */
    @Test
    public void testConvertToBeanMap() {

        SqlRow sqlRow = new SqlRow(new HashMap<String, Object>() {{ put("user_id", "0000000001"); }}, null);

        BeanMap beanMap = DataUtil.convertToBeanMap(sqlRow);
        assertEquals("0000000001", beanMap.get("user_id"));
    }

    /**
     * SqlRowをBeanMapに正しく変換できること(大文字存在の場合)
     */
    @Test
    public void testConvertAlphaToBeanMap() {

        SqlRow sqlRow = new SqlRow(new HashMap<String, Object>() {{ put("uSEr_id", "0000000001"); }}, null);

        BeanMap beanMap = DataUtil.convertToBeanMap(sqlRow);
        assertEquals("0000000001", beanMap.get("user_id"));
    }

    /**
     * SqlRowをBeanMapに正しく変換できること(複数項目)
     */
    @Test
    public void testConvertMultiColToBeanMap() {

        SqlRow sqlRow = new SqlRow(new HashMap<String, Object>() {{ put("user_id", "0000000001"); put("user_name","b"); }}, null);

        BeanMap beanMap = DataUtil.convertToBeanMap(sqlRow);
        assertEquals("0000000001", beanMap.get("user_id"));
    }

    /**
     * SqlRowをBeanMapに正しく変換できること(nullの場合)
     */
    @Test
    public void testConvertNullToBeanMap() {
        BeanMap beanMap = DataUtil.convertToBeanMap(null);
        assertNull(beanMap);
    }

    /**
     * カウント文結果を正しく取得できること
     */
    @Test
    public void testGetCountQueryResult() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", "0000000001");
        ResultSetIterator rs= DbConnectionContext.getConnection().prepareParameterizedSqlStatementBySqlId(ParamFilter.sqlFileNameToKey("oscana.s2n.seasar.DataUtilTest#testCountBySqlFile"),map).executeQueryByMap(map);
        long actual = DataUtil.getCountQueryResult(rs);
        int except = 1;
        assertEquals(except, actual);
    }

    /**
     * カウント文結果は正しく取得できないこと
     */
    @Test(expected = IllegalStateException.class)
    public void testGetCountQueryResultErr()  {
        Map<String, String> map = new HashMap<>();
        map.put("userId", "xxxx");
        ResultSetIterator rs = DbConnectionContext.getConnection().prepareParameterizedSqlStatementBySqlId(ParamFilter.sqlFileNameToKey("oscana.s2n.seasar.DataUtilTest#testCountBySqlFile"),map).executeQueryByMap(map);
        rs.next();
        DataUtil.getCountQueryResult(rs);
    }

    /**
     * カウント文結果は正しく取得できないこと
     */
    @Test(expected = IllegalStateException.class)
    public void testGetCountQueryResultErrByNull() {
        DataUtil.getCountQueryResult(null);
    }
}