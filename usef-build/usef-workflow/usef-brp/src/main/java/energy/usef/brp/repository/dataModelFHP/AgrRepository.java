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

import energy.usef.brp.model.dataModelFHP.AGR;
import energy.usef.core.repository.BaseRepository;

import java.util.List;

import javax.ejb.Stateless;

/**
 * @author TECNALIA
 */
/**
 * AGR Repository for BRP.
 */
@Stateless
public class AgrRepository extends BaseRepository<AGR> {

    /**
     * Return the existing AGR-s.
     * 
     * @return List of AGR entities
     */
    public List<AGR> getAgrs() {
        StringBuilder queryString = new StringBuilder("SELECT a FROM AGR a");
        List<AGR> result = entityManager.createQuery(queryString.toString(), AGR.class)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result;
    }

    /**
     * Return an AGR
     * 
     * @param domain
     * @return AGR entity
     */
    public AGR getAgr(String domain) {
        StringBuilder queryString = new StringBuilder("SELECT a FROM AGR a");
        queryString.append(" WHERE a.domain = :domain");
        List<AGR> result = entityManager.createQuery(queryString.toString(), AGR.class)
                .setParameter("domain", domain)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);        
    }
}
