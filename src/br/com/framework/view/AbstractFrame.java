package br.com.framework.view;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import br.com.framework.util.LogUtil;
import br.com.framework.view.listener.AbstractActionListener;

public abstract class AbstractFrame extends JFrame {
	
	public AbstractFrame(String frameName) {
		this.setName(frameName);

		//TODO : validar se melhor forma de sair
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// implementar em FramesManager??
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				LogUtil.trace("Finalizando.");
				System.exit(0);				
			}
		});
	}
	
	public void view() {
		this.pack();
		this.setVisible(true);
	}
	
	public abstract void initialPanel();	//GridLayout/GridBagLayout

	public abstract void clear();

}
