package banque.entite;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("ASSURANCE_VIE")
//DiscriminatorValue permet de différencier les différentes classes filles
// d'une même hiérarchie d'entités. Ici, "ASSURANCE_VIE" est la valeur qui sera utilisée pour
// identifier les instances de la classe AssuranceVie dans la table de base de données.
public class AssuranceVie extends Compte {
    private LocalDate dateFin;
    private double taux;

    public AssuranceVie() {}

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public double getTaux() { return taux; }
    public void setTaux(double taux) { this.taux = taux; }
}
