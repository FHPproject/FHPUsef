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

import energy.usef.brp.model.dataModelFHP.CurAlgAgrOffer;
import energy.usef.core.repository.BaseRepository;
import energy.usef.core.util.DateTimeUtil;

import java.util.List;

import javax.ejb.Stateless;
import javax.transaction.Transactional;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * CurAlgAgrDer Repository for BRP.
 */
@Stateless
public class CurAlgAgrOfferRepository extends BaseRepository<CurAlgAgrOffer> {

    
    /**
     * Create CurAlgAgrDer.
     * 
     * @param curAlgAgrId
     * @param agrId
     * @param agrFlexOfferId
     * @param selected
     * @return created CurAlgAgrDer ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create(long curAlgAgrId,long agrId, long agrFlexOfferId, boolean selected) {
        CurAlgAgrOffer curAlgAgrOffer = new CurAlgAgrOffer();
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        curAlgAgrOffer.setCurAlgAgrId(curAlgAgrId);
        curAlgAgrOffer.setAgrId(agrId);
        curAlgAgrOffer.setAgrFlexOfferId(agrFlexOfferId);
        curAlgAgrOffer.setSelected(selected);
       
        persist(curAlgAgrOffer);
        return curAlgAgrOffer.getId();
    }
    
    /**
     * Return the PTU of the Curtailment Algorithm for a determined loop number, AGR and DER
     * and for a determined start and end step dates.
     * 
     * @param agrFlexOfferId
     * @param agrId

     * @return CurAlgPtuAgrDer entity
     */
    public CurAlgAgrOffer get(long agrFlexOfferId, long agrId) {
        StringBuilder queryString = new StringBuilder("SELECT apad FROM CurAlgAgrOffer apad ");
        queryString.append(" WHERE apad.agrId = :agrId");
        queryString.append(" AND apad.agrFlexOfferId = :agrFlexOfferId");
       List<CurAlgAgrOffer> result = entityManager.createQuery(queryString.toString(), CurAlgAgrOffer.class)
                .setParameter("agrId", agrId)
                .setParameter("agrFlexOfferId", agrFlexOfferId)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }    
    

}
