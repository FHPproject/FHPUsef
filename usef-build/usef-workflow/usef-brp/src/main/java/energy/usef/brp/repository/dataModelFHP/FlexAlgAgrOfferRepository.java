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


import energy.usef.brp.model.dataModelFHP.FlexAlgAgrOffer;
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
 * FlexAlgAgrDer Repository for BRP.
 */
@Stateless
public class FlexAlgAgrOfferRepository extends BaseRepository<FlexAlgAgrOffer> {

    
    /**
     * Create FlexAlgAgrDer.
     * 
     * @param flexAlgAgrId
     * @param agrId
     * @param agrFlexOfferId
     * @param selected
     * @return created FlexAlgAgrDer ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create(long flexAlgAgrId,long agrId, long agrFlexOfferId, boolean selected) {
        FlexAlgAgrOffer flexAlgAgrOffer = new FlexAlgAgrOffer();
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        flexAlgAgrOffer.setFlexAlgAgrId(flexAlgAgrId);
        flexAlgAgrOffer.setAgrId(agrId);
        flexAlgAgrOffer.setAgrFlexOfferId(agrFlexOfferId);
        flexAlgAgrOffer.setSelected(selected);
       
        persist(flexAlgAgrOffer);
        return flexAlgAgrOffer.getId();
    }
    
    /**
     * Return the PTU of the Curtailment Algorithm for a determined loop number, AGR and DER
     * and for a determined start and end step dates.
     * 
     * @param agrFlexOfferId
     * @param agrId

     * @return FlexAlgPtuAgrDer entity
     */
    public FlexAlgAgrOffer get(long agrFlexOfferId, long agrId) {
        StringBuilder queryString = new StringBuilder("SELECT apad FROM FlexAlgAgrOffer apad ");
        queryString.append(" WHERE apad.agrId = :agrId");
        queryString.append(" AND apad.agrFlexOfferId = :agrFlexOfferId");
       List<FlexAlgAgrOffer> result = entityManager.createQuery(queryString.toString(), FlexAlgAgrOffer.class)
                .setParameter("agrId", agrId)
                .setParameter("agrFlexOfferId", agrFlexOfferId)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }    
    

}
