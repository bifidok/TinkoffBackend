package edu.java;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.ForcedType;
import org.jooq.meta.jaxb.Generate;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Property;
import org.jooq.meta.jaxb.Target;

public class JooqCodegen {
    private JooqCodegen() {

    }

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) throws Exception {
        Database database = new Database()
            .withName("org.jooq.meta.extensions.liquibase.LiquibaseDatabase")
            .withProperties(
                new Property().withKey("rootPath").withValue("migrations"),
                new Property().withKey("scripts").withValue("master.xml")
            )
            .withForcedTypes(
                new ForcedType()
                    .withUserType("edu.java.scrapper.models.ChatState")
                    .withEnumConverter(true)
                    .withIncludeExpression(".*\\.STATUS")
            );

        Generate options = new Generate()
            .withGeneratedAnnotation(true)
            .withGeneratedAnnotationDate(false)
            .withJpaAnnotations(false)
            .withValidationAnnotations(true)
            .withSpringAnnotations(true)
            .withConstructorPropertiesAnnotation(true)
            .withConstructorPropertiesAnnotationOnPojos(true)
            .withConstructorPropertiesAnnotationOnRecords(true)
            .withFluentSetters(false)
            .withDaos(false)
            .withPojos(true);

        Target target = new Target()
            .withPackageName("edu.java.scrapper.repositories.jooq.generated")
            .withDirectory("scrapper/src/main/java");

        Configuration configuration = new Configuration()
            .withGenerator(
                new Generator()
                    .withDatabase(database)
                    .withGenerate(options)
                    .withTarget(target)
            );

        GenerationTool.generate(configuration);
    }
}
