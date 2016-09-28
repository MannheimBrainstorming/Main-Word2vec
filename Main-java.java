import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class main {
	public static String removeQuestion(String textOne, String textQuestion) throws IOException{

	     ArrayList<String> arrayOne = new ArrayList<>();
	     ArrayList<String> arrayQuestion = new ArrayList<>();		
	 	 StringTokenizer st = new StringTokenizer(textOne);
	 	 StringTokenizer st2 = new StringTokenizer(textQuestion);

	     while (st.hasMoreTokens()) {
	         arrayOne.add(st.nextToken());
	     }
	     while (st2.hasMoreTokens()) {
	         arrayQuestion.add(st2.nextToken());
	     }
	     for (int z=0; z<4;z++){
	    	 for (int i=0;i<arrayQuestion.size();i++){
		    	 for (int j=0;j<arrayOne.size();j++){
		    		 if(arrayQuestion.get(i).equals(arrayOne.get(j))){
		    			 arrayOne.remove(j);
		    		 }
		    	 }
		     }
	     }
	     textOne="";
	     textQuestion="";
	     for (int i=0;i<arrayOne.size();i++){
	    	 textOne=textOne+" "+arrayOne.get(i);
	     }
	     
		 return textOne;
	}
	public static String[] gloss(String word, String PartofSpeech ) throws IOException{
		ArrayList<String> myGloss = new ArrayList<>();
		String wnhome = "/usr/local/WordNet-3.0/";
		String path = wnhome + File.separator + "dict";
		URL url  = new URL ("file", null, path);
		IDictionary dic = new Dictionary(url);
		dic.open();
		
		//Default value for idxWord,myWord and mySynSet
		IIndexWord idxWord = dic.getIndexWord("book", POS.NOUN);
		IWordID myWordID = idxWord.getWordIDs().get(0);
		IWord myWord = dic.getWord(myWordID);
		ISynset mySynSet = myWord.getSynset();
		//gathering all NOUNs
		
		if(PartofSpeech.equals("NOUN")){
			try {
				for (int i =0; i<20; i++){
					 if(dic.getIndexWord(word, POS.NOUN)==null){
						 break;
					 }
					 
					 idxWord = dic.getIndexWord(word, POS.NOUN);
					 if (idxWord.getWordIDs().get(i)!=null){
						 myGloss.add(dic.getWord(idxWord.getWordIDs().get(i)).getSynset().getGloss().replaceAll("_", " ").toLowerCase());
					 }
				}
			}catch (IndexOutOfBoundsException e) {
			    System.err.println("IndexOutOfBoundsException(noun): " + e.getMessage());
			}
		}
		
		//gathering all ADJECTIVES
		if(PartofSpeech.equals("ADJECTIVE")){
			try {
				for (int i =0; i<20; i++){
					 if(dic.getIndexWord(word, POS.ADJECTIVE)==null){
						 break;
					 }
					 idxWord = dic.getIndexWord(word, POS.ADJECTIVE);
					 if (idxWord.getWordIDs().get(i)!=null){
						 mySynSet = dic.getWord(idxWord.getWordIDs().get(i)).getSynset();
							for (IWord w : mySynSet.getWords()){
								if (myGloss.contains(w.getLemma())==false){
									myGloss.add(w.getSynset().getGloss().replaceAll("_", " ").toLowerCase());
								}
							}
					 }
				}
			}catch (IndexOutOfBoundsException e) {
			    System.err.println("IndexOutOfBoundsException(adjective): " + e.getMessage());
			}
		}

		//gathering all ADVERBS	
		if(PartofSpeech.equals("ADVERB")){	
			try {
				for (int i =0; i<20; i++){
					 if(dic.getIndexWord(word, POS.ADVERB)==null){
						 break;
					 }
					 idxWord = dic.getIndexWord(word, POS.ADVERB);
					 if (idxWord.getWordIDs().get(i)!=null){
						 mySynSet = dic.getWord(idxWord.getWordIDs().get(i)).getSynset();
							for (IWord w : mySynSet.getWords()){
								if (myGloss.contains(w.getLemma())==false ){
									myGloss.add(w.getSynset().getGloss().replaceAll("_", " ").toLowerCase());
								}
							}
					 }
				}
			}catch (IndexOutOfBoundsException e) {
			    System.err.println("IndexOutOfBoundsException:(adverb) " + e.getMessage());
			}
		}
		
		//gathering all VERBS
		if(PartofSpeech.equals("VERB")){	
			try {
				for (int i =0; i<20; i++){
					 if(dic.getIndexWord(word, POS.VERB)==null){
						 break;
					 }
					 idxWord = dic.getIndexWord(word, POS.VERB);
					 if (idxWord.getWordIDs().get(i)!=null){
						 mySynSet = dic.getWord(idxWord.getWordIDs().get(i)).getSynset();
							for (IWord w : mySynSet.getWords()){
								if (myGloss.contains(w.getLemma())==false){
									myGloss.add(w.getSynset().getGloss().replaceAll("_", " ").toLowerCase());
								}
							}
					 }
				}
			}catch (IndexOutOfBoundsException e) {
			    System.err.println("IndexOutOfBoundsException:(verb) " + e.getMessage());
			}
		}
		dic.close();
		String temp="";
		//trimming the gloss strings
		for(int i=0;i<myGloss.size();i++){
			if(myGloss.get(i).indexOf("; \"")>0){
				for (int j=0; j < myGloss.get(i).indexOf("; \""); j++){
					temp=temp+myGloss.get(i).charAt(j);
				}
			}
			else{
				temp=myGloss.get(i);
			}
			myGloss.set(i, temp);
			temp="";
		}
		String[] finalString = new String[myGloss.size()];
		for (int i=0;i<myGloss.size();i++){
			finalString[i]=myGloss.get(i);
		}
		return finalString;
	}
	public static String[] synonyms(String word, String PartofSpeech) throws IOException{
		ArrayList<String> myArralyList = new ArrayList<>();
		String wnhome = "/usr/local/WordNet-3.0/";
		String path = wnhome + File.separator + "dict";
		URL url  = new URL ("file", null, path);
		IDictionary dic = new Dictionary(url);
		dic.open();
		
		//Default value for idxWord,myWord and mySynSet
		IIndexWord idxWord = dic.getIndexWord("book", POS.NOUN);
		IWordID myWordID = idxWord.getWordIDs().get(0);
		IWord myWord = dic.getWord(myWordID);
		ISynset mySynSet = myWord.getSynset();
		
		//gathering all NOUNs
		if(PartofSpeech.equals("NOUN")){
			try {
				for (int i =0; i<20; i++){
					 if(dic.getIndexWord(word, POS.NOUN)==null){
						 break;
					 }
					 idxWord = dic.getIndexWord(word, POS.NOUN);
					 if (idxWord.getWordIDs().get(i)!=null){
						 mySynSet = dic.getWord(idxWord.getWordIDs().get(i)).getSynset();
							for (IWord w : mySynSet.getWords()){
								if (myArralyList.contains(w.getLemma())==false){
									myArralyList.add(w.getLemma().replaceAll("_", " ").toLowerCase());
								}
							}
					 }
				}
			}catch (IndexOutOfBoundsException e) {
			    System.err.println("IndexOutOfBoundsException(noun): " + e.getMessage());
			}
		}
		
		//gathering all ADJECTIVES
		if(PartofSpeech.equals("ADJECTIVE")){
			try {
				for (int i =0; i<20; i++){
					 if(dic.getIndexWord(word, POS.ADJECTIVE)==null){
						 break;
					 }
					 idxWord = dic.getIndexWord(word, POS.ADJECTIVE);
					 if (idxWord.getWordIDs().get(i)!=null){
						 mySynSet = dic.getWord(idxWord.getWordIDs().get(i)).getSynset();
							for (IWord w : mySynSet.getWords()){
								if (myArralyList.contains(w.getLemma())==false){
									myArralyList.add(w.getLemma().replaceAll("_", " ").toLowerCase());
								}
							}
					 }
				}
			}catch (IndexOutOfBoundsException e) {
			    System.err.println("IndexOutOfBoundsException(adjective): " + e.getMessage());
			}
		}

		//gathering all ADVERBS	
		if(PartofSpeech.equals("ADVERB")){	
			try {
				for (int i =0; i<20; i++){
					 if(dic.getIndexWord(word, POS.ADVERB)==null){
						 break;
					 }
					 idxWord = dic.getIndexWord(word, POS.ADVERB);
					 if (idxWord.getWordIDs().get(i)!=null){
						 mySynSet = dic.getWord(idxWord.getWordIDs().get(i)).getSynset();
							for (IWord w : mySynSet.getWords()){
								if (myArralyList.contains(w.getLemma())==false ){
									myArralyList.add(w.getLemma().replaceAll("_", " ").toLowerCase());
								}
							}
					 }
				}
			}catch (IndexOutOfBoundsException e) {
			    System.err.println("IndexOutOfBoundsException:(adverb) " + e.getMessage());
			}
		}
		
		//gathering all VERBS
		if(PartofSpeech.equals("VERB")){	
			try {
				for (int i =0; i<20; i++){
					 if(dic.getIndexWord(word, POS.VERB)==null){
						 break;
					 }
					 idxWord = dic.getIndexWord(word, POS.VERB);
					 if (idxWord.getWordIDs().get(i)!=null){
						 mySynSet = dic.getWord(idxWord.getWordIDs().get(i)).getSynset();
							for (IWord w : mySynSet.getWords()){
								if (myArralyList.contains(w.getLemma())==false){
									myArralyList.add(w.getLemma().replaceAll("_", " ").toLowerCase());
								}
							}
					 }
				}
			}catch (IndexOutOfBoundsException e) {
			    System.err.println("IndexOutOfBoundsException:(verb) " + e.getMessage());
			}
		}
		dic.close();
		String[] finalString = new String[myArralyList.size()];
		for (int i=0;i<myArralyList.size();i++){
			finalString[i]=myArralyList.get(i);
		}
		return finalString;
	}
	public static int StopWordsRemoval(String text) throws IOException{
		ArrayList<String> StopWordsList = new ArrayList<String>();
		StopWordsList = (ArrayList<String>) Files.readAllLines(Paths.get("/home/farbod/Desktop/stopwordslist541.txt"));
		int CheckStopWord =0;
		// i must be less equal than the number of the lines in the stopwords text file.
		for(int i=0; i<=257; i++){
			if (new String(text).equals(StopWordsList.get(i))){
				CheckStopWord = 1;
			}
		}
		// checkstopword=0 means the word is not a stop word.
		return CheckStopWord;
	}
	public static List<List<String>> POSPhrase (String phrase) throws IOException{
		// creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
				List<List<String>> fullList = new ArrayList<List<String>>();
				ArrayList<String> wordTokens = new ArrayList<>();
				ArrayList<String> wordPOS = new ArrayList<>();
				ArrayList<String> wordNE = new ArrayList<>();

				
				Properties props = new Properties();
				props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
				StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

				// create an empty Annotation just with the given text
				Annotation document = new Annotation(phrase);

				// run all Annotators on this text
				pipeline.annotate(document);
			
				// these are all the sentences in this document
				// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
				List<CoreMap> sentences = document.get(SentencesAnnotation.class);
				
				for(CoreMap sentence: sentences) {
				  // traversing the words in the current sentence
				  // a CoreLabel is a CoreMap with additional token-specific methods
				  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
				    // this is the text of the token which is lemmatized
				    String word = token.get(LemmaAnnotation.class);
				    // this is the POS tag of the token
				    String pos = token.get(PartOfSpeechAnnotation.class);
				    // this is the NER label of the token
				    String ne = token.get(NamedEntityTagAnnotation.class);
				    //System.out.println("Word:("+word+") POS("+pos+") EntityName("+ne+")"+"\n");
				    int CheckStopWord = StopWordsRemoval(word);
				    if (CheckStopWord == 0) {
					 if( 	 !pos.equalsIgnoreCase("CC")&&
							 !pos.equalsIgnoreCase("CD")&&
							 !pos.equalsIgnoreCase("DT")&&
							 !pos.equalsIgnoreCase("EX")&&
							 !pos.equalsIgnoreCase("FW")&&
							 !pos.equalsIgnoreCase("IN")&&
							 !pos.equalsIgnoreCase("LS")&&
							 !pos.equalsIgnoreCase("MD")&&
							 !pos.equalsIgnoreCase("PDT")&&
							 !pos.equalsIgnoreCase("POS")&&
							 !pos.equalsIgnoreCase("PRP")&&
							 !pos.equalsIgnoreCase("PRP$")&&
							 !pos.equalsIgnoreCase("SYM")&&
							 !pos.equalsIgnoreCase("UH")&&
							 !pos.equalsIgnoreCase("TO")&& 
							 !pos.equalsIgnoreCase("WDT")&& 	 
							 !pos.equalsIgnoreCase("WP")&&
							 !pos.equalsIgnoreCase("WP$")&&
							 !pos.equalsIgnoreCase("WRB")&&
							 !pos.equalsIgnoreCase("``")&&
							 !word.contains(".")&&
							 !word.contains(",")&&
							 !word.contains("?")&&
							 !word.contains("|")){
						 wordTokens.add(word);
						 wordPOS.add(pos);
						 wordNE.add(ne);
					 }
					 }
				  }
				}
		fullList.add(wordTokens);
		fullList.add(wordPOS);
		fullList.add(wordNE);
		return fullList;
		
	}
	public static String onlyOneString(String textOne) throws IOException{

		List<List<String>> fullList = new ArrayList<List<String>>();
		ArrayList<String> textString = new ArrayList<>();
		ArrayList<String> textStringPOS = new ArrayList<>();

		ArrayList<String> myArralyList = new ArrayList<>();
		ArrayList<String> myGloss = new ArrayList<>();
		String PartofSpeech="NOUN";
		
		fullList=POSPhrase(textOne);
		String tempArr = new String(); 
		StanfordLemmatizer test = new StanfordLemmatizer();

		//getting the tokenized text of the first phrase and lemmatize and stopword must be removed and get the POS
		for (int j=0; j<fullList.get(0).size();j++){
			try{
				//tempArr=test.lemmatize(fullList.get(0).get(j));
				tempArr=fullList.get(0).get(j);
				textString.add(tempArr);
				textStringPOS.add(fullList.get(1).get(j));
				
			} catch (IndexOutOfBoundsException e) {
				
			}
		}
		
		//getting the sysnsets for phrase1
		ArrayList<String> tempArrayList = new ArrayList<>();
		for (int i=0;i<textString.size();i++){
			if(textStringPOS.get(i).equals("NN") || textStringPOS.get(i).equals("NNP") ||textStringPOS.get(i).equals("NNS") ||textStringPOS.get(i).equals("NNPS")){
				PartofSpeech="NOUN";
			} else if(textStringPOS.get(i).equals("JJ") || textStringPOS.get(i).equals("JJR") ||textStringPOS.get(i).equals("JJS")){
				PartofSpeech="ADJECTIVE";
			} else if(textStringPOS.get(i).equals("RB") || textStringPOS.get(i).equals("RBR") ||textStringPOS.get(i).equals("RBS")){
				PartofSpeech="ADVERB";
			}else if(textStringPOS.get(i).equals("VB") || textStringPOS.get(i).equals("VBD") ||textStringPOS.get(i).equals("VBG") ||textStringPOS.get(i).equals("VBN") ||textStringPOS.get(i).equals("VBP")|| textStringPOS.get(i).equals("VBZ")){
				PartofSpeech="VERB";
			}
			else{
				PartofSpeech="NOUN";
			}
			String[] myTest= new String[tempArrayList.size()];
			myTest=(String[]) synonyms(textString.get(i).toLowerCase(),PartofSpeech);
			for (int j=myTest.length-1; j>=0;j--){
				myArralyList.add(myTest[j]);
			}
			Arrays.fill(myTest, null);
		}
	    //removing duplicates from myArrayList
	    for (int z=0;z<100;z++){
	    	try{
	    		for (int i=0;i<myArralyList.size();i++){
	    			for (int j=0;j<myArralyList.size();j++){
	    				if(i!=j){
	    					if(myArralyList.get(i).equals(myArralyList.get(j))){
	    						myArralyList.remove(j);
	    					}
	    					if(myArralyList.get(i).equals("null")){
	    						myArralyList.remove(i);
	    					}
	    				}
	    			}
	    		}
	   	 	}catch (IndexOutOfBoundsException e) {		
	   	 	}
	   	}
		
		//getting the gloss for phrase1
		for (int i=0;i<textString.size();i++){
			if(textStringPOS.get(i).equals("NN") || textStringPOS.get(i).equals("NNP") ||textStringPOS.get(i).equals("NNS") ||textStringPOS.get(i).equals("NNPS")){
				PartofSpeech="NOUN";
			} else if(textStringPOS.get(i).equals("JJ") || textStringPOS.get(i).equals("JJR") ||textStringPOS.get(i).equals("JJS")){
				PartofSpeech="ADJECTIVE";
			} else if(textStringPOS.get(i).equals("RB") || textStringPOS.get(i).equals("RBR") ||textStringPOS.get(i).equals("RBS")){
				PartofSpeech="ADVERB";
			}else if(textStringPOS.get(i).equals("VB") || textStringPOS.get(i).equals("VBD") ||textStringPOS.get(i).equals("VBG") ||textStringPOS.get(i).equals("VBN") ||textStringPOS.get(i).equals("VBP")|| textStringPOS.get(i).equals("VBZ")){
				PartofSpeech="VERB";
			}
			else{
				PartofSpeech="NOUN";
			}
			String[] myTest= new String[tempArrayList.size()]; 
			myTest=(String[]) gloss(textString.get(i).toLowerCase(),PartofSpeech);
			for (int j=myTest.length-1; j>=0;j--){
				myGloss.add(myTest[j]);
			}
			Arrays.fill(myTest, null);
		}

	
		
		List<String> tempArr2 = new LinkedList<String>(); 

		//Lemmatization for gloss
		for (int i=0; i<myGloss.size();i++){
			tempArr2=(test.lemmatize(myGloss.get(i)));
			for (int j=0; j<tempArr2.size();j++){
				myArralyList.add(tempArr2.get(j));
			}
			tempArr2.clear();
		}
				

		for (int z=0;z<20;z++){
			//Sorting arraylists
			Collections.sort(myArralyList);
			for(int i=0;i<myArralyList.size();i++){
				try{
					if(myArralyList.get(i).equals(myArralyList.get(i+1))){
						myArralyList.remove(i+1);
					}
				}catch(IndexOutOfBoundsException e){
				}
				try{
					if(myArralyList.get(i).equals(myArralyList.get(i-1))){
						myArralyList.remove(i-1);
					}
				}catch(IndexOutOfBoundsException e){
				}
				
			}
		}
		String resultText="";
		for (int i=0;i<myArralyList.size();i++){
			resultText=resultText+" "+myArralyList.get(i);
		}
		
		return resultText;
		
	}
	public static String[] removeSimilarity (String[] textArray) throws IOException{
		int counter=0;
		int cnt=0;
		String[] newTextArray = new String[textArray.length];
		for (int i=0;i<textArray.length;i++){
			StringTokenizer st = new StringTokenizer(textArray[i]);
		    while (st.hasMoreTokens()) {
		    	String temp=st.nextToken();
		    	for(int j=0;j<textArray.length;j++){
		    		if (j!=i){
		    			if (textArray[j].contains(temp)){
		    				counter++;
		    			}
		    		}
		    	}if (counter!=textArray.length-1){
		    		if (cnt==0){
		    			newTextArray[i]=temp;
		    			cnt=1;
		    		}
		    		else {
		    			newTextArray[i]=newTextArray[i]+" "+temp;
		    		}
		    	}
			    counter=0;
		    }
		    cnt=0;
		}
		return newTextArray;
	}
	public static LinkedHashSet<String> deDup(String s) {
		return new LinkedHashSet<String>(Arrays.asList(s.split(" ")));
	}
	public static void main(String[] args) throws IOException {
		
		String csvFile = "/home/farbod/Documents/University/Courses/Teamproject/Finalforreport/Runningthecodehere/CSV0T1Q1.csv";
		BufferedReader br = null;
		BufferedReader br2 = null;
		BufferedReader br3 = null;
		BufferedReader tbc = null;
		BufferedReader qTopic = null;
		BufferedReader answ = null;
		BufferedReader answ1 = null;
		String qword="";
		
		String line = "";
		String cvsSplitBy = "|";
		ArrayList<String> cluster1 = new ArrayList<>();
		ArrayList<String> cluster2 = new ArrayList<>();
		ArrayList<String> cluster3 = new ArrayList<>();

		try {
			//counting the number of clusters
			int tabCount =0;
			tbc= new BufferedReader(new FileReader(csvFile));
			line = tbc.readLine();
				String[] gsCount = line.split(cvsSplitBy);
				for (int i=0;i<gsCount.length;i++){
					if (gsCount[i].equals("|")){
						tabCount+=1;
					}
				}
			
			//reading question topic
			qTopic = new BufferedReader(new FileReader(csvFile));
			line = qTopic.readLine();
			int qtemp=0;
			String[] qTopic1 = line.split(cvsSplitBy);
			while (!"|".equals(qTopic1[qtemp])){
				qword=qword+qTopic1[qtemp];
				qtemp++;
			}
			
			//copying the answers into arrays
			answ = new BufferedReader(new FileReader(csvFile));
			int answCounter=0;
			while ((line = answ.readLine()) != null) {
				answCounter+=1;
			}
			String[] textArray=new String[answCounter-1];
			String[] orgtextArray=new String[answCounter-1];
			int strCnt=0;
			answ1 = new BufferedReader(new FileReader(csvFile));
			int jumpCnt=0;
			while ((line = answ1.readLine()) != null) {
				String[] GS = line.split(cvsSplitBy);	
				if (jumpCnt!=0){ 
					String word = "";
					int temp=0;
					while (!"|".equals(GS[temp])){
						word=word+GS[temp];
						temp++;
					}
					textArray[strCnt]=word;
					orgtextArray[strCnt]=word;
					//System.out.println(word);
					strCnt+=1;
				}else{
					jumpCnt+=1;
				}
			}
			
			//Cluster 111111111111111111
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

			    // use tab as separator
				String[] GS = line.split(cvsSplitBy);				
				String word = "";
				int temp=0;
				for (int i=0;i<GS.length;i++){
					if (GS[i].equals("|")){
						temp=i+1;
						break;
					}
				}
				while (!"|".equals(GS[temp])){
					word=word+GS[temp];
					temp++;
				}
				cluster1.add(word);
			}
			
			//Cluster 2222222222222222222
			br2 = new BufferedReader(new FileReader(csvFile));
			while ((line = br2.readLine()) != null) {

			    // use comma as separator
				String[] GS = line.split(cvsSplitBy);
				//System.out.println("GS1= " + GS[3] + " , GS2= " + GS[4]);
				
				String word = "";
				int temp=0;
				int chk=0;
				for (int i=0;i<GS.length;i++){
					if (GS[i].equals("|")){
						chk++;
						if (chk==2){
							temp=i+1;
							break;
						}
					}
				}
				if (tabCount==3){
					while (!"|".equals(GS[temp])){
						word=word+GS[temp];
						temp++;
					}
				} else{
					while (temp<GS.length){
						word=word+GS[temp];
						temp++;
					}
				}
				cluster2.add(word);
			}
			if (tabCount==3){
				//Cluster 33333333333333
				br3 = new BufferedReader(new FileReader(csvFile));
				while ((line = br3.readLine()) != null) {
				    // use tab as separator
					String[] GS = line.split(cvsSplitBy);				
					String word = "";
					int temp=0;
					int chk=0;
					for (int i=0;i<GS.length;i++){
						if (GS[i].equals("|")){
							chk++;
							if (chk==3){
								temp=i+1;
								break;
							}
						}
					}
					while (temp<GS.length){
						word=word+GS[temp];
						temp++;
					}
					cluster3.add(word);
				}
				
				String textQuestion= new String(qword);		
				for (int i=0;i<textArray.length;i++){
					textArray[i]=removeQuestion(onlyOneString(textArray[i]), onlyOneString(textQuestion));
				}

				String[] newTextArray= removeSimilarity(textArray);
				
				for (int i =0; i < newTextArray.length;i++){
					String set=deDup(newTextArray[i]).toString().replaceAll(",", "");
					//System.out.println(set);
				}

				PrintWriter writer = new PrintWriter("/home/farbod/Documents/University/Courses/Teamproject/Finalforreport/Runningthecodehere/forPython.txt", "UTF-8");
				for (int i =0 ; i < newTextArray.length;i++){
					String set=deDup(newTextArray[i]).toString().replaceAll(",", "");
					StanfordLemmatizer test2 = new StanfordLemmatizer();
					List<String> orgLemm = test2.lemmatize(orgtextArray[i]);
					for (int j=0;j<orgLemm.size();j++){
						set=set+" "+orgLemm.get(j);
					}
					set.replaceAll("-", " ");
					String[] arr = set.split(" ");    
					String newSet="";
					for ( String ss : arr) {
						if (StopWordsRemoval(ss)==0){	 
							newSet=newSet+" "+ss;
						}
					}
					writer.println(deDup(newSet).toString().replaceAll(",", ""));
					//System.out.println(deDup(newSet).toString().replaceAll(",", ""));
					orgLemm.clear();
				}
				writer.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//System.out.print(cluster1);

		
		//System.out.println(test2.lemmatize(newTextArray[0]));
		String s = null;
    	 
		 try {
		      
		 // run the Unix "ps -ef" command
		 // using the Runtime exec method:
		 Process p = Runtime.getRuntime().exec("python2.7 /home/farbod/Documents/University/Courses/Teamproject/Finalforreport/Runningthecodehere/google-word2vec.py");
		              
		             BufferedReader stdInput = new BufferedReader(new
		                  InputStreamReader(p.getInputStream()));
		  
		             BufferedReader stdError = new BufferedReader(new
		                  InputStreamReader(p.getErrorStream()));
		  
		             // read the output from the command
		 System.out.println("Here is the standard output of the command:\n");
		 while ((s = stdInput.readLine()) != null) {
		     System.out.println(s);
		 }
		  
		 // read any errors from the attempted command
		 System.out.println("Here is the standard error of the command (if any):\n");
		     while ((s = stdError.readLine()) != null) {
		         System.out.println(s);
		     }
		      
		     System.exit(0);
		 }
		 catch (IOException e) {
		     System.out.println("exception happened - here's what I know: ");
		     e.printStackTrace();
		     System.exit(-1);
		 }
	}

}
