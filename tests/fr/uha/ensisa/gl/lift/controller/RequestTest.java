package fr.uha.ensisa.gl.lift.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.ensisa.uha.ff.gl.lift.hard.Door;


public class RequestTest {
	private boolean openDoorsSent = false;
	
	@Test public void request0() {
		ElevatorControllerImpl sut = new ElevatorControllerImpl();
		
		Door d = new Door() {
			@Override
			public void openDoors() {
				RequestTest.this.openDoorsSent = true;
			}

			@Override
			public void closeDoors() {
				// TODO Auto-generated method stub
				
			}};
		
		sut.setDoor(d);
		
		sut.request(new Button () {
			@Override
			public void requestACK() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void requestServiced() {
				// TODO Auto-generated method stub
				
			}}, 0);
		
		assertTrue(this.openDoorsSent);
	}
}
