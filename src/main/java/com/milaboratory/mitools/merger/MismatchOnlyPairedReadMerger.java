package com.milaboratory.mitools.merger;

import cc.redberry.pipe.Processor;
import com.milaboratory.core.io.sequence.PairedRead;
import com.milaboratory.core.motif.BitapMatcher;
import com.milaboratory.core.motif.BitapPattern;
import com.milaboratory.core.motif.Motif;
import com.milaboratory.core.motif.MotifUtils;
import com.milaboratory.core.sequence.NSequenceWithQuality;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.core.sequence.NucleotideSequenceBuilder;
import com.milaboratory.core.sequence.SequenceQualityBuilder;

import static com.milaboratory.core.sequence.SequencesUtils.mismatchCount;
import static java.lang.Math.*;

public final class MismatchOnlyPairedReadMerger implements Processor<PairedRead, PairedReadMergingResult> {
    public static final int DEFAULT_MAX_SCORE_VALUE = 55;
    final int minOverlap;
    final int maxMismatches;
    final int motifLength;
    final int maxScoreValue;
    // opposite reads direction or collinear
    final boolean isOpposite;

    /**
     * Creates paired-end reads merger for Illumina (or other opposite reads) data.
     *
     * @param minOverlap    minimal number of nucleotide in overlap region
     * @param maxMismatches maximal allowed number of mismatches in overlap region
     */
    public MismatchOnlyPairedReadMerger(int minOverlap, int maxMismatches) {
        this(minOverlap, maxMismatches, DEFAULT_MAX_SCORE_VALUE, true);
    }

    /**
     * Creates paired-end reads merger for Illumina (or other opposite reads) data.
     *
     * @param minOverlap    minimal number of nucleotide in overlap region
     * @param maxMismatches maximal allowed number of mismatches in overlap region
     * @param maxScoreValue maximal output quality score value
     */
    public MismatchOnlyPairedReadMerger(int minOverlap, int maxMismatches, int maxScoreValue) {
        this(minOverlap, maxMismatches, maxScoreValue, true);
    }

    /**
     * Creates paired-end reads merger.
     *
     * @param minOverlap    minimal number of nucleotide in overlap region
     * @param maxMismatches maximal allowed number of mismatches in overlap region
     * @param maxScoreValue maximal output quality score value
     * @param isOpposite    {@code true} if reads are on different strands, like Illumina reads
     */
    public MismatchOnlyPairedReadMerger(int minOverlap, int maxMismatches, int maxScoreValue, boolean isOpposite) {
        this.minOverlap = minOverlap;
        this.maxMismatches = maxMismatches;
        this.isOpposite = isOpposite;
        this.maxScoreValue = maxScoreValue;

        // Calculating length fo motif to be used in Bitap search.
        this.motifLength = min(minOverlap, 62);
    }

    @Override
    public PairedReadMergingResult process(PairedRead pairedRead) {
        NSequenceWithQuality read1 = pairedRead.getR1().getData();
        NSequenceWithQuality read2 = pairedRead.getR2().getData();

        // If there is no sufficient letters in one of read overlapping is impossible
        if (read1.size() < minOverlap || read2.size() < minOverlap)
            // Return failed result
            return new PairedReadMergingResult(pairedRead);


        // Making reverse complement from second read to bring reads to the same strand
        // (if reads configuration is opposite)
        if (isOpposite)
            read2 = read2.getReverseComplement();

        // read2 always smaller then read1
        if (read2.size() > read1.size()) {
            NSequenceWithQuality tmp = read1;
            read1 = read2;
            read2 = tmp;
        }

        // Searching

        // Creating bitap pattern for beginning and ending of read2
        Motif<NucleotideSequence> motif = MotifUtils.twoSequenceMotif(
                read2.getSequence(), 0,
                read2.getSequence(), read2.size() - motifLength,
                motifLength
        );
        BitapPattern bitapPattern = motif.toBitapPattern();
        BitapMatcher bitapMatcher = bitapPattern.substitutionOnlyMatcherFirst(maxMismatches, read1.getSequence());

        int matchPosition, mismatches, overlap;

        while ((matchPosition = bitapMatcher.findNext()) != -1) {

            // Case: beginning of r2 matched

            // Finally checking current hit position
            overlap = min(read1.size() - matchPosition, read2.size());
            if ((mismatches = mismatchCount(
                    read1.getSequence(), matchPosition,
                    read2.getSequence(), 0,
                    overlap)) <= maxMismatches)
                return new PairedReadMergingResult(pairedRead, overlap(read1, read2, matchPosition),
                        overlap, mismatches);

            // Case: ending of r2 matched
            matchPosition += motifLength; // Calculating position of right overlap boundary
            overlap = min(matchPosition, read2.size());
            if ((mismatches = mismatchCount(
                    read1.getSequence(), matchPosition - overlap,
                    read2.getSequence(), max(0, read2.size() - overlap),
                    overlap)) <= maxMismatches)
                return new PairedReadMergingResult(pairedRead, overlap(read1, read2, min(matchPosition - read2.size(), 0)),
                        overlap, mismatches);
        }

        return new PairedReadMergingResult(pairedRead);
    }

    /**
     * @param seq1   sequence 1
     * @param seq2   sequence 2
     * @param offset position of first nucleotide of seq2 in seq1
     * @return overlapped sequence
     */
    private NSequenceWithQuality overlap(NSequenceWithQuality seq1, NSequenceWithQuality seq2, int offset) {
        // Calculating length of resulting sequence
        int length = abs(offset) +
                (offset >= 0 ?
                        max(seq1.size() - offset, seq2.size()) :
                        max(seq1.size(), seq2.size() + offset) // offset is negative here
                );

        NucleotideSequenceBuilder seqBuilder = new NucleotideSequenceBuilder().ensureCapacity(length);
        SequenceQualityBuilder qualBuilder = new SequenceQualityBuilder().ensureCapacity(length);

        byte quality, letter, l, q;
        int from = min(0, offset);
        int position, to = length + from;
        for (int i = from; i < to; ++i) {
            quality = 0;
            letter = -1;

            // Checking read 1
            if (i >= 0 && i < seq1.size()) {
                quality = seq1.getQuality().value(i);
                letter = seq1.getSequence().codeAt(i);
            }

            // Checking read 2
            position = i - offset;
            if (position >= 0 && position < seq2.size()) {
                l = seq2.getSequence().codeAt(position);
                q = seq2.getQuality().value(position);
                if (letter == -1) {
                    letter = l;
                    quality = q;
                } else if (letter == l)
                    quality = (byte) Math.max(maxScoreValue, quality + q);
                else if (q > quality) {
                    letter = l;
                    quality = q;
                }
            }

            assert letter != -1;

            seqBuilder.append(letter);
            qualBuilder.append(quality);
        }

        return new NSequenceWithQuality(seqBuilder.createAndDestroy(), qualBuilder.createAndDestroy());
    }
}
