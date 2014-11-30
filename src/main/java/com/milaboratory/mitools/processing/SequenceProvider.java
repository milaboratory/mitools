package com.milaboratory.mitools.processing;

import com.milaboratory.core.io.sequence.SequenceRead;
import com.milaboratory.core.sequence.NucleotideSequence;

public interface SequenceProvider {
    NucleotideSequence get(SequenceRead read);
}
