package br.com.framework.view;

import static br.com.framework.core.util.ConstanteLog.LOG_FRAME_SELECT;

import br.com.framework.core.util.LogUtil;
import br.com.framework.core.util.ValidatorUtil;

public class FramesManager {
	
	static private AbstractFrame frameAtual;
	static private AbstractFrame frameInicio;
	
	static public void selecionarFrame(AbstractFrame novoFrame) {
		if (ValidatorUtil.isNotEmpty(frameAtual)) {
			frameAtual.setVisible(false);
			frameAtual.clear();
		}
		frameAtual = novoFrame;
		LogUtil.trace(String.format(LOG_FRAME_SELECT, frameAtual.getName()));
		frameAtual.initialPanel();
		frameAtual.view();
	}
	
	static public void inicio() {
		selecionarFrame(frameInicio);
	}

	public static AbstractFrame getFrameInicio() {
		return frameInicio;
	}

	public static void setFrameInicio(AbstractFrame frameInicio) {
		FramesManager.frameInicio = frameInicio;
	}

}
