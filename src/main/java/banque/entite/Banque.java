package banque.entite;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "banque")
public class Banque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //GeneratedValue pour que la base de données gère l'auto-incrémentation de l'ID
    private Integer id;

    private String nom;

    @OneToMany(mappedBy = "banque")
    private Set<Client> clients = new HashSet<>();

    public Banque() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Set<Client> getClients() { return clients; }
    public void setClients(Set<Client> clients) { this.clients = clients; }
}
