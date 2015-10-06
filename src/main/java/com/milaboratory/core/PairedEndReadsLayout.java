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

public enum PairedEndReadsLayout implements java.io.Serializable {
    Opposite(new PairedTargetProvider() {
                @Override
                public PairedTarget[] createTargets(PairedRead read) {
                    return new PairedTarget[]{
                            new PairedTarget(
                                    read.getRead(0).getData(),
                                    read.getRead(1).getData().getReverseComplement()),
                            new PairedTarget(
                                    read.getRead(1).getData(),
                                    read.getRead(0).getData().getReverseComplement())};
                }
            }, true
    ),
    Collinear(new PairedTargetProvider() {
                @Override
                public PairedTarget[] createTargets(PairedRead read) {
                    return new PairedTarget[]{
                            new PairedTarget(
                                    read.getRead(0).getData(),
                                    read.getRead(1).getData()),
                            new PairedTarget(
                                    read.getRead(1).getData().getReverseComplement(),
                                    read.getRead(0).getData().getReverseComplement())};
                }
            }, false
    ),
    Unknown(new PairedTargetProvider() {
                @Override
                public PairedTarget[] createTargets(PairedRead read) {
                    return new PairedTarget[]{
                            new PairedTarget(
                                    read.getRead(0).getData(),
                                    read.getRead(1).getData()),
                            new PairedTarget(
                                    read.getRead(0).getData(),
                                    read.getRead(1).getData().getReverseComplement()),
                            new PairedTarget(
                                    read.getRead(1).getData(),
                                    read.getRead(0).getData().getReverseComplement()),
                            new PairedTarget(
                                    read.getRead(1).getData().getReverseComplement(),
                                    read.getRead(0).getData().getReverseComplement())};
                }
            }, false, true
    );
    private final PairedTargetProvider provider;
    private final boolean[] possibleRelativeStrands;

    PairedEndReadsLayout(PairedTargetProvider provider, boolean... possibleRelativeStrands) {
        this.provider = provider;
        this.possibleRelativeStrands = possibleRelativeStrands;
    }

    public PairedTarget[] createTargets(PairedRead read) {
        return provider.createTargets(read);
    }

    public boolean[] getPossibleRelativeStrands() {
        return possibleRelativeStrands;
    }

    private interface PairedTargetProvider {
        PairedTarget[] createTargets(PairedRead read);
    }
}
