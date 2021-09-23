package org.jazzteam.eltay.gasimov.validator;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;

import java.util.List;

public class CoefficientForPriseCalculationValidator {
    private CoefficientForPriseCalculationValidator() {
    }

    public static void validateCoefficient(CoefficientForPriceCalculation coefficientForValidate) throws IllegalArgumentException, ObjectNotFoundException {
        if (coefficientForValidate == null) {
            throw new ObjectNotFoundException("Данного коэффициента не сущесвует или же страна назначения не поддерживается");
        }
        if (coefficientForValidate.getCountry() == null) {
            throw new IllegalArgumentException("Значение страны коэффициента должно быть заполнено");
        }
        if (coefficientForValidate.getCountryCoefficient() == null) {
            throw new IllegalArgumentException("Значение коэффициента должно быть заполнено");
        }
        if (coefficientForValidate.getParcelSizeLimit() == null){
            throw new IllegalArgumentException("Значение предельного размера посылки должно быть заполнено");
        }
    }

    public static void validateOnSave(CoefficientForPriceCalculation coefficientFroValidate) throws IllegalArgumentException, ObjectNotFoundException {
        if (coefficientFroValidate == null) {
            throw new IllegalArgumentException("В введенные данные коэффициента не верны");
        }
        validateCoefficient(coefficientFroValidate);
    }

    public static void validateCoefficientList(List<CoefficientForPriceCalculation> coefficientsForValidate) throws IllegalArgumentException, ObjectNotFoundException {
        if(coefficientsForValidate.isEmpty()){
            throw new ObjectNotFoundException("В базе данных нет коэффициентов");
        }
    }

    public static void validateOnFindById(CoefficientForPriceCalculation foundCoefficient, Long id) throws ObjectNotFoundException {
        if (foundCoefficient == null) {
            throw new ObjectNotFoundException("Не существует коэффициента с таким id: " + id);
        }
    }

    public static void validateOnFindByCountry(CoefficientForPriceCalculation foundCoefficient, String countryForSearch) throws ObjectNotFoundException {
        if (foundCoefficient == null) {
            throw new ObjectNotFoundException("Не существует коэффициента с такой страной назначения " + countryForSearch);
        }
    }
}
