import java.math.BigDecimal;
import java.math.RoundingMode;

public class ContaPoupanca extends Conta implements Rentavel {
	public ContaPoupanca(Cliente titular) {
		super(titular);
	}

	public void rende() {
		BigDecimal rend = this.getSaldo().multiply(BigDecimal.valueOf(0.005));
		System.out.println("Rendimento de: R$" + rend.setScale(2,RoundingMode.HALF_UP));
		super.depositar(rend);
	}

	@Override
	public void depositar(BigDecimal valor) {
		super.depositar(valor);
		rende();
	}
}
