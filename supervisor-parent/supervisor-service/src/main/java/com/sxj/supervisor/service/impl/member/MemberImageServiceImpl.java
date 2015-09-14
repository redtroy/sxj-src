package com.sxj.supervisor.service.impl.member;

import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.tools.javac.util.List;
import com.sxj.supervisor.entity.member.MemberImageEntity;
import com.sxj.supervisor.service.member.IMemberImageService;

@Service
@Transactional
public class MemberImageServiceImpl implements IMemberImageService {

	@Override
	public List<MemberImageEntity> getImages() throws SQLException {
		return null;
	}

}
