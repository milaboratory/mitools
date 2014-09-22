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
public final class MergerParameters {
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
