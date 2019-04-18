/*
 * Created on Aug 4, 2006 
 */
package martijn.quoridor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

import javax.swing.JFrame;

import martijn.quoridor.brains.BrainFactory;
import martijn.quoridor.brains.DefaultBrainFactory;
import martijn.quoridor.ui.ComboPane;

/**
 * The application's main entry point.
 * 
 * @author Martijn van Steenbergen
 */
public class QuoridorApplication {

	/** Launches Quoridor with a {@link DefaultBrainFactory}. */
	public static void launch() {
		launch(new DefaultBrainFactory());
	}

	/** Launches Quoridor with the brains created by the specified factory. */
	public static void launch(BrainFactory factory) {
		JFrame f = new JFrame("Quoridor");
		ComboPane combo = new ComboPane(factory);
		f.setContentPane(combo);
		f.setSize(800, 1000);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/** Launches Quoridor. */
	public static void main(String[] args) {
		launch();
	}

}
