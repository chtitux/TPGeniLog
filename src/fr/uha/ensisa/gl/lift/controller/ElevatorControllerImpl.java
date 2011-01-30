package fr.uha.ensisa.gl.lift.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

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
	private boolean isDoorOpen;
	private Boolean isBetweenFloors;
	private Integer currentFloor;
	private Integer lastRequestedFloor;
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
		this.isDoorOpen = false;

		// pas de demande d'étage
		this.requestedFloors = new ArrayList<Integer>();
		
		this.alreadyTimeout = false;
		
		this.mustGoDown = false;
		this.mustGoUp = true;
		this.lastRequestedFloor = 0;
	}
	
	/**
	 * Les portes se sont ouvertes
	 */
	@Override
	public void doorOpened(Door sender) {
		this.debug("[porte] porte ouverte");
		//this.requestedFloors.remove(this.currentFloor);	// FIXME suppr etage desservi			// On enleve l'étage des listes de demande
		
		if (!isBetweenFloors)
			this.getTimer().countdown(5000);
		
		this.isDoorClosed = false;
		this.isDoorOpen = true;
	}

	/**
	 * Les portes se sont fermées
	 */
	@Override
	public void doorClosed(Door sender) {
		this.debug("[porte] porte fermée");

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
		this.debug("[motor] statut du moteur changé !"+status);
	}

	@Override
	public void request(Button sender, Integer floor) {
		this.debug("-début request-");
		this.lastRequestedFloor = floor;
		
		this.debug("[request] demande étage "+floor);
		this.debug((this.isDoorClosed) ? "[request] porte fermée" : "[request] porte pas fermée");
		this.debug((this.isDoorOpen) ? "[request] porte ouverte" : "[request] porte pas ouverte");

		this.requestedFloors.add(floor);		// on enregistre l'ordre
		
		//Create a HashSet which allows no duplicates
		HashSet<Integer> hs = new HashSet<Integer>(this.requestedFloors);
		//Assign the HashSet to a new ArrayList
		this.requestedFloors = new ArrayList<Integer>(hs);
		
		if (this.isBetweenFloors) {
			this.debug("[request] entre 2 étages, on enregistre");
			// On est entre 2 étages : on allume le bouton et on enregistre l'ordre
			// Le calcul du sens du moteur se fera lors de la fermeture au prochain etage
			sender.requestACK();				// on allume le bouton

		} else {
			// On est à un étage donné
			if (this.currentFloor != floor) {
				this.debug("[request] pas au bon étage");
				// On n'est pas au bon étage
				// On enregistre la demande. Si on est à l'arrêt (ie si la file contient 1 seul élément),
				// on ferme la porte si besoin OU on envoit l'ascenceur au bon endroit
				sender.requestACK();				// on allume le bouton

				if(this.isDoorClosed) { // Les portes sont fermées, on calcule ici
					this.debug("[request]  les portes sont fermées, on lance le moteur");
					this.computeActionMotor();
					
				} else { // les portes ne sont pas fermées. Elles vont se fermer toute seules
					this.debug("[request]  les portes ne sont pas fermées");
					this.debug("[request]  on attend la fermeture auto des portes");
				}
			} else {// On est déjà à l'étage, on ouvre les portes sans enregistrer l'etage
				this.openDoors();
			}
		}
		
		//Si on réouvre la porte après l'ordre de fermeture
		if (this.alreadyTimeout)
			//Si on veut changer d'étage
			if (this.currentFloor != floor)
				this.timer.countdown(5000);
			else
				this.timer.cancel();
		
		System.out.print("[request] étages demandés: ");
		for(int i = 0; i < this.requestedFloors.size(); i++) {
			System.out.print(this.requestedFloors.get(i)+", ");
		}
		this.debug("\n-fin request-");
	}
	
	public void computeActionMotor() {
		// Tri
		System.out.print("[compute] étages demandés: ");
		for(int i = 0; i < this.requestedFloors.size(); i++) {
			System.out.print(this.requestedFloors.get(i)+", ");
			
		}
		this.debug("");
		if(this.requestedFloors.size() == 0)
			return; // si pas d'etage à servir, ça ne sert à rien
		
		if(this.lastRequestedFloor == this.currentFloor)
			return;
		
		if(this.mustGoDown) {
			// l'ascenceur était en train de descendre
			
			if(this.getMinRequestedFloor() > this.currentFloor) {
				// le plus bas étage demandé est plus haut que l'actuel, on monte
				this.mustGoUp = true;
				this.mustGoDown = false;
				this.motor.goUp();
			} else { // sinon, le plus haut étage demandé est plus bas que l'actuel, on descend
				this.mustGoUp = false;
				this.mustGoDown = true;
				this.motor.goDown();
			}
		} else {
			// l'ascenceur était en train de monter
			
			if(this.getMaxRequestedFloor() > this.currentFloor) {
				// S'il reste un étage plus bas, on continue à monter
				this.mustGoUp = true;
				this.mustGoDown = false;
				this.motor.goUp();
			} else { // sinon, le plus haut étage demandé est plus bas que l'actuel, on descend
				this.mustGoUp = false;
				this.mustGoDown = true;
				this.motor.goDown();
			}
		}
		
		this.debug("[compute] up: "+this.mustGoUp+", down:"+this.mustGoDown);

	}
	
	private Integer getMaxRequestedFloor() {
		return Collections.max(this.requestedFloors);
	}
	
	private Integer getMinRequestedFloor() {
		return Collections.min(this.requestedFloors);
	}

	@Override
	public void cabinAtFloor(FloorSensor sender, Integer floor) {
		this.debug("[arrivee]  arrivée à l'étage "+floor);

		//On met à jour le numéro de l'étage actuel
		this.currentFloor = floor;		// On enregistre l'étage actuel
		this.isBetweenFloors = false;	// On se trouve sur un etage

		if(this.requestedFloors.contains(floor)) {
			// L'étage atteint est demandé
			this.motor.stopMove();							// On stope le moteur
			this.debug("[arrivee]   suppression de l'etage "+floor);
			this.requestedFloors.remove(floor);	// FIXME : deplacement ouverture porte			// On enleve l'étage des listes de demande
			this.getCabinButton(floor).requestServiced();	// On acquite les boutons cabines et etage
			this.getFloorButton(floor).requestServiced();	
			this.openDoors();							// On ouvre les portes
		}
		
	}

	@Override
	public void cabinLeftFloor(FloorSensor sender, Integer floor) {
		this.debug("[depart] départ de l'étage "+floor);
		this.isBetweenFloors = true;
		this.currentFloor = floor;
		
		this.debug("[depart] dernier etage demande :"+this.lastRequestedFloor);
		// Etat d'urgence : on stoppe quand :
		// on vient de quitter l'etage, mais qu'il vient d'etre demande
		if (this.lastRequestedFloor == this.currentFloor) {
			this.debug("[depart] arret d'urgence");
			this.motor.stopMove();
		}
			
	}

	@Override
	public void timeout(Timer timer) {
		if (!this.alreadyTimeout) {
			this.isBetweenFloors = false;
			this.closeDoors();
			this.timer.countdown(5000);
		}
		else {
			this.openDoors();
		}
		this.alreadyTimeout = true;
	}
	
	protected void closeDoors() {
		this.debug("[porte] : fermeture");
		this.isDoorOpen = false;
		this.door.closeDoors();
	}
	
	protected void openDoors() {
		this.debug("[porte] : ouverture");
		this.isDoorClosed = false;
		this.door.openDoors();
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
	
	protected void debug(String message) {
		System.out.println(message);
	}
	
}
