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

import energy.usef.brp.model.dataModelFHP.FlexAlg;
import energy.usef.brp.model.dataModelFHP.MarketType;
import energy.usef.core.repository.BaseRepository;
import energy.usef.core.util.DateTimeUtil;

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
 * FlexAlg Repository for BRP.
 */
@Stateless
public class FlexAlgRepository extends BaseRepository<FlexAlg> {

    /**
     * Return the last step of the Curtailment Algorithm for a determined date.
     * 
     * @param startDate
     * @return List of MeterDataCompany entities
     */
    public FlexAlg getLastFlexAlgForDate(LocalDate startDate, MarketType type) {
        StringBuilder queryString = new StringBuilder("SELECT c FROM FlexAlg c");
        queryString.append(" WHERE startDate = :startDate");
        queryString.append(" AND type = :type");        
        List<FlexAlg> result = entityManager.createQuery(queryString.toString(), FlexAlg.class)
                .setParameter("startDate", startDate.toDateMidnight().toDate(), TemporalType.DATE)
                .setParameter("type", type)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }
    
    /**
     * Create FlexAlg.
     * 
     * @param startDateTime
     * @param endDateTime
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @param type
     * @return created FlexAlg ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create( LocalDateTime startDateTime,
        LocalDateTime endDateTime, Integer ptuDuration, int numberOfPtusPerDay, MarketType type) {
        FlexAlg flexAlg = new FlexAlg();

        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        flexAlg.setDatetime(now.toDateTime().toDate());
        flexAlg.setNumberPtus(numberOfPtusPerDay);
        flexAlg.setPtuDurationMins(ptuDuration);
        LocalDate startDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        flexAlg.setStartDate(startDate.toDateMidnight().toDate());
        LocalDate endDate = new LocalDate(endDateTime.getYear(), endDateTime.getMonthOfYear(), endDateTime.getDayOfMonth());
        flexAlg.setEndDate(endDate.toDateMidnight().toDate());
        flexAlg.setStartDatetime(startDateTime);
        flexAlg.setEndDatetime(endDateTime);
        flexAlg.setImbalancePayment(0);
        flexAlg.setFlexPayment(0);
        flexAlg.setCashFlow(0);
        flexAlg.setType(type);
       
        persist(flexAlg);
        return flexAlg.getId();
    }
    

}
