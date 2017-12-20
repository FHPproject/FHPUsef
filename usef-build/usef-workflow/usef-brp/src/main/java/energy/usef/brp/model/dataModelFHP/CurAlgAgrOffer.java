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
 * @created 20-oct-2017 12:02:54
 */
@Entity
@Table(name = "CUR_ALG_AGR_OFFER")
public class CurAlgAgrOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
//	public BIGINT ID;
    @Column(name = "CUR_ALG_AGR_ID", nullable = false)
    private Long curAlgAgrId;
//	public BIGINT CUR_ALG_AGR_ID;
    @Column(name = "AGR_ID", nullable = false)
    private Long agrId;
//	public BIGINT AGR_ID;
    @Column(name = "AGR_FLEX_OFFER_ID", nullable = false)
    private Long agrFlexOfferId;
//	public BIGINT AGR_FLEX_OFFER_ID;
    @Column(name = "SELECTED")
    private boolean selected;
//	public BOOL SELECTED;

    public Long getCurAlgAgrId() {
        return curAlgAgrId;
    }

    public void setCurAlgAgrId(Long curAlgAgrId) {
        this.curAlgAgrId = curAlgAgrId;
    }

	public CurAlgAgrOffer(){

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

    public Long getAgrFlexOfferId() {
        return agrFlexOfferId;
    }

    public void setAgrFlexOfferId(Long agrFlexOfferId) {
        this.agrFlexOfferId = agrFlexOfferId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}