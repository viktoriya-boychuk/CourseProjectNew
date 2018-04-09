package utils;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;

public class FieldsValidation {
    public static Boolean textFieldIsNotEmpty(JFXTextField field){
        RequiredFieldValidator validator = new RequiredFieldValidator();
        field.getValidators().add(validator);
        validator.setMessage("Обов'язкове поле!");
        return field.validate();
    }
}