package br.com.framework.model;

import java.util.Date;

import br.com.framework.model.annotation.DBColumn;

public abstract class AbstractEntidadeAuditavel extends AbstractEntidade {
	
	@DBColumn(columnName="data_criacao", dataType=DataType.NDATETIME)
	private Date dataCriacao;

	@DBColumn(columnName="data_atualizacao", dataType=DataType.NDATETIME)
	private Date dataAtualizacao;
	
	@DBColumn(columnName="data_inativacao", dataType=DataType.NDATETIME)
	private Date dataInativacao;
	
	@DBColumn(columnName="ativo", dataType=DataType.NBOOLEAN)
	private Boolean ativo;
}
