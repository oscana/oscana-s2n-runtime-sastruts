package oscana.s2n.seasar.framework.util;

import static org.junit.Assert.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Test;

/**
 * {@link SAXParserFactoryUtil}のテスト
 *
 */
public class SAXParserFactoryUtilTest {

    /**
     * {@link SAXParserFactory}の新しいインスタンスを作成できること
     *
     * @throws Exception
     */
    @Test
    public void testNewInstance() {
        SAXParserFactory spf = SAXParserFactoryUtil.newInstance();
        assertNotNull(spf);
    }

    /**
     * 指定の{@link SAXParserFactory}を使って{@link SAXParser}の新しいインスタンスを作成できること
     *
     * @throws Exception
     */
    @Test
    public void testNewSAXParser() throws Exception {
        SAXParserFactory spf = SAXParserFactoryUtil.newInstance();
        SAXParser parser = SAXParserFactoryUtil.newSAXParser(spf);
        assertNotNull(parser);
    }
}