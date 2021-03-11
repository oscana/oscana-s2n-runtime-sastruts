package oscana.s2n.seasar.framework.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

import oscana.s2n.seasar.framework.exception.IORuntimeException;

/**
 * {@link FileOutputStreamUtil}のテスト。
 *
 */
public class FileOutputStreamUtilTest {

    /**
     * 正しいファイルパスを指定する場合、正常に生成すること
     */
    @Test
    public void testCreate() {
        File file = new File("src/test/resources/oscana/s2n/seasar/framework/util/FileOutputStreamUtil/testfile.csv");
        FileOutputStream fileOutputStream = FileOutputStreamUtil.create(file);
        assertNotNull(fileOutputStream);
    }

    /**
     * 正しくないファイルパスを指定する場合、例外を出力すること
     */
    @Test(expected = IORuntimeException.class)
    public void testCreate_throwException() {
        File file = new File("src/test/resources/oscana/s2n/seasar/framework/util/FileOutputStreamUtil");
        FileOutputStreamUtil.create(file);
    }

}
