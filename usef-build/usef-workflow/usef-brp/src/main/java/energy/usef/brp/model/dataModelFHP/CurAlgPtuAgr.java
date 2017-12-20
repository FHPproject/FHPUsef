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
@Table(name = "CUR_ALG_PTU_AGR")
public class CurAlgPtuAgr {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
//	public BIGINT ID;
    @Column(name = "CUR_ALG_PTU_ID", nullable = false)
    private Long curAlgPtuId;
//	public BIGINT CUR_ALG_PTU_ID;
    @Column(name = "CUR_ALG_AGR_ID", nullable = false)
    private Long curAlgAgrId;
//	public BIGINT CUR_ALG_AGR_ID;
    @Column(name = "AGR_ID", nullable = false)
    private Long agrId;
//	public BIGINT AGR_ID;
    @Column(name = "AGR_ENERGY")
    private float agrEnergy;

    public Long getCurAlgPtuId() {
        return curAlgPtuId;
    }

    public void setCurAlgPtuId(Long curAlgPtuId) {
        this.curAlgPtuId = curAlgPtuId;
    }

    public Long getCurAlgAgrId() {
        return curAlgAgrId;
    }

    public void setCurAlgAgrId(Long curAlgAgrId) {
        this.curAlgAgrId = curAlgAgrId;
    }

    public float getAgrEnergy() {
        return agrEnergy;
    }

    public void setAgrEnergy(float agrEnergy) {
        this.agrEnergy = agrEnergy;
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

	public CurAlgPtuAgr(){
	}

	public void finalize() throws Throwable {

	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgrId() {
        return agrId;
    }

    public void setAgrid(Long agrId) {
        this.agrId = agrId;
    }

}