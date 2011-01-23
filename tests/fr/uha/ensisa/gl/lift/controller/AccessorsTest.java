package fr.uha.ensisa.gl.lift.controller;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import org.junit.Before;
import org.junit.Test;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.FloorSensor;
import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;

public class AccessorsTest {
	public ElevatorControllerImpl sut = new ElevatorControllerImpl();
	public Door door = createMock(Door.class);
	public Button cb0 = createMock(Button.class);
	public Button fb0 = createMock(Button.class);
	public FloorSensor fs0 = createMock(FloorSensor.class);
	public Button cb1 = createMock(Button.class);
	public Button fb1 = createMock(Button.class);
	public FloorSensor fs1 = createMock(FloorSensor.class);
	public Button cb2 = createMock(Button.class);
	public Button fb2 = createMock(Button.class);
	public FloorSensor fs2 = createMock(FloorSensor.class);
	public Motor motor = createMock(Motor.class);
	public Timer timer = createMock(Timer.class);

	@Before
	public void createSut() {
		sut = new ElevatorControllerImpl();
	}

	@Test
	public void doorAccessor() {
		this.sut.setDoor(door);
		assertEquals(door, sut.getDoor());
		assertNotNull(sut.getDoor());
	}

	@Test
	public void motorAccessor() {
		this.sut.setMotor(motor);
		assertEquals(motor, sut.getMotor());
		assertNotNull(sut.getMotor());
	}

	@Test
	public void CabinButtonAccessorsZero() {
		this.sut.setCabinButton(0, cb0);
		assertEquals(cb0, sut.getCabinButton(0));
		assertNotNull(sut.getCabinButton(0));
	}

	@Test
	public void CabinButtonAccessorsTwo() {
		this.sut.setCabinButton(2, cb2);
		assertEquals(cb2, sut.getCabinButton(2));
		assertNotNull(sut.getCabinButton(2));
	}
	
	@Test
	public void CabinButtonAccessorsFault() {
		assertNull(sut.getCabinButton(10));
	}

	@Test
	public void FloorButtonAccessorsZero() {
		this.sut.setFloorButton(0, fb0);
		assertEquals(fb0, sut.getFloorButton(0));
		assertNotNull(sut.getFloorButton(0));
	}

	@Test
	public void FloorButtonAccessorsTwo() {
		this.sut.setFloorButton(2, fb2);
		assertEquals(fb2, sut.getFloorButton(2));
		assertNotNull(sut.getFloorButton(2));
	}
	
	@Test
	public void FloorButtonAccessorsFault() {
		assertNull(sut.getFloorButton(10));
	}

	@Test
	public void FloorSensorAccessorsZero() {
		this.sut.setFloorSensor(0, fs0);
		assertEquals(fs0, sut.getFloorSensor(0));
		assertNotNull(sut.getFloorSensor(0));
	}

	@Test
	public void FloorSensorAccessorsTwo() {
		this.sut.setFloorSensor(2, fs2);
		assertEquals(fs2, sut.getFloorSensor(2));
		assertNotNull(sut.getFloorSensor(2));
	}
	
	@Test
	public void FloorSensorAccessorsFault() {
		assertNull(sut.getFloorSensor(10));
	}

	@Test
	public void TimerAccessors() {
		this.sut.setTimer(timer);
		assertEquals(timer, sut.getTimer());
		assertNotNull(sut.getTimer());
	}
}