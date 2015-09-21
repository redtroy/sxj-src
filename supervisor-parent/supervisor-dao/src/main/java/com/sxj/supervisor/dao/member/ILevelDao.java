package com.sxj.supervisor.dao.member;

import java.util.List;

import com.sxj.supervisor.entity.member.LevelEntity;

public interface ILevelDao {

	public List<LevelEntity> getLevel(String name);
}
