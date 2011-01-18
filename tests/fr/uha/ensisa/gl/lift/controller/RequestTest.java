package fr.uha.ensisa.gl.lift.controller;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.Test;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.Door;


public class RequestTest {
	@Test public void request0() {
		ElevatorControllerImpl mock = createMock(ElevatorControllerImpl.class);
		ElevatorControllerImpl sut = new ElevatorControllerImpl();
		
		Door mockDoor = createMock(Door.class);
		sut.setDoor(mockDoor);
		Button mockButton = createMock(Button.class);
		
		
		
		sut.request(mockButton, 0);
		
		//assertTrue(this.openDoorsSent);
	}
}
