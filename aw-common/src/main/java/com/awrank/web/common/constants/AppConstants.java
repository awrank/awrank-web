package com.awrank.web.common.constants;

/**
 * Application constants.
 *
 * @author Andrew Stoyaltsev
 */
public interface AppConstants {

    static interface DateFormat {

        /*
        Naming format:
        - DF - means DateFormat, acronym
        - format
        - minus/dot/slash - type of separator in date format
         */

        static final String DF_yyyyMMdd_minus = "yyyy-MM-dd";

        static final String DF_ddMMyyyy_slash = "dd/MM/yyyy";

    }

}
