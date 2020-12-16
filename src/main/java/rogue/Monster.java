package rogue;

public class Monster {

    private Room roomLocation;
    private int idNum;
    private String name;
    private double hitPoints;
    private double damageValue;

    public Monster() {

    }

	public Room getRoomLocation() {
		return this.roomLocation;
	}

	public void setRoomLocation(Room roomLocation) {
		this.roomLocation = roomLocation;
	}

	public int getIdNum() {
		return this.idNum;
	}

	public void setIdNum(int idNum) {
		this.idNum = idNum;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getHitPoints() {
		return this.hitPoints;
	}

	public void setHitPoints(double hitPoints) {
		this.hitPoints = hitPoints;
	}

	public double getDamageValue() {
		return this.damageValue;
	}

	public void setDamageValue(double damageValue) {
		this.damageValue = damageValue;
	}

}
