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

package energy.usef.agr.workflow.operate.control.ads;

/**
 * Enumeration of the workflow parameters for the workflow 'Realize A-Plans and/or D-Prognoses by controlling Active Demand and
 * Supply'.
 */
public final class ControlActiveDemandSupplyStepParameter {

    /**
     * Input parameters.
     */
    public enum IN {
        PTU_DURATION,
        DEVICE_MESSAGE_DTO;
    }

    /**
     * Output parameters.
     */
    public enum OUT {
        FAILED_DEVICE_MESSAGE_DTO
    }

}
