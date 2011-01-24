package fr.uha.ensisa.gl.lift.controller;

import java.util.ArrayList;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.ButtonListener;
import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.DoorListener;
import fr.ensisa.uha.ff.gl.lift.hard.ElevatorController;
import fr.ensisa.uha.ff.gl.lift.hard.FloorSensor;
import fr.ensisa.uha.ff.gl.lift.hard.FloorSensorListener;
import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.ensisa.uha.ff.gl.lift.hard.MotorListener;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;
import fr.ensisa.uha.ff.gl.lift.hard.QueryableMotor.MotorStatus;
import fr.ensisa.uha.ff.gl.lift.hard.TimerListener;

public class ElevatorControllerImpl
	implements ElevatorController, DoorListener, MotorListener, ButtonListener, FloorSensorListener, TimerListener {

	private Door door;
	private Motor motor;
	private Timer timer;
	private ArrayList<FloorSensor> floorSensors;
	private ArrayList<Button> cabinButtons;
	private ArrayList<Button> floorButtons;
	private Integer lastFloor;
	private Integer requestedFloor;
	private boolean doorClosed;
	private boolean isMoving;
	
	public ElevatorControllerImpl() {
		this.floorSensors = new ArrayList<FloorSensor>();
		this.cabinButtons = new ArrayList<Button>();
		this.floorButtons = new ArrayList<Button>();
		this.lastFloor = 0; // l'ascenceur est construit à l'étage 0
		this.doorClosed = true;
		this.isMoving = false;
	}
	
	@Override
	public void doorOpened(Door sender) {
		this.getTimer().countdown(5000);
		this.doorClosed = false;
	}

	@Override
	public void doorClosed(Door sender) {
		this.doorClosed = true;
	}

	@Override
	public void motorStatusChanged(Motor sender, MotorStatus status) {
		switch (status)
		{
			case GoingDownward:
				this.motor.goDown();
				break;
			case GoingUpward:
				this.motor.goUp();
				break;
			case Stopped:
				this.motor.stopMove();
				break;
		}
	}

	@Override
	public void request(Button sender, Integer floor) {
		this.requestedFloor = floor;
		sender.requestACK();
		
		// Si l'ascenceur est à l'arrêt et
		// que le dernier étage atteint est l'étage demandé, on ouvre la porte
		if(this.isMoving == false && this.lastFloor.equals(this.requestedFloor)) {
			this.door.openDoors();
			sender.requestServiced();
		} else {
			this.door.closeDoors();
			// Si les portes sont encore ouvertes => probleme
			if(this.doorClosed == false)
				return;
			
			if(this.lastFloor.compareTo(this.requestedFloor) < 0) {
				// L'étage actuel est plus petit que demandé, il faut monter
				this.motor.goUp();
			} else { // Sinon, il faut descendre
				this.motor.goDown();
			}
		}
	}

	@Override
	public void cabinAtFloor(FloorSensor sender, Integer floor) {
		this.lastFloor = floor;
		
		if(this.lastFloor.equals(this.requestedFloor)) {
			// La cabine est à l'étage demandé, on s'arrête
			this.motor.stopMove();
			this.door.openDoors();
			this.cabinButtons.get(this.lastFloor).requestServiced();
			this.floorButtons.get(this.lastFloor).requestServiced();
		}
	}

	@Override
	public void cabinLeftFloor(FloorSensor sender, Integer floor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void timeout(Timer timer) {
		this.door.closeDoors();
		timer.cancel();
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
		if(floor >= this.cabinButtons.size())
			return null;
		else
			return this.cabinButtons.get(floor);
	}

	@Override
	public Button getFloorButton(int floor) {
		if(floor >= this.floorButtons.size())
			return null;
		else
			return this.floorButtons.get(floor);
	}

	@Override
	public void setCabinButton(int floor, Button button) {
		for (int i = this.cabinButtons.size(); i<=floor; i++)
			this.cabinButtons.add(null);
		
		this.cabinButtons.set(floor, button);
	}

	@Override
	public void setFloorButton(int floor, Button button) {
		for (int i = this.floorButtons.size(); i<=floor; i++)
			this.floorButtons.add(null);
		
		this.floorButtons.set(floor, button);
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
	
	public boolean isDoorClosed() {
		return doorClosed;
	}

}
