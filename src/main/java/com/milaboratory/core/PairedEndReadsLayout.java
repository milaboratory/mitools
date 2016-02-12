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
package com.milaboratory.core;

import com.milaboratory.core.io.sequence.PairedRead;
import com.milaboratory.core.sequence.NSequenceWithQuality;

public enum PairedEndReadsLayout implements java.io.Serializable {
    /**
     * R1     R2
     * ---> <---
     */
    DirectOnly(new PairedTargetProvider(
            +1, -2),
            true),
    /**
     * R2     R1
     * ---> <---
     */
    ReverseOnly(new PairedTargetProvider(
            +2, -1),
            true),
    /**
     * R1     R2
     * ---> <---
     * +
     * R2     R1
     * ---> <---
     */
    Opposite(new PairedTargetProvider(
            +1, -2,
            +2, -1),
            true),
    /**
     * R1     R2
     * ---> --->
     * +
     * R2     R1
     * <--- <---
     */
    Collinear(new PairedTargetProvider(
            +1, +2,
            -2, -1),
            false),
    /**
     * R1     R2
     * ---> <---
     * +
     * R2     R1
     * ---> <---
     * +
     * R1     R2
     * ---> --->
     * +
     * R2     R1
     * <--- <---
     */
    Unknown(new PairedTargetProvider(
            +1, -2,
            +2, -1,
            +1, +2,
            -2, -1),
            false, true);
    private final PairedTargetProvider provider;
    /**
     * Determines possible relative (R1 relative to R2) strands. (true for RC; false for same strand)
     *
     * Used only in paired-end reads merger.
     */
    private final boolean[] possibleRelativeStrands;

    PairedEndReadsLayout(PairedTargetProvider provider, boolean... possibleRelativeStrands) {
        this.provider = provider;
        this.possibleRelativeStrands = possibleRelativeStrands;
    }

    public PairedTarget[] createTargets(PairedRead read) {
        return provider.createTargets(read);
    }

    /**
     * Determines possible relative (R1 relative to R2) strands. (true for RC; false for same strand)
     *
     * Used only in paired-end reads merger.
     */
    public boolean[] getPossibleRelativeStrands() {
        return possibleRelativeStrands;
    }

    private static final class PairedTargetProvider {
        final byte[][] ids;

        PairedTargetProvider(int... ids) {
            assert ids.length % 2 == 0;
            this.ids = new byte[ids.length / 2][];
            for (int i = 0; i < ids.length / 2; i++) {
                this.ids[i] = new byte[]{(byte) ids[i * 2], (byte) ids[i * 2 + 1]};
            }
        }

        PairedTarget[] createTargets(PairedRead read) {
            final PairedTarget[] result = new PairedTarget[ids.length];
            for (int i = 0; i < ids.length; i++) {
                byte[] ii = ids[i];
                result[i] = new PairedTarget(dataFromId(read, ii[0]),
                        dataFromId(read, ii[1]), ii);
            }
            return result;
        }

        NSequenceWithQuality dataFromId(PairedRead read, byte id) {
            switch (id) {
                case +1:
                    return read.getR1().getData();
                case +2:
                    return read.getR2().getData();
                case -1:
                    return read.getR1().getData().getReverseComplement();
                case -2:
                    return read.getR2().getData().getReverseComplement();
            }
            throw new IllegalArgumentException();
        }
    }

    //private interface SingleTargetProvider {
    //    SingleTarget[] createTargets(SingleRead read);
    //}
}
