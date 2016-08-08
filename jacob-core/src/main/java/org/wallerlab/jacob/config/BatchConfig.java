package org.wallerlab.jacob.config;


import java.io.IOException;

import org.springframework.batch.item.adapter.ItemProcessorAdapter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.xml.bind.JAXBElement;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.wallerlab.jacob.generated.Jacob;

@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
@ComponentScan("org.wallerlab.jacob")
public class BatchConfig{

    /**
     * getting copy of application context
     */
    @Autowired
    ConfigurableApplicationContext context;

    private static final String xml_directory = "file:/Users/jacob/IdeaProjects/jacob/jacob-data/*.xml";

    @Autowired
    @Qualifier("bootstrapStep")
    private Step bootstrapStep;

    /**
     * Bean for building the bootstrap job executing bootstrapStep.
     *
     * @param jbf, JobBuilderFactory
     * @return bootstrap
     * @throws IOException
     */
    @Bean
    public Job bootstrapJob(JobBuilderFactory jbf) throws IOException {
        return jbf.get("bootstrap")
                .incrementer(new RunIdIncrementer())
                .flow(bootstrapStep)
                .end()
                .build();
    }

    /**
     * Bean for building the bootstrap step.
     *
     * @param sbf, StepBuilderFactory
     * @return bootstrapStep
     * @throws IOException
     */
    @Bean
    public Step bootstrapStep(StepBuilderFactory sbf) throws IOException {
        return sbf.get("bootstrapStep").chunk(1)
                .reader(multiReader())
                .processor(itemProcessor())
                .writer((ItemWriter)context.getBean("writer"))
                .build();
    }

    /**
     * Setting ItemReader for bootstrapStep as a MultiResourceItemReader. Use
     * PathMatchingResourcePatternResolver for setting the file path. Delegates
     * to the PdbmlFileReader to handle the pdbml files.
     *
     * @return Reader
     * @throws IOException
     */
    @Bean
    public MultiResourceItemReader multiReader() throws IOException {
        MultiResourceItemReader reader = new MultiResourceItemReader();
        PathMatchingResourcePatternResolver pathMatchinResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = pathMatchinResolver.getResources(xml_directory);
        for(Resource resource :resources)
            System.out.println("Resource found: " + resource);
        reader.setResources(resources);
        reader.setDelegate((ResourceAwareItemReaderItemStream) cmlFileReader());
        return reader;
    }

    @Bean
    ItemProcessor itemProcessor(){
        ItemProcessorAdapter processor = new ItemProcessorAdapter<>();
        processor.setTargetObject(context.getBean("translator"));
        processor.setTargetMethod("translate");
        return (ItemProcessor) processor;
    }

    /**
     *  Standard Spring Batch item reader for OXM (Object Unmarshalling)
     *
     * @return Item Reader bean -{@link org.springframework.batch.item.xml.StaxEventItemReader<T>}
     */
    @Bean
    StaxEventItemReader<JAXBElement> cmlFileReader() {
        StaxEventItemReader reader = new StaxEventItemReader();
        reader.setUnmarshaller(unmarshaller());
        reader.setFragmentRootElementName("Jacob");
        return reader;
    }

    /**
     *  Standard Spring Batch item reader for JAXB
     *
     * @return  JAXB reader bean -{@link org.springframework.oxm.jaxb.Jaxb2Marshaller}
     */
    @Bean
    org.springframework.oxm.Unmarshaller unmarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Jacob.class);
        return (Unmarshaller) marshaller;
    }


}