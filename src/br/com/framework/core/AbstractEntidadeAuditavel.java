package br.com.framework.core;

import java.util.Date;

import br.com.framework.model.DataType;
import br.com.framework.model.annotation.DBColumn;

public abstract class AbstractEntidadeAuditavel extends AbstractEntidade {
	
	@DBColumn(columnName="data_criacao", dataType=DataType.NDATETIME_UNIXEPOCH)
	private Date dataCriacao;

	@DBColumn(columnName="data_atualizacao", dataType=DataType.NDATETIME_UNIXEPOCH)
	private Date dataAtualizacao;
	
	@DBColumn(columnName="data_inativacao", dataType=DataType.NDATETIME_UNIXEPOCH)
	private Date dataInativacao;
	
	@DBColumn(columnName="ativo", dataType=DataType.NBOOLEAN)
	private Boolean ativo;
}
