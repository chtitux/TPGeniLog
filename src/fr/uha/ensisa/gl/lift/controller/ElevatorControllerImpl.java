package fr.uha.ensisa.gl.lift.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	private Integer nextRequestedFloor;
	private Boolean mustGoUp;
	private Boolean mustGoDown;
	private Boolean alreadyTimeout;
	private ArrayList<Integer> requestedFloors;

	public ElevatorControllerImpl() {
		this.floorSensors = new ArrayList<FloorSensor>();
		this.cabinButtons = new ArrayList<Button>();
		this.floorButtons = new ArrayList<Button>();
		
		//L'ascenceur est construit à l'étage 0, portes fermées
		this.currentFloor = 0;
		this.isBetweenFloors = false;
		this.isDoorClosed = true;

		// pas de demande d'étage
		this.requestedFloors = new ArrayList<Integer>();
		
		this.alreadyTimeout = false;
		
		this.mustGoDown = false;
		this.mustGoUp = false;
	}
	
	/**
	 * Les portes se sont ouvertes
	 */
	@Override
	public void doorOpened(Door sender) {
		if (!isBetweenFloors)
			this.getTimer().countdown(5000);
		
		this.isDoorClosed = false;
	}

	/**
	 * Les portes se sont fermées
	 */
	@Override
	public void doorClosed(Door sender) {
		this.alreadyTimeout = false;
		this.isBetweenFloors = false;
		this.isDoorClosed = true;
		this.getTimer().cancel();
		
		
		if(this.requestedFloors.size() > 0) { // Il reste des étage à desservir
			this.computeActionMotor();
		}
		
	}

	@Override
	public void motorStatusChanged(Motor sender, MotorStatus status) {
		//Rien pour le moment
	}

	@Override
	public void request(Button sender, Integer floor) {
		this.requestedFloors.add(floor);	// on enregistre l'ordre

		if (this.isBetweenFloors) {
			// On est entre 2 étages : on allume le bouton et on enregistre l'ordre
			// Le calcul du sens du moteur se fera lors de la fermeture au prochain etage
			sender.requestACK();				// on allume le bouton
			// this.requestedFloors.add(floor);	// on enregistre l'ordre // FIXME: à virer

		} else {
			// On est à un étage donné
			if (this.currentFloor != floor) {
				// On n'est pas au bon étage
				// On enregistre la demande. Si on est à l'arrêt (ie si la file contient 1 seul élément),
				// on ferme la porte si besoin OU on envoit l'ascenceur au bon endroit
				sender.requestACK();				// on allume le bouton
				// this.requestedFloors.add(floor);	// on enregistre l'ordre // FIXME: à virer
				if(this.isDoorClosed) { // Les portes sont fermées, on calcule ici
					if(this.requestedFloors.size() == 1) {
						this.computeActionMotor();
					} else { // ce n'est pas la 1ere requete
						// Les portes vont se fermer et on fait le calcul à ce moment là
					}
				} else { // les portes sont ouvertes, on les ferme
					//Sauf si on doit déservir l'étage courant !!
					if (!this.requestedFloors.contains(this.currentFloor))
						this.door.closeDoors();
				}
			} else {// On est déjà à l'étage, on ouvre les portes sans enregistrer l'etage
				this.door.openDoors();
			}
		}
		
		//Si on réouvre la porte après l'ordre de fermeture
		if (this.alreadyTimeout)
			//Si on veut changer d'étage
			if (this.currentFloor != floor)
				this.timer.countdown(5000);
			else
				this.timer.cancel();
		
	}
	
	public void computeActionMotor() {
		// Tri
		Integer temp;
		if(this.mustGoUp) { // l'ascenceur est en train de monter
			Collections.sort(this.requestedFloors);	// On trie les étages dans l'ordre croissant
			// Tant que l'étage prochain est inférieur au prochain étage dans l'immeuble, on le met après tous les autres
			temp = this.requestedFloors.get(0);
			this.requestedFloors.add(temp);
			this.requestedFloors.remove(0);
			
		} else if(this.mustGoDown) { // L'ascenceur est en train de descendre
			Collections.sort(this.requestedFloors, Collections.reverseOrder()); // On trie dans l'ordre décroissant (4,3,2,1 par ex)
			// Tant que l'étage prochain est supérieur au prochain étage dans l'immeuble, on le met après tous les autres
			// Ex : on est à l'étage 3, dont on veut 2, 1, 4, 3
			temp = this.requestedFloors.get(0);
			this.requestedFloors.add(temp);
			this.requestedFloors.remove(0);
			
		}
		// Si on est à un étage et que c'est la 1ere commande
		Integer floor = this.requestedFloors.get(0);
		if(this.currentFloor < floor) {
			this.mustGoUp = true;
			this.mustGoDown = false;
			this.motor.goUp();
		} else if(this.currentFloor > floor) {
			this.mustGoUp = false;
			this.mustGoDown = true;
			this.motor.goDown();
		}
	}

	@Override
	public void cabinAtFloor(FloorSensor sender, Integer floor) {
		//On met à jour le numéro de l'étage actuel
		this.currentFloor = floor;		// On enregistre l'étage actuel
		this.isBetweenFloors = false;	// On se trouve sur un etage

		if(this.requestedFloors.contains(floor)) {
			// L'étage atteint est demandé
			this.motor.stopMove();							// On stope le moteur
			this.requestedFloors.remove(floor);				// On enleve l'étage des listes de demande
			this.getCabinButton(floor).requestServiced();	// On acquite les boutons cabines et etage
			this.getFloorButton(floor).requestServiced();	
			this.door.openDoors();							// On ouvre les portes
		}
		
	}

	@Override
	public void cabinLeftFloor(FloorSensor sender, Integer floor) {
		this.isBetweenFloors = true;
		this.currentFloor = floor;
		
		
		// A cause d'un test (David :)
		if (this.requestedFloors.contains(floor))
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
