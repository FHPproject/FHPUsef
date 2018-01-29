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
@Table(name = "CUR_ALG_AGR_DER")
public class CurAlgAgrDer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
//	public BIGINT ID;
    @Column(name = "CUR_ALG_AGR_ID", nullable = false)
    private Long curAlgAgrId;
    @Column(name = "CUR_ALG_DER_ID", nullable = false)
    private Long curAlgDerId;
    @Column(name = "AGR_ID", nullable = false)
    private Long agrId;
    @Column(name = "DER_ID", nullable = false)
    private Long derId;
    @Column(name = "AGR_DER_PAYMENT")
    private float agrDerPayment;
//	public FLOAT AGR_PAYMENT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurAlgAgrId() {
        return curAlgAgrId;
    }

    public void setCurAlgAgrId(Long curAlgAgrId) {
        this.curAlgAgrId = curAlgAgrId;
    }

    public Long getCurAlgDerId() {
        return curAlgDerId;
    }

    public void setCurAlgDerId(Long curAlgDerId) {
        this.curAlgDerId = curAlgDerId;
    }

    public Long getAgrId() {
        return agrId;
    }

    public void setAgrId(Long agrId) {
        this.agrId = agrId;
    }

    public Long getDerId() {
        return derId;
    }

    public void setDerId(Long derId) {
        this.derId = derId;
    }

    public float getAgrDerPayment() {
        return agrDerPayment;
    }

    public void setAgrDerPayment(float agrDerPayment) {
        this.agrDerPayment = agrDerPayment;
    }


}