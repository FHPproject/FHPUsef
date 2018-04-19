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
        
    @Column(name = "OWNER_NAME")
    private String ownerName;
	//public VARCHAR OWNER_NAME;
    
    @Column(name = "PRODUCTION_UNIT_NAME")
    private String productionUnitName;
	//public PRODUCTION_UNIT_NAME;

        @Column(name = "LOCATION")
    private String location;
	//public LOCATION;
    
    @Column(name = "CURTAILMENT_FACTOR")
    private float curtailmentFactor;    
            
    @Column(name = "POWER_MIN")
    private float powerMin;
    
    @Column(name = "POWER_MAX")
    private float powerMax;    
        
    @Column(name = "SUBSIDY")
    private float subsidy;    

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

    
    public String getProductionUnitName() {
        return productionUnitName;
    }

    public void setProductionUnitName(String productionUnitName) {
        this.productionUnitName = productionUnitName;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
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
    
    public float getPowerMax() {
        return powerMax;
    }

    public void setPowerMax(float powerMax) {
        this.powerMax = powerMax;
    }    

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