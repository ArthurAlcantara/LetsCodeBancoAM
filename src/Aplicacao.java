import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Aplicacao {
	public static void main(String[] args) throws IOException {

		// Banco contem a "persistência" dos clientes e contas durante a execução do
		// programa.
		Banco b = new Banco();
		
		//Criando um cliente e uma conta corrente
		Cliente c1 = new PessoaFisica("Arthur","12345678900");
		//Conta Corrente do cliente 1
		Conta cc1 = new ContaCorrente(c1);
		b.addCliente(c1);
		b.addConta(cc1);
		b.mostrarClientes();
		b.mostrarContas();
		System.out.println(cc1.consultarSaldo());
		System.err.println("Deposita 10000");
		cc1.depositar(BigDecimal.valueOf(10000));
		System.out.println(cc1.consultarSaldo());
		try {
			cc1.sacar(BigDecimal.valueOf(255.90));
			System.err.println("Saca 255,90");
		} catch (SaldoInsuficienteException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(cc1.consultarSaldo());
		
		System.out.println("===============================================");
		
		Cliente c2 = new PessoaFisica("Mauro","98765432100");
		//Conta Poupanca do cliente 2.
		Conta cp2 = new ContaPoupanca(c2);
		b.addCliente(c2);
		b.addConta(cp2);
		b.mostrarClientes();
		b.mostrarContas();
		System.out.println(cp2.consultarSaldo());
		System.err.println("Deposita 1200");
		cp2.depositar(BigDecimal.valueOf(1200));
		System.out.println(cp2.consultarSaldo());
		
		System.out.println("===============================================");
		
		//Cliente PJ
		Cliente c3 = new PessoaJuridica("Padaria Massa Fina","83125850000143");
		//Conta Investimento do cliente 3.
		Conta ci3 = new ContaInvestimento(c3);
		b.addCliente(c3);
		b.addConta(ci3);
		b.mostrarContas();
		System.out.println(ci3.consultarSaldo());
		System.err.println("Deposita 5000");
		ci3.depositar(BigDecimal.valueOf(5000));
		System.err.println("Saca 1000");
		try {
			ci3.sacar(BigDecimal.valueOf(1000));
		} catch (SaldoInsuficienteException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(ci3.consultarSaldo());
		System.err.println("Investe 1300");
		try {
			((ContaInvestimento) ci3).investir(BigDecimal.valueOf(1300));
		} catch (SaldoInsuficienteException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(ci3.consultarSaldo());
		
		System.out.println("===============================================");
		b.mostrarContas();
		System.err.println("Transferencia C1 -> C3 de 2000");
		try {
			cc1.transferir(BigDecimal.valueOf(2000), ci3);
		} catch (SaldoInsuficienteException e) {
			System.err.println(e.getMessage());
		}
		b.mostrarContas();
		System.out.println("===============================================");
		Conta ci1 = new ContaInvestimento(c1);
		b.addConta(ci1);
		b.mostrarContas();
		System.err.println("Depositar 3000");
		ci1.depositar(BigDecimal.valueOf(3000));
		try {
			((ContaInvestimento) ci1).investir(BigDecimal.valueOf(1000));
		} catch (SaldoInsuficienteException e) {
			System.out.println(e.getMessage());
		}
		b.mostrarContas();
		
		
		// Sistema principal
		// Scanner que lê as opções
		Scanner sc = new Scanner(System.in);
		boolean sair = false;
		int opt = 0;
		do {
			System.out.println("****************************************************************");
			System.out.println("**                                                            **");
			System.out.println("**                  Bem-Vindo ao BANCO AM!!                   **");
			System.out.println("**                                                            **");
			System.out.println("****************************************************************");
			// Menu Principal
			b.mostrarMenuPrincipal();
			opt = sc.nextInt();
			switch (opt) {
			case 0:
				sair = true;
				break;
			case 1:
				boolean fim = false;
				// Menu de Clientes
				b.mostrarMenuClientes();
				do {
					// Menu de cadastro de cliente
					opt = sc.nextInt();
					switch (opt) {
					case 0:
						b.limparConsole();
						fim = true;
						break;
					case 1:
						//Cadastro de PF
						System.out.println("Nome:");
						String nm = sc.next();
						System.out.println("CPF:");
						String cpf = sc.next();
						System.out.println("Telefone:");
						String tel = sc.next();
						Cliente pf = new PessoaFisica(nm, cpf);
						pf.setTelefone(tel);
						b.addCliente(pf);
						System.out.println("Cliente Cadastrado!");
						fim = true;
						break;
					case 2:
						//Cadastro de PJ
						System.out.println("Razão Social:");
						String razaoSocial = sc.next();
						System.out.println("CNPJ:");
						String cnpj = sc.next();
						System.out.println("Telefone:");
						String t = sc.next();
						Cliente pj = new PessoaJuridica(razaoSocial, cnpj);
						pj.setTelefone(t);
						b.addCliente(pj);
						System.out.println("Cliente Cadastrado!");
						fim = true;
						break;
					default:
						break;
					}
				} while (!fim);
				b.limparConsole();
				break;
			case 2:
				// Menu de cadastro de conta
				fim = false;
				if (b.getClientes().size() == 0) {
					System.err.println("Não Existe Cliente Cadastrado");
					break;
				} else {
					do {
						b.mostrarMenuOClientes();
						List<Cliente> cli = b.getClientes();
						opt = sc.nextInt();
						switch (opt) {
						case 0:
							fim = true;
							break;
						case 1:
							//Lista todos os clientes
							b.mostrarClientes();
							break;
						case 2:
							//Abrir conta para o cliente
							int max = cli.size();
							do {
								System.out.println("Código do Cliente:");
								opt = sc.nextInt();
							} while ((opt < 1) || (opt > max));
							Cliente c = cli.get(opt - 1);

							if (c.getClass() == PessoaFisica.class) {
								//Abrir Conta para PF
								b.mostrarMenuTipoContaPF();
								opt = sc.nextInt();
								Conta ct;
								switch (opt) {
								case 0:
									fim = true;
									break;
								case 1:
									ct = new ContaCorrente(c);
									b.addConta(ct);
									fim = true;
									b.mostrarMensagemContaCriada(ct);
									break;
								case 2:
									ct = new ContaPoupanca(c);
									b.addConta(ct);
									fim = true;
									b.mostrarMensagemContaCriada(ct);
									break;
								case 3:
									ct = new ContaInvestimento(c);
									b.addConta(ct);
									fim = true;
									b.mostrarMensagemContaCriada(ct);
									break;
								default:
									break;
								}
							} else {
								//Abrir Conta para PJ
								b.mostrarMenuTipoContaPJ();
								opt = sc.nextInt();
								Conta ct;
								switch (opt) {
								case 0:
									fim = true;
									break;
								case 1:
									ct = new ContaCorrente(c);
									b.addConta(ct);
									fim = true;
									b.mostrarMensagemContaCriada(ct);
									break;
								case 2:
									ct = new ContaInvestimento(c);
									b.addConta(ct);
									fim = true;
									b.mostrarMensagemContaCriada(ct);
									break;
								default:
									break;
								}
							}
							break;
						default:
							break;
						}
					} while (!fim);
				}
				break;
			case 3:
				fim = false;
				if (b.getContas().size() == 0) {
					System.err.println("Não Existe Conta Cadastrada");
					break;
				} else {
					do {
						//Menu de Contas
						b.mostrarMenuContas();
						List<Conta> cts = b.getContas();
						opt = sc.nextInt();
						switch (opt) {
						case 0:
							fim = true;
							break;
						case 1:
							//listar todas as contas
							b.mostrarContas();
							break;
						case 2:
							//Fazer uma operação em conta
							int max = cts.size();
							do {
								System.out.println("Código da Conta:");
								opt = sc.nextInt();
							} while ((opt < 1) || (opt > max));
							Conta c = cts.get(opt - 1);
							BigDecimal vl;
							if (c.getClass() == ContaCorrente.class) {
								//Operações de Conta Corrente
								b.mostrarMenuOperacaoContaC();
								do {
									opt = sc.nextInt();
									switch (opt) {
									case 1:
										//Consultar Saldo
										System.out.println(c.consultarSaldo());
										fim = true;
										break;
									case 2:
										//Sacar um valor
										System.out.println(c.consultarSaldo());
										System.out.println("Valor a sacar:");
										vl = sc.nextBigDecimal();
										try {
											c.sacar(vl);
											System.out.println("Saque feito com sucesso!");
										} catch (Exception e) {
											System.err.println(e.getMessage());
										}
										fim = true;
										break;
									case 3:
										//Depositar
										System.out.println("Valor do depósito:");
										vl = sc.nextBigDecimal();
										try {
											c.depositar(vl);
											System.out.println("Depósito feito com sucesso!");
										} catch (Exception e) {
											System.err.println(e.getMessage());
										}
										fim = true;
										break;
									case 4:
										//Transferir
										System.out.println("Qt contas: " + cts.size());
										if (cts.size() > 1) {
											do {
												System.out.println("Código da Conta Destino:");
												opt = sc.nextInt();
											} while ((opt < 1) || (opt > max) || (opt == c.getCodigo()));
											Conta ctd = cts.get(opt - 1);
											System.out.println(c.consultarSaldo());
											System.out.println("Valor a transferir:");
											vl = sc.nextBigDecimal();
											try {
												c.transferir(vl, ctd);
												System.out.println("Transferência para " + ctd.getCodigo() + ", de "
														+ vl + " feita com sucesso!");
											} catch (Exception e) {
												System.err.println(e.getMessage());
											}
											fim = true;
										} else {
											System.out.println("Apenas uma conta existe no momento");
											fim = true;
										}
										break;
									default:
										break;
									}
								} while (!fim);

							} else if (c.getClass() == ContaPoupanca.class) {
								//Operações de Conta Poupança
								b.mostrarMenuOperacaoContaP();
								do {
									opt = sc.nextInt();
									switch (opt) {
									case 1:
										//Saldo
										System.out.println(c.consultarSaldo());
										fim = true;
										break;
									case 2:
										//Saque
										System.out.println(c.consultarSaldo());
										System.out.println("Valor a sacar:");
										vl = sc.nextBigDecimal();
										try {
											c.sacar(vl);
											System.out.println("Saque feito com sucesso!");
										} catch (Exception e) {
											System.err.println(e.getMessage());
										}
										fim = true;
										break;
									case 3:
										//Depósito - Com rendimento
										System.out.println("Valor do depósito:");
										vl = sc.nextBigDecimal();
										try {
											c.depositar(vl);
											System.out.println("Depósito feito com sucesso!");
										} catch (Exception e) {
											System.err.println(e.getMessage());
										}
										fim = true;
										break;
									case 4:
										//Transferência
										System.out.println("Qt contas: " + cts.size());
										if (cts.size() > 1) {
											do {
												System.out.println("Código da Conta Destino:");
												opt = sc.nextInt();
											} while ((opt < 1) || (opt > max) || (opt == c.getCodigo()));
											Conta ctd = cts.get(opt - 1);
											System.out.println(c.consultarSaldo());
											System.out.println("Valor a transferir:");
											vl = sc.nextBigDecimal();
											try {
												c.transferir(vl, ctd);
												System.out.println("Transferência para " + ctd.getCodigo() + ", de "
														+ vl + " feita com sucesso!");
											} catch (Exception e) {
												System.err.println(e.getMessage());
											}
											fim = true;
										} else {
											System.out.println("Apenas uma conta existe no momento");
											fim = true;
										}
										break;
									default:
										break;
									}
								} while (!fim);
							} else {
								//Operações de Conta Investimento
								b.mostrarMenuOperacaoContaI();
								do {
									opt = sc.nextInt();
									switch (opt) {
									case 0:
										fim = true;
										break;
									case 1:
										//Saldo
										System.out.println(c.consultarSaldo());
										fim = true;
										break;
									case 2:
										//Saque
										System.out.println(c.consultarSaldo());
										System.out.println("Valor a sacar:");
										vl = sc.nextBigDecimal();
										try {
											c.sacar(vl);
											System.out.println("Saque feito com sucesso!");
										} catch (Exception e) {
											System.err.println(e.getMessage());
										}
										fim = true;
										break;
									case 3:
										//Depósito
										System.out.println("Valor do depósito:");
										vl = sc.nextBigDecimal();
										try {
											c.depositar(vl);
											System.out.println("Depósito feito com sucesso!");
										} catch (Exception e) {
											System.err.println(e.getMessage());
										}
										fim = true;
										break;
									case 4:
										//Transferência
										System.out.println("Qt contas: " + cts.size());
										if (cts.size() > 1) {
											do {
												System.out.println("Código da Conta Destino:");
												opt = sc.nextInt();
											} while ((opt < 1) || (opt > max) || (opt == c.getCodigo()));
											Conta ctd = cts.get(opt - 1);
											System.out.println(c.consultarSaldo());
											System.out.println("Valor a transferir:");
											vl = sc.nextBigDecimal();
											try {
												c.transferir(vl, ctd);
												System.out.println("Transferência para " + ctd.getCodigo() + ", de "
														+ vl + " feita com sucesso!");
											} catch (Exception e) {
												System.err.println(e.getMessage());
											}
											fim = true;
										} else {
											System.out.println("Apenas uma conta existe no momento");
											fim = true;
										}
										break;
									case 5:
										//Investimento - Um valor como parâmetro - entre 0 e 10% de rendimento.
										//30% de chance de ser negativo
										System.out.println(c.consultarSaldo());
										System.out.println("Valor do investimento:");
										vl = sc.nextBigDecimal();
										try {
											// Casting de conta para investimento.
											((ContaInvestimento) c).investir(vl);
											System.out.println("Investimento feito com sucesso!");
										} catch (Exception e) {
											System.err.println(e.getMessage());
										}
										fim = true;
										break;
									case 6:
										//Gerar Rendimento
										((ContaInvestimento) c).rende();
										fim = true;
										break;
									default:
										break;
									}
								} while (!fim);
							}
							break;
						default:
							break;
						}
					} while (!fim);
				}
				break;
			default:
				break;
			}

		} while (!sair);
		System.out.println("Pressione qualquer tecla para sair...");
		System.in.read();
		System.out.println("Saindo");
		sc.close();
	}
}
