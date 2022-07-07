import java.math.BigDecimal;
import java.math.RoundingMode;

public class Conta {
	private int codigo;
	private static int total = 1;
	private BigDecimal saldo = BigDecimal.ZERO;
	private Cliente titular;

	public Conta() {
	}

	public Conta(Cliente c) {
		this.titular = c;
		this.codigo = total++;
	}

	protected BigDecimal getSaldo() {
		return this.saldo;
	}

	protected Cliente getTitular() {
		return this.titular;
	}

	public String consultarSaldo() {
		return "Saldo disponível na conta " + codigo + " é: R$" + this.saldo.setScale(2, RoundingMode.HALF_DOWN);
	}

	public void depositar(BigDecimal valor) {
		this.saldo = this.saldo.add(valor);
	}

	public void sacar(BigDecimal valor) throws SaldoInsuficienteException{
		//Saque para PJ paga 0.05 de impostos.
		if(valor.compareTo(this.saldo) < 0) {
			valor = valor.add(this.titular.getClass() == PessoaJuridica.class ? 
					valor.multiply(BigDecimal.valueOf(0.05)) : 
						BigDecimal.ZERO);
			this.saldo = this.saldo
					.subtract(valor);
		}else {
			throw new SaldoInsuficienteException("Não é possível sacar mais que o saldo");
		}
	}

	public int getCodigo() {
		return codigo;
	}

	public void transferir(BigDecimal valor, Conta c) throws SaldoInsuficienteException {
		if(valor.compareTo(this.saldo) < 0) {
			c.depositar(valor);
			this.saldo = this.saldo
					.subtract(valor.
							add(this.titular.getClass() == PessoaJuridica.class ? 
									valor.multiply(BigDecimal.valueOf(0.05)) : 
										BigDecimal.ZERO));
		}else {
			throw new SaldoInsuficienteException("Não é possível transferir mais que o saldo");
		}
	}
}
