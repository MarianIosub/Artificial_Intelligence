package ai.tema5.utils;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import lombok.SneakyThrows;
import org.eclipse.rdf4j.model.Statement;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ai.tema5.utils.StringUtils.OUTPUT_DOCUMENT;
import static ai.tema5.utils.StringUtils.OUTPUT_DOCUMENT_2;

public class ParserUtils {
    public static void findByEntityAndRelation(List<Statement> statements, String relation, String entity) {
        System.out.println("Relatiile care contin 'superTopicOf' si entitatatea "+entity+" sunt:\n\n");
        for (Statement statement : statements) {
            if ((statement.getObject().toString().contains(entity) || statement.getSubject().toString().contains(entity))
                    && statement.getPredicate().toString().contains(relation)) {
                System.out.println(statement);
            }
        }
    }

    @SneakyThrows
    public static void findTwoNounsWithVerbBetween(CoreDocument document) {
        File file = new File(OUTPUT_DOCUMENT);
        FileWriter fileWriter = new FileWriter(file);
        for (CoreSentence sentence : document.sentences()) {
            List<String> nounsAndVerbs = new ArrayList<>();
            for (int i = 0; i < sentence.tokens().size(); i++) {
                if (sentence.posTags() != null && sentence.posTags().get(i) != null && (sentence.posTags().get(i).contains("NN")
                        || sentence.posTags().get(i).contains("VB"))) {
                    nounsAndVerbs.add(sentence.posTags().get(i));
                }
            }
            Optional<String> firstNoun = nounsAndVerbs.stream()
                    .filter(verb -> verb.contains("NN"))
                    .findFirst();
            Optional<String> firstVerb = nounsAndVerbs.stream()
                    .filter(verb -> verb.contains("VB") && firstNoun.isPresent() && nounsAndVerbs.indexOf(verb) > nounsAndVerbs.indexOf(firstNoun.toString()))
                    .findFirst();
            Optional<String> lastNoun = nounsAndVerbs.stream()
                    .filter(verb -> verb.contains("NN") && firstVerb.isPresent() && nounsAndVerbs.indexOf(verb) > nounsAndVerbs.indexOf(firstVerb.toString()))
                    .findFirst();
            if (firstNoun.isPresent() && firstVerb.isPresent() && lastNoun.isPresent()) {
                fileWriter.append(sentence.toString()).append("\n");
            }
        }
        fileWriter.close();
    }

    @SneakyThrows
    public static void findSentencesWithOntology(List<Statement> ontologies, List<String> sentences) {
        File file = new File(OUTPUT_DOCUMENT_2);
        FileWriter fileWriter = new FileWriter(file);
        List<String> returnedSentences = new ArrayList<>();
        for (String sentence : sentences) {
            for (Statement ontology : ontologies) {
                String[] ontologyObjectSplitted = ontology.getObject().toString().split("/");
                String[] ontologySubjectSplitted = ontology.getSubject().toString().split("/");
                if (sentence.contains(ontologyObjectSplitted[ontologyObjectSplitted.length - 1]) ||
                        sentence.contains(ontologySubjectSplitted[ontologySubjectSplitted.length - 1])) {
                    returnedSentences.add(sentence);
                    break;
                }
            }
        }
        for (String sentence : returnedSentences) {
            fileWriter.append(sentence).append("\n");
        }
        fileWriter.close();
    }
}
