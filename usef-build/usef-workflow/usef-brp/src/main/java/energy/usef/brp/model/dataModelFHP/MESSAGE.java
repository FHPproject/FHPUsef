package energy.usef.brp.model.dataModelFHP;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:03:14
 */
public class MESSAGE {

	public BIGINT ID;
	public BLOB CONTENT_HASH;
	public VARCHAR CONVERSATION_ID;
	public TIMESTAMP CREATION_TIME;
	public TIMESTAMP EXPIRATION_TIME;
	public VARCHAR DIRECTION;
	public VARCHAR MESSAGE_ID;
	public VARCHAR MESSAGE_TYPE;
	/**
	 * In the case of the OAA GW this field can represent the JabberID of the REG/DER
	 */
	public VARCHAR RECEIVER;
	/**
	 * In the case of the OAA GW this field can represent the JabberID of the REG/DER
	 */
	public VARCHAR SENDER;
	public BLOB XML;

	public MESSAGE(){

	}

	public void finalize() throws Throwable {

	}

}