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

import energy.usef.brp.model.dataModelFHP.AgrFlexRequestPtu;
import energy.usef.core.repository.BaseRepository;
import energy.usef.core.workflow.dto.DispositionTypeDto;

import javax.ejb.Stateless;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * AgrFlexRequestPtu Repository for BRP.
 */
@Stateless
public class AgrFlexRequestPtuRepository extends BaseRepository<AgrFlexRequestPtu> {

    /**
     * Create AgrFlexRequestPtu.
     * 
     * @param agrFlexRequestId
     * @param startDateTime
     * @param endDateTime
     * @param numberOfPtus
     * @param startPtu
     * @param activePower
     * @param disposition
     * @return created AgrFlexRequestPtu ID.
     */
    public long create(long agrFlexRequestId, LocalDateTime startDateTime,
        LocalDateTime endDateTime, int numberOfPtus, int startPtu, DispositionTypeDto disposition,
        float activePower) {
        
        AgrFlexRequestPtu agrFlexRequestPtu = new AgrFlexRequestPtu();
        agrFlexRequestPtu.setAgrFlexRequestId(agrFlexRequestId);
        LocalDate startDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        agrFlexRequestPtu.setStartDate(startDate.toDateMidnight().toDate());
        LocalDate endDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        agrFlexRequestPtu.setEndDate(endDate.toDateMidnight().toDate());
        agrFlexRequestPtu.setStartDatetime(startDateTime.toDateTime().toDate());
        agrFlexRequestPtu.setEndDatetime(endDateTime.toDateTime().toDate());
        agrFlexRequestPtu.setNumberPtus(numberOfPtus);
        agrFlexRequestPtu.setStartPtu(startPtu);
        agrFlexRequestPtu.setDisposition(disposition);
        agrFlexRequestPtu.setActivePower(activePower);
        
        persist(agrFlexRequestPtu);
        return agrFlexRequestPtu.getId();
    }
}
