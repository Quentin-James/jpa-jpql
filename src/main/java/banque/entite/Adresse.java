package banque.entite;

import jakarta.persistence.Embeddable;
//Embeddable : classe qui peut être intégrée dans une autre classe (ex: adresse dans client)
@Embeddable
public class Adresse {
    private int numero;
    private String rue;
    private int codePostal;
    private String ville;

    public Adresse() {}

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getRue() { return rue; }
    public void setRue(String rue) { this.rue = rue; }

    public int getCodePostal() { return codePostal; }
    public void setCodePostal(int codePostal) { this.codePostal = codePostal; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
}
