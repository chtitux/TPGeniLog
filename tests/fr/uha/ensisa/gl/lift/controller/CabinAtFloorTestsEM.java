package fr.uha.ensisa.gl.lift.controller;

import org.junit.Before;
import org.junit.Test;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.FloorSensor;
import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;
import static org.easymock.EasyMock.*;

public class CabinAtFloorTestsEM {
	public ElevatorControllerImpl sut;
	public Door door = createMock(Door.class);
	public Timer timer = createMock(Timer.class);
	public Motor motor = createMock(Motor.class);
	
	// Étage 0
	public Button cb0 = createMock(Button.class);
	public Button fb0 = createMock(Button.class);
	public FloorSensor fs0 = createMock(FloorSensor.class);
	// Étage 1
	public Button cb1 = createMock(Button.class);
	public Button fb1 = createMock(Button.class);
	public FloorSensor fs1 = createMock(FloorSensor.class);
	// Étage 2
	public Button cb2 = createMock(Button.class);
	public Button fb2 = createMock(Button.class);
	public FloorSensor fs2 = createMock(FloorSensor.class);
	
	
	@Before public void createSut() {
		this.sut = new ElevatorControllerImpl();
		this.sut.setDoor(this.door);
		this.sut.setTimer(timer);
		this.sut.setMotor(this.motor);
		// Etage 0
		this.sut.setCabinButton(0, cb0);
		this.sut.setFloorButton(0, fb0);
		this.sut.setFloorSensor(0, fs0);
		// Etage 1
		this.sut.setCabinButton(1, cb1);
		this.sut.setFloorButton(1, fb1);
		this.sut.setFloorSensor(1, fs1);
		// Etage 2
		this.sut.setCabinButton(2, cb2);
		this.sut.setFloorButton(2, fb2);
		this.sut.setFloorSensor(2, fs2);
		
		
		reset(door);
		reset(timer);
		reset(motor);
		reset(cb0);
		reset(fb0);
		reset(fs0);
		reset(cb1);
		reset(fb1);
		reset(fs1);
		reset(cb2);
		reset(fb2);
		reset(fs2);
	}
	
	@Test public void zeroPressed() {
		this.door.openDoors();
		replay(this.door);
		this.sut.request(cb0, 0);
		verify(this.door);
	}
	
	@Test public void zeroPressedOpenClose() {
		this.door.openDoors();
		replay(this.door);
		this.sut.request(cb0, 0);
		verify(this.door);
		reset(this.door);
		
		replay(this.door);
		this.sut.doorOpened(this.door);
		verify(this.door);
		reset(this.door);
		
		this.door.closeDoors();
		replay(this.door);
		this.sut.timeout(timer);
		verify(this.door);
	}
	
	@Test public void oneFromZeroPressed() {
		this.fb0.requestACK();		// Demande d'ouverture de l'exterieur
		this.door.openDoors();		// Ouverture des portes
		this.fb0.requestServiced();	// 
		
		this.cb1.requestACK();		// Appui sur le bouton "etage 1"
		this.cb1.requestACK();		// Appui 2eme fois sur le bouton "etage 1"

		this.door.closeDoors();		// Fermeture des portes
		this.motor.goUp();			// Mise en route du moteur
		this.motor.stopMove();		// Arret du moteur
		this.door.openDoors();		// Ouverture des portes
		this.cb1.requestServiced();
		
		replay(this.door);
		replay(this.motor);
		replay(this.fb0);
		replay(this.cb1);
		
		this.sut.request(fb0, 0);
		this.sut.request(this.cb1, 1);
		this.sut.request(this.cb1, 1);
		this.sut.cabinAtFloor(fs1, 1);
		
		verify(this.motor);
		verify(this.door);
		verify(this.fb0);
		verify(this.cb1);
		
		reset(this.motor);
		reset(this.door);
	}
/*	
	@Test public void oneFromZeroPressed() {
		this.motor.goUp();
		this.door.closeDoors();
		this.motor.stopMove();
		this.door.openDoors();
		
		replay(this.door);
		replay(this.motor);

		this.sut.request(this.fb1, 1);
		this.sut.cabinAtFloor(fs1, 1);
		
		verify(this.motor);
		reset(this.motor);
		reset(this.door);
	}
*/
}
