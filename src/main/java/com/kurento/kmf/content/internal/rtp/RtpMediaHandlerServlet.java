package com.kurento.kmf.content.internal.rtp;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kurento.kmf.content.RtpContentHandler;
import com.kurento.kmf.content.internal.base.AbstractContentHandlerServlet;
import com.kurento.kmf.content.internal.base.AbstractContentSession;
import com.kurento.kmf.spring.KurentoApplicationContextUtils;

/**
 * 
 * Handler servlet for RTP.
 * 
 * @author Luis López (llopez@gsyc.es)
 * @version 1.0.0
 */
public class RtpMediaHandlerServlet extends AbstractContentHandlerServlet {

	/**
	 * Default serial identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory
			.getLogger(RtpMediaHandlerServlet.class);

	/**
	 * Autowired RTP Handler.
	 */
	@Autowired
	private RtpContentHandler rtpMediaHandler;

	/**
	 * Always return false since RTP handler does not support redirect strategy.
	 * 
	 * @return Redirect strategy (true|false)
	 */
	@Override
	protected boolean getUseRedirectStrategy(String handlerClass)
			throws ServletException {
		return false;
	}

	/**
	 * Always return false since RTP handler does not support JSON control
	 * protocol.
	 * 
	 * @return JSON Control Protocol strategy (true|false)
	 */
	@Override
	protected boolean getUseJsonControlProtocol(String handlerClass)
			throws ServletException {
		return true;
	}

	/**
	 * Create a content Request instance (as a Spring Bean).
	 * 
	 * @param asyncCtx
	 *            Asynchronous context
	 * @param contentId
	 *            Content identifier
	 * @return Content Request
	 */
	@Override
	protected AbstractContentSession createContentSession(
			AsyncContext asyncCtx, String contentId) {
		return (RtpContentSessionImpl) KurentoApplicationContextUtils.getBean(
				"rtpMediaRequestImpl", rtpMediaHandler, contentSessionManager,
				asyncCtx, contentId);
	}

	/**
	 * Logger accessor (getter).
	 * 
	 * @return Logger
	 */
	@Override
	protected Logger getLogger() {
		return log;
	}
}