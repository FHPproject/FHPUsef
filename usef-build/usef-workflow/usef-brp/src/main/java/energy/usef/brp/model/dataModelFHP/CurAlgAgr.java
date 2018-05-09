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
@Table(name = "CUR_ALG_AGR")
public class CurAlgAgr {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
//	public BIGINT ID;
    @Column(name = "CUR_ALG_ID", nullable = false)
    private Long curAlgId;
//	public BIGINT CUR_ALG_ID;
    @Column(name = "AGR_ID", nullable = false)
    private Long agrId;
//	public BIGINT AGR_ID;
    @Column(name = "AGR_FLEX_OFFER_ID", nullable = false)
    private Long agrFlexOfferId;
//	public BIGINT AGR_FLEX_OFFER_ID;
    @Column(name = "AGR_FLEX_REQUEST_ID", nullable = false)
    private Long agrFlexRequestId;
//	public BIGINT AGR_FLEX_REQUEST_ID;
    @Column(name = "AGR_PAYMENT")
    private float agrPayment;
//	public FLOAT AGR_PAYMENT;

    public Long getCurAlgId() {
        return curAlgId;
    }

    public void setCurAlgId(Long curAlgId) {
        this.curAlgId = curAlgId;
    }

    public float getAgrPayment() {
        return agrPayment;
    }

    public void setAgrPayment(float agrPayment) {
        this.agrPayment = agrPayment;
    }

	public CurAlgAgr(){
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

    public void setAgrId(Long agrId) {
        this.agrId = agrId;
    }
    
    public Long getAgrFlexRequestId() {
        return agrFlexRequestId;
    }

    public void setAgrFlexRequestId(Long agrFlexRequestId) {
        this.agrFlexRequestId = agrFlexRequestId;
    }
    
    public Long getAgrFlexOfferId() {
        return agrFlexOfferId;
    }

    public void setAgrFlexOfferId(Long agrFlexOfferId) {
        this.agrFlexOfferId = agrFlexOfferId;
    }
    
  
}