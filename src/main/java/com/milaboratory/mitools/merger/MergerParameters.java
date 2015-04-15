/*
 * Copyright 2015 MiLaboratory.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.milaboratory.mitools.merger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.milaboratory.primitivio.annotations.Serializable;

/**
 * @author Dmitry Bolotin
 * @author Stanislav Poslavsky
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE)
@Serializable(asJson = true)
public final class MergerParameters implements java.io.Serializable{
    final int minimalOverlap;
    final double minimalIdentity;

    @JsonCreator
    public MergerParameters(
            @JsonProperty("minimalOverlap") int minimalOverlap,
            @JsonProperty("minimalIdentity") double minimalIdentity) {
        this.minimalOverlap = minimalOverlap;
        this.minimalIdentity = minimalIdentity;
    }

    public int getMinimalOverlap() {
        return minimalOverlap;
    }

    public double getMinimalIdentity() {
        return minimalIdentity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MergerParameters that = (MergerParameters) o;

        if (Double.compare(that.minimalIdentity, minimalIdentity) != 0) return false;
        if (minimalOverlap != that.minimalOverlap) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = minimalOverlap;
        temp = Double.doubleToLongBits(minimalIdentity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
