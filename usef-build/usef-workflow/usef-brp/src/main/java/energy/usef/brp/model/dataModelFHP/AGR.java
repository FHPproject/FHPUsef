package energy.usef.brp.model.dataModelFHP;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:00:30
 */
/**
 * Entity for the AGR Table.
 */
@Entity
@Table(name = "AGR")
public class AGR {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
    //public BIGINT ID;
   
    @Column(name = "TYPE", nullable = false)
    private String type;
    //public VARCHAR TYPE;
    
    @Column(name = "OWNER_NAME", nullable = false)
    private String ownerName;
    //public VARCHAR OWNER_NAME;
    
    @Column(name = "DOMAIN", nullable = false)
    private String domain;  
    //public VARCHAR DOMAIN;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}