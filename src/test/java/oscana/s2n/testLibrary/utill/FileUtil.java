package oscana.s2n.testLibrary.utill;

import java.io.File;

import oscana.s2n.seasar.framework.util.InputStreamUtil;


/**
 * {@link File}を扱うユーティリティ・クラスです。
 *
 * @author higa
 */
public class FileUtil {

    /**
     * ファイルの内容をバイト配列に読み込んで返します。
     *
     * @param file
     *            ファイル
     * @return ファイルの内容を読み込んだバイト配列
     */
    public static byte[] getBytes(File file) {
        return InputStreamUtil.getBytes(FileInputStreamUtil.create(file));
    }
}
