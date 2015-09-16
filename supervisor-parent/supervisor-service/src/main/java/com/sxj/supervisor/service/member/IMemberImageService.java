package com.sxj.supervisor.service.member;

import java.util.List;

import com.sxj.supervisor.entity.member.CertificateEntity;
import com.sxj.supervisor.entity.member.CertificateLevelEntity;
import com.sxj.supervisor.entity.member.LevelEntity;
import com.sxj.supervisor.entity.member.MemberImageEntity;
import com.sxj.util.exception.ServiceException;

public interface IMemberImageService {

	public List<MemberImageEntity> getImages(String memberNo, String state,
			String certificateType) throws ServiceException;

	public List<CertificateLevelEntity> getCertificateLevels(String imageId)
			throws ServiceException;

	List<LevelEntity> getLevel(String name);

	List<CertificateEntity> getCertificate(String name);
}
