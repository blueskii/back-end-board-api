package com.mycompany.backendservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.backendservice.dao.MemberDao;
import com.mycompany.backendservice.dto.Member;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	public void join(Member member) {
		memberDao.insert(member);
	}
}

