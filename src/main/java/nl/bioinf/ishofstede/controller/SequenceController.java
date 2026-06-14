package nl.bioinf.ishofstede.controller;

import nl.bioinf.ishofstede.model.AnalysisResult;
import nl.bioinf.ishofstede.model.Fragment;
import nl.bioinf.ishofstede.model.RestrictionEnzyme;
import nl.bioinf.ishofstede.service.AnalysisService;
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

    private final AnalysisService analysisService;
    private final HistoryService historyService;

    public SequenceController(AnalysisService analysisService, HistoryService historyService) {
        this.analysisService = analysisService;
        this.historyService = historyService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "enzymes",
                analysisService.getEnzymes()
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

        String cleanedSequence = analysisService.cleanSequence(sequence);
        String errorMessage = analysisService.validateSequence(cleanedSequence);
        boolean valid = errorMessage == null;

        RestrictionEnzyme selectedEnzyme = analysisService.findEnzymeByName(enzyme);

        List<Fragment> fragments = new ArrayList<>();
        List<RestrictionEnzyme> cutterResults = new ArrayList<>();
        int cutCount = 0;

        if (!valid) {
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("valid", false);
            return "results";
        }

        if ("enzyme".equals(mode) && selectedEnzyme != null) {

            cutCount = analysisService.countCutSites(cleanedSequence, selectedEnzyme.getRecognitionSite());

            fragments = analysisService.generateFragments(cleanedSequence, selectedEnzyme.getRecognitionSite());

            AnalysisResult result = new AnalysisResult(cleanedSequence, enzyme, fragments, LocalDateTime.now());

            historyService.addAnalysis(result);
        }

        else if ("single".equals(mode)) {
            cutterResults = analysisService.findSingleOrDoubleCutters(cleanedSequence, 1);
        }
        else if ("double".equals(mode)) {
            cutterResults = analysisService.findSingleOrDoubleCutters(cleanedSequence, 2);
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

        String fasta = analysisService.formatFasta("Fragment_" + id, sequence);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fragment_"
                        + id + ".fasta").contentType(MediaType.TEXT_PLAIN).body(fasta);
    }
}