import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Sala {
private int numero;
private String bloco;
private int capacidadeMaxima;
private TipoSala tipo;
private Veterinario veterinario;
private final List<Atendimento> atendimentos = new ArrayList<>();
public Sala(int numero, String bloco, int capacidadeMaxima, TipoSala tipo) {
setNumero(numero);
setBloco(bloco);
setCapacidadeMaxima(capacidadeMaxima);
setTipo(tipo);
}

public void associarVeterinario(Veterinario novo) {
if (novo == null) {
throw new IllegalArgumentException("Veterinário inválido.");
}
if (novo == this.veterinario) {
throw new IllegalStateException(novo.getNome() + " já é o responsável por esta sala.");
}
if (novo.possuiSala()) {
throw new IllegalStateException(novo.getNome() + " já é responsável pela sala "
+ novo.getSala().getIdentificacao() + ". Um veterinário só pode ter uma sala.");
}
if (this.veterinario != null) {
this.veterinario.desvincularSala();
}
this.veterinario = novo;
novo.vincularSala(this);
}

void adicionarAtendimento(Atendimento atendimento) {
if (veterinario == null) {
throw new IllegalStateException("A sala " + getIdentificacao() + " não possui veterinário responsável.");
}
if (atendimentos.size() >= capacidadeMaxima) {
throw new IllegalStateException("A sala " + getIdentificacao() + " atingiu a capacidade máxima ("
+ capacidadeMaxima + " animais).");
}
if (!atendimentos.isEmpty()) {
Procedimento procedimentoDaSala = atendimentos.get(0).getProcedimento();
if (!procedimentoDaSala.mesmoTipo(atendimento.getProcedimento())) {
throw new IllegalStateException("A sala " + getIdentificacao()
+ " só aceita atendimentos do procedimento \"" + procedimentoDaSala.getNome() + "\".");
}
}
atendimentos.add(atendimento);
}

void removerAtendimento(Atendimento atendimento) {
atendimentos.remove(atendimento);
}

public List<Atendimento> getAtendimentos() {
return Collections.unmodifiableList(atendimentos);
}

public String getIdentificacao() {
return numero + " - Bloco " + bloco;
}

public int getNumero() {
return numero;
}

public void setNumero(int numero) {
if (numero <= 0) {
throw new IllegalArgumentException("Número da sala inválido.");
}
this.numero = numero;
}

public String getBloco() {
return bloco;
}

public void setBloco(String bloco) {
if (bloco == null || bloco.isBlank()) {
throw new IllegalArgumentException("Bloco é obrigatório.");
}
this.bloco = bloco.trim();
}

public int getCapacidadeMaxima() {
return capacidadeMaxima;
}

public void setCapacidadeMaxima(int capacidadeMaxima) {
if (capacidadeMaxima <= 0) {
throw new IllegalArgumentException("Capacidade deve ser maior que zero.");
}
this.capacidadeMaxima = capacidadeMaxima;
}

public TipoSala getTipo() {
return tipo;
}

public void setTipo(TipoSala tipo) {
if (tipo == null) {
throw new IllegalArgumentException("Tipo de sala é obrigatório.");
}
this.tipo = tipo;
}

public Veterinario getVeterinario() {
return veterinario;
}

@Override
public String toString() {
String vet = veterinario != null ? veterinario.getNome() : "Sem veterinário";
return String.format("Sala %s | Tipo: %s | Capacidade: %d | Ocupação: %d | Responsável: %s",
getIdentificacao(), tipo, capacidadeMaxima, atendimentos.size(), vet);
}
}