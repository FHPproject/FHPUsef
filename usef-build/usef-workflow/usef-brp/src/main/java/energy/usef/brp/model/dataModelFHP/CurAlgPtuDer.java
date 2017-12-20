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
@Table(name = "CUR_ALG_PTU_DER")
public class CurAlgPtuDer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
//	public BIGINT ID;
    @Column(name = "CUR_ALG_PTU_ID", nullable = false)
    private Long curAlgPtuId;
//	public BIGINT CUR_ALG_PTU_ID;
    @Column(name = "CUR_ALG_DER_ID", nullable = false)
    private Long curAlgDerId;
//	public BIGINT CUR_ALG_DER_ID;
    @Column(name = "DER_ID", nullable = false)
    private Long derId;

    public Long getCurAlgDerId() {
        return curAlgDerId;
    }

    public void setCurAlgDerId(Long curAlgDerId) {
        this.curAlgDerId = curAlgDerId;
    }

    public Long getDerId() {
        return derId;
    }

    public void setDerId(Long derId) {
        this.derId = derId;
    }

    public float getDerEnergy() {
        return derEnergy;
    }

    public void setDerEnergy(float derEnergy) {
        this.derEnergy = derEnergy;
    }

    public float getDerPayment() {
        return derPayment;
    }

    public void setDerPayment(float derPayment) {
        this.derPayment = derPayment;
    }

    public float getDerRemainingCurtailment() {
        return derRemainingCurtailment;
    }

    public void setDerRemainingCurtailment(float derRemainingCurtailment) {
        this.derRemainingCurtailment = derRemainingCurtailment;
    }
//	public BIGINT DER_ID;
    @Column(name = "DER_ENERGY")
    private float derEnergy;
    @Column(name = "DER_PAYMENT")
    private float derPayment;
    @Column(name = "DER_REMAINING_CURTAILMENT")
    private float derRemainingCurtailment;    

    public Long getCurAlgPtuId() {
        return curAlgPtuId;
    }

    public void setCurAlgPtuId(Long curAlgPtuId) {
        this.curAlgPtuId = curAlgPtuId;
    }
   
    public float getAgrPayment() {
        return agrPayment;
    }

    public void setAgrPayment(float agrPayment) {
        this.agrPayment = agrPayment;
    }
//	public FLOAT AGR_ENERGY;
    @Column(name = "AGR_PAYMENT")
    private float agrPayment;
//	public FLOAT AGR_PAYMENT;

	public CurAlgPtuDer(){
	}

	public void finalize() throws Throwable {

	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}