package fr.uha.ensisa.gl.lift.controller;

import static org.easymock.EasyMock.*;
import org.junit.Test;


public class DoorOpenedTest extends CabinAtFloorTestsEM {
	@Test
	public void normalOpen() {
		timer.countdown(5000);
		replay(timer);

		sut.doorOpened(door);
		verify(timer);
	}
}