package banque.entite;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LIVRETA")
public class LivretA extends Compte {
    private double taux;

    public LivretA() {}

    public double getTaux() { return taux; }
    public void setTaux(double taux) { this.taux = taux; }
}
