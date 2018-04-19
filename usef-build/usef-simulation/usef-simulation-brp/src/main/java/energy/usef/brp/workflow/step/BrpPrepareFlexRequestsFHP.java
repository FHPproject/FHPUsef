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

package energy.usef.brp.workflow.step;

import energy.usef.brp.config.ConfigBrp;
import energy.usef.brp.config.ConfigBrpParam;
import energy.usef.brp.model.dataModelFHP.AGR;
import energy.usef.brp.model.dataModelFHP.CurAlg;
import energy.usef.brp.model.dataModelFHP.CurAlgDer;
import energy.usef.brp.model.dataModelFHP.CurAlgPtu;
import energy.usef.brp.model.dataModelFHP.CurAlgPtuAgrDer;
import energy.usef.brp.model.dataModelFHP.DER;
import energy.usef.brp.model.dataModelFHP.DerCurtailment;
import energy.usef.brp.model.dataModelFHP.DerCurtailmentPtu;
import energy.usef.brp.model.dataModelFHP.DerProductionForecastPtu;
import energy.usef.brp.model.dataModelFHP.DerCurtailmentType;
import energy.usef.brp.model.dataModelFHP.DerProductionForecast;
import energy.usef.brp.model.dataModelFHP.DerProfitabilityThresholdPtu;
import energy.usef.brp.model.dataModelFHP.MarketReal;
//import energy.usef.brp.model.dataModelFHP.MarketForecastPtu;
import energy.usef.brp.model.dataModelFHP.MarketRealPtu;
import energy.usef.brp.model.dataModelFHP.MarketType;
import energy.usef.brp.pbcfeederimpl.PbcFeederService;
import energy.usef.brp.repository.dataModelFHP.AgrRepository;
import energy.usef.brp.repository.dataModelFHP.CurAlgDerRepository;
import energy.usef.brp.repository.dataModelFHP.CurAlgPtuAgrDerRepository;
import energy.usef.brp.repository.dataModelFHP.CurAlgPtuDerRepository;
import energy.usef.brp.repository.dataModelFHP.CurAlgPtuRepository;
import energy.usef.brp.repository.dataModelFHP.CurAlgRepository;
import energy.usef.brp.repository.dataModelFHP.DerCurtailmentRepository;
import energy.usef.brp.repository.dataModelFHP.DerCurtailmentPtuRepository;
import energy.usef.brp.repository.dataModelFHP.DerProductionForecastPtuRepository;
import energy.usef.brp.repository.dataModelFHP.DerProductionForecastRepository;
import energy.usef.brp.repository.dataModelFHP.DerProfitabilityThresholdPtuRepository;
import energy.usef.brp.repository.dataModelFHP.DerRepository;
//import energy.usef.brp.repository.dataModelFHP.MarketForecastPtuRepository;
//import energy.usef.brp.repository.dataModelFHP.MarketForecastRepository;
import energy.usef.brp.repository.dataModelFHP.MarketRealPtuRepository;
import energy.usef.brp.repository.dataModelFHP.MarketRealRepository;
import energy.usef.brp.workflow.plan.connection.forecast.PrepareFlexRequestWorkflowParameter;
import energy.usef.core.util.DateTimeUtil;
import energy.usef.core.util.PowerUtil;
import energy.usef.core.util.PtuUtil;
import energy.usef.core.workflow.WorkflowContext;
import energy.usef.core.workflow.WorkflowStep;
import energy.usef.core.workflow.dto.DispositionTypeDto;
import energy.usef.core.workflow.dto.FlexRequestDto;
import energy.usef.core.workflow.dto.PrognosisDto;
import energy.usef.core.workflow.dto.PtuFlexRequestDto;
import energy.usef.core.workflow.dto.PtuPrognosisDto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the PBC in charge of handling received A-Plans and asking the generation of flex requests. This step will
 * receive the list of A-Plan data per ptu for a given period (day). This implementation expects to find the following parameters as
 * input:
 * <ul>
 * <li>PTU_DURATION: PTU duration ({@link Integer})</li>
 * <li>PROCESSED_A_PLAN_DTO_LIST: A-Plan DTO list ({@link java.util.List}) of {@link PrognosisDto}</li>
 * </ul>
 * This implementation must return the following parameters as input:
 * <ul>
 * <li>FLEX_REQUEST_DTO_LIST: List of flex requests ({@link java.util.List}) of {@link FlexRequestDto}</li>
 * <li>ACCEPTED_A_PLAN_DTO_LIST: List of accepted A-Plans ({@link java.util.List}) of {@link PrognosisDto}</li>
 * </ul>
 * The list of processed A-Plan DTOs is iterated and for each item a random decision is taken: accepted, flex request is created, no
 * decision.
 */
