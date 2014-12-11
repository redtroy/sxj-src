package com.sxj.finance.website.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class MyFreeMarkerView extends FreeMarkerView {

	private static final String CONTEXT_PATH = "basePath";

	@Override
	protected void exposeHelpers(Map<String, Object> model,
			HttpServletRequest request) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		if (session != null) {
			String function = req.getParameter("function");
			session.setAttribute("function", function);
		}

		String path = request.getContextPath() + "/";
		model.put(CONTEXT_PATH, path);
		super.exposeHelpers(model, request);
	}
}
