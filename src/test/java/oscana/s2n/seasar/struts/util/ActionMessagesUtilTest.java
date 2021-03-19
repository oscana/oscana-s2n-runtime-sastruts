package oscana.s2n.seasar.struts.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import mockit.Expectations;
import mockit.Verifications;
import oscana.s2n.handler.HttpResourceHolder;
import oscana.s2n.handler.HttpResourceHolderHandler;
import oscana.s2n.struts.Globals;
import oscana.s2n.struts.action.ActionMessage;
import oscana.s2n.struts.action.ActionMessages;
import oscana.s2n.testCommon.S2NBaseTest;

/**
 * {@link ActionMessagesUtil}のテスト。
 */
public class ActionMessagesUtilTest extends S2NBaseTest {

    /**
     * @throws Exception
     */
    @Test
    public void testSaveErrors_request() throws Exception {


        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("hoge",
                    false));
            HttpServletRequest request = RequestUtil.getRequest();
            ActionMessagesUtil.saveErrors(request, errors);
            new Verifications() {
                {
                    httpServletRequest.setAttribute(anyString, any);//setAttributeを一回呼ぶこと確認
                    times = 1;
                    httpServletRequest.removeAttribute(anyString);//setAttributeを呼ばないこと確認
                    times = 0;
                }
            };

            return null;
        }));

    }

    /**
     * @throws Exception
     */
    @Test
    public void testHasErrors_request() throws Exception {
        new Expectations() {{
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("hoge",false));
            httpServletRequest.getAttribute(anyString);
            result = messages;
            minTimes = 0;

        }};
        setExecutionContext();

        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            assertTrue(ActionMessagesUtil.hasErrors(httpServletRequest));
            return null;
        }));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testHasErrors_request_errorsNotExist() throws Exception {
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            HttpServletRequest request = RequestUtil.getRequest();
            assertFalse(ActionMessagesUtil.hasErrors(request));
            return null;
        }));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testHasErrors_request_saveErrorsEmpty() throws Exception {
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            ActionMessages errors = new ActionMessages();
            HttpServletRequest request = RequestUtil.getRequest();
            ActionMessagesUtil.saveErrors(request, errors);
            assertFalse(ActionMessagesUtil.hasErrors(request));
            return null;
        }));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSaveMessages_request() throws Exception {
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("hoge",
                    false));
            HttpServletRequest request = RequestUtil.getRequest();
            ActionMessagesUtil.saveMessages(request, messages);
            new Verifications() {
                {
                    httpServletRequest.setAttribute(anyString, any);
                    times = 1;
                    httpServletRequest.removeAttribute(anyString);
                    times = 0;
                }
            };

            return null;
        }));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testHasErrors_request_saveMessagesEmpty() throws Exception {
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            ActionMessages errors = new ActionMessages();
            HttpServletRequest request = RequestUtil.getRequest();
            ActionMessagesUtil.saveMessages(request, errors);
            assertFalse(ActionMessagesUtil.hasErrors(request));
            return null;
        }));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testAddErrors_request() throws Exception {
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("hoge",
                    false));
            HttpServletRequest request = RequestUtil.getRequest();
            ActionMessagesUtil.addErrors(request, errors);

            assertNull(request.getAttribute(Globals.ERROR_KEY));
            return null;
        }));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testHasErrors_request_addErrorsEmpty() throws Exception {
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            HttpServletRequest request = RequestUtil.getRequest();
            ActionMessagesUtil.addErrors(request, null);

            assertFalse(ActionMessagesUtil.hasErrors(request));
            return null;
        }));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testAddMessages_request() throws Exception {
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("hoge",
                    false));
            HttpServletRequest request = RequestUtil.getRequest();
            ActionMessagesUtil.addMessages(request, messages);
            new Verifications() {
                {
                    httpServletRequest.getAttribute(anyString);//getAttributeを一回呼ぶこと確認
                    times = 1;
                    httpServletRequest.removeAttribute(anyString);//removeAttributeを呼ばないこと確認
                    times = 0;
                    httpServletRequest.setAttribute(anyString, any);//setAttributeを一回呼ぶこと確認
                    times = 1;
                }
            };
            return null;
        }));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testHasErrors_request_addMessagesEmpty() throws Exception {
        this.handle(Arrays.asList(new HttpResourceHolderHandler(), (data, context) -> {
            HttpServletRequest request = RequestUtil.getRequest();
            ActionMessagesUtil.addMessages(request, null);

            assertFalse(ActionMessagesUtil.hasErrors(request));
            return null;
        }));
    }

    @Override
    protected void setClassToRegist() {
        registClassList = Arrays.asList(HttpResourceHolder.class);
    }

}