package fr.uha.ensisa.gl.lift.controller.bouchons;

import fr.ensisa.uha.ff.gl.lift.hard.Motor;
import fr.uha.ensisa.gl.lift.controller.generatedTests.Enumerations.Messages;

public class MotorBouchon extends Bouchon implements Motor {

	@Override
	public void goUp() {
		this.setMessage(Messages.goUp);
	}

	@Override
	public void goDown() {
		this.setMessage(Messages.goDown);
	}

	@Override
	public void stopMove() {
		this.setMessage(Messages.stopMove);
	}

}
