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
	public Boolean alreadyTimeout;
	
	public ElevatorControllerImpl() {
		this.floorSensors = new ArrayList<FloorSensor>();
		this.cabinButtons = new ArrayList<Button>();
		this.floorButtons = new ArrayList<Button>();
		
		//L'ascenceur est construit à l'étage 0, portes fermées
		this.currentFloor = 0;
		this.requestedFloor = 0;
		this.isBetweenFloors = false;
		this.isDoorClosed = true;
		this.mustGoDown = false;
		this.mustGoUp = false;
		
		this.mustStay = new Boolean[3];
		this.mustStay[0] = false;
		this.mustStay[1] = false;
		this.mustStay[2] = false;
		
		this.alreadyTimeout = false;
	}
	
	@Override
	public void doorOpened(Door sender) {
		if (!isBetweenFloors)
			this.getTimer().countdown(5000);
		
		this.isDoorClosed = false;
	}

	@Override
	public void doorClosed(Door sender) {
		this.alreadyTimeout = false;
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
		}
		
		this.mustStay[currentFloor] = false;
	}

	@Override
	public void motorStatusChanged(Motor sender, MotorStatus status) {
		//Rien pour le moment
	}

	@Override
	public void request(Button sender, Integer floor) {
		this.requestedFloor = floor;
		this.mustStay[floor] = true;
		
		//Si on n'est pas à l'étage demandé ou si on vient d'en partir
		if (this.currentFloor != floor || this.isBetweenFloors)
			sender.requestACK();
		
		//Si on doit ouvrir la porte alors qu'on ne dirait pas
		if (this.requestedFloor == this.currentFloor || this.mustStay[currentFloor] && !this.alreadyTimeout)
			this.door.openDoors();
		
		//Si on réouvre la porte après l'ordre de fermeture
		if (this.alreadyTimeout)
			//Si on veut changer d'étage
			if (this.currentFloor != this.requestedFloor)
				this.timer.countdown(5000);
			else
				this.timer.cancel();
		
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
			}
		}
	}

	@Override
	public void cabinAtFloor(FloorSensor sender, Integer floor) {
		//On met à jour le numéro de l'étage actuel
		this.currentFloor = floor;
		
		//On stoppe le moteur si on est arrivé et on demande l'ouverture des portes
		//On notifie l'arrivée de l'ascenseur aux boutons de la cabine et de l'étage,
		//mais que si on vient de changer d'étage
		if(this.currentFloor == this.requestedFloor) {
			this.motor.stopMove();
			this.door.openDoors();
			
			this.getCabinButton(floor).requestServiced();
			this.getFloorButton(floor).requestServiced();
		}
		
		this.isBetweenFloors = false;
	}

	@Override
	public void cabinLeftFloor(FloorSensor sender, Integer floor) {
		this.isBetweenFloors = true;
		this.currentFloor = floor;
		
		if (this.mustStay[floor])
			this.motor.stopMove();
	}

	@Override
	public void timeout(Timer timer) {
		if (!this.alreadyTimeout) {
			this.isBetweenFloors = false;
			this.door.closeDoors();
			this.timer.countdown(5000);
		}
		else {
			this.door.openDoors();
		}
		this.alreadyTimeout = true;
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
}
