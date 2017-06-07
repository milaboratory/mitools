MiTools
[![Build Status](https://travis-ci.org/milaboratory/mitools.svg)](https://travis-ci.org/milaboratory/mitools)
=======


MiTools: yet another Next Generation Sequencing (NGS) data processing tool (based on MiLib)

## Installation / Download

#### Using Homebrew on Mac OS X or Linux ([linuxbrew](http://linuxbrew.sh/))

    brew tap milaboratory/all
    brew install mitools
    
or just

    brew install milaboratory/all/mitools

to upgrade already installed MiTools to the newest version:

    brew update
    brew upgrade mitools

#### Manual install (any OS)

* download latest MiTools version from [release page](https://github.com/milaboratory/mitools/releases/latest)
* unzip the archive
* add resulting folder to your ``PATH`` variable
  * or add symbolic link for ``mitools`` script to your ``bin`` folder
  * or use MiTools directly by specifying full path to the executable script

#### Requirements

* Any OS with Java support (Linux, Windows, Mac OS X, etc..)
* Java 1.7 or higher
 
## Usage

Cutting input fastq file to specified read length:

    mitools cut -l 50 input.fastq.gz output.fastq.gz

Reverse-complement all reads:

    mitools rc input.fastq.gz output.fastq.gz

Merge-overlap paired-end reads (like PEAR but works better in our experience; fast - based on [bitap](https://en.wikipedia.org/wiki/Bitap_algorithm) algorithm):

    mitools merge input_R1.fastq.gz input_R2.fastq.gz output.fastq.gz

Split input file into chunks of 1M reads:

    mitools split -c 1000000 input.fastq.gz output_{P}.fastq.gz
    
will produce ```output_0.fastq.gz, output_1.fastq.gz, ...``` files.

See ```mitools -h``` for available command paramenters and other useful actions.

## Documentation

See output of

    mitools -h
    
for available actions.

## License

   Copyright 2016 MiLaboratory.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
