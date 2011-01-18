package fr.uha.ensisa.gl.lift.controller;

import org.junit.Before;
import org.junit.Test;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.FloorSensor;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;
import static org.easymock.EasyMock.*;

public class CabinAtFloorTestsEM {
	public ElevatorControllerImpl sut;
	public Door door = createMock(Door.class);
	public Button cb0 = createMock(Button.class);
	public Button fb0 = createMock(Button.class);
	public FloorSensor fs0 = createMock(FloorSensor.class);
	public Timer timer = createMock(Timer.class);
	
	@Before public void createSut() {
		this.sut = new ElevatorControllerImpl();
		this.sut.setDoor(this.door);
		this.sut.setCabinButton(0, cb0);
		this.sut.setFloorButton(0, fb0);
		this.sut.setFloorSensor(0, fs0);
		
		this.sut.setTimer(timer);
		
		reset(door);
		reset(cb0);
		reset(fb0);
		reset(fs0);
		reset(timer);
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
		this.sut.timeout(timer);
		verify(this.door);
		reset(this.door);
		
		this.door.closeDoors();
		replay(this.door);
		this.sut.timeout(timer);
		verify(this.door);
	}
}
