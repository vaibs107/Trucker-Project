package io.egen.trucker.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tire {

	@Id
	private String tireId;

	private int frontLeft;
	private int frontRight;
	private int rearLeft;
	private int rearRight;

	public Tire() {
		this.tireId = UUID.randomUUID().toString();
	}

	public String getTireId() {
		return tireId;
	}

	public void setTireId(String tireId) {
		this.tireId = tireId;
	}

	public int getFrontLeft() {
		return frontLeft;
	}

	public void setFrontLeft(int frontLeft) {
		this.frontLeft = frontLeft;
	}

	public int getFrontRight() {
		return frontRight;
	}

	public void setFrontRight(int frontRight) {
		this.frontRight = frontRight;
	}

	public int getRearLeft() {
		return rearLeft;
	}

	public void setRearLeft(int rearLeft) {
		this.rearLeft = rearLeft;
	}

	public int getRearRight() {
		return rearRight;
	}

	public void setRearRight(int rearRight) {
		this.rearRight = rearRight;
	}

	@Override
	public String toString() {
		return "Tires [tireId=" + tireId + ", frontLeft=" + frontLeft + ", frontRight=" + frontRight + ", rearLeft="
				+ rearLeft + ", rearRight=" + rearRight + "]";
	}

}