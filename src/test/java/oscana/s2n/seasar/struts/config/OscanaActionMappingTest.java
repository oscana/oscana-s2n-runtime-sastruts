package oscana.s2n.seasar.struts.config;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import mockit.Expectations;
import oscana.s2n.handler.HttpResourceHolder;
import oscana.s2n.handler.HttpResourceHolderHandler;
import oscana.s2n.testCommon.S2NBaseTest;

/**
 * {@link OscanaActionMapping}のテスト。
 *
 */
public class OscanaActionMappingTest extends S2NBaseTest {

    /**
     * パスを戻る
     */
    @Test
    public void testGetPath() {
        new Expectations() {{

            httpServletRequest.getPathInfo();
            result = "/test";

        }};
        this.handle(Arrays.asList(new HttpResourceHolderHandler(),(data, context) -> {
            OscanaActionMapping s2ActionMapping = new OscanaActionMapping();
            assertEquals("/test",s2ActionMapping.getPath());
            return null;
        }));
    }

    @Override
    protected void setClassToRegist() {
        registClassList = Arrays.asList(HttpResourceHolder.class);
    }

}