package com.sxj.supervisor.website.controller.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.gather.AlEntity;
import com.sxj.supervisor.service.tasks.IAlGather;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("al")
public class AlController {

	@Autowired
	private IAlGather al;

	@RequestMapping("info")
	public @ResponseBody Map<String, List<AlEntity>> info() throws WebException {
		Map<String, List<AlEntity>> map = new HashMap<String, List<AlEntity>>();
		try {
			List<AlEntity> list = al.query();
			map.put("list", list);
		} catch (Exception e) {
			SxjLogger.error("查询铝近30天报价出错", e, this.getClass());
			throw new WebException(e.getMessage());
		}
		return map;
	}
}
