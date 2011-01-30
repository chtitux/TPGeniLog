package fr.uha.ensisa.gl.lift.soft.model.Bouchons;

import fr.ensisa.uha.ff.gl.lift.hard.Timer;
import fr.uha.ensisa.gl.lift.soft.model.Enumerations.Messages;

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
