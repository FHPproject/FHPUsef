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

package energy.usef.brp.workflow.plan.flexrequest.create;

import energy.usef.brp.model.dataModelFHP.AGR;
import energy.usef.brp.repository.dataModelFHP.AgrFlexRequestPtuRepository;
import energy.usef.brp.repository.dataModelFHP.AgrFlexRequestRepository;
import energy.usef.brp.repository.dataModelFHP.AgrRepository;
import energy.usef.brp.workflow.plan.connection.forecast.PrepareFlexRequestWorkflowParameter;
import static energy.usef.core.constant.USEFConstants.LOG_COORDINATOR_FINISHED_HANDLING_EVENT;
import static energy.usef.core.constant.USEFConstants.LOG_COORDINATOR_START_HANDLING_EVENT;
import static energy.usef.core.data.xml.bean.message.MessagePrecedence.TRANSACTIONAL;

import energy.usef.core.config.Config;
import energy.usef.core.config.ConfigParam;
import energy.usef.core.data.xml.bean.message.FlexRequest;
import energy.usef.core.data.xml.bean.message.MessageMetadata;
import energy.usef.core.data.xml.bean.message.PTU;
import energy.usef.core.data.xml.bean.message.USEFRole;
import energy.usef.core.model.DocumentStatus;
import energy.usef.core.model.DocumentType;
import energy.usef.core.model.PlanboardMessage;
import energy.usef.core.repository.PlanboardMessageRepository;
import energy.usef.core.service.business.CorePlanboardBusinessService;
import energy.usef.core.service.business.SequenceGeneratorService;
import energy.usef.core.service.helper.JMSHelperService;
import energy.usef.core.service.helper.MessageMetadataBuilder;
import energy.usef.core.util.PtuUtil;
import energy.usef.core.util.XMLUtil;
import energy.usef.core.workflow.dto.DispositionTypeDto;
import energy.usef.core.workflow.dto.FlexRequestDto;
import energy.usef.core.workflow.dto.PtuFlexRequestDto;
import energy.usef.core.workflow.transformer.DispositionTransformer;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Coordinator class for the workflow describing the creation and sending of flex requests from the BRP to the AGR.
 */
