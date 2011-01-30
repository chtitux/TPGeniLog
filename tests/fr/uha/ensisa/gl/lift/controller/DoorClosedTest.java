package fr.uha.ensisa.gl.lift.controller;

import static org.easymock.EasyMock.*;
import org.junit.Test;


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