package fr.uha.ensisa.gl.lift.controller;

import org.junit.Before;
import org.junit.Test;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.FloorSensor;
import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

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
	// Étage 3
	public Button cb3 = createMock(Button.class);
	public Button fb3 = createMock(Button.class);
	public FloorSensor fs3 = createMock(FloorSensor.class);
	
	
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
		// Etage 3
		this.sut.setCabinButton(3, cb3);
		this.sut.setFloorButton(3, fb3);
		this.sut.setFloorSensor(3, fs3);
		
		
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
		reset(cb3);
		reset(fb3);
		reset(fs3);
	}

}