@Singleton
public class BrpCreateFlexRequestCoordinator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrpCreateFlexRequestCoordinator.class);

    @Inject
    private JMSHelperService jmsHelperService;

    @Inject
    private Config config;

    @Inject
    private CorePlanboardBusinessService corePlanboardBusinessService;

    @Inject
    private SequenceGeneratorService sequenceGeneratorService;

    @Inject
    private AgrRepository agrRepository;

    @Inject
    private AgrFlexRequestRepository agrFlexRequestRepository;
    
    @Inject
    private AgrFlexRequestPtuRepository agrFlexRequestPtuRepository;

    @Inject
    private PlanboardMessageRepository planboardMessageRepository;

    /**
     * {@inheritDoc}
     */
    public void handleEvent(@Observes CreateFlexRequestEvent event) {
        LOGGER.info(LOG_COORDINATOR_START_HANDLING_EVENT, event);

        //TECNALIA-BEGIN
        Integer ptuDuration = config.getIntegerProperty(ConfigParam.PTU_DURATION);
        LocalDate aPlanDate = event.getFlexRequestDtos().get(0).getPeriod();
        int numberOfPtusPerDay = PtuUtil.getNumberOfPtusPerDay(aPlanDate, ptuDuration);
        //TECNALIA-END
        List<FlexRequestDto> flexRequestDtos = event.getFlexRequestDtos();
        for (FlexRequestDto flexRequestDto : flexRequestDtos) {
            FlexRequest xmlFlexRequest = completeFlexRequestMessage(flexRequestDto, ptuDuration, numberOfPtusPerDay);

            // need to link the flex request to a connection group.
            String usefIdentifier = xmlFlexRequest.getMessageMetadata().getRecipientDomain();
            corePlanboardBusinessService.storeFlexRequest(usefIdentifier, xmlFlexRequest, DocumentStatus.SENT, usefIdentifier);
            sendFlexRequestMessage(xmlFlexRequest);
        }
        LOGGER.info(LOG_COORDINATOR_FINISHED_HANDLING_EVENT, event);
    }

    private void sendFlexRequestMessage(FlexRequest flexRequest) {
        jmsHelperService.sendMessageToOutQueue(XMLUtil.messageObjectToXml(flexRequest));
    }

    /**
     * Builds a flex request message with the given parameters.
     *
     * @param flexRequestDto {@link FlexRequestDto} with the basic information.
     * @return a {@link FlexRequest} message.
     */
    private FlexRequest completeFlexRequestMessage(FlexRequestDto flexRequestDto, Integer ptuDuration,
            int numberOfPtusPerDay) {
        FlexRequest flexRequest = new FlexRequest();
        flexRequest.setPrognosisOrigin(flexRequestDto.getParticipantDomain());
        flexRequest.setPrognosisSequence(flexRequestDto.getPrognosisSequenceNumber());
        flexRequest.setSequence(sequenceGeneratorService.next());
        flexRequest.setPeriod(flexRequestDto.getPeriod());
        flexRequest.setPTUDuration(Period.minutes(config.getIntegerProperty(ConfigParam.PTU_DURATION)));
        flexRequest.setTimeZone(config.getProperty(ConfigParam.TIME_ZONE));
        flexRequest.setMessageMetadata(buildMessageMetadata(flexRequestDto.getParticipantDomain()));
        flexRequest.setExpirationDateTime(flexRequestDto.getExpirationDateTime());
        //TECNALIA-BEGIN
        LocalDate startDate = flexRequestDto.getPeriod();
        LocalDateTime startDateTime = new LocalDateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDateTime = startDateTime.plusMinutes(ptuDuration*numberOfPtusPerDay);
        LocalDateTime ptuStartDateTime = startDateTime;
        LocalDateTime ptuEndDateTime = ptuStartDateTime.plusMinutes(ptuDuration);
        //Save data in AGR_FLEX_REQUEST table
        AGR agr = agrRepository.getAgr(flexRequestDto.getParticipantDomain());
        //Get PrognosisId: ???????DocumentType.A_PLAN o DocumentType.D_PROGNOSIS
        PlanboardMessage planboardMessage  = planboardMessageRepository.findSinglePlanboardMessage(flexRequest.getPrognosisSequence(), 
                DocumentType.A_PLAN, flexRequestDto.getParticipantDomain());
        long prognosisId = planboardMessage.getId();
        long agrFlexRequestId = agrFlexRequestRepository.create(agr.getId(), flexRequestDto.getParticipantDomain(), startDateTime,
            endDateTime, ptuDuration, numberOfPtusPerDay, flexRequest.getPrognosisSequence(), prognosisId, flexRequest.getMessageMetadata().getMessageID());
        //TECNALIA-END
        //TECNALIA-BEGIN
        //flexRequestDto.getPtus().stream().forEach(ptuFlexRequest -> {
        for(PtuFlexRequestDto ptuFlexRequest: flexRequestDto.getPtus()) {
        //TECNALIA-END
            PTU ptu = new PTU();
            ptu.setStart(ptuFlexRequest.getPtuIndex());
            ptu.setDuration(BigInteger.ONE);
            ptu.setDisposition(DispositionTransformer.transformToXml(ptuFlexRequest.getDisposition()));
            ptu.setPower(ptuFlexRequest.getPower());
            flexRequest.getPTU().add(ptu);
            //TECNALIA-BEGIN
            //Save data in AGR_FLEX_REQUEST_PTU table
            ptuStartDateTime = startDateTime.plusMinutes((ptuFlexRequest.getPtuIndex().intValue() -  1) * ptuDuration);
            ptuEndDateTime = startDateTime.plusMinutes((ptuFlexRequest.getPtuIndex().intValue() -  0) * ptuDuration);           
            agrFlexRequestPtuRepository.create(agrFlexRequestId, ptuStartDateTime,
                    ptuEndDateTime, 1, ptuFlexRequest.getPtuIndex().intValue(), ptuFlexRequest.getDisposition(),
                    ptuFlexRequest.getPower().floatValue());
        //});
        }
            //TECNALIA-END
        return flexRequest;
    }

    private MessageMetadata buildMessageMetadata(String aggregatorDomain) {
        return new MessageMetadataBuilder().messageID()
                .conversationID()
                .timeStamp()
                .precedence(TRANSACTIONAL)
                .recipientDomain(aggregatorDomain)
                .recipientRole(USEFRole.AGR)
                .senderDomain(config.getProperty(ConfigParam.HOST_DOMAIN))
                .senderRole(USEFRole.BRP)
                .build();
    }
}
