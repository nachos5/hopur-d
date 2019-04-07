package hopurd.main.utilities;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.Callable;

public final class Language {

    private static final ObjectProperty<Locale> locale; // Current Locale
    private static final ArrayList<Locale> localesList; // List of Locales from RB 'languages'

    static {
        localesList = new ArrayList<>();
        localesList.add(new Locale("en", "US"));
        localesList.add(new Locale("is", "IS"));

        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    /**
     * Get a list of Locales in RB 'languages'
     *
     * @return List of Locale objects.
     */
    public static List<Locale> getSupportedLocales() {
        return localesList;
    }

    /**
     * Get default locale or english
     *
     * @return Locale
     */
    public static Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.ENGLISH;
    }

    /**
     * Get current Locale
     *
     * @return Locale
     */
    public static Locale getLocale() {
        return locale.get();
    }

    /**
     * Set a new Locale, if it is defined in RB 'languages'
     *
     * @param lang
     *          must be of the correct form and within localesList
     */
    public static void setLocale(String lang) {
        Optional<Locale> newLocale = localesList.stream()
                .filter(locale ->  locale.getLanguage().equals(lang) ).findFirst();

        if (newLocale.isPresent()) {
            localeProperty().set(newLocale.get());
            Locale.setDefault(newLocale.get());
        } else {
            System.err.println(lang + " is an invalid language");
        }
    }

    /**
     * Get current Locale property
     *
     * @return locale with ObjectProperty wrappings
     */
    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }


    /**
     * Get the MessageFormat.format of the localized string from the given key from the
     * resource bundle 'languages' for the current locale, with the optional arguments args.
     *
     * @param key
     *          message key
     * @param args
     *          optional arguments for the message
     * @return localized formatted string
     */
    public static String get(final String key, final Object... args) {
        setLocale(getLocale().getLanguage());   // Temp fix
        ResourceBundle bundle = ResourceBundle.getBundle("languages", new UTF8Control());
        return MessageFormat.format(bundle.getString(key), args);
    }

    /**
     * Creates a String binding to the localized string from the given key.
     *
     * @param key
     *          message key
     * @param args
     *          optional arguements for the message
     * @return String binding
     */
    public static StringBinding createStringBinding(final String key, Object... args) {
        return Bindings.createStringBinding(() -> get(key, args), locale);
    }

    /**
     * Creates a String binding to the localized string that is computed by calling the given function
     *
     * @param func
     *          function called on every change
     * @return String binding
     */
    public static StringBinding createStringBinding(Callable<String> func) {
        return Bindings.createStringBinding(func, locale);
    }


}
