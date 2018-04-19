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

import energy.usef.brp.model.dataModelFHP.MarketForecast;
import energy.usef.core.repository.BaseRepository;
import energy.usef.core.util.DateTimeUtil;

import javax.ejb.Stateless;
import javax.transaction.Transactional;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * MarketForecast Repository for BRP.
 */
@Stateless
public class MarketForecastRepository extends BaseRepository<MarketForecast> {

    /**
     * Initialize MarketForecast table for a determined date.
     * 
     * @param startDate
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @return MarketForecast Id
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public long InitializeTestValues(LocalDate startDate, Integer ptuDuration, int numberOfPtusPerDay) {
        MarketForecast marketForecast = new MarketForecast();
        //TODO: Initialize MarketForecast and MarketForecastPtu tables with Test values
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        marketForecast.setDatetime(now.toDateTime().toDate());
       
        marketForecast.setStartDate(startDate.toDateMidnight().toDate());
        
        persist(marketForecast);
        return marketForecast.getId();
    }
}
