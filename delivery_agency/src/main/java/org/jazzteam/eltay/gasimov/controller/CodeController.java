package org.jazzteam.eltay.gasimov.controller;

import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.jazzteam.eltay.gasimov.controller.constants.ControllerConstant.FIND_CODE_URL;

@RestController
@Log
public class CodeController {
    @Autowired
    private CodeService codeService;

    @GetMapping(path = FIND_CODE_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    boolean findPersonalCode(@RequestParam String code) {
        boolean isFound = codeService.findByCode(code) != null;
        codeService.delete(code);
        return isFound;
    }
}
