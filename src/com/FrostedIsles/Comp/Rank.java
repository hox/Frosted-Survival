package com.FrostedIsles.Comp;

public enum Rank {
	
	Default(0),
	Builder(1),
	Moderator(2),
	Admin(3),
	Manager(4),
	Owner(Integer.MAX_VALUE);
	
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
	public static int Builder() {
		return Builder.getRank();
	}

	/**Rank: 2*/
	public static int Moderator() {
		return Moderator.getRank();
	}

	/**Rank: 3*/
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
