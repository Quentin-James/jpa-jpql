package banque.entite;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "compte")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//InheritanceType.SINGLE_TABLE signifie que toutes les classes
// dérivées de Compte seront stockées dans une seule
// table "compte" avec une colonne supplémentaire pour différencier les types de comptes.
@DiscriminatorColumn(name = "TYPE_COMPTE")
public abstract class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String numero;
    private double solde;

    @ManyToMany(mappedBy = "comptes")
    private Set<Client> clients = new HashSet<>();

    @OneToMany(mappedBy = "compte")
    private Set<Operation> operations = new HashSet<>();

    public Compte() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }

    public Set<Client> getClients() { return clients; }
    public void setClients(Set<Client> clients) { this.clients = clients; }

    public Set<Operation> getOperations() { return operations; }
    public void setOperations(Set<Operation> operations) { this.operations = operations; }
}
