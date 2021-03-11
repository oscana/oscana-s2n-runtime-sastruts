package oscana.s2n.seasar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nablarch.core.db.statement.sqlconvertor.SqlConvertorSupport;
import nablarch.core.util.StringUtil;
import oscana.s2n.seasar.extension.sql.SemicolonNotAllowedRuntimeException;

/**
 * SQL文のリテラル置換を行うクラス。
 *
 * @author Ko Ho
 *
 */
public class LiteralReplacementSyntaxConverter extends SqlConvertorSupport {

    /**
     * 埋め込み変数コメントのパターン
     */
    private static final Pattern PATTERN = Pattern.compile("\\/\\*\\$(.+?)\\*\\/", Pattern.DOTALL);


    /**
     * SQL文の埋め込み変数コメント(/&lowast;$パラメータ名&lowast;/)を任意の文字列に置き換える。
     * <p/>
     * <ul>
     * <li>/&lowast;$パラメータ名&lowast;/を可変項目とみなし、パラメータとして与えられた任意の文字列に置き換える。</li>
     * <li>置き換え先のパラメータがnull又は空文字の場合は、「1 = 1」に変換する。</li>
     * <li>SLQインジェクションを予防するため、パラメータにセミコロンを含む場合は実行時例外を送出する。</li>
     * </ul>
     * <pre>
     * 例:
     * ・SQL
     *   SELECT * FROM employee WHERE /&lowast;$sqlParts&lowast;/
     * ・Java側から渡す引数の設定
     *   obj.put("sqlParts", "id = '1' and name = 'test'");
     * ・置換後のSQL
     *   SELECT * FROM employee WHERE id = '1' and name = 'test'
     * </pre>
     *
     * @param sql SQL文
     * @param obj 検索条件をもつオブジェクト
     * @return 可変条件構文を変換するSQL文
     * @throws SemicolonNotAllowedRuntimeException パラメータにセミコロン(;)が含まれる場合
     */
    @Override
    public String convert(String sql, Object obj) {

        List<String> sqlKeyList = getSqlKey(sql);
        if (sqlKeyList == null || sqlKeyList.size() < 1) {
            return sql;
        }

        String actualSql = sql;
        for (String sqlKey : sqlKeyList) {
            String paramKey = sqlKey.substring(3, sqlKey.length() - 2); // 3:"/*$"(prefix) 2:"*/"(suffix)
            Object bindValue = getBindValue(obj, paramKey);
            String value = "";
            if (bindValue == null) {
                value = " 1 = 1 ";
            } else if (StringUtil.isNullOrEmpty(bindValue.toString().trim())) {
                value = " 1 = 1 ";
            } else {
                value = bindValue.toString();
                if (value.contains(";")) {
                    throw new SemicolonNotAllowedRuntimeException();
                }
            }
            actualSql = actualSql.replace(sqlKey, value);
        }
        return actualSql;
    }

    /**
     * SQL文に含まれる埋め込み変数コメントのキーを返却する。
     * @param sql SQL文
     * @return 埋め込み変数コメントのキーをが格納されたリスト
     */
    private List<String> getSqlKey(String sql) {
        Matcher m = PATTERN.matcher(sql);
        m.reset();
        List<String> sqlKeyList = new ArrayList<>();
        while (m.find()) {
            sqlKeyList.add(m.group());
        }
        return sqlKeyList;
    }
}
