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

import energy.usef.brp.model.dataModelFHP.DerProductionForecastPtu;
import energy.usef.brp.model.dataModelFHP.MarketRealPtu;
import energy.usef.core.repository.BaseRepository;
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
 * MarketRealPtu Repository for BRP.
 */
@Stateless
public class MarketRealPtuRepository extends BaseRepository<MarketRealPtu> {

    /**
     * Return the MarketRealPtu.
     * 
     * @param marketRealId
     * @param ptuStartDateTime
     * @param ptuEndDateTime
     * @return MarketRealPtu entities
     */
    public MarketRealPtu get(long marketRealId, LocalDateTime ptuStartDateTime,
            LocalDateTime ptuEndDateTime) {
        StringBuilder queryString = new StringBuilder("SELECT ptu FROM MarketRealPtu ptu");
        queryString.append(" WHERE ptu.marketRealId = :marketRealId");
        queryString.append(" AND ptu.startDatetime <= :ptuStartDateTime");
        queryString.append(" AND ptu.endDatetime >= :ptuEndDateTime");
        List<MarketRealPtu> result = entityManager.createQuery(queryString.toString(), MarketRealPtu.class)
                .setParameter("marketRealId", marketRealId)
                .setParameter("ptuStartDateTime", ptuStartDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("ptuEndDateTime", ptuEndDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }
    

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create(long marketRealId, int startPtu, int numPtus, 
            LocalDateTime startDateTime, LocalDateTime endDateTime, float price) {
            MarketRealPtu marketRealPtu = new MarketRealPtu();                    
            
            marketRealPtu.setStartDate(startDateTime.toLocalDate().toDateTimeAtStartOfDay().toDate());        
            marketRealPtu.setEndDate(endDateTime.toLocalDate().toDateTimeAtStartOfDay().toDate());        
            marketRealPtu.setStartDatetime(startDateTime);
            marketRealPtu.setEndDatetime(endDateTime);       

            marketRealPtu.setMarketRealId(marketRealId);
            marketRealPtu.setStartPtu(startPtu);
            marketRealPtu.setNumberPtus(1);
            
            marketRealPtu.setPrice(price);
            //marketRealPtu.setEnergy(0);

            persist(marketRealPtu);
        return marketRealPtu.getId();
    }    

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long InitializeTestValues(long marketRealId, LocalDate startDate, Integer ptuDuration, int numberOfPtusPerDay) {

        //TODO: Initialize MarketRealPtu tables with Test values
        int ptuDone = 0;
        
        for (int ptuNum = 1; ptuNum<=numberOfPtusPerDay; ptuNum++) {
            MarketRealPtu marketRealPtu = new MarketRealPtu();
                    
            LocalDateTime dayStart = startDate.toDateTimeAtStartOfDay().toLocalDateTime();
            LocalDateTime ptuStart = dayStart.plusMinutes(ptuDuration * (ptuNum - 1));
            LocalDateTime ptuEnd = ptuStart.plusMinutes(ptuDuration);
            LocalDate endDate = startDate.plusDays(1);
            
            Date DateStartDate = startDate.toDateTimeAtStartOfDay().toDate(); 
            Date DateEndDate = ptuEnd.toLocalDate().toDateTimeAtStartOfDay().toDate();
            

            marketRealPtu.setStartDate(DateStartDate); 
            marketRealPtu.setEndDate(DateEndDate);            
            
            marketRealPtu.setStartDatetime(ptuStart); 
            marketRealPtu.setEndDatetime(ptuEnd);        

            marketRealPtu.setMarketRealId(marketRealId);
            marketRealPtu.setStartPtu(ptuNum);
            marketRealPtu.setNumberPtus(1);
            
            marketRealPtu.setPrice(1);
            marketRealPtu.setEnergy(1);

            persist(marketRealPtu);
            ptuDone++;
        }
      

        return ptuDone;
    }    
    
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public MarketRealPtu fetch(long marketRealId, 
            int startPtu, int numPtus, 
            LocalDateTime ptuStartDateTime, LocalDateTime ptuEndDateTime, 
            float price){
        // The same as get but in case there is no coincident register it creates it from stub data
        MarketRealPtu marketRealPtu = 
                get(marketRealId, ptuStartDateTime, ptuEndDateTime);
        if ((marketRealPtu == null) || (marketRealPtu.getId() == 0))
        {
            create(marketRealId, startPtu, numPtus, 
            ptuStartDateTime, ptuEndDateTime, price);
            marketRealPtu = get(marketRealId, ptuStartDateTime,
            ptuEndDateTime);                
        }          

        if(marketRealPtu == null)
            return null;
                
        return marketRealPtu;
    }        
}
