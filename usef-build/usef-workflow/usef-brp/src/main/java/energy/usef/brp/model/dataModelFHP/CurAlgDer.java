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
 * @created 20-oct-2017 12:02:51
 */
@Entity
@Table(name = "CUR_ALG_DER")
public class CurAlgDer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
//	public BIGINT ID;
    @Column(name = "CUR_ALG_ID", nullable = false)
    private Long curAlgId;
//	public BIGINT CUR_ALG_ID;
    @Column(name = "DER_ID", nullable = false)
    private Long derId;
//	public BIGINT DER_ID;
    @Column(name = "DER_PAYMENT")
    private float derPayment;
//	public FLOAT AGR_PAYMENT;

    public Long getCurAlgId() {
        return curAlgId;
    }

    public Long getDerId() {
        return derId;
    }

    public void setDerId(Long derId) {
        this.derId = derId;
    }

    public float getDerPayment() {
        return derPayment;
    }

    public void setDerPayment(float derPayment) {
        this.derPayment = derPayment;
    }

    public void setCurAlgId(Long curAlgId) {
        this.curAlgId = curAlgId;
    }

	public CurAlgDer(){
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