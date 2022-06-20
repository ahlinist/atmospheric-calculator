package atmospheric.calculator.enums

import groovy.transform.CompileStatic

@CompileStatic
enum Layer {

    TROPOSHPERE(11000.0, -0.0065),
    TROPOPAUSE(20000.0, 0.0),
    STRATOSPHERE_LOW(32000.0, 0.001),
    STRATOSPHERE_HIGH(47000.0, 0.0028),
    STRATOPAUSE(51000.0, 0.0),
    MESOSPHERE_LOW(71000.0, -0.0028)

    BigDecimal maxHeight
    BigDecimal lapseRate

    Layer(BigDecimal maxHeight, BigDecimal lapseRate) {
        this.maxHeight = maxHeight
        this.lapseRate = lapseRate
    }
}
