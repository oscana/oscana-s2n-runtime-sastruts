package oscana.s2n.seasar.framework.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link Base64Util}のテスト
 *
 */
public class Base64UtilTest {

    private static final String ORIGINAL1pad = "how now brown cow\r\n";

    private static final String ORIGINAL2pad = "how now brown fish\r\n";

    private static final String ORIGINAL3pad = "how now brown camel\r\n";

    private static final byte[] BINARY_DATA_1PAD = ORIGINAL1pad.getBytes();

    private static final byte[] BINARY_DATA_2PAD = ORIGINAL2pad.getBytes();

    private static final byte[] BINARY_DATA_3PAD = ORIGINAL3pad.getBytes();

    private static final String ENCODED_DATA_1PAD = "aG93IG5vdyBicm93biBjb3cNCg==";

    private static final String ENCODED_DATA_2PAD = "aG93IG5vdyBicm93biBmaXNoDQo=";

    private static final String ENCODED_DATA_3PAD = "aG93IG5vdyBicm93biBjYW1lbA0K";

    Base64Util base64Util = new Base64Util();

    /**
     * (移行元FM付属テストケース）
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testEncode1PAD() throws Exception {
        assertEquals("1", ENCODED_DATA_1PAD, base64Util.encode(BINARY_DATA_1PAD));
    }

    /**
     * 引数がBINARY_DATA_2PADの場合、Base64でエンコードすること。
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testEncode2PAD() throws Exception {
        assertEquals("1", ENCODED_DATA_2PAD, base64Util.encode(BINARY_DATA_2PAD));
    }

    /**
     * 引数がBINARY_DATA_3PADの場合、Base64でエンコードすること。
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testEncode3PAD() throws Exception {
        assertEquals("1", ENCODED_DATA_3PAD, base64Util.encode(BINARY_DATA_3PAD));
    }

    /**
     * 引数がないの場合、Base64でエンコードしないこと。
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testEncodeNull() throws Exception {
        assertEquals("", base64Util.encode(null));
        assertEquals("", base64Util.encode(new byte[] {}));
    }

    /**
     * (移行元FM付属テストケース）
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testDecode1PAD() throws Exception {
        byte[] decodedData = base64Util.decode(ENCODED_DATA_1PAD);
        assertEquals("1", BINARY_DATA_1PAD.length, decodedData.length);
        for (int i = 0; i < decodedData.length; i++) {
            assertEquals("2", BINARY_DATA_1PAD[i], decodedData[i]);
        }
    }

    /**
     * 引数がENCODED_DATA_2PADの場合、Base64でデコードすること。
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testDecode2PAD() throws Exception {
        byte[] decodedData = base64Util.decode(ENCODED_DATA_2PAD);
        assertEquals("1", BINARY_DATA_2PAD.length, decodedData.length);
        for (int i = 0; i < decodedData.length; i++) {
            assertEquals("2", BINARY_DATA_2PAD[i], decodedData[i]);
        }
    }

    /**
     * 引数がENCODED_DATA_3PADの場合、Base64でデコードすること。
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testDecode3PAD() throws Exception {
        byte[] decodedData = base64Util.decode(ENCODED_DATA_3PAD);
        assertEquals("1", BINARY_DATA_3PAD.length, decodedData.length);
        for (int i = 0; i < decodedData.length; i++) {
            assertEquals("2", BINARY_DATA_3PAD[i], decodedData[i]);
        }
    }

    /**
     * 引数がnullの場合、NullPointerExceptionが発生すること。
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test(expected = NullPointerException.class)
    public void testDecodeNull() {
        base64Util.decode(null);
    }

    /**
     * 引数が空文字の場合、StringIndexOutOfBoundsExceptionが発生すること。
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testDecodeEmpty() {
        try {
          base64Util.decode("");
          fail();
        } catch(StringIndexOutOfBoundsException e) {
            assertEquals(StringIndexOutOfBoundsException.class,e.getClass());
            assertEquals("String index out of range:-2",e.getMessage());
        }
    }

    /**
     * 桁数は４の倍数ではない場合、StringIndexOutOfBoundsExceptionが発生すること。
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @Test
    public void testDecode4Keta() {
        try {
          base64Util.decode("123");
          fail();
        } catch(StringIndexOutOfBoundsException e) {
            assertEquals(StringIndexOutOfBoundsException.class,e.getClass());
            assertEquals("String index out of range:-4",e.getMessage());
        }
    }
}
