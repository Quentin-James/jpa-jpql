package banque;

import banque.entite.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
//RunBanque est une classe de test pour vérifier
// le bon fonctionnement de la persistance des entités liées à la banque.
// Elle crée une banque, un client,
// un compte LivretA, et effectue un virement,
// puis persiste le tout dans la base de données.
public class RunBanque {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("banque");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Banque banque = new Banque();
            banque.setNom("MaBanque");
            em.persist(banque);

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

            //Insérer un compte associé à deux clients
            client.getComptes().add(livret);
            client2.getComptes().add(livret);
            livret.getClients().add(client);
            livret.getClients().add(client2);

            em.persist(livret);
            em.persist(client);
            em.persist(client2);

            // Insérer un client avec plusieurs comptes :
            // 1 AssuranceVie + 1 LivretA
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
            assuranceVie3.setDateFin(java.time.LocalDate.of(2035, 12, 31));

            // Lier client3 à ses deux comptes
            client3.getComptes().add(livretA3);
            client3.getComptes().add(assuranceVie3);
            livretA3.getClients().add(client3);
            assuranceVie3.getClients().add(client3);

            em.persist(livretA3);
            em.persist(assuranceVie3);
            em.persist(client3);

            // Insérer des opérations de type virement sur un compte
            Virement virement = new Virement();
            virement.setDate(java.time.LocalDateTime.now());
            virement.setMontant(200.0);
            virement.setMotif("Paiement");
            virement.setBeneficiaire("Paul");
            virement.setCompte(livret);
            em.persist(virement);

            Virement virement2 = new Virement();
            virement2.setDate(java.time.LocalDateTime.now());
            virement2.setMontant(500.0);
            virement2.setMotif("Loyer");
            virement2.setBeneficiaire("Propriétaire");
            virement2.setCompte(livretA3);
            em.persist(virement2);

            //Insérer des opérations de type operation sur un compte
            OperationSimple operation1 = new OperationSimple();
            operation1.setDate(java.time.LocalDateTime.now());
            operation1.setMontant(100.0);
            operation1.setMotif("Retrait");
            operation1.setCompte(livret);
            em.persist(operation1);


            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            em.close();
            emf.close();
        }
    }
}
