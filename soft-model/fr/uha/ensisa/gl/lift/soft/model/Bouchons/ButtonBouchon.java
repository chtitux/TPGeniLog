package fr.uha.ensisa.gl.lift.soft.model.Bouchons;

import fr.ensisa.uha.ff.gl.lift.hard.Button;
import fr.uha.ensisa.gl.lift.soft.model.Enumerations.Messages;

public class ButtonBouchon extends Bouchon implements Button {

	@Override
	public void requestACK() {
		this.setMessage(Messages.requestACK);
	}

	@Override
	public void requestServiced() {
		this.setMessage(Messages.requestServiced);
	}

}
