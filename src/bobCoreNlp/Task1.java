package bobCoreNlp;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.naturalli.OpenIE;
import edu.stanford.nlp.naturalli.SentenceFragment;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * A demo illustrating how to call the OpenIE system programmatically.
 * You can call this code with:
 *
 * <pre>
 *   java -mx1g -cp stanford-openie.jar:stanford-openie-models.jar edu.stanford.nlp.naturalli.OpenIEDemo
 * </pre>
 *
 */
public class Task1 {

  private Task1() {} // static main

  public static void main(String[] args) throws Exception {
    // Create the Stanford CoreNLP pipeline

    Properties props = PropertiesUtils.asProperties(
            "annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie"
    );
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	  DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get("./data/"));
	  
	  PrintWriter writer = new PrintWriter("./output.txt", "UTF-8");	
	  
	  
	  for(Path p :paths) {			  

		    // Annotate an example document.
		    String text;
		    text = IOUtils.slurpFile(p.toString());
		    writer.println("--------------------File:"+p.toString() + "----------------\n" );
		    System.out.println("Input:\n"+text);
		    Annotation doc = new Annotation(text);
		    pipeline.annotate(doc);
		
		    // Loop over sentences in the document
		    int sentNo = 0;
		    for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
		    	writer.println("Sentence #" + ++sentNo + ": " + sentence.get(CoreAnnotations.TextAnnotation.class));
		
		      // Print SemanticGraph
		    	writer.println(sentence.get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class).toString(SemanticGraph.OutputFormat.LIST));
		
		      // Get the OpenIE triples for the sentence
		      Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
		
		      // Print the triples
		      for (RelationTriple triple : triples) {
		        System.out.println(triple.confidence + "\t" +
		            triple.subjectLemmaGloss() + "\t" +
		            triple.relationLemmaGloss() + "\t" +
		            triple.objectLemmaGloss());
		      }
		
		      // Alternately, to only run e.g., the clause splitter:
		      List<SentenceFragment> clauses = new OpenIE(props).clausesInSentence(sentence);
		      for (SentenceFragment clause : clauses) {
		    	  writer.println(clause.parseTree.toString(SemanticGraph.OutputFormat.LIST));
		      }
		      writer.println();
		      
		    }
	  }
	  writer.close();
  }

}
