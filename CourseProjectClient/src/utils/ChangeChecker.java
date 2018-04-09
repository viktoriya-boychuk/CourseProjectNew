package utils;


import javafx.beans.value.ChangeListener;

public class ChangeChecker {
    static Boolean valueChanged = false;

    static public Boolean hasChanged() {
        return valueChanged;
    }

    static public void hasChanged(Boolean incomingValueChanged) {
        valueChanged = incomingValueChanged;
    }

    public static ChangeListener valueListener = (observable, oldValue, newValue) -> {
        if (newValue != oldValue)
            valueChanged = true;
    };
    public static ChangeListener textListener = (observable, oldValue, newValue) -> {
        if (!newValue.equals(oldValue))
            valueChanged = true;
    };
    public static ChangeListener toggleListener = (ov, old_toggle, new_toggle) -> {
        if (old_toggle != new_toggle) {
            valueChanged = true;
        }
    };
}
