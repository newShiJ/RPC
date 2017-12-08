package com.learn.test;

import java.util.List;

import com.learn.api.model.Player;
import com.learn.api.service.PlayerService;
import com.learn.client.core.ProxyFactory;

/**
 * 测试类
 * @author Ming
 *
 */
public class Test {
	public static void main(String[] args) {
		name();
	}
	
	public static void name() {
		PlayerService service = ProxyFactory.getInstance(PlayerService.class);
		List<Player> carList = service.carList(6);
		for (Player player : carList) {
			System.out.println(player);
		}
		System.out.println("-----------------------");
		List<Player> list = service.list(5,"James");
		for (Player player : list) {
			System.out.println(player);
		}
	}
}
