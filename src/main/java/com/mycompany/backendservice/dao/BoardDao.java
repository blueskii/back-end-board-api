package com.mycompany.backendservice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backendservice.dto.Board;
import com.mycompany.backendservice.dto.Pager;

@Mapper
public interface BoardDao {
	public List<Board> selectByPage(Pager pager);
	public int count();
	public Board selectByBno(int bno);
	public int insert(Board board);
	public int deleteByBno(int bno);
	public int updateByBno(Board board);
	public int updateBhitcount(int bno);
}
