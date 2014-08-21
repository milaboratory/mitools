package com.milaboratory.mitools.trimmer;

import cc.redberry.primitives.Filter;
import com.milaboratory.core.io.sequence.SingleRead;

public class ReadLengthFilter implements Filter<SingleRead> {
    final int minLength;

    public ReadLengthFilter(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public boolean accept(SingleRead singleRead) {
        return singleRead.getData().size() >= minLength;
    }
}
