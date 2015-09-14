package com.sxj.supervisor.service.member;

import java.sql.SQLException;

import com.sun.tools.javac.util.List;
import com.sxj.supervisor.entity.member.MemberImageEntity;

public interface IMemberImageService {

	public List<MemberImageEntity> getImages() throws SQLException;
}
