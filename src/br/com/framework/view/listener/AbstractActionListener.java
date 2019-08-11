package br.com.framework.view.listener;

import static br.com.framework.core.util.ConstanteLog.LOG_LISTENER_ACTION_PERFORMED;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.framework.core.util.LogUtil;

public abstract class AbstractActionListener implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent e) {
		LogUtil.trace(String.format(LOG_LISTENER_ACTION_PERFORMED,e.getActionCommand()));
	}

}
