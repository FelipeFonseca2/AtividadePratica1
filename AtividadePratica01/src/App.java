import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
private static final Scanner scanner = new Scanner(System.in);
private static final List<Veterinario> veterinarios = new ArrayList<>();
private static final List<Sala> salas = new ArrayList<>();
private static final List<Atendimento> atendimentos = new ArrayList<>();

public static void main(String[] args) {
carregarDadosIniciais();

int opcao;
do {
exibirMenu();
opcao = lerInteiro("Escolha uma opção: ");
System.out.println();
try {
switch (opcao) {
case 1 -> cadastrarAtendimento();
case 2 -> associarVeterinarioASala();
case 3 -> atribuirAtendimentoASala();
case 4 -> exibirAtendimentosDaSala();
case 5 -> exibirFinalizadosPorSala();
case 6 -> buscarPorStatus();
case 7 -> exibirDetalhesAtendimento();
case 8 -> finalizarAtendimento();
case 0 -> System.out.println("Encerrando o sistema...");
default -> System.out.println("Opção inválida.");
}
} catch (IllegalArgumentException | IllegalStateException e) {
System.out.println("ERRO: " + e.getMessage());
}
System.out.println();
} while (opcao != 0);

scanner.close();
}

private static void carregarDadosIniciais() {
veterinarios.add(new Veterinario("Ana Souza", "111.111.111-11", "Clínica Geral", "(11) 91111-1111"));
veterinarios.add(new Veterinario("Bruno Lima", "222.222.222-22", "Cirurgia", "(11) 92222-2222"));
veterinarios.add(new Veterinario("Carla Mendes", "333.333.333-33", "Dermatologia", "(11) 93333-3333"));
salas.add(new Sala(101, "A", 3, TipoSala.CONSULTORIO));
salas.add(new Sala(102, "A", 2, TipoSala.EXAMES));
salas.add(new Sala(201, "B", 1, TipoSala.CIRURGIA));
}

private static void exibirMenu() {
System.out.println("===== CLÍNICA VETERINÁRIA =====");
System.out.println("1 - Cadastrar atendimento");
System.out.println("2 - Associar veterinário a uma sala");
System.out.println("3 - Atribuir atendimento a uma sala");
System.out.println("4 - Exibir atendimentos de uma sala");
System.out.println("5 - Total de atendimentos finalizados por sala");
System.out.println("6 - Buscar atendimentos por status");
System.out.println("7 - Exibir detalhes de um atendimento");
System.out.println("8 - Finalizar atendimento");
System.out.println("0 - Sair");
}

private static void cadastrarAtendimento() {
System.out.println("--- Cadastrar atendimento ---");
String nomeAnimal = lerTexto("Nome do animal: ");
String especie = lerTexto("Espécie: ");
String nomeTutor = lerTexto("Nome do tutor: ");
LocalDate data = lerData("Data (dd/MM/aaaa): ");
LocalTime horario = lerHorario("Horário (HH:mm): ");
String observacoes = lerTextoOpcional("Observações (opcional): ");
System.out.println("--- Procedimento ---");
String nomeProcedimento = lerTexto("Nome do procedimento: ");
int duracao = lerInteiroPositivo("Duração estimada (minutos): ");
double valor = lerDecimal("Valor (R$): ");
NivelComplexidade complexidade = escolherComplexidade();
Atendimento atendimento = new Atendimento(nomeAnimal, especie, nomeTutor, data, horario, observacoes,
nomeProcedimento, duracao, valor, complexidade);
atendimentos.add(atendimento);
System.out.println("Atendimento cadastrado com código " + atendimento.getCodigo() + " (status: Agendado).");
}

private static void associarVeterinarioASala() {
System.out.println("--- Associar veterinário a uma sala ---");
Veterinario veterinario = escolherVeterinario();
Sala sala = escolherSala();
sala.associarVeterinario(veterinario);
System.out.println(veterinario.getNome() + " agora é responsável pela sala " + sala.getIdentificacao() + ".");
}

private static void atribuirAtendimentoASala() {
System.out.println("--- Atribuir atendimento a uma sala ---");
List<Atendimento> agendados = filtrarPorStatus(StatusAtendimento.AGENDADO);
if (agendados.isEmpty()) {
System.out.println("Não há atendimentos agendados.");
return;
}
System.out.println("Atendimentos agendados:");
agendados.forEach(a -> System.out.println(" " + a));

Atendimento atendimento = buscarAtendimento(lerInteiro("Código do atendimento: "));
Sala sala = escolherSala();
atendimento.atribuirSala(sala);
System.out.println("Atendimento #" + atendimento.getCodigo() + " atribuído à sala "
+ sala.getIdentificacao() + " (status: Em andamento).");
}

private static void exibirAtendimentosDaSala() {
System.out.println("--- Atendimentos de uma sala ---");
Sala sala = escolherSala();
System.out.println(sala);
List<Atendimento> lista = sala.getAtendimentos();
if (lista.isEmpty()) {
System.out.println("Nenhum atendimento atribuído a esta sala.");
} else {
for (Atendimento a : lista) {
System.out.println("----------------------------------");
System.out.println(a.getDetalhes());
}
System.out.println("----------------------------------");
}
System.out.println("Total de atendimentos: " + lista.size());
}

