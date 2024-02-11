public class Client {
    private String nom;

    public Client(){}
    public Client(String nom){
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Client{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
