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

import energy.usef.brp.model.dataModelFHP.DER;
import energy.usef.core.repository.BaseRepository;

import java.util.List;

import javax.ejb.Stateless;

/**
 * @author TECNALIA
 */
/**
 * CurAlg Repository for BRP.
 */
@Stateless
public class DerRepository extends BaseRepository<DER> {

    /**
     * Return the existing DER-s.
     * 
     * @return List of DER entities
     */
    public List<DER> getDers() {
        StringBuilder queryString = new StringBuilder("SELECT d FROM DER d");
        List<DER> result = entityManager.createQuery(queryString.toString(), DER.class)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result;
    }

}
