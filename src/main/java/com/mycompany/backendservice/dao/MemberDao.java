package com.mycompany.backendservice.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backendservice.dto.Member;

@Mapper
public interface MemberDao {
	public int insert(Member member);	
	public Member selectByMid(String mid);	
	public int update(Member member);
}
