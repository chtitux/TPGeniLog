package fr.uha.ensisa.gl.lift.controller;

import static org.easymock.EasyMock.*;

import org.junit.Before;
import org.junit.Test;

import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;

public class DoorClosedTest {
	public ElevatorControllerImpl sut;
	public Door door = createMock(Door.class);
	public Timer timer = createMock(Timer.class);
	public Motor motor = createMock(Motor.class);

	@Before
	public void createSut() {
		sut = new ElevatorControllerImpl();
		sut.setDoor(door);
		sut.setTimer(timer);
		sut.setMotor(motor);
		reset(door);
		reset(timer);
		reset(motor);
	}

	@Test
	public void normalClose() {
		timer.cancel();
		motor.stopMove();
		replay(timer);
		replay(motor);
		
		sut.setState(ElevatorControllerImpl.States.Running__AtFloor);
		sut.doorClosed(door);
		
		verify(timer);
		verify(motor);
	}
}