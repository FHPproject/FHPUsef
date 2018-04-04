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
import energy.usef.brp.model.dataModelFHP.MarketRealPtu;
import energy.usef.core.repository.BaseRepository;
import energy.usef.core.util.DateTimeUtil;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
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
    public long InitializeTestValues(LocalDate startDate, Integer ptuDuration, int numberOfPtusPerDay) {
        MarketReal marketReal = new MarketReal();
        //TODO: Initialize MarketReal table with Test values
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        LocalDate endDate = startDate.plusDays(1);
        
        marketReal.setDatetime(now.toDateTime().toDate());
       
        //marketReal.setStartDate(startDate.toDateMidnight().toDate());
        marketReal.setStartDate(startDate.toDateTimeAtStartOfDay().toDate()); 
        marketReal.setEndDate(endDate.toDateTimeAtStartOfDay().toDate());
        marketReal.setStartDatetime(startDate.toDateTimeAtStartOfDay().toDate()); 
        marketReal.setEndDatetime(endDate.toDateTimeAtStartOfDay().toDate());        
        
        marketReal.setPtuDurationMins(ptuDuration);
        marketReal.setNumberPtus(numberOfPtusPerDay);
        
        marketReal.setMarketType("DAY_AHEAD_MARKET");
        
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
}
