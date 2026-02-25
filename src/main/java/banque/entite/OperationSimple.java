package banque.entite;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("OPERATION_SIMPLE")
public class OperationSimple extends Operation {

    public OperationSimple() {}
}