public class BrpPrepareFlexRequestsFHP implements WorkflowStep {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrpPrepareFlexRequestsFHP.class);

    private static final BigDecimal PERCENTAGE_DEVIATION = new BigDecimal("0.20");
    private static final BigDecimal PERCENTAGE_REDUCTION = new BigDecimal("0.50");
    private static final int FLEX_REQUEST_EXPIRATION_DAYS = 4;

    @Inject
    private PbcFeederService pbcFeederService;

    @Inject
    private ConfigBrp configBrp;
    
//    @Inject 
//    private MarketForecastRepository marketForecastRepository;
    
//    @Inject
//    private MarketForecastPtuRepository marketForecastPtuRepository;
    
    @Inject 
    private MarketRealRepository marketRealRepository;
    
    @Inject
    private MarketRealPtuRepository marketRealPtuRepository;    

    @Inject 
    private DerRepository derRepository;
    
    @Inject
    private AgrRepository agrRepository;

    @Inject
    private DerProfitabilityThresholdPtuRepository derProfitabilityThresholdPtuRepository;
    
    @Inject
    private DerProductionForecastPtuRepository derProductionForecastPtuRepository;
    
    @Inject
    private DerProductionForecastRepository derProductionForecastRepository;
    
    @Inject 
    private DerCurtailmentRepository derCurtailmentRepository;
    
    @Inject
    private DerCurtailmentPtuRepository derCurtailmentPtuRepository;
    
    @Inject
    private CurAlgRepository curAlgRepository;
    
    @Inject
    private CurAlgPtuRepository curAlgPtuRepository;

    @Inject
    private CurAlgDerRepository curAlgDerRepository;
    
    @Inject
    private CurAlgPtuDerRepository curAlgPtuDerRepository;
    
    @Inject
    private CurAlgPtuAgrDerRepository curAlgPtuAgrDerRepository;
    
    /**BigDecimal value BigDecimal value = map.get(ptuAPlanDto.getPtuIndex().intValue());
            = map.get(ptuAPlanDto.getPtuIndex().intValue());
            
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public WorkflowContext invoke(WorkflowContext context) {
        LOGGER.info("BrpPrepareFlexRequestsFHP Stub invoked");

        List<FlexRequestDto> flexRequestDtos = new ArrayList<>();
        List<PrognosisDto> acceptedAPlans = new ArrayList<>();
        List<AGR> agrList = agrRepository.getAgrs();
        
        List<PrognosisDto> aPlanDtos = (List<PrognosisDto>) context
                .getValue(PrepareFlexRequestWorkflowParameter.IN.PROCESSED_A_PLAN_DTO_LIST.name());
        Integer ptuDuration = (Integer) context.getValue(PrepareFlexRequestWorkflowParameter.IN.PTU_DURATION.name());
        LOGGER.debug("Input: [{}] A-Plans (with PROCESSED status).", aPlanDtos.size());

        int agrCount = agrList.size();
        int aPlanCount = aPlanDtos.size();
        boolean process = false;

        //Get AGRs included in BRP portfolio
        boolean agrFound = false;            

        for(AGR agr: agrList) 
        {             
            if (agr.getType().compareTo("DCM") == 0)
            {
                agrFound = false;
                for(PrognosisDto aPlan: aPlanDtos) {  
                    if (aPlan.getParticipantDomain().compareTo(agr.getDomain()) == 0)
                    {
                        agrFound = true;
                    }
                }   
                if (agrFound == false)
                {
                    break;
                }    
            }
        }         
        if (agrFound == true)
        {
            process = true;
        }                

        //if (!aPlanDtos.isEmpty()) {
        // FlexRequests are generated when the last PROCESSED A-Plan is received
        if (process == true) {
            LocalDate aPlanDate = aPlanDtos.get(0).getPeriod();

            int numberOfPtusPerDay = PtuUtil.getNumberOfPtusPerDay(aPlanDate, ptuDuration);
            /*Map<Integer, BigDecimal> map = pbcFeederService.retrieveApxPrices(aPlanDate, 1, numberOfPtusPerDay);

            BigDecimal max = map.values().stream().max(BigDecimal::compareTo).get();
            BigDecimal min = map.values().stream().min(BigDecimal::compareTo).get();*/

            float curtailmentInitial = 0;
            int curtailmentAlgLoopNumber = getCurtailmentAlgLoopNumber(aPlanDate);
            if(curtailmentAlgLoopNumber == 0) {
                /*long marketRealId = priceDMForecast(aPlanDate, ptuDuration, numberOfPtusPerDay);
                curtailmentInitial = calculateInitialCurtailment(aPlanDate, marketRealId, ptuDuration, numberOfPtusPerDay);*/
                
                //long marketRealId = priceDMReal(aPlanDate, ptuDuration, numberOfPtusPerDay);
                curtailmentInitial = calculateInitialCurtailment(aPlanDate, ptuDuration, numberOfPtusPerDay);
            }
            //Calculate RemainingCurtailment
            float remainingCurtailment = updateRemainingCurtailment(aPlanDate, ptuDuration, numberOfPtusPerDay, curtailmentAlgLoopNumber);

            if(remainingCurtailment > 0) {
                // For each received a-plan.
                for (PrognosisDto aPlanDto : aPlanDtos) {
                    LOGGER.debug(
                            "A-Plan with sequence [{}] has been set to be processed for aggregator [{}]. Flex request might be generated.",
                            aPlanDto.getSequenceNumber(), aPlanDto.getParticipantDomain());

                    // If rejected, create a flex request to send.
                    FlexRequestDto flexRequestDto = buildMinimalFlexRequest(aPlanDto);


                    boolean anyFlexRequested  = buildFlexRequestFHPCurtailment(aPlanDto, flexRequestDto, 
                            ptuDuration, numberOfPtusPerDay, curtailmentAlgLoopNumber);

                    if (LOGGER.isTraceEnabled()) {
                        flexRequestDto.getPtus().sort((o1, o2) -> o1.getPtuIndex().compareTo(o2.getPtuIndex()));
                        flexRequestDto.getPtus().forEach(ptu ->
                                LOGGER.trace(" PTU [{}/{}] flex request: [{}] of [{}] Wh", ptu.getPtuIndex(),
                                        numberOfPtusPerDay,
                                        ptu.getDisposition(), PowerUtil.powerToEnergy(ptu.getPower(), ptuDuration)));
                    }
                    if (anyFlexRequested){
                        flexRequestDtos.add(flexRequestDto);
                    } else {
                        acceptedAPlans.add(aPlanDto);
                    }
                }
            }
        }

        context.setValue(PrepareFlexRequestWorkflowParameter.OUT.FLEX_REQUEST_DTO_LIST.name(), flexRequestDtos);
        context.setValue(PrepareFlexRequestWorkflowParameter.OUT.ACCEPTED_A_PLAN_DTO_LIST.name(), acceptedAPlans);

        LOGGER.debug("Output: Accepted [{}] A-Plans (status will be changed to ACCEPTED)", acceptedAPlans.size());
        LOGGER.debug("Output: Flex Requested for [{}] A-Plans (status will be changed to PENDING_FLEX_TRADING)", flexRequestDtos.size());

        return context;
    }

    private boolean buildFlexRequest(Map<Integer, BigDecimal> map, BigDecimal max, BigDecimal min, PrognosisDto aPlanDto,
            FlexRequestDto flexRequestDto) {
        boolean anyFlexRequested = false;
        for (PtuPrognosisDto ptuAPlanDto : aPlanDto.getPtus()) {
            BigDecimal value = map.get(ptuAPlanDto.getPtuIndex().intValue());
            // highest
            // max.subtract(value).divide(max) <= 0.20%
            if (max.subtract(value).divide(max, 2, RoundingMode.HALF_UP).abs().compareTo(PERCENTAGE_DEVIATION) <= 0) {
                anyFlexRequested = true;
                // expensive so request reduction
                flexRequestDto.getPtus().add(buildFlexRequestRequestedPtu(ptuAPlanDto, true));
                // lowest
                // value.subtract(min).divide(min) <= 0.20%
            } else if (value.subtract(min).divide(min, 2, RoundingMode.HALF_UP).abs().compareTo(PERCENTAGE_DEVIATION) <= 0) {
                anyFlexRequested = true;
                // cheap so request increase
                flexRequestDto.getPtus().add(buildFlexRequestRequestedPtu(ptuAPlanDto, false));
            } else {
                // average
                flexRequestDto.getPtus().add(buildFlexRequestAvailablePtu(ptuAPlanDto));
            }
        }
        return anyFlexRequested;
    }

  
    private boolean buildFlexRequestFHP(Map<Integer, BigDecimal> map, BigDecimal max, BigDecimal min, PrognosisDto aPlanDto,
            FlexRequestDto flexRequestDto) {
        boolean anyFlexRequested = true;
        for (PtuPrognosisDto ptuAPlanDto : aPlanDto.getPtus()) {
            BigDecimal value = map.get(ptuAPlanDto.getPtuIndex().intValue());
            flexRequestDto.getPtus().add(buildFlexRequestRequestedPtuFHP(ptuAPlanDto, true));
        }
        return anyFlexRequested;
    }


    /**
     * @author TECNALIA
     * 
     */
    private boolean buildFlexRequestFHPCurtailment(PrognosisDto aPlanDto,
            FlexRequestDto flexRequestDto, Integer ptuDuration, 
            int numberOfPtusPerDay, int curtailmentAlgLoopNumber) {
        boolean anyFlexRequested = true;
        LocalDate startDate = aPlanDto.getPeriod();
        LocalDateTime startDateTime = new LocalDateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDateTime = startDateTime.plusMinutes(ptuDuration*numberOfPtusPerDay);
        LocalDateTime ptuStartDateTime = startDateTime;
        LocalDateTime ptuEndDateTime = ptuStartDateTime.plusMinutes(ptuDuration);
        for (PtuPrognosisDto ptuAPlanDto : aPlanDto.getPtus()) {
            CurAlgPtu curAlgPtu = curAlgPtuRepository.get(curtailmentAlgLoopNumber, ptuStartDateTime, ptuEndDateTime);
            if(curAlgPtu.getPortfolioRemainingCurtailment() != 0) {
                //DispositionTypeDto.REQUESTED
                flexRequestDto.getPtus().add(buildFlexRequestRequestedPtuFHPCurtailment(ptuAPlanDto, 
                        curAlgPtu.getPortfolioRemainingCurtailment()));
            } else {
                //DispositionTypeDto.AVAILABLE
                //This is just applicable to the DSO flex request
                //flexRequestDto.getPtus().add(buildFlexRequestAvailablePtu(ptuAPlanDto));
             }
            ptuStartDateTime = ptuStartDateTime.plusMinutes(ptuDuration);
            ptuEndDateTime = ptuEndDateTime.plusMinutes(ptuDuration);
         }
        return anyFlexRequested;
    }

    /**
     * Builds a ptu of a flex request from the ptu of the related A-Plan where either reduction or increase of power is needed.
     *
     * @author TECNALIA
     * 
     * @param aplanPtu {@link PtuPrognosisDto} one of the ptus of the related A-Plan.
     * @param portfolioRemainingCurtailment
     * @return a PTU for the flex request.
     */
    private PtuFlexRequestDto buildFlexRequestRequestedPtuFHPCurtailment(PtuPrognosisDto aplanPtu, float portfolioRemainingCurtailment) {
        PtuFlexRequestDto ptuFlexRequestDto = new PtuFlexRequestDto();
        ptuFlexRequestDto.setPtuIndex(aplanPtu.getPtuIndex());
        ptuFlexRequestDto.setDisposition(DispositionTypeDto.REQUESTED);
        BigInteger power = new BigDecimal(portfolioRemainingCurtailment).toBigInteger();
        ptuFlexRequestDto.setPower(power);
        return ptuFlexRequestDto;
    }
    
    /**
     * Creates a minimal flex request from a A-Plan.
     *
     * @param aplanDto {@link PrognosisDto} A-Plan.
     * @return a Flex Request with the period and the origin and sequence of the related prognosis.
     */
    private FlexRequestDto buildMinimalFlexRequest(PrognosisDto aplanDto) {
        FlexRequestDto flexRequestDto = new FlexRequestDto();
        flexRequestDto.setPeriod(aplanDto.getPeriod());
        flexRequestDto.setParticipantDomain(aplanDto.getParticipantDomain());
        flexRequestDto.setPrognosisSequenceNumber(aplanDto.getSequenceNumber());
        flexRequestDto.setExpirationDateTime(DateTimeUtil.getCurrentDateTime().plusDays(FLEX_REQUEST_EXPIRATION_DAYS));
        return flexRequestDto;
    }

    /**
     * Builds a ptu of a flex request from the ptu of the related A-Plan. No power is available is ever available.
     *
     * @param aplanPtu {@link PtuPrognosisDto} one of the ptus of the related A-Plan.
     * @return a PTU for the flex request.
     */
    private PtuFlexRequestDto buildFlexRequestAvailablePtu(PtuPrognosisDto aplanPtu) {
        PtuFlexRequestDto ptuFlexRequestDto = new PtuFlexRequestDto();
        ptuFlexRequestDto.setPtuIndex(aplanPtu.getPtuIndex());
        ptuFlexRequestDto.setDisposition(DispositionTypeDto.AVAILABLE);
        ptuFlexRequestDto.setPower(BigInteger.ZERO);

        return ptuFlexRequestDto;
    }

    /**
     * Builds a ptu of a flex request from the ptu of the related A-Plan where either reduction or increase of power is needed.
     *
     * @param aplanPtu {@link PtuPrognosisDto} one of the ptus of the related A-Plan.
     * @param reduction {@link Boolean} Is there reduction or increase of power.
     * @return a PTU for the flex request.
     */
    private PtuFlexRequestDto buildFlexRequestRequestedPtu(PtuPrognosisDto aplanPtu, boolean reduction) {
        PtuFlexRequestDto ptuFlexRequestDto = new PtuFlexRequestDto();
        ptuFlexRequestDto.setPtuIndex(aplanPtu.getPtuIndex());
        ptuFlexRequestDto.setDisposition(DispositionTypeDto.REQUESTED);
        BigInteger difference = new BigDecimal(aplanPtu.getPower()).multiply(PERCENTAGE_REDUCTION)
                .setScale(0, RoundingMode.HALF_UP).toBigInteger();
        if (reduction) {
            // if this is a reduction negate the difference
            difference = difference.negate();
        }
        ptuFlexRequestDto.setPower(difference);
        return ptuFlexRequestDto;
    }

    private PtuFlexRequestDto buildFlexRequestRequestedPtuFHP(PtuPrognosisDto aplanPtu, boolean reduction) {
        PtuFlexRequestDto ptuFlexRequestDto = new PtuFlexRequestDto();
        ptuFlexRequestDto.setPtuIndex(aplanPtu.getPtuIndex());
        ptuFlexRequestDto.setDisposition(DispositionTypeDto.REQUESTED);
        //TODO: Parametrizar parametro
        for (int ptuIndex=0; ptuIndex<96; ptuIndex=ptuIndex+1){
            String BRP_ACTIVE_POWER = "BRP_ACTIVE_POWER"+ ptuIndex;
            BigInteger brpActivePower = BigInteger.valueOf(configBrp.getIntegerProperty(ConfigBrpParam.BRP_ACTIVE_POWER1).intValue());
            //BigInteger brpActivePower = BigInteger.valueOf(configBrp.getIntegerProperty(ConfigBrpParam.BRP_ACTIVE_POWER).intValue());
            ptuFlexRequestDto.setPower(brpActivePower);
        }
        return ptuFlexRequestDto;
    }

    /**
     * This method returns the loop number of the iteration of the Curtailment 
     * Mitigation algorithm.
     *
     * @author TECNALIA
     * 
     * @param startDate
     * @return 
     */   
    private int getCurtailmentAlgLoopNumber(LocalDate startDate) {
        int curtailmentAlgLoopNumber = 0;
        //Get loop value for date startDate from CUR_ALG DB table
        CurAlg curAlg = curAlgRepository.getLastCurAlgForDate(startDate, MarketType.DAY_AHEAD_MARKET);
        if (curAlg != null) {
            curtailmentAlgLoopNumber = curAlg.getLoop();
            curtailmentAlgLoopNumber++;
        } 
        return curtailmentAlgLoopNumber;
    }
    
    /**
     * This method makes a forecast of the day ahead market price for each PTU of the following day
     *
     * @author TECNALIA
     * 
     * @param startDate
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @return 
     */    
    /*private long priceDMForecast(LocalDate startDate, Integer ptuDuration, int numberOfPtusPerDay) {
        //Initialize PriceDMReal and EnergyDMForecast: MARKET_FORECAST and MARKET_FORECAST_PTU tables
        long marketRealId = marketForecastRepository.InitializeTestValues(startDate, ptuDuration, numberOfPtusPerDay);
        return marketRealId;
    }  */
    
    /**
     * This method makes a dummy real day ahead market price for each PTU of the following day
     *
     * @author TECNALIA
     * 
     * @param startDate
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @return 
     */    
    private long priceDMReal(LocalDate startDate, Integer ptuDuration, int numberOfPtusPerDay) {
        //Initialize PriceDMReal and EnergyDMReal: MARKET_FORECAST and MARKET_FORECAST_PTU tables
        long marketRealId = marketRealRepository.InitializeTestValues(startDate, ptuDuration, numberOfPtusPerDay);
        long numMarketRealPTU = marketRealPtuRepository.InitializeTestValues(marketRealId, startDate, ptuDuration, numberOfPtusPerDay);
        
        return marketRealId;
    }      
    
    /**
     * This method informs about how much energy could be curtailed and in which PTU
     * DERCurtailmentInitial = DERProductionForecast when PriceDMReal < DERProfitabilityThreshold
     * DERCurtailmentInitial = 0 when PriceDMReal >= DERProfitabilityThreshold
     *
     * @author TECNALIA
     * 
     * @param startDate
     * @param marketRealId
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @return  
     */
    private float calculateInitialCurtailment(LocalDate startDate, Integer ptuDuration, int numberOfPtusPerDay) {
        float curtailmentInitial = 0;// For all DERs and all PTUs
        
        // retrieve the data from the PBC Feeder
        Map<Integer, BigDecimal> pbcStubDataSolveWindForecast = pbcFeederService
        .retrieveSolveWindForecast(startDate, 1, numberOfPtusPerDay);  
        Map<Integer, BigDecimal> pbcStubDataMarketPrice = pbcFeederService
        .retrieveApxPrices(startDate, 1, numberOfPtusPerDay);          
                //Map<Integer, BigDecimal> pbcStubData = pbcFeederService
                //.retrieveSolveWindForecast(startDate, ((startDate.getDayOfWeek() - 1)*numberOfPtusPerDay)+1, numberOfPtusPerDay);    
                    
        //Get DERs producers included in BRP portfolio
        List<DER> derList = derRepository.getDers();
        for(DER der: derList) {
            //Fetch DER_CURTAILMENT register for CURTAILMENT_INITIAL
            //LocalDateTime startDateTime = new LocalDateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0, 0, 0);
            LocalDateTime startDateTime = startDate.toDateTimeAtStartOfDay().toLocalDateTime();
            LocalDateTime endDateTime = startDateTime.plusMinutes(ptuDuration*numberOfPtusPerDay);
            
            DerCurtailment derCurtailment = derCurtailmentRepository.fetch(der.getId(), 
                    DerCurtailmentType.CURTAILMENT_INITIAL, startDateTime,
                    endDateTime, ptuDuration, numberOfPtusPerDay);      
            
            DerProductionForecast derProductionForecast = derProductionForecastRepository.fetch(der.getId(), 
                    startDateTime,
                    endDateTime, ptuDuration, numberOfPtusPerDay);        
            
            MarketReal marketReal = marketRealRepository.fetch(MarketType.DAY_AHEAD_MARKET, 
                    startDateTime,
                    endDateTime, ptuDuration, numberOfPtusPerDay);              

            LocalDateTime ptuStartDateTime = startDateTime;
            LocalDateTime ptuEndDateTime = ptuStartDateTime.plusMinutes(ptuDuration*1);
            final int numPtus = 1;
            //curtailmentInitial = 0;
            for (int ptuNum = 1; ptuNum<=numberOfPtusPerDay; ptuNum++) {
                float activePower = 0;
                BigDecimal solveWindForecast = pbcStubDataSolveWindForecast.get(ptuNum);
                float powerMax = der.getPowerMax();
                activePower = powerMax * solveWindForecast.floatValue() / 1000;    
                BigDecimal priceBD = pbcStubDataMarketPrice.get(ptuNum);
                float price = priceBD.floatValue();
                DerProductionForecastPtu derProductionForecastPtu = 
                derProductionForecastPtuRepository.fetch(der.getId(), derProductionForecast.getId(),
                        ptuNum, 1, ptuStartDateTime, ptuEndDateTime, activePower);
                
                MarketRealPtu marketRealPtu = marketRealPtuRepository.fetch(marketReal.getId(), 
                        ptuNum, 1, ptuStartDateTime, ptuEndDateTime, price);
                
                DerProfitabilityThresholdPtu derProfitabilityThresholdPtu = 
                        derProfitabilityThresholdPtuRepository.get(der.getId(), ptuStartDateTime, ptuEndDateTime);
                
                // Prices are in €/MWh
                float profitabilityThreshold =  derProfitabilityThresholdPtu.getProfitabilityThreshold();
                if(price < profitabilityThreshold) {
                    activePower = derProductionForecastPtu.getActivePower();
                    curtailmentInitial+=activePower;
                }
                else activePower = 0;
                
                //Create DerCurtailmentInitial table: DER_CURTAILMENT_PTU
                derCurtailmentPtuRepository.create(derCurtailment.getId(), ptuNum, numPtus, ptuStartDateTime, ptuEndDateTime, activePower);
                ptuStartDateTime = ptuStartDateTime.plusMinutes(ptuDuration);
                ptuEndDateTime = ptuEndDateTime.plusMinutes(ptuDuration);
            }
        }
       return curtailmentInitial;
    }
    
    /**
     * Method that updates DERRemainingCurtailment value
     * if loop=0, DERRemainingCurtailment = DERCurtailmentInitial
     * else DERRemainingCurtailment=DERRemainingInitial - Sum(FlexAGREDEREnergy)
     *
     * @author TECNALIA
     * 
     * @param startDate
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @param curtailmentAlgLoopNumber     
     * @return  
     */
    private float updateRemainingCurtailment(LocalDate startDate, Integer ptuDuration, int numberOfPtusPerDay,
            int curtailmentAlgLoopNumber) {
        float remainingCurtailment = 0;
        LocalDateTime startDateTime = new LocalDateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDateTime = startDateTime.plusMinutes(ptuDuration*numberOfPtusPerDay);
        LocalDateTime ptuStartDateTime = startDateTime;
        LocalDateTime ptuEndDateTime = ptuStartDateTime.plusMinutes(ptuDuration);
        
        //Create PortfolioRemainingCurtailment table: CUR_ALG 
        long curAlgId = curAlgRepository.create(curtailmentAlgLoopNumber, startDateTime,
                    endDateTime, ptuDuration, numberOfPtusPerDay, MarketType.DAY_AHEAD_MARKET);
        //Get DERs producers included in BRP portfolio
        List<DER> derList = derRepository.getDers();
        for(DER der: derList) {
            //Create DERRemainingCurtailment tables: CURL_ALG_DER
            long curAlgDerId = curAlgDerRepository.create(curAlgId, der.getId());
        }
            
        final int numPtus = 1;
        for (int ptuNum = 1; ptuNum<=numberOfPtusPerDay; ptuNum++) {
            float portfolioRemainingCurtailmentPtu =0;
            //Create PortfolioRemainingCurtailment table: CURL_ALG_PTU table          
            long curAlgPtuId = curAlgPtuRepository.create(curAlgId, ptuNum, numPtus, 
                ptuStartDateTime, ptuEndDateTime);
            
            for(DER der: derList) {
                //Get DERCurtailmentInitialPtu
                DerCurtailmentPtu derCurtailmentPtu = derCurtailmentPtuRepository.get(der.getId(), 
                    ptuStartDateTime, ptuEndDateTime, DerCurtailmentType.CURTAILMENT_INITIAL);
                float derRemainingCurtailmentPtu =0;
                if(curtailmentAlgLoopNumber == 0) {
                    derRemainingCurtailmentPtu = derCurtailmentPtu.getActivePower(); 
                } else {
                    List<AGR> agrList = agrRepository.getAgrs();
                    float curAlgPtuAgrDerEnergySum = 0;
                    for(AGR agr:agrList) {
                        CurAlgPtuAgrDer curAlgPtuAgrDer = curAlgPtuAgrDerRepository.get(agr.getId(), 
                                der.getId(), curtailmentAlgLoopNumber-1, ptuStartDateTime, ptuEndDateTime);
                        curAlgPtuAgrDerEnergySum += curAlgPtuAgrDer.getAgrDerEnergy();
                    }
                    derRemainingCurtailmentPtu = derCurtailmentPtu.getActivePower() - curAlgPtuAgrDerEnergySum;
                }
                //Create DERRemainingCurtailment tables: CURL_ALG_PTU_DER
                CurAlgDer curAlgDer = curAlgDerRepository.get(curAlgId, der.getId());
                curAlgPtuDerRepository.create(curAlgDer.getId(), curAlgPtuId, der.getId(), derRemainingCurtailmentPtu);
                portfolioRemainingCurtailmentPtu += derRemainingCurtailmentPtu;
            }
            //Update PortfolioRemainingCurtailment
            curAlgPtuRepository.updateCurtailment(curAlgPtuId, portfolioRemainingCurtailmentPtu);
            remainingCurtailment += portfolioRemainingCurtailmentPtu;                
            ptuStartDateTime = ptuStartDateTime.plusMinutes(ptuDuration);
            ptuEndDateTime = ptuEndDateTime.plusMinutes(ptuDuration);
        } 
        return remainingCurtailment;
    }  
}
