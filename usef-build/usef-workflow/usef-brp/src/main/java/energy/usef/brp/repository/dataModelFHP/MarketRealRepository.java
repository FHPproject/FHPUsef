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

package energy.usef.brp.repository.dataModelFHP;

import energy.usef.brp.model.dataModelFHP.MarketReal;
import energy.usef.brp.model.dataModelFHP.MarketType;
import energy.usef.core.repository.BaseRepository;
import energy.usef.core.util.DateTimeUtil;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * MarketReal Repository for BRP.
 */
@Stateless
public class MarketRealRepository extends BaseRepository<MarketReal> {

    /**
     * Initialize MarketReal table for a determined date.
     * 
     * @param startDate
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @return MarketReal Id
     */
    
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long InitializeTestValues(LocalDate startDate, Integer ptuDuration, int numberOfPtusPerDay) {
        MarketReal marketReal = new MarketReal();
        //TODO: Initialize MarketReal table with Test values
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        LocalDate endDate = startDate.plusDays(1);
        
        marketReal.setDatetime(now.toDateTime().toDate());
       
        //marketReal.setStartDate(startDate.toDateMidnight().toDate());
        Date DateStartDate = startDate.toDateTimeAtStartOfDay().toDate(); 
        Date DateEndDate = endDate.toDateTimeAtStartOfDay().toDate();
        LocalDateTime DateStartDatetime = startDate.toDateTimeAtStartOfDay().toLocalDateTime(); 
        LocalDateTime DateEndDatetime = endDate.toDateTimeAtStartOfDay().toLocalDateTime();       
            
        marketReal.setStartDate(DateStartDate); 
        marketReal.setEndDate(DateEndDate);
        marketReal.setStartDatetime(DateStartDatetime); 
        marketReal.setEndDatetime(DateEndDatetime);        
        
        marketReal.setPtuDurationMins(ptuDuration);
        marketReal.setNumberPtus(numberOfPtusPerDay);
        
        marketReal.setMarketType(MarketType.DAY_AHEAD_MARKET);
        
        persist(marketReal);
        return marketReal.getId();
    }
    
    /**
     * Return the MarketReal for a determined date and DER.
     * 
     * @param derId
     * @param startDate
     * @return MarketReal entities
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public MarketReal fetch(MarketType marketType, LocalDateTime startDateTime,
        LocalDateTime endDateTime, Integer ptuDuration, int numberOfPtusPerDay){
        // The same as get but in case there is no coincident register it creates it from stub data
        MarketReal marketReal = 
                get(marketType, startDateTime, endDateTime);
        if ((marketReal == null) || (marketReal.getId() == 0))
        {
            create(marketType, startDateTime,
                endDateTime, ptuDuration, numberOfPtusPerDay);
            marketReal = get(marketType, startDateTime,
                                endDateTime);                
        }          

        if(marketReal == null)
            return null;
                
        return marketReal;
    }    
    
    
    /**
     * 
     * @param startDateTime
     * @param endDateTime
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @return created MarketReal ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create(MarketType marketType, LocalDateTime startDateTime,
        LocalDateTime endDateTime, Integer ptuDuration, int numberOfPtusPerDay) {
        
        MarketReal marketReal = new MarketReal();
        marketReal.setNumberPtus(numberOfPtusPerDay);
        marketReal.setPtuDurationMins(ptuDuration);
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        marketReal.setDatetime(now.toDateTime().toDate());       
        marketReal.setStartDate(startDateTime.toLocalDate().toDateTimeAtStartOfDay().toDate());        
        marketReal.setEndDate(endDateTime.toLocalDate().toDateTimeAtStartOfDay().toDate());        
        marketReal.setStartDatetime(startDateTime);
        marketReal.setEndDatetime(endDateTime);
        
        marketReal.setMarketType(marketType);        
       
        persist(marketReal);
        return marketReal.getId();
    }        
    
    public MarketReal get(long marketRealId) {
        StringBuilder queryString = new StringBuilder("SELECT ptu FROM MarketReal mr");
        queryString.append(" WHERE mr.marketRealId = :marketRealId");
        List<MarketReal> result = entityManager.createQuery(queryString.toString(), MarketReal.class)
                .setParameter("marketRealId", marketRealId)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }  
    
    public MarketReal get(MarketType marketType, LocalDateTime startDateTime,
        LocalDateTime endDateTime){
        StringBuilder queryString = new StringBuilder("SELECT c FROM MarketReal c");
        queryString.append(" WHERE c.marketType = :marketType");        
        queryString.append(" AND c.startDatetime <= :startDateTime");
        queryString.append(" AND c.endDatetime >= :endDateTime");
        List<MarketReal> result = entityManager.createQuery(queryString.toString(), MarketReal.class)
                .setParameter("marketType", marketType)
                .setParameter("startDateTime", startDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("endDateTime", endDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }      

}
