package com.learn.api.service;

import java.util.List;

import com.learn.api.model.Player;



public interface PlayerService {
	Player creat();
	
	List<Player> carList(Integer x);
	
	List<Player> list(Integer count,String name);
}
