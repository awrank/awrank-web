/**
 *
 */
package com.awrank.web.model.exception.entryhistory;

import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown in case of {@link com.awrank.web.model.service.impl.EntryHistoryService#add} was not executed.
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
public class EntryHistoryNotCreatedException extends AwRankException {

    public EntryHistoryNotCreatedException() {
        super();
    }

    @Override
    public String getMessage() {
        return "New entry history record was not created!";
    }

}
