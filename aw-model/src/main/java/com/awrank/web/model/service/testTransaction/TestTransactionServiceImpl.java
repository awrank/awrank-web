package com.awrank.web.model.service.testTransaction;

import com.awrank.web.model.dao.testTransaction.TestTransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class TestTransactionServiceImpl implements TestTransactionService {

    @Autowired
    private TestTransactionDao testTransactionDao;


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void create() {
        testTransactionDao.create();
        throw new RuntimeException("test rollback");
    }
}
