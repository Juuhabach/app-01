package br.upf.fabsoft.jmkidsmodel.nuvem;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(schema = "nuvem")
public class Brinquedo implements Serializable {

	@Id
	// @GeneratedValue(strategy = SEQUENCE, generator = "BrinquedoID")
	// @SequenceGenerator(name = "BrinquedoID", sequenceName = "BrinquedoID",
	// allocationSize = 1, schema = "nuvem")
	private Long id;
	@Length(max = 60)
	@Column(length = 60)
	private String chave;
	@Length(min = 2, max = 60)
	@NotEmpty
	@Column(length = 60)
	private String descricao;
	@Lob
	private String infTecnicas;
	@Temporal(TemporalType.DATE)
	private Date dataAquisicao;
	@DecimalMin(value = "0", inclusive = true)
	private Double valorAquisicao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraDesabilitado;
	@Lob
	private String motivoDesabilitado;
	@NotNull
	@Min(value = 0)
	private Integer tempoMinimo;
	@Basic(optional = true)
	@Column(name = "foto", columnDefinition = "BYTEA")
	private byte[] foto;
	// @Version
	private Integer versao;

	public Brinquedo() {
		super();
	}

	public Brinquedo(Long id, @Length(max = 60) String chave, @Length(min = 2, max = 60) @NotEmpty String descricao,
			String infTecnicas, Date dataAquisicao, @DecimalMin(value = "0", inclusive = true) Double valorAquisicao,
			Date dataHoraDesabilitado, String motivoDesabilitado, byte[] foto, @NotNull @Min(0) Integer tempoMinimo,
			Integer versao) {
		super();
		this.id = id;
		this.chave = chave;
		this.descricao = descricao;
		this.infTecnicas = infTecnicas;
		this.dataAquisicao = dataAquisicao;
		this.valorAquisicao = valorAquisicao;
		this.dataHoraDesabilitado = dataHoraDesabilitado;
		this.motivoDesabilitado = motivoDesabilitado;
		this.foto = foto;
		this.tempoMinimo = tempoMinimo;
		this.versao = versao;
	}

	public Brinquedo(Long id, @Length(max = 60) String chave, @Length(min = 2, max = 60) @NotEmpty String descricao,
			String infTecnicas, Date dataAquisicao, @DecimalMin(value = "0", inclusive = true) Double valorAquisicao,
			Date dataHoraDesabilitado, String motivoDesabilitado, String foto, @NotNull @Min(0) Integer tempoMinimo,
			Integer versao) {
		super();
		this.id = id;
		this.chave = chave;
		this.descricao = descricao;
		this.infTecnicas = infTecnicas;
		this.dataAquisicao = dataAquisicao;
		this.valorAquisicao = valorAquisicao;
		this.dataHoraDesabilitado = dataHoraDesabilitado;
		this.motivoDesabilitado = motivoDesabilitado;
		this.foto = foto.getBytes();
		this.tempoMinimo = tempoMinimo;
		this.versao = versao;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChave() {
		return this.chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getInfTecnicas() {
		return this.infTecnicas;
	}

	public void setInfTecnicas(String infTecnicas) {
		this.infTecnicas = infTecnicas;
	}

	public Date getDataAquisicao() {
		return this.dataAquisicao;
	}

	public void setDataAquisicao(Date dataAquisicao) {
		this.dataAquisicao = dataAquisicao;
	}

	public Double getValorAquisicao() {
		return this.valorAquisicao;
	}

	public void setValorAquisicao(Double valorAquisicao) {
		this.valorAquisicao = valorAquisicao;
	}

	public Date getDataHoraDesabilitado() {
		return this.dataHoraDesabilitado;
	}

	public void setDataHoraDesabilitado(Date dataHoraDesabilitado) {
		this.dataHoraDesabilitado = dataHoraDesabilitado;
	}

	public String getMotivoDesabilitado() {
		return this.motivoDesabilitado;
	}

	public void setMotivoDesabilitado(String motivoDesabilitado) {
		this.motivoDesabilitado = motivoDesabilitado;
	}

	public Integer getTempoMinimo() {
		return this.tempoMinimo;
	}

	public void setTempoMinimo(Integer tempoMinimo) {
		this.tempoMinimo = tempoMinimo;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public StreamedContent getFotoStream() {
		if (this.foto != null)
			return new DefaultStreamedContent(new ByteArrayInputStream(this.foto));
		else
			return null;
	}

	public Integer getVersao() {
		return this.versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Brinquedo other = (Brinquedo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}