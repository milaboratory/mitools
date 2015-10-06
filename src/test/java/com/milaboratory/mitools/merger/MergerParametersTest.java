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

import com.milaboratory.core.PairedEndReadsLayout;
import com.milaboratory.test.TestUtil;
import com.milaboratory.util.GlobalObjectMappers;
import org.junit.Assert;
import org.junit.Test;

public class MergerParametersTest {
    @Test
    public void test1() throws Exception {
        MergerParameters parameters = new MergerParameters(QualityMergingAlgorithm.SumMax, null, 15, 50, 0.8);
        TestUtil.assertJson(parameters);
    }

    @Test
    public void test2() throws Exception {
        MergerParameters parameters = new MergerParameters(QualityMergingAlgorithm.SumSubtraction,
                PairedEndReadsLayout.Collinear, 15, 50, 0.8);
        TestUtil.assertJson(parameters, true);
    }

    @Test
    public void test3() throws Exception {
        MergerParameters parameters = new MergerParameters(QualityMergingAlgorithm.SumSubtraction,
                PairedEndReadsLayout.Collinear, 15, 50, 0.8);
        String value = "{\n" +
                "  \"qualityMergingAlgorithm\" : \"SumSubtraction\",\n" +
                "  \"partsLayout\" : \"Collinear\",\n" +
                "  \"minimalOverlap\" : 15,\n" +
                "  \"minimalIdentity\" : 0.8\n" +
                "}";
        MergerParameters deserialized = GlobalObjectMappers.PRETTY.readValue(value, MergerParameters.class);
        Assert.assertEquals(parameters, deserialized);
    }

}