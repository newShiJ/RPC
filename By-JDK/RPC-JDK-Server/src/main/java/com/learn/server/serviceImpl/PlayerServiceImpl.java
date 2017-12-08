package com.learn.server.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.learn.api.model.Player;
import com.learn.api.service.PlayerService;

public class PlayerServiceImpl implements PlayerService{

	public Player creat() {
		return new Player(22,"cmm",new Date());
	}

	public List<Player> carList(Integer x) {
		// TODO Auto-generated method stub
		List<Player> list = new ArrayList<Player>();
		for (int i = 0; i < x; i++) {
			list.add(new Player(22*i,"cmm"+i,new Date()));
		}
		return list;
	}

	public List<Player> list(Integer count, String name) {
		List<Player> list = new ArrayList<Player>();
		for (int i = 0; i < count; i++) {
			list.add(new Player(22*i,name+i,new Date()));
		}
		return list;
	}

}
