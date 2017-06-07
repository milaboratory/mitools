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
package com.milaboratory.mitools.cli;

import com.milaboratory.cli.JCommanderBasedMain;
import com.milaboratory.util.VersionInfo;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        Signal.handle(new Signal("PIPE"), new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                System.exit(0);
            }
        });

        JCommanderBasedMain main = new JCommanderBasedMain("mitools",
                new MergeAction(),
                new TrimAction(),
                new RandomizeAction(),
                new RCAction(),
                new RenameAction(),
                new SplitAction(),
                new CheckAction(),
                new CutAction());

        main.setVersionInfoCallback(new Runnable() {
            @Override
            public void run() {
                VersionInfo milib = VersionInfo.getVersionInfoForArtifact("milib");
                VersionInfo mitools = VersionInfo.getVersionInfoForArtifact("mitools");

                StringBuilder builder = new StringBuilder();

                builder.append("MiTools v")
                        .append(mitools.getVersion())
                        .append(" (built ")
                        .append(mitools.getTimestamp())
                        .append("; rev=")
                        .append(mitools.getRevision())
                        .append("; branch=")
                        .append(mitools.getBranch())
                        .append("; host=")
                        .append(mitools.getHost())
                        .append(")")
                        .append("\n");

                builder.append("MiLib v")
                        .append(milib.getVersion())
                        .append(" (rev=").append(milib.getRevision())
                        .append("; branch=").append(milib.getBranch())
                        .append(")");

                System.out.println(builder.toString());
            }
        });

        main.main(args);
    }
}
