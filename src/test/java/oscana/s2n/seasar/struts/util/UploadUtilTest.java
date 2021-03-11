package oscana.s2n.seasar.struts.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.IOUtils;
import org.junit.Test;

import nablarch.fw.dicontainer.nablarch.Containers;
import oscana.s2n.handler.HttpResourceHolder;
import oscana.s2n.handler.HttpResourceHolderHandler;
import oscana.s2n.struts.upload.FormFile;
import oscana.s2n.testCommon.S2NBaseTest;
import oscana.s2n.testLibrary.utill.FileUtil;

/**
 * {@link UploadUtil}のテスト。
 *
 */
public class UploadUtilTest extends S2NBaseTest {
    // テストに使う一時ファイル
    private File tempFile;

    /**
     * @throws Exception
     */
    public void setUpWrite() throws Exception {
        this.tempFile = File.createTempFile(this.getClass().getName(), "tmp");
    }

    /**
     * {@link UploadUtil#checkSizeLimit(HttpServletRequest)} のテストです。
     */
    @Test
    public void testCheckSizeLimit() {
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            HttpResourceHolder resource = Containers.get().getComponent(HttpResourceHolder.class);
            assertTrue(UploadUtil.checkSizeLimit(resource.getHttpServletRequest()));
            return null;
        }));

    }

    /**
     * {@link UploadUtil#write(String, FormFile)} のテストです。
     *
     * @throws Exception
     */
    @Test
    public void testWrite() throws Exception {
        byte[] fileByte = IOUtils.toByteArray(new FileInputStream(
                "src/test/resources/oscana/s2n/seasar/struts/util/upload/uploadUtilTestFile1.csv"));
        setUpWrite();

        FormFile formFile = new FormFile();
        File file = new File("src/test/resources/oscana/s2n/seasar/struts/util/upload/uploadUtilTestFile1.csv");
        formFile.setContentType("UTF-8");
        formFile.setFileName("uploadTestFile");
        formFile.setSaveFile(file);

        UploadUtil.write(tempFile.getCanonicalPath(), formFile);

        assertEquals(Arrays.toString(fileByte), Arrays.toString(FileUtil.getBytes(tempFile)));

    }

    /**
     * {@link UploadUtil#write(String, FormFile)} のテストです。
     *
     * @throws Exception
     */
    @Test
    public void testWrite_setFormFileNull() throws Exception {
        setUpWrite();

        FormFile formFile = null;
        UploadUtil.write(tempFile.getCanonicalPath(), formFile);
        assertEquals("[]", Arrays.toString(FileUtil.getBytes(tempFile)));

    }

    /**
     * {@link UploadUtil#write(String, FormFile)} のテストです。
     *
     * @throws Exception
     */
    @Test
    public void testWrite_setFileSizeNull() throws Exception {
        setUpWrite();

        FormFile formFile = new FormFile();
        File file = new File("uploadUtilTestFile2.csv");

        formFile.setSaveFile(file);
        UploadUtil.write(tempFile.getCanonicalPath(), formFile);

        assertEquals("[]", Arrays.toString(FileUtil.getBytes(tempFile)));

    }

    @Override
    protected void setClassToRegist() {
        registClassList = Arrays.asList(HttpResourceHolder.class);
    }

}
