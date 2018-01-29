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

import energy.usef.brp.model.dataModelFHP.DerCurtailment;
import energy.usef.brp.model.dataModelFHP.DerProductionType;
import energy.usef.brp.model.dataModelFHP.DerProductionForecast;
import energy.usef.core.repository.BaseRepository;
import energy.usef.core.util.DateTimeUtil;

import javax.ejb.Stateless;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * DerCurtailment Repository for BRP.
 */
@Stateless
public class DerCurtailmentRepository extends BaseRepository<DerCurtailment> {

    /**
     * Create Curtailment.
     * 
     * @param derId
     * @param derProductionType
     * @param startDateTime
     * @param endDateTime
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @return created DerCurtailment ID.
     */
    public long create(long derId, DerProductionType derProductionType, LocalDateTime startDateTime,
        LocalDateTime endDateTime, Integer ptuDuration, int numberOfPtusPerDay) {
        
        DerCurtailment derCurtailment = new DerCurtailment();
        derCurtailment.setDerId(derId);
        derCurtailment.setType(derProductionType);
        derCurtailment.setNumberPtus(numberOfPtusPerDay);
        derCurtailment.setPtuDurationMins(ptuDuration);
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        derCurtailment.setDatetime(now.toDateTime().toDate());
        LocalDate startDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        derCurtailment.setStartDate(startDate.toDateMidnight().toDate());
        LocalDate endDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        derCurtailment.setEndDate(endDate.toDateMidnight().toDate());
        derCurtailment.setStartDatetime(startDateTime.toDateTime().toDate());
        derCurtailment.setEndDatetime(endDateTime.toDateTime().toDate());
       
        persist(derCurtailment);
        return derCurtailment.getId();
    }
}
