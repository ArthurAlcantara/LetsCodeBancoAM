public abstract class Cliente {
    private String telefone;

    protected void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public abstract String getCadastro();

    public abstract String getNome();

    public String getTelefone() {
		return telefone;
	}

	public Conta abrirConta(int escolha){
        Conta c;
        switch (escolha){
            case 2:
                c = new ContaPoupanca(this);
                break;
            case 3:
                c = new ContaInvestimento(this);
                break;
            default:
                c = new ContaCorrente(this);
                break;
        }
        return c;
    }
}
