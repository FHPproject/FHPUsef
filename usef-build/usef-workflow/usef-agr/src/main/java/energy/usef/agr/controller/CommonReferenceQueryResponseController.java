/*
 * Copyright 2015-2016 USEF Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package energy.usef.agr.controller;

import energy.usef.agr.config.ConfigAgr;
import energy.usef.agr.config.ConfigAgrParam;
import energy.usef.agr.model.datamodelFHP.FHPMessage;
import energy.usef.agr.model.datamodelFHP.FHPMessageStatus;
import energy.usef.agr.model.datamodelFHP.FHPMessageType;
import energy.usef.agr.repository.datamodelFHP.FHPMessageRepository;
import energy.usef.agr.workflow.nonudi.service.PlannerSend;
import energy.usef.agr.workflow.plan.connection.profile.AgrUpdateElementDataStoreEvent;
import energy.usef.core.config.Config;
import energy.usef.core.config.ConfigParam;
import energy.usef.core.controller.BaseIncomingResponseMessageController;
import energy.usef.core.data.xml.bean.message.CommonReferenceEntityType;
import energy.usef.core.data.xml.bean.message.CommonReferenceQuery;
import energy.usef.core.data.xml.bean.message.CommonReferenceQueryResponse;
import energy.usef.core.data.xml.bean.message.Connection;
import energy.usef.core.data.xml.bean.message.DispositionSuccessFailure;
import energy.usef.core.exception.BusinessException;
//import energy.usef.core.model.Connection;
import energy.usef.core.model.Message;
import energy.usef.core.service.business.CorePlanboardBusinessService;
import energy.usef.core.util.XMLUtil;
import java.math.BigDecimal;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processes common reference update response.
 */
