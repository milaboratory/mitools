package com.milaboratory.mitools.trimmer;

import cc.redberry.pipe.Processor;
import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.SingleReadImpl;
import com.milaboratory.core.sequence.SequenceQuality;

import java.util.concurrent.atomic.AtomicLong;

public final class SequenceTrimmer implements Processor<SingleRead, SingleRead> {
    final int qualityToTrimm;
    final boolean trimmLeft, trimmRight;
    final AtomicLong trimmedNucleotidesLeft = new AtomicLong(),
            trimmedReadsLeft = new AtomicLong(),
            trimmedNucleotidesRight = new AtomicLong(),
            trimmedReadsRight = new AtomicLong(),
            totalTrimmedReads = new AtomicLong();

    public SequenceTrimmer(int qualityToTrimm, boolean trimmLeft, boolean trimmRight) {
        this.qualityToTrimm = qualityToTrimm;
        this.trimmLeft = trimmLeft;
        this.trimmRight = trimmRight;
    }

    public long getTrimmedNucleotidesLeft() {
        return trimmedNucleotidesLeft.get();
    }

    public long getTrimmedReadsLeft() {
        return trimmedReadsLeft.get();
    }

    public long getTrimmedNucleotidesRight() {
        return trimmedNucleotidesRight.get();
    }

    public long getTrimmedReadsRight() {
        return trimmedReadsRight.get();
    }

    public long getTotalTrimmedReads() {
        return totalTrimmedReads.get();
    }

    @Override
    public SingleRead process(SingleRead input) {
        int begin, end; //, scoreSum, threshold;
        SequenceQuality quality = input.getData().getQuality();

        begin = 0;
        if (trimmLeft) {
            //scoreSum = 0;
            //threshold = 0;
            for (int i = 0; i < quality.size(); ++i) {
                //scoreSum += quality.value(i);
                //threshold += qualityToTrimm;
                if (quality.value(i) > qualityToTrimm) {
                    begin = i;
                    break;
                }
            }
        }

        end = quality.size();
        if (trimmRight) {
            //scoreSum = 0;
            //threshold = 0;
            for (int i = quality.size() - 1; i >= 0; --i) {
                //scoreSum += quality.value(i);
                //threshold += qualityToTrimm;
                if (quality.value(i) > qualityToTrimm) {
                    end = i + 1;
                    break;
                }
            }
        }

        if (end < begin) {
            end = (end + begin) / 2;
            begin = end;
        }

        if (begin != 0) {
            trimmedReadsLeft.incrementAndGet();
            trimmedNucleotidesLeft.addAndGet(begin);
        }

        int trimmedRight = quality.size() - end;
        if (trimmedRight != 0) {
            trimmedReadsRight.incrementAndGet();
            trimmedNucleotidesRight.addAndGet(trimmedRight);
        }

        if (begin == 0 && trimmedRight == 0)
            return input;
        else {
            totalTrimmedReads.incrementAndGet();
            return new SingleReadImpl(input.getId(), input.getData().getRange(begin, end), input.getDescription());
        }
    }
}
