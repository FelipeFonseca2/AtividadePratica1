public class Procedimento {
private String nome;
private int duracaoEstimada;
private double valor;
private NivelComplexidade nivelComplexidade;

public Procedimento(String nome, int duracaoEstimada, double valor, NivelComplexidade nivelComplexidade) {
setNome(nome);
setDuracaoEstimada(duracaoEstimada);
setValor(valor);
setNivelComplexidade(nivelComplexidade);
}

public boolean mesmoTipo(Procedimento outro) {
return outro != null && this.nome.equalsIgnoreCase(outro.nome);
}

public String getNome() {
return nome;
}

public void setNome(String nome) {
if (nome == null || nome.isBlank()) {
throw new IllegalArgumentException("Nome do procedimento é obrigatório.");
}
this.nome = nome.trim();
}

public int getDuracaoEstimada() {
return duracaoEstimada;
}

public void setDuracaoEstimada(int duracaoEstimada) {
if (duracaoEstimada <= 0) {
throw new IllegalArgumentException("A duração estimada deve ser maior que zero.");
}
this.duracaoEstimada = duracaoEstimada;
}

public double getValor() {
return valor;
}

public void setValor(double valor) {
if (valor < 0) {
throw new IllegalArgumentException("O valor não pode ser negativo.");
}
this.valor = valor;
}

public NivelComplexidade getNivelComplexidade() {
return nivelComplexidade;
}

public void setNivelComplexidade(NivelComplexidade nivelComplexidade) {
if (nivelComplexidade == null) {
throw new IllegalArgumentException("Nível de complexidade é obrigatório.");
}
this.nivelComplexidade = nivelComplexidade;
}

@Override
public String toString() {
return String.format("%s | Duração: %d min | Valor: R$ %.2f | Complexidade: %s",
nome, duracaoEstimada, valor, nivelComplexidade);
}
}