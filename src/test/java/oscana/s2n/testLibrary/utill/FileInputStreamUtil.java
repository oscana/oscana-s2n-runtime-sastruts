package oscana.s2n.testLibrary.utill;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import oscana.s2n.seasar.framework.exception.IORuntimeException;


/**
 * {@link FileInputStream}用のユーティリティクラスです。
 *
 * @author higa
 *
 */
public class FileInputStreamUtil {

    /**
     * インスタンスを構築します。
     */
    protected FileInputStreamUtil() {
    }

    /**
     * {@link FileInputStream}を作成します。
     *
     * @param file
     * @return {@link FileInputStream}
     * @throws IORuntimeException
     *             {@link IOException}が発生した場合
     * @see FileInputStream#FileInputStream(File)
     */
    public static FileInputStream create(File file) throws IORuntimeException {
        try {
            return new FileInputStream(file);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}