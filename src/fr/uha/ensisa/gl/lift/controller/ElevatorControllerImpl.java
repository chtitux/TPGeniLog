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
	private Boolean isDoorClosed;
	private Boolean isBetweenFloors;
	private Integer currentFloor;
	private Integer requestedFloor;
	private Boolean mustGoUp;
	private Boolean mustGoDown;
	private Boolean mustStay[];
	private Boolean alreadyAtFloor[];
	
	public enum States {
		Running__DoorOpened__Closing,
		Running__BetweenFloors,
		Running__DoorOpened,
		Running,
		Running__AtFloor,
		Running__Init,
		Running__DoorOpened__LetInOut,
		Emergency,
		Running__DoorOpened__Init,
		Init,
		Running__DoorOpened__ReopeningDoors,
		Running__DoorOpened__Final,
		Running__Emergency
		}
	private States state;
	
	public ElevatorControllerImpl() {
		this.floorSensors = new ArrayList<FloorSensor>();
		this.cabinButtons = new ArrayList<Button>();
		this.floorButtons = new ArrayList<Button>();
		
		//L'ascenceur est construit à l'étage 0, portes fermées
		this.currentFloor = 0;
		this.requestedFloor = 0;
		this.state = States.Running__AtFloor;
		this.isBetweenFloors = false;
		this.isDoorClosed = true;
		this.mustGoDown = false;
		this.mustGoUp = false;
		
		this.mustStay = new Boolean[3];
		this.mustStay[0] = false;
		this.mustStay[1] = false;
		this.mustStay[2] = false;
	}
	
	@Override
	public void doorOpened(Door sender) {
		this.state = States.Running__DoorOpened__LetInOut;
		//this.mustGoDown = false;
		//this.mustGoUp = false;
		
		if (!isBetweenFloors)
			this.getTimer().countdown(5000);
		
		this.isDoorClosed = false;
		this.mustStay[currentFloor] = false;
	}

	@Override
	public void doorClosed(Door sender) {
		this.state = States.Running__AtFloor;
		this.isBetweenFloors = false;
		this.isDoorClosed = true;
		this.getTimer().cancel();
		
		//On fait bouger le moteur
		if (this.currentFloor < this.requestedFloor) {
			this.mustGoUp = true;
			this.motor.goUp();
		}
		else if (this.currentFloor > this.requestedFloor) {
			this.mustGoDown = true;
			this.motor.goDown();
		}
		else if (this.currentFloor == this.requestedFloor && !this.mustGoDown && !this.mustGoUp) {
			this.mustGoDown = false;
			this.mustGoUp = false;
			//this.motor.stopMove();
		}
	}

	@Override
	public void motorStatusChanged(Motor sender, MotorStatus status) {
		this.isBetweenFloors = true;
		this.motor.stopMove();
		/*
		switch(this.state)
		{
			case Running__AtFloor:
				this.state = States.Running__BetweenFloors;
				break;
		}
		
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
		*/
	}

	@Override
	public void request(Button sender, Integer floor) {
		this.requestedFloor = floor;
		this.mustStay[floor] = true;
		
		if (this.currentFloor != floor || this.isBetweenFloors)
			sender.requestACK();
			
		if (this.requestedFloor == this.currentFloor || this.mustStay[currentFloor])
			this.door.openDoors();
		
		if (isDoorClosed && !this.mustStay[currentFloor]) {
			//On fait bouger le moteur
			if (this.currentFloor < this.requestedFloor) {
				this.mustGoUp = true;
				this.motor.goUp();
			}
			else if (this.currentFloor > this.requestedFloor) {
				this.mustGoDown = true;
				this.motor.goDown();
			}
			else if (this.currentFloor == this.requestedFloor && !this.mustGoDown && !this.mustGoUp) {
				this.mustGoDown = false;
				this.mustGoUp = false;
				//this.motor.stopMove();
			}
		}
		
		
		//this.requestedFloor = floor;
		//if (this.currentFloor.compareTo(this.requestedFloor) != 0)
			//sender.requestACK();
		/*
		// Si l'ascenceur est à l'arrêt et
		// que le dernier étage atteint est l'étage demandé, on ouvre la porte
		if(this.isMoving == false && this.currentFloor.equals(this.requestedFloor)) {
			this.door.openDoors();
			//sender.requestServiced();
		} else {
			if(!this.doorClosed)
			// Si les portes ne sont pas ouvertes, on les fermes
				this.door.closeDoors();
			
			if(this.currentFloor.compareTo(this.requestedFloor) < 0) {
				// L'étage actuel est plus petit que demandé, il faut monter
				this.motor.goUp();
			} else { // Sinon, il faut descendre
				this.motor.goDown();
			}
		}
		*/
	}

	@Override
	public void cabinAtFloor(FloorSensor sender, Integer floor) {
		this.state = States.Running__AtFloor;
		
		//On notifie l'arrivée de l'ascenseur aux boutons de la cabine et de l'étage,
		//mais que si on vient de changer d'étage
		if (this.currentFloor != floor)
		{
			this.getCabinButton(floor).requestServiced();
			this.getFloorButton(floor).requestServiced();
		}
		
		//On met à jour le numéro de l'étage actuel
		this.currentFloor = floor;
		
		//On stoppe le moteur si on est arrivé et on demande l'ouverture des portes
		if(this.currentFloor == this.requestedFloor) {
			this.motor.stopMove();
			this.door.openDoors();
		}
	}

	@Override
	public void cabinLeftFloor(FloorSensor sender, Integer floor) {
		this.state = States.Running__BetweenFloors;
		this.isBetweenFloors = true;
		this.currentFloor = floor;
	}

	@Override
	public void timeout(Timer timer) {
		this.state = States.Running__DoorOpened__Closing;
		this.isBetweenFloors = false;
		this.door.closeDoors();
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
	/*
	public boolean isDoorClosed() {
		return doorClosed;
	}
	*/
	public void setState(States state) {
		this.state = state;
	}

}
