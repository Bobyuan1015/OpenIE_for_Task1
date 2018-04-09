package bobCoreNlp;


import edu.stanford.nlp.coref.CorefCoreAnnotations.CorefChainAnnotation;

//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		System.out.println("helo");
//	}
//
//}


import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;


public class example {

  public static String text = "Joe Smith was born in California. " +
      "In 2017, he went to Paris, France in the summer. " +
      "His flight left at 3:00pm on July 10th, 2017. " +
      "After eating some escargot for the first time, Joe said, \"That was delicious!\" " +
      "He sent a postcard to his sister Jane Smith. " +
      "After hearing about Joe's trip, Jane decided she might go to France one day.";

//  public static void main(String[] args) {
//    // set up pipeline properties
//    Properties props = new Properties();
//    // set the list of annotators to run
//    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,quote");
//    // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
//    props.setProperty("coref.algorithm", "neural");
//    // build pipeline
//    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//    // create a document object
//    CoreDocument document = new CoreDocument(text);
//    // annnotate the document
//    pipeline.annotate(document);
//    // examples
//
//    // 10th token of the document
//    CoreLabel token = document.tokens().get(10);
//    System.out.println("Example: token");
//    System.out.println(token);
//    System.out.println();
//
//    // text of the first sentence
//    String sentenceText = document.sentences().get(0).text();
//    System.out.println("Example: sentence");
//    System.out.println(sentenceText);
//    System.out.println();
//
//    // second sentence
//    CoreSentence sentence = document.sentences().get(1);
//
//    // list of the part-of-speech tags for the second sentence
//    List<String> posTags = sentence.posTags();
//    System.out.println("Example: pos tags");
//    System.out.println(posTags);
//    System.out.println();
//
//    // list of the ner tags for the second sentence
//    List<String> nerTags = sentence.nerTags();
//    System.out.println("Example: ner tags");
//    System.out.println(nerTags);
//    System.out.println();
//
//    // constituency parse for the second sentence
//    Tree constituencyParse = sentence.constituencyParse();
//    System.out.println("Example: constituency parse");
//    System.out.println(constituencyParse);
//    System.out.println();
//
//    // dependency parse for the second sentence
//    SemanticGraph dependencyParse = sentence.dependencyParse();
//    System.out.println("Example: dependency parse");
//    System.out.println(dependencyParse);
//    System.out.println();
//
////    // kbp relations found in fifth sentence
////    List<RelationTriple> relations =
////        document.sentences().get(4).relations();
////    System.out.println("Example: relation");
////    System.out.println(relations.get(0));
////    System.out.println();
//
//    // entity mentions in the second sentence
//    List<CoreEntityMention> entityMentions = sentence.entityMentions();
//    System.out.println("Example: entity mentions");
//    System.out.println(entityMentions);
//    System.out.println();
//
//    // coreference between entity mentions
//    CoreEntityMention originalEntityMention = document.sentences().get(3).entityMentions().get(1);
//    System.out.println("Example: original entity mention");
//    System.out.println(originalEntityMention);
//    System.out.println("Example: canonical entity mention");
//    System.out.println(originalEntityMention.canonicalEntityMention().get());
//    System.out.println();
//
//    // get document wide coref info
//    Map<Integer, CorefChain> corefChains = document.corefChains();
//    System.out.println("Example: coref chains for document");
//    System.out.println(corefChains);
//    System.out.println();
//
//    // get quotes in document
//    List<CoreQuote> quotes = document.quotes();
//    CoreQuote quote = quotes.get(0);
//    System.out.println("Example: quote");
//    System.out.println(quote);
//    System.out.println();
//
//    // original speaker of quote
//    // note that quote.speaker() returns an Optional
//    System.out.println("Example: original speaker of quote");
//    System.out.println(quote.speaker().get());
//    System.out.println();
//
//    // canonical speaker of quote
//    System.out.println("Example: canonical speaker of quote");
//    System.out.println(quote.canonicalSpeaker().get());
//    System.out.println();
//
//  }

//  public static void main(String[] args) {
//
//      // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
//      Properties props = new Properties();
//      props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
//      StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//
//      // read some text in the text variable
//      String text = "Bell, a telecommunication company, which is based in Los Angeles, makes and distributes electronic, computer and building products.";
//
//      // create an empty Annotation just with the given text
//      Annotation document = new Annotation(text);
//
//      // run all Annotators on this text
//      pipeline.annotate(document);
//      List<CoreMap> sentences = document.get(SentencesAnnotation.class);
//
//      for(CoreMap sentence: sentences) {
//        // traversing the words in the current sentence
//        // a CoreLabel is a CoreMap with additional token-specific methods
//        for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
//          // this is the text of the token
//          String word = token.get(TextAnnotation.class);
//          // this is the POS tag of the token
//          String pos = token.get(PartOfSpeechAnnotation.class);
//          // this is the NER label of the token
//          String ne = token.get(NamedEntityTagAnnotation.class);
//          System.out.println(String.format("word=%s  pos=%s  ne=%s",word,pos,ne));
//        }
//
//        // this is the parse tree of the current sentence
//        Tree tree = sentence.get(TreeAnnotation.class);
//        System.out.println("tree:"+tree);
//        // this is the Stanford dependency graph of the current sentence
//        SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
//        System.out.println("dependencies:"+dependencies);
//      }
//
//      // This is the coreference link graph
//      // Each chain stores a set of mentions that link to each other,
//      // along with a method for getting the most representative mention
//      // Both sentence and token offsets start at 1!
//      Map<Integer, CorefChain> graph = 
//        document.get(CorefChainAnnotation.class);
//      System.out.println("final graph:"+graph);
//  
//  }
  
  
//  public static void main(String[] args) {
//  LexicalizedParser lp = LexicalizedParser.loadModel(
//		  "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",
//		  "-maxLength", "80", "-retainTmpSubcategories");
//		  TreebankLanguagePack tlp = new PennTreebankLanguagePack();
//		  // Uncomment the following line to obtain original Stanford Dependencies
//		  // tlp.setGenerateOriginalDependencies(true);
//		  GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
//		  String[] sent = {"This", "is", "an", "easy", "sentence", "."} ;
//		  Tree parse = lp.apply(SentenceUtils.toWordList(sent));
//		  GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
//		  Collection<TypedDependency> tdl = gs.allTypedDependencies();
//		  System.out.println(tdl);
//  }
}