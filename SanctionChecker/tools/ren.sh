#!/bin/bash

#rename sep test files back
for file in *.end; do mv $file ${file%%.*}.xml  ; done