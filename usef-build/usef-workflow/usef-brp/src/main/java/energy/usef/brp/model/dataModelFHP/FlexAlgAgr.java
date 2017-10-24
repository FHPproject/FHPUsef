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
@Table(name = "FLEX_ALG_AGR")
public class FlexAlgAgr {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
//	public BIGINT ID;
    @Column(name = "FLEX_ALG_ID", nullable = false)
    private Long flexAlgId;
//	public BIGINT FLEX_ALG_ID;
    @Column(name = "AGR_ID", nullable = false)
    private Long agrId;
//	public BIGINT AGR_ID;
    @Column(name = "AGR_FLEX_OFFER_ID", nullable = false)
    private Long agrFlexOfferId;
//	public BIGINT AGR_FLEX_OFFER_ID;
    @Column(name = "FLEX_PAYMENT")
    private float flexPayment;
//	public FLOAT FLEX_PAYMENT;

	public FlexAlgAgr(){
	}

	public void finalize() throws Throwable {

	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlexAlgId() {
        return flexAlgId;
    }

    public void setFlexAlgId(Long flexAlgId) {
        this.flexAlgId = flexAlgId;
    }

    public Long getAgrId() {
        return agrId;
    }

    public void setAgrId(Long agrId) {
        this.agrId = agrId;
    }

    public Long getAgrFlexOfferId() {
        return agrFlexOfferId;
    }

    public void setAgrFlexOfferId(Long agrFlexOfferId) {
        this.agrFlexOfferId = agrFlexOfferId;
    }

    public float getFlexPayment() {
        return flexPayment;
    }

    public void setFlexPayment(float flexPayment) {
        this.flexPayment = flexPayment;
    }

}