package com.guochaojava.shiro.session;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * @author guochao.
 * @since
 */
public class CustomSessionManager extends DefaultWebSessionManager {

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable serializable = getSessionId(sessionKey);
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }
        if (request != null && serializable != null) {
            Session session = (Session) request.getAttribute(serializable.toString());
            if (session != null) {
                return session;
            }
        }
        Session session = super.retrieveSession(sessionKey);
        if (request != null && serializable != null) {
            request.setAttribute(serializable.toString(), session);
        }

        return session;
    }
}