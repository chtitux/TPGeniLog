package fr.uha.ensisa.gl.lift.controller.bouchons;

import fr.ensisa.uha.ff.gl.lift.hard.Timer;
import fr.uha.ensisa.gl.lift.controller.generatedTests.Enumerations.Messages;

public class TimerBouchon extends Bouchon implements Timer {

	@Override
	public void countdown(int ms) {
		this.setMessage(Messages.countdown);
	}

	@Override
	public void cancel() {
		this.setMessage(Messages.cancel);
	}

}
