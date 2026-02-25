package banque;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import banque.entite.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class BanqueRepositoryTest {

    private static EntityManagerFactory emf;
    private EntityManager em;


    // Tests sur les Clients



      //Extraire tous les clients

    @Test
    public void testExtraireTousLesClients() {
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
        List<Client> clients = query.getResultList();

        assertFalse(clients.isEmpty());
    }


      //Extraire un client par son nom

    @Test
    public void testExtraireClientParNom() {
        TypedQuery<Client> query = em.createQuery(
                "SELECT c FROM Client c WHERE c.nom = :nom", Client.class);
        query.setParameter("nom", "Dupont");
        List<Client> clients = query.getResultList();

        assertEquals(1, clients.size());
        assertEquals("Dupont", clients.get(0).getNom());
    }


      //Extraire les clients d'une banque spécifique

    @Test
    public void testExtraireClientsParBanque() {
        TypedQuery<Client> query = em.createQuery(
                "SELECT c FROM Client c WHERE c.banque.nom = :nomBanque", Client.class);
        query.setParameter("nomBanque", "MaBanque");
        List<Client> clients = query.getResultList();

        assertTrue(clients.size() >= 3);
    }


    // Tests sur les Comptes



      //Extraire tous les comptes

    @Test
    public void testExtraireTousLesComptes() {
        TypedQuery<Compte> query = em.createQuery("SELECT c FROM Compte c", Compte.class);
        List<Compte> comptes = query.getResultList();

        assertFalse(comptes.isEmpty());
    }


      //Extraire les comptes de type LivretA

    @Test
    public void testExtraireComptesLivretA() {
        TypedQuery<LivretA> query = em.createQuery("SELECT l FROM LivretA l", LivretA.class);
        List<LivretA> livrets = query.getResultList();

        assertTrue(livrets.size() >= 2);
    }


      //Extraire les comptes de type AssuranceVie

    @Test
    public void testExtraireComptesAssuranceVie() {
        TypedQuery<AssuranceVie> query = em.createQuery("SELECT a FROM AssuranceVie a", AssuranceVie.class);
        List<AssuranceVie> assurances = query.getResultList();

        assertTrue(assurances.size() >= 1);
    }


      //Extraire un compte par son numéro

    @Test
    public void testExtraireCompteParNumero() {
        TypedQuery<Compte> query = em.createQuery(
                "SELECT c FROM Compte c WHERE c.numero = :numero", Compte.class);
        query.setParameter("numero", "FR76-1234");
        List<Compte> comptes = query.getResultList();

        assertEquals(1, comptes.size());
    }


      //Extraire les comptes ayant un solde supérieur à un montant donné

    @Test
    public void testExtraireComptesParSoldeMinimum() {
        TypedQuery<Compte> query = em.createQuery(
                "SELECT c FROM Compte c WHERE c.solde > :soldeMin", Compte.class);
        query.setParameter("soldeMin", 5000.0);
        List<Compte> comptes = query.getResultList();

        assertTrue(comptes.size() >= 1);
    }




      //Extraire les comptes d'un client spécifique

    @Test
    public void testExtraireComptesParClient() {
        TypedQuery<Compte> query = em.createQuery(
                "SELECT c FROM Compte c JOIN c.clients cl WHERE cl.nom = :nom", Compte.class);
        query.setParameter("nom", "Durand");
        List<Compte> comptes = query.getResultList();

        assertEquals(2, comptes.size()); // 1 LivretA + 1 AssuranceVie
    }


      //Extraire les clients ayant un compte partagé (compte avec plusieurs clients)

    @Test
    public void testExtraireClientsAvecComptePartage() {
        TypedQuery<Client> query = em.createQuery(
                "SELECT DISTINCT cl FROM Client cl JOIN cl.comptes c WHERE SIZE(c.clients) > 1", Client.class);
        List<Client> clients = query.getResultList();

        assertEquals(2, clients.size()); // Dupont et MICHEL partagent un compte
    }


     //Extraire le compte partagé par plusieurs clients

    @Test
    public void testExtraireComptePartage() {
        TypedQuery<Compte> query = em.createQuery(
                "SELECT c FROM Compte c WHERE SIZE(c.clients) > 1", Compte.class);
        List<Compte> comptes = query.getResultList();

        assertEquals(1, comptes.size());
        assertEquals("FR76-1234", comptes.get(0).getNumero());
    }


    // Tests sur les Opérations



      //Extraire toutes les opérations

    @Test
    public void testExtraireToutesLesOperations() {
        TypedQuery<Operation> query = em.createQuery("SELECT o FROM Operation o", Operation.class);
        List<Operation> operations = query.getResultList();

        assertTrue(operations.size() >= 2);
    }


      //Extraire les virements

    @Test
    public void testExtraireVirements() {
        TypedQuery<Virement> query = em.createQuery("SELECT v FROM Virement v", Virement.class);
        List<Virement> virements = query.getResultList();

        assertTrue(virements.size() >= 2);
    }


      //Extraire les opérations d'un compte spécifique

    @Test
    public void testExtraireOperationsParCompte() {
        TypedQuery<Operation> query = em.createQuery(
                "SELECT o FROM Operation o WHERE o.compte.numero = :numero", Operation.class);
        query.setParameter("numero", "FR76-1234");
        List<Operation> operations = query.getResultList();

        assertTrue(operations.size() >= 1);
    }


      //Extraire les virements par bénéficiaire

    @Test
    public void testExtraireVirementsParBeneficiaire() {
        TypedQuery<Virement> query = em.createQuery(
                "SELECT v FROM Virement v WHERE v.beneficiaire = :beneficiaire", Virement.class);
        query.setParameter("beneficiaire", "Paul");
        List<Virement> virements = query.getResultList();

        assertEquals(1, virements.size());
    }

    //Calculer le total des opérations sur un compte

    @Test
    public void testCalculerTotalOperationsParCompte() {
        TypedQuery<Double> query = em.createQuery(
                "SELECT SUM(o.montant) FROM Operation o WHERE o.compte.numero = :numero", Double.class);
        query.setParameter("numero", "FR76-1234");
        Double total = query.getSingleResult();

        assertNotNull(total);
        assertTrue(total > 0);
    }

    // Configuration des tests

    @BeforeEach
    public void ouvertureEm() {
        em = emf.createEntityManager();
    }

    @AfterEach
    public void fermetureEm() {
        em.close();
    }

    @BeforeAll
    public static void initDatabase() {
        emf = Persistence.createEntityManagerFactory("banque");
        EntityManager em = emf.createEntityManager();

        try {
            // Vérifier si des données existent déjà
            if (em.createQuery("SELECT COUNT(c) FROM Client c", Long.class).getSingleResult() == 0) {
                em.getTransaction().begin();

                // Créer la banque
                Banque banque = new Banque();
                banque.setNom("MaBanque");
                em.persist(banque);

                // Client 1 et 2 avec compte partagé
                Client client = new Client();
                client.setNom("Dupont");
                client.setPrenom("Jean");
                client.setBanque(banque);

                Client client2 = new Client();
                client2.setNom("MICHEL");
                client2.setPrenom("ARNAULT");
                client2.setBanque(banque);

                LivretA livret = new LivretA();
                livret.setNumero("FR76-1234");
                livret.setSolde(1000.0);
                livret.setTaux(0.5);

                client.getComptes().add(livret);
                client2.getComptes().add(livret);
                livret.getClients().add(client);
                livret.getClients().add(client2);

                em.persist(livret);
                em.persist(client);
                em.persist(client2);

                // Client 3 avec plusieurs comptes (LivretA + AssuranceVie)
                Client client3 = new Client();
                client3.setNom("Durand");
                client3.setPrenom("Marie");
                client3.setBanque(banque);

                LivretA livretA3 = new LivretA();
                livretA3.setNumero("FR76-5678");
                livretA3.setSolde(2500.0);
                livretA3.setTaux(0.75);

                AssuranceVie assuranceVie3 = new AssuranceVie();
                assuranceVie3.setNumero("FR76-9999");
                assuranceVie3.setSolde(15000.0);
                assuranceVie3.setTaux(2.0);
                assuranceVie3.setDateFin(LocalDate.of(2035, 12, 31));

                client3.getComptes().add(livretA3);
                client3.getComptes().add(assuranceVie3);
                livretA3.getClients().add(client3);
                assuranceVie3.getClients().add(client3);

                em.persist(livretA3);
                em.persist(assuranceVie3);
                em.persist(client3);

                // Opérations (virements)
                Virement virement = new Virement();
                virement.setDate(LocalDateTime.now());
                virement.setMontant(200.0);
                virement.setMotif("Paiement");
                virement.setBeneficiaire("Paul");
                virement.setCompte(livret);
                em.persist(virement);

                Virement virement2 = new Virement();
                virement2.setDate(LocalDateTime.now());
                virement2.setMontant(500.0);
                virement2.setMotif("Loyer");
                virement2.setBeneficiaire("Propriétaire");
                virement2.setCompte(livretA3);
                em.persist(virement2);

                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void fermetureRessources() {
        if (emf != null) {
            emf.close();
        }
    }
}
