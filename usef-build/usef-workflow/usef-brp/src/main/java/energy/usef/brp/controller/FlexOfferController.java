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

package energy.usef.brp.controller;

import energy.usef.brp.model.dataModelFHP.AGR;
import energy.usef.brp.model.dataModelFHP.AgrFlexRequest;
import energy.usef.brp.repository.dataModelFHP.AgrFlexOfferPtuRepository;
import energy.usef.brp.repository.dataModelFHP.AgrFlexOfferRepository;
import energy.usef.brp.repository.dataModelFHP.AgrFlexRequestRepository;
import energy.usef.brp.repository.dataModelFHP.AgrRepository;
import static energy.usef.core.data.xml.bean.message.MessagePrecedence.ROUTINE;

import energy.usef.core.config.Config;
import energy.usef.core.config.ConfigParam;
import energy.usef.core.controller.BaseIncomingMessageController;
import energy.usef.core.data.xml.bean.message.DispositionAcceptedRejected;
import energy.usef.core.data.xml.bean.message.FlexOffer;
import energy.usef.core.data.xml.bean.message.FlexOfferResponse;
import energy.usef.core.data.xml.bean.message.MessageMetadata;
import energy.usef.core.data.xml.bean.message.PTU;
import energy.usef.core.data.xml.bean.message.USEFRole;
import energy.usef.core.exception.BusinessException;
import energy.usef.core.exception.BusinessValidationException;
import energy.usef.core.model.DocumentStatus;
import energy.usef.core.model.DocumentType;
import energy.usef.core.model.Message;
import energy.usef.core.model.PtuContainerState;
import energy.usef.core.service.business.CorePlanboardBusinessService;
import energy.usef.core.service.helper.JMSHelperService;
import energy.usef.core.service.helper.MessageMetadataBuilder;
import energy.usef.core.service.validation.CorePlanboardValidatorService;
import energy.usef.core.util.XMLUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Incoming FlexOffer controller.
 */
