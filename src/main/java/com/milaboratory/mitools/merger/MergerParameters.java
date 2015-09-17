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
import com.milaboratory.core.PairedEndReadsLayout;
import com.milaboratory.primitivio.annotations.Serializable;

/**
 * @author Dmitry Bolotin
 * @author Stanislav Poslavsky
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE)
@Serializable(asJson = true)
public final class MergerParameters implements java.io.Serializable {
    final QualityMergingAlgorithm qualityMergingAlgorithm;
    final PairedEndReadsLayout partsLayout;
    final int minimalOverlap;
    final double minimalIdentity;

    @JsonCreator
    public MergerParameters(
            @JsonProperty("qualityMergingAlgorithm") QualityMergingAlgorithm qualityMergingAlgorithm,
            @JsonProperty("partsLayout") PairedEndReadsLayout partsLayout,
            @JsonProperty("minimalOverlap") int minimalOverlap,
            @JsonProperty("minimalIdentity") double minimalIdentity) {
        this.qualityMergingAlgorithm = qualityMergingAlgorithm;
        this.partsLayout = partsLayout;
        this.minimalOverlap = minimalOverlap;
        this.minimalIdentity = minimalIdentity;
    }

    public int getMinimalOverlap() {
        return minimalOverlap;
    }

    public double getMinimalIdentity() {
        return minimalIdentity;
    }

    public QualityMergingAlgorithm getQualityMergingAlgorithm() {
        return qualityMergingAlgorithm;
    }

    public PairedEndReadsLayout getPartsLayout() {
        return partsLayout;
    }

    public MergerParameters overrideReadsLayout(PairedEndReadsLayout partsLayout) {
        return new MergerParameters(qualityMergingAlgorithm, partsLayout, minimalOverlap, minimalIdentity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MergerParameters that = (MergerParameters) o;

        if (minimalOverlap != that.minimalOverlap) return false;
        if (Double.compare(that.minimalIdentity, minimalIdentity) != 0) return false;
        if (qualityMergingAlgorithm != that.qualityMergingAlgorithm) return false;
        return partsLayout == that.partsLayout;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = qualityMergingAlgorithm.hashCode();
        result = 31 * result + (partsLayout != null ? partsLayout.hashCode() : 0);
        result = 31 * result + minimalOverlap;
        temp = Double.doubleToLongBits(minimalIdentity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
