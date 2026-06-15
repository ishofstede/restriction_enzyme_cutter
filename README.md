# DNA Restriction Enzyme Analyzer

## Overview

This is a Spring Boot web application that performs restriction enzyme analysis on DNA sequences. The user can input DNA in raw or FASTA format and simulate restriction enzyme cutting.

The application calculates fragment properties and allows downloading FASTA output.

## Features

- Accepts raw DNA and FASTA input
- Validates DNA sequence (ACGT)
- Supports at least 15 restriction enzymes
- Performs restriction enzyme cutting simulation
- Generates DNA fragments
- Calculates GC content
- Calculates molecular weight
- Finds single and double cutters
- Outputs FASTA format with 80 character line length
- Stores last 5 analyses in memory

## Technologies

- Java
- Spring Boot
- Thymeleaf
- Maven

No database is used. History is stored in memory.

## Project Structure

```bash
src/main/java/nl.bioinf.ishofstede
├── controller/
│   └── SequenceController
│
├── service/
│   ├── SequenceService
│   ├── EnzymeService
│   ├── FragmentAnalysisService
│   ├── FastaService
│   └── HistoryService
│
└── model/
    ├── RestrictionEnzyme
    ├── Fragment
    └── AnalysisResult
```

## How to run

1. Clone repository
2. Run mvn clean install
3. Run mvn spring-boot:run
4. Open http://localhost:8080

## Usage

1. Enter DNA sequence (raw or FASTA)
2. Select enzyme or single/double cutter mode
3. Submit
4. View results
5. Download FASTA if needed

## Assignment Requirements

- DNA input validation
- 15 restriction enzymes
- Fragment generation
- Single/double cutter detection
- FASTA output with 80 char line limit
- History of last 5 analyses

## Author

Name: Isabella Hofstede
Year: 2026
