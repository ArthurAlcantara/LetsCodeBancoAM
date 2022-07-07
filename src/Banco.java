import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Banco {

	private List<Conta> Contas = new ArrayList<>();
	private List<Cliente> Clientes = new ArrayList<>();

	public List<Conta> getContas() {
		return Contas;
	}

	public List<Cliente> getClientes() {
		return Clientes;
	}

	public void addCliente(Cliente c) {
		this.Clientes.add(c);
	}

	public void addConta(Conta c) {
		this.Contas.add(c);
	}

	public void mostrarContas() {
		System.out.println("Contas");
		for (Conta c : this.Contas) {
			System.out.println(" - Codigo: " + c.getCodigo() + ", " + c.getClass().toString().substring(6) + ", "
					+ c.getTitular().getNome() + ", Saldo atual: R$"
					+ c.getSaldo().setScale(2, RoundingMode.HALF_DOWN));
		}
	}

	public void mostrarClientes() {
		System.out.println("Clientes");
		int i = 0;
		for (Cliente c : this.Clientes) {
			System.out.println(" - Cod: " + (i + 1) + ", " + c.getNome() + ", " + c.getCadastro() + ", "
					+ c.getClass().toString().substring(6));
			i++;
		}
	}

	public void mostrarMenuPrincipal() {
		System.out.println("1 - Adicinonar um Cliente");
		System.out.println("2 - Operações de Cliente");
		System.out.println("3 - Operações de Conta");
		System.out.println("0 - Sair");
	}

	public void mostrarMenuClientes() {
		System.out.println("1 - Adicionar um PF");
		System.out.println("2 - Adicionar um PJ");
		System.out.println("0 - Voltar");
	}

	public void mostrarMenuOClientes() {
		System.out.println("1 - Listar Clientes");
		System.out.println("2 - Abrir Conta");
		System.out.println("0 - Voltar");
	}

	public void mostrarMenuContas() {
		System.out.println("1 - Listar Contas");
		System.out.println("2 - Fazer Operação");
		System.out.println("0 - Voltar");
	}

	public void mostrarMenuOperacaoContaC() {
		System.out.println("1 - Consultar Saldo");
		System.out.println("2 - Sacar");
		System.out.println("3 - Depositar");
		System.out.println("4 - Transferir");
		System.out.println("0 - Voltar");
	}

	public void mostrarMenuOperacaoContaP() {
		System.out.println("1 - Consultar Saldo");
		System.out.println("2 - Sacar");
		System.out.println("3 - Depositar (Gera Rendimentos Instantaneos!)");
		System.out.println("4 - Transferir");
		System.out.println("0 - Voltar");
	}

	public void mostrarMenuOperacaoContaI() {
		System.out.println("1 - Consultar Saldo");
		System.out.println("2 - Sacar");
		System.out.println("3 - Depositar");
		System.out.println("4 - Transferir");
		System.out.println("5 - Investir (Gera Rendimento Instantâneo!)");
		System.out.println("6 - Gerar Rendimento");
		System.out.println("0 - Voltar");
	}

	public void mostrarMenuTipoContaPF() {
		System.out.println("Tipo da Conta");
		System.out.println("1 - Corrente");
		System.out.println("2 - PoupanSSa");
		System.out.println("3 - Investimento");
		System.out.println("0 - Voltar");
	}

	public void mostrarMenuTipoContaPJ() {
		System.out.println("Tipo da Conta");
		System.out.println("1 - Corrente");
		System.out.println("2 - Investimento");
		System.out.println("0 - Voltar");
	}

	public void mostrarMensagemContaCriada(Conta ct) {
		System.out.println(
				"    Conta " + ct.getClass().toString().substring(6) + " criada para " + ct.getTitular().getNome());
	}

	public void limparConsole() {
		try {
			String operatingSystem = System.getProperty("os.name"); // Check the current operating system
			if (operatingSystem.contains("Windows")) {
				ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
				Process startProcess = pb.inheritIO().start();
				startProcess.waitFor();
			} else {
				ProcessBuilder pb = new ProcessBuilder("clear");
				Process startProcess = pb.inheritIO().start();
				startProcess.waitFor();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
