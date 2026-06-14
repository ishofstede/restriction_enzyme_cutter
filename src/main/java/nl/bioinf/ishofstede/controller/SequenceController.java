package nl.bioinf.ishofstede.controller;

import nl.bioinf.ishofstede.model.AnalysisResult;
import nl.bioinf.ishofstede.model.Fragment;
import nl.bioinf.ishofstede.model.RestrictionEnzyme;
import nl.bioinf.ishofstede.service.EnzymeService;
import nl.bioinf.ishofstede.service.FastaService;
import nl.bioinf.ishofstede.service.SequenceService;
import nl.bioinf.ishofstede.service.FragmentAnalysisService;
import nl.bioinf.ishofstede.service.HistoryService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
public class SequenceController {

    private final SequenceService sequenceService;
    private final EnzymeService enzymeService;
    private final FragmentAnalysisService fragmentAnalysisService;
    private final FastaService fastaService;
    private final HistoryService historyService;;

    public SequenceController(
            SequenceService sequenceService,
            EnzymeService enzymeService,
            FragmentAnalysisService fragmentAnalysisService,
            FastaService fastaService,
            HistoryService historyService)
    {
        this.sequenceService = sequenceService;
        this.enzymeService = enzymeService;
        this.fragmentAnalysisService = fragmentAnalysisService;
        this.fastaService = fastaService;
        this.historyService = historyService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "enzymes",
                enzymeService.getEnzymes()
        );

        return "index";
    }

    @PostMapping("/analyze")
    public String analyze(
            @RequestParam String sequence,
            @RequestParam String enzyme,
            @RequestParam String mode,
            Model model
    ) {

        String cleanedSequence = sequenceService.cleanSequence(sequence);
        String errorMessage = sequenceService.validateSequence(cleanedSequence);
        boolean valid = errorMessage == null;

        RestrictionEnzyme selectedEnzyme = enzymeService.findEnzymeByName(enzyme);

        List<Fragment> fragments = new ArrayList<>();
        List<RestrictionEnzyme> cutterResults = new ArrayList<>();
        int cutCount = 0;

        if (!valid) {
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("valid", false);
            return "results";
        }

        if ("enzyme".equals(mode) && selectedEnzyme != null) {

            cutCount = fragmentAnalysisService.countCutSites(cleanedSequence, selectedEnzyme.getRecognitionSite());

            fragments = fragmentAnalysisService.generateFragments(cleanedSequence, selectedEnzyme.getRecognitionSite());

            AnalysisResult result = new AnalysisResult(cleanedSequence, enzyme, fragments, LocalDateTime.now());

            historyService.addAnalysis(result);
        }

        else if ("single".equals(mode)) {
            cutterResults = fragmentAnalysisService.findSingleOrDoubleCutters(cleanedSequence, 1);
        }
        else if ("double".equals(mode)) {
            cutterResults = fragmentAnalysisService.findSingleOrDoubleCutters(cleanedSequence, 2);
        }

        model.addAttribute("originalInput", sequence);
        model.addAttribute("cleanedSequence", cleanedSequence);
        model.addAttribute("valid", true);
        model.addAttribute("selectedEnzyme", selectedEnzyme);
        model.addAttribute("cutCount", cutCount);
        model.addAttribute("fragments", fragments);
        model.addAttribute("mode", mode);
        model.addAttribute("cutterResults", cutterResults);
        model.addAttribute("history", historyService.getHistory());

        return "results";
    }

    @GetMapping("/fragment/{id}/fasta")
    public ResponseEntity<String> downloadFasta(
            @PathVariable int id,
            @RequestParam String sequence
    ) {

        String fasta = fastaService.formatFasta("Fragment_" + id, sequence);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fragment_"
                        + id + ".fasta").contentType(MediaType.TEXT_PLAIN).body(fasta);
    }
}