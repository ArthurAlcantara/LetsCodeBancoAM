public class PessoaJuridica extends Cliente{
    public PessoaJuridica(String razaoSocial, String cnpj){
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }
    public String getNome(){
        return razaoSocial;
    }
    private String razaoSocial;
    private String cnpj;

    public String getCadastro() {
        return cnpj;
    }
}
