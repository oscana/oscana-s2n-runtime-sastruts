/*
 * 取り込み元
 *    ライブラリ名：     sa-struts
 *    クラス名：         org.seasar.struts.util.ActionMessagesUtil
 *    ソースリポジトリ： https://github.com/seasarorg/sa-struts/blob/master/src/main/java/org/seasar/struts/util/ActionMessagesUtil.java
 *
 * 上記ファイルを取り込み、修正を加えた。
 *
 * Copyright 2020 TIS Inc.
 *
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package oscana.s2n.seasar.struts.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oscana.s2n.struts.Globals;
import oscana.s2n.struts.action.ActionMessages;

/**
 * ActionMessages用のユーティリティクラス。
 *
 * @author higa
 */
public final class ActionMessagesUtil {

    private ActionMessagesUtil() {
    }

    /**
     * エラーメッセージをリクエストに保存する。
     *
     * @param request
     *            リクエスト
     * @param errors
     *            エラーメッセージ
     * @since 1.0.2
     */
    public static void saveErrors(HttpServletRequest request,
            ActionMessages errors) {
        if ((errors == null) || errors.isEmpty()) {
            request.removeAttribute(Globals.ERROR_KEY);
            return;
        }
        request.setAttribute(Globals.ERROR_KEY, errors);
    }

    /**
     * エラーメッセージをセッションに保存する。
     *
     * @param session
     *            セッション
     * @param errors
     *            エラーメッセージ
     * @since 1.0.2
     */
    public static void saveErrors(HttpSession session, ActionMessages errors) {
        if ((errors == null) || errors.isEmpty()) {
            session.removeAttribute(Globals.ERROR_KEY);
            return;
        }
        session.setAttribute(Globals.ERROR_KEY, errors);
    }

    /**
     * メッセージをリクエストに保存する。
     *
     * @param request
     *            リクエスト
     * @param messages
     *            メッセージ
     * @since 1.0.2
     */
    public static void saveMessages(HttpServletRequest request,
            ActionMessages messages) {
        if ((messages == null) || messages.isEmpty()) {
            request.removeAttribute(Globals.MESSAGE_KEY);
            return;
        }
        request.setAttribute(Globals.MESSAGE_KEY, messages);
    }

    /**
     * メッセージをセッションに保存する。
     *
     * @param session
     *            セッション
     * @param messages
     *            メッセージ
     * @since 1.0.2
     */
    public static void saveMessages(HttpSession session, ActionMessages messages) {
        if ((messages == null) || messages.isEmpty()) {
            session.removeAttribute(Globals.MESSAGE_KEY);
            return;
        }
        session.setAttribute(Globals.MESSAGE_KEY, messages);
    }

    /**
     * エラーメッセージをリクエストに追加する。
     *
     * @param request
     *            リクエスト
     * @param errors
     *            エラーメッセージ
     * @since 1.0.2
     */
    public static void addErrors(HttpServletRequest request,
            ActionMessages errors) {
        if (errors == null) {
            return;
        }
        ActionMessages requestErrors = (ActionMessages) request
                .getAttribute(Globals.ERROR_KEY);
        if (requestErrors == null) {
            requestErrors = new ActionMessages();
        }
        requestErrors.add(errors);
        saveErrors(request, requestErrors);
    }

    /**
     * エラーメッセージをセッションに追加する。
     *
     * @param session
     *            セッション
     * @param errors
     *            エラーメッセージ
     * @since 1.0.2
     */
    public static void addErrors(HttpSession session, ActionMessages errors) {
        if (errors == null) {
            return;
        }
        ActionMessages sessionErrors = (ActionMessages) session
                .getAttribute(Globals.ERROR_KEY);
        if (sessionErrors == null) {
            sessionErrors = new ActionMessages();
        }
        sessionErrors.add(errors);
        saveErrors(session, sessionErrors);
    }

    /**
     * エラーメッセージがあるかどうかを返却する。
     *
     * @param request
     *            リクエスト
     * @return エラーメッセージがあるかどうか
     * @since 1.0.4
     */
    public static boolean hasErrors(HttpServletRequest request) {
        ActionMessages errors = (ActionMessages) request
                .getAttribute(Globals.ERROR_KEY);
        if (errors != null && !errors.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * メッセージをリクエストに追加する。
     *
     * @param request
     *            リクエスト
     * @param messages
     *            メッセージ
     * @since 1.0.2
     */
    public static void addMessages(HttpServletRequest request,
            ActionMessages messages) {
        if (messages == null) {
            return;
        }
        ActionMessages requestMessages = (ActionMessages) request
                .getAttribute(Globals.MESSAGE_KEY);
        if (requestMessages == null) {
            requestMessages = new ActionMessages();
        }
        requestMessages.add(messages);
        saveMessages(request, requestMessages);
    }

    /**
     * メッセージをセッションに追加する。
     *
     * @param session
     *            セッション
     * @param messages
     *            メッセージ
     * @since 1.0.2
     */
    public static void addMessages(HttpSession session, ActionMessages messages) {
        if (messages == null) {
            return;
        }
        ActionMessages sessionMessages = (ActionMessages) session
                .getAttribute(Globals.MESSAGE_KEY);
        if (sessionMessages == null) {
            sessionMessages = new ActionMessages();
        }
        sessionMessages.add(messages);
        saveMessages(session, sessionMessages);
    }
}