@Startup
@Singleton
public class CommonReferenceQueryResponseController extends BaseIncomingResponseMessageController<CommonReferenceQueryResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonReferenceQueryResponseController.class);

    @Inject
    private CorePlanboardBusinessService corePlanboardBusinessService;

    @Inject
    private ConfigAgr configAgr;

    @Inject
    private Event<AgrUpdateElementDataStoreEvent> agrUpdateElementDataStoreEventManager;
    
    @Inject
    private FHPMessageRepository fHPMessageRepository;    
    
    @Inject
    private Config config;    

    /**
     * {@inheritDoc}
     */
    @Lock(LockType.WRITE)
    public void action(CommonReferenceQueryResponse message, Message savedMessage) throws BusinessException {
        LOGGER.debug("CommonReferenceQueryResponse received");
        if (DispositionSuccessFailure.SUCCESS.equals(message.getResult())) 
        {
            LOGGER.info("Store CommonReferenceQueryResponse");
            boolean useferAgr = configAgr.getBooleanProperty(ConfigAgrParam.AGR_USEFER);
            
            if (useferAgr == true)
            {
                // we now that the common reference query is not null
                Message commonReferenceQuery = findFirstMessageOfConversation(message);
                CommonReferenceEntityType type = fetchEntityTypeFromOriginalQuery(commonReferenceQuery);

                Integer initializationDelay = 1;
                Integer initializationDuration = configAgr.getIntegerProperty(ConfigAgrParam.AGR_INITIALIZE_PLANBOARD_DAYS_INTERVAL);
                LocalDate initializationDate = commonReferenceQuery.getCreationTime().toLocalDate().plusDays(initializationDelay);
                // store the common reference query response.
                corePlanboardBusinessService.storeCommonReferenceQueryResponse(message, type, initializationDate,
                        initializationDuration);
                // fire event to populate profile values for connection portfolio if every response has been received.
                if (messageService.hasEveryCommonReferenceQuerySentAResponseReceived(commonReferenceQuery.getCreationTime())) 
                {
                    LOGGER.debug("Every CommonReferenceQuery has a related Response for period [{}].", initializationDate);
                    // Update the element data store
                    agrUpdateElementDataStoreEventManager.fire(new AgrUpdateElementDataStoreEvent(initializationDate));
                }   
                
                FHPMessage fHPmessageUpdate = new FHPMessage();
                fHPmessageUpdate = fHPMessageRepository.getLatestMessage(FHPMessageType.PU_AggregationLevelUpdate);
                long aggregationLevelUpdateSequence =fHPmessageUpdate.getSequenceNumber();                    

//                UPAggregationLevelInform uPAggregationLevelInform = new UPAggregationLevelInform();
//                uPAggregationLevelInform.setPUAggregationLevelUpdateSequence(aggregationLevelUpdateSequence);
//                uPAggregationLevelInform.setPUAggregationLevelUpdateId(aggregationLevelUpdateSequence);
//                if (type == CommonReferenceEntityType.BRP)
//                {
//                    uPAggregationLevelInform.setEntity(AggregationLevelEntity.BALANCING_GROUP);
//                }
//                else if (type == CommonReferenceEntityType.CONGESTION_POINT)
//                {
//                    uPAggregationLevelInform.setEntity(AggregationLevelEntity.GRID_ZONE);
//                }                
//                plannerSend.postAggregationLevelInform(uPAggregationLevelInform);
                
                FHPMessage fHPmessageInform = new FHPMessage();
                fHPmessageInform.setCreationDatetime(message.getMessageMetadata().getTimeStamp());
//                fHPmessageInform.setExpirationTime(expirationTime);
                fHPmessageInform.setFHPMessageStatus(FHPMessageStatus.NEW);
                fHPmessageInform.setFHPMessageType(FHPMessageType.UP_AggregationLevelInform);
                fHPmessageInform.setParticipantDomain(fHPmessageUpdate.getParticipantDomain());
                fHPmessageInform.setSequenceNumber(0);

                fHPmessageInform.setMessageId(message.getMessageMetadata().getMessageID()); 
                
                JsonObjectBuilder messageBuilder = Json.createObjectBuilder();

                JsonObjectBuilder messageMetaDataBuilder = Json.createObjectBuilder();
                messageMetaDataBuilder.add("senderDomain", config.getProperty(ConfigParam.HOST_DOMAIN));
                messageMetaDataBuilder.add("senderRole", "AGR");
                messageMetaDataBuilder.add("recipientDomain", fHPmessageInform.getParticipantDomain());
                messageMetaDataBuilder.add("recipientRole", "PLN");      
                messageMetaDataBuilder.add("timeStamp", fHPmessageInform.getCreationDatetime().toString());  
                messageMetaDataBuilder.add("messageID", fHPmessageInform.getMessageId());  
                messageBuilder.add("messageMetaData", messageMetaDataBuilder);
                                
                JsonObjectBuilder messageHeaderBuilder = Json.createObjectBuilder();
                messageHeaderBuilder.add("message", fHPmessageInform.getFHPMessageType().toString());  
                messageHeaderBuilder.add("aggregationLevelUpdateSequence", aggregationLevelUpdateSequence); 
                if (type == CommonReferenceEntityType.CONGESTION_POINT)
                {
                    messageHeaderBuilder.add("entity", "GridZone"); 
                }
                else if (type == CommonReferenceEntityType.BRP)
                {
                    messageHeaderBuilder.add("entity", "BalancingGroup"); 
                }    
                messageBuilder.add("messageHeader", messageHeaderBuilder);
                boolean vr_created = false;
                
                if (type == CommonReferenceEntityType.BRP)
                {            
                    JsonArrayBuilder balancingGroupsBuilder = Json.createArrayBuilder();

                    int numConnections = message.getConnection().size();
                    boolean todasCogidas = false;
                    String currentDomain = "";
                    if (numConnections > 0)
                    {
                        vr_created = true;
                        boolean cogidaConn[] = new boolean[numConnections];
                        for (int i = 0; i < cogidaConn.length; i++)
                        {
                            cogidaConn[i] = false;
                        }
                        do
                        {
                            currentDomain = "";
                            JsonArrayBuilder connectionsBuilder = Json.createArrayBuilder();
                            JsonObjectBuilder balancingGroupBuilder = Json.createObjectBuilder();
                            for (int j = 0; j < message.getConnection().size(); j++)
                            {
                                if (cogidaConn[j] == false)
                                {
                                    if (currentDomain.compareTo("") == 0)
                                    {                                    
                                        // Nuevo grupo
                                        currentDomain = message.getConnection().get(j).getBRPDomain();                                    
                                        balancingGroupBuilder.add("entityAddress", currentDomain + ":Group1"); 
                                        balancingGroupBuilder.add("bRPDomain", currentDomain);   
                                    }
                                    if (currentDomain.compareTo(message.getConnection().get(j).getBRPDomain()) == 0)
                                    {                                    
                                        // Nueva connection
                                        JsonObjectBuilder connectionBuilder = Json.createObjectBuilder();
                                        connectionBuilder.add("entityAddress", message.getConnection().get(j).getEntityAddress()); 
                                        connectionsBuilder.add(connectionBuilder);
                                        cogidaConn[j] = true;
                                    }                                
                                }
                            }  
                            balancingGroupBuilder.add("connections", connectionsBuilder);
                            balancingGroupsBuilder.add(balancingGroupBuilder);
                            todasCogidas = true;
                            for (int i = 0; i < cogidaConn.length; i++)
                            {
                                if (cogidaConn[i] == false) todasCogidas = false;
                            } 
                        }
                        while (todasCogidas == false);
                    }
                    messageBuilder.add("balancingGroups", balancingGroupsBuilder);
                }    
                else if (type == CommonReferenceEntityType.CONGESTION_POINT)
                {            
                    JsonArrayBuilder gridZonesBuilder = Json.createArrayBuilder();

                    int numCongestionPoints = message.getCongestionPoint().size();
                    boolean todasCogidas = false;
                    String currentDSODomain = "";
                    String currentEntityAddress = "";
                    if (numCongestionPoints > 0)
                    {
                        vr_created = true;
                        for (int k = 0; k < numCongestionPoints; k++)
                        {
                            JsonObjectBuilder gridZoneBuilder = Json.createObjectBuilder();                            
                            currentDSODomain = message.getCongestionPoint().get(k).getDSODomain();
                            currentEntityAddress = message.getCongestionPoint().get(k).getEntityAddress();
                            gridZoneBuilder.add("entityAddress", currentEntityAddress);
                            gridZoneBuilder.add("dSODomain", currentDSODomain);                               
                            int numConnections = message.getCongestionPoint().get(k).getConnection().size();
                            if (numConnections > 0)
                            {
                                JsonArrayBuilder connectionsBuilder = Json.createArrayBuilder();
                                for (int j = 0; j < numConnections; j++)
                                {
                                    String connectionEntityAddress = message.getCongestionPoint().get(k).getConnection().get(j).getEntityAddress();
                                    JsonObjectBuilder connectionBuilder = Json.createObjectBuilder();
                                    connectionBuilder.add("entityAddress", connectionEntityAddress); 
                                    connectionsBuilder.add(connectionBuilder);
                                }  
                                gridZoneBuilder.add("connections", connectionsBuilder);
                                gridZonesBuilder.add(gridZoneBuilder);
                            }
                        }
                    }
                    messageBuilder.add("gridZones", gridZonesBuilder);
                }    
                     
                if (vr_created == true)
                {
                    JsonObject messageJsonObject = messageBuilder.build();
                    String json = "";
                    json = messageJsonObject.toString();
                    fHPmessageInform.setJson(json);                

                    long fHPMessageId = fHPMessageRepository.create(
                            fHPmessageInform.getCreationDatetime(), 
                            fHPmessageInform.getFHPMessageStatus(), 
                            fHPmessageInform.getFHPMessageType(), 
                            fHPmessageInform.getExpirationTime(), 
                            fHPmessageInform.getParticipantDomain(), 
                            fHPmessageInform.getSequenceNumber(), 
                            fHPmessageInform.getMessageId(), 
                            fHPmessageInform.getJson());    

                    PlannerSend plannerSend = new PlannerSend(); 
                    plannerSend.init();                
                    boolean vr = plannerSend.postFHPMessage(fHPmessageInform);
                    if (vr == true) 
                        fHPMessageRepository.updateStatus(fHPMessageId, FHPMessageStatus.SUBMITTED_OK);
                    else 
                        fHPMessageRepository.updateStatus(fHPMessageId, FHPMessageStatus.SUBMITTED_FAIL);
                }
            }
            else
            {
                // we now that the common reference query is not null
                Message commonReferenceQuery = findFirstMessageOfConversation(message);
                CommonReferenceEntityType type = fetchEntityTypeFromOriginalQuery(commonReferenceQuery);

                Integer initializationDelay = 1;
                Integer initializationDuration = configAgr.getIntegerProperty(ConfigAgrParam.AGR_INITIALIZE_PLANBOARD_DAYS_INTERVAL);
                LocalDate initializationDate = commonReferenceQuery.getCreationTime().toLocalDate().plusDays(initializationDelay);
                // store the common reference query response.
                corePlanboardBusinessService.storeCommonReferenceQueryResponse(message, type, initializationDate,
                        initializationDuration);
                // fire event to populate profile values for connection portfolio if every response has been received.
                if (messageService.hasEveryCommonReferenceQuerySentAResponseReceived(commonReferenceQuery.getCreationTime())) 
                {
                    LOGGER.debug("Every CommonReferenceQuery has a related Response for period [{}].", initializationDate);
                    // Update the element data store
                    agrUpdateElementDataStoreEventManager.fire(new AgrUpdateElementDataStoreEvent(initializationDate));
                }   
            }
        }
        LOGGER.debug("CommonReferenceQueryResponse finished");
    }

    private CommonReferenceEntityType fetchEntityTypeFromOriginalQuery(Message commonReferenceQuery) {
        return XMLUtil.xmlToMessage(commonReferenceQuery.getXml(), CommonReferenceQuery.class).getEntity();
    }

}
