package br.com.framework.core;

//Possibilidade de adicao de hash a Entidade.
public class HashableEntity {
	
	Entidade entidade;
	
	public HashableEntity(Entidade entidade) {
		this.entidade = entidade;
	}

	@Override
	public int hashCode() {
		StringBuilder sb = new StringBuilder();
		sb.append(entidade.getClass().hashCode());
		sb.append("-");
		sb.append(entidade.getId());
		return sb.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashableEntity other = (HashableEntity) obj;
		if (other.hashCode() != hashCode())
			return false;
		return true;
	}

	
}
