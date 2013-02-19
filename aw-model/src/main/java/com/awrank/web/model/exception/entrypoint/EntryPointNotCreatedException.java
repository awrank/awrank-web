/**
 *
 */
package com.awrank.web.model.exception.entrypoint;

import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown in case of {@link com.awrank.web.model.service.impl.EntryPointService#add} was not executed.
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
public class EntryPointNotCreatedException extends AwRankException {

    public EntryPointNotCreatedException() {
        super();
    }

    @Override
    public String getMessage() {
        return "New entry point was not created!";
    }

}
