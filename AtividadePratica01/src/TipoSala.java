public enum TipoSala {
CONSULTORIO("Consultório"),
CIRURGIA("Centro cirúrgico"),
EXAMES("Sala de exames"),
EMERGENCIA("Emergência");

private final String descricao;

TipoSala(String descricao) {
this.descricao = descricao;
}

public String getDescricao() {
return descricao;
}

@Override
public String toString() {
return descricao;
}
}