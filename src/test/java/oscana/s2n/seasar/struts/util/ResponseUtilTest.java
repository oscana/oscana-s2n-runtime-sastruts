package oscana.s2n.seasar.struts.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import mockit.Verifications;
import nablarch.fw.dicontainer.nablarch.Containers;
import nablarch.fw.web.HttpResponse;
import oscana.s2n.common.web.download.S2NStreamResponse;
import oscana.s2n.handler.HttpResourceHolder;
import oscana.s2n.handler.HttpResourceHolderHandler;
import oscana.s2n.testCommon.S2NBaseTest;

/**
 * {@link ResponseUtil}のテスト。
 *
 */
public class ResponseUtilTest extends S2NBaseTest{

    /**
    * レスポンスを返却するのケース
    *
    * @throws Exception
    */
    @Test
    public void testGetResponse() throws Exception {

        // output
        final HttpServletResponse[] output = new HttpServletResponse[1];

        // テストコードを実施
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            output[0] = ResponseUtil.getResponse();
            return null;
        }));

        // 結果確認
        assertNotNull(output[0]);
        assertTrue(output[0] == httpServletResponse);
    }

    /**
    * バイトをダウンロードさせるケース
    *
    * @throws Exception
    */
    @Test
    public void testDownload_Byte() throws Exception {
        // input
        byte[] bytes = new byte[] { 0x1, 0x2, (byte) 0xfe, (byte) 0xff };
        // output
        final HttpResponse[] output = new HttpResponse[1];
        // テストコードを実施
        this.handle(Arrays.asList(new HttpResourceHolderHandler(),(data, context) -> {
            ResponseUtil.download("testDownload_Byte.txt", bytes);
            output[0] = Containers.get().getComponent(HttpResourceHolder.class).getForcedNextResponse();
            return null;
        }));

        // 結果確認
        assertNotNull(output[0]);
        assertTrue(output[0] instanceof S2NStreamResponse);
        assertThat(output[0].getContentType(), is("application/octet-stream"));
        assertThat(output[0].getContentDisposition(), is("attachment; filename=\"testDownload_Byte.txt\""));

        S2NStreamResponse res = (S2NStreamResponse) output[0];
        byte[] c = new byte[bytes.length];
        res.getBodyStream().read(c);
        assertTrue(Arrays.equals(bytes, c));
    }

    /**
    * ストリームをダウンロードさせるケース
    *
    * @throws Exception
    */
    @Test
    public void testDownload_InputStream() throws Exception {
        //　input
        byte[] bytes = new byte[] { 0x1, 0x2, (byte) 0xfe, (byte) 0xff };

        //　output
        final HttpResponse[] output = new HttpResponse[1];

        // テストコードを実行
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            ResponseUtil.download("testDownload_InputStream.txt", new ByteArrayInputStream(bytes));
            output[0] = Containers.get().getComponent(HttpResourceHolder.class).getForcedNextResponse();
            return null;
        }));

        // 結果確認
        assertNotNull(output[0]);
        assertTrue(output[0] instanceof S2NStreamResponse);
        assertThat(output[0].getContentType(), is("application/octet-stream"));
        assertThat(output[0].getContentDisposition(), is("attachment; filename=\"testDownload_InputStream.txt\""));

        S2NStreamResponse res = (S2NStreamResponse) output[0];
        byte[] c = new byte[bytes.length];
        res.getBodyStream().read(c);
        assertTrue(Arrays.equals(bytes, c));
    }

    /**
    * ストリームをダウンロードさせるケース(Content-Lengthフィールド指定あり)
    * @throws IOException
    */
    @Test
    public void testDownload_InputStreamWithContentLength() throws IOException {

        // input
        byte[] bytes = new byte[] { 0x1, 0x2, (byte) 0xfe, (byte) 0xff };
        // output
        final Object[] output = new Object[1];
        // テストコードを実行
        this.handle(Arrays.asList(new HttpResourceHolderHandler(),(data, context) -> {
            ResponseUtil.download("testDownload_InputStreamWithContentLength.txt", new ByteArrayInputStream(bytes), 2);
            new Verifications() {
                {
                    httpServletResponse.setContentLength(anyInt);
                    times = 1;

                }
            };
            output[0] = Containers.get().getComponent(HttpResourceHolder.class).getForcedNextResponse();
            return ((HttpServletResponse)Containers.get().getComponent(HttpResourceHolder.class).getHttpServletResponse());
        }));

        // 結果確認
        assertNotNull(output[0]);
        assertThat(((HttpResponse)output[0]).getContentType(),is("application/octet-stream"));
        assertThat(((HttpResponse)output[0]).getContentDisposition(),is("attachment; filename=\"testDownload_InputStreamWithContentLength.txt\""));

    }

    /**
    * レスポンスにテキストを書き込むケース
    * @throws IOException
    */
    @Test
    public void testWrite() throws IOException {
        // output
        final HttpResponse[] output = new HttpResponse[1];
        // テストコードを実行
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            ResponseUtil.write("you are welcome");
            output[0] = Containers.get().getComponent(HttpResourceHolder.class).getForcedNextResponse();
            return null;
        }));

        // 結果確認
        assertNotNull(output[0]);
        assertTrue(output[0].getBodyString().contains("you are welcome"));
        assertThat(((HttpResponse) output[0]).getContentType(), is("text/plain; charset=UTF-8"));
        assertThat(((HttpResponse) output[0]).getCharset().name(), is("UTF-8"));
    }

    /**
    * レスポンスにテキストを書き込むケース(Content-Typeフィールド指定あり)
    * @throws IOException
    */
    @Test
    public void testWriteContentType() throws IOException {

        // output
        final HttpResponse[] output = new HttpResponse[1];
        // テストコードを実行
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            ResponseUtil.write("こんにちは","text/xml");
            output[0] = Containers.get().getComponent(HttpResourceHolder.class).getForcedNextResponse();
            return null;
        }));

        // 結果確認
        HttpResponse result = output[0];
        assertNotNull(result);
        assertThat(result.getContentType(),is("text/xml; charset=UTF-8"));
        assertThat(result.getCharset().name(),is("UTF-8"));

        InputStream is = result.getBodyStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(buffer.toString("UTF-8"), "こんにちは");
        assertNotEquals(buffer.toString("Shift_JIS"), "こんにちは");
        assertEquals(result.getBodyString(),"こんにちは");
    }

    /**
    * レスポンスにテキストを書き込むケース(Content-Typeとencodeフィールド指定あり)
    * @throws IOException
    *
    * @throws Exception
    */
    @Test
    public void testWriteContentTypeAndEncode() throws IOException {
        // output
        final HttpResponse[] output = new HttpResponse[1];
        // テストコードを実行
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            ResponseUtil.write("こんにちは","text/csv","Shift_JIS");
            output[0] = Containers.get().getComponent(HttpResourceHolder.class).getForcedNextResponse();
            return null;
        }));

        // 結果確認
        HttpResponse result = output[0];
        assertNotNull(result);
        assertThat(result.getContentType(),is("text/csv; charset=Shift_JIS"));
        assertThat(result.getCharset().name(),is("Shift_JIS"));
        InputStream is = result.getBodyStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(buffer.toString("Shift_JIS"), "こんにちは");
        assertNotEquals(buffer.toString("UTF-8"), "こんにちは");
        assertEquals(result.getBodyString(),"こんにちは");

    }

    @Override
    protected void setClassToRegist() {
        registClassList = Arrays.asList(HttpResourceHolder.class);
    }

}