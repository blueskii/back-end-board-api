package com.mycompany.backendservice.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.mycompany.backendservice.dto.Member;

public class AppUserDetails extends User  {
	private Member member;
	
	public AppUserDetails(Member member, List<GrantedAuthority> authorities) {	
		super(member.getMid(), 
			  member.getMpassword(), 
			  member.isMenabled(), 
			  true, true, true, 
			  authorities);
		this.member = member;
	}

	public Member getMember() {
		return member;
	}
}
