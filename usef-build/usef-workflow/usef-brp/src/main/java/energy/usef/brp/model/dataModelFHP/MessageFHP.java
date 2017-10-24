package energy.usef.brp.model.dataModelFHP;

import java.sql.Blob;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:03:14
 */
/**
 * Entity for the MESSAGEFHP Table.
 */
@Entity
@Table(name = "MESSAGEFHP")
public class MessageFHP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
	//public BIGINT ID;
    
    @Column(name = "CONTENT_HASH")
    private Blob contentHash;    
	//public BLOB CONTENT_HASH;
    
    @Column(name = "CONVERSATION_ID")
    private String conversationId; 
	//public VARCHAR CONVERSATION_ID;
    
    @Column(name = "CREATION_TIME")
    private Date creationTime; 
	//public TIMESTAMP CREATION_TIME;
    
    @Column(name = "EXPIRATION_TIME")
    private Date expirationTime; 
	//public TIMESTAMP EXPIRATION_TIME;
	
    @Column(name = "DIRECTION")
    private String direction; 
        //public VARCHAR DIRECTION;
    
    @Column(name = "MESSAGE_ID")
    private String messageID; 
	//public VARCHAR MESSAGE_ID;
        
    @Column(name = "MESSAGE_TYPE")
    private String messageType; 
	//public VARCHAR MESSAGE_TYPE;
    
	/**
	 * In the case of the OAA GW this field can represent the JabberID of the REG/DER
	 */
    @Column(name = "RECEIVER")
    private String receiver; 
	//public VARCHAR RECEIVER;
	/**
	 * In the case of the OAA GW this field can represent the JabberID of the REG/DER
	 */
    @Column(name = "SENDER")
    private String sender; 
	//public VARCHAR SENDER;
    
    @Column(name = "XML")
    private Blob xml; 
	//public BLOB XML;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getContentHash() {
        return contentHash;
    }

    public void setContentHash(Blob contentHash) {
        this.contentHash = contentHash;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Blob getXml() {
        return xml;
    }

    public void setXml(Blob xml) {
        this.xml = xml;
    }

    public MessageFHP(){
	}

    public void finalize() throws Throwable {
	}

}