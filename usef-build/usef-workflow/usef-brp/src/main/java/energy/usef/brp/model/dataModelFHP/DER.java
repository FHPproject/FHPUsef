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
 * @created 20-oct-2017 12:02:29
 */
@Entity
@Table(name = "DER")
public class DER {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
	//public BIGINT ID;
    
    @Column(name = "TYPE")
    private String type;
	//public VARCHAR TYPE;

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
    
    @Column(name = "OWNER_NAME")
    private String ownerName;
	//public VARCHAR OWNER_NAME;
    
    @Column(name = "CURTAILMENT_FACTOR")
    private float curtailmentFactor;

    public float getCurtailmentFactor() {
        return curtailmentFactor;
    }

    public void setCurtailmentFactor(float curtailmentFactor) {
        this.curtailmentFactor = curtailmentFactor;
    }

    public float getPowerMin() {
        return powerMin;
    }

    public void setPowerMin(float powerMin) {
        this.powerMin = powerMin;
    }
        
    @Column(name = "POWER_MIN")
    private float powerMin;
        
    @Column(name = "SUBSIDY")
    private float subsidy;

    public float getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(float subsidy) {
        this.subsidy = subsidy;
    }


    public DER(){
    }

    public void finalize() throws Throwable {
    }

}