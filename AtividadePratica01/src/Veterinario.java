public class Veterinario {
private String nome;
private String cpf;
private String especialidade;
private String telefone;
private Sala sala;

public Veterinario(String nome, String cpf, String especialidade, String telefone) {
setNome(nome);
setCpf(cpf);
setEspecialidade(especialidade);
setTelefone(telefone);
}

public boolean possuiSala() {
return sala != null;
}

void vincularSala(Sala sala) {
this.sala = sala;
}

void desvincularSala() {
this.sala = null;
}

public Sala getSala() {
return sala;
}

public String getNome() {
return nome;
}

public void setNome(String nome) {
if (nome == null || nome.isBlank()) {
throw new IllegalArgumentException("Nome do veterinário é obrigatório.");
}
this.nome = nome.trim();
}

public String getCpf() {
return cpf;
}

public void setCpf(String cpf) {
if (cpf == null || cpf.isBlank()) {
throw new IllegalArgumentException("CPF é obrigatório.");
}
this.cpf = cpf.trim();
}

public String getEspecialidade() {
return especialidade;
}

public void setEspecialidade(String especialidade) {
this.especialidade = especialidade;
}

public String getTelefone() {
return telefone;
}

public void setTelefone(String telefone) {
this.telefone = telefone;
}

@Override
public String toString() {
String infoSala = possuiSala() ? "Sala " + sala.getIdentificacao() : "Sem sala";
return String.format("%s | CPF: %s | Especialidade: %s | Tel: %s | %s",
nome, cpf, especialidade, telefone, infoSala);
}
}