package org.jazzteam.eltay.gasimov.validator;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;

import java.util.List;

public class CoefficientForPriseCalculationValidator {
    private CoefficientForPriseCalculationValidator() {
    }

    public static void validateCoefficient(CoefficientForPriceCalculation coefficientForValidate) throws IllegalArgumentException, ObjectNotFoundException {
        if (coefficientForValidate == null) {
            throw new ObjectNotFoundException("There is no coefficient with such Id or its country is not supported yet");
        }
        if (coefficientForValidate.getCountry() == null) {
            throw new IllegalArgumentException("Coefficient must have country");
        }
        if (coefficientForValidate.getCountryCoefficient() == null) {
            throw new IllegalArgumentException("Coefficient cannot be null");
        }
        if (coefficientForValidate.getParcelSizeLimit() == null){
            throw new IllegalArgumentException("Parcel size limit cannot be null");
        }
    }

    public static void validateOnSave(CoefficientForPriceCalculation coefficientFroValidate) throws IllegalArgumentException, ObjectNotFoundException {
        if (coefficientFroValidate == null) {
            throw new IllegalArgumentException("Coefficient cannot be null");
        }
        validateCoefficient(coefficientFroValidate);
    }

    public static void validateCoefficientList(List<CoefficientForPriceCalculation> coefficientsForValidate) throws IllegalArgumentException, ObjectNotFoundException {
        if(coefficientsForValidate.isEmpty()){
            throw new ObjectNotFoundException("There is no coefficients on the repository");
        }
    }

    public static void validateOnFindById(CoefficientForPriceCalculation foundCoefficient, Long id) throws ObjectNotFoundException {
        if (foundCoefficient == null) {
            throw new ObjectNotFoundException("There is no client with this id " + id);
        }
    }

    public static void validateOnFindByCountry(CoefficientForPriceCalculation foundCoefficient, String countryForSearch) throws ObjectNotFoundException {
        if (foundCoefficient == null) {
            throw new ObjectNotFoundException("There is no client with this country " + countryForSearch);
        }
    }
}
