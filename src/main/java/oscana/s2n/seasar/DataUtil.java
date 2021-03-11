package oscana.s2n.seasar;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nablarch.core.db.statement.ResultSetIterator;
import nablarch.core.db.statement.SqlRow;
import oscana.s2n.seasar.framework.beans.util.BeanMap;

/**
 * DBアクセスのユーティリティクラス。
 *
 * @author Rai Shuu
 */
public class DataUtil {

    /**
     * SqlRowリストをBeanMapリストに変換する。
     *
     * @param sqlRows SqlRowリスト。nullは不可
     * @return BeanMapリスト
     */
    public static List<BeanMap> convertToBeanMapList(List<SqlRow> sqlRows) {
        return sqlRows.stream().map(DataUtil::convertToBeanMap).collect(Collectors.toList());
    }

    /**
     * SqlRowをBeanMapに変換する。
     * <p/>
     * S2JDBCの仕様に合わせて変換以外に下記の処理を行う。
     * <ul>
     * <li>S2JDBCだと項目名を小文字に変換するため、SqlRowの項目名を小文字に変換してBeanMapに設定する。</li>
     * <li>S2JDBCだと1件検索で対象データが存在しない場合にnullが返されるので、SqlRowのnull指定を可とし、nullの場合はnullを返す。</li>
     * </ul>
     *
     * @param sqlRow SqlRow。nullでもよい。
     * @return BeanMap。SqlRowがnullの場合はnullを返す。
     */
    public static BeanMap convertToBeanMap(SqlRow sqlRow) {
        if (sqlRow == null) {
            return null;
        }
        BeanMap beanMap = new BeanMap();
        for (Map.Entry<String, Object> entry : sqlRow.entrySet()) {
            beanMap.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        return beanMap;
    }

    /**
     * COUNT文の結果となる件数を取得する。
     * <p/>
     * 指定されたResultSetIteratorはこのメソッド内でクローズする。
     *
     * @param rs ResultSetIterator
     * @return COUNT文の結果となる件数
     */
    public static long getCountQueryResult(ResultSetIterator rs) {
        long count;
        try {
            if (rs != null && rs.next()) {
                count = rs.getLong(1);
            } else {
                throw new IllegalStateException("Count query didn't return result.");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return count;
    }
}