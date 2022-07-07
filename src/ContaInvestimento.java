import java.math.BigDecimal;
import java.math.RoundingMode;

public class ContaInvestimento extends Conta implements Rentavel {
	public ContaInvestimento(Cliente titular) {
		super(titular);
	}

	public void rende() {
		BigDecimal rend = super.getTitular().getClass() == PessoaFisica.class
				? super.getSaldo().multiply(BigDecimal.valueOf(0.01))
				: super.getSaldo().multiply(BigDecimal.valueOf(0.03));
		System.out.println("Rendimento de R$" + (rend.setScale(2, RoundingMode.HALF_UP)) + ", saldo resultante: R$"
				+ (super.getSaldo().add(rend).setScale(2, RoundingMode.HALF_UP)));
		super.depositar(rend);
	}

	// Não é possível investir mais que metade do saldo da conta
	// 30% de chance do investimento ser negativo
	public void investir(BigDecimal vlInvestimento) throws SaldoInsuficienteException {
		if (vlInvestimento.compareTo(super.getSaldo().divide(BigDecimal.valueOf(2))) < 0) {
			BigDecimal rendimento = BigDecimal.valueOf((Math.random() * 10) * (Math.random() > 0.3 ? 1 : -1));
			System.out.println("Rendimento :" + rendimento.setScale(2, RoundingMode.HALF_UP) + " %");
			BigDecimal rend = super.getTitular().getClass() == PessoaFisica.class ? 
					vlInvestimento
						.multiply(rendimento)
						.divide(BigDecimal.valueOf(100))
					: vlInvestimento
						.multiply(rendimento
								.add(BigDecimal.valueOf(2))
								.divide(BigDecimal.valueOf(100)));
			System.out.println("Seu investimento de " + (vlInvestimento.setScale(2, RoundingMode.HALF_UP)) + " rendeu "
					+ (vlInvestimento.add(rend).setScale(2, RoundingMode.HALF_UP)));
			super.depositar(rend);
		} else {
			throw new SaldoInsuficienteException("Não é possível investir mais que 50% do seu saldo!");
		}
	}
}
