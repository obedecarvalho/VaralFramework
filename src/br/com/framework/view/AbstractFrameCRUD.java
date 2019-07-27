package br.com.framework.view;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

import br.com.framework.view.listener.AbstractActionListener;

public abstract class AbstractFrameCRUD extends AbstractFrame {
	
	public AbstractFrameCRUD(String frameName) {
		super(frameName);
	}

	public abstract void editPanel();
	
	public abstract void newPanel();
	
	public abstract void viewPanel();
	
	public abstract void listPanel();
	
	public abstract void deletePanel();
	
	public abstract void searchPanel();
	
	protected JButton addBtnInicio() {
		JButton btnInicio = new JButton("Início");
		btnInicio.addActionListener(new AbstractActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				super.actionPerformed(e);
				FramesManager.inicio();
			}
		});
		return btnInicio;		
	}
	
	protected JLabel addLblNomeFrame() {
		JLabel lblNomeFrame = new JLabel(getName());
		return lblNomeFrame;
		
	}

}
