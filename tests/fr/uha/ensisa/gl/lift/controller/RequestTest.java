package fr.uha.ensisa.gl.lift.controller;

import org.junit.Test;


public class RequestTest extends CabinAtFloorTestsEM {
	@Test public void request0() {
		this.zeroPressed();
	}
	
	@Test public void request0OpenClose() {
		try {
			this.zeroPressedOpenClose();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
