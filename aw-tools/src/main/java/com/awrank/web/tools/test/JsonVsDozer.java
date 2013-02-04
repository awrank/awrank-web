package com.awrank.web.tools.test;

import com.awrank.web.model.dao.dictionary.wrapper.DictionaryResource;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.constant.ELanguage;
//import org.dozer.DozerBeanMapper;
//import org.dozer.Mapper;

/**
 * User: a_polyakov
 */
public class JsonVsDozer {
    private static int COUNT_ITERATION = 1000000;

    public static final void main(String args[]) {
        DictionaryResource wrapper = new DictionaryResource(15L, ELanguage.RU, "Error", "Error desc");
//        Mapper mapper = new DozerBeanMapper();

        long time = System.currentTimeMillis();
//        for (int i=0; i<COUNT_ITERATION; i++){
//            final Dictionary dictionary=mapper.map(wrapper, Dictionary.class);
//            dictionary.setId(null);
//        }
//        time=System.currentTimeMillis()-time;
//        System.out.println("Time dozer "+time);
//        time=System.currentTimeMillis();
        for (int i = 0; i < COUNT_ITERATION; i++) {
            final Dictionary dictionary = new Dictionary(wrapper.toJsonObject());
            dictionary.setId(null);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Time json " + time);

    }
}
