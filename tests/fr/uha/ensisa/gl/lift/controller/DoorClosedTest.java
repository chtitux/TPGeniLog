package fr.uha.ensisa.gl.lift.controller;

import static org.easymock.EasyMock.*;

import org.junit.Before;
import org.junit.Test;

import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.ensisa.uha.ff.gl.lift.hard.Timer;

public class DoorClosedTest  extends CabinAtFloorTestsEM {
	@Test
	public void normalClose() {
		timer.cancel();
		replay(timer);
		replay(motor);
		
		sut.doorClosed(door);
		
		verify(timer);
	}
}