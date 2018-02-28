package com.FrostedIsles.Economy;

import java.util.HashMap;

import com.FrostedIsles.Comp.Main;

public class EconomyManager {


	public static HashMap<String, Double> bal = new HashMap<>(); // PlayerName , Balance
	
	public static void setBalance(String player, double amount) {
		bal.put(player, amount);
	}
	
	public static Double getBalance(String player) {
		return bal.get(player);
	}
	public static boolean hasAccount(String player) {
		return bal.containsKey(player);
	}
	
	public static HashMap<String, Double> getBalanceMap() {
		return bal;
	}
	

}
