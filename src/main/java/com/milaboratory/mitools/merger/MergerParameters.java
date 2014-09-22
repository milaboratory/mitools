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
    final int overlappingCount;
    final double minimalIdentity;
    final int maxScoreValue;
    final Boolean isOpposite;

    public MergerParameters(
            int overlappingCount,
            double minimalIdentity,
            Boolean isOpposite) {
        this(overlappingCount, minimalIdentity, MismatchOnlyPairedReadMerger.DEFAULT_MAX_SCORE_VALUE, isOpposite);
    }

    @JsonCreator
    public MergerParameters(
            @JsonProperty("overlappingCount") int overlappingCount,
            @JsonProperty("minimalIdentity") double minimalIdentity,
            @JsonProperty("maxScoreValue") int maxScoreValue,
            @JsonProperty("isOpposite") Boolean isOpposite) {
        this.overlappingCount = overlappingCount;
        this.minimalIdentity = minimalIdentity;
        this.maxScoreValue = maxScoreValue;
        this.isOpposite = isOpposite;
    }

    public int getOverlappingCount() {
        return overlappingCount;
    }

    public double getMinimalIdentity() {
        return minimalIdentity;
    }

    public int getMaxScoreValue() {
        return maxScoreValue;
    }

    public Boolean getIsOpposite() {
        return isOpposite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MergerParameters that = (MergerParameters) o;

        if (maxScoreValue != that.maxScoreValue) return false;
        if (Double.compare(that.minimalIdentity, minimalIdentity) != 0) return false;
        if (overlappingCount != that.overlappingCount) return false;
        if (isOpposite != null ? !isOpposite.equals(that.isOpposite) : that.isOpposite != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = overlappingCount;
        temp = Double.doubleToLongBits(minimalIdentity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + maxScoreValue;
        result = 31 * result + (isOpposite != null ? isOpposite.hashCode() : 0);
        return result;
    }
}
