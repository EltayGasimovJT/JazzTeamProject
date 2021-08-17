package validator;

import entity.CoefficientForPriceCalculation;

import java.util.List;

public class CoefficientForPriseCalculationValidator {
    private CoefficientForPriseCalculationValidator() {
    }

    public static void validateCoefficient(CoefficientForPriceCalculation coefficientForValidate) throws IllegalArgumentException {
        if (coefficientForValidate == null) {
            throw new IllegalArgumentException("There is no coefficient with such Id or its country is not supported yet!!!");
        }
        if (coefficientForValidate.getCountry() == null) {
            throw new IllegalArgumentException("Coefficient must have country!!!");
        }
        if (coefficientForValidate.getCountryCoefficient() == null) {
            throw new IllegalArgumentException("Coefficient cannot be null!!!");
        }
        if (coefficientForValidate.getParcelSizeLimit() == null){
            throw new IllegalArgumentException("Parcel size limit cannot be null!!!");
        }
    }

    public static void validateOnSave(CoefficientForPriceCalculation coefficientFroValidate) throws IllegalArgumentException {
        if (coefficientFroValidate == null) {
            throw new IllegalArgumentException("Coefficient cannot be null!!!");
        }
    }

    public static void validateCoefficientList(List<CoefficientForPriceCalculation> coefficientsForValidate){
        if(coefficientsForValidate.isEmpty()){
            throw new IllegalArgumentException("There is no coefficients on the repository!!!");
        }
    }
}
