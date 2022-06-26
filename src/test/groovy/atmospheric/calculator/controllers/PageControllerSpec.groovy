package atmospheric.calculator.controllers

import spock.lang.Specification
import spock.lang.Subject

class PageControllerSpec extends Specification {

    @Subject
    PageController pageController = new PageController()

    def "should return main page name"() {
        expect:
        pageController.mainPage() == 'index.html'
    }
}
