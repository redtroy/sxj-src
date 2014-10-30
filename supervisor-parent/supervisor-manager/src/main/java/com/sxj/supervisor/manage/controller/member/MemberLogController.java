package com.sxj.supervisor.manage.controller.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.member.MemberLogEntity;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.member.LogQuery;
import com.sxj.supervisor.service.member.IMemberLogService;

@Controller
@RequestMapping("/member/log")
public class MemberLogController extends BaseController {

	@Autowired
	private IMemberLogService service;

	@RequestMapping("query")
	public String queryLogs(LogQuery query, ModelMap map) {
		if (query != null) {
			query.setPagable(true);
		}
		List<MemberLogEntity> list = service.queryLogs(query);
		map.put("list", list);
		map.put("query", query);
		return "manage/member/logList";
	}

	@RequestMapping("add")
	public void queryLogs(MemberLogEntity log, ModelMap map) {
		if (log == null) {
			return;
		}
		service.addLog(log);
	}
}
