package fr.uha.ensisa.gl.lift.controller;

import static org.easymock.EasyMock.*;

import org.junit.Before;
import org.junit.Test;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.FloorSensor;
import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.ensisa.uha.ff.gl.lift.hard.QueryableMotor.MotorStatus;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;

public class DoorOpenedTest {
	public ElevatorControllerImpl sut;
	public Door door = createMock(Door.class);
	public Button cb0 = createMock(Button.class);
	public Button fb0 = createMock(Button.class);
	public Button fb2 = createMock(Button.class);
	public Button cb2 = createMock(Button.class);
	public FloorSensor fs0 = createMock(FloorSensor.class);
	public Motor motor = createMock(Motor.class);
	public Timer timer = createMock(Timer.class);

	@Before
	public void createSut() {
		sut = new ElevatorControllerImpl();
		sut.setDoor(door);
		sut.setCabinButton(0, cb0);
		sut.setFloorButton(0, fb0);
		sut.setFloorSensor(0, fs0);
		sut.setMotor(motor);
		sut.setTimer(timer);
		reset(door);
		reset(cb0);
		reset(fb0);
		reset(fs0);
		reset(motor);
		reset(timer);
	}

	@Test
	public void normalOpen() {
		timer.countdown(5000);
		replay(timer);

		sut.setState(ElevatorControllerImpl.States.Running__AtFloor);
		sut.doorOpened(door);
		verify(timer);
	}
}