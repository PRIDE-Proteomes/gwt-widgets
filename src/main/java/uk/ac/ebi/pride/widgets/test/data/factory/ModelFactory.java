package uk.ac.ebi.pride.widgets.test.data.factory;


import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import uk.ac.ebi.pride.widgets.test.data.model.*;

public abstract class ModelFactory {

    interface BeanFactory extends AutoBeanFactory {
        AutoBean<Protein> protein();
        AutoBean<ProteinList> proteinList();
        AutoBean<ModifiedLocation> modifiedLocation();

        AutoBean<PeptideSpectrumMatch> peptideSpectrumMatch();
        AutoBean<PeptideSpectrumMatchList> peptideSpectrumMatchList();
        AutoBean<PeptideModification> peptideModification();

        AutoBean<Peptide> peptide();

        AutoBean<PeakList> peakList();
    }

    public static <T> T getModelObject(Class<T> cls, String json) throws ModelFactoryException {

        try{
            BeanFactory factory = GWT.create(BeanFactory.class);
            AutoBean<T> bean = AutoBeanCodex.decode(factory, cls, json);
            return bean.as();
        }catch (Throwable e){
            throw new ModelFactoryException("Error mapping json string for [" + cls + "]: " + json, e);
        }
    }

}