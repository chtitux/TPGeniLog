package fr.uha.ensisa.gl.lift.controller.bouchons;

import fr.ensisa.uha.ff.gl.lift.hard.Door;
import fr.uha.ensisa.gl.lift.controller.generatedTests.Enumerations.Messages;

public class DoorBouchon extends Bouchon implements Door {

	@Override
	public void openDoors() {
		this.setMessage(Messages.openDoors);
	}

	@Override
	public void closeDoors() {
		this.setMessage(Messages.closeDoors);
	}

}
