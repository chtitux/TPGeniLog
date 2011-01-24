package fr.uha.ensisa.gl.lift.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import fr.ensisa.uha.ff.gl.lift.hard.ElevatorController;
import fr.ensisa.uha.ff.gl.lift.hard.svg.generator.SVGLiftGenerator;
import fr.ensisa.uha.ff.gl.lift.hard.svg.swing.JLiftFrame;
import fr.uha.ensisa.ff.gl.lift.async.ElevatorBroker;

public class Main {

	public static void main(String[] args)
	{
		final SVGLiftGenerator hard = new SVGLiftGenerator(4);
		final JLiftFrame frame = new JLiftFrame(hard);
		final ElevatorController controller = new fr.uha.ensisa.gl.lift.controller.ElevatorControllerImpl();
		final ElevatorBroker runner = new ElevatorBroker(controller, hard);
		frame.addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
		super.windowClosing(e);
		runner.stop();
		frame.dispose();
		}});
		runner.start();
		frame.centerOnScreen();
		frame.pack();
		frame.setVisible(true);
		hard.setTypicalBugFrequency(10000);
		//hard.setBugged(true);
	}
}
