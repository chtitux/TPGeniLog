package fr.uha.ensisa.gl.lift.controller;

import java.util.ArrayList;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.ElevatorController;
import fr.ensisa.uha.ff.gl.lift.hard.FloorSensor;
import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;
import fr.ensisa.uha.ff.gl.lift.hard.QueryableMotor.MotorStatus;

public class ElevatorControllerImpl implements ElevatorController {

	private Door door;
	private Motor motor;
	private Timer timer;
	private ArrayList<FloorSensor> floorSensors;

	public ElevatorControllerImpl() {
		this.floorSensors = new ArrayList<FloorSensor>();
	}
	
	@Override
	public void doorOpened(Door sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doorClosed(Door sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void motorStatusChanged(Motor sender, MotorStatus status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void request(Button sender, Integer floor) {
		this.door.openDoors();
	}

	@Override
	public void cabinAtFloor(FloorSensor sender, Integer floor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cabinLeftFloor(FloorSensor sender, Integer floor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void timeout(Timer timer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Door getDoor() {
		return this.door;
	}

	@Override
	public void setDoor(Door door) {
		this.door = door;
	}

	@Override
	public Motor getMotor() {
		return this.motor;
	}

	@Override
	public void setMotor(Motor motor) {
		this.motor = motor;
	}

	@Override
	public Button getCabinButton(int floor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Button getFloorButton(int floor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCabinButton(int floor, Button button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFloorButton(int floor, Button button) {
		// TODO Auto-generated method stub

	}

	@Override
	public FloorSensor getFloorSensor(int floor) {
		if (floor >= this.floorSensors.size())
			return null;
		else
			return this.floorSensors.get(floor);
	}

	@Override
	public void setFloorSensor(int floor, FloorSensor sensor) {
		for (int i = this.floorSensors.size(); i<=floor; i++)
			this.floorSensors.add(null);
		
		this.floorSensors.set(floor, sensor);
	}

	@Override
	public Timer getTimer() {
		return this.timer;
	}

	@Override
	public void setTimer(Timer t) {
		this.timer = t;
	}

}
