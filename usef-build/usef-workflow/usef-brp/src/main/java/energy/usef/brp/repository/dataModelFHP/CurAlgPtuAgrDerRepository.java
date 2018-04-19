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

import energy.usef.brp.model.dataModelFHP.CurAlgPtuAgrDer;
import energy.usef.core.model.DocumentStatus;
import energy.usef.core.repository.BaseRepository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * CurAlg Repository for BRP.
 */
@Stateless
public class CurAlgPtuAgrDerRepository extends BaseRepository<CurAlgPtuAgrDer> {

    /**
     * Return the PTU of the Curtailment Algorithm for a determined loop number, AGR and DER
     * and for a determined start and end step dates.
     * 
     * @param agrId
     * @param derId
     * @param curtailmentAlgLoopNumber
     * @param ptuStartDateTime
     * @param ptuEndDateTime
     * @return CurAlgPtuAgrDer entity
     */
    public CurAlgPtuAgrDer get(long agrId, long derId, int curtailmentAlgLoopNumber,
            LocalDateTime ptuStartDateTime, LocalDateTime ptuEndDateTime) {
        StringBuilder queryString = new StringBuilder("SELECT apad FROM CurAlgPtuAgrDer apad, ");
        queryString.append(" CurAlg a, CurAlgPtu ap, CurAlgAgrDer aad, CurAlgAgr aa, AgrFlexOffer fof, AgrFlexOrder for");
        queryString.append(" WHERE apad.derId = :derId");
        queryString.append(" AND apad.agrId = :agrId");
        queryString.append(" AND apad.curAlgPtuId = ap.Id");
        queryString.append(" AND ap.curAlgId = a.id");
        queryString.append(" AND ap.startDatetime <= :ptuStartDateTime");
        queryString.append(" AND ap.endDatetime >= :ptuEndDateTime");
        queryString.append(" AND a.loop = :curtailmentAlgLoopNumber");
        queryString.append(" AND a.startDatetime <= :ptuStartDateTime");
        queryString.append(" AND a.endDatetime >= :ptuEndDateTime");
        queryString.append(" AND apad.curAlgAgrDerId = aad.id");
        queryString.append(" AND aad.curAlgAgrId = aa.id");
        queryString.append(" AND aa.agrFlexOfferId = fof.id");
        queryString.append(" AND for.agrFlexOfferId = fof.id");
        queryString.append(" AND for.status = :acceptedStatus");
       List<CurAlgPtuAgrDer> result = entityManager.createQuery(queryString.toString(), CurAlgPtuAgrDer.class)
                .setParameter("agrId", agrId)
                .setParameter("derId", derId)
                .setParameter("curtailmentAlgLoopNumber", curtailmentAlgLoopNumber)
                .setParameter("ptuStartDateTime", ptuStartDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("ptuEndDateTime", ptuEndDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("acceptedStatus", DocumentStatus.ACCEPTED)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }

}
