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
 * @created 20-oct-2017 12:02:12
 */
@Entity
@Table(name = "CONSUMER")
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
    	//public BIGINT ID;
    @Column(name = "TYPE")
    private String type;
	//public VARCHAR TYPE;
    @Column(name = "OWNER_NAME")
    private String ownerName;
	//public VARCHAR OWNER_NAME;

    public Consumer(){
	}

    public void finalize() throws Throwable {
	}

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

}