private static void exibirFinalizadosPorSala() {
System.out.println("--- Atendimentos finalizados por sala ---");
for (Sala sala : salas) {
int total = 0;
for (Atendimento a : atendimentos) {
if (a.getStatus() == StatusAtendimento.FINALIZADO && a.getSala() == sala) {
total++;
}
}
System.out.println("Sala " + sala.getIdentificacao() + ": " + total + " finalizado(s)");
}
}

private static void buscarPorStatus() {
System.out.println("--- Buscar atendimentos por status ---");
StatusAtendimento status = escolherStatus();
List<Atendimento> encontrados = filtrarPorStatus(status);
if (encontrados.isEmpty()) {
System.out.println("Nenhum atendimento com status " + status + ".");
return;
}
for (Atendimento a : encontrados) {
System.out.println("----------------------------------");
System.out.println(a.getDetalhes());
}
System.out.println("----------------------------------");
System.out.println("Total encontrado: " + encontrados.size());
}

private static void exibirDetalhesAtendimento() {
System.out.println("--- Detalhes do atendimento ---");
Atendimento atendimento = buscarAtendimento(lerInteiro("Código do atendimento: "));
System.out.println(atendimento.getDetalhes());
}

private static void finalizarAtendimento() {
System.out.println("--- Finalizar atendimento ---");
List<Atendimento> emAndamento = filtrarPorStatus(StatusAtendimento.EM_ANDAMENTO);
if (emAndamento.isEmpty()) {
System.out.println("Não há atendimentos em andamento.");
return;
}
System.out.println("Atendimentos em andamento:");
emAndamento.forEach(a -> System.out.println(" " + a));

Atendimento atendimento = buscarAtendimento(lerInteiro("Código do atendimento: "));
atendimento.finalizar();
System.out.println("Atendimento #" + atendimento.getCodigo() + " finalizado.");
}

private static List<Atendimento> filtrarPorStatus(StatusAtendimento status) {
List<Atendimento> resultado = new ArrayList<>();
for (Atendimento a : atendimentos) {
if (a.getStatus() == status) {
resultado.add(a);
}
}
return resultado;
}

private static Atendimento buscarAtendimento(int codigo) {
for (Atendimento a : atendimentos) {
if (a.getCodigo() == codigo) {
return a;
}
}
throw new IllegalArgumentException("Atendimento com código " + codigo + " não encontrado.");
}

private static Veterinario escolherVeterinario() {
System.out.println("Veterinários:");
for (int i = 0; i < veterinarios.size(); i++) {
System.out.println(" " + (i + 1) + " - " + veterinarios.get(i));
}
int indice = lerOpcao("Escolha o veterinário: ", veterinarios.size());
return veterinarios.get(indice - 1);
}

private static Sala escolherSala() {
System.out.println("Salas:");
for (int i = 0; i < salas.size(); i++) {
System.out.println(" " + (i + 1) + " - " + salas.get(i));
}
int indice = lerOpcao("Escolha a sala: ", salas.size());
return salas.get(indice - 1);
}

private static StatusAtendimento escolherStatus() {
StatusAtendimento[] valores = StatusAtendimento.values();
for (int i = 0; i < valores.length; i++) {
System.out.println(" " + (i + 1) + " - " + valores[i]);
}
return valores[lerOpcao("Escolha o status: ", valores.length) - 1];
}

private static NivelComplexidade escolherComplexidade() {
NivelComplexidade[] valores = NivelComplexidade.values();
for (int i = 0; i < valores.length; i++) {
System.out.println(" " + (i + 1) + " - " + valores[i]);
}
return valores[lerOpcao("Nível de complexidade: ", valores.length) - 1];
}

private static String lerTexto(String mensagem) {
while (true) {
System.out.print(mensagem);
String texto = scanner.nextLine().trim();
if (!texto.isEmpty()) {
return texto;
}
System.out.println("Campo obrigatório.");
}
}

private static String lerTextoOpcional(String mensagem) {
System.out.print(mensagem);
return scanner.nextLine().trim();
}

private static int lerInteiro(String mensagem) {
while (true) {
System.out.print(mensagem);
try {
return Integer.parseInt(scanner.nextLine().trim());
} catch (NumberFormatException e) {
System.out.println("Digite um número inteiro válido.");
}
}
}

private static int lerInteiroPositivo(String mensagem) {
while (true) {
int valor = lerInteiro(mensagem);
if (valor > 0) {
return valor;
}
System.out.println("O valor deve ser maior que zero.");
}
}

private static int lerOpcao(String mensagem, int maximo) {
while (true) {
int opcao = lerInteiro(mensagem);
if (opcao >= 1 && opcao <= maximo) {
return opcao;
}
System.out.println("Escolha um número entre 1 e " + maximo + ".");
}
}

private static double lerDecimal(String mensagem) {
while (true) {
System.out.print(mensagem);
try {
double valor = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
if (valor >= 0) {
return valor;
}
System.out.println("O valor não pode ser negativo.");
} catch (NumberFormatException e) {
System.out.println("Digite um valor numérico válido.");
}
}
}

private static LocalDate lerData(String mensagem) {
DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
while (true) {
System.out.print(mensagem);
try {
return LocalDate.parse(scanner.nextLine().trim(), formato);
} catch (DateTimeParseException e) {
System.out.println("Data inválida. Use o formato dd/MM/aaaa.");
}
}
}

private static LocalTime lerHorario(String mensagem) {
DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
while (true) {
System.out.print(mensagem);
try {
return LocalTime.parse(scanner.nextLine().trim(), formato);
} catch (DateTimeParseException e) {
System.out.println("Horário inválido. Use o formato HH:mm.");
}
}
}
}