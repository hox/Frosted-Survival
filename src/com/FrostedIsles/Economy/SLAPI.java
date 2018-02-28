package com.FrostedIsles.Economy;

import com.FrostedIsles.Comp.ConfigurationManager;
import com.FrostedIsles.Comp.Main;

public class SLAPI {
	private static ConfigurationManager config;
	
	public static void saveBalances() {
	for(String p : EconomyManager.getBalanceMap().keySet()) {
		config.data.set("balance."+p, EconomyManager.getBalanceMap().get(p));
	}
	}
}
