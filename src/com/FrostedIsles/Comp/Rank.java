package com.FrostedIsles.Comp;

public enum Rank {
	
	Default(0),
	VIP(1),
	VIPPlus(2),
	Builder(Util.max-4),
	Moderator(Util.max-3),
	Admin(Util.max-2),
	Manager(Util.max-1),
	Owner(Util.max);
	
	private final int rank;
	
	Rank(int rank) {
		this.rank = rank;
	}
	
	/**Get rank value. Higher value = higher rank*/
	public int getRank() {
		return rank;
	}
	
	
	// TODO: Ranks

	/**Rank: 0*/
	public static int Default() {
		return Default.getRank();
	}
	
	/**Rank: 1*/
	public static int VIP() {
		return VIP.getRank();
	}
	
	/**Rank: 2*/
	public static int VIPPlus() {
		return VIPPlus.getRank();
	}

	/**Rank: 2147483643*/
	public static int Builder() {
		return Builder.getRank();
	}

	/**Rank: 2147483644*/
	public static int Moderator() {
		return Moderator.getRank();
	}

	/**Rank: 2147483645*/
	public static int Admin() {
		return Admin.getRank();
	}

	/**Rank: 2147483646*/
	public static int Manager() {
		return Manager.getRank();
	}

	/**Rank: 2147483647*/
	public static int Owner() {
		return Owner.getRank();
	}
}
