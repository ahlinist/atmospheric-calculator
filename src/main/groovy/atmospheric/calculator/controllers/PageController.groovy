package atmospheric.calculator.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PageController {

    @GetMapping('/')
    String mainPage() {
        'index.html'
    }
}
