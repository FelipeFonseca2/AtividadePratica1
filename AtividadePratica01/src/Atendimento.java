import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class Atendimento {
private static int proximoCodigo = 1;
private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
private final int codigo;
private String nomeAnimal;
private String especie;
private String nomeTutor;
private LocalDate data;
private LocalTime horario;
private StatusAtendimento status;
private String observacoes;
private final Procedimento procedimento;
private Sala sala;
public Atendimento(String nomeAnimal, String especie, String nomeTutor, LocalDate data, LocalTime horario,
String observacoes, String nomeProcedimento, int duracaoEstimada, double valor,
NivelComplexidade complexidade) {
setNomeAnimal(nomeAnimal);
setEspecie(especie);
setNomeTutor(nomeTutor);
setData(data);
setHorario(horario);
setObservacoes(observacoes);
this.procedimento = new Procedimento(nomeProcedimento, duracaoEstimada, valor, complexidade);
this.codigo = proximoCodigo++;
this.status = StatusAtendimento.AGENDADO;
this.sala = null;
}

public void atribuirSala(Sala novaSala) {
if (novaSala == null) {
throw new IllegalArgumentException("Sala inválida.");
}
if (status != StatusAtendimento.AGENDADO) {
throw new IllegalStateException("Só é possível atribuir sala a atendimentos agendados. Status atual: "
+ status + ".");
}
novaSala.adicionarAtendimento(this);
this.sala = novaSala;
this.status = StatusAtendimento.EM_ANDAMENTO;
}

public void finalizar() {
if (status != StatusAtendimento.EM_ANDAMENTO) {
throw new IllegalStateException("Só é possível finalizar atendimentos em andamento. Status atual: "
+ status + ".");
}
sala.removerAtendimento(this);
this.status = StatusAtendimento.FINALIZADO;
}

public String getDetalhes() {
String infoSala;
String infoVet;
if (sala == null) {
infoSala = "Nenhuma (atendimento agendado)";
infoVet = "Nenhum";
} else {
infoSala = sala.getIdentificacao() + " (" + sala.getTipo() + ")";
infoVet = sala.getVeterinario() != null
? sala.getVeterinario().getNome() + " - " + sala.getVeterinario().getEspecialidade()
: "Nenhum";
}
return "Código.......: " + codigo
+ "\nAnimal.......: " + nomeAnimal + " (" + especie + ")"
+ "\nTutor........: " + nomeTutor
+ "\nData/Horário.: " + data.format(FORMATO_DATA) + " às " + horario.format(FORMATO_HORA)
+ "\nStatus.......: " + status
+ "\nProcedimento.: " + procedimento
+ "\nSala.........: " + infoSala
+ "\nVeterinário..: " + infoVet
+ "\nObservações..: " + (observacoes.isBlank() ? "-" : observacoes);
}

public int getCodigo() {
return codigo;
}

public String getNomeAnimal() {
return nomeAnimal;
}

public void setNomeAnimal(String nomeAnimal) {
if (nomeAnimal == null || nomeAnimal.isBlank()) {
throw new IllegalArgumentException("Nome do animal é obrigatório.");
}
this.nomeAnimal = nomeAnimal.trim();
}

public String getEspecie() {
return especie;
}

public void setEspecie(String especie) {
if (especie == null || especie.isBlank()) {
throw new IllegalArgumentException("Espécie é obrigatória.");
}
this.especie = especie.trim();
}

public String getNomeTutor() {
return nomeTutor;
}

public void setNomeTutor(String nomeTutor) {
if (nomeTutor == null || nomeTutor.isBlank()) {
throw new IllegalArgumentException("Nome do tutor é obrigatório.");
}
this.nomeTutor = nomeTutor.trim();
}

public LocalDate getData() {
return data;
}

public void setData(LocalDate data) {
if (data == null) {
throw new IllegalArgumentException("Data é obrigatória.");
}
this.data = data;
}

public LocalTime getHorario() {
return horario;
}

public void setHorario(LocalTime horario) {
if (horario == null) {
throw new IllegalArgumentException("Horário é obrigatório.");
}
this.horario = horario;
}

public StatusAtendimento getStatus() {
return status;
}

public String getObservacoes() {
return observacoes;
}

public void setObservacoes(String observacoes) {
this.observacoes = observacoes == null ? "" : observacoes.trim();
}

public Procedimento getProcedimento() {
return procedimento;
}

public Sala getSala() {
return sala;
}

@Override
public String toString() {
return String.format("#%d | %s (%s) | Tutor: %s | %s | %s",
codigo, nomeAnimal, especie, nomeTutor, procedimento.getNome(), status);
}
}