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
 * @created 20-oct-2017 12:03:00
 */
@Entity
@Table(name = "FLEX_ALG_PTU_AGR")
public class FlexAlgPtuAgr {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
//	public BIGINT ID;
    @Column(name = "FLEX_ALG_PTU_ID", nullable = false)
    private Long flexAlgPtuId;
//	public BIGINT FLEX_ALG_PTU_ID;
    @Column(name = "FLEX_ALG_AGR_ID", nullable = false)
    private Long flexAlgAgrId;
//	public BIGINT FLEX_ALG_AGR_ID;
    @Column(name = "AGR_ID", nullable = false)
    private Long agrId;
//	public BIGINT AGR_ID;
    @Column(name = "FLEX_ENERGY")
    private float flexEnergy;
//	public FLOAT FLEX_ENERGY;
    @Column(name = "FLEX_PAYMENT")
    private float flexPayment;
//	public FLOAT FLEX_PAYMENT;

	public FlexAlgPtuAgr(){
	}

	public void finalize() throws Throwable {

	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlexAlgPtuId() {
        return flexAlgPtuId;
    }

    public void setFlexAlgPtuId(Long flexAlgPtuId) {
        this.flexAlgPtuId = flexAlgPtuId;
    }

    public Long getFlexAlgAgrId() {
        return flexAlgAgrId;
    }

    public void setFlexAlgAgrId(Long flexAlgAgrid) {
        this.flexAlgAgrId = flexAlgAgrId;
    }

    public Long getAgrId() {
        return agrId;
    }

    public void setAgrId(Long agrId) {
        this.agrId = agrId;
    }

    public float getFlexEnergy() {
        return flexEnergy;
    }

    public void setFlexEnergy(float flexEnergy) {
        this.flexEnergy = flexEnergy;
    }

    public float getFlexPayment() {
        return flexPayment;
    }

    public void setFlexPayment(float flexPayment) {
        this.flexPayment = flexPayment;
    }

}