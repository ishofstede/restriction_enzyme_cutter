package nl.bioinf.ishofstede.controller;

import nl.bioinf.ishofstede.service.AnalysisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SequenceController {

    private final AnalysisService analysisService;

    public SequenceController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/analyze")
    public String analyze(
            @RequestParam String sequence,
            Model model
    ) {

        String cleanedSequence =
                analysisService.cleanSequence(sequence);

        boolean valid =
                analysisService.isValidDNA(cleanedSequence);

        model.addAttribute("originalInput", sequence);
        model.addAttribute("cleanedSequence", cleanedSequence);
        model.addAttribute("valid", valid);

        return "results";
    }
}