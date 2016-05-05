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

import com.milaboratory.core.io.sequence.PairedRead;
import com.milaboratory.core.sequence.NSequenceWithQuality;

public class PairedReadMergingResult implements java.io.Serializable {
    private static final int MATCH_SCORE = 2;
    private static final int MISMATCH_SCORE = -5;

    final PairedRead originalRead;
    final NSequenceWithQuality overlappedSequence;
    final int overlap;
    final int errors;

    /**
     * Constructor for failed merging result.
     *
     * @param originalRead original read
     */
    public PairedReadMergingResult(PairedRead originalRead) {
        this.originalRead = originalRead;
        this.overlappedSequence = null;
        this.overlap = 0;
        this.errors = -1;
    }

    /**
     * Constructor for successful merging result.
     *
     * @param originalRead       original read
     * @param overlappedSequence reconstructed (overlapped) sequence from paired-end reads
     * @param overlap            number of overlapped nucleotides
     * @param errors             number of mismatches/insertions/deletions found in overlapping region
     */
    public PairedReadMergingResult(PairedRead originalRead, NSequenceWithQuality overlappedSequence,
                                   int overlap, int errors) {
        this.originalRead = originalRead;
        this.overlappedSequence = overlappedSequence;
        this.overlap = overlap;
        this.errors = errors;
    }

    public boolean isSuccessful() {
        return overlappedSequence != null;
    }

    public PairedRead getOriginalRead() {
        return originalRead;
    }

    public NSequenceWithQuality getOverlappedSequence() {
        return overlappedSequence;
    }

    public int getOverlap() {
        return overlap;
    }

    public int getErrors() {
        return errors;
    }

    public int score() {
        return (overlap - errors) * MATCH_SCORE + errors * MISMATCH_SCORE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PairedReadMergingResult that = (PairedReadMergingResult) o;

        if (overlap != that.overlap) return false;
        if (errors != that.errors) return false;
        if (!originalRead.equals(that.originalRead)) return false;
        return overlappedSequence.equals(that.overlappedSequence);

    }

    @Override
    public int hashCode() {
        int result = originalRead.hashCode();
        result = 31 * result + overlappedSequence.hashCode();
        result = 31 * result + overlap;
        result = 31 * result + errors;
        return result;
    }

    @Override
    public String toString() {
        return "PairedReadMergingResult{" +
                "originalRead=" + originalRead +
                ", overlappedSequence=" + overlappedSequence +
                ", overlap=" + overlap +
                ", errors=" + errors +
                '}';
    }
}
