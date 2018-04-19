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

import energy.usef.brp.model.dataModelFHP.AgrFlexOfferPtu;
import energy.usef.core.repository.BaseRepository;
import energy.usef.core.workflow.dto.DispositionTypeDto;

import javax.ejb.Stateless;
import javax.transaction.Transactional;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * AgrFlexOfferPtu Repository for BRP.
 */
@Stateless
public class AgrFlexOfferPtuRepository extends BaseRepository<AgrFlexOfferPtu> {

    /**
     * Create AgrFlexOfferPtu.
     * 
     * @param agrFlexOfferId
     * @param startDateTime
     * @param endDateTime
     * @param numberOfPtus
     * @param startPtu
     * @param activePower
     * @param disposition
     * @return created AgrFlexOfferPtu ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public long create(long agrFlexOfferId, LocalDateTime startDateTime,
        LocalDateTime endDateTime, int numberOfPtus, int startPtu, float price,
        float activePower) {
        
        AgrFlexOfferPtu agrFlexOfferPtu = new AgrFlexOfferPtu();
        agrFlexOfferPtu.setAgrFlexOfferId(agrFlexOfferId);
        LocalDate startDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        agrFlexOfferPtu.setStartDate(startDate.toDateMidnight().toDate());
        LocalDate endDate = new LocalDate(endDateTime.getYear(), endDateTime.getMonthOfYear(), endDateTime.getDayOfMonth());
        agrFlexOfferPtu.setEndDate(endDate.toDateMidnight().toDate());
        agrFlexOfferPtu.setStartDatetime(startDateTime);
        agrFlexOfferPtu.setEndDatetime(endDateTime);
        agrFlexOfferPtu.setNumberPtus(numberOfPtus);
        agrFlexOfferPtu.setStartPtu(startPtu);
        agrFlexOfferPtu.setPrice(price);
        agrFlexOfferPtu.setActivePower(activePower);
        
        persist(agrFlexOfferPtu);
        return agrFlexOfferPtu.getId();
    }
}
