package com.milaboratory.mitools.merger;

import com.milaboratory.core.io.sequence.PairedRead;
import com.milaboratory.core.sequence.NSequenceWithQuality;

public class PairedReadMergingResult {
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
}