@Stateless
public class FlexOfferController extends BaseIncomingMessageController<FlexOffer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlexOfferController.class);

    @Inject
    private JMSHelperService jmsService;

    @Inject
    private CorePlanboardBusinessService corePlanboardBusinessService;

    @Inject
    private CorePlanboardValidatorService corePlanboardValidatorService;

    @Inject
    private Config config;
    
    @Inject
    private AgrFlexOfferRepository  agrFlexOfferRepository;

    @Inject
    private AgrFlexOfferPtuRepository  agrFlexOfferPtuRepository;
    
    @Inject
    private AgrRepository agrRepository;
    
    @Inject
    private AgrFlexRequestRepository agrFlexRequestRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void action(FlexOffer request, Message savedMessage) throws BusinessException {
        LOGGER.info("FlexOffer received.");
        try {
            // validate
            corePlanboardValidatorService.validateCurrency(request.getCurrency());
            corePlanboardValidatorService.validateTimezone(request.getTimeZone());
            corePlanboardValidatorService.validatePTUDuration(request.getPTUDuration());
            if (!request.getPTU().isEmpty()) {
                corePlanboardValidatorService.validatePTUsForPeriod(request.getPTU(), request.getPeriod(),
                        false);
            }
            corePlanboardValidatorService.validateDomain(request.getFlexRequestOrigin());

            corePlanboardValidatorService
                    .validatePlanboardMessageExpirationDate(request.getFlexRequestSequence(), DocumentType.FLEX_REQUEST,
                            request.getMessageMetadata().getSenderDomain());

            String usefIdentifier = request.getMessageMetadata().getSenderDomain();

            // The Period the offer applies to should have at least one PTU that is not already pending settlement.
            corePlanboardValidatorService.validateIfPTUForPeriodIsNotInPhase(usefIdentifier,
                    request.getPeriod(), PtuContainerState.PendingSettlement, PtuContainerState.Settled);

            // store
            corePlanboardBusinessService.storeFlexOffer(usefIdentifier, request,
                    DocumentStatus.ACCEPTED, request.getMessageMetadata().getSenderDomain());

            //TECNALIA-BEGIN
            saveAgrFlexOffer(request, DocumentStatus.ACCEPTED);
            //TECNALIA-END
        
            // send response
            sendResponse(request, null);
        } catch (BusinessValidationException exception) {
            //TECNALIA-BEGIN
            saveAgrFlexOffer(request, DocumentStatus.REJECTED);
            //TECNALIA-END
            sendResponse(request, exception);
        }

    }

    private void sendResponse(FlexOffer request, BusinessValidationException exception) {
        FlexOfferResponse response = new FlexOfferResponse();

        MessageMetadata messageMetadata = MessageMetadataBuilder
                .build(request.getMessageMetadata().getSenderDomain(), request.getMessageMetadata().getSenderRole(),
                        config.getProperty(ConfigParam.HOST_DOMAIN), USEFRole.BRP, ROUTINE)
                .conversationID(request.getMessageMetadata().getConversationID()).build();
        response.setMessageMetadata(messageMetadata);
        response.setSequence(request.getSequence());
        if (exception == null) {
            response.setResult(DispositionAcceptedRejected.ACCEPTED);
        } else {
            response.setResult(DispositionAcceptedRejected.REJECTED);
            response.setMessage(exception.getMessage());
        }

        // send the response xml to the out queue.
        jmsService.sendMessageToOutQueue(XMLUtil.messageObjectToXml(response));

        LOGGER.info("FlexOfferResponse with conversation-id {} is sent to the outgoing queue.",
                response.getMessageMetadata().getConversationID());
    }
    
    //TECNALIA-BEGIN
    void saveAgrFlexOffer(FlexOffer request, DocumentStatus status) {
        LocalDateTime offerStartDateTime;
        LocalDateTime offerEndDateTime;
        LocalDate startDate = request.getPeriod();
        LocalDateTime startDateTime = new LocalDateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0, 0, 0);
        offerStartDateTime = startDateTime;
        offerEndDateTime = startDateTime;
        int ptuDuration = request.getPTUDuration().getMinutes();
        //Save data in AGR_FLEX_REQUEST table
        AGR agr = agrRepository.getAgr(request.getMessageMetadata().getSenderDomain());
        AgrFlexRequest agrFlexRequest = agrFlexRequestRepository.get(agr.getId(), request.getPeriod());
        long agrFlexOfferId = agrFlexOfferRepository.create(agr.getId(), request.getMessageMetadata().getSenderDomain(), 
                request.getPTUDuration().getMinutes(), request.getPTU().size(), request.getFlexRequestSequence(), 
                agrFlexRequest.getId(), request.getMessageMetadata().getMessageID(), request.getCurrency(),status);
         
        int numPtu = 1;
        for(PTU ptu: request.getPTU()) {
            //Save data in AGR_FLEX_OFFER_PTU table
            LocalDateTime ptuStartDateTime = startDateTime.plusMinutes((ptu.getStart().intValue()-1)*ptuDuration);
            LocalDateTime ptuEndDateTime = ptuStartDateTime.plusMinutes(ptu.getDuration().intValue()*ptuDuration);
            agrFlexOfferPtuRepository.create(agrFlexOfferId, ptuStartDateTime,
                    ptuEndDateTime, ptu.getDuration().intValue(), ptu.getStart().intValue(), ptu.getPrice().floatValue(),
                    ptu.getPower().floatValue());
            if(numPtu == 1) {
                offerStartDateTime = ptuStartDateTime;
                offerEndDateTime = ptuEndDateTime;
            } else { 
                if (offerStartDateTime.isAfter(ptuStartDateTime)) {
                    offerStartDateTime = ptuStartDateTime;               
                }
                if (offerEndDateTime.isBefore(ptuEndDateTime)) {
                    offerEndDateTime = ptuEndDateTime;               
                }
            }
            numPtu++;
        }
        agrFlexOfferRepository.updateDates(agrFlexOfferId, offerStartDateTime, offerEndDateTime);
    }
    //TECNALIA-END
}
