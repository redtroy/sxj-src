package com.sxj.supervisor.model.member;

import java.io.Serializable;
import java.util.List;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.MemberImageEntity;

public class MemberModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6794022001507567153L;

	private MemberEntity member;

	private List<MemberImageEntity> imageList;

	public MemberEntity getMember() {
		return member;
	}

	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public List<MemberImageEntity> getImageList() {
		return imageList;
	}

	public void setImageList(List<MemberImageEntity> imageList) {
		this.imageList = imageList;
	}

}
