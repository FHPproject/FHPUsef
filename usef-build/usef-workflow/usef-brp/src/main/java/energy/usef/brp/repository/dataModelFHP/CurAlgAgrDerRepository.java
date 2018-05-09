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

import energy.usef.brp.model.dataModelFHP.CurAlgAgrDer;
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
 * CurAlgAgrDer Repository for BRP.
 */
@Stateless
public class CurAlgAgrDerRepository extends BaseRepository<CurAlgAgrDer> {

    
    /**
     * Create CurAlgAgrDer.
     * 
     * @param curAlgAgrId
     * @param curAlgDerId
     * @param agrId
     * @param curAlgId
     * @param agrDerPayment
     * @param derId
     * @return created CurAlgAgrDer ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create(long curAlgAgrId,long curAlgDerId,long agrId, long derId, float agrDerPayment) {
        CurAlgAgrDer curAlgAgrDer = new CurAlgAgrDer();
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        curAlgAgrDer.setCurAlgAgrId(curAlgAgrId);
        curAlgAgrDer.setCurAlgDerId(curAlgDerId);
        curAlgAgrDer.setAgrId(agrId);
        curAlgAgrDer.setDerId(derId);
        curAlgAgrDer.setAgrDerPayment(0);
       
        persist(curAlgAgrDer);
        return curAlgAgrDer.getId();
    }
    
    /**
     * Return the PTU of the Curtailment Algorithm for a determined loop number, AGR and DER
     * and for a determined start and end step dates.
     * 
     * @param curAlgAgrId
     * @param curAlgDerId
     * @return CurAlgPtuAgrAgrDer entity
     */
    public CurAlgAgrDer get(long curAlgAgrId, long curAlgDerId) {
        StringBuilder queryString = new StringBuilder("SELECT apad FROM CurAlgAgrDer apad ");
        queryString.append(" WHERE apad.curAlgDerId = :curAlgDerId");
        queryString.append(" AND apad.curAlgAgrId = :curAlgAgrId");
       List<CurAlgAgrDer> result = entityManager.createQuery(queryString.toString(), CurAlgAgrDer.class)
                .setParameter("curAlgDerId", curAlgDerId)
                .setParameter("curAlgAgrId", curAlgAgrId)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }    
    

}
