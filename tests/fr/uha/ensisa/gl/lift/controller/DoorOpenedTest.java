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

public class DoorOpenedTest extends CabinAtFloorTestsEM {
	@Test
	public void normalOpen() {
		timer.countdown(5000);
		replay(timer);

		sut.setState(ElevatorControllerImpl.States.Running__AtFloor);
		sut.doorOpened(door);
		verify(timer);
	}
}